package utils;

import models.Transaction;
import java.sql.*;

public class TransactionDAO {
    public Transaction createTransaction(int bookingID, String creditCardNumber, double amount) {
        String query = "INSERT INTO Transaction (bookingID, creditCardNumber, amount) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, bookingID);
            pstmt.setString(2, creditCardNumber);
            pstmt.setDouble(3, amount);
            
            pstmt.executeUpdate();
            
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return new Transaction(rs.getInt(1), bookingID, creditCardNumber, amount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
} 