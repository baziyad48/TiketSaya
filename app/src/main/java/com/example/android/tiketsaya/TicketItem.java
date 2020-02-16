package com.example.android.tiketsaya;

public class TicketItem {
    String tour_name, location, quantity, id_ticket;

    public TicketItem() {
    }

    public TicketItem(String id_ticket, String tour_name, String location, String quantity) {
        this.id_ticket = id_ticket;
        this.tour_name = tour_name;
        this.location = location;
        this.quantity = quantity;
    }

    public void setId_ticket(String id_ticket) {
        this.id_ticket = id_ticket;
    }

    public void setTour_name(String tour_name) {
        this.tour_name = tour_name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getId_ticket() {
        return id_ticket;
    }

    public String getTour_name() {
        return tour_name;
    }

    public String getLocation() {
        return location;
    }

    public String getQuantity() {
        return quantity;
    }
}
