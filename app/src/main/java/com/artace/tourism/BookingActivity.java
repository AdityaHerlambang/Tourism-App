package com.artace.tourism;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.Request;
import com.artace.tourism.connection.DatabaseConnection;
import com.artace.tourism.constant.Field;
import com.artace.tourism.databinding.ActivityBookingBinding;
import com.artace.tourism.utils.StringPostRequest;
import com.artace.tourism.utils.VolleyResponseListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BookingActivity extends AppCompatActivity {

    ActivityBookingBinding binding;

    SharedPreferences sharedpreferences;
    Bundle extras;

    final static String TAG = "BookingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_booking);

        setComponentView();

        sharedpreferences = getSharedPreferences(Field.getLoginSharedPreferences(), Context.MODE_PRIVATE);

        extras = getIntent().getExtras();

        loadTour();

    }

    private void setComponentView(){
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Tour Booking");
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void loadTour(){
        String url = DatabaseConnection.getTourDetail();

        Map<String,String> params = new HashMap<String, String>();
        params.put("id",extras.getString("tour_id"));
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

                                binding.tourName.setText(obj.getString("name"));
                                binding.tourShortdesc.setText(obj.getString("short_description"));
                                binding.email.setText(sharedpreferences.getString("email",""));

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(BookingActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
