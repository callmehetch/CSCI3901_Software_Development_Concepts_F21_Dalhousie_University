import java.sql.*;

public class MySQLPersonAttributes {
    private final Connection connect;

    //Constructor to initialize the database connection.
    public MySQLPersonAttributes() {
        connect = connector();
    }

    //Method to update the birthDate for a given personID
    public void updateBirthDate(String birthDate, int ID) {
        //String of SQL query to be executed below using prepared statement
        //Query to update the birthDate for a person related to the given personID
        String sql = "update persons set birthDate=" + "'" + birthDate + "' " +
                "WHERE personID = " + "'" + ID + "'";
        try (PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            //Prepared statement executing update
            preparedStatement.executeUpdate();
            //SQL exception catch
        } catch (SQLException e) {
            System.out.printf("Exception- [%s]: %s%n", sql, e.getMessage());
        }
    }

    //Method to update the birthLocation for a given personID
    public void updateBirthLocation(String birthLocation, int ID) {
        //String of SQL query to be executed below using prepared statement
        //Query to update the birthLocation for a person related to the given personID
        String sql = "update persons set birthLocation=" + "'" + birthLocation + "' " +
                "WHERE personID = " + "'" + ID + "'";
        try (PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            //Prepared statement executing update
            preparedStatement.executeUpdate();
            //SQL exception catch
        } catch (SQLException e) {
            System.out.printf("Exception- [%s]: %s%n", sql, e.getMessage());
        }
    }

    //Method to update the deathDate for a given personID
    public void updateDeathDate(String deathDate, int ID) {
        //String of SQL query to be executed below using prepared statement
        //Query to update the deathDate for a person related to the given personID
        String sql = "update persons set deathDate=" + "'" + deathDate + "' " +
                "WHERE personID = " + "'" + ID + "'";
        try (PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            //Prepared statement executing update
            preparedStatement.executeUpdate();
            //SQL exception catch
        } catch (SQLException e) {
            System.out.printf("Exception- [%s]: %s%n", sql, e.getMessage());
        }
    }

    //Method to update the deathLocation for a given personID
    public void updateDeathLocation(String deathLocation, int ID) {
        //String of SQL query to be executed below using prepared statement
        //Query to update the deathLocation for a person related to the given personID
        String sql = "update persons set deathLocation=" + "'" + deathLocation + "' " +
                "WHERE personID = " + "'" + ID + "'";
        try (PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            //Prepared statement executing update
            preparedStatement.executeUpdate();
            //SQL exception catch
        } catch (SQLException e) {
            System.out.printf("Exception- [%s]: %s%n", sql, e.getMessage());
        }
    }

    //Method to update the gender for a given personID
    public void updateGender(String gender, int ID) {
        //String of SQL query to be executed below using prepared statement
        //Query to update the gender for a person related to the given personID
        String sql = "update persons set gender=" + "'" + gender + "' " +
                "WHERE personID = " + "'" + ID + "'";
        try (PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            //Prepared statement executing update
            preparedStatement.executeUpdate();
            //SQL exception catch
        } catch (SQLException e) {
            System.out.printf("Exception- [%s]: %s%n", sql, e.getMessage());
        }
    }

    //Method to update the occupation for a given personID
    public void updateOccupation(String occupation, int ID) {
        ResultSet resultSet;
        //String of SQL query to be executed below using prepared statement
        //Query to get the occupationID if the occuption is already existing in the occupation table
        String sql = "select occupationID from occupations where occupationName ='" + occupation + "'";
        try (PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            //Prepared statement executing query
            resultSet = preparedStatement.executeQuery();
            Statement statement = connect.createStatement();
            if (resultSet.next()) {
                //if provided occupation is already there in the database, just add the occupationID to personoccupation table with the given mediaID
                statement.execute("insert into personoccupation (personID, occupationID) " +
                        "values (" + ID + " , " + resultSet.getInt(1) + ")");
            } else {
                //otherwise add the new tag to tag table and add this tagID to mediatags table along with the mediID provided
                statement.execute("insert into occupations (occupationName) values ('" + occupation + "')");
                statement.execute("insert into personoccupation (personID, occupationID)  " +
                        "(select " + ID + ", occupationID from occupations where occupationName='" + occupation + "')");
            }
            //SQL exception catch
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.printf("Exception- [%s]: %s%n", sql, e.getMessage());
        }
    }

    //Method to update the references for a given personID
    public void updateReferences(String references, int ID) {
        //String of SQL query to be executed below using prepared statement
        //Query to insert references for a given personID
        String sql = "insert into referencetable (referencesOfPerson, personID) " +
                "values('" + references + "', " + ID + ")";
        try (PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            //Prepared statement executing update
            preparedStatement.executeUpdate();
            //SQL exception catch
        } catch (SQLException e) {
            System.out.printf("Exception- [%s]: %s%n", sql, e.getMessage());
        }
    }

    //Method to update the notes for a given personID
    public void updateNotes(String notes, int ID) {
        ResultSet resultSet;
        //String of SQL query to be executed below using prepared statement
        //Query to get the notesID if the given notes is already existing in the notes table
        String sql = "select notesID from notes where notesOfPerson = \"" + notes + "\" ";
        try (PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
            //Prepared statement executing query
            resultSet = preparedStatement.executeQuery();
            Statement statement = connect.createStatement();
            if (resultSet.next()) {
                //if provided notes is already there in the database, just add the notesID to personnotes table with the given personID
                statement.execute("insert into personnotes (personID, notesID) " +
                        "values (" + ID + " , " + resultSet.getInt(1) + ")");
            } else {
                //otherwise add the new notes to notes table and add this notesID to personnotes table along with the personID provided
                statement.execute("insert into notes (notesOfPerson) values ('" + notes + "')");
                statement.execute("insert into personnotes (personID, notesID)  " +
                        "(select " + ID + ", notesID from notes where notesOfPerson='" + notes + "')");
            }
            //SQL exception catch
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.printf("Exception- [%s]: %s%n", sql, e.getMessage());
        }
    }
}
