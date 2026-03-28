package com.restaurant.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "table_id")
    private RestaurantTable table;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time;

    private int numberOfPeople;

    private String status = "PENDING"; // PENDING, CONFIRMED, CANCELLED, REJECTED

    private String specialRequest;

    // ---- Constructors ----
    public Booking() {}

    public Booking(Long id, User user, RestaurantTable table, LocalDate date,
                   LocalTime time, int numberOfPeople, String status, String specialRequest) {
        this.id = id;
        this.user = user;
        this.table = table;
        this.date = date;
        this.time = time;
        this.numberOfPeople = numberOfPeople;
        this.status = status;
        this.specialRequest = specialRequest;
    }

    // ---- Getters & Setters ----
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public RestaurantTable getTable() { return table; }
    public void setTable(RestaurantTable table) { this.table = table; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getTime() { return time; }
    public void setTime(LocalTime time) { this.time = time; }

    public int getNumberOfPeople() { return numberOfPeople; }
    public void setNumberOfPeople(int numberOfPeople) { this.numberOfPeople = numberOfPeople; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getSpecialRequest() { return specialRequest; }
    public void setSpecialRequest(String specialRequest) { this.specialRequest = specialRequest; }
}
