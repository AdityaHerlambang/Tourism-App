package com.artace.tourism;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.artace.tourism.adapter.TourAdapter;
import com.artace.tourism.connection.DatabaseConnection;
import com.artace.tourism.model.ModelTour;
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
    String TAG = "ModelTour";
    Toolbar mToolbar;
    ImageView mImage;
    AppBarLayout mAppBar;
    private RecyclerView mList;
    private List<ModelTour> modelTourList = new ArrayList<ModelTour>();
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

        mScroller = (NestedScrollView) findViewById(R.id.activity_tour_nestedScrollView);
        mToolbar = (Toolbar) findViewById(R.id.activity_tour_toolbar);
        mImage = (ImageView) findViewById(R.id.activity_tour_imageHeader);
        mAppBar = (AppBarLayout) findViewById(R.id.activity_tour_appBarLayout);

        mList = (RecyclerView) findViewById(R.id.activity_tour_recyclerTour);
        adapter = new TourAdapter(this, modelTourList);
        mList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mList.setAdapter(adapter);

        loadData();

        mToolbar.setTitle("ModelTour");
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

    private void loadData(){
        Map<String,String> params = new HashMap<String, String>();
        params.put("emptyvalue","emptyvalue");
        Log.d(TAG,params.toString());

        String url = DatabaseConnection.getTourUrl();
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

                                ModelTour modelTour = new ModelTour();
                                modelTour.setName(obj.getString("name"));
                                modelTour.setImage(obj.getString("image"));
                                modelTour.setShort_description(obj.getString("short_description"));
                                modelTour.setDuration_day(obj.getInt("duration_hour"));
                                modelTour.setDuration_hour(obj.getInt("duration_day"));

                                modelTour.setAdult_price(obj.getInt("adult_price"));
                                modelTour.setLocation(obj.getString("location"));
                                modelTourList.add(modelTour);

                                Log.e("sdf",obj.toString());

                            } catch (Exception e) {
                                Log.e(TAG,e.getMessage());
                            }finally {
                                //Notify adapter about data changes
                                adapter.notifyItemChanged(i);
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
                loadData();
                Log.e(TAG,"Ada ERROR : "+message);
            }
        });



//        if (tipe == COUNTRY){
//            url = DatabaseConnection.getCountryUrl(id);
//        }
//        if (tipe == CATEGORY){
//            url = DatabaseConnection.getCategoryUrl(id);
//        }

        Log.d(TAG,url);
    }
}
