package com.artace.tourism;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.artace.tourism.connection.DatabaseConnection;
import com.artace.tourism.databinding.ActivityTourDetailBinding;
import com.artace.tourism.utils.StringPostRequest;
import com.artace.tourism.utils.VolleyResponseListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TourDetailActivity extends AppCompatActivity {

    ActivityTourDetailBinding binding;

    final static String TAG = "TourDetail";

    NestedScrollView mScroller;
    Toolbar mToolbar;
    AppBarLayout mAppBar;
    float opacity = 0;
    String id, name, image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_tour_detail);

        Bundle extras = getIntent().getExtras();
        id = extras.getString("id");
        name = extras.getString("name");
        image = extras.getString("image");

        setHeader();
        loadData();



    }

    private void loadData(){
        String url = DatabaseConnection.getTourDetail();

        Map<String,String> params = new HashMap<String, String>();
        params.put("id",id);
        Log.d(TAG,params.toString());
        Log.d(TAG,url);

        StringPostRequest strReq = new StringPostRequest();
        strReq.sendRequest(Request.Method.POST,this, params, url, new VolleyResponseListener() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONArray jsonArray = new JSONArray(response);
                    try {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject obj = jsonArray.getJSONObject(i);

                                Picasso.with(TourDetailActivity.this)
                                        .load(image)
                                        .placeholder(R.drawable.placeholder_vertical)
                                        .into(binding.image);
                                binding.name.setText(name);
                                binding.airport.setText(obj.getString("nearest_airport")+" (Nearest Airport)");
                                binding.duration.setText(obj.getString("duration_day")+" Day(s)");
                                binding.price.setText("US$ "+obj.getString("adult_price"));
                                binding.shortdesc.setText(obj.getString("short_description"));
                                binding.overview.setText(obj.getString("overview"));
                                binding.activities.setText(obj.getString("activity"));
                                binding.preparation.setText(obj.getString("preparation"));

                            } catch (Exception e) {
                                Log.e(TAG,e.getMessage());
                            }
                        }
                    } catch (Exception e) {
                        Log.e(TAG,"2 = " + e.getMessage().toString());
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(String message) {
                Log.e(TAG,"Ada ERROR : "+message);
            }
        });
    }

    private void setHeader(){
        mScroller = (NestedScrollView) findViewById(R.id.nestedScrollView);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mAppBar = (AppBarLayout) findViewById(R.id.appBarLayout);

        mToolbar.setTitle("Tour");
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
