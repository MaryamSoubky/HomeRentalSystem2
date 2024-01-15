package Main;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Receptionist extends User implements Serializable {
    private static int nextID = 0;
    private int receptionistID;
    private Type type;
    private int numOfBookings = 0;
    private int recepRev = 0;
    private static ArrayList<House> houses = new ArrayList <House>();
    private static ArrayList<Renter> renters =  new ArrayList <Renter>();
    private static ArrayList<Booking> bookings =  new ArrayList <Booking>();
    
    // Constructor
    public Receptionist(String email, String password, Type type) {
        super(email, password);
        this.receptionistID = nextID++;
        this.type = Type.Receptionist;
    }
    
    // Getters and Setters
	public int getReceptionistID() {
		return receptionistID;
	}

	public int getNumOfBookings() {
		return numOfBookings;
	}

	public void setNumOfBookings(int numOfBookings) {
		this.numOfBookings = numOfBookings;
	}

	public static ArrayList<Booking> getBookings() {
		return bookings;
	}
	
    public int getRecepRev() {
		return recepRev;
	}


	// Methods
    public boolean createBooking(int receptionistID, Renter renter, java.util.Date startDate, java.util.Date endDate){
        if(renters.isEmpty())
            return false;
        else{
        	System.out.println("Choose the category of the house: ");
        	System.out.println("0: Apartment");
        	System.out.println("1: Villa");
        	System.out.println("2: Cottage");
        	Scanner s = new Scanner(System.in);
        	String inputCategory = s.nextLine();
        	Category category = Category.valueOf(inputCategory);
        	System.out.println("Houses with the selected category: " + category);
        	for(House house : houses){
        		if(house.getCategory() == category) {
        			System.out.println("House ID: " + house.getHouseID() + ", address: " + house.getAddress() + " Price: " + house.getPrice());
        			System.out.println("Type in the house ID of the house: ");
        			int houseID = s.nextInt();
                    Booking booking = new Booking(receptionistID, renter, houseID, startDate, endDate);
                    booking.setCost(calculatePayment(booking.getBookingID()));
                    numOfBookings++;
                    System.out.println("Rental Details and Terms: ");
                    System.out.println("Booking ID: " + booking.getBookingID() + ", booked by: " + renter.getEmail() + ", Category of the house " + category + "Price per night: " + house.getPrice() + " , Starting from " + startDate + " to " + endDate + " Total Price: " + booking.getCost());
                    int bookingsOfRenter = renter.getNumOfBookings();
                    renter.setNumOfBookings(bookingsOfRenter++);
                    recepRev += booking.getCost();
                    int renterRev = renter.getRenterRev();
                    renterRev += booking.getCost();
                    renter.setRenterRev(renterRev);
                    return true;
        		}
        	}
        	return false;

        }
    }

	public Boolean cancelBooking(int bookingID){
        for(Booking booking : bookings){
            if(booking.getBookingID() == bookingID){
                House house = getHouse(booking.getHouseID());
                recepRev -= booking.getCost();
                booking = null;
                numOfBookings--;
                return true;
            }
        }
        System.out.println("Error: Booking not found!");
        return false;
    }
    
    public int calculatePayment(int bookingID) {
        for(Booking booking : bookings){
            if(booking.getBookingID() == bookingID){
            long duration = booking.getEndDate().getTime() - booking.getStartDate().getTime();
            int nights = (int) (duration / (1000 * 60 * 60 * 24)); // Convert milliseconds to nights
            House house = getHouse(booking.getHouseID());
            int totalPrice = nights * house.getPrice();
            return totalPrice;
            }
        }
            System.out.println("Error: House details not found.");
            return 0;
    }
    
    public House getHouse(int houseID) {
    	for(House house : houses) {
    		if(house.getHouseID() == houseID)
    			return house;
    	}
    	return null;
    }
    
    public Renter getRenter(int renterID) {
    	for(Renter renter : renters) {
    		if(renter.getRenterID() == renterID)
    			return renter;
    	}
    	return null;
    }
    
    public static void addBookingToFile() throws IOException {
        	try {
        		FileOutputStream i = new FileOutputStream("C:\\Users\\soubk\\eclipse-workspace\\HomeRentalSystem\\src\\Bookings.dat");
        		ObjectOutputStream in = new ObjectOutputStream(i);
        		System.out.println(bookings.toString());
        		in.writeObject(bookings);
        		in.close();
        		i.close();
        	} catch (IOException e) {
            System.out.println("Error putting bookings in file: " + e.getMessage());
        	}
    }
    
    public static void removeBookingFromFile() throws FileNotFoundException, IOException, ClassNotFoundException{
    	try {
    		FileInputStream i = new FileInputStream("C:\\Users\\soubk\\eclipse-workspace\\HomeRentalSystem\\src\\Bookings.dat");
    		ObjectInputStream in =  new ObjectInputStream(i);
    		try {
    			bookings = (ArrayList<Booking>) in.readObject();
    			in.close();
    			} catch (ClassNotFoundException ec) {
    				Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ec);
    				in.close();
    		}
    	} catch (IOException e) {
            System.out.println("Error changing bookings from file: " + e.getMessage());
        }
    }
}
