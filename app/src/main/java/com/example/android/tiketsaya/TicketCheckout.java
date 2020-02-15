package com.example.android.tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TicketCheckout extends AppCompatActivity {

    Button btn_pay, btn_plus, btn_minus;
    TextView caption_quantity, caption_price, caption_balance, caption_title, caption_city, caption_policy;
    int quantity = 1, balance , price;
    ImageView alert;

    final String USERNAME_KEY = "username";
    String username_default;
    String username_local;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_checkout);

        Bundle bundle =  getIntent().getExtras();
        final String tour_title = bundle.getString("tour_title", "");

        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_local = sharedPreferences.getString(username_default, "");

        btn_pay = findViewById(R.id.btn_pay);
        btn_plus =  findViewById(R.id.btn_plus);
        btn_minus = findViewById(R.id.btn_minus);
        caption_quantity = findViewById(R.id.caption_quantity);
        caption_balance = findViewById(R.id.caption_balance);
        caption_price = findViewById(R.id.caption_price);
        alert = findViewById(R.id.alert);
        caption_title = findViewById(R.id.caption_title_checkout);
        caption_city = findViewById(R.id.caption_city_checkout);
        caption_policy = findViewById(R.id.policy_checkout);

        //Mengisi TextView
        reference = FirebaseDatabase.getInstance().getReference().child("Tour").child(tour_title);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                caption_title.setText(dataSnapshot.child("tour_name").getValue().toString());
                caption_city.setText(dataSnapshot.child("location").getValue().toString());
                caption_policy.setText(dataSnapshot.child("policy").getValue().toString());
                price = Integer.valueOf(dataSnapshot.child("ticket_price").getValue().toString());
                caption_price.setText("IDR " + (quantity * price) + "K");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Mengisi balance
        reference = FirebaseDatabase.getInstance().getReference().child("User").child(username_local);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                balance = Integer.valueOf(dataSnapshot.child("balance").getValue().toString());
                caption_balance.setText("IDR " + balance + "K");
                Log.v("balance", String.valueOf(balance));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Inisialisasi jumlah ticket ketika petama kali
        caption_quantity.setText(String.valueOf(quantity));
        btn_minus.animate().alpha(0).setDuration(300).start();
        btn_minus.setEnabled(false);
        alert.animate().alpha(0).setDuration(300).start();

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_pay.setEnabled(false);
                btn_pay.setText("Loading");

                //Update balance dan ticket history
                reference = FirebaseDatabase.getInstance().getReference().child("Ticket").child(username_local).child(tour_title + "_" + System.currentTimeMillis());

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("id_ticket").setValue(tour_title + "_" + System.currentTimeMillis());
                        dataSnapshot.getRef().child("tour_name").setValue(tour_title);
                        dataSnapshot.getRef().child("quantity").setValue(String.valueOf(quantity));
                        dataSnapshot.getRef().child("location").setValue(caption_city.getText().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                reference = FirebaseDatabase.getInstance().getReference().child("User").child(username_local);
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        reference.getRef().child("balance").setValue(balance - (quantity * price));

                        Intent intent = new Intent(TicketCheckout.this, TicketSuccess.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity++;
                caption_quantity.setText(String.valueOf(quantity));
                caption_price.setText("IDR " + (quantity * price) + "K");
                if(quantity > 1){
                    btn_minus.animate().alpha(1).setDuration(300).start();
                    btn_minus.setEnabled(true);
                }
                if(quantity > 9 || (quantity * price) > balance) {
                    btn_plus.animate().alpha(0).setDuration(300).start();
                    btn_plus.setEnabled(false);
                }
                if(quantity * price > balance) {
                    caption_balance.setTextColor(getResources().getColor(R.color.textRed));
                    btn_pay.animate().translationY(200).alpha(0).setDuration(300).start();
                    btn_pay.setEnabled(false);
                    alert.animate().alpha(1).setDuration(300).start();
                }
            }
        });

        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity--;
                caption_quantity.setText(String.valueOf(quantity));
                caption_price.setText("IDR " + (quantity * price) + "K");
                if(quantity < 2){
                    btn_minus.animate().alpha(0).setDuration(300).start();
                    btn_minus.setEnabled(false);
                }
                if(quantity < 10) {
                    btn_plus.animate().alpha(1).setDuration(300).start();
                    btn_plus.setEnabled(true);
                }
                if(quantity * price <= balance) {
                    caption_balance.setTextColor(getResources().getColor(R.color.bluePrimary));
                    btn_pay.animate().translationY(0).alpha(1).setDuration(300).start();
                    btn_pay.setEnabled(true);
                    alert.animate().alpha(0).setDuration(300).start();
                }
            }
        });
    }
}
