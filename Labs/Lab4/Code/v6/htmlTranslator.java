import java.util.Scanner;
import java.io.FileReader;
import java.io.InputStream;
import java.io.BufferedReader;
import java.util.Stack;
import java.util.Map;
import java.util.Set;

public class htmlTranslator {
	private static final String titleMarker = "title: ";
	private static final String listStartMarker = "-";
	private static final int NOTHING = -1;
	private static final int PARAGRAPH = 1;
	private static final int UNORDEREDLIST = 2;
	private static final int BOLD = 3;
	private static final int UNDERLINE = 4;
	private static final int ITALICIZE = 5;
	private static final int LISTITEM = 6;

	private static Map<Integer, String> tagConvert = Map.of( 
		  PARAGRAPH, "p"
		, UNORDEREDLIST, "ul"
		, BOLD, "b"
		, UNDERLINE, "u"
		, ITALICIZE, "i" 
		, LISTITEM, "li"
		);
	private static Map<Character, Integer> pairFormatSymbols = Map.of( 
		  '*', BOLD
		, '%', UNDERLINE
		, '_', ITALICIZE
		);

	private static Map<Character, Integer> lineFormatSymbols = Map.of( 
		  '-', LISTITEM
		);

	private static final int NoPart = 1;
	private static final int InParagraph = 2;
	private static final int InUnorderedList = 3;

	private int blockStatus = NoPart;
	private Stack<Integer> tagList = new Stack<Integer>();

	// Determine if a given input line matches the format for a title
	// for the document;

	private static Boolean isTitleLine( String line ) {
		Boolean titleLine = false;

		// Start with the simple requirement that the title
		// keyword must start the line.

		if ((line.length() >= titleMarker.length()) && (titleMarker.equals(line.substring(0, titleMarker.length())))) {
			titleLine = true;
		}

		return titleLine;
	}

	// Take as a precondition that we have determined this line to
	// be a proper start to the title header line.
	// The CMS only allows a single header line for the file, so
	// we can take a simple approach to printing the content.
	// We are also told that there are no formatting codes in the title
	// line, so we can take the line text as-is as the title.

	private static void translateTitleLine( String line ) {
		System.out.print("<head>\n<title>");
		System.out.print(line.substring(titleMarker.length(), line.length()));
		System.out.println("</title>\n</head>");
	}

	// Close all tags of a paragraph or an unordered list as if that block is ending.
	// Indicate what should be the last tag removed from the list to stop early if we
	// want to just close tags up to some point in the stack for proper nesting.
	// send the value of NOTHING if you want to empty the stack.

	private void closeTextTags( int lastPop ) {
		int theTag;

		if (!tagList.empty()) {
			// We're now given that there must be some tag for us to close, which
			// lets us do a do...while loop so we can check the last item out
			// before continuing.
			do {
				theTag = tagList.pop();
				System.out.println( "</"+tagConvert.get(theTag)+">");
			} while ( !tagList.empty() && (theTag != lastPop) );
		}
	}

	// Determine whether or not a line of text is an item for a list.

	private Boolean isListItem( String line ) {
		Boolean isList = false;

		// All list items are defined to start with a particular list marker 
		// at the start of the line.

		if (listStartMarker.equals(line.substring(0, 1))) {
			isList = true;
		}

		return isList;
	}

	// Translate a single line of a file from the CMS notation to
	// html 1.0 format.  The method uses accumulated context from
	// previous lines, which piles up in the class' attributes.

	private void translateBodyLine( String line ) {
		Set<Character> pairCharacters = pairFormatSymbols.keySet();
		Set<Character> lineCharacters = lineFormatSymbols.keySet();
		int lineItem = NOTHING;
		int lineStart = 0;

		if (line.length() > 0) {
			// Check to see if we're starting any block of text in the body
			if (blockStatus == NoPart) {
				int toStart = PARAGRAPH; // default block of text

				blockStatus = InParagraph;
				// The only other kind of block is an unordered list.  Check for that.
				if (isListItem(line)) {
					toStart = UNORDEREDLIST;
					blockStatus = InUnorderedList;
				}

				// Start the block with appropriate tags and record of nesting.
				System.out.println("<"+tagConvert.get(toStart)+">");
				tagList.push( toStart );
			}


			char[] theLine = line.toCharArray();

			// See if we have any formatting to do as the whole line

			lineItem = NOTHING;
			lineStart = 0;
			if (lineCharacters.contains(theLine[0])) {
				lineItem = lineFormatSymbols.get(theLine[0]);
				System.out.print("<"+tagConvert.get(lineItem)+">");
				tagList.push( lineItem );
				lineStart = 1;
			}
		
			// Now handle the line itself.

			for (int i = lineStart; i < theLine.length; i++) {
				if (pairCharacters.contains(theLine[i])) {
					// Assume that the user is intentional, so they'll open and
					// close pairs of tags in proper order, as best they can.

					int theOperation = pairFormatSymbols.get(theLine[i]);
					if ( theOperation ==  tagList.peek()) {
						// Closing a new nested tag
						System.out.print("</"+tagConvert.get(theOperation)+">");
						tagList.pop();
					} else {
						// Opening a new nested tag
						System.out.print("<"+tagConvert.get(theOperation)+">");
						tagList.push(theOperation);
					}
				} else {
					// Just a regular character, so print it.
					System.out.print( theLine[i] );
				}
			}

			// If we were in a line item, close that one properly.

			if (lineItem != NOTHING) {
				closeTextTags( lineItem );
			} else {
				System.out.println( );
			}
		}
	}

	// Translate the contents of a file from the CMS notation
	// to html 1.0 format.

	public void translateFile( String filename ) {
		BufferedReader userfile = null;
		String inputLine = "";

		try {
			userfile = new BufferedReader( new FileReader( filename ) );

			// The file opened, so include an opening HTML tag

			System.out.println("<html>");

			// Ignore any leading blank lines

			while ( ((inputLine = userfile.readLine()) != null) &&
			        (inputLine.length() == 0)) {
				// Do nothing but consume the line
			}

			// We have a line in memory.  Check for a title line.
			// Process it and then bring in the next line of
			// the file so the upcoming while loop always has
			// a line already loaded.

			if (isTitleLine( inputLine )) {
				translateTitleLine( inputLine );
				inputLine = userfile.readLine();
			}

			// Know that we will have some web page body, even if empty.
			// Start that part of the document.

			System.out.println("<body>");

			// Iterate through the body of the file, one line at a time.
			// Recall that we already have one line in memory.

			while ( inputLine != null) {
				// Blank lines signal a transition.  Close whatever we're in.
				// Make the choice that multiple blank lines in a row will represent a 
				// break in style, rather than multiple empty paragraphs.

				if (inputLine.length() == 0) {
					if (blockStatus != NoPart) {
						closeTextTags( NOTHING );
						blockStatus = NoPart;
					}
				} else {
					translateBodyLine( inputLine );
				}

				inputLine = userfile.readLine();
			}

			// Close any outstanding paragraph or list tags.

			closeTextTags( NOTHING );

			// Close the body and HTML tags

			System.out.println("</body>\n</html>");
		} catch (Exception e) {
			System.out.println( e.getMessage() );
		} finally {
			// Close the file, if it was opened.

			try {
				userfile.close();
			} catch (Exception error) {
				System.out.println( error.getMessage() );
			}
		}
	}
}

