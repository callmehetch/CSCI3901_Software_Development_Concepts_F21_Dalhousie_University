
import java.util.*;

public class Main {

    public static void main(String[] args) {
        String cityName;
        boolean testRequired;
        int timeToTest;
        int nightlyHotelCost;
        String startCity;
        String destinationCity;
        int flightTime;
        int flightCost;
        int trainTime;
        int trainCost;
        TravelAssistant newTravel = new TravelAssistant();
        Scanner scan = new Scanner(System.in);
        int input = 0;
        while (input != 6){
            System.out.println("Enter an integer: 1)addCity 2)addFlight 3)addTrain 4)planTrip 5)getData 6)quit");
            input = scan.nextInt();
            switch (input){
                case 1:
                    System.out.println("Enter a city name: ");
                    cityName = scan.next();
                    System.out.println("Enter true/false if test required: ");
                    testRequired = scan.nextBoolean();
                    System.out.println("Enter the no of [days] it takes to get test result in this city: ");
                    timeToTest = scan.nextInt();
                    System.out.println("Enter the cost of hotel per night: ");
                    nightlyHotelCost = scan.nextInt();
                    System.out.println(newTravel.addCity(cityName, testRequired, timeToTest, nightlyHotelCost));
                    break;
                case 2:
                    System.out.println("Enter the starting city: ");
                    startCity = scan.next();
                    System.out.println("Enter the destination city: ");
                    destinationCity = scan.next();
                    System.out.println("Enter the time of flight in [minutes]: ");
                    flightTime = scan.nextInt();
                    System.out.println("Enter the cost of flight: ");
                    flightCost = scan.nextInt();
                    System.out.println(newTravel.addFlight(startCity, destinationCity, flightTime, flightCost));
                    break;
                case 3:
                    System.out.println("Enter the starting city: ");
                    startCity = scan.next();
                    System.out.println("Enter the destination city: ");
                    destinationCity = scan.next();
                    System.out.println("Enter the time of train in [minutes]: ");
                    trainTime = scan.nextInt();
                    System.out.println("Enter the cost of train: ");
                    trainCost = scan.nextInt();
                    System.out.println(newTravel.addTrain(startCity, destinationCity, trainTime, trainCost));
                    break;
                case 4:
                    System.out.println("Enter the starting city: ");
                    startCity = scan.next();
                    System.out.println("Enter the destination city: ");
                    destinationCity = scan.next();
                    System.out.println("Enter true/false if vaccinated: ");
                    boolean isVaccinated = scan.nextBoolean();
                    System.out.println("Enter the costImportance: ");
                    int costImportance = scan.nextInt();
                    System.out.println("Enter the travelImportance: ");
                    int travelImportance = scan.nextInt();
                    System.out.println("Enter the travelHopImportance: ");
                    int travelHopImportance = scan.nextInt();
                    System.out.println(newTravel.planTrip(startCity, destinationCity, isVaccinated, costImportance, travelImportance, travelHopImportance));
                    break;
                default:
                    System.out.println("Please Enter a valid input!");
//                case 5:
//                    newTravel.getData();
            }
        }
    }
}
