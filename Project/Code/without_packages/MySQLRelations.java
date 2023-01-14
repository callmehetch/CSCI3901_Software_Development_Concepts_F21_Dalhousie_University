import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class MySQLRelations extends MySQLMediaAttributes {
    private final Connection connect;

    //Constructor to initialize the database connection.
    public MySQLRelations() {
        connect = connector();
    }

    //Method to add child into the relations table in the database
    public void addChild(int parentID, int childID) {
        //String of SQL query to be executed below using prepared statement
        //Query to insert relation between two persons as child
        String sql = "insert into relations(firstPartnerID, secondPartnerID, relation) " +
                "values ( " + parentID + "," + childID + ",'child');";
        try (PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            //SQL exception catch
        } catch (SQLException e) {
            System.out.printf("Exception- [%s]: %s%n", sql, e.getMessage());
        }
    }

    //Method to add partner relation into the relations table in the database
    public void addPartnering(int firstPartnerID, int secondPartnerID) {
        //String of SQL query to be executed below using prepared statement
        //Query to insert relation between two persons as partner
        String sql = "insert into relations(firstPartnerID, secondPartnerID, relation) " +
                "values ( " + firstPartnerID + "," + secondPartnerID + ",'partner');";
        try (PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            //SQL exception catch
        } catch (SQLException e) {
            System.out.printf("Exception- [%s]: %s%n", sql, e.getMessage());
        }
    }

    //Method to add dissolute relation into the relations table in the database
    public void addDissolution(int firstPartnerID, int secondPartnerID) {
        //String of SQL query to be executed below using prepared statement
        //Query to insert relation between two persons as dissolution
        String sql = "insert into relations(firstPartnerID, secondPartnerID, relation) " +
                "values ( " + firstPartnerID + "," + secondPartnerID + ",'dissolute');";
        try (PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            //SQL exception catch
        } catch (SQLException e) {
            System.out.printf("Exception- [%s]: %s%n", sql, e.getMessage());
        }
    }
}
