package com.br.wetter.wetter.views.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.br.wetter.wetter.R;
import com.br.wetter.wetter.TO.PartidaTo;
import com.br.wetter.wetter.TO.SectionPartidaTo;
import com.br.wetter.wetter.adaptes.PartidaSectionAdaper;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PartidasFragment extends Fragment {

    private RecyclerView mRecyclerView;

    public PartidasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_partidas, container, false);
        inicializarComponentes(view);
        setRecyclerviewPartida(view);
        return view;
    }

    private void inicializarComponentes(View view) {
        mRecyclerView = view.findViewById(R.id.partida_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);

    }

    private void setRecyclerviewPartida(View view){
        List<PartidaTo> list = new ArrayList<>();
        List<PartidaTo> list2 = new ArrayList<>();
        PartidaTo mPartidaTo = new PartidaTo();
        mPartidaTo.setHora("11:00");
        mPartidaTo.setNome_band_left("Brasil");
        mPartidaTo.setGetNome_band_right("Belgica");
        list.add(mPartidaTo);

        PartidaTo mPartidaTo2 = new PartidaTo();
        mPartidaTo2.setHora("12:00");
        mPartidaTo2.setNome_band_left("Brasil");
        mPartidaTo2.setGetNome_band_right("Belgica");
        list2.add(mPartidaTo);
        list2.add(mPartidaTo2);

        List<SectionPartidaTo> listSe = new ArrayList<>();
        SectionPartidaTo to = new SectionPartidaTo();
        to.setData("QUI 16/06/2018");
        to.setmPartidaToList(list);

        SectionPartidaTo to2 = new SectionPartidaTo();
        to2.setData("SEX 16/06/2018");
        to2.setmPartidaToList(list2);

        listSe.add(to);
        listSe.add(to2);

        PartidaSectionAdaper mPartidaSectionAdaper = new PartidaSectionAdaper(view.getContext(), listSe);
        mRecyclerView.setAdapter(mPartidaSectionAdaper);
    }

}
