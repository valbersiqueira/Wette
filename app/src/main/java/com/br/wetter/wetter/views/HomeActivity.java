package com.br.wetter.wetter.views;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.br.wetter.wetter.R;
import com.br.wetter.wetter.adaptes.TabsAdapter;
import com.br.wetter.wetter.tabs.SlidingTabLayout;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar_home;

    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        inicializarComponent();
    }

    private void inicializarComponent() {
        this.toolbar_home = findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar_home);
       /* getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/
        getSupportActionBar().setTitle(null);
        this.mSlidingTabLayout = findViewById(R.id.tab_home);
        this.mViewPager = findViewById(R.id.view_page_home);
        inicializarTabView();
    }

    private void inicializarTabView() {
        mSlidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.amarelo));
        TabsAdapter mTabsAdapter = new TabsAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mTabsAdapter);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                return true;
            case R.id.podium:
//                Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                finish();
            default:
                return  super.onOptionsItemSelected(item);
        }
    }
}
