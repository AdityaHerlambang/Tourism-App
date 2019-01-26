package com.artace.tourism;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.artace.tourism.adapter.TourAdapter;
import com.artace.tourism.connection.DatabaseConnection;
import com.artace.tourism.model.Tour;
import com.artace.tourism.utils.StringPostRequest;
import com.artace.tourism.utils.VolleyResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TourActivity extends AppCompatActivity{

    NestedScrollView mScroller;
    String TAG = "Tour";
    Toolbar mToolbar;
    ImageView mImage;
    AppBarLayout mAppBar;
    private RecyclerView mList;
    private List<Tour> tourList;
    private RecyclerView.Adapter adapter;

    String CATEGORY = "2";
    String COUNTRY = "1";

    float opacity = 0;
    String tipe, id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);

        Intent intent = getIntent();
        tipe = intent.getStringExtra("tipe");
        id = intent.getStringExtra("id");

        mScroller = (NestedScrollView) findViewById(R.id.activity_category_nestedScrollView);
        mToolbar = (Toolbar) findViewById(R.id.activity_category_toolbar);
        mImage = (ImageView) findViewById(R.id.activity_category_imageHeader);
        mAppBar = (AppBarLayout) findViewById(R.id.activity_category_appBarLayout);
        mList = (RecyclerView) findViewById(R.id.activity_category_recyclerTour);

        tourList = new ArrayList<>();
        adapter = new TourAdapter(getApplicationContext(), tourList);

        mList.setAdapter(adapter);

        loadData();

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

    private void searchData(String search){
        Map<String,String> params = new HashMap<String, String>();
        if (tipe == COUNTRY){
            params.put("country",id);
            params.put("category",search);
        }
        if (tipe == CATEGORY){
            params.put("category",id);
            params.put("country",search);
        }

        Log.d(TAG,params.toString());
        String url = DatabaseConnection.TOUR_URL;

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
                                Log.d(TAG,"RESPONSE = "+obj.toString());

                                Tour tour = new Tour();
                                tour.setNama(obj.getString("name"));
                                tour.setImageCategory(obj.getString("image"));
                                tour.setShortDesc(obj.getString("description"));
                                if (obj.getString("duration_day") == null){
                                    tour.setDurasi(obj.getString("duration_day"));
                                }
                                else if (obj.getString("duration_hour") == null){
                                    tour.setDurasi(obj.getString("duration_hour"));
                                }
                                tour.setHarga(obj.getString("price"));
                                tour.setLokasi(obj.getString("lokasi"));

                                tourList.add(tour);
                            } catch (Exception e) {
                                Log.e(TAG,e.getMessage());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    adapter.notifyDataSetChanged();
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

    private void loadData(){
        Map<String,String> params = new HashMap<String, String>();
        Log.d(TAG,params.toString());
        String url = DatabaseConnection.TOUR_URL;
        if (tipe == COUNTRY){
            url = DatabaseConnection.getCountryUrl(id);
        }
        if (tipe == CATEGORY){
            url = DatabaseConnection.getCategoryUrl(id);
        }

        Log.d(TAG,url);

        StringPostRequest strReq = new StringPostRequest();
        strReq.sendRequest(Request.Method.GET,this, params, url, new VolleyResponseListener() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray jsonArray = new JSONArray(response);
                    try {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                Log.d(TAG,"RESPONSE = "+obj.toString());

                                Tour tour = new Tour();
                                tour.setNama(obj.getString("name"));
                                tour.setImageCategory(obj.getString("image"));
                                tour.setShortDesc(obj.getString("description"));
                                tour.setDurasi(obj.getString("durasi"));
                                tour.setHarga(obj.getString("price"));
                                tour.setLokasi(obj.getString("lokasi"));

                                tourList.add(tour);
                            } catch (Exception e) {
                                Log.e(TAG,e.getMessage());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    adapter.notifyDataSetChanged();
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
}
