package com.example.android.tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GetStarted extends AppCompatActivity {
    Button btn_signIn, btn_getStarted;
    Animation top_down, bottom_up;
    ImageView app_emblem;
    TextView app_tagline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        top_down = AnimationUtils.loadAnimation(this, R.anim.top_down);
        bottom_up = AnimationUtils.loadAnimation(this, R.anim.bottom_up);

        app_emblem = findViewById(R.id.app_emblem);
        app_tagline = findViewById(R.id.app_tagline);

        btn_signIn = findViewById(R.id.btn_signIn);
        btn_getStarted = findViewById(R.id.btn_getStarted);

        app_emblem.startAnimation(top_down);
        app_tagline.startAnimation(top_down);

        btn_signIn.startAnimation(bottom_up);
        btn_getStarted.startAnimation(bottom_up);

        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GetStarted.this, SignIn.class);
                startActivity(intent);
            }
        });

        btn_getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GetStarted.this, Register.class);
                startActivity(intent);
            }
        });
    }
}
