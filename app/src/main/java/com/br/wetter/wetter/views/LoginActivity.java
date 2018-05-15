package com.br.wetter.wetter.views;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.br.wetter.wetter.R;
import com.br.wetter.wetter.TO.UsersTO;
import com.br.wetter.wetter.bo.LoginBO;
import com.br.wetter.wetter.util.ConstantesFirebase;
import com.br.wetter.wetter.util.ProgressBar;
import com.br.wetter.wetter.util.UtilMensageResultFirebase;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private TextView nao_tenho_cadastro, esqueci_minha_senha;
    private AlertDialog mDialog;
    private EditText email, senha;
    private LoginButton logar_com_facebook;

    private CallbackManager callbackManager;


    private FirebaseAuth mAuth;

    private DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();

    //    create user
    private DatabaseReference userReference = mDatabaseReference.child(ConstantesFirebase.USER);

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        inicializarComponentes();


       /* try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        }
        catch (PackageManager.NameNotFoundException e) {

        }
        catch (NoSuchAlgorithmException e) {

        }*/
    }

    @Override
    public void onStart() {
        super.onStart();
        updateUserLogado(mAuth.getCurrentUser());
        // Check if user is signed in (non-null) and update UI accordingly.

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (callbackManager != null) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void inicializarComponentes() {
        email = findViewById(R.id.email_lg_edt);
        senha = findViewById(R.id.senha_lg_edt);
        nao_tenho_cadastro = findViewById(R.id.no_cadastro_lg_txt);
        esqueci_minha_senha = findViewById(R.id.esqueci_senha_lg_txt);
        logar_com_facebook = findViewById(R.id.logar_facebook_lg_btn);

        mProgressBar = new ProgressBar(this);
    }

    public void logar_facebook(View view) {
        callbackManager = CallbackManager.Factory.create();
        logar_com_facebook.setReadPermissions("email", "public_profile");
        logar_com_facebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Validação Cancelada!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }

    public void entrar(View view) {
        List<TextView> textViewList = new ArrayList<>();
        textViewList.add(email);
        textViewList.add(senha);
        if (new LoginBO().isValid(textViewList)) {
            logarComEmail();
        }
    }

    public void view_cadastro(View view) {
        startActivity(new Intent(LoginActivity.this, CadastroActivity.class));
    }

    static final String TAG = "TOKEN_FACEBOOK";

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            UsersTO userObj = new UsersTO();
                            userObj.setNome(user.getDisplayName());
                            userObj.setEmail(user.getEmail());
                            userObj.setFechas(0);
                            userObj.setMeusPontos(0);
                            userObj.setFoto_perfil(String.valueOf(user.getPhotoUrl()));
                            userReference.child(user.getUid()).setValue(userObj);

                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Falha na Autenticação.",
                                    Toast.LENGTH_SHORT).show();
                            updateUserLogado(null);
                        }

                    }
                });
    }

    private void updateUserLogado(FirebaseUser user) {
        LoginButton face_btn = findViewById(R.id.logar_facebook_lg_btn);
        if (user != null) {
            if (face_btn.getText().toString().equalsIgnoreCase("sair")) {
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            } else if (user.isEmailVerified()) {
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            } else {
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
            }
        }
        mProgressBar.hidenDialogo();
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
                    verificarEmail();
                    dialog.dismiss();
                }
            }
        });
    }

    private void verificarEmail() {
        final FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
        mFirebaseUser.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
//                            Toast.makeText(LoginActivity.this, "Email validado", Toast.LENGTH_SHORT).show();
                        } else {
//                            Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void logarComEmail() {
        mProgressBar.openDialogo();
        String emailTxt, senhaTxt;
        emailTxt = email.getText().toString();
        senhaTxt = senha.getText().toString();
        mAuth.signInWithEmailAndPassword(emailTxt, senhaTxt).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    if (!firebaseUser.isEmailVerified()) {
                        dialogoInfoEmail();
                    } else {
                        updateUserLogado(firebaseUser);
                    }

                } else {
                    Toast.makeText(LoginActivity.this, UtilMensageResultFirebase.getMsg(
                            task.getException().getMessage()), Toast.LENGTH_SHORT).show();
//                        Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    updateUserLogado(null);
                }

                if (!task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    updateUserLogado(null);
                }
            }
        });
    }
}
