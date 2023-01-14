import assignment.classes.FamilyTree;
import assignment.classes.FileIdentifier;
import assignment.classes.PersonIdentity;
import assignment.database.MySQLFetcher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenealogyPopulator {
    //Declaring the lists and maps for person identity, file identity, family tree and file identifier
    private final List<PersonIdentity> personIdentityList = new ArrayList<>();
    private final List<FileIdentifier> fileIdentifierList = new ArrayList<>();
    private final Map<Integer, FamilyTree> familyTreeMap = new HashMap<>();
    private final Map<Integer, FileIdentifier> fileIdentifierMap = new HashMap<>();
    private final MySQLFetcher mySQLFetcher = new MySQLFetcher();

    //Constructor to populate persons, media, relations when genealogy populator is called
    public GenealogyPopulator() {
        //--------------------------------------Persons-----------------------------------------------------------------
        personPopulator();
        //--------------------------------------Media-----------------------------------------------------------------
        mediaPopulator();
        //----------------------------------------------relations-------------------------------------------------------
        relationsPopulator();
    }

    //--------------------------------------------------Persons-----------------------------------------------------------

    //Method to populate persons from the previous data existing in the database
    private void personPopulator() {
        //declare the table name to persons
        final String personsTableName = "persons";
        //Chcek if there is existing data in the persons table by fetching the total number of rows
        int personRowCount = mySQLFetcher.totalRows(personsTableName);
        if (personRowCount > 0) {
            try {
                //get the resultset from mysql fetch query with complete data of persons available in the database
                ResultSet personResultSet = mySQLFetcher.fetchPesons();
                while (personResultSet.next()) {
                    //------------------addPerson
                    PersonIdentity person = new PersonIdentity(personResultSet.getInt(1),
                            personResultSet.getString(2));
                    //Add the person into the familyTree
                    familyTreeMap.put(person.getPersonID(), new FamilyTree(person));
                    //Add the person into the personIdentity list
                    personIdentityList.add(person);
                    System.out.println("Fetching PersonID: " + person.getPersonID() + " " +
                            "from the previous data and passing it to personIdentity ");
                    //Call other methods to populate attributes of a person
                    populatePersonAttributes(personResultSet, person);
                    populatePersonRemainingAttributes(person);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //Method being called from persons populator. Method to populate the existing person attributes from the previous data existing in the database
    private void populatePersonAttributes(ResultSet personResultSet, PersonIdentity person) throws SQLException {
        //Set the attributes to the person as following
        person.setBirthDate(personResultSet.getString(3));
        person.setBirthLocation(personResultSet.getString(4));
        person.setDeathDate(personResultSet.getString(5));
        person.setDeathLocation(personResultSet.getString(6));
        person.setGender(personResultSet.getString(7));
    }

    //Method being called from person populator. Method to populate the remaining person attributes from the previous data existing in the database
    private void populatePersonRemainingAttributes(PersonIdentity person) throws SQLException {
        ResultSet resultSet1 = mySQLFetcher.fetchOccupation(person.getPersonID());
        if (resultSet1.next()) {
            person.setOccupationName(resultSet1.getString(1));
        }
        //-------------------- set Reference fetched from the database
        ResultSet resultSet2 = mySQLFetcher.fetchReference(person.getPersonID());
        while (resultSet2.next()) {
            person.getReferencesOfPerson().add(resultSet2.getString(1));
        }
        //---------------------set Notes fetched from the database
        ResultSet resultSet3 = mySQLFetcher.fetchNotes(person.getPersonID());
        while (resultSet3.next()) {
            person.getNotesOfPerson().add(resultSet3.getString(1));
        }
    }
    //--------------------------------------------------Media-----------------------------------------------------------

    //Method to populate media from the previous data existing in the database
    private void mediaPopulator() {
        //declare the table name to relations
        final String mediaTableName = "media";
        //Chcek if there is existing data in the media table by fetching the total number of rows
        if (mySQLFetcher.totalRows(mediaTableName) > 0) {
            try {
                //get the resultset from mysql fetch query with complete data of media available in the database
                ResultSet mediaResultSet = mySQLFetcher.fetchMedia();
                while (mediaResultSet.next()) {
                    //Create a new fileIdentifier and populate it with addMedia fileIdentifier
                    FileIdentifier fileIdentifier = populateAddMedia(mediaResultSet);
                    //Calling populating metodhs for media attributes
                    populateMediaLocation(fileIdentifier);
                    populateMediaTags(fileIdentifier);
                    populatePeopleInMedia(fileIdentifier);
                    //Finally put this media fileidentifier into the fileidentifier map
                    fileIdentifierMap.put(mediaResultSet.getInt(1), fileIdentifier);
                }
                //SQL exception catch
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //Method being called from media populator. Method to populate the existing media from the previous data existing in the database
    private FileIdentifier populateAddMedia(ResultSet mediaResultSet) throws SQLException {
        //Set the media file attributes as following
        FileIdentifier fileIdentifier = new FileIdentifier(mediaResultSet.getString(2));
        fileIdentifier.setMediaID(mediaResultSet.getInt(1));
        fileIdentifier.setMediaFileLocation(mediaResultSet.getString(2));
        fileIdentifier.setDateTaken(mediaResultSet.getString(3));
        //Add this file identifier to the fileidentifier list
        fileIdentifierList.add(fileIdentifier);
        System.out.println("Fetching mediaID: " + fileIdentifier.getMediaID() + " from the previous data and passing it to FileIdentifier");
        return fileIdentifier;
    }

    //Method being called from media populator. Method to populate the people existing in the media from the previous data existing in the database
    private void populatePeopleInMedia(FileIdentifier fileIdentifier) throws SQLException {
        //Populate the people in media list of people after fetching people from the database
        //Fetch the resultset of people using mysql fetcher
        ResultSet resultSet3 = mySQLFetcher.fetchPeopleInMedia(fileIdentifier.getMediaID());
        //
        while (resultSet3.next()) {
            //While the resultSet has some data find the person if exists in the personIdentityList
            for (PersonIdentity personidentity : personIdentityList) {
                //If exists in the list add the person to the people list related to the fileIdentifier
                if (personidentity.getPersonID() == resultSet3.getInt(1)) {
                    fileIdentifier.getPeopleInMedia().add(personidentity);
                }
            }
        }
    }

    //Method being called from media populator. Method to populate the tags existing in the media from the previous data existing in the database
    private void populateMediaTags(FileIdentifier fileIdentifier) throws SQLException {
        //Populate the tags in media after fetching people from the database
        //Fetch the resultset of people using mysql fetcher
        ResultSet resultSet2 = mySQLFetcher.fetchMediaTags(fileIdentifier.getMediaID());
        while (resultSet2.next()) {
            //While the resultSet has some data add it to the fileIdentifier
            fileIdentifier.getTagsOfMedia().add(resultSet2.getString(1));
        }
    }

    //Method being called from media populator. Method to populate the attributes existing in the media from the previous data existing in the database
    private void populateMediaLocation(FileIdentifier fileIdentifier) throws SQLException {
        //Populate the location in media after fetching people from the database
        //Fetch the resultset of people using mysql fetcher
        ResultSet resultSet1 = mySQLFetcher.fetchMediaLocation(fileIdentifier.getMediaID());
        if (resultSet1.next()) {
            //If the resultset has some data, set the location
            fileIdentifier.setLocationOfMedia(resultSet1.getString(1));
        }
    }


    //--------------------------------------------------Relations-----------------------------------------------------------
    //Method to populate relation from the previous data existing in the database
    private void relationsPopulator() {
        //declare the table name to relations
        final String relationTableName = "relations";
        //Chcek if there is existing data in the relations table by fetching the total number of rows
        int relationsRowCount = mySQLFetcher.totalRows(relationTableName);
        if (relationsRowCount > 0) {
            try {
                //get the resultset from mysql fetch query with complete data of relations available in the database
                ResultSet relationResultSet = mySQLFetcher.fetchRelation();
                while (relationResultSet.next()) {
                    //Check if the person ID already there in the familytree that
                    if (!familyTreeMap.containsKey(relationResultSet.getInt(2)) || !familyTreeMap.containsKey(relationResultSet.getInt(3))) {
                        continue;
                    }
                    //Call the personRelations populating method
                    personRealtionsPopulate(relationResultSet);
                }
                //SQL exception catch
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //Method being called from relations populator. Method to populate the persons relation from the previous data existing in the database
    private void personRealtionsPopulate(ResultSet relationResultSet) throws SQLException {
        //Iterate through the persons in the person Identity list to find the persons if exist
        for (int i = 0, personIdentityListSize1 = personIdentityList.size(); i < personIdentityListSize1; i++) {
            PersonIdentity personidentity1 = personIdentityList.get(i);
            //If the first person is existing
            if (personidentity1.getPersonID() == relationResultSet.getInt(2)) {
                //check for the secondperson by iteration
                for (int j = 0, personIdentityListSize2 = personIdentityList.size(); j < personIdentityListSize2; j++) {
                    PersonIdentity personidentity2 = personIdentityList.get(j);
                    //If the person identity matches with the person from the resultset
                    if (personidentity2.getPersonID() == relationResultSet.getInt(3)) {
                        //Call the methods to populate child, partner, diccolute
                        childPopulator(relationResultSet, i, j);
                        partnerPopulator(relationResultSet, i, j);
                        dissolutePopulator(relationResultSet, i, j);
                    }
                }
            }
        }
    }

    //Method being called from person relations populator. Method to populate the dissolute relations from the previous data existing in the database
    private void dissolutePopulator(ResultSet relationResultSet, int i, int j) throws SQLException {
        //If the relation mentioned in the database is dissolute,
        if (relationResultSet.getString(4).equalsIgnoreCase("dissolute")) {
            System.out.println("Fetching dissolution relation between: " + personIdentityList.get(i).getPersonID() + "-" + personIdentityList.get(j).getPersonID());
            //Keep the key 0 for dissolutionsa add them to the Map of partners for the first partner
            familyTreeMap.get(personIdentityList.get(i).getPersonID()).getMapofPartners().put(0, personIdentityList.get(j));
            //Keep the key 0 for dissolutionsa add them to the Map of partners for the second partner
            familyTreeMap.get(personIdentityList.get(j).getPersonID()).getMapofPartners().put(0, personIdentityList.get(i));
        }
    }

    //Method being called from person relations populator. Method to populate the partner relations from the previous data existing in the database
    private void partnerPopulator(ResultSet relationResultSet, int i, int j) throws SQLException {
        if (relationResultSet.getString(4).equalsIgnoreCase("partner")) {
            System.out.println("Fetching partner relation between: " + personIdentityList.get(i).getPersonID() + "-" + personIdentityList.get(j).getPersonID());
            //Keep the key 1 for partnerships add them to the Map of partners for the first partner
            familyTreeMap.get(personIdentityList.get(i).getPersonID()).getMapofPartners().put(1, personIdentityList.get(j));
            //Keep the key 1 for partnerships add them to the Map of partners for the second partner
            familyTreeMap.get(personIdentityList.get(j).getPersonID()).getMapofPartners().put(1, personIdentityList.get(i));
        }
    }

    //Method being called from person relations populator. Method to populate the child relations from the previous data existing in the database
    private void childPopulator(ResultSet relationResultSet, int i, int j) throws SQLException {
        if (relationResultSet.getString(4).equalsIgnoreCase("child")) {
            System.out.println("Fetching parent/child relation between: " + personIdentityList.get(i).getPersonID() + "-" + personIdentityList.get(j).getPersonID());
            //Add the list of children to the parents in the familytreemap
            familyTreeMap.get(personIdentityList.get(i).getPersonID()).getListOfChildren().add(personIdentityList.get(j));
            //Add the parent to the children in the familytreemap
            familyTreeMap.get(personIdentityList.get(j).getPersonID()).getListOfParents().add(personIdentityList.get(i));
        }
    }


    //---------------------Getters And Setters For Encapsulation Of the Data Attributes---------------------------------
    public List<PersonIdentity> getPersonIdentityList() {
        return personIdentityList;
    }

    public List<FileIdentifier> getFileIdentifierList() {
        return fileIdentifierList;
    }

    public Map<Integer, FamilyTree> getFamilyTreeMap() {
        return familyTreeMap;
    }

    public Map<Integer, FileIdentifier> getFileIdentifierMap() {
        return fileIdentifierMap;
    }
    //---------------------Getters And Setters For Encapsulation Of the Data Attributes---------------------------------
}
