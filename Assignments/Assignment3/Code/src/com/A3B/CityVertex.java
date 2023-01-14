package com.A3B;

//Class for storing cities
public class CityVertex {
    //-------------------Variables to store data related to cities---------------------------------
    //Variable to store a city name
    private String cityName;
    //Variable to store if test is required for a city
    private boolean testRequired;
    //Variable to store the time taken to get test results
    private int timeToTest;
    //Variable to store the cost of staying at a hotel in the city
    private int nightlyHotelCost;

    //---------------------------Constructures to store data related to cities--------------------------
    public CityVertex(String cityName, boolean testRequired, int timeToTest, int nightlyHotelCost) {
        //Setting the cityName to cityName argument passed
        setCityName(cityName);
        //Setting the testRequired to testRequired argument passed
        setTestRequired(testRequired);
        //Setting the timeToTest to timeToTest argument passed
        setTimeToTest(timeToTest);
        //Setting the nightlyHotelCost to nightlyCost argument passed
        setNightlyHotelCost(nightlyHotelCost);
    }

    //--------------------Setters and Getters-----------------------------
    //Getter for a city name
    public String getCityName() {
        //return the city name
        return cityName;
    }
    //Setter for a city name
    public void setCityName(String cityName) {
        //set the city name
        this.cityName = cityName;
    }
    //Getter for test requirement
    public boolean isTestRequired() {
        //return the test requirement
        return testRequired;
    }
    //Setter for a test requirement
    public void setTestRequired(boolean testRequired) {
        //set the test requirement
        this.testRequired = testRequired;
    }
    //Getter for the time to receive test result
    public int getTimeToTest() {
        //return the time taken to get results
        return timeToTest;
    }
    //Setter for the time to receive test result
    public void setTimeToTest(int timeToTest) {
        //set time taken to get test results
        this.timeToTest = timeToTest;
    }
    //Getter for the cost of spending a night at hotel
    public int getNightlyHotelCost() {
        //return the hotel cost for the night
        return nightlyHotelCost;
    }
    //Setter for the cost of spending a night at hotel
    public void setNightlyHotelCost(int nightlyHotelCost) {
        //set the hotel cost
        this.nightlyHotelCost = nightlyHotelCost;
    }

}
