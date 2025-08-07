package com.example.ResortBooking.service;


import com.example.ResortBooking.model.Reservation;
import com.example.ResortBooking.model.Tour;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TourService contains the business logic for managing tours and reservations.
 * It handles actions such as reserving, canceling, creating, updating, validating,
 * and finalizing tours.
 */
//Cross Origin used to suppress the warnings on certain browsers as it is unknown which will be used for the live demo
@CrossOrigin
@Service
public class TourService {

    // List to store all available tours
    private final List<Tour> tours = new ArrayList<>();
    // Map to store reservations by their unique booking reference
    private final Map<String, Reservation> reservations = new HashMap<>();
    // Map to store reservations grouped by tour ID
    private final Map<Integer, List<Reservation>> reservationsByTour = new HashMap<>(); // New mapping

    /**
     * Constructor initializes predefined tours. Note: Two are the same date despite an oversight in the programming of the assignment
     * as a result, date does not matter to the functionality
     */
    public TourService() {
        // Predefined tours
        tours.add(new Tour(123, "Whale Watching", "2025-01-20", 4500, 8));
        tours.add(new Tour(124, "Mountain Hiking", "2025-01-21", 3000, 10));
        tours.add(new Tour(125, "City Tour", "2025-01-20", 2000, 15));
    }

    /**
     * Retrieves all tours.
     * @return List of all tours.
     */
    public List<Tour> getAllTours() {
        return tours;
    }

    /**
     * Reserves a tour for a guest if the guest has not already reserved the same tour
     * and if there is available space on the tour.
     * @param reference Guest's unique booking reference.
     * @param name Guest's name.
     * @param tourId ID of the tour to be reserved.
     * @return true if the reservation is successful, false otherwise.
     */
    public boolean reserveTour(String reference, String name, int tourId) {
        // Check if the user is already signed up for this tour
        List<Reservation> tourReservations = reservationsByTour.getOrDefault(tourId, new ArrayList<>());
        for (Reservation reservation : tourReservations) {
            if (reservation.getReference().equals(reference)) {
                return false; // User already signed up for this tour
            }
        }

        // Reserve the tour if not already signed up and spots are available
        for (Tour tour : tours) {
            if (tour.getId() == tourId && tour.getReserved() < tour.getPlaces()) {
                tour.setReserved(tour.getReserved() + 1);

                // Create the reservation and add it to both mappings
                Reservation newReservation = new Reservation(reference, name);
                reservations.put(reference, newReservation);
                tourReservations.add(newReservation);
                reservationsByTour.put(tourId, tourReservations);

                return true;
            }
        }

        return false;
    }

    /**
     * Cancels a reservation by booking reference.
     * Removes the reservation from both mappings and updates the tour's reserved count.
     * @param reference Guest's booking reference.
     * @return true if the cancellation is successful, false otherwise.
     */
    public boolean cancelReservation(String reference) {
        Reservation reservation = reservations.remove(reference); // Remove from reservations map
        if (reservation != null) {
            // Find and remove the reservation from the tour-specific list
            for (Map.Entry<Integer, List<Reservation>> entry : reservationsByTour.entrySet()) {
                List<Reservation> tourReservations = entry.getValue();
                if (tourReservations.removeIf(r -> r.getReference().equals(reference))) {
                    // Update the reserved count for the tour
                    for (Tour tour : tours) {
                        if (tour.getId() == entry.getKey()) {
                            tour.setReserved(tour.getReserved() - 1);
                            return true;
                        }
                    }
                }
            }
        }
        return false; // Cancellation Failed
    }
    /**
     * Retrieves all reservations for a specific tour by its ID.
     * @param tourId ID of the tour.
     * @return List of reservations for the tour.
     */
    public List<Reservation> getReservationsByTour(int tourId) {
        return reservationsByTour.getOrDefault(tourId, new ArrayList<>());
    }

    /**
     * Creates a new tour and adds it to the list if the ID does not already exist.
     * @param id Tour ID.
     * @param description Tour description.
     * @param date Tour date.
     * @param cost Tour cost.
     * @param places Maximum number of guests allowed on the tour.
     * @return true if the tour is successfully created, false otherwise.
     */
    public boolean createTour(int id, String description, String date, int cost, int places) {
        // Check if a tour with the same ID already exists
        for (Tour tour : tours) {
            if (tour.getId() == id) {
                return false; // Tour with this ID already exists
            }
        }
        // Add the new tour
        tours.add(new Tour(id, description, date, cost, places));
        return true;
    }
    /**
     * Updates the details of an existing tour by its ID.
     * @param id Tour ID.
     * @param description Updated description.
     * @param date Updated date.
     * @param cost Updated cost.
     * @param places Updated maximum number of places.
     * @return true if the update is successful, false otherwise.
     */
    public boolean updateTour(int id, String description, String date, int cost, int places) {
        for (Tour tour : tours) {
            if (tour.getId() == id) {
                // Update the tour's attributes
                tour.setDescription(description);
                tour.setDate(date);
                tour.setCost(cost);
                tour.setPlaces(places);
                return true;
            }
        }
        return false; // Tour not found
    }

    /**
     * Deletes a tour by its ID.
     * @param id Tour ID.
     * @return true if the tour is successfully deleted, false otherwise.
     */
    public boolean deleteTour(int id) {
        return tours.removeIf(tour -> tour.getId() == id);
    }


/*    public boolean validateReservations(int tourId) {
        // NON FUNCTIONAL: Code is placeholder
        List<Reservation> tourReservations = reservationsByTour.getOrDefault(tourId, new ArrayList<>());
        for (Reservation reservation : tourReservations) {
            reservations.remove(reservation.getReference());
        }
        reservationsByTour.remove(tourId);
        return true;
    }

    public boolean finalizeReservations(int tourId) {
        List<Reservation> tourReservations = reservationsByTour.getOrDefault(tourId, new ArrayList<>());
        Tour targetTour = null;
        for (Tour tour : tours) {
            if (tour.getId() == tourId) {
                targetTour = tour;
                break;
            }
        }
        if (targetTour == null) return false; // Tour not found

        // If overbooked, cancel excess reservations (last-in-first-out policy)
        while (tourReservations.size() > targetTour.getPlaces()) {
            Reservation removed = tourReservations.remove(tourReservations.size() - 1);
            reservations.remove(removed.getReference());
        }
        reservationsByTour.put(tourId, tourReservations);
        return true;
    }*/
}