
//Class for storing flights
public class FlightHop {
    //-------------------Variables to store data related to cities---------------------------------
    //Variable to store a city name of starting city
    private String startCity;
    //Variable to store a city name of destination city
    private String destinationCity;
    //Variable to store time taken for the flight
    private int flightTime;
    //Variable to store cost spent on the flight
    private int flightCost;
    //---------------------------Constructures to store data related to flights--------------------------
    public FlightHop(String startCity, String destinationCity, int flightTime, int flightCost) {
        //Setting the startCity to startCity argument passed
        setStartCity(startCity);
        //Setting the destinationCity to destinationCity argument passed
        setDestinationCity(destinationCity);
        //Setting the flightTime to flightTime argument passed
        setFlightTime(flightTime);
        //Setting the flightCost to flightCost argument passed
        setFlightCost(flightCost);
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
    //Getter for a destination city name
    public void setDestinationCity(String destinationCity) {
        //set the destination city name
        this.destinationCity = destinationCity;
    }
    //Getter for the time of flight
    public int getFlightTime() {
        //return the flight time
        return flightTime;
    }
    //Setter for the time of flight
    public void setFlightTime(int flightTime) {
        //set the flight time
        this.flightTime = flightTime;
    }
    //Getter for the cost of flight
    public int getFlightCost() {
        //return the cost of a flight
        return flightCost;
    }
    //Setter for the cost of flight
    public void setFlightCost(int flightCost) {
        //set the cost of a flight
        this.flightCost = flightCost;
    }

}
