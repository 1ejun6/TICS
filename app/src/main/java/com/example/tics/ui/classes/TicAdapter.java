package com.example.tics.ui.classes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tics.R;

import java.util.ArrayList;

public class TicAdapter extends RecyclerView.Adapter<TicAdapter.TicViewHolder> {

    private ArrayList<Tic> TicList;

    public TicAdapter(ArrayList<Tic> ticList) {
        TicList = ticList;
    }

    @NonNull
    @Override
    public TicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tic_item, parent, false);
        return new TicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicViewHolder holder, int position) {
        Tic tic = TicList.get(position);

        holder.Tic_Count.setText(String.format("%d TIC", tic.getTicCount()));

        holder.Tic_DateTime.setText(tic.getTicDate() + "-" + tic.getTicTime());

        // Set the Picture
        byte[] pictureData = tic.getPicture();
        if (pictureData != null) {
            Bitmap pictureBitmap = BitmapFactory.decodeByteArray(pictureData, 0, pictureData.length);
            holder.Tic_Picture.setImageBitmap(pictureBitmap);
        }
    }

    @Override
    public int getItemCount() {
        return TicList.size();
    }

    static class TicViewHolder extends RecyclerView.ViewHolder {

        TextView Tic_Count;
        TextView Tic_DateTime;
        ImageView Tic_Picture;

        TicViewHolder(@NonNull View itemView) {
            super(itemView);
            Tic_Count = itemView.findViewById(R.id.Tic_Count);
            Tic_DateTime = itemView.findViewById(R.id.Tic_DateTime);
            Tic_Picture = itemView.findViewById(R.id.Tic_Picture);
        }
    }
}
