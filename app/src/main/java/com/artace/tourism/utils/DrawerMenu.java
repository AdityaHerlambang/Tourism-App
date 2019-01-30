package com.artace.tourism.utils;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import com.artace.tourism.LoginActivity;
import com.artace.tourism.R;
import com.artace.tourism.RegisterProviderActivity;
import com.artace.tourism.RegisterTrevelerActivity;
import com.artace.tourism.TentangAplikasiActivity;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;

public class DrawerMenu {

    String password, username, nama, sub_title, foto;
    boolean session;
    SharedPreferences sharedpreferences;

    public DrawerMenu(){

    }

    public void createDrawer(Context context, AppCompatActivity activity, Toolbar mToolbar){

        //get the value username and password from login
        // Cek session login jika TRUE maka langsung buka MainActivity

            nama = "Guest";
            sub_title = "Silahkan Login";



        // Create the AccountHeader

        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Picasso.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Picasso.with(imageView.getContext()).cancelRequest(imageView);
            }
        });

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.drawable.material_background)
                .addProfiles(
                        new ProfileDrawerItem().withName(nama).withEmail(sub_title)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();


            buildDrawerNotLoggedIn(context,activity,mToolbar, headerResult);


    }

    private void buildDrawerNotLoggedIn(Context context, AppCompatActivity activity, Toolbar mToolbar, AccountHeader headerResult){
        final Context contextFinal = context;
        final AppCompatActivity activityFinal = activity;
        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Login").withIcon(GoogleMaterial.Icon.gmd_account_circle);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName("Daftar Sebagai Seniman").withIcon(GoogleMaterial.Icon.gmd_account_box);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withName("Daftar Sebagai Event organizer").withIcon(GoogleMaterial.Icon.gmd_account_box);
        SecondaryDrawerItem item4 = new SecondaryDrawerItem().withIdentifier(4).withName("Tentang Aplikasi").withIcon(GoogleMaterial.Icon.gmd_info);

        //create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(activity)
                .withAccountHeader(headerResult)
                .withToolbar(mToolbar)
                .addDrawerItems(
                        item1,
                        item2,
                        item3,
                        new DividerDrawerItem(),
                        item4
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem.getIdentifier() == 1){
                            Intent intent = new Intent(contextFinal, LoginActivity.class);
                            contextFinal.startActivity(intent);
                        }
                        if (drawerItem.getIdentifier() == 2){
                            Intent intent = new Intent(contextFinal, RegisterTrevelerActivity.class);
                            contextFinal.startActivity(intent);
                        }
                        if (drawerItem.getIdentifier() == 3){
                            Intent intent = new Intent(contextFinal, RegisterProviderActivity.class);
                            contextFinal.startActivity(intent);
                        }
                        if (drawerItem.getIdentifier() == 4){
                            Intent intent = new Intent(contextFinal, TentangAplikasiActivity.class);
                            contextFinal.startActivity(intent);
                        }
                        return false;
                    }
                })
                .build();

        if(mToolbar != null)
            result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
    }

}
