import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Class to store credentials
public class MySQLCredentials {
    public Connection MySQLCredentials() {
        //Please change the credentials accordingly
        final String dbHost = "jdbc:mysql://db.cs.dal.ca:3306?serverTimezone=UTC&useSSL=false";
        final String username = "nadipineni";
        final String password = "B00899473";
        Connection connect = null;

        try {
            //JDBC Driver class
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(dbHost, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connect;
    }
}
