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
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.abdeveloper.library.MultiSelectDialog;
import com.abdeveloper.library.MultiSelectModel;
import com.android.volley.Request;
import com.artace.tourism.connection.DatabaseConnection;
import com.artace.tourism.constant.Field;
import com.artace.tourism.databinding.ActivityRegisterProviderBinding;
import com.artace.tourism.databinding.ActivityRegisterTrevelerBinding;
import com.artace.tourism.utils.StringPostRequest;
import com.artace.tourism.utils.VolleyResponseListener;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class RegisterProviderActivity extends AppCompatActivity implements Validator.ValidationListener {

    ActivityRegisterProviderBinding binding;

    final static String TAG = "RegisterProvider";

    /* FORM COMPONENTS */
    Validator validator;
    final static String notEmptyMsg = "This field can not be empty";

    @NotEmpty(message = notEmptyMsg, trim = true)
    @Pattern(regex = "^(?=.{4,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$", message = "Username can't contain space, at least 4 character and 20 character max")
    private EditText mUsername;

    @Order(1)
    @NotEmpty(message = notEmptyMsg, trim = true)
    @Password(min = 2, message="Password require at least 2 characters")
    private EditText mPassword;

    @ConfirmPassword(message = "Password doesn't match")
    private EditText mConfirm;

    @NotEmpty(message = notEmptyMsg, trim = true)
    private EditText mCountry;

    @NotEmpty(message = notEmptyMsg, trim = true)
    private EditText mName;

    @NotEmpty(message = notEmptyMsg, trim = true)
    private EditText mPhone;

    @NotEmpty(message = notEmptyMsg, trim = true)
    @Email
    private EditText mEmail;

    ArrayList<MultiSelectModel> listCountry = new ArrayList<>();

    MultiSelectDialog multiSelectDialog;

    String country_id;
    SweetAlertDialog sDialog;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_register_provider);

        sharedpreferences = getSharedPreferences(Field.getLoginSharedPreferences(), Context.MODE_PRIVATE);

        validator = new Validator(this);
        validator.setValidationListener(this);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Register Provider");
        ab.setDisplayHomeAsUpEnabled(true);

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
            }
        });

        loadCountries();

        setComponentView();

    }

    private void loadCountries(){
        Map<String,String> params = new HashMap<String, String>();
        params.put("empty","empty");
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
                            JSONObject obj = jsonArray.getJSONObject(i);
                            listCountry.add(new MultiSelectModel(obj.getInt("id"),obj.getString("name")));
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
                Log.e(TAG,"Ada ERROR : "+message);
            }
        });
    }

    private void chooseCountry(){
        multiSelectDialog = new MultiSelectDialog()
                .title("Choose Country")
                .titleSize(25)
                .positiveText("Selesai")
                .negativeText("Batal")
                .setMinSelectionLimit(1)
                .setMaxSelectionLimit(1)
                .multiSelectList(listCountry)
                .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, String dataString) {
                        //will return list of selected IDS
                        for (int i = 0; i < selectedIds.size(); i++) {
                            //selectedIds.get(i), selectedNames.get(i), dataString

                            Log.d(TAG,"id = "+selectedIds.get(i)+" name = "+selectedNames.get(i)+" dataString = "+dataString);
                            binding.country.setText(selectedNames.get(i));
                            country_id =  String.valueOf(selectedIds.get(i));

                            hideSoftKeyboard();

                        }


                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG,"Dialog cancelled");
                    }
                });
        multiSelectDialog.show(getSupportFragmentManager(), "multiSelectDialog");
    }

    private void setComponentView(){
        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mConfirm = findViewById(R.id.confirm_password);
        mCountry = findViewById(R.id.country);
        mName = findViewById(R.id.name);
        mPhone = findViewById(R.id.phone);
        mEmail = findViewById(R.id.email);

        binding.country.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    chooseCountry();
                }
            }
        });
        binding.country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseCountry();
            }
        });
    }

    private void submitForm(){

        sDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        sDialog.setTitle("Processing...");
        sDialog.show();

        Map<String,String> params = new HashMap<String, String>();
        params.put("username",binding.username.getText().toString());
        params.put("password",binding.password.getText().toString());
        params.put("country_id",country_id);
        params.put("name",binding.name.getText().toString());
        params.put("phone",binding.phone.getText().toString());
        params.put("email",binding.email.getText().toString());
        Log.d(TAG,params.toString());

        String url = DatabaseConnection.getRegisterProvider();
        Log.d(TAG,url);

        StringPostRequest strReq = new StringPostRequest();
        strReq.sendRequest(Request.Method.POST,this, params, url, new VolleyResponseListener() {
            @Override
            public void onResponse(String response) {
                if(getIntent().getExtras() != null){
                    Bundle extras = getIntent().getExtras();
                    if(extras.getString("from").equals("Booking")){
                        Bundle extrasBooking = new Bundle();
                        extrasBooking.putString("tour_id",extras.getString("tour_id"));
                        Intent intent = new Intent(RegisterProviderActivity.this, BookingActivity.class);
                        intent.putExtras(extrasBooking);
                        startActivity(intent);
                    }
                }
                else{
                    Intent intent = new Intent(RegisterProviderActivity.this, MainActivity.class);
                    Bundle extras = new Bundle();
                    extras.putString("from","Register");
                    intent.putExtras(extras);
                    startActivity(intent);
                }

                login();


            }

            @Override
            public void onError(String message) {
                Log.e(TAG,"Ada ERROR : "+message);
            }
        });
    }

    private void login(){
        Map<String,String> params = new HashMap<String, String>();
        params.put("username",binding.password.getText().toString());
        params.put("password",binding.password.getText().toString());
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

                    editor.putString("id", obj.getString("user_id"));
                    editor.putString("user_id", obj.getString("user_id"));
                    editor.putString("country_id", obj.getString("country_id"));
                    editor.putString("name", obj.getString("name"));
                    editor.putString("phone", obj.getString("phone"));
                    editor.putString("email", obj.getString("email"));
                    editor.commit();

                    Intent intent = new Intent(RegisterProviderActivity.this,ProviderActivity.class);
                    Bundle extras = new Bundle();
                    extras.putString("from","Register");
                    intent.putExtras(extras);
                    startActivity(intent);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    /* FORM VALIDATION */

    @Override
    public void onValidationSucceeded() {
//        Toast.makeText(this, "SUKSES SUBMIT FORM", Toast.LENGTH_LONG).show();
        submitForm();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
