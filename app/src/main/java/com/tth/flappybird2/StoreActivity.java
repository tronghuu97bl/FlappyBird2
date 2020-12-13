package com.tth.flappybird2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class StoreActivity extends AppCompatActivity {
    private static final String APP_NAME = "FlappyBird2";
    private List<BirdM> birdMList;
    private RecyclerView recyclerView;
    private DbHelper dbHelper;
    private BirdMAdapter birdMAdapter;
    private int coin;
    private SharedPreferences pre;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        button = findViewById(R.id.button2);
        recyclerView = findViewById(R.id.rv);
        dbHelper = new DbHelper(this);
        //dbHelper.deleteListBird();
        birdMList = dbHelper.getListBirdM();
        if (birdMList.size() == 0) {
            dbHelper.creatListBird();
            birdMList = dbHelper.getListBirdM();
        }
        birdMAdapter = new BirdMAdapter(birdMList, this);
        recyclerView.setAdapter(birdMAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        pre = getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        coin = pre.getInt("coin_fre", 0);
        button.setText(String.valueOf(coin));
        button.setClickable(false);
    }

    public void updateCoin() {
        coin = pre.getInt("coin_fre", 0);
        button.setText(String.valueOf(coin));
    }
}