package company.models;

public class SpecialVehicle {
    private int id;
    private String brand;
    private String model;
    private int year;
    private double price;
    private boolean isAvailable;

    public SpecialVehicle(int id, String brand, String model, int year, double price, boolean isAvailable) {
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

    @Override
    public String toString() {
        return String.format(
                "%d %s %s, %d year, $%.2f, available=%s",
                id, brand, model, year, price, isAvailable
        );
    }


}