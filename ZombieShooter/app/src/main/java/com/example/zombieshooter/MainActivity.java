package com.example.zombieshooter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private boolean mute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GameActivity.class));
            }

        });

        TextView highScoreTxt = findViewById(R.id.highScoreTxt);

        final SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);
        highScoreTxt.setText("HighScore: " + prefs.getInt("highscore", 0));

        mute = prefs.getBoolean("mute", false);

        final ImageView volumeCtrl = findViewById(R.id.volumeCtrl);

        if (mute) {
            volumeCtrl.setImageResource(R.drawable.ic_baseline_volume_off_24);
        } else {
            volumeCtrl.setImageResource(R.drawable.ic_baseline_volume_up_24);
        }

        volumeCtrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mute = !mute;

                if (mute) {
                    volumeCtrl.setImageResource(R.drawable.ic_baseline_volume_off_24);
                } else {
                    volumeCtrl.setImageResource(R.drawable.ic_baseline_volume_up_24);
                }

                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("mute", mute);
                editor.apply();
            }
        });
    }
}