package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Lab1{

    public static void main(String args[]){

        // Declaring and initializing important variables.
        int k1;
        String str;
        boolean k;
        Scanner s1 = new Scanner(System.in);  // Scanner to input from the user
        Map m1 = new Map();                   // User defined Map class.

        // Predefined list of commands to operate on the Map
        // PUT / GET / REMOVE / DISPLAY / QUIT
        String put = "put";
        String get = "get";
        String remove = "remove";
        String display = "display";
        String quit = "quit";
        String size = "size";
        String command = "";
    

        System.out.println(" ");
        System.out.println("Welcome to Map implementation");

        //while loop that runs until the Quit command is Requested by the user
        while(!command.equals(quit)){

            //conditional handling of printing of the command only if it's not quit
            if (!command.equals(quit)){
                System.out.println("---------------------------------------");
                System.out.println("Please enter any of the below commands to operate on the Map ");
                System.out.println("-"+put);
                System.out.println("-"+get);
                System.out.println("-"+remove);
                System.out.println("-"+display);
                System.out.println("-"+size);
                System.out.println("-"+quit);

            }

            // Getting input command from the user
            command = s1.nextLine();           // Getting the next command from the user
            command = command.toLowerCase();   // Converting it into lower case for uniformity

            //Switch command executes code based on the input command requested by the user
            switch (command){

                //Put command adds an element into the map
                case "put" : System.out.println(" ");

                    System.out.println("To add an element please enter KEY and VALUE");
                    System.out.print("KEY (int): ");
                    k = s1.hasNextInt(); // To check if the entered value is an integer

                    if(k){
                        k1 = s1.nextInt(); // Get the Key [integer] from the user
                        s1.nextLine();     // handling Enter [next line] for integer
                    }
                    else {
                        System.out.println("Wrong input please enter an integer");
                        s1.nextLine();  // handling Enter [next line] for integer
                        continue;       // Continue from the beginning
                    }

                    System.out.print("VALUE : ");
                    str = s1.nextLine();           // get value from the user

                    m1.put(k1,str);
                    break;

                //Get command gets an element into the map
                case "get":
                    System.out.println("To get an element please enter your key");
                    System.out.print("KEY (int): ");

                    k = s1.hasNextInt(); // To check if the entered value is an integer

                    if(k){
                        k1 = s1.nextInt();  // Get the Key [integer] from the user
                        s1.nextLine();     // handling Enter [next line] for integer
                    }
                    else {
                        System.out.println("Enter an integer");
                        s1.nextLine();  // handling Enter [next line] for integer
                        continue;       // Continue from the beginning
                    }

                    System.out.println("Value :"+m1.get(k1));  // Print out the key to the console
                    break;

                //Remove command deletes the Key Value pair from the List
                case "remove":
                    System.out.println("To Remove an element please enter your key");
                    System.out.print("KEY : ");

                    k = s1.hasNextInt();  // To check if the entered value is an integer

                    if(k){
                        k1 = s1.nextInt();  // Get the Key [integer] from the user
                        s1.nextLine();      // handling Enter [next line] for integer
                    }
                    else {
                        System.out.println("Wrong input Key, Please enter an integer!");
                        s1.nextLine();   // handling Enter [next line] for integer
                        continue;        // Continue from the beginning
                    }

                    m1.remove(k1);       // Remove the item from the list
                    break;

                case "display":
                    m1.displayMap();    // Display the Map items
                    break;
                case "size":
                    m1.size();    // Display the Map items
                    break;
                case "quit":
                    break;              // Quit the Map implementation

                default:
                    System.out.println("Command not recognised Please Enter the commands as presented ");
                    continue;       // Continue from the beginning if invalid command is entered



            }


        }


    }
}


// Class named Map that tries to implement the basic functionalities of the concept Map in java
// Variable :  Arraylist of object[ key : value ] pair.

class Map{

    // Map variables
    private ArrayList<Pair> list = new ArrayList<Pair>();  // arraylist of objects [Key : value] pair.

    // Default constructor do nothing.
    public Map() {
    }

    // Constructor that takes key and value and adds it to the list
    public Map(int key, String value) {
        Pair oj = new Pair(key,value);
        this.list.add(oj);
    }

    // Put function takes the key value pair and compares if there is an existing key.
    // if there is an existing key ot replaces it with the new key value pair.
    public void put(int key, String value){

        Pair oj = new Pair(key,value);  // creating and object of [key:value] pair to add it to list.
        boolean exists = false;             // check if the key exists.

        // loop  for checking if there is an existing key in the list
        for (int i = 0; i < this.list.size(); i++) {

            if(this.list.get(i).getKey() == key){
                exists = true;
                this.list.set(i,oj);  // this will replace the existing index with the new obj [ Key: value ]
                System.out.println(" Element successfully inserted with KEY "+ key);
            }

        }
           // if the key doesn't exist then we will add it to the list
            if (!exists){
                this.list.add(oj);
            }


    }

    // get function get the value of the element on the list based on the user give input [Key].
    public String get(int key){

        // loop to iterate and find the matching key
        for (int i = 0; i < this.list.size(); i++) {

            if(this.list.get(i).getKey() == key){

                return this.list.get(i).getValue();   // get the value of the key
            }
        }
        // else return not found
        return "Doesn't exists";
    }

    // Remove function deletes the item in the list.
    public void remove(int key){

        boolean a = false;  // Case to check if element exists or not

        for (int i = 0; i < this.list.size(); i++) {
            // loop to iterate and find the matching key
            if(this.list.get(i).getKey() == key){
                a = true;
                this.list.remove(i);     // Remove the value of the key
                System.out.println("Element has been Removed : "+ key);
            }
        }

        // if user input key is not present in the list
        if(!a){
            System.out.println("Element not Found");  // if element not found
        }

    }

    // Display function displays all the elements in a list
    public void  displayMap(){

        // Check if there are any elements in the list
        if(this.list.size() == 0){
            System.out.println("No elements present in Map");
        }
        else{
            System.out.println("Total elements in the MAP is "+ this.list.size());
        }

        // loop to iterate and print all the keys in the list.
        for (int i = 0; i < this.list.size(); i++) {

            System.out.println("KEY : "+ this.list.get(i).getKey()+" "+", VALUE : "+this.list.get(i).getValue());

        }

    }


    // Display function displays the size of the MAP
    public void  size(){

        // Check if there are any elements in the list
        if(this.list.size() == 0){
            System.out.println("No elements present in Map");
        }
        else{
            System.out.println("Total elements in the MAP is "+ this.list.size());
        }

    }

}


// A class named obj, whose instances store [key : value] pair.
// it has 2 private variable, getter and setter function to get values when ever necessary
// int key
// String value

class Pair {

    // Variable declaration
    private int key;
    private String value;

    // Constructor declaration
    public Pair(int key, String value) {
        this.key = key;
        this.value = value;
    }

    // Getter function to get key variable data.
    public int getKey() {
        return this.key;
    }

    // Setter function set key varable data.
    public void setKey(int key) {
        this.key = key;
    }

    // Getter function to get value variable data.
    public String getValue() {
        return this.value;
    }

    // Setter function to set key variable data.
    public void setValue(String value) {
        this.value = value;
    }
}
