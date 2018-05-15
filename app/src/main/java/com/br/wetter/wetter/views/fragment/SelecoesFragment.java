package com.br.wetter.wetter.views.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.br.wetter.wetter.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelecoesFragment extends Fragment {


    public SelecoesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_selecoes, container, false);
        return view;
    }

}
