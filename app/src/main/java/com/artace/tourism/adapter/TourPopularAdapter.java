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
import android.widget.ImageView;
import android.widget.TextView;
import com.artace.tourism.R;
import com.artace.tourism.TourDetailActivity;
import com.artace.tourism.model.ModelTour;
import com.squareup.picasso.Picasso;
import java.util.List;

public class TourPopularAdapter extends RecyclerView.Adapter<TourPopularAdapter.MyViewHolder> {

    private List<ModelTour> dataList;
    private Context context;
    private LayoutInflater inflater;
    ModelTour data;
    final String TAG = "TourAdapter";

    public TourPopularAdapter(Context context, List<ModelTour> dataList) {
        this.context = context;
        this.dataList = dataList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.item_tour_popular, parent, false);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        data = dataList.get(position);

        holder.mNama.setText(data.getName());
        holder.mLokasi.setText(data.getLocation());
        holder.mShortDesc.setText(data.getShort_description());
        holder.mPrice.setText(Double.toString(data.getAdult_price()));
        setDurationView(holder);
        holder.mDurationDays.setText(Integer.toString(data.getDuration_day())+" Days");
        holder.mDurationHour.setText(Integer.toString(data.getDuration_hour())+" Hour");
        Picasso.with(context)
                .load(data.getImage())
                .placeholder(R.drawable.placeholder_vertical)
                .into(holder.imageTour);

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
                extras.putString("status", data.getCustomStatus());
                intent.putExtras(extras);
                context.startActivity(intent);
            }
        });
    }

    private void setDurationView(MyViewHolder holder){
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(holder.mParentLayout);

        if (data.getDuration_hour() < 1 && data.getDuration_day() > 0){
            constraintSet.clear(R.id.item_tour_popular_durationDays,ConstraintSet.END);
            constraintSet.connect(R.id.item_tour_popular_durationDays,ConstraintSet.END,R.id.item_tour_popular_txtDuration,ConstraintSet.END,100);
            constraintSet.applyTo(holder.mParentLayout);
            holder.mDurationHour.setVisibility(View.GONE);
        }
        if (data.getDuration_day() < 1 && data.getDuration_hour() > 0){
            constraintSet.clear(R.id.item_tour_popular_durationHour,ConstraintSet.RIGHT);
            constraintSet.connect(R.id.item_tour_popular_durationHour,ConstraintSet.RIGHT,R.id.item_tour_popular_constraintLayout,ConstraintSet.RIGHT,32);
            constraintSet.applyTo(holder.mParentLayout);
            holder.mDurationDays.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CardView mCard;
        private TextView mNama, mLokasi, mShortDesc, mDurationDays, mDurationHour, mPrice;
        private ImageView imageTour;
        private ConstraintLayout mParentLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            mCard = itemView.findViewById(R.id.item_tour_popular_card);
            mNama = itemView.findViewById(R.id.item_tour_popular_nama);
            mLokasi = itemView.findViewById(R.id.item_tour_popular_lokasi);
            mShortDesc = itemView.findViewById(R.id.item_tour_popular_shortDesc);
            mDurationDays = itemView.findViewById(R.id.item_tour_popular_durationDays);
            mDurationHour = itemView.findViewById(R.id.item_tour_popular_durationHour);
            mPrice = itemView.findViewById(R.id.item_tour_popular_hargaMinimum);
            imageTour = itemView.findViewById(R.id.item_tour_popular_imageTour);
            mParentLayout = itemView.findViewById(R.id.item_tour_popular_constraintLayout);
        }
    }
}
