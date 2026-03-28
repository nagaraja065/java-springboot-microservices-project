package com.restaurant.model;

import jakarta.persistence.*;

@Entity
@Table(name = "restaurant_table")
public class RestaurantTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tableNumber;
    private int capacity;
    private String location; // e.g. Indoor, Outdoor, Window, Private
    private boolean available = true;

    // ---- Constructors ----
    public RestaurantTable() {}

    public RestaurantTable(Long id, String tableNumber, int capacity, String location, boolean available) {
        this.id = id;
        this.tableNumber = tableNumber;
        this.capacity = capacity;
        this.location = location;
        this.available = available;
    }

    // ---- Getters & Setters ----
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTableNumber() { return tableNumber; }
    public void setTableNumber(String tableNumber) { this.tableNumber = tableNumber; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}
