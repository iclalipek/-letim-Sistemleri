package com.flightreservation;

import java.util.List;

public class Flight {
    private String flightId;
    private List<Seat> seats;

    public Flight(String flightId, List<Seat> seats) {
        this.flightId = flightId;
        this.seats = seats;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public Seat getSeat(int seatNumber) {
        for (Seat seat : seats) {
            if (seat.getSeatNumber() == seatNumber) {
                return seat;
            }
        }
        return null; // Koltuk bulunamadı
    }
}
