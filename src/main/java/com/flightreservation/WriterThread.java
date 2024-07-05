package com.flightreservation;

public class WriterThread extends Thread {
    private ReservationSystem system;
    private String flightId;
    private int seatNumber;
    private boolean isReservation; // True ise rezervasyon, false ise iptal

    public WriterThread(ReservationSystem system, String flightId, int seatNumber, boolean isReservation) {
        this.system = system;
        this.flightId = flightId;
        this.seatNumber = seatNumber;
        this.isReservation = isReservation;
    }

    @Override
    public void run() {
        if (isReservation) {
            system.makeReservation(flightId, seatNumber);
        } else {
            system.cancelReservation(flightId, seatNumber);
        }
    }
}
