package com.br.wetter.wetter.views;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.br.wetter.wetter.R;
import com.br.wetter.wetter.bo.CreateUsersBO;
import com.br.wetter.wetter.TO.UsersTO;
import com.br.wetter.wetter.util.ConstantesFirebase;
import com.br.wetter.wetter.util.ProgressBar;
import com.br.wetter.wetter.util.UtilIcon;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CadastroActivity extends AppCompatActivity {

    public static final int PERMISSAO_CAMERA = 1;
    public static final int PERMISSAO_GELERIA = 2;
    public static final int RESULT_IMAGEM = 112;
    public static final int RESULT_GALERIA = 113;

    private TextView foto_cadastro, nome, email, senha, conf_senha;
    private CircleImageView foto_btn;

    //    databese
    private DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();

    //    create user
    private DatabaseReference userReference = mDatabaseReference.child(ConstantesFirebase.USER);

    //    storage imagens
    private FirebaseStorage mStorage = FirebaseStorage.getInstance(ConstantesFirebase.URL_STORAGE);
    private StorageReference storageRef = mStorage.getReference();

    private FirebaseAuth mFirebaseAuth;

    private Bitmap mBitmap;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        this.inicializarComponents();


    }

    private void inicializarComponents() {
        this.foto_cadastro = findViewById(R.id.icon_foto_cadastro);
        nome = findViewById(R.id.nome_cad_edt);
        email = findViewById(R.id.email_cad_edt);
        senha = findViewById(R.id.senha_cad_edt);
        conf_senha = findViewById(R.id.conf_senha_cad_edt);

        foto_btn = findViewById(R.id.foto_btn);

        inicializarFotoCadastro();
        mProgressBar = new ProgressBar(this);
    }

    private void inicializarFotoCadastro() {
        foto_cadastro.setTypeface(UtilIcon.getTypeface(this));
    }

    private void createUsers(String email, String senha) {
        try {

            mFirebaseAuth = FirebaseAuth.getInstance();
            mFirebaseAuth.createUserWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String uId = String.valueOf(task.getResult().getUser().getUid());
                                if (mBitmap != null) {
                                    salvarFoto(uId);
                                } else {
                                    salvarUser(uId, "");
                                }
                            } else {
                                Toast.makeText(CadastroActivity.this,
                                        "Falha ao tentar se Cadastrar", Toast.LENGTH_SHORT).show();
                            }

                            if (!task.isSuccessful()) {
                                Toast.makeText(CadastroActivity.this,
                                        task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        } catch (Exception e) {
            Log.e("ERRO_CREATE_USER", e.getMessage());
        }
    }

    public void cancelar(View view) {
        CadastroActivity.this.finish();
    }

    public void salvar_click(View view) {
        List<TextView> texts = new ArrayList<>();
        texts.add(nome);
        texts.add(email);
        texts.add(senha);
        texts.add(conf_senha);

        if (new CreateUsersBO().isValid(texts)) {
            mProgressBar.openDialogo();
            createUsers(email.getText().toString(), senha.getText().toString());
        }
    }

    public void tirar_foto(View view) {
        selectCameraGaleria();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("RESULT_COD_CAMERA", String.valueOf(requestCode));
        if (requestCode == RESULT_IMAGEM) {
            if (data != null) {
                Bundle mBundle = data.getExtras();
                if (mBundle != null) {
                    mBitmap = (Bitmap) mBundle.get("data");
                    foto_btn.setImageBitmap(mBitmap);
                    foto_cadastro.setText("");
                }
            }
        } else if (requestCode == RESULT_GALERIA) {
            if (data != null) {
                Uri imagemSelecionada = data.getData();
                if (imagemSelecionada != null) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imagemSelecionada);
                        mBitmap = Bitmap.createScaledBitmap(bitmap, 250, 250, true);
                        foto_btn.setImageBitmap(mBitmap);
                        foto_cadastro.setText("");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void salvarFoto(final String uId) {
        StorageReference imagemRef = storageRef.child("imagens/" + uId + ".png");
        Bitmap bitmap = mBitmap;
        ByteArrayOutputStream mByteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, mByteArrayOutputStream);
        byte[] data = mByteArrayOutputStream.toByteArray();

        UploadTask mUploadTask = imagemRef.putBytes(data);
        mUploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("EXCEPTION_IMAGEM", e.getMessage());
                Toast.makeText(CadastroActivity.this, "Falha ao tentar se Cadastrar", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                salvarUser(uId, String.valueOf(taskSnapshot.getDownloadUrl()));
            }
        });
    }

    private void salvarUser(String uId, String url_imagem) {
        UsersTO user = new UsersTO();
        user.setNome(nome.getText().toString());
        user.setEmail(email.getText().toString());
        user.setFechas(0);
        user.setMeusPontos(0);
        user.setFoto_perfil(url_imagem);
        userReference.child(uId).setValue(user);
        verificarEmail();
        mProgressBar.hidenDialogo();
        dialogoInfoEmail();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    private void calDialog(String msg, final String[] permission, final int codPermissao) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .title(R.string.title_dialogo)
                .content(msg)
                .positiveText(R.string.posti_text_dialogo)
                .negativeText(R.string.negative_text_dialogo)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        ActivityCompat.requestPermissions(CadastroActivity.this, permission, codPermissao);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                });
        MaterialDialog dialog = builder.build();
        dialog.show();
    }


    private void selectCameraGaleria() {
        ImageView camera, galeria;
        TextView cameraTxt, galeriaTxt;

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogo_open_camera_galeria, null);
        mBuilder.setView(view);

        cameraTxt = view.findViewById(R.id.camera_txt);
        galeriaTxt = view.findViewById(R.id.galeria_txt);
        cameraTxt.setTypeface(UtilIcon.getTypeface(this));
        galeriaTxt.setTypeface(UtilIcon.getTypeface(this));


        camera = view.findViewById(R.id.camera_btn);
        galeria = view.findViewById(R.id.galeria_btn);

        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                openCamera();
            }
        });

        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                openGaleria();
            }
        });

    }

    public void openCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                calDialog("Permissão para acessar a camera do dispositivo",
                        new String[]{Manifest.permission.CAMERA}, PERMISSAO_CAMERA);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSAO_CAMERA);
            }
        } else {
            startAbriCamera();
        }
    }

    public void openGaleria() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                calDialog("Permissão para acessar a galeria de fotos do dispositivo",
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSAO_GELERIA);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSAO_GELERIA);
            }
        } else {
            startGaleria();
        }
    }

    private void startAbriCamera() {
        Intent mIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(mIntent, RESULT_IMAGEM);
    }

    private void startGaleria() {
        Intent mIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(mIntent, "Selecione uma imagem"), RESULT_GALERIA);
    }

    private void verificarEmail() {
        final FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mFirebaseUser.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(CadastroActivity.this, "Email validado", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CadastroActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void dialogoInfoEmail() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        LayoutInflater mLayoutInflater = LayoutInflater.from(this);
        View view = mLayoutInflater.inflate(R.layout.dialogo_info_email, null);
        Button ok_btn = view.findViewById(R.id.ok_btn_dialogo_email);
        TextView msg_txt = view.findViewById(R.id.msg_dialogo_email);
        msg_txt.setText(R.string.msg_dialogo_email);
        mBuilder.setView(view);
        mBuilder.setCancelable(false);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null) {
                    dialog.dismiss();
                    startActivity(new Intent(CadastroActivity.this, LoginActivity.class));
                }
            }
        });
    }
}
