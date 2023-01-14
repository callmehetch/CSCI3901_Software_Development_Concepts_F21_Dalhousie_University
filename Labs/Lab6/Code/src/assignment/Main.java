package assignment;

import java.sql.*;
public class Main {

    public static void main(String[] args) {
	Connection connect = null;
    Statement statement = null;
    ResultSet resultSet = null;

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connect = DriverManager.getConnection("jdbc:mysql://db.cs.dal.ca:3306?serverTimezone=UTC&useSSL=false", "nadipineni", "B00899473");
        statement = connect.createStatement();
        System.out.println("Connection success");
        statement.execute ("use csci3901;");
        resultSet = statement.executeQuery("select lastName from employees limit 3;");
        while (resultSet.next()) {
            System.out.println("Employee name: " + resultSet.getString("lastName"));
        }
        resultSet.close();
        statement.close();
        connect.close();
    } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
    }
    }
}
