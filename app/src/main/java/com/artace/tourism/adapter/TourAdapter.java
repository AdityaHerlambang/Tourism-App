package com.artace.tourism.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.artace.tourism.R;
import com.artace.tourism.model.Tour;

import java.util.List;

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.ViewHolder> {

    private Context context;
    private List<Tour> list;

    public TourAdapter(Context context, List<Tour> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_tour, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Tour Tour = list.get(position);

        holder.mNama.setText(Tour.getNama());
        holder.mLokasi.setText(Tour.getLokasi());
        holder.mShortDesc.setText(Tour.getShortDesc());
        holder.harga.setText(Tour.getHarga());
        holder.durasi.setText(Tour.getDurasi());
        int id = context.getResources().getIdentifier("com.artace.tourism.adapter:drawable/" + Tour.getImageCategory(), null, null);
        holder.imageCategory.setImageResource(id);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mNama, mLokasi, mShortDesc, durasi, harga;
        public ImageView imageCategory;

        public ViewHolder(View itemView) {
            super(itemView);

            mNama = itemView.findViewById(R.id.item_tour_nama);
            mLokasi = itemView.findViewById(R.id.item_tour_lokasi);
            mShortDesc = itemView.findViewById(R.id.item_tour_shortDesc);
            durasi = itemView.findViewById(R.id.item_tour_duration);
            harga = itemView.findViewById(R.id.item_tour_hargaMinimum);
            imageCategory = itemView.findViewById(R.id.activity_category_imageHeader);

        }
    }

}
