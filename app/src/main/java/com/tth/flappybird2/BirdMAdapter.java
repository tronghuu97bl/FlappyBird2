package com.tth.flappybird2;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BirdMAdapter extends RecyclerView.Adapter {
    private static final String APP_NAME = "FlappyBird2";
    private List<BirdM> bird;
    private Context context;
    private int coin;
    private SharedPreferences pre;
    private DbHelper db;

    public BirdMAdapter(List<BirdM> bird, Context context) {
        this.bird = bird;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bird_item_store, parent, false);
        return new BirdViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final BirdViewHolder birdViewHolder = (BirdViewHolder) holder;
        final BirdM birdM = bird.get(position);
        pre = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        coin = pre.getInt("coin_fre", 0);
        birdViewHolder.button_set.setEnabled(false);
        birdViewHolder.button_set.setText("Áp dụng");
        if (birdM.getState()) {
            birdViewHolder.button.setText("Đã sở hữu");
            birdViewHolder.button.setClickable(false);
            birdViewHolder.button_set.setEnabled(true);
        } else {
            birdViewHolder.button.setText(String.valueOf(birdM.getPrice()));
            birdViewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(coin>birdM.getPrice()){
                        db = new DbHelper(context);
                        db.updateBird(birdM);
                        coin -= birdM.getPrice();
                        pre.edit().putInt("coin_fre", coin).apply();
                        ((StoreActivity) context).updateCoin();
                        birdViewHolder.button.setText("Đã sở hữu");
                        birdViewHolder.button.setClickable(false);
                        birdViewHolder.button_set.setEnabled(true);
                    }
                }
            });
        }
        birdViewHolder.imageView.setImageResource(birdM.getImageID());
        birdViewHolder.textView.setText(birdM.getName());
        birdViewHolder.button_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pre.edit().putInt("Bird_id", birdM.getBird_id()).apply();
            }
        });
    }

    @Override
    public int getItemCount() {
        return bird.size();
    }
}
