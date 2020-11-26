package com.tth.flappybird2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tth.flappybird2.sprites.GameDificulty;

public class StartActivity extends Activity {
    private ImageView ivPlay, ivStore, ivMute;
    private TextView tvLevel;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        preferences = getSharedPreferences("DIF",MODE_PRIVATE);
        if(preferences!=null){
            preferences.edit().putInt("DIF",1).apply();
        }
        ivPlay = findViewById(R.id.imageView);
        ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        ivStore = findViewById(R.id.imageView4);
        ivStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        tvLevel = findViewById(R.id.textView);
        tvLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show dialog, pick Dificulty and set Dificulty
            }
        });

        ivMute=findViewById(R.id.imageView5);
        ivMute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mute volume
            }
        });
    }

}