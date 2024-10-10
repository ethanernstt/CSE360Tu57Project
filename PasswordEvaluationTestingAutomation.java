package simpleDatabase;
public class PasswordEvaluationTestingAutomation {
	
	static int numPassed = 0;
	static int numFailed = 0;
	/** The main method that runs the password testing 
	 * 
	 * @param args The standard input parameter
	 * */
	public static void main(String[] args) {
		System.out.println("____________________________________________________________________________");
		System.out.println("\nTesting Automation");
		
		performTestCase(1, "Aa!156789", true);
		performTestCase(2, "A!", false);
		performTestCase(3, "zz!!5678", false); 		//no uppercase
		performTestCase(4, "Zs!423", false); 		//7 characters
		performTestCase(5, "", false);				//empty string
		performTestCase(6, "ZZ!!5678", false);		//no lowercase
		performTestCase(7, "Za345678", false);		//no special chars
		performTestCase(8, "thisWorks?213", true);	//regular password
		
		
		System.out.println("____________________________________________________________________________");
		System.out.println();
		System.out.println("Number of tests passed: "+ numPassed);
		System.out.println("Number of tests failed: "+ numFailed);
	}

	/** Function to perform tests
	 * @param testCase the number of the test case 
	 * @param inputText the password being tested
	 * @param expectedPass if the test is expected to pass*/
	private static void performTestCase(int testCase, String inputText, boolean expectedPass) {
		//Print formatting
		System.out.println("____________________________________________________________________________\n\nTest case: " + testCase);
		System.out.println("Input: \"" + inputText + "\"");
		System.out.println("______________");
		System.out.println("\nFinite state machine execution trace:");
		
		//Check password validity and store errors in resultText
		String resultText= PasswordEvaluator.evaluatePassword(inputText);
		System.out.println();
		
		//If errors in resultText
		if (resultText != "") {
			if (expectedPass) {
				System.out.println("***Failure*** The password <" + inputText + "> is invalid." + 
						"\nBut it was supposed to be valid, so this is a failure!\n");
				System.out.println("Error message: " + resultText);
				numFailed++;
			}
			else {			
				System.out.println("***Success*** The password <" + inputText + "> is invalid." + 
						"\nBut it was supposed to be invalid, so this is a pass!\n");
				System.out.println("Error message: " + resultText);
				numPassed++;
			}
		}
		//No errors in resultText
		else {	
			if (expectedPass) {	
				System.out.println("***Success*** The password <" + inputText + 
						"> is valid, so this is a pass!");
				numPassed++;
			}
			else {
				System.out.println("***Failure*** The password <" + inputText + 
						"> was judged as valid" + 
						"\nBut it was supposed to be invalid, so this is a failure!");
				numFailed++;
			}
		}
		displayEvaluation();
	}
	/** This method prints which criterion the password meets and fails to meet */
	private static void displayEvaluation() {
		//check upper case
		if (PasswordEvaluator.foundUpperCase)
			System.out.println("At least one upper case letter - Satisfied");
		else
			System.out.println("At least one upper case letter - Not Satisfied");
		
		//check lower case
		if (PasswordEvaluator.foundLowerCase)
			System.out.println("At least one lower case letter - Satisfied");
		else
			System.out.println("At least one lower case letter - Not Satisfied");
	
		//check numerical
		if (PasswordEvaluator.foundNumericDigit)
			System.out.println("At least one digit - Satisfied");
		else
			System.out.println("At least one digit - Not Satisfied");
		
		//check special char
		if (PasswordEvaluator.foundSpecialChar)
			System.out.println("At least one special character - Satisfied");
		else
			System.out.println("At least one special character - Not Satisfied");

		//check 8+ chars
		if (PasswordEvaluator.foundLongEnough)
			System.out.println("At least 8 characters - Satisfied");
		else
			System.out.println("At least 8 characters - Not Satisfied");
	}
}
