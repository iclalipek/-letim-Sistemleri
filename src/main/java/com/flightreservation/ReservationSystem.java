package com.flightreservation;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReservationSystem {
    private ReservationDatabase database;
    private ReentrantReadWriteLock lock;

    public ReservationSystem() {
        this.database = new ReservationDatabase();
        this.lock = new ReentrantReadWriteLock(true); // Adil kilitleme kullanımı
    }

    public void initializeSystem() {
        try {
            database.loadFlights("resources/flights.txt");
            database.loadSeats("resources/seats.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startSimulation() {
        // Reader ve Writer thread'lerini oluşturur ve başlatır
        ReaderThread reader1 = new ReaderThread(this, "Ankara-İstanbul");
        ReaderThread reader2 = new ReaderThread(this, "Trabzon-Ankara");
        WriterThread writer1 = new WriterThread(this, "Ankara-İstanbul", 1, true);
        WriterThread writer2 = new WriterThread(this, "Trabzon-Ankara", 1, true);

        reader1.start();
        reader2.start();
        writer1.start();
        writer2.start();
    }

    public void makeReservation(String flightId, int seatNumber) {
        lock.writeLock().lock();
        try {
            database.reserveSeat(flightId, seatNumber);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void cancelReservation(String flightId, int seatNumber) {
        lock.writeLock().lock();
        try {
            database.cancelSeat(flightId, seatNumber);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void queryReservation(String flightId) {
        lock.readLock().lock();
        try {
            database.querySeats(flightId);
        } finally {
            lock.readLock().unlock();
        }
    }
}
