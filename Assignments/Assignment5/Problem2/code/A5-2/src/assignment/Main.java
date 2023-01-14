package assignment;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter the startDate in (YYYY-MM-DD): ");
        //String startYear = "2003-04-01";
        String startYear = scan.nextLine();  // Read user input
        System.out.println("Enter the endDate in (YYYY-MM--DD): ");
        //String endYear = "2005-02-28";
        String endYear = scan.nextLine();
        System.out.println("Enter the filePath: ");
        //String filePath = "H:\\DAL\\CSCI3901\\ASSIGNMENTS\\5\\A5-2\\src\\assignment\\assignment.xml";
        String filePath = scan.nextLine();
        System.out.println("Given details are: startDate: " + startYear + " endDate: " + endYear);
        CreateXMLFile M1 = new CreateXMLFile();
        System.out.println(M1.mySQLQuery(startYear, endYear, filePath));
    }
}
