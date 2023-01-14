package assignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Boggle {
    //Create places to store all the grid data, dictionary words and row size, column size of the grid
    private char[][] gridValuesFromUser;
    private List<String> dictWords = new ArrayList<String>();
    private int r;
    private int c;

    //-------------------------Method to get Dictionary words-----------------------------------------------------------
    boolean getDictionary(BufferedReader stream){
        //Check if the stream passed is null
        if(stream == null){
            return false;
        }
        String s = null;
        try {
            //Read the stream, line by line as long as it is not null
            while ((s=stream.readLine()) != null){
                //To check if we hit a blank line
                if (s.trim().length() == 0){
                    break;
                }
                //Do not consider words with length less than 2
                if(s.trim().length()<2){
                    continue;
                }
                //Add the words to dictionary words list
                dictWords.add(s.toLowerCase());
            }
            //Exception to catch error while reading stream
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(dictWords.size()<1){
            //System.out.println("No words in dictionary. Return true only if the words are all read and ready to use for puzzle-solving.");
            return false;
        }
        return true;
    }

    //-------------------------Method to get Puzzle Grid----------------------------------------------------------------
    boolean getPuzzle(BufferedReader stream){
        //Check if the stream passed is null
        if(stream == null){
            return false;
        }
        String p = null;
        //Valid length stores the length of first word in puzzle and check if all other words have same length or not
        int validLength = -1;
        List<String> puzzleWords = new ArrayList<String>();
        try {
            //Read the stream, line by line as long as it is not null
            while ((p=stream.readLine()) != null){
                //To check if we hit a blank line
                if (p.trim().length() == 0){
                    break;
                }
                //Do not consider puzzle if any of the words is with length less than 2
                if(p.length() <2){
                    return false;
                }
                //Get length of first word
                if(validLength == -1){
                    validLength = p.trim().length();
                }
                //check if all other words have same length
                if(p.length() != validLength){
                    return false;
                }

                puzzleWords.add(p.toLowerCase());
            }
        }
        //Exception to catch error while reading stream
        catch (IOException e) {
            e.printStackTrace();
        }
        if(puzzleWords.size()<0 || validLength<0){
            //System.out.println("No words in the puzzle. Puzzle can't be used");
            return false;
        }
        //Create a 2D-Matrix to store the words into a Grid with row size as list length and column size as the length of first word
        r = puzzleWords.size();
        c = validLength;
        gridValuesFromUser = new char[r][c];
        for(int i=0; i<r; i++){
            for(int j=0; j<c; j++){
                //Check if all the characters are english letters from a to z only
                int checkAscii = (int)puzzleWords.get(i).charAt(j);
                if(checkAscii>=97 && checkAscii<=122) gridValuesFromUser[i][j] = puzzleWords.get(i).charAt(j);
                else {
                    gridValuesFromUser = new char[r][c];
                    return false;
                }
            }
        }
        return true;
    }

    //Create a place to store Final String to be returned, Direction or path, finalStrings list, and
    private String finalString = "";
    private String direction = "";
    private List<String> finalStringList = new ArrayList<String>();
    //Dictionary set is to store the dictwords if it contains or not
    private Set<String> dictWordsSet = new HashSet<String>();
    //A list to store the position of first letter of a word from dictionary so that if there's another word with the first letter, it doesn't have to traverse all the grid to find that position.
    private Map<Character, List<Integer>> gridPositionMap = new HashMap<Character, List<Integer>>();
    private int[][] sol;
    //static private int iterationCounter = 0;
    List<String> solve(){
        //long startTime = System.currentTimeMillis();
        //Making a new grid with all zeros to prevent revisits to a letter in grid
        sol = new int[r][c];
        for(int i=0; i<r; i++){
            for(int j=0; j<c; j++){
                sol[i][j] = 0;
            }
        }
        //Assume that there are no words found in the grid at first
        int check = 0;
        //Traverse trhough all the words in the dictionary one by one
        for(int k=0; k<dictWords.size(); k++) {
            //System.out.println("Dict: " + dictWords.get(k));
            //Dictionary of character, list or array sized two
//            if(gridPositionMap.containsKey(dictWords.get(k).charAt(0))){
//                System.out.println("............................................................");
//                finalString += dictWords.get(k).charAt(0);
//                boolean result = solveGrid(dictWords.get(k), gridPositionMap.get(dictWords.get(k).charAt(0)).get(0), gridPositionMap.get(dictWords.get(k).charAt(0)).get(1));
//                if(result){
//                    check = 1;
//                    if(!(dictWordsSet.contains(finalString))){
//                        finalStringList.add(finalString + "\t " + (gridPositionMap.get(dictWords.get(k).charAt(0)).get(1)+1) + "\t " +(r-gridPositionMap.get(dictWords.get(k).charAt(0)).get(0)) + "\t" + direction);
//                        System.out.println("=======================added==============================: " + finalString);
//                    }
//                    dictWordsSet.add(finalString);
//                    finalString = "";
//                    direction = "";
//
//                }
//            for(int l=0; l<r; l++){
//                        for(int m=0; m<c; m++){
//                            sol[l][m] = 0;
//                        }
//                    }
//                x = 0;
//            }else{
            //Traverse through the grid to find the position of first letter of the word from dictionary
                for (int i = 0; i < r; i++) {
                    for (int j = 0; j < c; j++) {
                        List<Integer> pos = new ArrayList<>();
                        if (gridValuesFromUser[i][j] == dictWords.get(k).charAt(0)){
                            //If found, add it to the finalString and invoke solveGrid
                            finalString += dictWords.get(k).charAt(0);
                            //This is to store the coordinates to prevent if there's another word with the first letter, it doesn't have to traverse all the grid to find that position.
                            pos.add(i);
                            pos.add(j);
                            gridPositionMap.put(dictWords.get(k).charAt(0), pos);
                            //invoke solveGrid
                            boolean result = solveGrid(dictWords.get(k), i, j);
                            //If the word is found add it to the finalSting List
                            if(result){
                                check = 1;
                                //Before adding check if it was already add such as tail was there in multiple instances
                                if(!(dictWordsSet.contains(finalString))){
                                    finalStringList.add(finalString + "\t " + (j+1) + "\t " +(r-i) + "\t" + direction);
                                }
                                //add the string to dictionary set
                                dictWordsSet.add(finalString);
                                //Reset finalString, direction string
                                finalString = "";
                                direction = "";
                            }
                            //reset the grid to track visits
                            for(int l=0; l<r; l++){
                                for(int m=0; m<c; m++){
                                    sol[l][m] = 0;
                                }
                            }
                            //reset the position inside word
                            x = 0;
                        }
                        //iterationCounter++;
                    }

                }
        //    }

       }
        //If check stays zero, no words found
        if(check==0){
            //System.out.println("No words found in the grid");
            return null;
        }
        //Finally sort the list and return
        finalStringList.sort(Comparator.comparing(String::toString));
        //long endTime = System.currentTimeMillis();
        //System.out.println("It took " + (endTime - startTime) + " milliseconds");
        //System.out.println("Iterations: "+ iterationCounter);
        return finalStringList;
    }

    //A place to store the position inside word, and direction array, MAX to note that we visited the grid position
    private int x = 0;
    private char[] pathWord = {'L', 'D', 'R', 'U', 'N', 'E', 'S', 'W'};
    private int MAX = 9999;
    private boolean solveGrid(String checkDict, int R, int C) {
        //If the string is equals to the word return true saying we found the word
        if(finalString.equalsIgnoreCase(checkDict)){
            return true;
        }
        //Check if we have visited all possible directions, then return false saying the word isn't found
        if(finalString.charAt(finalString.length() - 1) == gridValuesFromUser[R][C]){
            if(sol[R][C] == MAX){
                return  false;
            }
            //Going for the next direction and checking if there's a letter at that position and that letter is equal to the next letter in word and also if it's not visited before
            x = x +1;
            sol[R][C] = MAX;
            //Check in the direction of Left, if the letter matches call the same function recursively
            if(C>0 && (gridValuesFromUser[R][C-1]== checkDict.charAt(x)) && !(sol[R][C-1]==MAX)){
                finalString += checkDict.charAt(x);
                direction += pathWord[0];
                if(solveGrid(checkDict, R, C-1)){
                    return true;
                };
            }
            //Check in the direction of Down, if the letter matches call the same function recursively
            if(R<r-1 && (gridValuesFromUser[R+1][C]== checkDict.charAt(x)) && !(sol[R+1][C]==MAX)){
                finalString += checkDict.charAt(x);
                //System.out.println("It's D");
                direction += pathWord[1];
                if(solveGrid(checkDict, R+1, C)){
                    return true;
                };
            }
            //Check in the direction of Right, if the letter matches call the same function recursively
            if(C<c-1 && (gridValuesFromUser[R][C+1]== checkDict.charAt(x)) && !(sol[R][C+1]==MAX)){
                finalString += checkDict.charAt(x);
                //System.out.println("It's R");
                direction += pathWord[2];
                if(solveGrid(checkDict, R, C+1)){
                    return true;
                };
            }
            //Check in the direction of Upper, if the letter matches call the same function recursively
            if(R>0 && (gridValuesFromUser[R-1][C]== checkDict.charAt(x)) && !(sol[R-1][C]==MAX)){
                finalString += checkDict.charAt(x);
                //System.out.println("It's U");
                direction += pathWord[3];
                if(solveGrid(checkDict, R-1, C)){
                    return true;
                };
            }

            //Check in the direction of Diagonal Up Left, if the letter matches call the same function recursively
            if(R>0 && C>0 && (gridValuesFromUser[R-1][C-1]== checkDict.charAt(x)) && !(sol[R-1][C-1]==MAX)){
                finalString += checkDict.charAt(x);
                //System.out.println("It's N");
                direction += pathWord[4];
                if(solveGrid(checkDict, R-1, C-1)){
                    return true;
                };
            }
            //Check in the direction of Diagonal Up Right, if the letter matches call the same function recursively
            if(R>0 && C<c-1 && (gridValuesFromUser[R-1][C+1]== checkDict.charAt(x)) && !(sol[R-1][C+1]==MAX)){
                finalString += checkDict.charAt(x);
                //System.out.println("It's E");
                direction += pathWord[5];
                if(solveGrid(checkDict, R-1, C+1)){
                    return true;
                };
            }
            //Check in the direction of Diagonal Down Right, if the letter matches call the same function recursively
            if(R<r-1 && C<c-1 && (gridValuesFromUser[R+1][C+1]== checkDict.charAt(x)) && !(sol[R+1][C+1]==MAX)){
                finalString += checkDict.charAt(x);
                //System.out.println("It's S");
                direction += pathWord[6];
                if(solveGrid(checkDict, R+1, C+1)){
                    return true;
                };
            }
            //Check in the direction of Diagonal Down Left, if the letter matches call the same function recursively
            if(R<r-1 && C>0 && (gridValuesFromUser[R+1][C-1]== checkDict.charAt(x)) && !(sol[R+1][C-1]==MAX)){
                finalString += checkDict.charAt(x);
                //System.out.println("It's W");
                direction += pathWord[7];
                if(solveGrid(checkDict, R+1, C-1)){
                    return true;
                };
            }
            //Backtrack to previous letter and direction if the letter is not found
            //System.out.println("Final--");
            finalString = finalString.substring(0, finalString.length()-1);
            if(direction.length()>0){
            //System.out.println("Direction--");
                direction = direction.substring(0, direction.length()-1);
            }else{
                return false;
            }
            //Also, reset visit to zero
            sol[R][C] = 0;
            x--;
        }
        //Finally, return false saying that word couldn't be found
        return false;
    }

    String print(){
        String printString = "";
        //Traverse though the grid and append it to the string
        for(int i=0; i<gridValuesFromUser.length; i++){
            for(int j=0; j<gridValuesFromUser[0].length; j++){
                //--Note:Uncomment the print statements below to print directly here instead of returning
                //System.out.print(gridValuesFromUser[i][j]);
                printString += gridValuesFromUser[i][j];
            }
            //System.out.println("");
            //To get in new line
            printString += "\n";
        }
        //Return the string
        return printString;
    }
}

//----------------------------------------------To-Do-------------------------------------------------------------------
// Create List of List of String List<String>
// Read using readline
// Read first line .
// Use trim and check if it is empty and check if it is less than 2 -> return false
// Find the length of this string
// Check if the length matches with validLength when validLength is not -1
// If not matching, return false

// Now, add it to the List

// You will get List
// find row size = list.size();
// find col size = validLength

// Create a global (class variable) char matrix of size = [rowSize][colSize]
// gridValuesFromUser = new char[rowSize][colSize];
// loop through main list
// loop through characters in each list item
// Start setting your grid values
// Check if character is within ascii range and convert to lower if in ascii
// Else return false