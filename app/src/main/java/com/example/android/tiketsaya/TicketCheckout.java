package com.example.android.tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TicketCheckout extends AppCompatActivity {

    Button btn_pay, btn_plus, btn_minus;
    TextView caption_quantity;
    int default_quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_checkout);

        btn_pay = (Button) findViewById(R.id.btn_pay);
        btn_plus = (Button) findViewById(R.id.btn_plus);
        btn_minus = (Button) findViewById(R.id.btn_minus);
        caption_quantity = (TextView) findViewById(R.id.caption_quantity);

        //Inisialisasi jumlah ticket ketika petama kali
        caption_quantity.setText(String.valueOf(default_quantity));

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
                if(default_quantity < 99) {
                    default_quantity++;
                    caption_quantity.setText(String.valueOf(default_quantity));
                }
            }
        });

        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(default_quantity > 1) {
                    default_quantity--;
                    caption_quantity.setText(String.valueOf(default_quantity));
                }
            }
        });
    }
}
