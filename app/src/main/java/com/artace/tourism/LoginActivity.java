package com.artace.tourism;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.android.volley.Request;
import com.artace.tourism.connection.DatabaseConnection;
import com.artace.tourism.constant.Field;
import com.artace.tourism.databinding.ActivityLoginBinding;
import com.artace.tourism.model.ModelCountry;
import com.artace.tourism.utils.StringPostRequest;
import com.artace.tourism.utils.VolleyResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    SharedPreferences sharedpreferences;
    Boolean session = false;

    Bundle extras;

    static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

// Cek session login jika TRUE maka langsung buka MainActivity
        sharedpreferences = getSharedPreferences(Field.getLoginSharedPreferences(), Context.MODE_PRIVATE);

        // Cek session login jika TRUE maka langsung buka MainActivity
        sharedpreferences = getSharedPreferences(Field.getLoginSharedPreferences(), Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(Field.getSessionStatus(),false);

        extras = getIntent().getExtras();

        if (session) {
            if(extras.getString("from").equals("MainActivity")){
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
            if(extras.getString("from").equals("Booking")){
                //TODO : Intent ke form booking

            }
        }
        else{
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.apply();
        }
        hideSoftKeyboard();

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitLogin();
            }
        });

        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(extras.getString("from").equals("Booking")){
                    Intent intent = new Intent(LoginActivity.this, RegisterTrevelerActivity.class);
                    Bundle extras = new Bundle();
                    extras.putString("from","Booking");
                    intent.putExtras(extras);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(LoginActivity.this, ChooseRegisterActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    private void submitLogin(){
        Map<String,String> params = new HashMap<String, String>();
        params.put("username",binding.loginEmail.getText().toString());
        params.put("password",binding.loginPassword.getText().toString());
        Log.d(TAG,params.toString());

        final SweetAlertDialog sDialog = new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
        sDialog.setTitle("Logging in");
        sDialog.show();

        String url = DatabaseConnection.getLOGIN();
        Log.d(TAG,url);

        StringPostRequest strReq = new StringPostRequest();
        strReq.sendRequest(Request.Method.POST,this, params, url, new VolleyResponseListener() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);

                    sDialog.dismissWithAnimation();

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putBoolean(Field.getSessionStatus(), true);
                    editor.putString("role_id", obj.getString("role_id"));

                    if(Integer.valueOf(obj.getString("role_id")) == 1){ //Jika Guest
                        editor.putString("id", obj.getString("id"));
                        editor.putString("user_id", obj.getString("user_id"));
                        editor.putString("country_id", obj.getString("country_id"));
                        editor.putString("firstname", obj.getString("firstname"));
                        editor.putString("lastname", obj.getString("lastname"));
                        editor.putString("phone", obj.getString("phone"));
                        editor.putString("email", obj.getString("email"));
                        if(extras.getString("from").equals("MainActivity")){ //Jika intent dari Main Activity
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        if(extras.getString("from").equals("Booking")){
                            //TODO : Intent ke form booking
                        }
                    }else{ //Jika Tour Provider
                        editor.putString("user_id", obj.getString("user_id"));
                        editor.putString("country_id", obj.getString("country_id"));
                        editor.putString("name", obj.getString("name"));
                        editor.putString("phone", obj.getString("phone"));
                        editor.putString("email", obj.getString("email"));
                        Intent intent = new Intent(LoginActivity.this,ProviderActivity.class);
                        startActivity(intent);
                    }

                    editor.commit();

                } catch (Exception e) {
                    Log.e(TAG,e.getMessage());
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
