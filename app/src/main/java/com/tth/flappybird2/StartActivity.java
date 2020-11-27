package com.tth.flappybird2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tth.flappybird2.sprites.GameDificulty;

import java.sql.Array;

public class StartActivity extends Activity {
    private ImageView ivPlay, ivStore, ivMute;
    private TextView tvLevel;
    private SharedPreferences preferences;
    private int dificulty_current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        preferences = getSharedPreferences("DIF", MODE_PRIVATE);
        dificulty_current = preferences.getInt("DIF", 0);
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
                builder.setTitle("Chọn mức độ khó của trò chơi");
                final String[] item = new String[]{"Dễ", "Bình thường", "Khó"};
                builder.setSingleChoiceItems(item, dificulty_current, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dificulty_current = i;
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        preferences.edit().putInt("DIF", dificulty_current).apply();
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        ivMute = findViewById(R.id.imageView5);
        ivMute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mute volume
            }
        });
    }

}