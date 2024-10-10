package simpleDatabase;
import java.sql.*;
import java.security.SecureRandom;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List; // Import for List
import java.util.ArrayList; // Import for ArrayList
class DatabaseHelper {

	// JDBC driver name and database URL 
	static final String JDBC_DRIVER = "org.h2.Driver";   
	static final String DB_URL = "jdbc:h2:~/firstDatabase";  

	//  Database credentials 
	static final String USER = "sa"; 
	static final String PASS = ""; 

	private Connection connection = null;
	private Statement statement = null; 
	//	PreparedStatement pstmt

	public void connectToDatabase() throws SQLException {
		try {
			Class.forName(JDBC_DRIVER); // Load the JDBC driver
			System.out.println("Connecting to database...");
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
			statement = connection.createStatement(); 
			createTables();  // Create the necessary tables if they don't exist
		} catch (ClassNotFoundException e) {
			System.err.println("JDBC Driver not found: " + e.getMessage());
		}
	}
	//join code should be 5 chars login
	//join code will replace username after the user has enter the correct join code and enters a username
	
	private void createTables() throws SQLException {
	    // Create user table which holds all of the users with this data
	    String userTable = "CREATE TABLE IF NOT EXISTS cse360users ("
	    		+ "id INT AUTO_INCREMENT PRIMARY KEY, "  // Auto-increment syntax
	            + "username VARCHAR(20) UNIQUE NOT NULL, " // Username cannot be NULL
	            + "password VARCHAR(20) DEFAULT NULL, " // Password can be NULL initially
	            + "role VARCHAR(15), " // Role given by admin, can be NULL
	            + "email VARCHAR(30) DEFAULT NULL, "
	            + "firstName VARCHAR(255) DEFAULT NULL, " // First name can be NULL initially
	            + "middleName VARCHAR(255) DEFAULT NULL, " // Middle name can be NULL
	            + "lastName VARCHAR(255) DEFAULT NULL, " // Last name can be NULL initially
	            + "prefFirstName VARCHAR(255) DEFAULT NULL, " // Preferred first name can be NULL
	            + "completedReg BOOLEAN DEFAULT FALSE, " // Default registration status is false 
	            + "passwordBeingReset BOOLEAN DEFAULT FALSE, " // Flag for password reset
	            + "otp VARCHAR(20) UNIQUE NULL, " // OTP for password reset
	            + "otpExpiry TIMESTAMP DEFAULT NULL, " // OTP expiry time; explicitly setting default
	            + "otpUsed BOOLEAN DEFAULT FALSE" // Flag for OTP usage
	            + ")"; // End of table	 definition

	    statement.execute(userTable);
	    
	    
	    
	    
	    
	    
	}







	public boolean isDatabaseEmpty() throws SQLException {
	    String query = "SELECT COUNT(*) AS count FROM cse360users";
	    try {
	        ResultSet resultSet = statement.executeQuery(query);
	        if (resultSet.next()) {
	            return resultSet.getInt("count") == 0;
	        }
	    } catch (SQLException e) {
	        // Handle the exception if the table does not exist
	        if (e.getErrorCode() == 42104) { // Error code for table not found in H2
	            return true; // Table doesn't exist, so database is empty
	        } else {
	            throw e; // Re-throw if it's a different SQL error
	        }
	    }
	    return true;
	}

	
	//Sign up function that is seen by the first user AKA the Admin
		public void signUpAdmin(String username, String password, String role) throws SQLException{
			String insertUser = "INSERT INTO cse360users (username, password, role) VALUES (?, ?, ?)";
			try(PreparedStatement pstmt = connection.prepareStatement(insertUser)){
				pstmt.setString(1, username);
				pstmt.setString(2, password);
				pstmt.setString(3, role);
				pstmt.executeUpdate();
			}
			
		}
	
		
	//Register function only seen by the admin to create the one time password for the user and assign them there role or roles
	public void register(String username, String password, String role) throws SQLException {
		String insertUser = "INSERT INTO cse360users (username, role) VALUES (?, ?)";
		try (PreparedStatement pstmt = connection.prepareStatement(insertUser)) {
			pstmt.setString(1, username);
			pstmt.setString(3, role);
			pstmt.executeUpdate();
		}
	}
	
	
	
		
	// Update new user function used after user enters correct invite code
		public void updateNewUser(String InvitePassword, String newUsername, String newPassword) throws SQLException{
		    String updateUser = "UPDATE cse360users SET username = ?, password = ? WHERE username = ?";
		    try(PreparedStatement pstmtUpdate = connection.prepareStatement(updateUser)){
		        pstmtUpdate.setString(1, newUsername);
		        pstmtUpdate.setString(2, newPassword);
		        pstmtUpdate.setString(3, InvitePassword);
		        
		        // Execute the update query
		        pstmtUpdate.executeUpdate();
		    }
		}
		
		
	//Sign up function that is seen by the user
	public void signUp(String username, String password) throws SQLException{
		String insertUser = "INSERT INTO cse360users (username, password) VALUES (?, ?)";
		try(PreparedStatement pstmt = connection.prepareStatement(insertUser)){
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			pstmt.executeUpdate();
		}
		
	}

	public boolean login(String username, String password) throws SQLException {
		String query = "SELECT * FROM cse360users WHERE username = ? AND password = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			try (ResultSet rs = pstmt.executeQuery()) {
				return rs.next();
			}
		}
	}
	
	//Checks if user/username Exists
	public boolean doesUserExist(String username) {
	    String query = "SELECT COUNT(*) FROM cse360users WHERE username = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        
	        pstmt.setString(1, username);
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            // If the count is greater than 0, the user exists
	            return rs.getInt(1) > 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false; // If an error occurs, assume user doesn't exist
	}
	
	//displays user by admin and only shows what the admin should see
	public List<String> displayUsersByAdmin() throws SQLException {
	    List<String> userList = new ArrayList<>();
	    String sql = "SELECT * FROM cse360users";
	    Statement stmt = connection.createStatement();
	    ResultSet rs = stmt.executeQuery(sql);

	    while (rs.next()) {
	        int id = rs.getInt("id");
	        String username = rs.getString("username");
	        String email = rs.getString("email");
	        String firstName = rs.getString("firstName");
	        String middleName = rs.getString("middleName");
	        String lastName = rs.getString("lastName");
	        String prefFirstName = rs.getString("prefFirstName");
	        String role = rs.getString("role");

	        // Create a formatted string for the user
	        String userInfo = String.format("ID: %d, Username: %s, Email: %s, First Name: %s, Middle Name: %s, Last Name: %s, Preferred First Name: %s, Role: %s",
	                id, username, email, firstName, middleName, lastName, prefFirstName, role);
	        
	        userList.add(userInfo); // Add the user info to the list
	    }
	    return userList; // Return the list of user info strings
	}


	
	//gets the role from the current logged in user.
	public String getRole(String username) throws SQLException{
		String role = null;
	    
	    // Define the SQL query to get the user's role
	    String sql = "SELECT role FROM cse360users WHERE username = ?";
	    PreparedStatement pstmt = connection.prepareStatement(sql);
	    pstmt.setString(1, username);
	    
	    // Execute the query and retrieve the role
	    ResultSet rs = pstmt.executeQuery();
	    
	    if (rs.next()) {
	        role = rs.getString("role");
	    }
	    
	    rs.close();
	    pstmt.close();
	    
	    return role;
	}
	
	//function that checks if the account/ user has fully setup there account. it just returns a boolean true or false
	public boolean hasSetupAccount(String username) throws SQLException{
		boolean completedReg = false;
		String sql = "SELECT completedReg FROM cse360users WHERE username = ?";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, username);
		
		ResultSet rs = pstmt.executeQuery();
		
		if(rs.next()) {
			completedReg = rs.getBoolean("completedReg");
		}
		
		rs.close();
		pstmt.close();		
		return completedReg;
	}
	
	
	//returns if the passwordBeingReset flag is true or false indicating that password is currently being reset or not
	public boolean isPasswordBeingReset(String username) throws SQLException {
	    String query = "SELECT passwordBeingReset FROM cse360users WHERE username = ?";
	    
	    try (PreparedStatement statement = connection.prepareStatement(query)) {
	        statement.setString(1, username);
	        ResultSet resultSet = statement.executeQuery();
	        
	        if (resultSet.next()) {
	            return resultSet.getBoolean("passwordBeingReset");
	        } else {
	            // User not found
	            return false;
	        }
	    }
	}


	
	// Fully sets up the user's account with their information
	public void setupAccount(String username, String email, String firstName, String middleName, String lastName, String prefFirstName) throws SQLException {
	    String updateUserInfo = "UPDATE cse360users SET email = ?, firstName = ?, middleName = ?, lastName = ?, prefFirstName = ?, completedReg = TRUE WHERE username = ?";
	    try (PreparedStatement pstmtUpdate = connection.prepareStatement(updateUserInfo)) {
	        pstmtUpdate.setString(1, email);
	        pstmtUpdate.setString(2, firstName);
	        pstmtUpdate.setString(3, middleName);
	        pstmtUpdate.setString(4, lastName);
	        pstmtUpdate.setString(5, prefFirstName);
	        pstmtUpdate.setString(6, username);

	        // Execute the update query
	        pstmtUpdate.executeUpdate();
	    }
	}
	
	// Function to delete a user account based on username
	public void deleteAccount(String username) throws SQLException {
	    String deleteUser = "DELETE FROM cse360users WHERE username = ?";
	    try (PreparedStatement pstmtDelete = connection.prepareStatement(deleteUser)) {
	        pstmtDelete.setString(1, username);
	        
	        // Execute the delete statement
	        int rowsAffected = pstmtDelete.executeUpdate();
	        
	        // Optional: Check if any rows were deleted
	        if (rowsAffected > 0) {
	            System.out.println("Account deleted successfully.");
	        } else {
	            System.out.println("No account found with the provided username.");
	        }
	    }
	}

	
	
	public String inviteUser(String role) throws SQLException {
	    // Generate a random one-time password (OTP)
	    String oneTimePassword = generateRandomPassword(10); // Generate a 10-character OTP

	    // Insert the new user with the OTP as the username and assign the role
	    String sql = "INSERT INTO cse360users (username, role) VALUES (?, ?)";
	    PreparedStatement pstmt = connection.prepareStatement(sql);
	    pstmt.setString(1, oneTimePassword); // Use the OTP as the username
	    pstmt.setString(2, role);            // Assign the role

	    // Execute the update
	    pstmt.executeUpdate();

	    // Clean up resources
	    pstmt.close();

	    // Return the OTP so it can be shared with the user
	    return oneTimePassword;
	}

	// Helper method to generate a random password
	private String generateRandomPassword(int length) throws SQLException {
	    // Define characters to use in the password
	    String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	    SecureRandom random = new SecureRandom();
	    StringBuilder password = new StringBuilder();

	    for (int i = 0; i < length; i++) {
	        int index = random.nextInt(chars.length());
	        password.append(chars.charAt(index));
	    }

	    return password.toString();
	}
	
	
	public void resetUserPassword(String username) throws SQLException {
	    String setOTP = "UPDATE cse360users SET passwordBeingReset = ?, otp = ?, otpExpiry = ?, otpUsed = ? WHERE username = ?";
	    
	    boolean passwordBeingReset = true;
	    String otp = generateRandomPassword(10); // Ensure this method generates a valid OTP
	    long expiryTimeMillis = System.currentTimeMillis() + (15 * 60 * 1000); // 15 minutes
	    Timestamp otpExpiry = new Timestamp(expiryTimeMillis);
	    boolean otpUsed = false;

	    try (PreparedStatement pstmtResetUserPassword = connection.prepareStatement(setOTP)) {
	        pstmtResetUserPassword.setBoolean(1, passwordBeingReset);
	        pstmtResetUserPassword.setString(2, otp);
	        pstmtResetUserPassword.setTimestamp(3, otpExpiry);
	        pstmtResetUserPassword.setBoolean(4, otpUsed);
	        pstmtResetUserPassword.setString(5, username);

	        // Execute the update query
	        pstmtResetUserPassword.executeUpdate();
	        System.out.println("OTP: " + otp);
	    }
	}
	
	public void updateUserPassword(String username, String newPassword) throws SQLException {
	    String updatePasswordQuery = "UPDATE cse360users SET password = ?, passwordBeingReset = ? WHERE username = ?";

	    // Assuming you want to reset the passwordBeingReset flag back to false after the password has been updated
	    boolean passwordBeingReset = false;

	    try (PreparedStatement pstmt = connection.prepareStatement(updatePasswordQuery)) {
	        pstmt.setString(1, newPassword);
	        pstmt.setBoolean(2, passwordBeingReset);
	        pstmt.setString(3, username);
	        
	        // Execute the update query
	        int rowsUpdated = pstmt.executeUpdate();
	        
	        if (rowsUpdated > 0) {
	            System.out.println("Password updated successfully for user: " + username);
	        } else {
	            System.out.println("No user found with username: " + username);
	        }
	    } catch (SQLException e) {
	        System.err.println("Error updating password for user: " + username);
	        e.printStackTrace();
	        throw e; // Rethrow the exception for further handling if needed
	    }
	}



	public boolean check_OtpExpiry(String username, String otp) throws SQLException {
	    String query = "SELECT otpExpiry, otpUsed FROM cse360users WHERE username = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        pstmt.setString(1, username);
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            Timestamp otpExpiry = rs.getTimestamp("otpExpiry");
	            boolean otpUsed = rs.getBoolean("otpUsed");

	            // Check if the OTP has been used or has expired
	            if (!otpUsed && otpExpiry != null && otpExpiry.after(new Timestamp(System.currentTimeMillis()))) {
	                // Optionally, mark the OTP as used after successful validation
	                markOtpAsUsed(username); // This is an additional step, depending on your needs
	                return true;
	            }
	        }
	    }	
	    return false;
	}
	
	private void markOtpAsUsed(String username) throws SQLException {
	    String updateOtpUsed = "UPDATE cse360users SET otpUsed = ? WHERE username = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(updateOtpUsed)) {
	        pstmt.setBoolean(1, true);
	        pstmt.setString(2, username);
	        pstmt.executeUpdate();
	    }
	}

	
	public void closeConnection() {
		try{ 
			if(statement!=null) statement.close(); 
		} catch(SQLException se2) { 
			se2.printStackTrace();
		} 
		try { 
			if(connection!=null) connection.close(); 
		} catch(SQLException se){ 
			se.printStackTrace(); 
		} 
	}

}
