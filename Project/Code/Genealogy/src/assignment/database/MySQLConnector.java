package assignment.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLConnector {
    //Connecter method for initializing database connection with the below crendentials
    public static Connection connector() {
        //Database credentials. please change them according to the database. Thank you
        final String dbHost = "jdbc:mysql://db.cs.dal.ca:3306?serverTimezone=UTC&useSSL=false";
        final String username = "nadipineni";
        final String password = "B00899473";
        final String database = "use nadipineni;";
        //Initializing the database connection
        Statement statement;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //create a connection using the above credentials
            Connection connection = DriverManager.getConnection(dbHost, username, password);
            statement = connection.createStatement();
            //Statement to use the specified database
            statement.execute(database);
            return connection;

            //SQL exception catch
        } catch (SQLException e1) {
            System.out.println("Error at database");
            e1.printStackTrace();
            return null;
            //Class not found exception for jdbc connector
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}

