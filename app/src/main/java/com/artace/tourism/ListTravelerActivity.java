package com.artace.tourism;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.artace.tourism.adapter.TravelerAdapter;
import com.artace.tourism.connection.DatabaseConnection;
import com.artace.tourism.databinding.ActivityListTravelerBinding;
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

public class ListTravelerActivity extends AppCompatActivity {

    ActivityListTravelerBinding binding;
    TravelerAdapter travelerAdapter;
    private List<ModelTransaction> modelTransactionList = new ArrayList<ModelTransaction>();
    String TAG = "ListTraveler";
    String id, url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_list_traveler);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        binding.listTravelerToolbar.setTitle("List Traveler");
        setSupportActionBar(binding.listTravelerToolbar);
        binding.listTravelerToolbar.setTitleTextColor(getColor(R.color.primary_dark));

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
            upArrow.setColorFilter(Color.argb(255,0,0,0), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }

        setRecyclerTraveler();
    }


    public void setRecyclerTraveler(){

        travelerAdapter = new TravelerAdapter(this, modelTransactionList);
        binding.listTravelerRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.listTravelerRecyclerView.setAdapter(travelerAdapter);

        loadDataTraveler();
    }

    public void loadDataTraveler(){


        SharedPreferences sharedpreferences = getSharedPreferences("True", MODE_PRIVATE);
        String idProvider = sharedpreferences.getString("id",null);
        url = DatabaseConnection.getTrevelerProvider("1");

        Map<String,String> params = new HashMap<String, String>();
        params.put("emptyvalue","emptyvalue");
        Log.d(TAG,params.toString());
        Log.d(TAG,url);

        StringPostRequest strReq = new StringPostRequest();
        strReq.sendRequest(Request.Method.GET,this, params, url, new VolleyResponseListener() {
            @Override
            public void onResponse(String response) {
                modelTransactionList.clear();
                try{
                    JSONArray jsonArray = new JSONArray(response);
                    try {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject obj = jsonArray.getJSONObject(i);

                                ModelTransaction data = new ModelTransaction(obj.getInt("id"), obj.getInt("tour_id"),
                                        obj.getInt("guest_id"), obj.getInt("adult_qty"), obj.getInt("child_qty"), obj.getInt("confirmation_status")
                                        , obj.getInt("country_id"), obj.getString("tour_name"), obj.getString("denied_reason"), obj.getString("tour_start_date") ,
                                        obj.getString("firstname") , obj.getString("lastname") , obj.getString("country_name"), obj.getString("phone_number"));
                                modelTransactionList.add(data);

                            } catch (Exception e) {
                                Log.e(TAG,e.getMessage());
                            }finally {
                                travelerAdapter.notifyItemChanged(i);
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
    public boolean onSupportNavigateUp() {
        finish();
        onBackPressed();
        return true;
    }

}
