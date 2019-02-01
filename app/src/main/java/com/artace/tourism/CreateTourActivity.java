package com.artace.tourism;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.abdeveloper.library.MultiSelectDialog;
import com.abdeveloper.library.MultiSelectModel;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.artace.tourism.connection.DatabaseConnection;
import com.artace.tourism.constant.Field;
import com.artace.tourism.databinding.ActivityCreateTourBinding;
import com.artace.tourism.utils.StringPostRequest;
import com.artace.tourism.utils.VolleyMultipartRequest;
import com.artace.tourism.utils.VolleyResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CreateTourActivity extends AppCompatActivity {

    ActivityCreateTourBinding binding;
    String TAG = "CreateTour";

    ArrayList<MultiSelectModel> listCountry = new ArrayList<>();

    MultiSelectDialog multiSelectDialog;

    Bitmap bitmapFoto;

    String country_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_create_tour);

        loadCountries();

        setComponentView();
    }

    private void setComponentView(){

        binding.createTourBtnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
            }
        });

        binding.createTourBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm(bitmapFoto);
            }
        });

        binding.createTourCountry.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    chooseCountry();
                }
            }
        });
        binding.createTourCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseCountry();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            //getting the image Uri
            Uri imageUri = data.getData();
            try {
                //getting bitmap object from uri
                bitmapFoto = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                //displaying selected image to imageview
                binding.createTourViewFoto.setImageBitmap(bitmapFoto);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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
                            binding.createTourCountry.setText(selectedNames.get(i));
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

    private void submitForm(final Bitmap bitmap){

        SharedPreferences sharedpreferences = getSharedPreferences(Field.getLoginSharedPreferences(), Context.MODE_PRIVATE);
        final String idProvider = sharedpreferences.getString("provider_id",null);

        final SweetAlertDialog sDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        sDialog.setTitle("Processing...");
        sDialog.show();

        String url = DatabaseConnection.getCreateTour();
        Log.d(TAG,url);

        //Multipart Volley Request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Log.e("inserttour",obj.getString("message"));
                            sDialog.dismissWithAnimation();
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        finally{
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * PARMETER POST
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("country_id",country_id);
                params.put("tour_provider_id",idProvider);
                params.put("name",binding.createTourName.getText().toString());
                params.put("location",binding.createTourLocation.getText().toString());
                params.put("short_description",binding.createTourShortDescription.getText().toString());
                params.put("overview",binding.createTourOverview.getText().toString());
                params.put("activity",binding.createTourActivity.getText().toString());
                params.put("minimum_person",binding.createTourMinimumPerson.getText().toString());
                params.put("preperation",binding.createTourPreperation.getText().toString());
                params.put("location_latitude",null);
                params.put("location_longitude",null);
                params.put("nearest_airport",binding.createTourNearestAirport.getText().toString());
                params.put("verified_by_admin","1");
                params.put("adult_price",binding.createTourAdultPrice.getText().toString());
                params.put("child_price",binding.createTourChildPrice.getText().toString());
                params.put("max_capacity",binding.createTourMaxCapacity.getText().toString());
                params.put("duration_hour",binding.createTourDurationHour.getText().toString());
                params.put("duration_day",binding.createTourDurationDay.getText().toString());
                params.put("status_popularity","0");
                params.put("status","1");
                Log.e(TAG, params.toString());
                return params;
            }

            /*
             * passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("image", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };
        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

    private void hideSoftKeyboard(){
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
