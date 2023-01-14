package com.A3B;

//Class for storing trains
public class TrainHop {
    //-------------------Variables to store data related to trains---------------------------------
    //Variable to store a city name of starting city
    private String startCity;
    //Variable to store a city name of destination city
    private String destinationCity;
    //Variable to store time taken for the train
    private int trainTime;
    //Variable to store the cost spent on the flight
    private int trainCost;
    //---------------------------Constructures to store data related to flights--------------------------
    public TrainHop(String startCity, String destinationCity, int trainTime, int trainCost) {
        //Setting the startCity to startCity argument passed
        setStartCity(startCity);
        //Setting the destinationCity to destinationCity argument passed
        setDestinationCity(destinationCity);
        //Setting the trainTime to trainTime argument passed
        setTrainTime(trainTime);
        //Setting the trainCost to trainCost argument passed
        setTrainCost(trainCost);
    }
    
    //--------------------Setters and Getters-----------------------------
    //Getter for a start city name
    public String getStartCity() {
        //return the starting city
        return startCity;
    }
    //Setter for a start city name
    public void setStartCity(String startCity) {
        //set the starting city
        this.startCity = startCity;
    }
    //Getter for a destination city name
    public String getDestinationCity() {
        //return the destination city name
        return destinationCity;
    }
    //Setter for a destination city name
    public void setDestinationCity(String destinationCity) {
        //set the destination city name
        this.destinationCity = destinationCity;
    }
    //Getter for the time of train
    public int getTrainTime() {
        //return the train time
        return trainTime;
    }
    //Setter for the time of train
    public void setTrainTime(int trainTime) {
        //set the train time
        this.trainTime = trainTime;
    }
    //Getter for the cost of train
    public int getTrainCost() {
        //return the train cost
        return trainCost;
    }
    //Setter for the cost of train
    public void setTrainCost(int trainCost) {
        //set the train cost
        this.trainCost = trainCost;
    }
}
