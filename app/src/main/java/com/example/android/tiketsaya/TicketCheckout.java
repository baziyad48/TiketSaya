package com.example.android.tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TicketCheckout extends AppCompatActivity {

    Button btn_pay, btn_plus, btn_minus;
    TextView caption_quantity, caption_price, caption_balance;
    int quantity = 1, balance = 100, price = 20;
    ImageView alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_checkout);

        btn_pay = findViewById(R.id.btn_pay);
        btn_plus =  findViewById(R.id.btn_plus);
        btn_minus = findViewById(R.id.btn_minus);
        caption_quantity = findViewById(R.id.caption_quantity);
        caption_balance = findViewById(R.id.caption_balance);
        caption_price = findViewById(R.id.caption_price);
        alert = findViewById(R.id.alert);

        //Inisialisasi jumlah ticket ketika petama kali
        caption_quantity.setText(String.valueOf(quantity));
        btn_minus.animate().alpha(0).setDuration(300).start();
        btn_minus.setEnabled(false);
        alert.animate().alpha(0).setDuration(300).start();

        //Inisialisasi total harga dan balance ketika pertama kali
        caption_price.setText("IDR " + (quantity * price) + "K");
        caption_balance.setText("IDR " + balance + "K");

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TicketCheckout.this, TicketSuccess.class);
                startActivity(intent);
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
