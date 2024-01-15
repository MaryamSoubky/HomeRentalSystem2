package Main;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Renter extends User implements Serializable {
    private static int nextID = 0;
    private int renterID;
    private Type type;
    private int numOfBookings = 0;
    private int renterRev = 0;
    private ArrayList<Renter> renters =  new ArrayList <Renter>();
    private static ArrayList<Booking> bookingHistory;

    public Renter(String email, String password, Type type) {
    	super(email, password);
        this.bookingHistory = new ArrayList<>();
        this.type = Type.Renter;
    }
    
    //Getters and Setters
    public static int getNextID() {
		return nextID;
	}

	public static void setNextID(int nextID) {
		Renter.nextID = nextID;
	}

	public int getRenterID() {
		return renterID;
	}

	public void setRenterID(int renterID) {
		this.renterID = renterID;
	}
	
	public int getNumOfBookings() {
		return numOfBookings;
	}

	public void setNumOfBookings(int numOfBookings) {
		this.numOfBookings = numOfBookings;
	}

	public int getRenterRev() {
		return renterRev;
	}

	public void setRenterRev(int renterRev) {
		this.renterRev = renterRev;
	}

	public static ArrayList<Booking> getBookingHistory() {
		return bookingHistory;
	}

	public static void setBookingHistory(ArrayList<Booking> bookingHistory) {
		Renter.bookingHistory = bookingHistory;
	}

    public ArrayList<Booking> viewBookingHistory() {
        return new ArrayList<>(bookingHistory);
    }
	public int rateBooking(Booking booking, int rating) {
        // Assuming that the rating is associated with a booking
        if (booking != null && bookingHistory.contains(booking)) {
            booking.setRating(rating);
            return rating;
        } else {
            System.out.println("Invalid booking or booking not found in history.");
            return -1;
        }
    }

    // Additional method to view the details of a specific booking
    public void viewBookingDetails(Booking booking) {
        if (booking != null && bookingHistory.contains(booking)) {
            System.out.println("Booking Details:");
            System.out.println("House ID: " + booking.getHouseID());
            System.out.println("Start Date: " + booking.getStartDate());
            System.out.println("End Date: " + booking.getEndDate());
            System.out.println("Rating: " + booking.getRating());
        } else {
            System.out.println("Invalid booking or booking not found in history.");
        }
    }
    public static void addBookingHistoryToFile() throws IOException {
    	try {
    		FileOutputStream i = new FileOutputStream("C:\\Users\\soubk\\eclipse-workspace\\HomeRentalSystem\\src\\BookingHistory.dat");
    		ObjectOutputStream in = new ObjectOutputStream(i);
    		System.out.println(bookingHistory.toString());
    		in.writeObject(bookingHistory);
    		in.close();
    		i.close();
    	} catch (IOException e) {
        System.out.println("Error putting Booking History in file: " + e.getMessage());
    	}
}

    public static void removeBookingHistoryFromFile() throws FileNotFoundException, IOException, ClassNotFoundException{
    	try {
    		FileInputStream i = new FileInputStream("C:\\Users\\soubk\\eclipse-workspace\\HomeRentalSystem\\src\\BookingHistory.dat");
    		ObjectInputStream in =  new ObjectInputStream(i);
    		try {
    			bookingHistory = (ArrayList<Booking>) in.readObject();
    			in.close();
				} catch (ClassNotFoundException ec) {
					Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ec);
					in.close();
				}
    	} catch (IOException e) {
    		System.out.println("Error changing Booking History from file: " + e.getMessage());
    	}
    }
    public Renter getRenter(int renterID) {
    	for(Renter renter : renters) {
    		if(renter.getRenterID() == renterID)
    			return renter;
    	}
    	return null;
    }
}
