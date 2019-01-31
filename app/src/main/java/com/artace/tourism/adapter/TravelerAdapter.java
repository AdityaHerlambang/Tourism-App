package com.artace.tourism.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import com.android.volley.Request;
import com.artace.tourism.R;
import com.artace.tourism.TourDetailActivity;
import com.artace.tourism.connection.DatabaseConnection;
import com.artace.tourism.model.ModelTour;
import com.artace.tourism.model.ModelTransaction;
import com.artace.tourism.utils.StringPostRequest;
import com.artace.tourism.utils.VolleyResponseListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TravelerAdapter extends RecyclerView.Adapter<TravelerAdapter.MyViewHolder> {

    private List<ModelTransaction> dataList;
    private Context context;
    private LayoutInflater inflater;
    ModelTransaction data;
    final String TAG = "TravelerAdapter";

    public TravelerAdapter(Context context, List<ModelTransaction> dataList) {
        this.context = context;
        this.dataList = dataList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.item_provider_confirm, parent, false);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        data = dataList.get(position);

        holder.mName.setText(data.getFirstname()+" "+data.getLastname());
        holder.mStartDate.setText(data.getTour_start_date());
        holder.mAdultQty.setText(Integer.toString(data.getAdult_qty()));
        holder.mChildQty.setText(Integer.toString(data.getChild_qty()));
        holder.mTour.setText(data.getTour_name());

        holder.mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,String> params = new HashMap<String, String>();
                params.put("id",Integer.toString(data.getId()));
                params.put("status","Confirmation");
                String url = DatabaseConnection.getConfirmationTraveler();

                Log.d(TAG,params.toString());
                Log.d(TAG,url);

                loadResponseData(params, url);
            }
        });

        holder.mBtnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,String> params = new HashMap<String, String>();
                params.put("id",Integer.toString(data.getId()));
                params.put("status","Rejected");
                String url = DatabaseConnection.getConfirmationTraveler();

                Log.d(TAG,params.toString());
                Log.d(TAG,url);

                loadResponseData(params, url);
            }
        });
    }

    public void loadResponseData(Map<String,String> params, String url){
        StringPostRequest strReq = new StringPostRequest();
        strReq.sendRequest(Request.Method.POST,context, params, url, new VolleyResponseListener() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONArray jsonArray = new JSONArray(response);
                    try {

                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                Log.e(TAG, obj.toString());
                                if (obj.getString("status").equals("1")){
                                    Log.e(TAG, "sucess");
                                }
                                else{
                                    //error on query
                                }

                            } catch (Exception e) {
                                Log.e(TAG,e.getMessage());
                            }finally {
                                //Notify adapter about data changes
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
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mName, mTour, mAdultQty, mChildQty, mStartDate;
        private Button mBtnConfirm, mBtnReject;

        public MyViewHolder(View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.provider_confirm_NamaTreveler);
            mTour = itemView.findViewById(R.id.provider_confirm_tour);
            mAdultQty = itemView.findViewById(R.id.provider_confirm_adultQty);
            mChildQty = itemView.findViewById(R.id.provider_confirm_childQty);
            mStartDate = itemView.findViewById(R.id.provider_confirm_tourStart);

            mBtnConfirm = itemView.findViewById(R.id.provider_confirm_btnConfirmation);
            mBtnReject = itemView.findViewById(R.id.provider_confirm_btnRejected);
        }
    }
}
