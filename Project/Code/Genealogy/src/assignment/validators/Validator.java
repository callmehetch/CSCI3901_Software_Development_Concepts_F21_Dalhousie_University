package assignment.validators;

import assignment.classes.FileIdentifier;
import assignment.classes.PersonIdentity;
import assignment.database.MySQLFetcher;

public class Validator {
    //initializing the mysql fetcher to make the fetching queries
    private final MySQLFetcher mySQLFetcher = new MySQLFetcher();

    //method to invoke fetchperson in my sql fecher to validate the personIdentities sending in arguments are present in the database
    public boolean isPersonPresent(PersonIdentity personID) {
        return !mySQLFetcher.isPersonPresentinDB(personID);
    }

    //method to invoke fetchpersonID in my sql fecher to validate the personID of personIdentity sending in arguments is present in the database
    public boolean isPersonIDPresent(int personID) {
        return mySQLFetcher.isPersonIDPresentinDB(personID);
    }

    //method to invoke fetchmedia in my sql fecher to validate the fileIdentifiers sending in arguments are present in the database
    public boolean isMediaPresent(FileIdentifier mediaID) {
        return !mySQLFetcher.isMediaPresentinDB(mediaID);
    }

    //This method is used in validating duplicate file locations. Duplicate file locations criteria is ambiguos as it
    // wasnt mentioned in the project pdf, so commented in the geneaology class. Please uncommment that to avoid
    // duplicate file locations
    public String isFileLocationPresent(String name) {
        return mySQLFetcher.isFileLocationPresent(name);
    }
}
