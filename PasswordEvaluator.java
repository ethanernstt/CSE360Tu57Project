package simpleDatabase;


public class PasswordEvaluator {
	//allocated memory of variables that we use for testing characteristics of the password
	public static String passwordErrorMessage = "";
	public static String passwordInput = "";
	public static int passwordIndexofError = -1;
	public static boolean foundUpperCase = false;
	public static boolean foundLowerCase = false;
	public static boolean foundNumericDigit = false;
	public static boolean foundSpecialChar = false;
	public static boolean foundLongEnough = false;
	private static String inputLine = "";
	private static char currentChar;
	private static int currentCharNdx;
	private static boolean running;

	private static void displayInputState() { //displays the input state so we can see if there is a problem wit the program itself, the password or both.
		System.out.println(inputLine);
		System.out.println(inputLine.substring(0,currentCharNdx) + "?");
		System.out.println("The password size: " + inputLine.length() + "  |  The currentCharNdx: " + 
				currentCharNdx + "  |  The currentChar: \"" + currentChar + "\"");
	}

	public static String evaluatePassword(String input) {
		passwordErrorMessage = "";
		passwordIndexofError = 0;
		inputLine = input;
		currentCharNdx = 0;
		
		if(input.length() <= 0) return "*** Error *** The password is empty!";
		
		currentChar = input.charAt(0);		// The current character from the above indexed position

		passwordInput = input;
		foundUpperCase = false;
		foundLowerCase = false;	
		foundNumericDigit = false;
		foundSpecialChar = false;
		foundNumericDigit = false;
		foundLongEnough = false;
		running = true;

		while (running) {
			displayInputState();
			//character / digit Identifier
			if (currentChar >= 'A' && currentChar <= 'Z') {  //UpperCase Identifier
				System.out.println("Upper case letter found");
				foundUpperCase = true;
			} else if (currentChar >= 'a' && currentChar <= 'z') { //LowerCase Identifier
				System.out.println("Lower case letter found");
				foundLowerCase = true;
			} else if (currentChar >= '0' && currentChar <= '9') { //Digit Identifier
				System.out.println("Digit found");
				foundNumericDigit = true;
			} else if ("~`!@#$%^&*()_-+={}[]|\\:;\"'<>,.?/".indexOf(currentChar) >= 0) { //Special Character Identifier
				System.out.println("Special character found");
				foundSpecialChar = true;
			} else {
				passwordIndexofError = currentCharNdx;	//last case: Error Identifier
				return "*** Error *** An invalid character has been found!"; 
			}
			if (currentCharNdx >= 8) { // Password length Identifier
				System.out.println("At least 8 characters found"); //need at least 8 character long including Upper case, Lower case, Digit and Special
				foundLongEnough = true;
			}
			currentCharNdx++; // current character index is moved 1 position
			if (currentCharNdx >= inputLine.length()) // if current character index is longer or equal to the length of the password length we change the 'running' variable to false so we can stop the program
				running = false;
			else
				currentChar = input.charAt(currentCharNdx); // if the current character index is not longer or equal to the length then we proceed
			
			System.out.println(); // prints as space at the end.
		}
		
		String errMessage = ""; //empty variable for the error message that will be displayed if error is found
		if (!foundUpperCase) //if there is no character is found with an upper case then we display the no upper case error message
			errMessage += "Upper case; ";
		
		if (!foundLowerCase) // No lowercase character found error message
			errMessage += "Lower case; ";
		
		if (!foundNumericDigit) // No Digit character found error message
			errMessage += "Numeric digits; ";
			
		if (!foundSpecialChar) // No special character found error message
			errMessage += "Special character; ";
			
		if (!foundLongEnough) // Not long enough password error message
			errMessage += "Long Enough; ";
		
		if (errMessage == "")// if we finish the password Evaluator with out any error messages then we return "" which is nothing.
			return "";
		
		passwordIndexofError = currentCharNdx; //password error index to help identify errors in the password so the user can change the password
		return errMessage + "conditions were not satisfied"; //prints what conditions were not satisfies for the user

	}
}
