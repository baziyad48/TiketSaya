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

public class RegisterSuccess extends AppCompatActivity {

    Button btn_explore;
    ImageView succes_img;
    TextView success_title, success_subtitle;

    Animation app_spalsh, bottom_up, top_down;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_success);

        btn_explore = findViewById(R.id.btn_explore);

        succes_img = findViewById(R.id.success_img);
        success_title = findViewById(R.id.success_title);
        success_subtitle = findViewById(R.id.success_subtitle);

        app_spalsh = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        bottom_up = AnimationUtils.loadAnimation(this, R.anim.bottom_up);
        top_down = AnimationUtils.loadAnimation(this, R.anim.top_down);

        succes_img.setAnimation(app_spalsh);
        success_title.setAnimation(top_down);
        success_subtitle.setAnimation(top_down);
        btn_explore.setAnimation(bottom_up);

        btn_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterSuccess.this, Home.class);
                startActivity(intent);
            }
        });
    }
}
