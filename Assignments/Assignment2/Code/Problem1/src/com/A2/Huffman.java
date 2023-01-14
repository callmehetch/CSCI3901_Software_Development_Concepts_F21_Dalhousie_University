package com.A2;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Huffman implements FileCompressor {
    //Variable Initialization
    private int level;
    private boolean reset;
    private String thisLine;
    private String info = "";
    private String codeToOutput="";
    private String newCh = "&";
    //private  String decodeString = "";
    private String dInfo;
    private String enVal;
    private String dVal= "";
    HashMap<Character, Integer> mapFreqs = new HashMap<Character, Integer>();
    HashMap<Character, String> C1 = new HashMap<Character, String>();
    implementHeap H1 = new implementHeap();
    implementHeap h2 = new implementHeap();
    Node hNodes = new Node();
    Node hDNodes = new Node();
    BufferedReader B1;
    BufferedReader B2;
    char[] cArray;
    String[] bits;
    Node r;
    //Default main method
    public static void main(String[] args) {

    }
    //Implemented encode method
    @Override
    public boolean encode(String input_filename, int level, boolean reset, String output_filename) {
        setLevel(level);
        setReset(reset);
        //File reading and storing those characters or string and their frequencies in a map
        try {
           setB1(new BufferedReader(new FileReader(input_filename)));
            while ((thisLine = getB1().readLine()) !=null){
                setThisLine(getThisLine() + getNewCh());
                char[] chars = getThisLine().toCharArray();
                for (char k:
                     chars) {
                    mapFreqs.put(k, mapFreqs.getOrDefault(k,0) + 1);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Construct a heap of this map so that everytime an item popped, automatically sorted
        H1.construct(mapFreqs);
        //To create a tree
        while (H1.getNum()>0){
            Node right = H1.pop();
            Node left = H1.pop();
            Node root = new Node(left,right);
            H1.insert(root);
            hNodes = root;
        }

        //Map of coded character with respective code
        Map<Character, String> C1 = codebook();
        //Adding codes into a string in the order of given string from input file

        for (char k:
             mapFreqs.keySet()) {
            setInfo(getInfo()+ k + "'" + mapFreqs.get(k) + "'");
        }
        //System.out.println(M1);
        //System.out.println(info);
        //Finally storing those codes and coded string into an output file
        try (PrintWriter out = new PrintWriter(output_filename)) {
             setB1(new BufferedReader(new FileReader(input_filename)));
            out.println(getInfo());
            while ((thisLine = getB1().readLine()) !=null) {
               setcArray(getThisLine().toCharArray());
                for(char c: getcArray()) {
                    //System.out.println(C1.get(c));
                    setCodeToOutput(getCodeToOutput() + C1.get(c));
                }
                setCodeToOutput(getCodeToOutput() + C1.get(getNewCh().charAt(0)));
                out.print(getCodeToOutput());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean decode(String input_filename, String output_filename) {
        try{
            //Reading a file and split accordingly so that we can have an array of these splits
            setB2(new BufferedReader(new FileReader(input_filename)));
            setdInfo(getB2().readLine());

            setBits(getdInfo().split("\\'"));
            //Exception
            if(bits.length % 2 != 0) { getB2().close(); throw new Exception("Invalid") ;}
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
        //Retrieved characters and their frequencies are added to a map
            for(int i=0 ; i<getBits().length; i+=2) {
                mapFreqs.put(bits[i].charAt(0), Integer.parseInt(bits[i+1]));
            }
        //Construct a heap of this map so that everytime an item popped, automatically sorted
        h2.construct(mapFreqs);
        //To create a tree
        while (h2.getNum()>0){
                Node right = h2.pop();
                Node left = h2.pop();
                Node root = new Node(left,right);
                h2.insert(root);
                hDNodes = root;
            }
        //reading the given file
        try {
            setEnVal(getB2().readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(getEnVal());

            r = hDNodes;
        //Looping till the length of coded string and changing each code to character and storing in dVal

            for(int i=0; i<getEnVal().length(); i++) {
                if(r.getLeft() == null && r.getRight() == null) {
                    if(r.getCh().equalsIgnoreCase(getNewCh())) {
                        setdVal(getdVal() + "\r\n");
                    } else {
                        setdVal(getdVal() + r.getCh());

                    }
                    r = hDNodes;
                }
                if(getEnVal().charAt(i) == '0') {
                    r = r.getLeft();
                } else if(getEnVal().charAt(i) == '1') {
                    r = r.getRight();
                }
            }
        //Finally printing the dVal string into given output file
        PrintWriter out = null;
        try {
            out = new PrintWriter(output_filename, "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        out.print(getdVal());
            out.close();
        return true;
    }
    //Method for codebook
    @Override
    public Map<Character, String> codebook() {
        //System.out.println("Seventh");
        //System.out.println("Seventh");
        //Creating a Binary tree and getting code for each character based on the interated path
        class BT {
            void iterate(Node N, String s) {
                if(N.getRight() == null && N.getLeft()  == null ) {
                    C1.put(N.getCh().charAt(0), s);
                    return;
                }
                iterate(N.getLeft(), s + "0");
                iterate(N.getRight(), s + "1");
            }
            public Map<Character, String> mapBuilder() {
                iterate(hNodes, "");
                return C1;
            }
        };
        return new BT().mapBuilder();
    }



    public String getNewCh() {
        return newCh;
    }

    public void setNewCh(String newCh) {
        this.newCh = newCh;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isReset() {
        return reset;
    }

    public void setReset(boolean reset) {
        this.reset = reset;
    }

    public String getThisLine() {
        return thisLine;
    }

    public void setThisLine(String thisLine) {
        this.thisLine = thisLine;
    }
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
    public String getCodeToOutput() {
        return codeToOutput;
    }

    public void setCodeToOutput(String codeToOutput) {
        this.codeToOutput = codeToOutput;
    }
    public String getdInfo() {
        return dInfo;
    }

    public void setdInfo(String dInfo) {
        this.dInfo = dInfo;
    }

    public String getEnVal() {
        return enVal;
    }

    public void setEnVal(String enVal) {
        this.enVal = enVal;
    }

    public String getdVal() {
        return dVal;
    }

    public void setdVal(String dVal) {
        this.dVal = dVal;
    }
    public BufferedReader getB1() {
        return B1;
    }

    public void setB1(BufferedReader b1) {
        B1 = b1;
    }

    public BufferedReader getB2() {
        return B2;
    }

    public void setB2(BufferedReader b2) {
        B2 = b2;
    }
    public char[] getcArray() {
        return cArray;
    }

    public void setcArray(char[] cArray) {
        this.cArray = cArray;
    }

    public String[] getBits() {
        return bits;
    }

    public void setBits(String[] bits) {
        this.bits = bits;
    }


}
