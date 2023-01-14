package assignment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class CreateXMLFile extends MySQLCredentials{

    public boolean mySQLQuery(String startYear, String endYear, String filePath) {
        //-----------Please use the below sting to change the database-------------------------------
        final String selectDatabase = "USE csci3901;";
        //-------------------------------------------------------------------------------------------

        //Check for null values
        if(startYear==null || endYear==null || filePath==null)
            return false;

        //Validate date
        if(!(isValidDate(startYear, endYear))){
            System.out.println("Invalid Date");
            return false;
        }

        Statement statement;
        ResultSet resultSet;
        //-------------------Query to get Managers----------------------------
        final String resultSetQuery = "SELECT e1.firstName, e1.lastName, o.city, COUNT(DISTINCT e2.employeeNumber), count(DISTINCT c.customerNumber), SUM(e.s), e1.employeeNumber\n" +
                "FROM employees e1\n" +
                "INNER JOIN offices o ON e1.officeCode = o.officeCode\n" +
                "INNER JOIN employees e2 ON e1.employeeNumber = e2.reportsTo\n" +
                "INNER JOIN customers c ON e2.employeeNumber = c.salesRepEmployeeNumber \n" +
                "LEFT JOIN (SELECT o.customerNumber cn, SUM(od.quantityOrdered * od.priceEach) s FROM orders o\n" +
                "INNER JOIN orderdetails od ON o.orderNumber=od.orderNumber AND o.orderDate BETWEEN \'"+startYear+"\' AND \'"+endYear+"\' GROUP BY o.customerNumber) e on c.customerNumber=e.cn \n" +
                "GROUP BY e1.employeeNumber ORDER BY e1.firstName;";

        try {
            Connection connect = MySQLCredentials();
            statement = connect.createStatement();
            //System.out.println("Connection success");

            //Document builder declaration
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder newDocumentBuilder = builderFactory.newDocumentBuilder();
            Document newDocument = newDocumentBuilder.newDocument();
            newDocument.setXmlStandalone(true);

            //Creating an element with yearEnd report method
            Element yearEndReport = yearEndReportMethod(startYear, endYear, newDocument);

            //Element with taf manager list
            final Element managerList =newDocument.createElement("manager_list");
            yearEndReport.appendChild(managerList);

            statement.execute (selectDatabase);
            //Run through the managerlist as long as there is resultset
            resultSet = statement.executeQuery(resultSetQuery);
            while (resultSet.next()) {
                manager(newDocument, managerList, resultSet);
            }
            //Creating productline list element
            final Element productLineList=newDocument.createElement("product_line_list");
            yearEndReport.appendChild(productLineList);
            //Query for the productline list
            Statement statementProduct=connect.createStatement();
            ResultSet resultSetProduct=statementProduct.executeQuery("SELECT distinct pl.productLine, pl.textDescription FROM productlines pl\n" +
                    "INNER JOIN products p ON pl.productLine=p.productLine \n" +
                    "INNER JOIN orderdetails od ON p.productCode=od.productCode\n" +
                    "INNER JOIN orders o ON o.orderNumber = od.orderNumber AND o.orderDate BETWEEN \'"+startYear+"\' AND \'"+endYear+"\';");

            //Loop through the productline list
            while(resultSetProduct.next()){
                Element productLine = product(newDocument, productLineList, resultSetProduct);
                //Query for the customer list
                String productLineString = resultSetProduct.getString(1);
                Statement statementCustomer=connect.createStatement();
                ResultSet resultSetCustomer=statementCustomer.executeQuery("SELECT c.customerName, sum(od.quantityOrdered*od.priceEach) from customers c \n" +
                        "INNER JOIN orders o ON o.customerNumber = c.customerNumber\n" +
                        "INNER JOIN orderdetails od ON od.orderNumber = o.orderNumber \n" +
                        "INNER JOIN products p ON od.productCode=p.productCode \n" +
                        "INNER JOIN productlines pl ON p.productLine=pl.productLine\n" +
                        "AND p.productLine=\""+productLineString+"\" AND o.orderDate BETWEEN \'"+startYear+"\' AND \'"+endYear+"\' GROUP BY c.customerNumber;");
               //Run through the customer method as long as the resultset has an element
                while(resultSetCustomer.next()){
                    customer(newDocument, productLine, resultSetCustomer);
                }
            }

            //Transform factory instance has been created
            TransformerFactory transformerFactoryInstance = TransformerFactory.newInstance();
            Transformer newTransformer = transformerFactoryInstance.newTransformer();
            //Keep doc type as public
            newTransformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");
            //To improve human readability - indent has been added
            newTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
            //To make indivisual element indent
            newTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(newDocument);
            //Streaming the result to the file given
            StreamResult sr = new StreamResult(new File(filePath));
            newTransformer.transform(source, sr);

        } catch ( SQLException | ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean isValidDate(String startDate, String endDate){
        //date validation - Strict to make sure leap years and non leap years, months and everything is checked
        try {
            LocalDate.parse(startDate, DateTimeFormatter.ofPattern("uuuu-M-d").withResolverStyle(ResolverStyle.STRICT));
            LocalDate.parse(endDate, DateTimeFormatter.ofPattern("uuuu-M-d").withResolverStyle(ResolverStyle.STRICT));
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }


    //Method to create year end report elements
    public Element yearEndReportMethod(String startYear, String endYear, Document doc){
        //append year end report to doc
        Element yearEndReport = doc.createElement("year_end_report");
        doc.appendChild(yearEndReport);

        //Append year to year end report
        final Element year = doc.createElement("year");
        yearEndReport.appendChild(year);

        //append start date to year
        final Element startDate = doc.createElement("start_date");
        startDate.appendChild(doc.createTextNode(startYear));
        year.appendChild(startDate);

        //append year to end date
        final Element endDate = doc.createElement("end_date");
        endDate.appendChild(doc.createTextNode(endYear));
        year.appendChild(endDate);

        return yearEndReport;
    }

    //Method to create manager elements
    public void manager(Document doc, Element managerList, ResultSet resultSet) {
        Element manager =doc.createElement("manager");
        try {
            //append manager mist to manager
            managerList.appendChild(manager);

            //append manager name to the manager
            final Element managerName=doc.createElement("manager_name");
            managerName.appendChild(doc.createTextNode(resultSet.getString(1) + " " + resultSet.getString(2)));
            manager.appendChild(managerName);

            //append manager city to manager
            final Element managerCity=doc.createElement("manager_city");
            managerCity.appendChild(doc.createTextNode(resultSet.getString(3)));
            manager.appendChild(managerCity);

            //append staff to manager
            final Element noOfEmployees=doc.createElement("staff");
            noOfEmployees.appendChild(doc.createTextNode(resultSet.getString(4)));
            manager.appendChild(noOfEmployees);

            //append customers to manager
            final Element noOfCustomers=doc.createElement("customers");
            noOfCustomers.appendChild(doc.createTextNode(resultSet.getString(5)));
            manager.appendChild(noOfCustomers);

            //append sales_value to manager
            final Element totalSales=doc.createElement("sales_value");
            totalSales.appendChild(doc.createTextNode(resultSet.getString(6)));
            manager.appendChild(totalSales);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Method to create product elements
    public  Element product(Document doc, Element productLineList, ResultSet resultSetProduct){
        Element productLine = null;
        try{
            //append product line to the product line list
            productLine = doc.createElement("product_line");
            productLineList.appendChild(productLine);

            //append product line name to product line
            final Element productLineName = doc.createElement("product_line_name");
            productLineName.appendChild(doc.createTextNode(resultSetProduct.getString(1)));
            productLine.appendChild(productLineName);

            //append production line description to productline
            final Element productLineDescription = doc.createElement("product_line_description");
            productLineDescription.appendChild(doc.createTextNode(resultSetProduct.getString(2)));
            productLine.appendChild(productLineDescription);
        } catch (
        SQLException e) {
            e.printStackTrace();
        }
        return productLine;
    }

    //Method to create customer
    public void customer(Document doc, Element productLine, ResultSet resultSetCustomer){
        try {
            //append customer to productline
            final Element customer=doc.createElement("customer");
            productLine.appendChild(customer);

            //append customer name to customer
            final Element customerName = doc.createElement("customer_name");
            customerName.appendChild(doc.createTextNode(resultSetCustomer.getString(1)));
            customer.appendChild(customerName);

            //append order value to customer
            final Element orderValue = doc.createElement("order_value");
            orderValue.appendChild(doc.createTextNode(resultSetCustomer.getString(2)));
            customer.appendChild(orderValue);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
