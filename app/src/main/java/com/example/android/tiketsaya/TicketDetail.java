package com.example.android.tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class TicketDetail extends AppCompatActivity {

    Button btn_buy_ticket;
    TextView caption_title, caption_city, caption_photo, caption_wifi, caption_festival, description;
    ImageView thumbnail;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);

        btn_buy_ticket = findViewById(R.id.buy_ticket);
        caption_title = findViewById(R.id.caption_title);
        caption_city = findViewById(R.id.caption_city);
        caption_photo = findViewById(R.id.caption_photo);
        caption_wifi = findViewById(R.id.caption_wifi);
        caption_festival = findViewById(R.id.caption_festival);
        description = findViewById(R.id.description);
        thumbnail = findViewById(R.id.thumbnail);

        //Mengambil data dari intent
        Bundle bundle =  getIntent().getExtras();
        String tour_title = bundle.getString("tour_title", "");

        reference = FirebaseDatabase.getInstance().getReference().child("Tour").child(tour_title);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                caption_title.setText(dataSnapshot.child("tour_name").getValue().toString());
                caption_city.setText(dataSnapshot.child("location").getValue().toString());
                caption_photo.setText(dataSnapshot.child("is_photo_spot").getValue().toString());
                caption_wifi.setText(dataSnapshot.child("is_wifi").getValue().toString());
                caption_festival.setText(dataSnapshot.child("is_festival").getValue().toString());
                description.setText(dataSnapshot.child("short_description").getValue().toString());

                Picasso.get().load(dataSnapshot.child("url_thumbnail").getValue().toString()).fit().centerCrop().into(thumbnail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_buy_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TicketDetail.this, TicketCheckout.class);
                startActivity(intent);
            }
        });
    }
}
