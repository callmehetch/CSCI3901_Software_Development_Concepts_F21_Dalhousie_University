import java.util.Scanner;
import java.io.FileReader;
import java.io.InputStream;
import java.io.BufferedReader;
import java.util.Stack;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.lang.Character;

public class htmlTranslator {
	// Define symbols to use within the program as a way of knowing what we're seeing in the text.

	private static final int NOTHING = -1;
	private static final int PARAGRAPH = 1;
	private static final int UNORDEREDLIST = 2;
	private static final int BOLD = 3;
	private static final int UNDERLINE = 4;
	private static final int ITALICIZE = 5;
	private static final int LISTITEM = 6;
	private static final int BOLDWORD = 7;

	// Define maps of what we will see in the actual text and what they are in our internal symbols.

	private static final String titleMarker = "title: ";

	private static final Map<Character, Integer> pairFormatSymbols = new HashMap<Character, Integer>() {{
		put( '*', BOLD );
		put( '%', UNDERLINE );
		put( '_', ITALICIZE );
	}};

	private static final Map<Character, Integer> lineFormatSymbols = new HashMap<Character, Integer>() {{
		put( '-', LISTITEM );
	}};

	private static final Map<Character, Integer> paragraphFormatSymbols = new HashMap<Character, Integer>() {{
		put( '-', UNORDEREDLIST );
	}};

	private static final Map<Character, Integer> wordFormatSymbols = new HashMap<Character, Integer>() {{
		put( '!', BOLDWORD );
	}};

	// Define how the internal symbols should map to HTML tags.

	private static final Map<Integer, String> tagConvert = new HashMap<Integer, String>() {{
		put( PARAGRAPH, "p" );
		put( UNORDEREDLIST, "ul" );
		put( BOLD, "b" );
		put( BOLDWORD, "b" );
		put( UNDERLINE, "u" );
		put( ITALICIZE, "i"  );
		put( LISTITEM, "li" );
	}};

	private static final Map<Integer, Boolean> inlineTags = new HashMap<Integer, Boolean>() {{
		put( BOLD, true );
		put( BOLDWORD, true );
		put( UNDERLINE, true );
		put( ITALICIZE, true );
	}};

	// Start the actual storage for the class.  These are variables that let us track
	// what we have seen so far in the input file relative to HTML structures.

	private int blockStatus = NOTHING;
	private Stack<Integer> tagList = new Stack<Integer>();
	private int lineItem = NOTHING;
	private int wordOp = NOTHING;



	// Determine if a given input line matches the format for a title
	// for the document;

	private static Boolean isTitleLine( String line ) {
		Boolean titleLine = false;

		// Start with the simple requirement that the title
		// keyword must start the line.

		if ((line.length() >= titleMarker.length()) && 
		    (titleMarker.equals(line.substring(0, titleMarker.length())))) {
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
		Set<Integer> inlineTagsKeys = inlineTags.keySet();

		if (!tagList.empty()) {
			// We're now given that there must be some tag for us to close, which
			// lets us do a do...while loop so we can check the last item out
			// before continuing.
			do {
				theTag = tagList.pop();

				if (inlineTagsKeys.contains(theTag)) {
					System.out.print( "</"+tagConvert.get(theTag)+">");
				} else {
					System.out.println( "</"+tagConvert.get(theTag)+">");
				}
			} while ( !tagList.empty() && (theTag != lastPop) );
		}
	}

	// Determine if we are starting a new block of text with a given line and,
	// if yes, start the appropriate block tags for the output.

	private void checkBlockStart( char[] theLine ) {
		Set<Character> paragraphCharacters = paragraphFormatSymbols.keySet();

		if (blockStatus == NOTHING) {
			blockStatus = PARAGRAPH; // default block of text

			// Check about starting some other kind of block
			if (paragraphCharacters.contains( theLine[0] )) {
				blockStatus = paragraphFormatSymbols.get( theLine[0] );
			}

			// Start the block with appropriate tags and record of nesting.
			System.out.println("<"+tagConvert.get(blockStatus)+">");
			tagList.push( blockStatus );
		}
	}

	// Determine if a new line that we're starting needs any line-level formatting.
	// Return the character in the input line where the meaningful text of the line
	// actually starts.

	private int checkLineStart( char[] theLine ) {
		int lineStart = 0;
		Set<Character> lineCharacters = lineFormatSymbols.keySet();

		// Assume that the line isn't anything special

		lineItem = NOTHING;

		if (lineCharacters.contains(theLine[0])) {
			// We're seeing a special start to the line.  Designiate
			// that line grouping in the output and skip over
			// the leading symbol of the line for the rest of the text.

			lineItem = lineFormatSymbols.get(theLine[0]);
			System.out.print("<"+tagConvert.get(lineItem)+">");
			tagList.push( lineItem );
			lineStart = 1;
		}

		return lineStart;
	}

	// Translate a single line of a file from the CMS notation to
	// html 1.0 format.  The method uses accumulated context from
	// previous lines, which piles up in the class' attributes.

	private void translateBodyLine( String line ) {
		Set<Character> pairCharacters = pairFormatSymbols.keySet();
		Set<Character> wordCharacters = wordFormatSymbols.keySet();

		int lineStart = 0;
		boolean inWord = false;

		if (line.length() > 0) {

			char[] theLine = line.toCharArray();

			// Check to see if we're starting any block of text in the body
			checkBlockStart( theLine );

			// See if we have any formatting to do as the whole line

			lineStart = checkLineStart( theLine );
			inWord = false;

			// Now handle the line itself.

			for (int i = lineStart; i < theLine.length; i++) {
				if (pairCharacters.contains(theLine[i])) {
					// Assume that the user is intentional, so they'll open and
					// close pairs of tags in proper order, as best they can.

					int theOperation = pairFormatSymbols.get(theLine[i]);
					if ( theOperation ==  tagList.peek()) {
						// Closing a new nested tag
						closeTextTags( theOperation );
					} else {
						// Opening a new nested tag
						System.out.print("<"+tagConvert.get(theOperation)+">");
						tagList.push(theOperation);
					}
				} else if (!inWord && (wordCharacters.contains(theLine[i]))) {
					// We have a tag that should be applied to the next word in the text
					// As word tags, treat several of the same indicator to one word
					// as just one instance
					int theOperation = wordFormatSymbols.get(theLine[i]);

					if (!tagList.empty() && theOperation != tagList.peek()) {
						System.out.print("<"+tagConvert.get(theOperation)+">");
						tagList.push(theOperation);
						wordOp = theOperation;
					}
					
				} else {
					// Just a regular character.  Manage formatting that applies just to one word,
					// which requires us to remember when we are in or out of a word.

					if (Character.isWhitespace( theLine[i] )) {
						if (inWord && (wordOp != NOTHING)) {
							// A word has ended.  If we're doing word stuff then close that off.
							closeTextTags( wordOp );
							wordOp = NOTHING;
						}
						inWord = false;
					} else {
						inWord = true;
					}

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

			try {
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
						if (blockStatus != NOTHING) {
							closeTextTags( NOTHING );
							blockStatus = NOTHING;
						}
					} else {
						translateBodyLine( inputLine );
					}
	
					inputLine = userfile.readLine();
				}

				// Close any outstanding paragraph or list tags.

				closeTextTags( NOTHING );

				// Close the body tags
				System.out.println("</body>");

			} catch (Exception e) {
			}

			// Close the body tags
			System.out.println("</html>");
		} catch (Exception e) {
			System.out.println( "Error in processing the file." );
		} finally {
			// Close the file, if it was opened.

			try {
				userfile.close();
			} catch (Exception error) {
			}
		}
	}
}

