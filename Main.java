package com.hotelbooking;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static BookingDAO bookingDAO = new BookingDAO();

    public static void main(String[] args) {

        showWelcome();

        while (true) {

            showMenu();

            System.out.print("Enter your choice: ");

            int choice;

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                printError("Please enter a valid number.");
                continue;
            }

            switch (choice) {

                case 1:
                    addBooking();
                    break;

                case 2:
                    viewBookings();
                    break;

                case 3:
                    searchBooking();
                    break;

                case 4:
                    cancelBooking();
                    break;

                case 5:
                    exitSystem();
                    return;

                default:
                    printError("Invalid choice. Please select 1-5.");
            }
        }
    }

    // Welcome Screen
    private static void showWelcome() {

        System.out.println();
        System.out.println("============================================================");
        System.out.println("             HOTEL BOOKING MANAGEMENT SYSTEM");
        System.out.println("============================================================");
        System.out.println("             Java + MySQL + JDBC Application");
        System.out.println("============================================================");
        System.out.println();
        System.out.println("              Welcome to Hotel Booking System");
        System.out.println("        Manage bookings quickly, safely and efficiently.");
        System.out.println();
    }

    // Main Menu
    private static void showMenu() {

        System.out.println();
        System.out.println("------------------------------------------------------------");
        System.out.println("                    MAIN MENU");
        System.out.println("------------------------------------------------------------");
        System.out.println("  [1]  Add New Booking");
        System.out.println("  [2]  View All Bookings");
        System.out.println("  [3]  Search Booking");
        System.out.println("  [4]  Cancel Booking");
        System.out.println("  [5]  Exit");
        System.out.println("------------------------------------------------------------");
    }

    // Add Booking
    private static void addBooking() {

        System.out.println();
        System.out.println("============================================================");
        System.out.println("                    ADD NEW BOOKING");
        System.out.println("============================================================");

        System.out.print("Guest Name       : ");
        String guestName = scanner.nextLine();

        System.out.print("Phone Number     : ");
        String phone = scanner.nextLine();

        System.out.println();
        System.out.println("Available Room Types");
        System.out.println("------------------------------------------------------------");
        System.out.println("  [1] Standard Room       - Rs. 1500 / night");
        System.out.println("  [2] Deluxe Room         - Rs. 2500 / night");
        System.out.println("  [3] Suite Room          - Rs. 4000 / night");
        System.out.println("------------------------------------------------------------");

        System.out.print("Select Room Type : ");

        int roomChoice;

        try {
            roomChoice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            printError("Invalid room selection.");
            return;
        }

        String roomType;
        double roomPrice;

        switch (roomChoice) {

            case 1:
                roomType = "Standard";
                roomPrice = 1500;
                break;

            case 2:
                roomType = "Deluxe";
                roomPrice = 2500;
                break;

            case 3:
                roomType = "Suite";
                roomPrice = 4000;
                break;

            default:
                printError("Invalid room type.");
                return;
        }

        LocalDate checkIn;
        LocalDate checkOut;

        try {

            System.out.print("Check-in Date    : ");
            checkIn = LocalDate.parse(scanner.nextLine());

            System.out.print("Check-out Date   : ");
            checkOut = LocalDate.parse(scanner.nextLine());

        } catch (DateTimeParseException e) {

            printError("Invalid date format.");
            System.out.println("Please use: YYYY-MM-DD");
            return;
        }

        if (!checkOut.isAfter(checkIn)) {
            printError("Check-out date must be after check-in date.");
            return;
        }

        Booking booking = new Booking(
                guestName,
                phone,
                roomType,
                checkIn,
                checkOut,
                roomPrice
        );

        boolean success = bookingDAO.addBooking(booking);

        if (success) {

            System.out.println();
            System.out.println("============================================================");
            System.out.println("                 BOOKING CONFIRMED");
            System.out.println("============================================================");

            System.out.printf("%-20s : %s%n", "Guest Name", guestName);
            System.out.printf("%-20s : %s%n", "Phone", phone);
            System.out.printf("%-20s : %s%n", "Room Type", roomType);
            System.out.printf("%-20s : %s%n", "Check-in", checkIn);
            System.out.printf("%-20s : %s%n", "Check-out", checkOut);
            System.out.printf("%-20s : Rs. %.2f%n", "Room Price", roomPrice);
            System.out.printf("%-20s : Rs. %.2f%n",
                    "TOTAL COST", booking.getTotalCost());

            System.out.println("------------------------------------------------------------");
            System.out.println("           Booking saved successfully in database.");
            System.out.println("============================================================");

        } else {

            printError("Unable to save booking.");
        }
    }

    // View All Bookings
    private static void viewBookings() {

        System.out.println();
        System.out.println("============================================================");
        System.out.println("                    ALL BOOKINGS");
        System.out.println("============================================================");

        List<Booking> bookings = bookingDAO.getAllBookings();

        if (bookings.isEmpty()) {

            System.out.println();
            System.out.println("              No bookings available.");
            System.out.println();

        } else {

            System.out.println("Total Bookings: " + bookings.size());
            System.out.println();

            for (Booking booking : bookings) {

                System.out.println("------------------------------------------------------------");
                System.out.println("Booking ID      : " + booking.getBookingId());
                System.out.println("Guest Name      : " + booking.getGuestName());
                System.out.println("Phone           : " + booking.getPhone());
                System.out.println("Room Type       : " + booking.getRoomType());
                System.out.println("Check-in        : " + booking.getCheckIn());
                System.out.println("Check-out       : " + booking.getCheckOut());
                System.out.printf("Room Price      : Rs. %.2f%n",
                        booking.getRoomPrice());
                System.out.printf("Total Cost      : Rs. %.2f%n",
                        booking.getTotalCost());
            }

            System.out.println("------------------------------------------------------------");
        }
    }

    // Search Booking
    private static void searchBooking() {

        System.out.println();
        System.out.println("============================================================");
        System.out.println("                    SEARCH BOOKING");
        System.out.println("============================================================");

        System.out.print("Enter Booking ID : ");

        try {

            int bookingId = Integer.parseInt(scanner.nextLine());

            Booking booking = bookingDAO.getBookingById(bookingId);

            if (booking != null) {

                System.out.println();
                System.out.println("---------------- BOOKING DETAILS ----------------");

                System.out.println("Booking ID      : " + booking.getBookingId());
                System.out.println("Guest Name      : " + booking.getGuestName());
                System.out.println("Phone           : " + booking.getPhone());
                System.out.println("Room Type       : " + booking.getRoomType());
                System.out.println("Check-in        : " + booking.getCheckIn());
                System.out.println("Check-out       : " + booking.getCheckOut());

                System.out.printf("Room Price      : Rs. %.2f%n",
                        booking.getRoomPrice());

                System.out.printf("Total Cost      : Rs. %.2f%n",
                        booking.getTotalCost());

                System.out.println("-----------------------------------------------");

            } else {

                printError("No booking found with ID: " + bookingId);
            }

        } catch (NumberFormatException e) {

            printError("Invalid Booking ID.");
        }
    }

    // Cancel Booking
    private static void cancelBooking() {

        System.out.println();
        System.out.println("============================================================");
        System.out.println("                    CANCEL BOOKING");
        System.out.println("============================================================");

        System.out.print("Enter Booking ID : ");

        try {

            int bookingId = Integer.parseInt(scanner.nextLine());

            Booking booking = bookingDAO.getBookingById(bookingId);

            if (booking == null) {

                printError("Booking not found.");
                return;
            }

            System.out.println();
            System.out.println("Booking found for: " + booking.getGuestName());
            System.out.println("Room Type       : " + booking.getRoomType());
            System.out.println("Total Cost      : Rs. " +
                    String.format("%.2f", booking.getTotalCost()));

            System.out.println();
            System.out.print("Confirm cancellation (Y/N): ");

            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("Y")) {

                boolean success = bookingDAO.cancelBooking(bookingId);

                if (success) {

                    System.out.println();
                    System.out.println("============================================================");
                    System.out.println("              BOOKING CANCELLED SUCCESSFULLY");
                    System.out.println("============================================================");
                    System.out.println("Booking ID: " + bookingId);
                    System.out.println("Guest    : " + booking.getGuestName());
                    System.out.println("============================================================");

                } else {

                    printError("Unable to cancel booking.");
                }

            } else {

                System.out.println();
                System.out.println("Cancellation aborted.");
            }

        } catch (NumberFormatException e) {

            printError("Invalid Booking ID.");
        }
    }

    // Error Message
    private static void printError(String message) {

        System.out.println();
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("ERROR: " + message);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    // Exit
    private static void exitSystem() {

        System.out.println();
        System.out.println("============================================================");
        System.out.println("          THANK YOU FOR USING OUR SYSTEM");
        System.out.println("============================================================");
        System.out.println("        Hotel Booking Management System");
        System.out.println("                 Have a nice day!");
        System.out.println("============================================================");
    }
}