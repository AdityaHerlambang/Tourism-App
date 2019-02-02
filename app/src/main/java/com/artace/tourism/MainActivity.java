package com.artace.tourism;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
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
import com.artace.tourism.adapter.TourAdapter;
import com.artace.tourism.connection.DatabaseConnection;
import com.artace.tourism.constant.Field;
import com.artace.tourism.databinding.ActivityMainBinding;
import com.artace.tourism.model.ModelCountry;
import com.artace.tourism.model.ModelTour;
import com.artace.tourism.utils.DrawerMenu;
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

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    Integer[] IMAGE_RESOUCE;

    //RecyclerView and Network
    RecyclerView countriesRecyclerView,toursRecyclerView;
    CountriesAdapter adapter;
    TourAdapter adapterTour;
    List<ModelCountry> dataList = new ArrayList<ModelCountry>();
    List<ModelTour> dataListTours = new ArrayList<ModelTour>();
    RequestQueue queue;
    SharedPreferences sharedpreferences;
    String url, searchString = "";

    Boolean session = false;

    //TAG
    final String TAG = "RealisasiTahunan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        setComponentView();

        IMAGE_RESOUCE = new Integer[]{
                R.drawable.dashboard_header1,
                R.drawable.dashboard_header2,
                R.drawable.dashboard_header3,
        };
        initializeKenBurnsView();
        setCountriesRecyclerView();
        setTourRecyclerView();

        if(getIntent().getExtras() != null){
            Bundle extras = getIntent().getExtras();
            if(extras.getString("from") != null){
                if(extras.getString("from").equals("Register")){
                    SweetAlertDialog sDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                    sDialog.setTitle("Success !");
                    sDialog.setContentText("You are now registered and logged in");
                    sDialog.show();
                }
                if(extras.getString("from").equals("Booking")){
                    SweetAlertDialog sDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                    sDialog.setTitle("Success !");
                    sDialog.setContentText("You just booked a Tour. Please wait until the tour provider approve your booking.");
                    sDialog.show();
                }
            }
        }

    }



    private void setComponentView(){

        this.setSupportActionBar(binding.toolbar);
        ActionBar ab = this.getSupportActionBar();
        ab.setTitle("");
//
        DrawerMenu drawer = new DrawerMenu();
        drawer.createDrawer(this, this, binding.toolbar);

        binding.agriculture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,TourActivity.class);
                Bundle extras = new Bundle();
                extras.putString("tipe","1");
                extras.putString("id","1");
                extras.putString("name","Agriculture");
                extras.putString("image","https://www.plasticseurope.org/application/files/6615/1274/2893/5.6._aaheader.png");
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        binding.dance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,TourActivity.class);
                Bundle extras = new Bundle();
                extras.putString("tipe","1");
                extras.putString("id","2");
                extras.putString("name","Dance");
                extras.putString("image","https://www.balispirit.com/assets/images/traditional-dance-in-bali-balispirit750x400.jpg");
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        binding.cook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,TourActivity.class);
                Bundle extras = new Bundle();
                extras.putString("tipe","1");
                extras.putString("id","3");
                extras.putString("name","Cook");
                extras.putString("image","https://media.juiceonline.com/wp-content/uploads/2018/07/10123656/mistakes-cooking-edibles-1280x800.jpg");
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        binding.handycraft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,TourActivity.class);
                Bundle extras = new Bundle();
                extras.putString("tipe","1");
                extras.putString("id","4");
                extras.putString("name","Handycraft");
                extras.putString("image","https://cdn.pixabay.com/photo/2016/02/17/00/48/handycraft-1204317_960_720.jpg");
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        binding.itdesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,TourActivity.class);
                Bundle extras = new Bundle();
                extras.putString("tipe","1");
                extras.putString("id","5");
                extras.putString("name","It / Design");
                extras.putString("image","https://www.escholar.com/wp-content/uploads/2017/12/technology-785742_960_720.jpg");
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        binding.herds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,TourActivity.class);
                Bundle extras = new Bundle();
                extras.putString("tipe","1");
                extras.putString("id","6");
                extras.putString("name","Herds");
                extras.putString("image","https://upload.wikimedia.org/wikipedia/commons/6/67/Sheep_and_herder_India.jpg");
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        binding.community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,TourActivity.class);
                Bundle extras = new Bundle();
                extras.putString("tipe","1");
                extras.putString("id","7");
                extras.putString("name","Community Design");
                extras.putString("image","https://huminst.osu.edu/sites/huminst.osu.edu/files/Community%20arts.jpg");
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        binding.others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,TourActivity.class);
                Bundle extras = new Bundle();
                extras.putString("tipe","1");
                extras.putString("id","8");
                extras.putString("name","Others");
                extras.putString("image","https://apairandasparediy.com/wp-content/uploads/2015/09/21075568479_d2a7427bf2_b-778x542.jpg");
                intent.putExtras(extras);
                startActivity(intent);
            }
        });


        // Cek session login jika TRUE maka langsung buka MainActivity
        sharedpreferences = getSharedPreferences(Field.getLoginSharedPreferences(), Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(Field.getSessionStatus(),false);

        if (session) {
            if(sharedpreferences.getString("role_id","").equals("1")){
                binding.cardIntroTitle.setText("See Your Booked Tour");
                binding.mainLearnMore.setText("Booked Tour");
                binding.mainLearnMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this,BookedTourActivity.class);
                        startActivity(intent);
                    }
                });
            }
            else if (sharedpreferences.getString("role_id","").equals("2")){
                Intent intent = new Intent(MainActivity.this, ProviderActivity.class);
                startActivity(intent);
            }
            else{
                binding.cardIntro.setVisibility(View.GONE);
            }
        }
        else{
            binding.cardIntro.setVisibility(View.VISIBLE);
            binding.mainLearnMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    Bundle extras = new Bundle();
                    extras.putString("from","MainActivity");
                    intent.putExtras(extras);
                    startActivity(intent);
                }
            });
        }
    }

    private void setCountriesRecyclerView(){

        countriesRecyclerView = (RecyclerView) findViewById(R.id.main_countries_recyclerview);
        adapter = new CountriesAdapter(this, dataList);
        countriesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        countriesRecyclerView.setAdapter(adapter);

        loadDataCountries();
    }

    private void setTourRecyclerView(){

        toursRecyclerView = (RecyclerView) findViewById(R.id.main_tours_recyclerview);
        adapterTour = new TourAdapter(this, dataListTours);
        toursRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        toursRecyclerView.setAdapter(adapterTour);
        toursRecyclerView.setNestedScrollingEnabled(false);

        loadDataTour();

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

    public void loadDataTour(){

        url = DatabaseConnection.getAllTour();

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
                                dataListTours.add(modelTour);

                            } catch (Exception e) {
                                Log.e(TAG,e.getMessage());
                            }finally {
                                adapterTour.notifyItemChanged(i);
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
