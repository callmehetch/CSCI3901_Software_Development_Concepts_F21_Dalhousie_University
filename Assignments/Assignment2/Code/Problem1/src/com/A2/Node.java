package com.A2;

public class Node {
    //Node variables
    private String ch;
    private int freq;
    private Node right;
    private Node left;
    //Node contructor
    public Node(String ch, int freq, Node right, Node left) {
        setCh(ch);
        setFreq(freq);
        setRight(right);
        setLeft(left);
    }
    //Node constructor with two arguments
    public Node(String ch, int freq){
        setCh(ch);
        setFreq(freq);
        setLeft(null);
        setRight(null);
    }
    //Node constructer with two node arguments
    public Node(Node left, Node right){
        setCh("root");
        setFreq(left.getFreq() + right.getFreq());
        setLeft(left);
        setRight(right);
    }
    //Default
    public Node() {

    }

    //Getters and Setters
    public String getCh() {
        return ch;
    }

    public void setCh(String ch) {
        this.ch = ch;
    }

    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }
}
