package com.A3B;

//Importing List library to create lists
import java.util.List;

//Class for storing vertices
public class Vertex {
    //-------------------Variables to store data related to trains---------------------------------
    //Variable to store a city name of the vertices
    private String cName;
    //Variable to store list of adjacent cities of this vertex
    private List<AdjVertex> adjVertices;
    //---------------------------Constructures to store data related to vertices--------------------------
    public Vertex(String cName, List<AdjVertex> adjVertices) {
        //Setting the cName to cName argument passed
        setcName(cName);
        //Setting the adjVertices to adjVertices argument passed
        setAdjVertices(adjVertices);
    }

    //--------------------Setters and Getters-----------------------------
    //Getter for a city name
    public String getcName() {
        //return the city name
        return cName;
    }
    //Setter for a city name
    public void setcName(String cName) {
        //set the city name
        this.cName = cName;
    }
    //Getter for the adjacent city names
    public List<AdjVertex> getAdjVertices() {
        //return the adjacent city names
        return adjVertices;
    }
    //Setter for the adjacent city names
    public void setAdjVertices(List<AdjVertex> adjVertices) {
        //set the adjacent city names
        this.adjVertices = adjVertices;
    }
}
