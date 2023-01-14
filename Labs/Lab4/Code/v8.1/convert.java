import java.util.Scanner;

public class convert {
	public static void main( String[] args ) {
		String filename = new String();
		Scanner userInput = new Scanner( System.in );
		htmlTranslator translate = new htmlTranslator();

		// Repeatedly get files to convert until the user says "quit"

		while (!filename.equalsIgnoreCase("quit")) {

			// Get the filename from the user

			System.out.println( "filename? " );
			filename = userInput.next();

			// Ensure that the user provided some input.  Using a scanner,
			// the system should keep asking for input until it gets 
			// something.
	
			if (filename.length() > 0) {
				translate.translateFile( filename );
			} else {
				System.out.println( "No filename provided." );
			}
		}

		// Close the input stream
		userInput.close();
	}
}

