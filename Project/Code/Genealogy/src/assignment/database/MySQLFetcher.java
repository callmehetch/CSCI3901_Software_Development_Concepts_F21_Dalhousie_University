package assignment.database;

import assignment.classes.FileIdentifier;
import assignment.classes.PersonIdentity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static assignment.database.MySQLConnector.connector;

public class MySQLFetcher {
    //get connection from the MySQL Connector and initialize in the constructor
    private final Connection connect;

    //Constructor to initialize the connection to the database
    public MySQLFetcher() {
        connect = connector();
    }

    //Method to check if the person is present in the database using the below query
    public boolean isPersonPresentinDB(PersonIdentity personID) {
        try {
            Statement statement = connect.createStatement();
            //Store the result from the executed query into the resultSet declared
            ResultSet resultSet = statement.executeQuery("select personID from persons where personID = " + personID.getPersonID() + ";");
            //If the resultset has any data, then person is there in DB
            if (resultSet.next()) {
                return true;
            }
            //SQL exception catch
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Method to check if the media file is present in the database using the below query
    public boolean isMediaPresentinDB(FileIdentifier mediaID) {
        try {
            Statement statement = connect.createStatement();
            //Store the result from the executed query into the resultSet declared
            ResultSet resultSet = statement.executeQuery(new StringBuilder().append("select personID from persons where personID = ").append(mediaID.getMediaID()).append(";").toString());
            //If the resultset has any data, then media is there in DB
            if (resultSet.next()) {
                return true;
            }
            //SQL exception catch
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //--------------------------Single method to count the total rows in a given table----------------------------------------
    public int totalRows(String table) {
        int count = 0;
        try {
            Statement statement = connect.createStatement();
            //Store the result from the executed query into the resultSet declared
            ResultSet resultSet = statement.executeQuery(new StringBuilder().append("select count(*) from ").append(table).append(";").toString());
            //get the count ffrom resultSet if it has next
            if (resultSet.next())
                count = resultSet.getInt(1);
            return count;
            //SQL exception catch
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    //--------------------------Single method to count the total rows in a given table----------------------------------------

    //Method to fetch complete data from the persons table to repopulate
    public ResultSet fetchPesons() {
        ResultSet resultSet = null;
        try {
            Statement statement = connect.createStatement();
            //Store the result from the executed query into the resultSet declared
            resultSet = statement.executeQuery("select * from persons;");
            //SQL exception catch
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    //Method to fecth occupations from the occupations table for the personID given
    public ResultSet fetchOccupation(int personID) {
        ResultSet resultSet = null;
        try {
            Statement statement = connect.createStatement();
            //Store the result from the executed query into the resultSet declared
            resultSet = statement.executeQuery(new StringBuilder().append("select occupationName, p.personID from occupations o, personoccupation po, persons p " +
                    "where o.occupationId=po.occupationID AND po.personID=").append(personID).append("; ").toString());
            //SQL exception catch
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    //Method to fetch references for the personID given
    public ResultSet fetchReference(int personID) {
        ResultSet resultSet = null;
        try {
            Statement statement = connect.createStatement();
            //Store the result from the executed query into the resultSet declared
            resultSet = statement.executeQuery(new StringBuilder().append("select referencesOfPerson from referencetable where personID = ").append(personID).append("; ").toString());
            //SQL exception catch
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    //Method to fetch notes for the personID given
    public ResultSet fetchNotes(int personID) {
        ResultSet resultSet = null;
        try {
            Statement statement = connect.createStatement();
            //Store the result from the executed query into the resultSet declared
            resultSet = statement.executeQuery(new StringBuilder().append("select notesOfPerson from notes n, persons p, personnotes pn where pn.personID=p.personID AND n.notesID=pn.notesID AND p.personID = ").append(personID).append("; ").toString());
            //SQL exception catch
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    //Method to fetch complete data from the media table to repopulate
    public ResultSet fetchMedia() {
        ResultSet resultSet = null;
        try {
            Statement statement = connect.createStatement();
            //Store the result from the executed query into the resultSet declared
            resultSet = statement.executeQuery("select * from media;");
            //SQL exception catch
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    //Method to fetch location for the mediaID given
    public ResultSet fetchMediaLocation(int mediaID) {
        ResultSet resultSet = null;
        try {
            Statement statement = connect.createStatement();
            //Store the result from the executed query into the resultSet declared
            resultSet = statement.executeQuery(new StringBuilder().append("select city from commonlocation c, medialocation ml, media m where c.commonLocationID= ml.mediaLocationID AND m.mediaID = ml.mediaID AND m.mediaID = ").append(mediaID).append("; ").toString());
            //SQL exception catch
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    //Method to fetch tags for the mediaID given
    public ResultSet fetchMediaTags(int mediaID) {
        ResultSet resultSet = null;
        try {
            Statement statement = connect.createStatement();
            //Store the result from the executed query into the resultSet declared
            resultSet = statement.executeQuery(new StringBuilder().append("select tagOfMedia from tag t, mediatags mt, media m where t.tagID = mt.tagID AND m.mediaID=mt.mediaID AND m.mediaID = ").append(mediaID).append("; ").toString());
            //SQL exception catch
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    //Method to fetch people linked to the media for the mediaID given
    public ResultSet fetchPeopleInMedia(int mediaID) {
        ResultSet resultSet = null;
        try {
            Statement statement = connect.createStatement();
            //Store the result from the executed query into the resultSet declared
            resultSet = statement.executeQuery(new StringBuilder().append("select personID from peopleinmedia where mediaID = ").append(mediaID).append("; ").toString());
            //SQL exception catch
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    //Method to fetch complete data from the relations table to repopulate
    public ResultSet fetchRelation() {
        ResultSet resultSet = null;
        try {
            Statement statement = connect.createStatement();
            //Store the result from the executed query into the resultSet declared
            resultSet = statement.executeQuery("select * from relations");
            //SQL exception catch
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    //Method to check if the personID is present in the database using the below query
    public boolean isPersonIDPresentinDB(int personID) {
        try {
            Statement statement = connect.createStatement();
            //Store the result from the executed query into the resultSet declared
            ResultSet resultSet = statement.executeQuery(new StringBuilder().append("select personID from persons where personID = ").append(personID).append(";").toString());
            if (resultSet.next()) {
                return true;
            }
            //SQL exception catch
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Method to check if the media file name is present in the database using the below query
    public String isFileLocationPresent(String name) {
        try {
            Statement statement = connect.createStatement();
            //Store the result from the executed query into the resultSet declared
            ResultSet resultSet = statement.executeQuery(new StringBuilder().append("select fileLocation from media where fileLocation= '").append(name).append("';").toString());
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
            //SQL exception catch
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
