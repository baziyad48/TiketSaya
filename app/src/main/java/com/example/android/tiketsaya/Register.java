package com.example.android.tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    Button btn_register;
    EditText username, password, email;

    //Deklarasi referensi database
    DatabaseReference reference;

    //Deklarasi key shared preferences
    final String USERNAME_KEY = "username";
    String username_default;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_register = findViewById(R.id.btn_register);
        username = findViewById(R.id.register_username);
        password = findViewById(R.id.register_password);
        email = findViewById(R.id.register_email);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_register.setEnabled(false);
                btn_register.setText("Loading");

                //Menggunakan shared preferences
                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(username_default, username.getText().toString());
                editor.apply();

                //Upload ke database
                reference = FirebaseDatabase.getInstance().getReference().child("User").child(username.getText().toString());
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("username").setValue(username.getText().toString());
                        dataSnapshot.getRef().child("password").setValue(password.getText().toString());
                        dataSnapshot.getRef().child("email").setValue(email.getText().toString());
                        dataSnapshot.getRef().child("balance").setValue(100);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Intent intent = new Intent(Register.this, RegisterBio.class);
                startActivity(intent);
            }
        });
    }
}
