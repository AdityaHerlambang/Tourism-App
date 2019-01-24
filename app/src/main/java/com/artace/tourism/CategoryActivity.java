package com.artace.tourism;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;


public class CategoryActivity extends AppCompatActivity{

    NestedScrollView mScroller;
    String TAG = "Category";
    Toolbar mToolbar;
    ImageView mImage;
    AppBarLayout mAppBar;

    float opacity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        mScroller = (NestedScrollView) findViewById(R.id.activity_category_nestedScrollView);
        mToolbar = (Toolbar) findViewById(R.id.activity_category_toolbar);
        mImage = (ImageView) findViewById(R.id.activity_category_imageHeader);
        mAppBar = (AppBarLayout) findViewById(R.id.activity_category_appBarLayout);

        mToolbar.setTitle("Category");
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        if (mScroller != null) {
            mScroller.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

//              scroll down
                if (scrollY > oldScrollY) {
                    if (scrollY > 100 && scrollY <= 400){
                        settingToolbar(scrollY);
                    }
                }

//              scroll up
                if (scrollY < oldScrollY) {
                    if (scrollY > 100 && scrollY <= 400){
                        settingToolbar(scrollY);
                    }
                }

                if (scrollY >= 400){
                    mAppBar.setElevation(6);
                    int color = 135;

                    if (getSupportActionBar() != null){
                        Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
                        upArrow.setColorFilter(Color.argb(255,color,color,color), PorterDuff.Mode.SRC_ATOP);
                        getSupportActionBar().setHomeAsUpIndicator(upArrow);
                    }

                    mToolbar.setTitleTextColor(Color.argb(255,color,color,color));
                    mToolbar.setBackgroundColor(Color.argb(255, 255, 255, 255));
                }

//                check for top
                if (scrollY == 0) {
                    mAppBar.setElevation(0);
                    mToolbar.setTitleTextColor(Color.argb(255,255,255,255));
                    mToolbar.setBackgroundColor(Color.argb(0, 255, 255, 255));
                    mAppBar.bringToFront();
                }

//                check for bottom
//                if (scrollY == ( v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight() )) {
//                    Log.i(TAG, "BOTTOM SCROLL");
//                }
                }
            });
        }

    }

    private void settingToolbar(int scrollY){

        opacity = ((float)scrollY - 100) / 400;
        mAppBar.setElevation(opacity * 6);
        int color = 255 - (int)(120 * (float)opacity);

        if (getSupportActionBar() != null){
            Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
            upArrow.setColorFilter(Color.argb(255,255,255,255), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }

        mToolbar.setTitleTextColor(Color.argb(255,255,255,255));
        mToolbar.setBackgroundColor(Color.argb((int)(opacity * 255), 255, 255, 255));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
