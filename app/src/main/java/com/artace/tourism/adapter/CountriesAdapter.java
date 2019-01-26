package com.artace.tourism.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.artace.tourism.MainActivity;
import com.artace.tourism.R;
import com.artace.tourism.model.ModelCountry;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Herlambang
 */

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.MyViewHolder>{

    private List<ModelCountry> dataList;
    private Context context;
    private LayoutInflater inflater;
    ModelCountry data;
    RequestQueue queue;
    final String TAG = "CountriesAdapter";
    SharedPreferences sharedpreferences;

    public CountriesAdapter(Context context, List<ModelCountry> dataList) {

        this.context = context;
        this.dataList = dataList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView = inflater.inflate(R.layout.item_countries, parent, false);
        return new MyViewHolder(rootView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        data = dataList.get(position);

        holder.mNama.setText(data.getName());

        Picasso.with(context)
                .load(data.getImage())
                .placeholder(R.drawable.placeholder_vertical)
                .into(holder.mImage);

        holder.mCard.setTag(position);
        holder.mCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data = dataList.get((int) view.getTag());

                //TODO: Intent ke halaman country
            }
        });

    }



    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mNama;
        private ImageView mImage;
        private CardView mCard;

        public MyViewHolder(View itemView) {
            super(itemView);

            mNama = itemView.findViewById(R.id.item_countries_name);
            mImage = itemView.findViewById(R.id.item_countries_image);
            mCard = itemView.findViewById(R.id.item_countries_card);

        }
    }
}
