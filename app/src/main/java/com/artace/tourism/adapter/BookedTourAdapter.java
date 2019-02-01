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
import com.artace.tourism.model.ModelTransaction;
import com.artace.tourism.utils.FormatTanggal;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class BookedTourAdapter extends RecyclerView.Adapter<BookedTourAdapter.MyViewHolder> {

    private List<ModelTransaction> dataList;
    private Context context;
    private LayoutInflater inflater;
    ModelTransaction data;
    final String TAG = "BookedTourAdpt";

    public BookedTourAdapter(Context context, List<ModelTransaction> dataList) {
        this.context = context;
        this.dataList = dataList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.item_booked_tour, parent, false);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        data = dataList.get(position);

//        this.tour_id = tour_id;
//        this.tour_start_date = tour_start_date;
//        this.price = price;
//        this.image = image;
//        this.short_description = short_description;
//        this.name = name;
//        this.location = location;

        holder.mNama.setText(data.getName());
        holder.mLokasi.setText(data.getLocation());
        holder.mShortDesc.setText(data.getShort_description());
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        holder.mDateStart.setText(format.format(data.getTour_start_date()));



        holder.mPrice.setText("US$ "+String.valueOf(data.getPrice()));

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
                extras.putString("id",String.valueOf(data.getTour_id()));
                extras.putString("name",data.getName());
                extras.putString("image",data.getImage());
                intent.putExtras(extras);
                context.startActivity(intent);
            }
        });

        if(data.getConfirmation() == 0){
            holder.mStatus.setText("Pending Confirmation");
            holder.mStatus.setBackgroundColor(context.getResources().getColor(R.color.colorSecondary));
        }
        else if(data.getConfirmation() == 1){
            holder.mStatus.setText("Confirmed");
            holder.mStatus.setBackgroundColor(context.getResources().getColor(R.color.colorSuccess));
        }
        else if(data.getConfirmation() == 2){
            holder.mStatus.setText("Rejected");
            holder.mStatus.setBackgroundColor(context.getResources().getColor(R.color.colorWarning));
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CardView mCard;
        public TextView mNama, mLokasi, mShortDesc, mDateStart, mPrice, mStatus;
        public ImageView imageTour;

        public MyViewHolder(View itemView) {
            super(itemView);
            mCard = itemView.findViewById(R.id.item_booked_card);

            mNama = itemView.findViewById(R.id.item_booked_nama);
            mLokasi = itemView.findViewById(R.id.item_booked_lokasi);
            mShortDesc = itemView.findViewById(R.id.item_booked_shortDesc);
            mDateStart = itemView.findViewById(R.id.item_booked_startdate);
            mPrice = itemView.findViewById(R.id.item_booked_price);
            imageTour = itemView.findViewById(R.id.item_booked_imageTour);
            mStatus = itemView.findViewById(R.id.item_booked_status);

        }
    }
}
