package com.br.wetter.wetter.adaptes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.br.wetter.wetter.R;
import com.br.wetter.wetter.TO.PartidaTo;

import java.util.List;

public class PartidaAdaperrecyclerview extends RecyclerView.Adapter<PartidaAdaperrecyclerview.MyViewHolder> {

    private Context mContext;
    private List<PartidaTo> partidaToList;

    public PartidaAdaperrecyclerview(Context mContext, List<PartidaTo> partidaToList) {
        this.mContext = mContext;
        this.partidaToList = partidaToList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_partida_recyclerview,parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PartidaTo mPartidaTo = partidaToList.get(position);

        holder.hora.setText("X\n" + mPartidaTo.getHora());
        holder.nome_left.setText(mPartidaTo.getNome_band_left());
        holder.nome_right.setText(mPartidaTo.getGetNome_band_right());
        holder.apostar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "click btn apostar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return partidaToList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView band_left, band_right;
        TextView nome_left, nome_right, hora;
        Button apostar;

        public MyViewHolder(View view) {
            super(view);
            band_left = view.findViewById(R.id.img_left_item_part);
            band_right = view.findViewById(R.id.img_right_item_part);
            nome_left = view.findViewById(R.id.nome_band_left_item_part);
            nome_right = view.findViewById(R.id.nome_band_right__item_part);
            hora = view.findViewById(R.id.horario__item_part);
            apostar = view.findViewById(R.id.apostar_btn_item_part);
        }
    }
}
