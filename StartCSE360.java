package simpleDatabase;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.List; // Import for List
import java.util.ArrayList; // Import for ArrayList

import java.sql.SQLException;

public class StartCSE360 extends Application {

    private static final DatabaseHelper databaseHelper = new DatabaseHelper();
    private Stage primaryStage;
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws SQLException {
        this.primaryStage = primaryStage;
        databaseHelper.connectToDatabase();
        
        // Show initial login screen or admin setup
        if (databaseHelper.isDatabaseEmpty()) {
            setupAdministratorScreen();
        } else {
        	showWelcomeScreen();
        }
        
        primaryStage.setTitle("CSE360 Application");
        primaryStage.show();
    }

    private void setupAdministratorScreen() {
        // Layout for admin setup
        VBox adminSetupLayout = new VBox(10);
        adminSetupLayout.setPadding(new Insets(10));

        Label title = new Label("Setup Administrator");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Admin Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Admin Password");

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");

        Button submitButton = new Button("Submit");
        
        Label messageLabel = new Label();
        
        submitButton.setOnAction(e -> {
            try {
                String username = usernameField.getText();
                String password = passwordField.getText();
                String confirmPassword = confirmPasswordField.getText();
                
                if (password.equals(confirmPassword)) {
                    String role = "Admin";
                    databaseHelper.signUpAdmin(username, password, role);
                    messageLabel.setText("Administrator setup completed.");
                    showLoginScreen();  // After setup, show the login screen
                } else {
                    messageLabel.setText("Passwords do not match.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                messageLabel.setText("Database error occurred.");
            }
        });

        adminSetupLayout.getChildren().addAll(title, usernameField, passwordField, confirmPasswordField, submitButton, messageLabel);

        Scene adminSetupScene = new Scene(adminSetupLayout, 300, 250);
        primaryStage.setScene(adminSetupScene);
    }

    private void showWelcomeScreen() {
        VBox welcomeLayout = new VBox(10);
        welcomeLayout.setPadding(new Insets(10));
        
        Label welcomeLabel = new Label("Welcome! Please choose an option:");
        
        Button loginButton = new Button("Login");
        Button inviteCodeButton = new Button("Invite Code");

        loginButton.setOnAction(e -> showLoginScreen());
        inviteCodeButton.setOnAction(e -> inviteCodeScreen());

        welcomeLayout.getChildren().addAll(welcomeLabel, loginButton, inviteCodeButton);
        
        Scene welcomeScene = new Scene(welcomeLayout, 300, 200);
        primaryStage.setScene(welcomeScene);
    }
    
    
    private void showLoginScreen() {
        // Layout for login screen
        VBox loginLayout = new VBox(10);
        loginLayout.setPadding(new Insets(10));

        Label loginLabel = new Label("Login");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginButton = new Button("Login");
        Label messageLabel = new Label();

        loginButton.setOnAction(e -> {
            try {
                String username = usernameField.getText();
                String password = passwordField.getText();

                if (databaseHelper.login(username, password)) {
                    if (!hasSetupAccount(username)) {
                        setupAccountScreen(username); // Show setup account screen if not fully set up
                    } else if(databaseHelper.isPasswordBeingReset(username)){
                    	handlePasswordResetScreen(username);
                    }else {
                        String role = databaseHelper.getRole(username);
                        messageLabel.setText("Login successful as " + role);
                        loadRoleSpecificOptions(role);
                    }
                } else {
                    messageLabel.setText("Invalid credentials, try again.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                messageLabel.setText("Database error occurred.");
            }
        });

        loginLayout.getChildren().addAll(loginLabel, usernameField, passwordField, loginButton, messageLabel);

        Scene loginScene = new Scene(loginLayout, 300, 200);
        primaryStage.setScene(loginScene);
    }

    private boolean hasSetupAccount(String username) throws SQLException {
        // Assuming databaseHelper has a method that checks if the account is fully set up
        return databaseHelper.hasSetupAccount(username);
    }

    private void handlePasswordResetScreen(String username) throws SQLException {
        // Layout for password reset screen
        VBox resetLayout = new VBox(10);
        resetLayout.setPadding(new Insets(10));

        Label resetLabel = new Label("Password Reset");
        Label otpLabel = new Label("Enter the One-Time Password (OTP) sent to your email:");
        TextField otpField = new TextField();
        otpField.setPromptText("Enter OTP");

        Label newPasswordLabel = new Label("Enter your new password:");
        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setPromptText("New Password");

        Button resetButton = new Button("Reset Password");
        Label messageLabel = new Label();

        // Check if password is being reset
        if (databaseHelper.isPasswordBeingReset(username)) {
            resetButton.setOnAction(e -> {
                String enteredOtp = otpField.getText();

                // Validate the OTP
                try {
					if (databaseHelper.check_OtpExpiry(username, enteredOtp)) {
					    String newPassword = newPasswordField.getText();

					    // Update the password in the database
					    try {
					        databaseHelper.updateUserPassword(username, newPassword);
					        messageLabel.setText("Your password has been successfully reset.");
					    } catch (SQLException ex) {
					        messageLabel.setText("Error resetting password: " + ex.getMessage());
					    }
					} else {
					    messageLabel.setText("Invalid or expired OTP. Please try again.");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            });
        } else {
            messageLabel.setText("No password reset request found for this user.");
        }

        // Add all components to layout
        resetLayout.getChildren().addAll(resetLabel, otpLabel, otpField, newPasswordLabel, newPasswordField, resetButton, messageLabel);

        // Set the scene
        Scene resetScene = new Scene(resetLayout, 400, 250);
        primaryStage.setScene(resetScene);
    }

    private void setupAccountScreen(String username) {
        VBox setupLayout = new VBox(10);
        setupLayout.setPadding(new Insets(10));

        Label setupLabel = new Label("Please complete your account setup:");
        
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        
        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First Name");
        
        TextField middleNameField = new TextField();
        middleNameField.setPromptText("Middle Name");
        
        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");
        
        TextField prefFirstNameField = new TextField();
        prefFirstNameField.setPromptText("Preferred First Name");

        Button submitButton = new Button("Submit");
        Label messageLabel = new Label();

        submitButton.setOnAction(e -> {
            try {
                String email = emailField.getText();
                String firstName = firstNameField.getText();
                String middleName = middleNameField.getText();
                String lastName = lastNameField.getText();
                String prefFirstName = prefFirstNameField.getText();

                databaseHelper.setupAccount(username, email, firstName, middleName, lastName, prefFirstName);
                messageLabel.setText("Account fully set up! Thank you!");

                // Optionally redirect to the role-specific options after setup
                String role = databaseHelper.getRole(username);
                loadRoleSpecificOptions(role);

            } catch (SQLException ex) {
                ex.printStackTrace();
                messageLabel.setText("Error setting up account. Please try again.");
            }
        });

        setupLayout.getChildren().addAll(setupLabel, emailField, firstNameField, middleNameField, lastNameField, prefFirstNameField, submitButton, messageLabel);

        Scene setupScene = new Scene(setupLayout, 400, 300);
        primaryStage.setScene(setupScene);
    }


    private void loadRoleSpecificOptions(String role) throws SQLException {
        switch (role) {
            case "Admin":
                showAdminOptions();
                break;
            case "Instructor":
                showInstructorOptions();
                break;
            case "Student":
                showStudentOptions();
                break;
            default:
                System.out.println("Unknown role.");
                break;
        }
    }

    // Admin-specific menu and options
    private void showAdminOptions() {
        VBox adminLayout = new VBox(10);
        adminLayout.setPadding(new Insets(10));
        
        Label adminMenuLabel = new Label("Admin Options");
        
        Button inviteUserButton = new Button("Invite User");
        Button showUsersButton = new Button("Show Users");
        Button resetPasswordButton = new Button("Reset User Password");
        Button deleteUserButton = new Button("Delete User");
        Button logoutButton = new Button("Logout");
        
        inviteUserButton.setOnAction(e -> inviteUserScreen());
        showUsersButton.setOnAction(e -> showUsersScreen());
        resetPasswordButton.setOnAction(e -> resetPasswordScreenAdmin());
        deleteUserButton.setOnAction(e -> deleteUserScreen());
        logoutButton.setOnAction(e -> showWelcomeScreen()); // Logout redirects to login screen
        
        adminLayout.getChildren().addAll(adminMenuLabel, inviteUserButton, showUsersButton, resetPasswordButton, deleteUserButton, logoutButton);
        
        Scene adminScene = new Scene(adminLayout, 300, 300);
        primaryStage.setScene(adminScene);
    }

    private void inviteCodeScreen() {
        VBox inviteLayout = new VBox(10);
        inviteLayout.setPadding(new Insets(10));

        Label inviteLabel = new Label("Enter your invite code:");
        TextField inviteCodeField = new TextField();
        inviteCodeField.setPromptText("Invite Code");

        Button submitButton = new Button("Submit");
        Label messageLabel = new Label();
        Button exitButton = new Button("Exit");
        submitButton.setOnAction(e -> {
            String inviteCode = inviteCodeField.getText();
            
            // Check if the invite code exists
            if (databaseHelper.doesUserExist(inviteCode)) {
                messageLabel.setText("One Time Invite Code verified! Please create your new username and password.");

                // Create new fields for username and password
                TextField newUserNameField = new TextField();
                newUserNameField.setPromptText("New Username");

                PasswordField newPasswordField = new PasswordField();
                newPasswordField.setPromptText("New Password");

                Button createUserButton = new Button("Create Account");
                createUserButton.setOnAction(createEvent -> {
                    String newUsername = newUserNameField.getText();
                    String newPassword = newPasswordField.getText();
                    
                    // Check if the new username already exists
                    if (databaseHelper.doesUserExist(newUsername)) {
                        messageLabel.setText("This username is already taken. Please choose a different one.");
                    } else {
                        try {
                            // Update the database with the new user details
                            databaseHelper.updateNewUser(inviteCode, newUsername, newPassword);
                            messageLabel.setText("Account created successfully! You can now log in.");
                        } catch (SQLException ex) {
                            messageLabel.setText("Error creating account. Please try again.");
                            ex.printStackTrace();
                        }
                    }
                });

                // Add new fields and button to the layout
                inviteLayout.getChildren().addAll(newUserNameField, newPasswordField, createUserButton);
            } else {
                // Invalid invite code
                messageLabel.setText("Invalid one-time invite code. Please check and try again.");
            }
        });
        
        exitButton.setOnAction(e -> showWelcomeScreen());
        
        inviteLayout.getChildren().addAll(inviteLabel, inviteCodeField, submitButton, exitButton, messageLabel);
        
        Scene inviteScene = new Scene(inviteLayout, 300, 300);
        primaryStage.setScene(inviteScene);
    }


    private void inviteUserScreen() {
        VBox inviteUserLayout = new VBox(10);
        inviteUserLayout.setPadding(new Insets(10));

        Label inviteUserLabel = new Label("Invite User");
        
        TextField emailField = new TextField();
        emailField.setPromptText("User Email");

        TextField roleField = new TextField();
        roleField.setPromptText("User Role (e.g., Instructor, Student)");

        Button submitButton = new Button("Send Invite");
        Button exitButton = new Button("Exit");
        Label messageLabel = new Label();

        submitButton.setOnAction(e -> {
            String email = emailField.getText();
            String role = roleField.getText();
            String oneTimePass = null;
            // Call the method to handle sending the invite (you'll need to implement this)
            try {
                oneTimePass = databaseHelper.inviteUser(role);
                
                    messageLabel.setText("Invitation sent to " + email + ", OTP password is :" + oneTimePass);
                
            } catch (SQLException ex) {
                ex.printStackTrace();
                messageLabel.setText("Database error occurred.");
            }
        });
        
        exitButton.setOnAction(e -> showAdminOptions());

        inviteUserLayout.getChildren().addAll(inviteUserLabel, emailField, roleField, submitButton, exitButton, messageLabel);
        
        Scene inviteUserScene = new Scene(inviteUserLayout, 300, 250);
        primaryStage.setScene(inviteUserScene);
    }

    private void showUsersScreen() {
        VBox usersLayout = new VBox(10);
        usersLayout.setPadding(new Insets(10));

        Label usersLabel = new Label("Users List");
        ListView<String> usersListView = new ListView<>();

        try {
            // Call the displayUsersByAdmin method to get user details
            List<String> users = databaseHelper.displayUsersByAdmin();
            usersListView.getItems().addAll(users); // Add all user info to the ListView
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Create a back button to return to admin options
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> showAdminOptions()); // Go back to admin options

        usersLayout.getChildren().addAll(usersLabel, usersListView, backButton);

        Scene usersScene = new Scene(usersLayout, 400, 300);
        primaryStage.setScene(usersScene);
    }


    private void resetPasswordScreenAdmin() {
        VBox resetLayout = new VBox(10);
        resetLayout.setPadding(new Insets(10));

        Label resetLabel = new Label("Reset User Password");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter username to reset password");
        
        Button resetButton = new Button("Reset Password");
        Label messageLabel = new Label();
        Button backButton = new Button("Back");

        // Action for the reset button
        resetButton.setOnAction(e -> {
            String username = usernameField.getText();

            try {
                if (databaseHelper.doesUserExist(username)) {
                    databaseHelper.resetUserPassword(username); // Update password in the database
                    messageLabel.setText("Password will now be reset" + username);
                } else {
                    messageLabel.setText("User does not exist.");
                }
            } catch (SQLException ex) {
                messageLabel.setText("Error resetting password: " + ex.getMessage());
            }
        });

        // Action for the back button
        backButton.setOnAction(e -> {
            showAdminOptions(); // Redirect to admin options
        });

        resetLayout.getChildren().addAll(resetLabel, usernameField, resetButton, messageLabel, backButton);

        Scene resetScene = new Scene(resetLayout, 400, 250);
        primaryStage.setScene(resetScene);
    }
    
    
    
    private void handlePasswordReset(String username) {
        VBox resetUserPasswordLayout = new VBox(10);
        resetUserPasswordLayout.setPadding(new Insets(10));

        // Label for reset instruction
        Label resetLabel = new Label("Reset User Password");
        resetUserPasswordLayout.getChildren().add(resetLabel);

        // OTP input field
        TextField otpField = new TextField();
        otpField.setPromptText("Enter One-Time Password (OTP)");
        resetUserPasswordLayout.getChildren().add(otpField);

        // New password input field
        TextField newPasswordField = new TextField();
        newPasswordField.setPromptText("Enter new Password");
        resetUserPasswordLayout.getChildren().add(newPasswordField);

        // Confirm button
        Button confirmPasswordButton = new Button("Confirm New Password");
        resetUserPasswordLayout.getChildren().add(confirmPasswordButton);

        // Back button
        Button backButton = new Button("Back");
        resetUserPasswordLayout.getChildren().add(backButton); // Add back button to the layout

        // Set action for the confirm button
        confirmPasswordButton.setOnAction(event -> {
            try {
                if (databaseHelper.isPasswordBeingReset(username)) {
                    String enteredOtp = otpField.getText();
                    if (databaseHelper.check_OtpExpiry(username, enteredOtp)) {
                        String newPassword = newPasswordField.getText();
                        databaseHelper.updateUserPassword(username, newPassword);
                        showAlert("Success", "Your password has been successfully reset.");
                    } else {
                        showAlert("Error", "Invalid or expired OTP. Please try again or have your administrator send another password.");
                    }
                } else {
                    showAlert("Error", "Your password is not being reset. Please check your username.");
                }
            } catch (SQLException e) {
                showAlert("Error", "An error occurred while resetting your password: " + e.getMessage());
            }
        });

        // Action for the back button
        backButton.setOnAction(e -> {
            showAdminOptions(); // Redirect to admin options
        });

       
    }



    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void deleteUserScreen() {
        // Layout for delete user screen
        VBox deleteLayout = new VBox(10);
        deleteLayout.setPadding(new Insets(10));

        Label deleteLabel = new Label("Delete User Account");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter username to delete");

        Label confirmationLabel = new Label("Type 'yes' to confirm, or 'no' to cancel:");
        TextField confirmationField = new TextField();
        confirmationField.setPromptText("Confirmation");

        Button deleteButton = new Button("Delete Account");
        Label messageLabel = new Label();
        Button backButton = new Button("Back");

        deleteButton.setOnAction(e -> {
            String userToDelete = usernameField.getText();
            String confirmation = confirmationField.getText().toLowerCase();

            // Check confirmation
            if (confirmation.equals("yes")) {
                // Proceed with deletion
                try {
                    databaseHelper.deleteAccount(userToDelete);
                    messageLabel.setText("The account has been deleted.");
                    showAdminOptions();
                } catch (SQLException ex) {
                    messageLabel.setText("Error occurred while deleting the account: " + ex.getMessage());
                }
            } else if (confirmation.equals("no")) {
                messageLabel.setText("Account deletion canceled.");
            } else {
                messageLabel.setText("Invalid input. Account deletion aborted.");
            }
        });

        // Set up the back button action separately
        backButton.setOnAction(e -> {
            showAdminOptions(); // Redirect to admin options on logout
        });


        deleteLayout.getChildren().addAll(deleteLabel, usernameField, confirmationLabel, confirmationField, deleteButton, messageLabel, backButton);

        Scene deleteScene = new Scene(deleteLayout, 400, 200);
        primaryStage.setScene(deleteScene);
    }


    private void showInstructorOptions() {
        VBox instructorLayout = new VBox(10);
        instructorLayout.setPadding(new Insets(10));

        Label instructorMenuLabel = new Label("Instructor Options");

        // Logout button
        Button logoutButton = new Button("Logout");
        
        // Action for logout
        logoutButton.setOnAction(e -> {
        	showWelcomeScreen(); // Redirect to login screen on logout
        });

        instructorLayout.getChildren().addAll(instructorMenuLabel, logoutButton);

        Scene instructorScene = new Scene(instructorLayout, 300, 200);
        primaryStage.setScene(instructorScene);
    }

    private void showStudentOptions() {
        VBox studentLayout = new VBox(10);
        studentLayout.setPadding(new Insets(10));

        Label studentMenuLabel = new Label("Student Options");

        // Logout button
        Button logoutButton = new Button("Logout");
        
        // Action for logout
        logoutButton.setOnAction(e -> {
            showWelcomeScreen(); // Redirect to login screen on logout
        });

        studentLayout.getChildren().addAll(studentMenuLabel, logoutButton);

        Scene studentScene = new Scene(studentLayout, 300, 200);
        primaryStage.setScene(studentScene);
    }

}
