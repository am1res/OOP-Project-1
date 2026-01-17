package entity;

public class Truck {
    private int id;
    private String brand;
    private String model;
    private int year;
    private double price;
    private boolean isAvailable;

    public Truck(int id, String brand, String model, int year, double price, boolean isAvailable) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    public int getId() { return id; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public double getPrice() { return price; }
    public boolean isAvailable() { return isAvailable; }
}

