package com.artace.tourism;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


import com.artace.tourism.utils.DrawerMenu;

public class ProviderActivity extends AppCompatActivity {

    BottomNavigationView mBottomNavigation;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);

        initDrawerMenu();

        //loading the default fragment
        replaceFragment(new ProviderProfileFragment());

        initBottomBar();

    }

    private void initBottomBar(){
        mBottomNavigation = (BottomNavigationView) findViewById(R.id.activity_provider_bottom_navigation);

        mBottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch (item.getItemId()) {
                    case R.id.navigation_profile:
                        fragment = new ProviderProfileFragment();
                        break;

                    case R.id.navigation_tour:
                        fragment = new ProviderTourFragment();
                        break;

                    case R.id.navigation_confirm:
                        fragment = new ProviderConfirmFragment();
                        break;
                }

                return replaceFragment(fragment);
            }
        });
    }

    private boolean replaceFragment (Fragment fragment){
        String backStateName =  fragment.getClass().getName();
        String fragmentTag = backStateName;

        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);

        if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null){ //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.activity_provider_container, fragment, fragmentTag);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(backStateName);
            ft.commit();
            return true;
        }
        return true;
    }

    private void initDrawerMenu(){
        mToolbar = findViewById(R.id.activity_provider_toolbar);
        this.setSupportActionBar(mToolbar);
        ActionBar ab = this.getSupportActionBar();
        ab.setTitle("");
//
        DrawerMenu drawer = new DrawerMenu();
        drawer.createDrawer(this, this, mToolbar);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.activity_provider_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    //langsung keluar
    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
