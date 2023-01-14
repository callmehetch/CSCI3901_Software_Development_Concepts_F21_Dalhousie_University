package assignment.classes;

import java.util.ArrayList;
import java.util.List;

public class PersonIdentity {
    //Declaring the attributes required for a person and reporting.
    private int personID;
    private String personName;
    private String birthDate;
    private String birthLocation;
    private String deathDate;
    private String deathLocation;
    private String gender;
    private String occupationName;
    private List<String> ReferencesOfPerson;
    private List<String> notesOfPerson;

    //Personidentity constructor to initialize personID, personName, ReferencesOFPerson, NotesOfPerson.
    public PersonIdentity(int personID, String personName) {
        setPersonID(personID);
        setPersonName(personName);
        setReferencesOfPerson(new ArrayList<>());
        setNotesOfPerson(new ArrayList<>());
    }

    //---------------------Getters And Setters For Encapsulation Of the Data Attributes---------------------------------
    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setBirthLocation(String birthLocation) {
        this.birthLocation = birthLocation;
    }

    public void setDeathDate(String deathDate) {
        this.deathDate = deathDate;
    }

    public void setDeathLocation(String deathLocation) {
        this.deathLocation = deathLocation;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setOccupationName(String occupationName) {
        this.occupationName = occupationName;
    }

    public List<String> getReferencesOfPerson() {
        return ReferencesOfPerson;
    }

    public void setReferencesOfPerson(List<String> referencesOfPerson) {
        this.ReferencesOfPerson = referencesOfPerson;
    }

    public List<String> getNotesOfPerson() {
        return notesOfPerson;
    }

    public void setNotesOfPerson(List<String> notesOfPerson) {
        this.notesOfPerson = notesOfPerson;
    }

    //---------------------Getters And Setters For Encapsulation Of the Data Attributes---------------------------------
}