package com.example.android.tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity {

    ImageView img_pisa, img_torii, img_pagoda, img_temple, img_sphinx, img_monas, profile;
    TextView name, bio, balance;

    final String USERNAME_KEY = "username";
    String username_default;
    String username_local;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_local = sharedPreferences.getString(username_default, "");

        img_pisa = findViewById(R.id.img_pisa);
        img_torii = findViewById(R.id.img_torii);
        img_pagoda = findViewById(R.id.img_pagoda);
        img_temple = findViewById(R.id.img_temple);
        img_sphinx = findViewById(R.id.img_sphinx);
        img_monas = findViewById(R.id.img_monas);

        profile = findViewById(R.id.profile);
        name = findViewById(R.id.home_name);
        bio = findViewById(R.id.home_bio);
        balance = findViewById(R.id.home_balance);

        reference = FirebaseDatabase.getInstance().getReference().child("User").child(username_local);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Set value dari Firebase
                name.setText(dataSnapshot.child("name").getValue().toString());
                bio.setText(dataSnapshot.child("bio").getValue().toString());
                balance.setText("IDR " + dataSnapshot.child("balance").getValue().toString() + "K");

                //Load gambar dari Firebasae menggunakan Picasso
                Log.v("url_photo", dataSnapshot.child("url_photo").getValue().toString());
                Picasso.get().load(dataSnapshot.child("url_photo").getValue().toString()).fit().centerCrop().into(profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        img_pisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, TicketDetail.class);
                intent.putExtra("tour_title", "Pisa");
                startActivity(intent);
            }
        });

        img_torii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, TicketDetail.class);
                intent.putExtra("tour_title", "Torii");
                startActivity(intent);
            }
        });

        img_pagoda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, TicketDetail.class);
                intent.putExtra("tour_title", "Pagoda");
                startActivity(intent);
            }
        });

        img_temple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, TicketDetail.class);
                intent.putExtra("tour_title", "Temple");
                startActivity(intent);
            }
        });

        img_sphinx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, TicketDetail.class);
                intent.putExtra("tour_title", "Sphinx");
                startActivity(intent);
            }
        });

        img_monas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, TicketDetail.class);
                intent.putExtra("tour_title", "Monas");
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Profile.class);
                startActivity(intent);
            }
        });
    }
}
