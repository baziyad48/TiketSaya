package com.example.android.tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Ticket extends AppCompatActivity {

    DatabaseReference reference;
    TextView tour_name, location, tour_time, tour_date, policy, quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        tour_name = findViewById(R.id.detail_tour);
        location = findViewById(R.id.detail_location);
        tour_time = findViewById(R.id.detail_time);
        tour_date = findViewById(R.id.detail_date);
        policy = findViewById(R.id.detail_policy);
        quantity = findViewById(R.id.detail_quantity);

        Bundle bundle = getIntent().getExtras();
        final String nama_wisata = bundle.getString("tour_name");
        final String jumlah_tiket = bundle.getString("quantity");

        reference = FirebaseDatabase.getInstance().getReference().child("Tour").child(nama_wisata);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tour_name.setText(dataSnapshot.child("tour_name").getValue().toString());
                location.setText(dataSnapshot.child("location").getValue().toString());
                tour_time.setText(dataSnapshot.child("tour_time").getValue().toString());
                policy.setText(dataSnapshot.child("policy").getValue().toString());
                quantity.setText(jumlah_tiket + " Tickets");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
