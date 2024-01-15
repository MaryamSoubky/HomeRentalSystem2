package Main;
import java.io.Serializable;
import java.util.Date;

public class Booking implements Serializable{
    private static int nextID = 0;
    private int bookingID;
    private User renter;
    private java.util.Date startDate;
    private java.util.Date endDate;
    private int cost;
    private boolean isCancelled;
    private int houseID;
    private int rating;

	// Constructor
    public Booking(int receptionistID, User renter, int houseID, java.util.Date startDate, java.util.Date endDate) {
        this.bookingID = nextID++;
        this.renter = renter;
//        this.house = house;
//        cost = house.getPrice();
        this.isCancelled = false;
        this.houseID = houseID;
        this.startDate = startDate;
        this.endDate = endDate;
    } 

// Getters and Setters
    public int getHouseID() {
    	return houseID;
    } 
        public int getBookingID()
    {
        return bookingID;
    }

    public User getRenter() {
        return renter;
    }

    public void setRenter(User renter) {
        this.renter = renter;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public java.util.Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
    
    public boolean isCancelled() {
        return isCancelled;
    }
    
    public void cancelBooking() {
        isCancelled = true;
    }


	public void setBookingID(int bookingID) {
		this.bookingID = bookingID;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
    
    
}

