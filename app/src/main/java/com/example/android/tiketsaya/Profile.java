package com.example.android.tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {

    Button btn_edit, btn_signout;
    ImageView img_avatar, edit_back;
    TextView name, bio;

    RecyclerView view;
    ArrayList<TicketItem> list;
    TicketAdapter adapter;

    DatabaseReference reference;

    final String USERNAME_KEY = "username";
    String username_default;
    String username_local;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btn_edit = findViewById(R.id.btn_edit);
        btn_signout = findViewById(R.id.btn_signout);
        edit_back = findViewById(R.id.edit_back);
        img_avatar = findViewById(R.id.profile_image);
        name = findViewById(R.id.profile_name);
        bio = findViewById(R.id.profile_bio);

        view = findViewById(R.id.item_ticket_placeholder);
        view.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<TicketItem>();

        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_local = sharedPreferences.getString(username_default, "");

        reference = FirebaseDatabase.getInstance().getReference().child("User").child(username_local);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name.setText(dataSnapshot.child("name").getValue().toString());
                bio.setText(dataSnapshot.child("bio").getValue().toString());
                Picasso.get().load(dataSnapshot.child("url_photo").getValue().toString()).fit().centerCrop().into(img_avatar);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reference = FirebaseDatabase.getInstance().getReference().child("Ticket").child(username_local);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Melakukan perulangan untuk inisialisasi nilai ArrayList yang berasal dari Firebase
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    TicketItem ticket = snapshot.getValue(TicketItem.class);
                    list.add(ticket);
                }

                //Membuat adapter yang diisi oleh list yang sudah dibuat
                adapter = new TicketAdapter(Profile.this, list);
                view.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, ProfileEdit.class);
                startActivity(intent);
            }
        });

        edit_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, Home.class);
                startActivity(intent);
            }
        });

        btn_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Menghapus shared preferences
                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(username_default, null);
                editor.apply();

                Intent intent = new Intent(Profile.this, GetStarted.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
