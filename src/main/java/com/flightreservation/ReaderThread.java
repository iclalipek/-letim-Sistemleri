package com.flightreservation;

public class ReaderThread extends Thread {
    private ReservationSystem system;
    private String flightId;

    public ReaderThread(ReservationSystem system, String flightId) {
        this.system = system;
        this.flightId = flightId;
    }

    @Override
    public void run() {
        system.queryReservation(flightId);
    }
}
