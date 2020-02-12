package com.example.android.tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TicketSuccess extends AppCompatActivity {

    Button btn_ticket, btn_dashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_success);

        btn_ticket = (Button) findViewById(R.id.btn_ticket);
        btn_dashboard = (Button) findViewById(R.id.btn_dashboard);

        btn_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TicketSuccess.this, Profile.class);
                startActivity(intent);
            }
        });

        btn_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TicketSuccess.this, Home.class);
                startActivity(intent);
            }
        });
    }
}
