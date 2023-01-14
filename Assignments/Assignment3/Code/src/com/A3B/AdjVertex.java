package com.A3B;

//Class for storing adjacent vertices
public class AdjVertex {
    //-------------------Variables to store data related to adjacent vertices---------------------------------
    //Variable to store adjacent city name
    private String adjName;
    //Variable to the way of travelling
    private String way;
    //Variable to store the edge weight
    private int edge;
    //---------------------------Constructures to store data related to adjacent vertices-------------------------------------
    public AdjVertex(String adjName, String way, int edge) {
        //Setting the adjName to adjName argument passed
        setAdjName(adjName);
        //Setting the edge to edge argument passed
        setEdge(edge);
        //Setting the way to way argument passed
        setWay(way);
    }

    //--------------------Setters and Getters-----------------------------
    //Getter for adjacent city name
    public String getAdjName() {
        //return the adjacent city name
        return adjName;
    }
    //Setter for adjacent city name
    public void setAdjName(String adjName) {
        //return the adjacent city name
        this.adjName = adjName;
    }
    //Getter for way of travelling
    public String getWay() {
        //return the way of travelling
        return way;
    }
    //Setter for way of travelling
    public void setWay(String way) {
        //set the way of travelling
        this.way = way;
    }
    //Getter for the edge weight
    public int getEdge() {
        //return the edge weight
        return edge;
    }
    //Setter for the edge weight
    public void setEdge(int edge) {
        //set the edge weight
        this.edge = edge;
    }

}
