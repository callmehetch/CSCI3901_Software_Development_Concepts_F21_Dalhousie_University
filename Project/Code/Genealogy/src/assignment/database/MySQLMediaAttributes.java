package assignment.database;

import java.sql.*;

import static assignment.database.MySQLConnector.connector;

public class MySQLMediaAttributes extends MySQLPersonAttributes {
    private final Connection connect;

    //Constructor to initialize the database connection.
    public MySQLMediaAttributes() {
        connect = connector();
    }

    //Method to update the Media date taken from the mediaAttributes method
    public void updateMediaDate(String mediaDate, int ID) {
        //String of SQL query to be executed below using prepared statement
        //Query to update the media date for a file related to the given mediaID
        String sql = "update media set mediaDate=" + "'" + mediaDate + "' " +
                "WHERE mediaID = " + "'" + ID + "'";
        try (PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            //Prepared statement executing update
            preparedStatement.executeUpdate();
            //SQL exception catch
        } catch (SQLException e) {
            System.out.printf("Exception- [%s]: %s%n", sql, e.getMessage());
        }
    }

    //Method to update the Media date taken from the mediaAttributes method
    public void updateMediaLocation(String mediaLocation, int ID) {
        ResultSet resultSet;
        //String of SQL query to be executed below using prepared statement
        //Query to get the existing location ID if present in the database
        String sql = "select commonlocationID from commonlocation where city = \"" + mediaLocation + "\"  OR province = \"" + mediaLocation + "\" " +
                "OR countryName = \"" + mediaLocation + "\" ";
        try (PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            //Prepared statement executing query
            resultSet = preparedStatement.executeQuery();
            Statement statement = connect.createStatement();
            if (resultSet.next()) {
                //if provided location is already there in the database, just add the medialocation table with the given mediaID
                statement.execute("insert into medialocation (mediaID, commonLocationID) values (" + ID + " , " + resultSet.getInt(1) + ")");
            } else {
                //otherwise add the new location to common location table and add this commonlocationID to medialocation table along with the mediID provided
                statement.execute("insert into commonlocation (city) values ('" + mediaLocation + "')");
                statement.execute("insert into medialocation (mediaID, commonLocationID)  (select " + ID + ", commonLocationID from commonlocation " +
                        "where city = \"" + mediaLocation + "\"   OR province = \"" + mediaLocation + "\" OR countryName = \"" + mediaLocation + "\")");
            }
            //SQL exception catch
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.printf("Exception- [%s]: %s%n", sql, e.getMessage());
        }
    }

    public void updateMediaTag(String mediaTag, int ID) {
        ResultSet resultSet;
        //String of SQL query to be executed below using prepared statement
        //Query to get the existing tag ID if present in the database
        String sql = "select tagID from tag where tagOfMedia = \"" + mediaTag + "\" ";
        try (PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            //Prepared statement executing query
            resultSet = preparedStatement.executeQuery();
            Statement statement = connect.createStatement();
            if (resultSet.next()) {
                //if provided tag is already there in the database, just add the mediatags table with the given mediaID
                statement.execute("insert into mediatags (mediaID, tagID) values (" + ID + " , " + resultSet.getInt(1) + ")");
            } else {
                //otherwise add the new tag to tag table and add this tagID to mediatags table along with the mediID provide
                statement.execute("insert into tag (tagOfMedia) values ('" + mediaTag + "')");
                statement.execute("insert into mediatags (mediaID, tagID)  (select \"" + ID + "\", tagID from tag where tagOfMedia = \"" + mediaTag + "\" )");
            }
            //SQL exception catch
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.printf("Exception- [%s]: %s%n", sql, e.getMessage());
        }
    }

    public void updatePeopleinMedia(int mediaID, int personID) {
        //String of SQL query to be executed below using prepared statement
        String sql = "insert into peopleinmedia (mediaID, personID) values(" + mediaID + "," + personID + ")";
        try (PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            //Prepared statement executing query
            preparedStatement.execute();
            //SQL exception catch
        } catch (SQLException e) {
            System.out.printf("Exception- [%s]: %s%n", sql, e.getMessage());
        }
    }
}
