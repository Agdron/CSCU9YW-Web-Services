package com.example.ResortBooking.controller;


import com.example.ResortBooking.model.Reservation;
import com.example.ResortBooking.model.Tour;
import com.example.ResortBooking.service.TourService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TourController handles API endpoints for managing tours and reservations.
 * Routes are prefixed with "/api".
 */

@RestController
@RequestMapping("/api")
public class TourController {

    private final TourService tourService;

    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    /**
     * GET /tours - Fetch all available tours.
     * @return List of all tours.
     */
    @GetMapping("/tours")
    public List<Tour> getAllTours() {
        return tourService.getAllTours();
    }

    /**
     * POST /reserve - Reserve a tour for a guest.
     * @param reference Booking reference of the guest.
     * @param name Name of the guest.
     * @param tourId ID of the tour to reserve.
     * @return Success or failure message.
     */
    @PostMapping("/reserve")
    public String reserveTour(@RequestParam String reference,
                              @RequestParam String name,
                              @RequestParam int tourId) {
        System.out.println("Received reference: " + reference); // TODO: REMOVE DEBUG MESSAGES: Only here to test method body as all fundamentally work the same
        System.out.println("Received name: " + name);
        System.out.println("Received tourId: " + tourId);
        return tourService.reserveTour(reference, name, tourId)
                ? "Reservation successful!"
                : "Reservation failed! Tour is full or invalid tour ID.";
    }

    /**
     * DELETE /cancel/{reference} - Cancel a reservation by booking reference.
     * @param reference Booking reference of the reservation to cancel.
     * @return Success or failure message.
     */
    @DeleteMapping("/cancel/{reference}")
    public String cancelReservation(@PathVariable String reference) {
        return tourService.cancelReservation(reference)
                ? "Reservation canceled successfully!"
                : "Cancellation failed! Booking reference not found.";
    }

    /**
     * GET /reservations/{tourId} - Get reservations for a specific tour.
     * @param tourId ID of the tour.
     * @return List of reservations for the tour.
     */
    @GetMapping("/reservations/{tourId}")
    public List<Reservation> getReservationsByTour(@PathVariable int tourId) {
        return tourService.getReservationsByTour(tourId);
    }

    //Features below are only used in the Admin Client. Not all are fully functional due to time limitations
    //Attempts made for marking purposes

    /**
     * POST /tours - Create a new tour.
     * @param id Tour ID.
     * @param description Tour description.
     * @param date Tour date.
     * @param cost Tour cost.
     * @param places Maximum number of guests allowed.
     * @return Success or failure message.
     */
    @PostMapping("/tours")
    public String createTour(@RequestParam int id,
                             @RequestParam String description,
                             @RequestParam String date,
                             @RequestParam int cost,
                             @RequestParam int places) {
        return tourService.createTour(id, description, date, cost, places)
                ? "Tour created successfully!"
                : "Failed to create tour. Tour with this ID already exists.";
    }

    /**
     * PUT /tours/{id} - Update an existing tour.
     * @param id Tour ID.
     * @param description Updated description.
     * @param date Updated date.
     * @param cost Updated cost.
     * @param places Updated maximum places.
     * @return Success or failure message.
     */
    @PutMapping("/tours/{id}")
    public String updateTour(@PathVariable int id,
                             @RequestParam String description,
                             @RequestParam String date,
                             @RequestParam int cost,
                             @RequestParam int places) {
        return tourService.updateTour(id, description, date, cost, places)
                ? "Tour updated successfully!"
                : "Failed to update tour. Tour not found.";
    }


    /**
     * DELETE /tours/{id} - Delete a tour by ID.
     * @param id Tour ID.
     * @return Success or failure message.
     */
    @DeleteMapping("/tours/{id}")
    public String deleteTour(@PathVariable int id) {
        return tourService.deleteTour(id)
                ? "Tour deleted successfully!"
                : "Failed to delete tour. Tour not found.";
    }


/*    *//**
     *
     * CURRENTLY LIMITED FUNCTIONALITY
     *//*
    @PostMapping("/validate/{tourId}")
    public String validateReservations(@PathVariable int tourId) {
        return tourService.validateReservations(tourId)
                ? "Reservations validated and updated."
                : "Failed to validate reservations.";
    }


    @PostMapping("/finalize/{tourId}")
    public String finalizeReservations(@PathVariable int tourId) {
        return tourService.finalizeReservations(tourId)
                ? "Reservations finalized successfully."
                : "Failed to finalize reservations.";
    }*/
}