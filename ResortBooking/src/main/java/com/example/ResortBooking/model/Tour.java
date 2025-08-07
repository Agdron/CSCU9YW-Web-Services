package com.example.ResortBooking.model;

/**
 * Tour Class sets up the parameters for the Tours, listing the id, description, date, cost, number of places and how many are reserved.
 *
 * Getters and setters provided below as per standard
 */

public class Tour {
    private int id;
    private String description;
    private String date;
    private int cost;
    private int places;
    private int reserved;

    public Tour(int id, String description, String date, int cost, int places) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.cost = cost;
        this.places = places;
        this.reserved = 0;
    }

    public int getId() { return id; }
    public void setId(int id) {
        this.id = id;
    }
    public String getDescription() { return description; }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDate() { return date; }
    public void setDate(String date) {
        this.date = date;
    }
    public int getCost() { return cost; }
    public void setCost(int cost) {
        this.cost = cost;
    }
    public int getPlaces() { return places; }
    public void setPlaces(int places) {
        this.places = places;
    }
    public int getReserved() { return reserved; }
    public void setReserved(int reserved) { this.reserved = reserved; }
}