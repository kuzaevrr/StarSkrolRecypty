package com.kuzaev.starskrolrecypty.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kuzaev.starskrolrecypty.MainActivity;
import com.kuzaev.starskrolrecypty.R;
import com.kuzaev.starskrolrecypty.objects.Star;

import java.util.List;

public class AdapterStar extends RecyclerView.Adapter <AdapterStar.HolderAdapterStar> {

    List<Star> starList;

    public AdapterStar(List<Star> starList) {
        this.starList = starList;
    }

    @NonNull
    @Override
    public HolderAdapterStar onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.star_item, parent,false);
        return new HolderAdapterStar(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderAdapterStar holder, int position) {
        Star star = starList.get(position);
        holder.textView.setText(star.getStarName());
        holder.imageView.setImageBitmap(star.getBitmapStarImage());
    }

    @Override
    public int getItemCount() {
        return starList.size();
    }

    class HolderAdapterStar extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;

        public HolderAdapterStar(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

}
