package com.hotelbooking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    // Add Booking
    public boolean addBooking(Booking booking) {

        String sql = "INSERT INTO bookings " +
                "(guest_name, phone, room_type, check_in, check_out, room_price, total_cost) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, booking.getGuestName());
            ps.setString(2, booking.getPhone());
            ps.setString(3, booking.getRoomType());
            ps.setDate(4, Date.valueOf(booking.getCheckIn()));
            ps.setDate(5, Date.valueOf(booking.getCheckOut()));
            ps.setDouble(6, booking.getRoomPrice());
            ps.setDouble(7, booking.getTotalCost());

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Error while adding booking!");
            e.printStackTrace();
            return false;
        }
    }

    // View All Bookings
    public List<Booking> getAllBookings() {

        List<Booking> bookings = new ArrayList<>();

        String sql = "SELECT * FROM bookings";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                Booking booking = new Booking();

                booking.setBookingId(rs.getInt("booking_id"));
                booking.setGuestName(rs.getString("guest_name"));
                booking.setPhone(rs.getString("phone"));
                booking.setRoomType(rs.getString("room_type"));
                booking.setCheckIn(
                        rs.getDate("check_in").toLocalDate()
                );
                booking.setCheckOut(
                        rs.getDate("check_out").toLocalDate()
                );
                booking.setRoomPrice(
                        rs.getDouble("room_price")
                );
                booking.setTotalCost(
                        rs.getDouble("total_cost")
                );

                bookings.add(booking);
            }

        } catch (SQLException e) {
            System.out.println("Error while fetching bookings!");
            e.printStackTrace();
        }

        return bookings;
    }

    // Search Booking
    public Booking getBookingById(int bookingId) {

        String sql = "SELECT * FROM bookings WHERE booking_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, bookingId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Booking booking = new Booking();

                booking.setBookingId(
                        rs.getInt("booking_id")
                );

                booking.setGuestName(
                        rs.getString("guest_name")
                );

                booking.setPhone(
                        rs.getString("phone")
                );

                booking.setRoomType(
                        rs.getString("room_type")
                );

                booking.setCheckIn(
                        rs.getDate("check_in").toLocalDate()
                );

                booking.setCheckOut(
                        rs.getDate("check_out").toLocalDate()
                );

                booking.setRoomPrice(
                        rs.getDouble("room_price")
                );

                booking.setTotalCost(
                        rs.getDouble("total_cost")
                );

                return booking;
            }

        } catch (SQLException e) {
            System.out.println("Error while searching booking!");
            e.printStackTrace();
        }

        return null;
    }

    // Cancel Booking
    public boolean cancelBooking(int bookingId) {

        String sql = "DELETE FROM bookings WHERE booking_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, bookingId);

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Error while cancelling booking!");
            e.printStackTrace();
            return false;
        }
    }
}