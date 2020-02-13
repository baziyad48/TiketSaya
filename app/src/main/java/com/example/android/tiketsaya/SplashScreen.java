package com.example.android.tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    Animation app_splash, bottom_up;
    ImageView app_logo;
    TextView app_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Load animation
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        bottom_up = AnimationUtils.loadAnimation(this, R.anim.bottom_up);

        app_logo = findViewById(R.id.app_logo);
        app_title = findViewById(R.id.app_title);

        //Menjalankan animasi
        app_logo.startAnimation(app_splash);
        app_title.startAnimation(bottom_up);

        //handler untuk mengubah activity berdasarkan waktu
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //berpindah activity
                Intent intent = new Intent(SplashScreen.this, GetStarted.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}
