package com.flightreservation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationDatabase {
    private Map<String, Flight> flights;

    public ReservationDatabase() {
        this.flights = new HashMap<>();
    }
    
    // Uçuşları veritabanına ekleme metodu
    public void addFlight(Flight flight) {
        flights.put(flight.getFlightId(), flight);
    }

    public void loadFlights(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                String flightId = parts[0];
                int seatCount = Integer.parseInt(parts[1]);
                List<Seat> seats = new ArrayList<>();
                for (int i = 1; i <= seatCount; i++) {
                    seats.add(new Seat(i));
                }
                Flight flight = new Flight(flightId, seats);
                flights.put(flightId, flight);
            }
        }
    }

    public void loadSeats(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                String flightId = parts[0];
                int seatNumber = Integer.parseInt(parts[1]);
                Flight flight = flights.get(flightId);
                if (flight != null) {
                    Seat seat = flight.getSeat(seatNumber);
                    if (seat != null) {
                        seat.setReserved(false); // Varsayılan olarak boş koltuk
                    }
                }
            }
        }
    }
    // Koltuk rezervasyonu yapma metodu
    public void reserveSeat(String flightId, int seatNumber) {
        Flight flight = flights.get(flightId);
        if (flight != null) {
            Seat seat = flight.getSeat(seatNumber);
            if (seat != null && !seat.isReserved()) {
                seat.setReserved(true);
                System.out.println("Seat " + seatNumber + " on flight " + flightId + " reserved successfully.");
            } else {
                System.out.println("Seat " + seatNumber + " on flight " + flightId + " is already reserved or does not exist.");
            }
        } else {
            System.out.println("Flight " + flightId + " does not exist.");
        }
    }

    // Rezervasyon iptali metodu
    public void cancelSeat(String flightId, int seatNumber) {
        Flight flight = flights.get(flightId);
        if (flight != null) {
            Seat seat = flight.getSeat(seatNumber);
            if (seat != null && seat.isReserved()) {
                seat.setReserved(false);
                System.out.println("Seat " + seatNumber + " on flight " + flightId + " reservation cancelled successfully.");
            } else {
                System.out.println("Seat " + seatNumber + " on flight " + flightId + " is not reserved or does not exist.");
            }
        } else {
            System.out.println("Flight " + flightId + " does not exist.");
        }
    }

    // Koltuk sorgulama metodu
    public void querySeats(String flightId) {
        Flight flight = flights.get(flightId);
        if (flight != null) {
            System.out.println("Seats on flight " + flightId + ":");
            for (Seat seat : flight.getSeats()) {
                System.out.println("Seat " + seat.getSeatNumber() + ": " + (seat.isReserved() ? "Reserved" : "Available"));
            }
        } else {
            System.out.println("Flight " + flightId + " does not exist.");
        }
    }
}
