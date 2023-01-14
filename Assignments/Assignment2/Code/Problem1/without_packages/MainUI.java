import java.io.File;
import java.util.Scanner;

public class MainUI {
	//Main method to implement different actions to given by the user
	public static void main( String[] args ) throws Exception {
		Scanner in = new Scanner( System.in );
		System.out.println("Input file name please:");
		String input = in.next();
		System.out.println("Output filename please:");
		String output = in.next();
		System.out.println("Enter 'E' for encode, 'D' for Decode");
		char option = in.next().charAt(0);

		if(!new File(input).exists()) {
			System.out.println("File not found");
			in.close();
			return;
		}
		//Switch case to implament particular encode or decode
		switch(option) {
			case 'E' :
				encode(input, output);
				break;
			case 'D' :
				decode(input, output);
				break;

			default:
				System.out.println("Invalid Option");
		}
		in.close();
	}
	//Method to call encode
	static void encode(String input, String output) throws Exception {
		Huffman filecompressor= new Huffman();
		//System.out.println("First");
		if(filecompressor.encode(input, 0 , false, output)) {
			System.out.println("Given file has been encoded Successfully. Please open the output file");
		}

	}
	//Method to call decode
	static void decode(String input, String output) throws Exception {
		Huffman filecompressor= new Huffman();
		if(filecompressor.decode(input, output)) {
			System.out.println("Given file has been decoded Successfully. Please open the output file");
		}
	}


}
