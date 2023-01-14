import java.sql.*;

//Order Manager class
public class OrderManager {

    //Initialize connection with null
    Connection connect = null;
    //----------------------------------------------------------------------------------------------------------------------
    //Please change the database here as there was no argument being passed the database name is hard coded inside the class
    final String database = "nadipineni";
    //----------------------------------------------------------------------------------------------------------------------
    public void mySQLconnect() {
        //Database Credentials
        final String dbHost = "jdbc:mysql://db.cs.dal.ca:3306?serverTimezone=UTC&useSSL=false";
        final String username = "nadipineni";
        final String password = "B00899473";

        //DataBase driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(dbHost, username, password);
            //System.out.println("Connection success");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    //Checkout method
    public boolean checkout(int orderNumber, int shipperId){
        mySQLconnect();

        //As promocode details weren't passed as arguments, these were given here.
        String promoCode = "PROMO01";
        double promoPercentage = 25.0;
        double shippingPercentage = 13.0;


        try{
            //Selecting the database
            Statement statement = connect.createStatement();
            statement.execute ("use "+database+";");
            //Query to check if the given orderNumber exists
            ResultSet resultSet = statement.executeQuery("SELECT o.orderNumber FROM orders o WHERE o.orderNumber="+ orderNumber +"");
            if(!resultSet.isBeforeFirst()){
                System.out.println("Order Number doesn't exist. Try again with valid orderNumber. Thank you");
                return false;
            }
            //Query to check if the given shipperID exists in the database
            ResultSet resultSet2 = statement.executeQuery("SELECT s.shipperID FROM shipping s WHERE s.shipperID="+ shipperId +"");
            if(!resultSet2.isBeforeFirst()&&shipperId>0){
                System.out.println("Shipper Number doesn't exist. Try again with valid shipperID. Thank you");
                return false;
            }
            // ----------------Promo------------------------
            //Query to insert promo details inside database. Can be commented out if populating via workbench
            statement.execute("INSERT INTO promotions (promoOfficeCode, promoCode, percentOff) (SELECT oi.officeCode, \" "+ promoCode + " \", "+ promoPercentage +" \n" +
                    "from offices oi, customers c, employees e, orders o where o.orderNumber = " + orderNumber + " \n" +
                    "AND c.customerNumber = o.customerNumber\n" +
                    "AND c.salesRepEmployeeNumber = e.employeeNumber\n" +
                    "AND e.officeCode = oi.officeCode);");
            //Query to get the promo of the office where the employee, who sold an item to the customer
            ResultSet resultSetPromo = statement.executeQuery("SELECT promoID from orders o, customers c, employees e, offices oi, promotions p \n" +
                    "where orderNumber = "+ orderNumber + " \n" +
                    "AND c.customerNumber = o.customerNumber \n" +
                    "AND  c.salesRepEmployeeNumber = e.employeeNumber\n" +
                    "AND e.officeCode = oi.officeCode\n" +
                    "AND e.officeCode = p.promoOfficeCode;");
            //If the query returns the promoID, UPDATE it in orders table
            if(resultSetPromo.next()) {
                int promoID = resultSetPromo.getInt(1);
                statement.execute("UPDATE orders SET orderPromoID = " + promoID + " WHERE orderNumber = " + orderNumber + ";");
            }else{
                System.out.println("Promo is not set for the office where the employee, who sold an item to the customer, works. zero");
            }


            // ---------------------shipping-----------------------------------
            if(shipperId==0){
                //Insert the shipping percentage as zero, shipOrderNumber if shipperID is zero
                statement.execute("INSERT INTO shippercentage(shippingPercentage, shipOrderNumber) VALUES (0, "+ orderNumber +");");
            }else {
                //Insert the shipping percentage, shipOrderNumber if shipperID is not zero and update the ordernumber
                statement.execute("UPDATE orders SET orderShipperID="+ shipperId +" WHERE orderNumber = "+ orderNumber +";");
                statement.execute("INSERT INTO shippercentage(shippingPercentage, shipOrderNumber) VALUES ("+ shippingPercentage +", "+ orderNumber +");");
            }

            //--------------------------Taxes-----------------------------------
            //Query to get the taxID from the office where the employee, who sold an item to the customer, works.
            ResultSet resultSetTax = statement.executeQuery("SELECT taxID FROM taxes t, orders o, customers c, employees e, offices oi, officestax ot WHERE o.cuStomerNumber=c.customerNumber \n" +
                    "AND c.salesRepEmployeeNumber=e.employeeNumber AND e.officeCode=oi.officeCode AND oi.officeCode=ot.officesTaxCode\n" +
                    "AND ot.officesTaxID=t.taxID AND o.orderNumber = "+ orderNumber +";");

            if(resultSetTax.next()){
                //Update taxID for the order if tax is set from the question 3.2
                int taxID = resultSetTax.getInt(1);
                statement.execute("UPDATE orders SET orderTaxID="+taxID+" WHERE orderNumber = "+ orderNumber +"");
            }else {
                System.out.println("No tax set for the office where the employee, who sold an item to the customer, works. So assuming that tax is zero");
            }
            resultSet.close();
            resultSet2.close();
            resultSetPromo.close();
            resultSetTax.close();
            statement.close();
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public double orderValue(int orderNumber){
        mySQLconnect();
        //Declaration and intialization of percentages and amount
        int amount = 0;
        double promoPercent = 0.0;
        double shipPercent = 0.0;
        double taxPercent = 0.0;
        double finalAmount = 0.0;
        try {
            Statement statement = connect.createStatement();
            statement.execute ("use "+database+";");
            //Check if the given orderNumber exists
            ResultSet resultSet = statement.executeQuery("SELECT o.orderNumber FROM orders o WHERE o.orderNumber="+ orderNumber +"");
            if(!resultSet.isBeforeFirst()){
                System.out.println("Order Number doesn't exist. Try again with valid orderNumber. Thank you");
                return finalAmount;
            }
            //Get the sum of sale amount for the given order
            ResultSet resultSetorderAmount = statement.executeQuery("SELECT sum(priceEach*quantityOrdered) FROM orderdetails INNER JOIN orders ON orders.orderNumber=orderdetails.orderNumber WHERE orders.orderNumber = "+orderNumber+";");
            if(resultSetorderAmount.next()){
                amount = resultSetorderAmount.getInt(1);
            }
            //Get the promoPercentage for the given order
            ResultSet resultSetorderPromo = statement.executeQuery("SELECT percentOff FROM orders INNER JOIN promotions ON orderPromoID = promoID WHERE orderNumber = "+orderNumber+";");
            if(resultSetorderPromo.next()){
                promoPercent = resultSetorderPromo.getDouble(1);
            }
            //Get the shipping percentage for the given order
            ResultSet resultSetorderShip = statement.executeQuery("SELECT shippingPercentage FROM shippercentage INNER JOIN orders ON shipOrderNumber = orderNumber AND orderNumber = "+orderNumber+" ORDER BY lastNegotiationDate DESC LIMIT 1;");
            if(resultSetorderShip.next()){
                shipPercent = resultSetorderShip.getDouble(1);
            }
            //Get the tax percent amount for the given order
            ResultSet resultSetorderTax = statement.executeQuery("SELECT taxPercentage FROM taxes INNER JOIN orders ON orderTaxID=taxID AND orderNumber = "+orderNumber+" ORDER BY taximplementationDate DESC LIMIT 1;");
            if(resultSetorderTax.next()){
                taxPercent = resultSetorderTax.getDouble(1);
            }

            //Finally, calculate the total amount
            finalAmount = amount + ((amount*promoPercent)/100) + ((amount*shipPercent)/100) + ((amount*taxPercent)/100);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return finalAmount;
    }
}
