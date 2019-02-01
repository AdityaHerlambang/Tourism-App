package com.artace.tourism;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


import com.artace.tourism.utils.DrawerMenu;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ProviderActivity extends AppCompatActivity {

    BottomNavigationView mBottomNavigation;
    Toolbar mToolbar;
    protected ActionBarDrawerToggle drawerToggle;
    String title = "Profile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);

        if(getIntent().getExtras() != null){
            Bundle extras = getIntent().getExtras();
            if(extras.getString("from").equals("Register")){
                SweetAlertDialog sDialog = new SweetAlertDialog(ProviderActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                sDialog.setTitle("Success !");
                sDialog.setContentText("You are now registered and logged in");
                sDialog.show();
            }
        }

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
                        title = "Profile";
                        break;

                    case R.id.navigation_tour:
                        fragment = new ProviderTourFragment();
                        title = "List Tour";
                        break;

                    case R.id.navigation_confirm:
                        fragment = new ProviderConfirmFragment();
                        title = "Confirmation";
                        break;
                }

                return replaceFragment(fragment);
            }
        });
    }

    public boolean replaceFragment (Fragment fragment){
        String backStateName =  fragment.getClass().getName();
        String fragmentTag = backStateName;

        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);
        FragmentTransaction ft = manager.beginTransaction();
        if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null){ //fragment not in back stack, create it.
            ft.replace(R.id.activity_provider_container, fragment, fragmentTag);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(fragmentTag);
            ft.commit();
            return true;
        }
        return true;
    }

    private void initDrawerMenu(){
        mToolbar = findViewById(R.id.activity_provider_toolbar);
        this.setSupportActionBar(mToolbar);
        ActionBar ab = this.getSupportActionBar();
        ab.setTitle(title);
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
