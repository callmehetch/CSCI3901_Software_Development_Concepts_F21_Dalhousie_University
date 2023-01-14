package com.company;

class PriorityQ {
    private int MAX;
    private int[] arr;
    private int nItems;

    public PriorityQ(){
        MAX = 10;
        arr = new int [MAX];
        nItems = 0;
    }

    public PriorityQ(int initialCapacity, Comparable<? super HuffmanNode> comparator){
        MAX = initialCapacity;
        arr = new int [MAX];
        nItems = 0;
    }
    public void insert(int val){
        int i;
        if(nItems==0){
            arr[0] = val;
            nItems++;
            return;
        }
        for(i=nItems-1;i>=0;i--){
            if(val>arr[i]){

                arr[i+1] = arr[i];
            }else {
                break;
            }
        }
        arr[i+1] = val;
        nItems++;
    }
//    public void printPriorityQ(){
//        for (int i=0; i<nItems;i++){
//            System.out.println(arr[i] + " ");
//        }
//    }
    public int remove(){
        return arr[--nItems];
    }
    public boolean isFull(){
        return nItems == MAX;
    }
    public boolean isEmpty(){
        return nItems == 0;
    }
    public int getPeek(){
        return arr[nItems-1];
    }

}
