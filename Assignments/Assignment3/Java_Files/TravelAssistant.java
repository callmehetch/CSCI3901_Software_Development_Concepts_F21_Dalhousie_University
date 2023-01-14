
import java.util.*;

//TravelAssistant class with addCity(), addFlight(), addTrain(), planTrip() methods
public class TravelAssistant {
    //--------------Private Lists to store data of cities, vertices, trains, and just the city arraylist---------------------------
    //ArrayList to store list of cities objects
    private List<CityVertex> newCityVertexList = new ArrayList<>();
    //ArrayList to store list of flights objects
    private List<FlightHop> newFlightList = new ArrayList<>();
    //ArrayList to store list of trains objects
    private List<TrainHop> newTrainList = new ArrayList<>();
    //ArrayList to store list of just the cities
    private List<String> onlyCities = new ArrayList<>();

    //----------------------------------------------------------addCity Method------------------------------------------------------------------
    public boolean addCity(String cityName, boolean testRequired, int timeToTest, int nightlyHotelCost) throws IllegalArgumentException{
        //Surrounded by try catch block to throw  IllegalArgumentException for invalid inputs
        try {
            //Checking if the cityName is null, if city name is empty, hotel cost is zero or less
            if(cityName == null || cityName.isEmpty() || nightlyHotelCost<1){
                //Throwing an IllegalArgumentException
                throw  new IllegalArgumentException();
            }
            //Loop through each city vertex list and check if the city is already exists. Please enter another
            for (CityVertex c: newCityVertexList) {
                //Conditionally check if the city is already there
                if(c.getCityName().equalsIgnoreCase(cityName)){
                    //System.out.println("Error: Same city already exists. Please enter another city!");
                    //return false if city already exists
                    return false;
                }
            }

            //Adding city to onlyCities list
            onlyCities.add(cityName);
           //Creating new city vertex list
            CityVertex newCity = new CityVertex(cityName, testRequired, timeToTest, nightlyHotelCost);
            //Adding the data to city vertex list
            newCityVertexList.add(newCity);

            //Catchinh illegalArgumentException
        }catch(IllegalArgumentException ex) {
            //Throwing the caught IllegalArgumentException
            throw new IllegalArgumentException("Please provide a valid input");
        }
        //Return true if the city is successfully added
        return true;
    }

    //----------------------------------------------------------addFlight Method------------------------------------------------------------------
    public boolean addFlight(String startCity, String destinationCity, int flightTime, int flightCost) throws IllegalArgumentException{
        //New FlightHop object is being created
        FlightHop newFlight = new FlightHop(startCity, destinationCity, flightTime, flightCost);
        //Surrounded by try catch block to throw  IllegalArgumentException for invalid inputs
        try {
            //Checking if the city name is null, if city name is empty, flight cost less than 1, flight time is less than 1
            if(startCity == null || startCity.isEmpty() || destinationCity == null || destinationCity.isEmpty() || flightCost<1 || flightTime<1){
                //Throwing an IllegalArgumentException
                throw new IllegalArgumentException();
            }
            //If there is no city in city list for either start city and destination city then IllegalArgumentException
            if(!(onlyCities.contains(startCity) && onlyCities.contains(destinationCity))){
                //Throwing an IllegalArgumentException
                throw new IllegalArgumentException();
            }
        //Loop through each city vertex list and check if the city is already exists. Please enter another
        for (FlightHop f: newFlightList) {
            //Conditionally check if the same train is already there
            if(f.getStartCity().equalsIgnoreCase(startCity) && f.getDestinationCity().equalsIgnoreCase((destinationCity))){
                //System.out.println("Error: Same flight already exists. Please enter another flight!");
                //return false if city already exists
                return false;
            }
        }
        //Adding train to flight hop list
        newFlightList.add(newFlight);
        //Catching illegalArgumentException
    }catch(IllegalArgumentException ex) {
        //Throwing the caught IllegalArgumentException
        throw new IllegalArgumentException("Please provide a valid input");
    }
    //Return true if the flight is successfully added
    return true;
    }


    //----------------------------------------------------------addTrain Method------------------------------------------------------------------
    public boolean addTrain(String startCity, String destinationCity, int trainTime, int trainCost) throws IllegalArgumentException{
        //New TrainHop object is being created
        TrainHop newTrain = new TrainHop(startCity, destinationCity, trainTime, trainCost);
        //Surrounded by try catch block to throw  IllegalArgumentException for invalid inputs
        try {
            //Checking if the city name is null, if city name is empty, train cost less than 1, train time is less than 1
            if(startCity == null || startCity.isEmpty() || destinationCity == null || destinationCity.isEmpty() || trainCost<1 || trainTime<1){
                //Throwing an IllegalArgumentException
                throw new IllegalArgumentException();
            }
            //If there is no city in city list for either start city and destination city then IllegalArgumentException
            if(!(onlyCities.contains(startCity) && onlyCities.contains(destinationCity))){
                 //Throwing an IllegalArgumentException
                throw new IllegalArgumentException();
            }
            //Loop through each city vertex list and check if the city is already exists. Please enter another
            for (TrainHop t: newTrainList) {
                //Conditionally check if the same train is already there
                if(t.getStartCity().equalsIgnoreCase(startCity) && t.getDestinationCity().equalsIgnoreCase((destinationCity))){
                    //System.out.println("Error: Same train already exists. Please enter another train!");
                    //return false if train already exists
                    return false;
                }
            }
            //Adding train to train hop list
            newTrainList.add(newTrain);
            //Catching illegalArgumentException
        }catch(IllegalArgumentException ex) {
            //Throwing the caught IllegalArgumentException
            throw new IllegalArgumentException("Please provide a valid input");
        }
        //Return true if the flight is successfully added
        return true;
    }


    //------------------------------------------------
    //ArrayList to store list of vertices 
    private static List<Vertex> newVertices = new ArrayList<>();
    //Infinity distance for non visted vertex
    private final static int INF = 9999;
    //Set to store the set of visited vertices while finding shortest path between start city and destination city
    private Set<String> visitVertex = new HashSet<String> ();
    //Map to store the distance between start city and adjacent vertices and the final list to be returned
    private Map<String, listToReturn> distMap = new HashMap<String, listToReturn>();

    //----------------------------------------------------------planTrip Method------------------------------------------------------------------
    public List<String> planTrip ( String startCity, String destinationCity, boolean isVaccinated, int costImportance, int travelTimeImportance, int travelHopImportance ) throws IllegalArgumentException{
        //Declaration and initialization of hotel variable to zero. This is to store the city hotel cost
        int hotel = 0;
        //Declaration and initialization of Flight weight variable to zero. This is to store the edge weight
        int weightF = 0;
        //Declaration and initialization of Train weight variable to zero. This is to store the edge weight
        int weightT = 0;
        //Checking if the city name is null, if city name is empty, importances are less than 1 to throw an exception.
        if(startCity==null || startCity.isEmpty() || destinationCity == null || destinationCity.isEmpty() || costImportance<0 || travelHopImportance<0 || travelHopImportance<0){
             //Throwing an IllegalArgumentException
            throw new IllegalArgumentException("Please enter a valid input");
        }
        //Loop through each flight in the flight list to pass weights
        for (FlightHop aFlight : newFlightList) {
             //Loop through each city in the city list to get start city data
            for (CityVertex aCityVertex : newCityVertexList) {
                //If it is not the start city continue loop
                if (!aCityVertex.getCityName().equals(startCity)) {
                    continue;
                }
                //hotel cost is added
                hotel = aCityVertex.getNightlyHotelCost();
            }
            //Checking if a person is Vaccinated
            //if vaccinated , no hotel cost
            //If not vaccinated, add hotel cost to the weight
            weightF = isVaccinated == true ? (aFlight.getFlightCost() * costImportance) + (aFlight.getFlightTime() * travelTimeImportance) + 1 * travelHopImportance : ((aFlight.getFlightCost() + hotel) * costImportance) + (aFlight.getFlightTime() * travelTimeImportance) + 1 * travelHopImportance;
            //Finally, add these flights to the travel network
            travelNetwork(aFlight.getStartCity(), "fly ", aFlight.getDestinationCity(), weightF);
        }
        //Loop through each train in the train list to pass weights
        for (TrainHop aTrain : newTrainList) {
            //Loop through each city in the city list to get start city data
            for (CityVertex aCityVertex : newCityVertexList) {
                //If it is the start city continue, add hotel cost
                if (aCityVertex.getCityName().equals(startCity)) {
                    //hotel cost is added
                    hotel = aCityVertex.getNightlyHotelCost();
                }
            }
            //Checking if a person is Vaccinated and adding hotel cost
            weightT = isVaccinated == true ? ((aTrain.getTrainCost()) * costImportance) + (aTrain.getTrainTime() * travelTimeImportance) + 1 * travelHopImportance : ((aTrain.getTrainCost() + hotel) * costImportance) + (aTrain.getTrainTime() * travelTimeImportance) + 1 * travelHopImportance;
            //Going through every flight to compare weights
            for (FlightHop aNewFlight: newFlightList) {
                //If there is already a flight between the same cities
                if(aNewFlight.getStartCity().equalsIgnoreCase(aTrain.getStartCity()) && aNewFlight.getStartCity().equalsIgnoreCase(aTrain.getStartCity())){
                    //Calculate flight weight
                    weightF = isVaccinated == true ? (aNewFlight.getFlightCost() * costImportance) + (aNewFlight.getFlightTime() * travelTimeImportance) + 1 * travelHopImportance : ((aNewFlight.getFlightCost() + hotel) * costImportance) + (aNewFlight.getFlightTime() * travelTimeImportance) + 1 * travelHopImportance;
                    //If flight weight is greater than train weight
                    if (weightF>weightT){
                        //Finally, add these trains to the travel network incase weight is less
                        travelNetwork(aTrain.getStartCity(), "train ", aTrain.getDestinationCity(), weightT);
                    }
                }
            }
        }
        //loop through each city list to keep distance as infinity except self
        for (CityVertex aCityVertex : newCityVertexList) {
            //Keeping distance using ternary operator
            distMap.put(aCityVertex.getCityName(), aCityVertex.getCityName().equals(startCity) ? new listToReturn("", 0, "", aCityVertex.getCityName()) : new listToReturn("", INF, "", ""));
        }
        //dijkstra method call to get shortest route
        dijkstra();

        //Creating a new object of listtoreturn
        listToReturn aListToReturn;
        //Intializing the newly created object
        aListToReturn = distMap.get(destinationCity);

        //List to store the travelRoute
        List<String> route;
        //intializing the newly created list
        route = new ArrayList<>();
        //If there is no route null has to be returned
        if (aListToReturn == null) {
            return null;
        } else {
            //starts from the starting city
            if (!aListToReturn.getCity().equals(startCity)) {
                //Running a do while loop when city is not equals to the start city
                do {
                    //Adding to list. All visited routes' way of travel and the city name
                    route.add(aListToReturn.getWay() + aListToReturn.getCity());
                    //If distance between the list elements is zero. Continue
                    if (aListToReturn.getDis() == 0) {
                        continue;
                    }
                    //If distance between the map elements is null. Continue
                    if (distMap.get(aListToReturn.getVertex()) == null) {
                        continue;
                    }
                    //Finally, getting the distance map after traversal
                    aListToReturn = distMap.get(aListToReturn.getVertex());
                } while (!aListToReturn.getCity().equals(startCity));
            }
        }
        //If there is no possible route, return null
        if (route.size() <= 0) {
            return null;
        }
        //Otherwise add start to starting city and print in reverse order
        route.add("start " + startCity);
        //Reversing
        Collections.reverse(route);
        //Return the route list as metioned in the PDF
        return route;
    }



    //-----------------------------------------------------------Private method to create travel network---------------------------------------------
    private void travelNetwork(String startCity, String mode, String destinationCity, int cost) {
        //j variable declaration to count the city positions
        int j;
        //Initialize j to size of the vertices 
        j = newVertices.size() - 1;
        //Declaration of city positions
        int newPos;
        //Intializing city position to -1
        newPos = -1;
        //If city position is positive
        if (j >= 0) {
            //Run a do while loop
            do {
                //Vertex intializing and declaration with city vertex
                Vertex aVertex = newVertices.get(j);
                //check if it is start city
                if (aVertex.getcName().equals(startCity)) {
                    //If it is, update city position to j
                    newPos = j;
                }
                //decrement j as part of looping
                j--;
            } while (j >= 0);
        }
        //Finally if there is no adjacent vertex
        if (newPos != -1) {
            //Create one adjacent
            Vertex aVertice = newVertices.get(newPos);
            //New adjacent vertex is created
            List<AdjVertex> aNewAdjVertex = aVertice.getAdjVertices();
            //Newly created adjacent vertex is added to aNewAdjVertex list
            aNewAdjVertex.add(new AdjVertex(destinationCity, mode, cost));
        } else {
            //Else, add the new adjacent vertex to existing vertex
            List<AdjVertex> aAdjVertexList = new ArrayList<>();
            //Adding the newly created adjacent vertex list
            aAdjVertexList.add(new AdjVertex(destinationCity, mode, cost));
            //Adding the adjacent vertex to the parent vertex
            newVertices.add(new Vertex(startCity, aAdjVertexList));
        }

    }

    //-----------------------------------------------------------Private method to implement dijkstra's algorithm---------------------------------------------
    private void dijkstra() {
        //If the there are still unvisted cities left in the list, run loop
        if (visitVertex.size() < newCityVertexList.size()) {
            //Do-While loop to find the shortest route 
            do {
                //New string variable to check 
                String mVertex;
                //Map entry for the list to be returned
                Map.Entry<String, listToReturn> m = null;
                //looping through all the map entries to find the value of m
                for (Map.Entry<String, listToReturn> entry : distMap.entrySet()) {
                    //If visited vertex has the map key, do 
                    if (!visitVertex.contains(entry.getKey()))
                        //check for m null and ditance of m and entry
                        if ((m == null || m.getValue().getDis() > entry.getValue().getDis())) {
                            //m to be set to entry
                            m = entry;
                        }
                }
                //Using ternary operator to check if m or m key is null
                mVertex = m != null ? m.getKey() : null;
                //If minimum vertex is not null
                if (mVertex != null) {
                    //Add it to the visited vertices list
                    visitVertex.add(mVertex);
                    //Declare and initialize position to -1
                    int pos = -1;
                    //Declare and initialize i value to size
                    int i = newVertices.size() - 1;
                    //Run a loop to get the position
                    while (i >= 0) {
                        //Creating a new vertex to check the position
                        Vertex someVertex = newVertices.get(i);
                        //If the new vertex has the city name as minimum vertex then
                        if(someVertex.getcName().equals(mVertex)) {
                            //Keep postion to i
                            pos = i;
                        }
                        //Decrement i value as part of the loop
                        i--;
                    }
                    //If position is -1, continue the main loop
                    if (pos == -1)
                        continue;
                    //Create a new vertes and intializing with vertex at position pos
                    Vertex aNewVertex = newVertices.get(pos);
                    //Creating a new adj vertex list and initializing with adjacent vertices
                    List<AdjVertex> adjVList = aNewVertex.getAdjVertices();
                    //Run a for loop tthough the adjacent vertices to find the minimum costing route
                    for (AdjVertex adjVertex : adjVList) {
                        //Declare and intializing a new straing adjacent city vertices
                        String adjCityVertex = adjVertex.getAdjName();
                        //If the distance doesn't contain the adjacent city vertex then continue the loop
                        if (!distMap.containsKey(adjCityVertex)) {
                            continue;
                        }
                        //Create a new object of listtoreturn
                        listToReturn aListReturn = distMap.get(adjCityVertex);
                        //Declare and initialize a new integer with distance
                        int adjEdge = aListReturn.getDis();
                        //If adjacent edge distance is lesser then continue
                        if (adjEdge <= distMap.get(mVertex).getDis()) {
                            continue;
                        }
                        //Adding vertex to aListReturn object
                        aListReturn.setVertex(mVertex);
                         //Adding distance of aListToReturn object
                         aListReturn.setDis(distMap.get(mVertex).getDis() + adjVertex.getEdge());
                        //Adding city to aListReturn object
                        aListReturn.setCity(adjCityVertex);
                        //Adding way of travel to aListToReturn object
                        aListReturn.setWay(adjVertex.getWay());
                        //Finnaly adding the object to distance map
                        distMap.put(adjCityVertex, aListReturn);
                    }
                }
                //Part of do while loop checking if we have visted all cities 
            } while (visitVertex.size() < newCityVertexList.size());
        }
    }



    //------------------------------------------------------Method to print all the data---------------------------------------------------------------

    // public void getData(){
    //     System.out.println("--------------Total Cities-------------------");
    //     for (CityVertex c:
    //          newCityVertexList) {
    //         System.out.println("City Name: " + c.getCityName() + " | Test Requirement: " + c.isTestRequired() + " | Time to Test: " + c.getTimeToTest() + " | Hotel Cost: " + c.getNightlyHotelCost());
    //     }
    //     System.out.println("--------------Total Flights-------------------");
    //     for (FlightHop f:
    //             newFlightList) {
    //         System.out.println("Starting City: " + f.getStartCity() + " | Destination City: " + f.getDestinationCity() + " | Flight Time: " + f.getFlightTime() + " | Flight Cost: " + f.getFlightCost());
    //     }
    //     System.out.println("---------------Total Trains--------------------");
    //     for (TrainHop t:
    //             newTrainList) {
    //         System.out.println("Starting City: " + t.startCity + " | Destination City: " + t.destinationCity + " | Train Time: " + t.trainTime + " | Train Cost: " + t.trainCost);
    //     }

    // }

}


