package Main;
import java.io.IOException;

public class MainMenu {
	public static void main(String[] args) throws ClassNotFoundException {
    // Create an instance of your HouseManager class
    Admin admin = new Admin("maryam", "maryam", Type.Admin, "girla9182@gmail.com");
    // Test addHouse method
    admin.addHouse("123 Main St", Category.Apartment, 2000);
    admin.addHouse("858 Wow St", Category.Apartment, 7000);
    admin.addHouse("637 Meow St", Category.Cottage, 6000);
    admin.addHouse("234 Ok St", Category.Villa, 3000);
    admin.addHouse("155 Mn St", Category.Cottage, 1000);

    // Test editHouse method
    admin.editHouse(1, "456 Oak St", Category.Villa, 2500);

    // Test listHouses method
    admin.listHouses();

    // Test searchHouse method
    admin.searchHouse(1);

    // Test removeHouse method
    // Test listHouses after removing a house
    admin.listHouses();

    // Test putHouseInFile method
    try {
        admin.putHouseInFile();
    } catch (IOException e1) {
        System.out.println("Error putting houses in file: " + e1.getMessage());
    }

    // Test changeHouseFromFile method
    try {
        admin.changeHouseFromFile();
    } catch (IOException e) {
        System.out.println("Error changing houses from file: " + e.getMessage());
    }

    // Test listHouses after changing houses from file
    admin.listHouses();
}
}
