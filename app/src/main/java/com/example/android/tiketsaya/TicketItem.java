package com.example.android.tiketsaya;

public class TicketItem {
    String tour_name, location, quantity;

    public TicketItem() {
    }

    public TicketItem(String tour_name, String location, String quantity) {
        this.tour_name = tour_name;
        this.location = location;
        this.quantity = quantity;
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
