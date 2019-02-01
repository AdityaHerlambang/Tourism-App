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
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.artace.tourism.adapter.TourAdapter;
import com.artace.tourism.adapter.TourPopularAdapter;
import com.artace.tourism.connection.DatabaseConnection;
import com.artace.tourism.model.ModelTour;
import com.artace.tourism.utils.StringPostRequest;
import com.artace.tourism.utils.VolleyResponseListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TourActivity extends AppCompatActivity{

    NestedScrollView mScroller;
    String TAG = "ModelTour";
    Toolbar mToolbar;
    ImageView mImage, mImageHeader;
    AppBarLayout mAppBar;
    EditText mSearch;
    TextView mTxtHeader;
    private RecyclerView mListTour, mListPopularTour;
    private List<ModelTour> modelTourList = new ArrayList<ModelTour>();
    private RecyclerView.Adapter adapterTour;
    private RecyclerView.Adapter adapterTourPopular;

    String CATEGORY = "2";
    String COUNTRY = "1";
    String url = "";

    float opacity = 0;
    String tipe, id, name, image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);

        Intent intent = getIntent();
        tipe = intent.getStringExtra("tipe");
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        image = intent.getStringExtra("image");

        mScroller = (NestedScrollView) findViewById(R.id.activity_tour_nestedScrollView);
        mToolbar = (Toolbar) findViewById(R.id.activity_tour_toolbar);
        mImage = (ImageView) findViewById(R.id.activity_tour_imageHeader);
        mAppBar = (AppBarLayout) findViewById(R.id.activity_tour_appBarLayout);
        mSearch = (EditText) findViewById(R.id.activity_tour_searchTour);
        mImageHeader = (ImageView) findViewById(R.id.activity_tour_imageHeader);
        mTxtHeader = (TextView) findViewById(R.id.activity_tour_txtHeader);

        Picasso.with(this)
                .load(image)
                .placeholder(R.drawable.placeholder_vertical)
                .into(mImageHeader);

        mTxtHeader.setText(name);

        inisialisasiToolbar();
        settingRecyclerTour("tour");
        settingRecyclerTourPopular("tour");

        mSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    settingRecyclerTourPopular("search");
                    settingRecyclerTour("search");
                    return true;
                }
                return false;
            } });

        hideSoftKeyboard();
    }

    private void inisialisasiToolbar(){
        mToolbar.setTitle("ModelTour");
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
            upArrow.setColorFilter(Color.argb(255,255,255,255), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
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
//               on the line
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
        finish();
        onBackPressed();
        return true;
    }

    private void settingRecyclerTourPopular(String status){
        mListPopularTour = (RecyclerView) findViewById(R.id.activity_tour_recyclerPopularTour);
        adapterTourPopular = new TourPopularAdapter(this, modelTourList);
        mListPopularTour.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mListPopularTour.setAdapter(adapterTourPopular);

        if (status.equals("search")){
            loadDataSearch("Popular");
        }
        else{
            loadData("Popular");
        }
    }

    private void settingRecyclerTour(String status){
        mListTour = (RecyclerView) findViewById(R.id.activity_tour_recyclerTour);
        adapterTour = new TourAdapter(this, modelTourList);
        mListTour.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mListTour.setAdapter(adapterTour);

        if (status.equals("search")){
            loadDataSearch("Tour");
        }
        else{
            loadData("Tour");
        }
    }

    public void loadData(final String status){

        if (tipe.equals(COUNTRY)){
            if (status.equals("Popular")){
                url = DatabaseConnection.getPopularCountryUrl(id);
            }
            else{
                url = DatabaseConnection.getCountryUrl(id);
            }
        }
        if (tipe.equals(CATEGORY)){
            if (status.equals("Popular")){
                url = DatabaseConnection.getPopularCategoryUrl(id);
            }
            else{
                url = DatabaseConnection.getCategoryUrl(id);
            }
        }

        Map<String,String> params = new HashMap<String, String>();
        params.put("emptyvalue","emptyvalue");
        Log.d(TAG,params.toString());
        Log.d(TAG,url);

        StringPostRequest strReq = new StringPostRequest();
        strReq.sendRequest(Request.Method.GET,this, params, url, new VolleyResponseListener() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONArray jsonArray = new JSONArray(response);
                    try {
                        modelTourList.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject obj = jsonArray.getJSONObject(i);

                                ModelTour modelTour = new ModelTour();
                                modelTour.setId(obj.getString("id"));
                                modelTour.setName(obj.getString("name"));
                                modelTour.setImage(obj.getString("image"));
                                modelTour.setShort_description(obj.getString("short_description"));
                                modelTour.setDuration_day(obj.getInt("duration_hour"));
                                modelTour.setDuration_hour(obj.getInt("duration_day"));
                                modelTour.setAdult_price(obj.getInt("adult_price"));
                                modelTour.setLocation(obj.getString("location"));
                                modelTourList.add(modelTour);

                            } catch (Exception e) {
                                Log.e(TAG,e.getMessage());
                            }finally {
                                //Notify adapter about data changes
                                if (status.equals("Popular")){
                                    adapterTourPopular.notifyItemChanged(i);
                                }
                                else if (status.equals("Tour")){
                                    adapterTour.notifyItemChanged(i);
                                }
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

    private void loadDataSearch(final String status){
        Map<String,String> params = new HashMap<String, String>();
        if (tipe.equals(COUNTRY)){
            params.put("country_name",name);
            params.put("category_name",mSearch.getText().toString());
        }
        if (tipe.equals(CATEGORY)){
            params.put("category_name",name);
            params.put("country_name",mSearch.getText().toString());
        }

        Log.d(TAG,params.toString());
        if (status.equals("Tour")){
            url = DatabaseConnection.getSearchTourUrl();
        }
        else if (status.equals("Popular")){
            url = DatabaseConnection.getSearchPopularTourUrl();
        }

        Log.d(TAG, url);

        StringPostRequest strReq = new StringPostRequest();
        strReq.sendRequest(Request.Method.POST,this, params, url, new VolleyResponseListener() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONArray jsonArray = new JSONArray(response);
                    try {
                        modelTourList.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject obj = jsonArray.getJSONObject(i);

                                ModelTour modelTour = new ModelTour();
                                modelTour.setId(obj.getString("id"));
                                modelTour.setName(obj.getString("name"));
                                modelTour.setImage(obj.getString("image"));
                                modelTour.setShort_description(obj.getString("short_description"));
                                modelTour.setDuration_day(obj.getInt("duration_hour"));
                                modelTour.setDuration_hour(obj.getInt("duration_day"));
                                modelTour.setAdult_price(obj.getInt("adult_price"));
                                modelTour.setLocation(obj.getString("location"));
                                modelTourList.add(modelTour);

                            } catch (Exception e) {
                                Log.e(TAG,e.getMessage());
                            }finally {
                                //Notify adapter about data changes
                                if (status.equals("Popular")){
                                    adapterTourPopular.notifyItemChanged(i);
                                }
                                else if (status.equals("Tour")){
                                    adapterTour.notifyItemChanged(i);
                                }
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

    private void hideSoftKeyboard(){
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

}
