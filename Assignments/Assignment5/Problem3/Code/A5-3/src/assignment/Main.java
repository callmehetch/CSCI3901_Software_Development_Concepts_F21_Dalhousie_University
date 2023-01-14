package assignment;

//Main class to run the program and call the methods
public class Main {
    //Main menthod to be executed when the program is being run
    public static void main(String[] args) {
        //Order manager object
        OrderManager om = new OrderManager();
        om.checkout(10121, 3);
        System.out.println(om.orderValue(10121));

    }
}
