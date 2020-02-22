package com.example.android.tiketsaya;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {
    Context context;
    ArrayList<TicketItem> ticketItems;

    public TicketAdapter(Context context, ArrayList<TicketItem> ticketItems) {
        this.context = context;
        this.ticketItems = ticketItems;
    }

    //Memasangkan layout ke activity
    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TicketViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ticket, parent, false));
    }

    //Memasukkan data ke arraylist dari suatu class TicketItem
    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, final int position) {
        holder.tour_name.setText(ticketItems.get(position).getTour_name());
        holder.location.setText(ticketItems.get(position).getLocation());
        holder.quantity.setText(ticketItems.get(position).getQuantity() + " Ticket");

        //Ketika LinearLayout diklik akan berpindah activity
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Ticket.class);
                intent.putExtra("tour_name", ticketItems.get(position).getTour_name());
                intent.putExtra("quantity", ticketItems.get(position).getQuantity());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ticketItems.size();
    }

    class TicketViewHolder extends RecyclerView.ViewHolder{
        TextView tour_name, location, quantity;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);

            tour_name = itemView.findViewById(R.id.ticket_item_title);
            location = itemView.findViewById(R.id.ticket_item_location);
            quantity = itemView.findViewById(R.id.ticket_item_quantity);
        }
    }

}
