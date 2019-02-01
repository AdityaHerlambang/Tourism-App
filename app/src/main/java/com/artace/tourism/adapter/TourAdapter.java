package com.artace.tourism.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import android.widget.TextView;

import com.android.volley.Request;
import com.artace.tourism.R;
import com.artace.tourism.TourDetailActivity;
import com.artace.tourism.connection.DatabaseConnection;
import com.artace.tourism.model.ModelTour;
import com.artace.tourism.utils.StringPostRequest;
import com.artace.tourism.utils.VolleyResponseListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.MyViewHolder> {

    private List<ModelTour> dataList;
    private Context context;
    private LayoutInflater inflater;
    ModelTour data;
    final String TAG = "TourAdapter";

    public TourAdapter(Context context, List<ModelTour> dataList) {
        this.context = context;
        this.dataList = dataList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.item_tour, parent, false);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        data = dataList.get(position);

        holder.mNama.setText(data.getName());
        holder.mLokasi.setText(data.getLocation());
        holder.mShortDesc.setText(data.getShort_description());
        holder.harga.setText(Double.toString(data.getAdult_price()));
        setDurationView(holder);
        holder.durasiDays.setText(Integer.toString(data.getDuration_day())+" Days");
        holder.durasiHour.setText(Integer.toString(data.getDuration_hour())+" Hour");

        Picasso.with(context)
                .load(data.getImage())
                .placeholder(R.drawable.placeholder_vertical)
                .into(holder.imageTour);
        if (data.getCustomStatus() != null){
            if (data.getCustomStatus().equals("fragment")){
                holder.mRemove.setTag(position);
                removeTour(holder, position);
            }
        }
        else{
            holder.mRemove.setVisibility(View.GONE);
        }


        holder.mCard.setTag(position);
        holder.mCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data = dataList.get((int) view.getTag());
                Intent intent = new Intent(context, TourDetailActivity.class);
                Bundle extras = new Bundle();
                extras.putString("id",data.getId());
                extras.putString("name",data.getName());
                extras.putString("image",data.getImage());
                if (data.getCustomStatus() != null){
                    extras.putString("status", data.getCustomStatus());
                }
                intent.putExtras(extras);
                context.startActivity(intent);
            }
        });
    }

    private void removeTour(MyViewHolder holder, final int position){
        holder.mRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (Integer) view.getTag();
                data = dataList.get(tag);
                final String url = DatabaseConnection.getDeleteTour(data.getId());

                new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("you can't undo this action")
                        .setCancelText("No,cancel!")
                        .setConfirmText("Yes, delete!")
                        .showCancelButton(true)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                loadResponseData(url);
                                dataList.remove(position);
                                notifyItemRemoved(position);

                                sDialog.dismissWithAnimation();
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });
    }

    private void setDurationView(MyViewHolder holder){
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(holder.mParentLayout);

        if (data.getDuration_hour() < 1 && data.getDuration_day() > 0){
            constraintSet.clear(R.id.item_tour_durationDays,ConstraintSet.END);
            constraintSet.connect(R.id.item_tour_durationDays,ConstraintSet.END,R.id.item_tour_txtDuration,ConstraintSet.END,100);
            constraintSet.applyTo(holder.mParentLayout);
            holder.durasiHour.setVisibility(View.GONE);
        }
        if (data.getDuration_day() < 1 && data.getDuration_hour() > 0){
            constraintSet.clear(R.id.item_tour_durationHour,ConstraintSet.RIGHT);
            constraintSet.connect(R.id.item_tour_durationHour,ConstraintSet.RIGHT,R.id.item_tour_constraintLayout,ConstraintSet.RIGHT,32);
            constraintSet.applyTo(holder.mParentLayout);
            holder.durasiDays.setVisibility(View.GONE);
        }
    }

    public void loadResponseData(String url){
        final Map<String,String> params = new HashMap<String, String>();
        params.put("empty","empty");

        Log.e(TAG,params.toString());
        Log.e(TAG,url);

        final SweetAlertDialog sDialog = new SweetAlertDialog(context,SweetAlertDialog.PROGRESS_TYPE);
        sDialog.setTitle("Processing. . .");
        sDialog.show();

        StringPostRequest strReq = new StringPostRequest();
        strReq.sendRequest(Request.Method.GET,context, params, url, new VolleyResponseListener() {
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
                                sDialog.dismissWithAnimation();

                            } catch (Exception e) {
                                Log.e(TAG,e.getMessage());
                            }finally {
                                //Notify adapter about data changes
//                                notifyItemChanged(i);

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
        private CardView mCard;
        public TextView mNama, mLokasi, mShortDesc, durasiDays, durasiHour, harga;
        public ImageView imageTour;
        ConstraintLayout mParentLayout;
        Button mRemove;

        public MyViewHolder(View itemView) {
            super(itemView);
            mCard = itemView.findViewById(R.id.item_tour_card);
            mNama = itemView.findViewById(R.id.item_tour_nama);
            mLokasi = itemView.findViewById(R.id.item_tour_lokasi);
            mShortDesc = itemView.findViewById(R.id.item_tour_shortDesc);
            durasiDays = itemView.findViewById(R.id.item_tour_durationDays);
            durasiHour = itemView.findViewById(R.id.item_tour_durationHour);
            harga = itemView.findViewById(R.id.item_tour_hargaMinimum);
            imageTour = itemView.findViewById(R.id.item_tour_imageTour);
            mParentLayout = itemView.findViewById(R.id.item_tour_constraintLayout);
            mRemove = itemView.findViewById(R.id.item_tour_btnRemove);

        }
    }
}
