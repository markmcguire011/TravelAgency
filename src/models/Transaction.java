package models;

public class Transaction {
    private int tid;
    private int bookingID;
    private String creditCardNumber;
    private double amount;

    public Transaction(int tid, int bookingID, String creditCardNumber, double amount) {
        this.tid = tid;
        this.bookingID = bookingID;
        this.creditCardNumber = creditCardNumber;
        this.amount = amount;
    }

    public int getTid() { return tid; }
    public int getBookingID() { return bookingID; }
    public String getCreditCardNumber() { return creditCardNumber; }
    public double getAmount() { return amount; }
} 