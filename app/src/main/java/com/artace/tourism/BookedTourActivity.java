package com.artace.tourism;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.artace.tourism.adapter.BookedTourAdapter;
import com.artace.tourism.adapter.CountriesAdapter;
import com.artace.tourism.adapter.TourAdapter;
import com.artace.tourism.connection.DatabaseConnection;
import com.artace.tourism.constant.Field;
import com.artace.tourism.model.ModelCountry;
import com.artace.tourism.model.ModelTransaction;
import com.artace.tourism.utils.StringPostRequest;
import com.artace.tourism.utils.VolleyResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookedTourActivity extends AppCompatActivity {

    //RecyclerView and Network
    RecyclerView recyclerView;
    BookedTourAdapter adapter;
    List<ModelTransaction> dataList = new ArrayList<ModelTransaction>();
    RequestQueue queue;
    SharedPreferences sharedpreferences;
    String url;
    
    final static String TAG = "BookedTour";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_tour);

        sharedpreferences = getSharedPreferences(Field.getLoginSharedPreferences(), Context.MODE_PRIVATE);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Booked Tour");
        ab.setDisplayHomeAsUpEnabled(true);

        initRecyclerView();

    }

    private void initRecyclerView(){

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new BookedTourAdapter(this, dataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);

        loadData();

    }

    public void loadData(){

        url = DatabaseConnection.getBookedTour();

        Map<String,String> params = new HashMap<String, String>();
        params.put("guest_id",sharedpreferences.getString("id",""));
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

                                ModelTransaction data = new ModelTransaction();
                                data.setTour_id(obj.getInt("tour_id"));
                                data.setName(obj.getString("name"));
                                data.setImage(obj.getString("image"));
                                data.setShort_description(obj.getString("short_description"));
                                data.setPrice(obj.getInt("price"));
                                data.setTour_start_date(obj.getString("tour_start_date"));
                                data.setLocation(obj.getString("location"));
                                data.setConfirmation(obj.getInt("confirmation_status"));
                                dataList.add(data);

                            } catch (Exception e) {
                                Log.e(TAG,e.getMessage());
                            }finally {
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
                Log.e(TAG,"Ada ERROR : "+message);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(BookedTourActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
