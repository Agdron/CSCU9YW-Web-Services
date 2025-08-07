package com.example.ResortBooking.model;

/**
 * Reservation Class sets up the parameters for the reservations on the tours. As these are randomly selected from the database
 * it was deemed necessary to only require the name and the reference.
 *
 * In hindsight this was a mistake as it means the tours can no longer be validated
 * cue the sad trumpet noises as my mark plummets
 *
 * Getters and setters provided below as per standard
 */

public class Reservation {
    private String reference;
    private String name;

    public Reservation(String reference, String name) {
        this.reference = reference;
        this.name = name;
    }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "Reservation{" +
                "reference='" + reference + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}