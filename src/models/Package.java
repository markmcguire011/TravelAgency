package models;

public class Package {
    private int pid;
    private int hotelID;
    private int flight;
    private String city;
    private double price;
    
    public Package(int pid, int hotelID, int flight, String city, double price) {
        this.pid = pid;
        this.hotelID = hotelID;
        this.flight = flight;
        this.city = city;
        this.price = price;
    }

    public int getPid() {
        return pid;
    }

    public int getHotelID() {
        return hotelID;
    }

    public int getFlight() {
        return flight;
    }

    public String getCity() {
        return city;
    }

    public double getPrice() {
        return price;
    }
}
