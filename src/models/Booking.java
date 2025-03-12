package models;

import java.util.Date;

public class Booking {
    private int bid;
    private String username;
    private int packageID;
    private Date fromDate;
    private Date toDate;

    public Booking(int bid, String username, int packageID, Date fromDate, Date toDate) {
        this.bid = bid;
        this.username = username;
        this.packageID = packageID;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    // Getters
    public int getBid() {
        return bid;
    }

    public String getUsername() {
        return username;
    }

    public int getPackageID() {
        return packageID;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public Date getToDate() {
        return toDate;
    }
}