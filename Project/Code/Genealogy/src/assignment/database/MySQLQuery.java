package assignment.database;

import java.sql.*;

import static assignment.database.MySQLConnector.connector;

public class MySQLQuery extends MySQLRelations {
    private final Connection connect;

    public MySQLQuery() {
        connect = connector();
    }

    //------------------------------------Persons---------------------------------------------------

    //Method to Insert person into the database
    public void InsertPersonByName(String name) {
        try {
            Statement statement = connect.createStatement();
            //SQL query to insert person names
            statement.execute("insert into persons (personName) values ('" + name + "');");
            //SQL exception catch
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Method to get the last person id from the database
    public int getLastPersonId() {
        int personID = 0;
        ResultSet resultSet;
        //SQL qeury to get the last person id
        String sql = "select max(personID) FROM persons";
        try (PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            //Prepared statement executing query
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            //Store the last person id from the database to the previously declared personID
            personID = resultSet.getInt(1);
            //SQL exception catch
        } catch (SQLException e) {
            System.out.printf("Exception- [%s]: %s%n", sql, e.getMessage());
        }

        return personID;
    }

    //-------------------------------------Media----------------------------------------------------------
    //Method to insert media file into the database
    public void insertMedia(String name) {
        //SQL query to insert media name
        String sql = "insert into media (fileLocation) values('" + name + "')";
        try (PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            //SQL exception catch
        } catch (SQLException e) {
            System.out.printf("Exception- [%s]: %s%n", sql, e.getMessage());
        }
    }

    //Method to get the last media file id from the database
    public int getLastMediaId() {
        int mediaID = 0;
        ResultSet resultSet;
        //SQL qeury to get the last media id
        String sql = "select max(mediaID) FROM media";
        try (PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            //Prepared statement executing query
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            //Store the last media id from the database to the previously declared mediaID
            mediaID = resultSet.getInt(1);
            //SQL exception catch
        } catch (SQLException e) {
            System.out.printf("Exception- [%s]: %s%n", sql, e.getMessage());
        }

        return mediaID;
    }

}
