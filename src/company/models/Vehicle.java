package company.models;

public class Vehicle {
    private int id;
    private NewUser owner;
    private Category category;
    private String type;
    private String brand;
    private String model;
    private int year;
    private double price;
    private boolean isAvailable;

    public Vehicle(int id, NewUser owner, Category category, String type, String brand, String model,
                   int year, double price, boolean isAvailable) {
        this.id = id;
        this.owner = owner;
        this.category = category;
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public NewUser getOwner() { return owner; }
    public void setOwner(NewUser owner) { this.owner = owner; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", owner=" + owner.getName() + " " + owner.getSurname() +
                ", category=" + category.getName() +
                ", type='" + type + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", price=" + price +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
