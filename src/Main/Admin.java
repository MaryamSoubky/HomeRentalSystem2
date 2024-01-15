package Main;
import java.util.ArrayList;
import java.nio.file.*;
import java.io.IOException;
import java.util.Date;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Admin extends User implements Serializable {
    private String username;
    private String password;
    private Type type;
    private static ArrayList<User> users = new ArrayList<User>();
    private static ArrayList<House> houses = new ArrayList <House>();
    private static ArrayList<Booking> bookings =  new ArrayList <Booking>();
    private static ArrayList<Renter> renters =  new ArrayList <Renter>();
    private static ArrayList<Receptionist> receptionists = new ArrayList<Receptionist>();

    // Constructor
    public Admin(String name, String pass, Type type, String email) {
    super(email, pass);
    this.type = Type.Admin;
    }


	// Methods
    public void addHouse(String address, Category category, int price){
    	try {
        House house = new House(address, category, price);
        houses.add(house);
        System.out.println("House added successfully.");
    	} catch (Exception e) {
        System.out.println("Error adding house: " + e.getMessage());
    	}
    }
    
    public boolean editHouse(int houseID, String address, Category newCategory, int newPrice){
    	try {
        for(House house : houses){
            if(house.getHouseID() == houseID){
                house.setAddress(address);
                house.setCategory(newCategory);
                house.setPrice(newPrice);
                return true;
            }
       }
        System.out.println("House not found!");
    	}catch (Exception e) {
            System.out.println("Error editing house: " + e.getMessage());
        }
        return false;
    }
    
    public boolean removeHouse(int houseID){
    	try {
        for(House house: houses){
            if(house.getHouseID() == houseID){
                houses.remove(house);
                System.out.println("House removed successfully.");
                return true;
            }
        }
        System.out.println("Error: House not found");
        } catch (Exception e) {
            System.out.println("Error removing house: " + e.getMessage());
        }
        return false;
    }
    
    public static void listHouses(){
    	try {
        System.out.println("Houses List:");
        for(House house: houses){
            System.out.println("House ID: " + house.getHouseID() + ", Catgory: " + house.getCategory() + ", Number of Rentals: " + house.getNumberOfRentals() + ", Price: " + house.getPrice() + ", Revenue: " + house.getRevenue() + ", Availability: " + house.isAvailable());
        }
        } catch (Exception e) {
            System.out.println("Error listing houses: " + e.getMessage());
        }
    }
    
    public boolean searchHouse(int houseID){
        for(House house : houses){
            if(house.getHouseID() == houseID){
                System.out.println("House is found!");
                System.out.println("House ID: " + house.getHouseID() + ", Catgory: " + house.getCategory() + ", Number of Rentals: " + house.getNumberOfRentals() + ", Price: " + house.getPrice() + ", Revenue: " + house.getRevenue() + ", Availability: " + house.isAvailable());
                return true;
            }
        }
        return false;
    }
    
    public static void putHouseInFile() throws IOException {
    	try {
    		FileOutputStream i = new FileOutputStream("C:\\Users\\soubk\\eclipse-workspace\\HomeRentalSystem\\src\\Houses.dat");
    		ObjectOutputStream in = new ObjectOutputStream(i);
    		System.out.println(houses.toString());
    		in.writeObject(houses);
    		in.close();
    		i.close();
    	} catch (IOException e) {
        System.out.println("Error putting houses in file: " + e.getMessage());
    	}
    }
    
    public static void changeHouseFromFile() throws FileNotFoundException, IOException, ClassNotFoundException{
    	try {
    		FileInputStream i = new FileInputStream("C:\\Users\\soubk\\eclipse-workspace\\HomeRentalSystem\\src\\Houses.dat");
    		ObjectInputStream in =  new ObjectInputStream(i);
    		try {
    			houses = (ArrayList<House>) in.readObject();
    			in.close();
    			} catch (ClassNotFoundException ec) {
    				Logger.getLogger(House.class.getName()).log(Level.SEVERE, null, ec);
    				in.close();
    		}
    	} catch (IOException e) {
            System.out.println("Error changing houses from file: " + e.getMessage());
        }
    }
    
    public void viewHouseCategory(int houseID){
    	try {
    		for(House house : houses){
    			if(house.getHouseID() == houseID){
    				System.out.println("The house is a " + house.getCategory());
    				return;
    			}
    		} 
    		System.out.println("House not found!");
        } catch (Exception e) {
            System.out.println("Error viewing house category: " + e.getMessage());
        }
    }

    public int viewNumOfRentalsOverTime(int houseID, java.util.Date startDate, java.util.Date endDate){
    	try {
    		int numOfRentals = 0;
    		for (Booking booking : bookings){
    			if (booking.getHouseID() == houseID && isDateInRange(booking, startDate, endDate)) {
    				numOfRentals++;
    			}
    		}
    		System.out.println("Number of rentals for the house " + houseID + "is " +  numOfRentals + " from the date: " + startDate + " to: " + endDate);
    	} catch (Exception e) {
            System.out.println("Error viewing the number of rentals: " + e.getMessage());
        }
    	return -1;
    }

    public boolean isDateInRange(Booking booking, Date startDate, Date endDate) {
    	try {
    		return !booking.getStartDate().after(endDate) && !booking.getEndDate().before(startDate);
    	} catch (Exception e) {
            System.out.println("Error in checking the dates: " + e.getMessage());
        }
    	return false;
    }
    
    public void mostRentedHouse(java.util.Date startDate, java.util.Date endDate){
    	try {
    		int maxRentals = 0;
    		House mostRentedHouse = null;
    		for(House house : houses){
    			int rentals = 0;
    			rentals = viewNumOfRentalsOverTime(house.getHouseID(), startDate, endDate);
    			if(rentals > maxRentals){
    				maxRentals = rentals;
    				mostRentedHouse = house;
    			}
    		}
    		if(mostRentedHouse != null){
    			System.out.println("The most rented house is " + mostRentedHouse.getHouseID() + " with " + maxRentals + " rentals between " + startDate + " and " + endDate);
    		} else {
    			System.out.println("No house has been rented between " + startDate + " and " + endDate);
    		}
    	} catch (Exception e) {
            System.out.println("Error viewing the mosted rented house: " + e.getMessage());
        }
    }
    
    public House maxRevenueHouse(java.util.Date startDate, java.util.Date endDate){
    	try {
    		if (houses.isEmpty()) {
    			System.out.println("There are no houses");
    			return null;
    		}
    		int maxRevenue = -1;
    		House maxRevHouse = null;
    		for(House house: houses){
    			int rentals = viewNumOfRentalsOverTime(house.getHouseID(), startDate, endDate);
    			int rev = rentals * house.getPrice();
    			if(maxRevenue<rev){
    				maxRevenue = rev;
    				maxRevHouse = house;
    			}
    		}
    		return maxRevHouse;
    	} catch (Exception e) {
            System.out.println("Error viewing the house with maximum revenue: " + e.getMessage());
        }
    	return null;
    }
    
    public void addUser(String email, String password){
    	try {
    		User user = new User(email, password);
    		users.add(user);
    		System.out.println("New user added successfully.");
    	} catch (Exception e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
    }
    
    public boolean editUser(String email, String newName, String newEmail, String newPassword){
    	try {
    		for(User user : users){
    			if(user.getEmail().equals(email)){
    				user.setEmail(newEmail);
    				user.setPassword(newPassword);
    				return true;
    			}
    		}
    		System.out.println("Error: User not found!");
    		return false;
    	} catch (Exception e) {
            System.out.println("Error editing the user: " + e.getMessage());
        }
    	return false;
    }
    
    public boolean removeUser(String email){
    	try {
    		for(User user: users){
    			if(user.getEmail().equals(email)){
    				users.remove(user);
    				System.out.println("User removed successfully.");
    				return true;
    			}
    		}
    		System.out.println("Error: User not found!");
    		return false;
    	} catch (Exception e) {
            System.out.println("Error removing the user: " + e.getMessage());
        }
    	return false;
    }
    
    public static void listUsers(){
    	try {
    		System.out.println("Users List:");
    		for(User user: users){
    			System.out.println("Email: " + user.getEmail() + ", Type: " + user.getType());
    		}
    	} catch (Exception e) {
            System.out.println("Error listing the users: " + e.getMessage());
        }
    }
    
    public boolean searchUser(String email) {
    	try {
    		for(User user : users){
    			if(user.getEmail().equals(email)){
    				System.out.println("User is found!");
    				System.out.println("User email: " + user.getEmail() + ", User type: " + user.getType());
    				return true;
    			}
    		}
    		System.out.println("Error: User not found!");	
    		return false;
    	} catch (Exception e) {
            System.out.println("Error searching the users: " + e.getMessage());
        }
    	return false;
    }
   
    public static void putUserInFile() throws IOException{
    	try {
    		FileOutputStream i = new FileOutputStream("\"C:\\\\Users\\\\soubk\\\\eclipse-workspace\\\\HomeRentalSystem\\\\src\\Users.dat");
    		ObjectOutputStream in = new ObjectOutputStream(i);
    		System.out.println(users.toString());
    		in.writeObject(users);
    		in.close();
    		i.close();
    	}catch (IOException e) {
            System.out.println("Error putting users in file: " + e.getMessage());
        }
    }
    
    public static void changeUserFromFile() throws FileNotFoundException, IOException, ClassNotFoundException {
    	try {
    		FileInputStream i = new FileInputStream("\"C:\\\\Users\\\\soubk\\\\eclipse-workspace\\\\HomeRentalSystem\\\\src\\Users.dat");
    		ObjectInputStream in =  new ObjectInputStream(i);
    		try {
    			users = (ArrayList<User>) in.readObject();
    			in.close();
    			} catch (ClassNotFoundException ec) {
    				Logger.getLogger(House.class.getName()).log(Level.SEVERE, null, ec);
    				in.close();
    		}
    	} catch (IOException e) {
            System.out.println("Error changing users from file: " + e.getMessage());
        }
    }
    
    public void numOfBookingsPerRecep(){
        for(Receptionist receptionist: receptionists) {
        	System.out.println("Number of bookings per each receptionist: ");
        	System.out.println("Receptionist ID: " + receptionist.getReceptionistID() + ", Email: " + receptionist.getEmail() + ", Number Of Bookings: " + receptionist.getNumOfBookings());
        }
    }
    
    public Receptionist maxNumOfBookingsOfRecep(){
        if (receptionists.isEmpty()) {
        	System.out.println("There are no receptionists ");
            return null;
        }
        int maxRecep = -1;
        Receptionist recepWithMaxBookings = null;
        for(Receptionist receptionist: receptionists){
            int numOfBookings = receptionist.getNumOfBookings();
            if(maxRecep < numOfBookings){
            	maxRecep = numOfBookings;
            	recepWithMaxBookings = receptionist;
            }
        }
        return recepWithMaxBookings;
    }
    
    public Receptionist recepWithMaxRev(){
        if (receptionists.isEmpty()) {
        	System.out.println("There are no receptionists ");
            return null;
        }
        int maxRecepRev = -1;
        Receptionist recepWithMaxRev = null;
        for(Receptionist receptionist: receptionists){
            int recepRev = receptionist.getRecepRev();
            if(maxRecepRev < recepRev){
            	maxRecepRev = recepRev;
            	recepWithMaxRev = receptionist;
            }
        }
        return recepWithMaxRev;
    }
    
    public void numOfBookingsPerRenter(){
    	try {
    		for(Renter renter: renters) {
    			System.out.println("Number of bookings per each renter: ");
    			System.out.println("Renter ID: " + renter.getRenterID() + ", Email: " + renter.getEmail() + ", Number Of Bookings: " + renter.getNumOfBookings());
    		}
    	}catch (Exception e) {
            System.out.println("Error viewing the number of bookings per renter: " + e.getMessage());
        }
    }
    
    public Renter renterWithMaxBookings(){
    	try {
            if (renters.isEmpty()) {
            	System.out.println("There are no renters ");
                return null;
            }
            int maxRenter = -1;
            Renter renterWithMaxBookings = null;
            for(Renter renter: renters){
                int numOfBookings = renter.getNumOfBookings();
                if(maxRenter < numOfBookings){
                	maxRenter = numOfBookings;
                	renterWithMaxBookings = renter;
                }
            }
            return renterWithMaxBookings;
    	}catch (Exception e) {
            System.out.println("Error viewing the renter with max bookings: " + e.getMessage());
        }
    	return null;

    }
    
    public Renter renterWithMaxRev(){
    	try {
            if (renters.isEmpty()) {
            	System.out.println("There are no renters ");
                return null;
            }
            int maxRenterRev = -1;
            Renter renterWithMaxRev = null;
            for(Renter renter: renters){
                int renterRev = renter.getRenterRev();
                if(maxRenterRev < renterRev){
                	maxRenterRev = renterRev;
                	renterWithMaxRev = renter;
                }
            }
            return renterWithMaxRev;
    	}catch (Exception e) {
            System.out.println("Error viewing the renter with max revenue: " + e.getMessage());
        }
    	return null;

    }
    
    public void viewAllBookingsDetails(){
    	try {
    		for(Booking booking: bookings){
    			System.out.println("Booking ID: "+ booking.getBookingID() + "Class: "+ booking.getClass() +"Renter: "+ booking.getRenter()  + "Cost: "+ booking.getCost() + "Start Date: "+ booking.getStartDate() + "End Date: "+ booking.getEndDate());
    		}
    	} catch (Exception e) {
            System.out.println("Error viewing all the booking details: " + e.getMessage());
        }
    }
    
    public void viewBookingDetails(int bookingID){
    	try {
            for(Booking booking: bookings){
                if(booking.getBookingID() == bookingID){
                    System.out.println("Booking ID: "+ booking.getBookingID());
                    System.out.println("Class: "+ booking.getClass());
                    System.out.println("Renter: "+ booking.getRenter());
                    System.out.println("House ID: "+ booking.getHouseID());
                    System.out.println("Cost: "+ booking.getCost());
                    System.out.println("Start Date: "+ booking.getStartDate());
                    System.out.println("End Date: "+ booking.getEndDate());
                }
            }
    	} catch (Exception e) {
            System.out.println("Error viewing the booking details: " + e.getMessage());
        }

    }
    
    public int averageRevOverTime(java.util.Date startDate, java.util.Date endDate){
    	try {
    		for(House house: houses) {
    			int numOfRentals = viewNumOfRentalsOverTime(house.getHouseID(), startDate, endDate);
    			int revenue = numOfRentals * house.getPrice();           
    			long duration = endDate.getTime() - startDate.getTime();
    			int nights = (int) (duration / (1000 * 60 * 60 * 24));
    			int avgRevenue = revenue/nights;
    			return avgRevenue;
    		}
        } catch (Exception e) {
            System.out.println("Error calculating average revenue: " + e.getMessage());
        }
        return -1;
    }
    
    public int totalRevOverTime(java.util.Date startDate, java.util.Date endDate) {
    	try {
        	int totalRevenue = 0;
        	for(House house: houses) {
        	int numOfRentals = viewNumOfRentalsOverTime(house.getHouseID(), startDate, endDate);
        	totalRevenue += numOfRentals * house.getPrice();
        	}
        	return totalRevenue;
    	} catch (Exception e) {
            System.out.println("Error calculating total revenue: " + e.getMessage());
            return 0;
        }

    }
    
    public void viewDetails(Category category, int houseID) {
    	try {
    		for(House house : houses){
    			if(house.getHouseID() == houseID){
    				System.out.println("House Details:");
    				System.out.println("Category: " + house.getCategory());
    				System.out.println("Number of Rentals: " + house.getNumberOfRentals());
    				System.out.println("House ID: " + house.getHouseID());
    				System.out.println("Price Per Month: $" + house.getPrice());
    				System.out.println("Revenue: $" + house.getRevenue());
    				System.out.println("Available: " + house.isAvailable());
    			}
        	}
        } catch (Exception e) {
            System.out.println("Error viewing house details: " + e.getMessage());
        }
    }
}