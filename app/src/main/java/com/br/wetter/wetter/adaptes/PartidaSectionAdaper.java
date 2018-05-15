package com.br.wetter.wetter.adaptes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.br.wetter.wetter.R;
import com.br.wetter.wetter.TO.SectionPartidaTo;

import java.util.ArrayList;
import java.util.List;

public class PartidaSectionAdaper extends RecyclerView.Adapter<PartidaSectionAdaper.SectionViewHolder> {

    private Context mContext;
    private List<SectionPartidaTo> list;

    public PartidaSectionAdaper(Context mContext, List<SectionPartidaTo> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public SectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_item_partida, parent, false);
        return new SectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SectionViewHolder holder, int position) {
        SectionPartidaTo mSectionPartidaTo = list.get(position);

        holder.data.setText(mSectionPartidaTo.getData());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        holder.mRecyclerView.setLayoutManager(linearLayoutManager);
        holder.mRecyclerView.setHasFixedSize(true);
        holder.mRecyclerView.setNestedScrollingEnabled(false);
        PartidaAdaperrecyclerview mAdaperrecyclerview = new PartidaAdaperrecyclerview(mContext, mSectionPartidaTo.getmPartidaToList());
        holder.mRecyclerView.setAdapter(mAdaperrecyclerview);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SectionViewHolder extends RecyclerView.ViewHolder {
        TextView data;
        RecyclerView mRecyclerView;

        public SectionViewHolder(View itemView) {
            super(itemView);
            data = itemView.findViewById(R.id.data_section_part);
            mRecyclerView = itemView.findViewById(R.id.recyclerview_section_part);
        }
    }
}
