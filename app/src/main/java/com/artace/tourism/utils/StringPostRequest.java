package com.artace.tourism.utils;


import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class StringPostRequest {

    final static String TAG = "Parameters";

    public StringPostRequest() {
    }

    public void sendPost(Context context, Map<String,String> params, String url, final VolleyResponseListener listener){

        final Map<String,String> paramsFinal = params;
        final Context contextFinal = context;

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("POSTREQUEST",response);
                listener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("POSTREQUEST",error.toString());
                listener.onError(error.getStackTrace().toString());
            }
        }){
            @Override
            protected Map<String,String> getParams(){

                return paramsFinal;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(sr);
    }

    public void sendRequest(int method, Context context, Map<String,String> params, String url, final VolleyResponseListener listener){

        final Map<String,String> paramsFinal = params;
        Log.d(TAG,paramsFinal.toString());
        final Context contextFinal = context;

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("REQUEST",response);
                listener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("REQUEST",error.toString());
                listener.onError(error.getMessage()+" "+error.getLocalizedMessage());
            }
        }){
            @Override
            protected Map<String,String> getParams(){

                return paramsFinal;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                300000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(sr);
    }

    public void sendPostWithoutLoading(Context context, Map<String,String> params, String url, final VolleyResponseListener listener){

        final Map<String,String> paramsFinal = params;

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("POSTREQUEST",response);
                listener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("POSTREQUEST",error.toString());
                listener.onError(error.getMessage()+" "+error.getLocalizedMessage());
            }
        }){
            @Override
            protected Map<String,String> getParams(){

                return paramsFinal;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                300000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(sr);
    }
}