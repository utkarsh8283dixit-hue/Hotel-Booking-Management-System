package com.hotelbooking;

import java.time.LocalDate;

public class Booking {

    private int bookingId;
    private String guestName;
    private String phone;
    private String roomType;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private double roomPrice;
    private double totalCost;

    public Booking() {
    }

    public Booking(String guestName, String phone, String roomType,
                   LocalDate checkIn, LocalDate checkOut,
                   double roomPrice) {

        this.guestName = guestName;
        this.phone = phone;
        this.roomType = roomType;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.roomPrice = roomPrice;

        long nights = java.time.temporal.ChronoUnit.DAYS.between(
                checkIn, checkOut
        );

        this.totalCost = nights * roomPrice;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(double roomPrice) {
        this.roomPrice = roomPrice;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return "Booking ID: " + bookingId +
                "\nGuest Name: " + guestName +
                "\nPhone: " + phone +
                "\nRoom Type: " + roomType +
                "\nCheck-in: " + checkIn +
                "\nCheck-out: " + checkOut +
                "\nRoom Price: ₹" + roomPrice +
                "\nTotal Cost: ₹" + totalCost;
    }
}