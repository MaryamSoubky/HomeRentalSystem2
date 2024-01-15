package application;

import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {
    
 private final Map<String, Map<String, String>> userDatabase = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Welcome");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(40, 40, 40, 40));
        grid.setVgap(10);
        grid.setHgap(10);

        Label welcomeLabel = new Label("Welcome!");
        GridPane.setConstraints(welcomeLabel, 6, 0);

        Button loginButton = new Button("Login");
        Button signUpButton = new Button("Sign Up");

        GridPane.setConstraints(loginButton, 6, 1);
        GridPane.setConstraints(signUpButton, 6, 2);

        grid.getChildren().addAll(welcomeLabel, loginButton, signUpButton);

        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();

        loginButton.setOnAction(e -> showLoginPage());
        signUpButton.setOnAction(e -> showSignUpPage());
    }

    private void showLoginPage() {
        Stage loginStage = new Stage();
        loginStage.setTitle("Login Page");

        GridPane loginGrid = new GridPane();
        loginGrid.setPadding(new Insets(20, 20, 20, 20));
        loginGrid.setVgap(10);
        loginGrid.setHgap(10);

        Label usernameLabel = new Label("Username:");
        TextField usernameInput = new TextField();
        GridPane.setConstraints(usernameLabel, 0, 0);
        GridPane.setConstraints(usernameInput, 1, 0);

        Label passwordLabel = new Label("Password:");
        PasswordField passwordInput = new PasswordField();
        GridPane.setConstraints(passwordLabel, 0, 1);
        GridPane.setConstraints(passwordInput, 1, 1);

        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll("Admin", "Receptionist", "Renter");
        roleComboBox.setPromptText("Select Role");
        GridPane.setConstraints(roleComboBox, 1, 2);

        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 1, 3);
        loginButton.setOnAction(e -> authenticateUser(usernameInput.getText(), passwordInput.getText(), roleComboBox.getValue()));

        loginGrid.getChildren().addAll(usernameLabel, usernameInput, passwordLabel, passwordInput, roleComboBox, loginButton);

        Scene loginScene = new Scene(loginGrid, 300, 200);
        loginStage.setScene(loginScene);
        loginStage.show();
    }

    private void showSignUpPage() {
        Stage signUpStage = new Stage();
        signUpStage.setTitle("Sign Up Page");

        GridPane signUpGrid = new GridPane();
        signUpGrid.setPadding(new Insets(20, 20, 20, 20));
        signUpGrid.setVgap(10);
        signUpGrid.setHgap(10);

        Label usernameLabel = new Label("Username:");
        TextField usernameInput = new TextField();
        GridPane.setConstraints(usernameLabel, 0, 0);
        GridPane.setConstraints(usernameInput, 1, 0);

        Label passwordLabel = new Label("Password:");
        PasswordField passwordInput = new PasswordField();
        GridPane.setConstraints(passwordLabel, 0, 1);
        GridPane.setConstraints(passwordInput, 1, 1);

        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll("Admin", "Receptionist", "Renter");
        roleComboBox.setPromptText("Select Role");
        GridPane.setConstraints(roleComboBox, 1, 2);

        Button signUpButton = new Button("Sign Up");
        GridPane.setConstraints(signUpButton, 1, 3);
        signUpButton.setOnAction(e -> registerUser(usernameInput.getText(), passwordInput.getText(), roleComboBox.getValue()));

        signUpGrid.getChildren().addAll(usernameLabel, usernameInput, passwordLabel, passwordInput, roleComboBox, signUpButton);

        Scene signUpScene = new Scene(signUpGrid, 300, 200);
        signUpStage.setScene(signUpScene);
        signUpStage.show();
    }

    private void authenticateUser(String username, String password, String role) {
        if (userDatabase.containsKey(username)) {
            Map<String, String> userData = userDatabase.get(username);
            if (userData.get("password").equals(password) && userData.get("role").equals(role)) {
                System.out.println("Welcome, " + username + "!");
            } else {
                System.out.println("Invalid username or password for the selected role.");
            }
        } else {
            System.out.println("Invalid username. Please sign up first.");
        }
    }

    private void registerUser(String username, String password, String role) {
        if (!userDatabase.containsKey(username)) {
            Map<String, String> userData = new HashMap<>();
            userData.put("password", password);
            userData.put("role", role);
            userDatabase.put(username, userData);
            System.out.println(" An "+ role +"account added successfully! with username: " + username);
        } else {
            System.out.println("Username already exists. Please choose a different username.");
        }
    }
    

    public static void main(String[] args) {
        launch(args);
    }
}