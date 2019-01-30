package com.artace.tourism.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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
        holder.durasi.setText(Integer.toString(data.getDuration_day()));

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
                intent.putExtras(extras);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CardView mCard;
        public TextView mNama, mLokasi, mShortDesc, durasi, harga;
        public ImageView imageTour;
        public MyViewHolder(View itemView) {
            super(itemView);
            mCard = itemView.findViewById(R.id.item_tour_card);
            mNama = itemView.findViewById(R.id.item_tour_nama);
            mLokasi = itemView.findViewById(R.id.item_tour_lokasi);
            mShortDesc = itemView.findViewById(R.id.item_tour_shortDesc);
            durasi = itemView.findViewById(R.id.item_tour_duration);
            harga = itemView.findViewById(R.id.item_tour_hargaMinimum);
            imageTour = itemView.findViewById(R.id.item_tour_imageTour);
        }
    }
}
