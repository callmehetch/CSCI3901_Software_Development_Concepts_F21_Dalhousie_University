package assignment;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int input = 0;
        Boggle boggle = new Boggle();
        while(input!=5){
            System.out.println("Enter a choice: 1)getDictionary 2)getPuzzle 3)solve 4)print 5)Exit");
            input = scan.nextInt();
            switch (input){
                case 1:
                    System.out.println("Enter the dictionary: ");
                    FileReader in = null;
                    try {
                        in = new FileReader("H:\\DAL\\CSCI3901\\ASSIGNMENTS\\4\\A4A\\src\\assignment\\d.txt");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    BufferedReader dr = new BufferedReader(in);
                    System.out.println(boggle.getDictionary(dr));
                    break;
                case 2:
                    System.out.println("Enter the puzzle: ");
                    FileReader pn = null;
                    try {
                        pn = new FileReader("H:\\DAL\\CSCI3901\\ASSIGNMENTS\\4\\A4A\\src\\assignment\\p.txt");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    BufferedReader pr = new BufferedReader(pn);
                    System.out.println(boggle.getPuzzle(pr));
                    break;
                case 3:
                    System.out.println("---------Solving the Puzzle----------------");
                    System.out.println(boggle.solve());
                    break;
                case 4:
                    System.out.println("--------------Printing ----------------");
                    System.out.print(boggle.print());
                    break;
            }
        }

    }
}
