package application;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import Main.Receptionist;
import Main.Type;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import Main.Renter;
import Main.Booking;

public class ReceptionistGUI extends Application {
		private Receptionist receptionist;
		private Booking booking;

	    public static void main(String[] args) {
	        launch(args);
	    }
	        @Override
	        public void start(Stage primaryStage) {
	            receptionist = new Receptionist("receptionist@example.com", "password", Type.Receptionist);

	            primaryStage.setTitle("Receptionist GUI");

	            GridPane grid = new GridPane();
	            grid.setPadding(new Insets(10, 10, 10, 10));
	            grid.setVgap(8);
	            grid.setHgap(10);

	            // Create Booking Form
	            Label renterIdLabel = new Label("Renter ID:");
	            GridPane.setConstraints(renterIdLabel, 0, 0);
	            TextField renterIdInput = new TextField();
	            GridPane.setConstraints(renterIdInput, 1, 0);

	            Label startDateLabel = new Label("Start Date (yyyy-MM-dd):");
	            GridPane.setConstraints(startDateLabel, 0, 1);
	            TextField startDateInput = new TextField();
	            GridPane.setConstraints(startDateInput, 1, 1);

	            Label endDateLabel = new Label("End Date (yyyy-MM-dd):");
	            GridPane.setConstraints(endDateLabel, 0, 2);
	            TextField endDateInput = new TextField();
	            GridPane.setConstraints(endDateInput, 1, 2);

	            Button createBookingButton = new Button("Create Booking");
	            GridPane.setConstraints(createBookingButton, 1, 3);
	            createBookingButton.setOnAction(e -> createBookingAction(renterIdInput.getText(), startDateInput.getText(), endDateInput.getText()));

	            // Cancel Booking Form
	            Label cancelBookingIdLabel = new Label("Booking ID to cancel:");
	            GridPane.setConstraints(cancelBookingIdLabel, 0, 4);
	            TextField cancelBookingIdInput = new TextField();
	            GridPane.setConstraints(cancelBookingIdInput, 1, 4);

	            Button cancelBookingButton = new Button("Cancel Booking");
	            GridPane.setConstraints(cancelBookingButton, 1, 5);
	            cancelBookingButton.setOnAction(e -> cancelBookingAction(cancelBookingIdInput.getText()));

	            // Calculate Payment Form
	            Label paymentBookingIdLabel = new Label("Booking ID to calculate payment:");
	            GridPane.setConstraints(paymentBookingIdLabel, 0, 6);
	            TextField paymentBookingIdInput = new TextField();
	            GridPane.setConstraints(paymentBookingIdInput, 1, 6);

	            Button calculatePaymentButton = new Button("Calculate Payment");
	            GridPane.setConstraints(calculatePaymentButton, 1, 7);
	            calculatePaymentButton.setOnAction(e -> calculatePaymentAction(paymentBookingIdInput.getText()));

	            grid.getChildren().addAll(
	                    renterIdLabel, renterIdInput, startDateLabel, startDateInput, endDateLabel, endDateInput, createBookingButton,
	                    cancelBookingIdLabel, cancelBookingIdInput, cancelBookingButton,
	                    paymentBookingIdLabel, paymentBookingIdInput, calculatePaymentButton);

	            Scene scene = new Scene(grid, 400, 300);
	            primaryStage.setScene(scene);
	            primaryStage.show();
	        }

	        private void createBookingAction(int renterID, java.util.Date startDate, java.util.Date endDate) {
	            try {
	                Renter renter = Renter.getRenter(renterID);
	                Date startDate = Booking.getStartDate();
	                Date endDate = Booking.getEndDate();
	                receptionist.createBooking(receptionist.getReceptionistID(), renter, startDate, endDate);
	                System.out.println("Booking created successfully.");
	            } catch (Exception e) {
	                System.out.println("Error creating booking: " + e.getMessage());
	            }
	        }

	        private void cancelBookingAction(String bookingId) {
	            try {
	                int bookingID = Integer.parseInt(bookingId);
	                receptionist.cancelBooking(bookingID);
	                System.out.println("Booking canceled successfully.");
	            } catch (NumberFormatException e) {
	                System.out.println("Invalid Booking ID format.");
	            }
	        }

	        private void calculatePaymentAction(String bookingId) {
	            try {
	                int bookingID = Integer.parseInt(bookingId);
	                int totalPayment = receptionist.calculatePayment(bookingID);
	                System.out.println("Total payment for Booking ID " + bookingID + ": " + totalPayment);
	            } catch (NumberFormatException e) {
	                System.out.println("Invalid Booking ID format.");
	            }
	        }
	    }
	    }
}

