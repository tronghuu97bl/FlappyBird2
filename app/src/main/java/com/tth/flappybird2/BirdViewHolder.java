package com.tth.flappybird2;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BirdViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;
    public TextView textView;
    public Button button, button_set;

    public BirdViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageView6);
        button = itemView.findViewById(R.id.button);
        button_set = itemView.findViewById(R.id.button3);
        textView = itemView.findViewById(R.id.textView2);
    }
}
