package com.artace.tourism;

import android.app.DatePickerDialog;
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
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;

import com.android.volley.Request;
import com.artace.tourism.connection.DatabaseConnection;
import com.artace.tourism.constant.Field;
import com.artace.tourism.databinding.ActivityBookingBinding;
import com.artace.tourism.model.ModelCountry;
import com.artace.tourism.utils.FormatTanggal;
import com.artace.tourism.utils.StringPostRequest;
import com.artace.tourism.utils.VolleyResponseListener;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BookingActivity extends AppCompatActivity {

    ActivityBookingBinding binding;

    SharedPreferences sharedpreferences;
    Bundle extras;

    String tour_start_date;
    double price = 0, adultPrice = 0, childPrice = 0, adultPriceTotal = 0, childPriceTotal = 0;
    int adultQty, childQty;

    final static String TAG = "BookingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_booking);

        setComponentView();
        sharedpreferences = getSharedPreferences(Field.getLoginSharedPreferences(), Context.MODE_PRIVATE);
        extras = getIntent().getExtras();

        loadTour();

        binding.btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SweetAlertDialog sDialog = new SweetAlertDialog(BookingActivity.this,SweetAlertDialog.WARNING_TYPE);
                sDialog.setTitle("Book this Tour ?");
                sDialog.setContentText("This tour will be added to your booked tour list");
                sDialog.setConfirmButton("Yes", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        submitForm();
                        sweetAlertDialog.dismissWithAnimation();
                    }
                });
                sDialog.setCancelButton("No", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                });
                sDialog.show();
            }
        });

    }

    private void submitForm(){
        Map<String,String> params = new HashMap<String, String>();
        params.put("tour_id",extras.getString("tour_id"));
        params.put("guest_id",sharedpreferences.getString("id",""));
        params.put("adult_qty",String.valueOf(adultQty));
        params.put("child_qty",String.valueOf(childQty));
        params.put("tour_start_date",tour_start_date);
        params.put("firstname",binding.firstname.getText().toString());
        params.put("lastname",binding.lastname.getText().toString());
        params.put("phone_number",binding.phone.getText().toString());
        params.put("price",String.valueOf(price));
        Log.d(TAG,params.toString());

        String url = DatabaseConnection.getMakeBooking();
        Log.d(TAG,url);

        StringPostRequest strReq = new StringPostRequest();
        strReq.sendRequest(Request.Method.POST,this, params, url, new VolleyResponseListener() {
            @Override
            public void onResponse(String response) {
                Intent intent = new Intent(BookingActivity.this,MainActivity.class);
                Bundle extras = new Bundle();
                extras.putString("from","Booking");
                intent.putExtras(extras);
                startActivity(intent);
            }

            @Override
            public void onError(String message) {
                Log.e(TAG,"Ada ERROR : "+message);
            }
        });
    }


    private void setComponentView(){
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Tour Booking");
        ab.setDisplayHomeAsUpEnabled(true);

        binding.price.setText("US$");

        binding.tourStartDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(view.hasFocus()){
                    pickDate(binding.tourStartDate);
                }
            }
        });
        binding.tourStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDate(binding.tourStartDate);
            }
        });

        binding.adultQtyBtn.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                price = 0;
                adultQty = newValue;
                price = (adultPrice*adultQty)+(childPrice*childQty);
                adultPriceTotal = (adultPrice*adultQty);
                binding.price.setText("US$"+String.valueOf(price));
            }
        });

        binding.childQtyBtn.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                price = 0;
                childQty = newValue;
                price = (adultPrice*adultQty)+(childPrice*childQty);
                childPriceTotal = (childPrice*childQty);
                binding.price.setText("US$"+String.valueOf(price));
            }
        });

    }

    private void pickDate(final EditText mComponent){

        //Khusus Pick Date TANGGAL BELANJA

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        mComponent.setText(FormatTanggal.english(dayOfMonth,(monthOfYear + 1),year));
                        //Format Tanggal Belanja

                        tour_start_date = year+"-"+(monthOfYear+1)+"-"+dayOfMonth;

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker();
        datePickerDialog.show();
        hideSoftKeyboard();
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

                                adultPrice = obj.getDouble("adult_price");
                                childPrice = obj.getDouble("child_price");



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

    private void hideSoftKeyboard(){
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
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
