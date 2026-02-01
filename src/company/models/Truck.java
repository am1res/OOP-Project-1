package company.models;

public class Truck {
    private int id;
    private NewUser owner;
    private Category category;
    private String type;
    private String brand;
    private String model;
    private int year;
    private double price;
    private boolean isAvailable;

    public Truck(int id, NewUser owner, Category category, String type, String brand, String model,
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
    public NewUser getOwner() { return owner; }
    public Category getCategory() { return category; }
    public String getType() { return type; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public double getPrice() { return price; }
    public boolean isAvailable() { return isAvailable; }

    @Override
    public String toString() {
        return "Truck{" +
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

