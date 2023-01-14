package com.company;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;


public class Huffman {
    public static void printCode(HuffmanNode root, String s) {
        if (root.left == null && root.right == null && Character.isLetter(root.c)) {

            System.out.println(root.c + "   |  " + s);

            return;
        }
        printCode(root.left, s + "0");
        printCode(root.right, s + "1");
    }

    public static void main(String[] args) {


        String s = "AABAABACDDCBA";
        int len = s.length();
        int n = (int) s.chars().distinct().count();
//        int[] charfreq = new int[n];
//        char[] charArray = s.toCharArray();
//        char[] charArray1 = new char[n];

        Map<Character, Integer> freqMap = new HashMap<>();
        for(int i=0; i<len; i++){
            if(!(freqMap.containsKey(s.charAt(i)))){
                freqMap.put(s.charAt(i), 1);
            }
            else {
                freqMap.put(s.charAt(i), freqMap.get(s.charAt(i)) + 1);
            }
        }

//        int[] charfreq1 = new int[n];
//        char[] charArray1 = new char[n];
//        for(int i=0; i<len;i++){
//            if(charArray[i]!='0'){
//                charArray1[i] = charArray
//            }
//        }
//        for(int i=0; i<charfreq.length;i++){
//            System.out.println(charfreq[i]);
//            System.out.println(charArray[i]);
//            System.out.println(" ");
//       }
//        HashMap<Character,Integer> frequencies = new HashMap<>();
//        for (char ch : s.toCharArray())
//            frequencies.put(ch, frequencies.getOrDefault(ch, 0) + 1);

//        char[] charArray = { 'A', 'B', 'C', 'D' };
//          int[] charfreq = { 5, 1, 6, 3 };

        PriorityQueue<HuffmanNode> q = new PriorityQueue<HuffmanNode>(n, new ImplementComparator());
        System.out.println(q);

        for (Map.Entry<Character, Integer> entry : freqMap.entrySet()){
            HuffmanNode hn = new HuffmanNode();
            hn.c = entry.getKey();
            hn.item = entry.getValue();
            hn.left = null;
            hn.right = null;

            q.add(hn);
    }


//        for (int i = 0; i < n; i++) {
//
//            HuffmanNode hn = new HuffmanNode();
//            hn.c = charArray[i];
//            hn.item = charfreq[i];
//
//            hn.left = null;
//            hn.right = null;
//
//            q.add(hn);
//        }

        HuffmanNode root = null;

        while (q.size() > 1) {

            HuffmanNode x = q.peek();
            q.poll();

            HuffmanNode y = q.peek();
            q.poll();

            HuffmanNode f = new HuffmanNode();

            f.item = x.item + y.item;
            f.c = '-';
            f.left = x;
            f.right = y;
            root = f;

            q.add(f);
        }
        System.out.println(" Char | Huffman code ");
        System.out.println("--------------------");
        printCode(root, "");
    }
}
