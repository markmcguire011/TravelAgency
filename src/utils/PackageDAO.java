package utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import models.Package;

public class PackageDAO {
    public List<Package> getAllPackages() {
        List<Package> packages = new ArrayList<>();
        String query = "SELECT * FROM Package";
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                packages.add(new Package(rs.getInt("pid"), rs.getInt("hid"), rs.getInt("fid"), rs.getString("city"),
                        rs.getDouble("price")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return packages;
    }

    public Package getPackageById(int pid) {
        String query = "SELECT * FROM Package WHERE pid = ?";
        try (PreparedStatement pstmt = DatabaseConnection.getConnection().prepareStatement(query)) {
            pstmt.setInt(1, pid);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Package(rs.getInt("pid"), rs.getInt("hid"), rs.getInt("fid"), rs.getString("city"),
                        rs.getDouble("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
