
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.control.CheckBox;


public class project extends Application {
	
	private Stage stage;
	private Scene scene;
	private BorderPane borderPane;

	private VBox vbox1;
	private Button button1username;
	private Button button1InviteCode;
	private Button buttonTest;

	private VBox vbox2;
	private Button button2;
	
	private VBox vbox3;
	private Button button3;
	
	private VBox vbox4;
	private Button button4;
	
	private VBox vbox5;
	private Button button5Invite;
	private Button button5Reset;
	private Button button5Delete;
	private Button button5Show;
	private Button button5Add;
	private Button button5Remove;
	private Button button5Logout;
	
	private VBox vbox6;
	private Button button6;
	
	private VBox vbox7;
	private Button button7;
	
	private VBox vbox8;
	private Button button8;
	
	private VBox vbox9;
	private Button button9;
	
	private VBox vbox10;
	private Button button10A;
	private Button button10S;
	
	private VBox vbox11;
	private Button button11S;
	private Button button11I;
	
	private VBox vbox12;
	private Button button12A;
	private Button button12I;
	
	private VBox vbox13;
	private Button button13A;
	private Button button13I;
	private Button button13S;
	
	private VBox vbox14;
	private Button button14;
	
	private VBox vbox15;
	private Button button15;

	private VBox vbox16;
	private Button button16;
	
	private VBox vbox17;
	private Button button17;
	
	private VBox vbox18;
	private Button button18;

	private VBox vbox19;
	private Button button19;
	
	private VBox vbox20;
	private Button button20;
	
	public void start(Stage primaryStage) throws Exception {

		stage = primaryStage;
		stage.setTitle("Program");

		//Creates a vbox for all the different pages
		vbox1 = createLoginPage();
		vbox2 = createUserCheckPage();
		vbox3 = createRegistration();
		vbox4 = createSetUpPage();
		vbox5 = createAdminPage();
		vbox6 = createInviteCodePage();
		vbox7 = createSignInPage();
		vbox8 = createStudentPage();
		vbox9 = createInstructorPage();
		vbox10 = createSelectRoleAS();
		vbox11 = createSelectRoleSI();
		vbox12 = createSelectRoleAI();
		vbox13 = createSelectRoleALL();
		vbox14 = createAddRole();
		vbox15 = createPassResetEnterUser();
		vbox16 = createDeleteUserEnterUser();
		vbox17 = createShowUsers();
		vbox18 = createAddRoleEnterUser();
		vbox19 = createRemoveRoleEnterUser();
		vbox20 = createRemoveRole();
		
		borderPane = new BorderPane();
		
		borderPane.setCenter(vbox1);

		scene = new Scene(borderPane, 800, 500);

		stage.setScene(scene);

		stage.show();
	}
	private VBox createLoginPage() {

	    // Create display text
	    Text welcomeText = new Text("Welcome!");
	    welcomeText.setStyle("-fx-fill: white; -fx-font-size: 40px; -fx-font-family: 'Verdana';");

	    // Creates buttons
	    button1username = new Button("Have a Username?");
	    button1username.setOnAction(e->switchPanes(vbox2));
	    
	    button1InviteCode = new Button("Have an Invite Code?");
	    button1InviteCode.setOnAction(e->switchPanes(vbox6));
	    
	    buttonTest = new Button("Test");
	    buttonTest.setOnAction(e->switchPanes(vbox13));
	    
	    // Create a VBox with the text and buttons
	    vbox1 = new VBox(10, welcomeText, button1username, button1InviteCode, buttonTest);
	    vbox1.setStyle("-fx-background-color: seagreen");
	    vbox1.setAlignment(Pos.CENTER);

	    return vbox1;
	}
	
	private VBox createUserCheckPage() {
		
		// Create display text
		Text welcomeText = new Text("Enter Username:");
	    welcomeText.setStyle("-fx-fill: white; -fx-font-size: 20px; -fx-font-family: 'Verdana';");
		
	    // Create button
		button2 = new Button("Continue");
		button2.setOnAction(e -> switchPanes(vbox7));
		vbox2 = new VBox(button2);
		
	    // Create text fields
		TextField usernameField = new TextField();
		
	    // Create a VBox with the text, text fields, and button
	    vbox2 = new VBox(10, welcomeText, usernameField, button2);
		vbox2.setStyle("-fx-background-color: seagreen");
		vbox2.setAlignment(Pos.CENTER);

		return vbox2;
	}
	
	private VBox createRegistration() {
		
		// Create display text
	    Text welcomeText = new Text("Create a New Account");
	    welcomeText.setStyle("-fx-fill: white; -fx-font-size: 20px; -fx-font-family: 'Verdana';");

	    Text usernameLabel = new Text("Username:");
	    usernameLabel.setStyle("-fx-fill: white; -fx-font-size: 14px;");

	    Text passLabel = new Text("Password:");
	    passLabel.setStyle("-fx-fill: white; -fx-font-size: 14px;");
	    
	    Text confirmLabel = new Text("Confirm Password:");
	    confirmLabel.setStyle("-fx-fill: white; -fx-font-size: 14px;");

	    // Create text fields
	    TextField usernameField = new TextField();
	    TextField passwordField = new TextField();	    
	    TextField confirmField = new TextField();

	    // Create button
	    button3 = new Button("Continue");
	    button3.setOnAction(e -> switchPanes(vbox1));

	    // Create a VBox with the text, text fields, and button
	    vbox3 = new VBox(10, welcomeText, usernameLabel, usernameField, passLabel, passwordField, confirmLabel, confirmField, button3);
		vbox3.setStyle("-fx-background-color: seagreen");
		vbox3.setAlignment(Pos.CENTER);
		
		return vbox3;
	}
	
	private VBox createSetUpPage() {
		
		// Create display text
	    Text welcomeText = new Text("Finish Setting Up Your New Account");
	    welcomeText.setStyle("-fx-fill: white; -fx-font-size: 25px; -fx-font-family: 'Verdana';");
		
	    Text emailLabel = new Text("Email:");
	    emailLabel.setStyle("-fx-fill: white; -fx-font-size: 20px;");

	    Text firstLabel = new Text("First Name:");
	    firstLabel.setStyle("-fx-fill: white; -fx-font-size: 20px;");
	    
	    Text middleLabel = new Text("Middle Name:");
	    middleLabel.setStyle("-fx-fill: white; -fx-font-size: 20px;");
	    
	    Text lastLabel = new Text("Last Name:");
	    lastLabel.setStyle("-fx-fill: white; -fx-font-size: 20px;");
	    
	    Text preferedLabel = new Text("Prefered First Name:");
	    preferedLabel.setStyle("-fx-fill: white; -fx-font-size: 20px;");
	    
	    // Create text fields
	    TextField emailField = new TextField();
	    TextField firstField = new TextField();
	    TextField middleField = new TextField();
	    TextField lastField = new TextField();    
	    TextField preferedField = new TextField();
	    
	    // Create button
	    button4 = new Button("Continue");
	    //button4.setOnAction(e -> switchPanes());
	    
	    // Create a VBox with the text, text fields, and button
	    vbox4 = new VBox(10, welcomeText, emailLabel, emailField, firstLabel, firstField, middleLabel, middleField, lastLabel, lastField,preferedLabel, preferedField, button4);
	    vbox4.setStyle("-fx-background-color: seagreen");
	    vbox4.setAlignment(Pos.CENTER);
	    
		return vbox4;
	}
	
	private VBox createAdminPage(){
		// Create display text
		Text welcomeText = new Text("Admin Options");
	    welcomeText.setStyle("-fx-fill: white; -fx-font-size: 20px; -fx-font-family: 'Verdana';");
	
		//Create buttons
		button5Invite = new Button("Invite New User");
		button5Invite.setOnAction(e->switchPanes(vbox14));
		
		button5Reset = new Button("Reset Password");
		button5Reset.setOnAction(e->switchPanes(vbox15));
		
		button5Delete = new Button("Delete User");
		button5Delete.setOnAction(e->switchPanes(vbox16));
		
		button5Show = new Button("Show Users");
		button5Show.setOnAction(e->switchPanes(vbox17));
		
		button5Add = new Button("Add Role");
		button5Add.setOnAction(e->switchPanes(vbox18));
		
		button5Remove = new Button("Remove Role");
		button5Remove.setOnAction(e->switchPanes(vbox19));
		
		button5Logout= new Button("Logout");
		button5Logout.setOnAction(e->switchPanes(vbox1));
		
	    // Create a VBox with the text and buttons
	    vbox5 = new VBox(10, welcomeText, button5Invite, button5Reset, button5Delete, button5Show, button5Add, button5Remove,button5Logout);
		vbox5.setStyle("-fx-background-color: seagreen");
	    vbox5.setAlignment(Pos.CENTER);
		
		return vbox5;
	}
	
	private VBox createInviteCodePage() {
		// Create display text
		Text welcomeText = new Text("Enter Invite Code:");
	    welcomeText.setStyle("-fx-fill: white; -fx-font-size: 20px; -fx-font-family: 'Verdana';");
	    
		//Create button
	    button6 = new Button("Continue");
	    button6.setOnAction(e->switchPanes(vbox3));
	    
	    // Create text field
	    TextField inviteField = new TextField();
		
	    // Create a VBox with the text, text fields, and button
		vbox6= new VBox(10, welcomeText, inviteField, button6);
	    vbox6.setStyle("-fx-background-color: seagreen");
	    vbox6.setAlignment(Pos.CENTER);
	    
		return vbox6;
	}
	
	private VBox createSignInPage(){
		// Create display text
		Text welcomeText = new Text("Sign In");
	    welcomeText.setStyle("-fx-fill: white; -fx-font-size: 30px; -fx-font-family: 'Verdana';");
		
	    Text passwordText = new Text("Enter Password:");
	    passwordText.setStyle("-fx-fill: white; -fx-font-size: 20px; -fx-font-family: 'Verdana';");
	    
		//Create button
	    button7 = new Button("Continue");
	    button7.setOnAction(e->switchPanes(vbox4));
		
	    // Create text field
	    TextField passwordField = new TextField();
	    
	    // Create a VBox with the text, text fields, and button
	    vbox7 = new VBox(10, welcomeText, passwordText, passwordField, button7);
		vbox7.setStyle("-fx-background-color: seagreen");
	    vbox7.setAlignment(Pos.CENTER);
		
		return vbox7;
	}
	
	private VBox createStudentPage() {
		// Create display text
		Text welcomeText = new Text("Student Options");
	    welcomeText.setStyle("-fx-fill: white; -fx-font-size: 20px; -fx-font-family: 'Verdana';");
		
		//Create button
		button8 = new Button("Log Out");
		button8.setOnAction(e ->switchPanes(vbox1));
		
	    // Create a VBox with the text and button
		vbox8 = new VBox(10, welcomeText, button8);
		vbox8.setStyle("-fx-background-color: seagreen");
	    vbox8.setAlignment(Pos.CENTER);
		
		return vbox8;
	}
	
	private VBox createInstructorPage() {
		// Create display text
		Text welcomeText = new Text("Instructor Options");
	    welcomeText.setStyle("-fx-fill: white; -fx-font-size: 20px; -fx-font-family: 'Verdana';");
		
	    //Create button
		button9 = new Button("Log Out");
		button9.setOnAction(e ->switchPanes(vbox1));
		
	    // Create a VBox with the text and button
		vbox9 = new VBox(10, welcomeText, button9);
		vbox9.setStyle("-fx-background-color: seagreen");
	    vbox9.setAlignment(Pos.CENTER);
		
		return vbox9;
	}
	
	private VBox createSelectRoleAS() {
		// Create display text
		Text welcomeText = new Text("Select Appropriate Role");
	    welcomeText.setStyle("-fx-fill: white; -fx-font-size: 20px; -fx-font-family: 'Verdana';");
		
	    //Create buttons
	    button10A = new Button("Admin");
		button10A.setOnAction(e ->switchPanes(vbox5));
		
		button10S = new Button("Student");
		button10S.setOnAction(e ->switchPanes(vbox8));
		
	    // Create a VBox with the text and buttons
		vbox10 = new VBox(10, welcomeText, button10A, button10S);
		vbox10.setStyle("-fx-background-color: seagreen");
	    vbox10.setAlignment(Pos.CENTER);
	    
		return vbox10;
	}
	
	private VBox createSelectRoleSI() {
		// Create display text
		Text welcomeText = new Text("Select Appropriate Role");
	    welcomeText.setStyle("-fx-fill: white; -fx-font-size: 20px; -fx-font-family: 'Verdana';");
		
	    //Create buttons
	    button11I = new Button("Instructor");
		button11I.setOnAction(e ->switchPanes(vbox9));
		
		button11S = new Button("Student");
		button11S.setOnAction(e ->switchPanes(vbox8));
		
	    // Create a VBox with the text and buttons
		vbox11 = new VBox(10, welcomeText, button11I, button11S);
		vbox11.setStyle("-fx-background-color: seagreen");
	    vbox11.setAlignment(Pos.CENTER);
	    
		return vbox11;
	}
	
	private VBox createSelectRoleAI() {
		// Create display text
		Text welcomeText = new Text("Select Appropriate Role");
	    welcomeText.setStyle("-fx-fill: white; -fx-font-size: 20px; -fx-font-family: 'Verdana';");
		
	    //Create buttons
	    button12A = new Button("Admin");
		button12A.setOnAction(e ->switchPanes(vbox5));
		
		button12I = new Button("Instructor");
		button12I.setOnAction(e ->switchPanes(vbox9));
		
	    // Create a VBox with the text and buttons
		vbox12 = new VBox(10, welcomeText, button12A, button12I);
		vbox12.setStyle("-fx-background-color: seagreen");
	    vbox12.setAlignment(Pos.CENTER);
		
		return vbox12;
	}
	
	private VBox createSelectRoleALL() {
		// Create display text
		Text welcomeText = new Text("Select Appropriate Role");
	    welcomeText.setStyle("-fx-fill: white; -fx-font-size: 20px; -fx-font-family: 'Verdana';");
		
	    //Create buttons
	    button13A = new Button("Admin");
		button13A.setOnAction(e ->switchPanes(vbox5));
		
		button13I = new Button("Instructor");
		button13I.setOnAction(e ->switchPanes(vbox9));
		
		button13S = new Button("Student");
		button13S.setOnAction(e ->switchPanes(vbox8));
		
		// Create a VBox with the text and buttons
		vbox13 = new VBox(10, welcomeText, button13A, button13I, button13S);
		vbox13.setStyle("-fx-background-color: seagreen");
	    vbox13.setAlignment(Pos.CENTER);
		
		return vbox13;
	}
	
	private VBox createAddRole() {
		// Create display text
		Text welcomeText = new Text("Add Role");
	    welcomeText.setStyle("-fx-fill: white; -fx-font-size: 20px; -fx-font-family: 'Verdana';");
		
	    //Create button
		button14 = new Button("Continue");
		button14.setOnAction(e ->switchPanes(vbox5));
		
		//Create check boxes
		CheckBox cbAdmin = new CheckBox("Admin");
		cbAdmin.setIndeterminate(false);
		cbAdmin.setStyle("-fx-text-fill: white;");
		
		CheckBox cbInstructor = new CheckBox("Instructor");
		cbInstructor.setIndeterminate(false);
		cbInstructor.setStyle("-fx-text-fill: white;");
		
		CheckBox cbStudent = new CheckBox("Student");
		cbStudent.setIndeterminate(false);
		cbStudent.setStyle("-fx-text-fill: white;");

		// Create a VBox with the text, check boxes, and button
		vbox14 = new VBox(10, welcomeText, cbAdmin, cbInstructor, cbStudent, button14);
		vbox14.setStyle("-fx-background-color: seagreen");
	    vbox14.setAlignment(Pos.CENTER);
		
		return vbox14;
	}
	
	private VBox createPassResetEnterUser() {
		// Create display text
		Text welcomeText = new Text("Enter Username:");
	    welcomeText.setStyle("-fx-fill: white; -fx-font-size: 20px; -fx-font-family: 'Verdana';");
	  
	    //Create button
	    button15 = new Button("Continue");
	    button15.setOnAction(e->switchPanes(vbox5));
	 
	    // Create text field
	    TextField usernameField = new TextField();
	    
	    // Create a VBox with the text, text fields, and button
	    vbox15 = new VBox(10, welcomeText, usernameField, button15);
	    vbox15.setStyle("-fx-background-color: seagreen");
	    vbox15.setAlignment(Pos.CENTER);
		
		return vbox15;
	}
	
	private VBox createDeleteUserEnterUser() {
		// Create display text
		Text welcomeText = new Text("Enter Username:");
	    welcomeText.setStyle("-fx-fill: white; -fx-font-size: 20px; -fx-font-family: 'Verdana';");
	    
	    //Create button
	    button16 = new Button("Continue");
	    button16.setOnAction(e->switchPanes(vbox5));
	 
	    // Create text field
	    TextField usernameField = new TextField();
	    
	    // Create a VBox with the text, text fields, and button
	    vbox16 = new VBox(10, welcomeText, usernameField, button16);
	    vbox16.setStyle("-fx-background-color: seagreen");
	    vbox16.setAlignment(Pos.CENTER);
		
		return vbox16;
	}

	private VBox createShowUsers() {
		// Create display text
		Text welcomeText = new Text("Users");
	    welcomeText.setStyle("-fx-fill: white; -fx-font-size: 20px; -fx-font-family: 'Verdana';");
	  
	    //Create button
	    button17 = new Button("Continue");
	    button17.setOnAction(e->switchPanes(vbox5));
	    
	    // Create a VBox with the text and button
	    vbox17 = new VBox(10, welcomeText, button17);
	    vbox17.setStyle("-fx-background-color: seagreen");
	    vbox17.setAlignment(Pos.CENTER);
		
		return vbox17;
	}
	
	private VBox createAddRoleEnterUser() {
		// Create display text
		Text welcomeText = new Text("Enter Username:");
	    welcomeText.setStyle("-fx-fill: white; -fx-font-size: 20px; -fx-font-family: 'Verdana';");
	  
	    //Create button
	    button18 = new Button("Continue");
	    button18.setOnAction(e->switchPanes(vbox14));
	 
	    // Create text field
	    TextField usernameField = new TextField();
	    
	    // Create a VBox with the text, text fields, and button
	    vbox18 = new VBox(10, welcomeText, usernameField, button18);
	    vbox18.setStyle("-fx-background-color: seagreen");
	    vbox18.setAlignment(Pos.CENTER);
		
		return vbox18;
	}
	
	private VBox createRemoveRoleEnterUser() {
		// Create display text
		Text welcomeText = new Text("Enter Username:");
	    welcomeText.setStyle("-fx-fill: white; -fx-font-size: 20px; -fx-font-family: 'Verdana';");
	  
	    //Create button
	    button19 = new Button("Continue");
	    button19.setOnAction(e->switchPanes(vbox20));
	 
	    // Create text field
	    TextField usernameField = new TextField();
	    
	    // Create a VBox with the text, text fields, and button
	    vbox19 = new VBox(10, welcomeText, usernameField, button19);
	    vbox19.setStyle("-fx-background-color: seagreen");
	    vbox19.setAlignment(Pos.CENTER);
		
		return vbox19;
	}
	
	private VBox createRemoveRole() {
		// Create display text
		Text welcomeText = new Text("Remove Role");
	    welcomeText.setStyle("-fx-fill: white; -fx-font-size: 20px;");
	  
	    //Create button
		button20 = new Button("Continue");
		button20.setOnAction(e ->switchPanes(vbox5));
		
		//Create check boxes
		CheckBox cbAdmin = new CheckBox("Admin");
		cbAdmin.setIndeterminate(false);
		cbAdmin.setStyle("-fx-text-fill: white;");
		
		CheckBox cbInstructor = new CheckBox("Instructor");
		cbInstructor.setIndeterminate(false);
		cbInstructor.setStyle("-fx-text-fill: white;");
		
		CheckBox cbStudent = new CheckBox("Student");
		cbStudent.setIndeterminate(false);
		cbStudent.setStyle("-fx-text-fill: white;");
		
		// Create a VBox with the text, check boxes, and buttons
		vbox20 = new VBox(10, welcomeText, cbAdmin, cbInstructor, cbStudent, button20);
		vbox20.setStyle("-fx-background-color: seagreen");
	    vbox20.setAlignment(Pos.CENTER);
		
		return vbox20;
	}

	// Switch Layout Panes in Center of BorderPane
	public void switchPanes(Pane pane) {
		borderPane.setCenter(pane);
	}

	public static void main(String[] args) {
		Application.launch(args);

	}
}