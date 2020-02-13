package com.example.android.tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class Profile extends AppCompatActivity {

    LinearLayout ticket_detail;
    Button btn_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ticket_detail = (LinearLayout) findViewById(R.id.ticket_detail);
        btn_edit = (Button) findViewById(R.id.btn_edit);

        ticket_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, Ticket.class);
                startActivity(intent);
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, ProfileEdit.class);
                startActivity(intent);
            }
        });
    }
}
