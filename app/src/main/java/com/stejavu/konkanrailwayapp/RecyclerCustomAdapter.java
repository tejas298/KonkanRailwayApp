package com.stejavu.konkanrailwayapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.stejavu.konkanrailwayapp.com.konkanrail.entities.RecyclerDataModel;

import java.util.ArrayList;

public class RecyclerCustomAdapter extends RecyclerView.Adapter<RecyclerCustomAdapter.CustomViewHolder> {

    ArrayList<RecyclerDataModel> dataList;

    public RecyclerCustomAdapter(ArrayList<RecyclerDataModel> dataList) {
        this.dataList = dataList;
    }
/*
    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(),"View Clicked"+v.getTag(), Toast.LENGTH_LONG).show();
    }
*/
    public class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView viewContent;
        TextView viewDescription;
        ImageView viewImage;
        CardView cardView;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            viewContent = itemView.findViewById(R.id.viewName);
            viewImage = itemView.findViewById(R.id.viewImage);
            viewDescription = itemView.findViewById(R.id.viewDescription);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_card_view, parent, false);

        CustomViewHolder customViewHolder = new CustomViewHolder(view);
        view.setTag(customViewHolder.getAdapterPosition());
        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.cardView.setTag(position);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((int)v.getTag() == 1){
                    Intent intent = new Intent(v.getContext(),TrainStatusActivity.class);
                    v.getContext().startActivity(intent);
                }else if((int)v.getTag() == 0){
                    Intent intent = new Intent(v.getContext(),TrainSchedule.class);
                    v.getContext().startActivity(intent);
                }else if((int)v.getTag() == 2){
                    Intent intent = new Intent(v.getContext(),SeatPNRActivity.class);
                    v.getContext().startActivity(intent);
                }else if((int)v.getTag() == 3){
                    Intent intent = new Intent(v.getContext(),MainAmenitiesActivity.class);
                    v.getContext().startActivity(intent);
                }else if((int)v.getTag() == 4){
                    Intent intent = new Intent(v.getContext(),AbstractActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("motto","press");
                    intent.putExtras(bundle);
                    v.getContext().startActivity(intent);
                }else if((int)v.getTag() == 5){
                    Intent intent = new Intent(v.getContext(),AboutUs.class);
                    v.getContext().startActivity(intent);
                }
            }
        });
        holder.viewContent.setText(dataList.get(position).getContent());
        holder.viewContent.setTypeface(holder.viewContent.getTypeface(), Typeface.BOLD);

        holder.viewImage.setImageResource((int)dataList.get(position).getImageSrc());
        holder.viewDescription.setText(dataList.get(position).getContentDesc());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}
