package com.artace.tourism;

import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.artace.tourism.adapter.CountriesAdapter;
import com.artace.tourism.connection.DatabaseConnection;
import com.artace.tourism.model.ModelCountry;
import com.artace.tourism.utils.KenBurnsEffect;
import com.artace.tourism.utils.StringPostRequest;
import com.artace.tourism.utils.VolleyResponseListener;
import com.goka.kenburnsview.KenBurnsView;
import com.goka.kenburnsview.LoopViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Integer[] IMAGE_RESOUCE;

    //RecyclerView and Network
    RecyclerView countriesRecyclerView;
    CountriesAdapter adapter;
    List<ModelCountry> dataList = new ArrayList<ModelCountry>();
    RequestQueue queue;
    SharedPreferences sharedpreferences;
    String url, searchString = "";

    //TAG
    final String TAG = "RealisasiTahunan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IMAGE_RESOUCE = new Integer[]{
                R.drawable.dashboard_header1,
                R.drawable.dashboard_header2,
                R.drawable.dashboard_header3,
        };
        initializeKenBurnsView();
        setCountriesRecyclerView();
    }

    private void setCountriesRecyclerView(){

        countriesRecyclerView = (RecyclerView) findViewById(R.id.main_countries_recyclerview);
        adapter = new CountriesAdapter(this, dataList);
        countriesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        countriesRecyclerView.setAdapter(adapter);

        loadDataCountries();
    }

    private void loadDataCountries(){
        Map<String,String> params = new HashMap<String, String>();
        params.put("emptyvalue","emptyvalue");
        Log.d(TAG,params.toString());

        String url = DatabaseConnection.getAllCountries();
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

                                ModelCountry data = new ModelCountry(
                                        obj.getString("id"),
                                        obj.getString("country_code"),
                                        obj.getString("name"),
                                        obj.getString("image")
                                );

                                dataList.add(data);

                            } catch (Exception e) {
                                Log.e(TAG,e.getMessage());
                            }finally {
                                //Notify adapter about data changes
                                adapter.notifyItemChanged(i);
                            }
                        }
                    } catch (Exception e) {
                        Log.e(TAG,"2 = " + e.getMessage());
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(String message) {
                loadDataCountries();
                Log.e(TAG,"Ada ERROR : "+message);
            }
        });
    }

    private void initializeKenBurnsView(){
        // KenBurnsView
        final KenBurnsEffect kenBurnsView = findViewById(R.id.ken_burns_view);
         kenBurnsView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        // File path, or a uri or url
        List<Integer> urls = Arrays.asList(IMAGE_RESOUCE);
        kenBurnsView.initResourceIDs(urls);

        // ResourceID
        //List<Integer> resourceIDs = Arrays.asList(SampleImages.IMAGES_RESOURCE);
        //kenBurnsView.loadResourceIDs(resourceIDs);

        // MIX (url & id)
        //List<Object> mixingList = Arrays.asList(SampleImages.IMAGES_MIX);
        //kenBurnsView.loadMixing(mixingList);

        // LoopViewListener
        LoopViewPager.LoopViewPagerListener listener = new LoopViewPager.LoopViewPagerListener() {
            @Override
            public View OnInstantiateItem(int page) {
                TextView counterText = new TextView(getApplicationContext());
                counterText.setText("");
                counterText.setVisibility(View.GONE);
                return counterText;
            }

            @Override
            public void onPageScroll(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                kenBurnsView.forceSelected(position);
            }

            @Override
            public void onPageScrollChanged(int page) {
            }
        };

        // LoopView
        LoopViewPager loopViewPager = new LoopViewPager(this, urls.size(), listener);

        //LoopViewPager loopViewPager = new LoopViewPager(this, resourceIDs.size(), listener);

        //LoopViewPager loopViewPager = new LoopViewPager(this, mixingList.size(), listener);


        FrameLayout viewPagerFrame = (FrameLayout) findViewById(R.id.view_pager_frame);
        viewPagerFrame.addView(loopViewPager);

        kenBurnsView.setPager(loopViewPager);
    }

}
