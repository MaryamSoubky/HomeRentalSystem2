package Main;
import java.io.Serializable;

enum Category {
    Apartment,
    Villa,
    Cottage
}

public class House implements Serializable{
    private static int nextID = 0;
    private int houseID;
    private String address;
    private Category category;
    private int numberOfRentals = 0;
    private int price;
    private int revenue = 0;
    private boolean available = true;

    // Constructor
    public House(String address, Category category, int price) {
        this.houseID = nextID++;
        this.category = category;
        this.price = price;
    }
    
    // Setter and getter methods
    public int getHouseID() {
        return houseID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getNumberOfRentals() {
        return numberOfRentals;
    }

    public void setNumberOfRentals(int numberOfRentals) {
        this.numberOfRentals = numberOfRentals;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue += revenue;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
    
    // Methods
    public boolean viewAvailability() {
       if (numberOfRentals > 0 && revenue > 1000) {
        return true;
    } else {
        return false;
    }
    }

    public int checkRevenue(int houseID) {
          if (this.houseID == houseID) {
        int calculatedRevenue = numberOfRentals * price;
        setRevenue(calculatedRevenue);
        return calculatedRevenue;
    } else {
        System.out.println("House not found with ID: " + houseID);
        return -1;
    }
   
       
}
}
