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

public class TicketSuccess extends AppCompatActivity {

    Button btn_dashboard, btn_ticket;
    ImageView ticket_image;
    TextView ticket_title, ticket_subtitle;

    Animation app_splash, bottom_up, top_down;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_success);

        btn_dashboard = findViewById(R.id.btn_dashboard);
        btn_ticket = findViewById(R.id.btn_ticket);
        ticket_image = findViewById(R.id.ticket_image);
        ticket_title = findViewById(R.id.ticket_title);
        ticket_subtitle = findViewById(R.id.ticket_subtitle);

        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        top_down = AnimationUtils.loadAnimation(this, R.anim.top_down);
        bottom_up = AnimationUtils.loadAnimation(this, R.anim.bottom_up);

        ticket_image.startAnimation(app_splash);
        ticket_title.startAnimation(top_down);
        ticket_subtitle.startAnimation(top_down);
        btn_ticket.startAnimation(bottom_up);
        btn_dashboard.startAnimation(bottom_up);

        btn_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TicketSuccess.this, Home.class);
                startActivity(intent);
            }
        });

        btn_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TicketSuccess.this, Ticket.class);
                startActivity(intent);
            }
        });
    }
}
