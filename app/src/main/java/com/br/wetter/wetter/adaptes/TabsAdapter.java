package com.br.wetter.wetter.adaptes;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.br.wetter.wetter.views.fragment.BoloesFragment;
import com.br.wetter.wetter.views.fragment.PartidasFragment;
import com.br.wetter.wetter.views.fragment.SelecoesFragment;

public class TabsAdapter extends FragmentStatePagerAdapter{

    private String[] titles = {"Partidas", "Seleções", "Bolões"};

    public TabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment mFragment = null;
        switch (position){
            case 0:
                mFragment = new PartidasFragment();
                break;
            case 1:
                mFragment = new SelecoesFragment();
                break;
            case 2:
                mFragment = new BoloesFragment();
                break;
        }
        return mFragment;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
