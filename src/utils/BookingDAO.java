package src.utils;

import src.models.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public List<Booking> getAllBookings(String username) {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM Booking WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Booking booking = new Booking(rs.getInt("bid"), rs.getString("username"), rs.getInt("packageID"), rs.getDate("fromDate"), rs.getDate("toDate"));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public List<Booking> getCurrentBookings(String username) {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM Booking WHERE toDate >= CURRENT_DATE() AND username = ?;";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Booking booking = new Booking(rs.getInt("bid"), rs.getString("username"), rs.getInt("packageID"), rs.getDate("fromDate"), rs.getDate("toDate"));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }

    public List<Booking> getPastBookings(String username) {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT * FROM Booking WHERE toDate < CURRENT_DATE() AND username = ?";

        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)){

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Booking booking = new Booking(rs.getInt("bid"), rs.getString("username"), rs.getInt("packageID"), rs.getDate("fromDate"), rs.getDate("toDate"));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }



    public int createBooking(Booking booking) {
        String query = "INSERT INTO Booking (username, packageID, fromDate, toDate) VALUES (?, ?, ?, ?);";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, booking.getUsername());
            pstmt.setInt(2, booking.getPackageID());
            pstmt.setDate(3, new java.sql.Date(booking.getFromDate().getTime()));
            pstmt.setDate(4, new java.sql.Date(booking.getToDate().getTime()));

            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}