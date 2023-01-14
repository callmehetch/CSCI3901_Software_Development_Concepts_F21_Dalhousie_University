package assignment.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FamilyTree {
    //Declaring the attributes required for a family tree and reporting.
    private PersonIdentity personIdentity;
    private Map<Integer, PersonIdentity> MapofPartners;
    private List<PersonIdentity> listOfParents;
    private List<PersonIdentity> listOfChildren;

    //Familytree constructor to initialize parents, children, partners.
    public FamilyTree(PersonIdentity personIdentity) {
        setPersonIdentity(personIdentity);
        setListOfParents(new ArrayList<>());
        setListOfChildren(new ArrayList<>());
        setMapofPartners(new HashMap<>());
    }

    //---------------------Getters And Setters For Encapsulation Of the Data Attributes---------------------------------
    public PersonIdentity getPersonIdentity() {
        return personIdentity;
    }

    public void setPersonIdentity(PersonIdentity personIdentity) {
        this.personIdentity = personIdentity;
    }

    public List<PersonIdentity> getListOfParents() {
        return listOfParents;
    }

    public void setListOfParents(List<PersonIdentity> listOfParents) {
        this.listOfParents = listOfParents;
    }

    public List<PersonIdentity> getListOfChildren() {
        return listOfChildren;
    }

    public void setListOfChildren(List<PersonIdentity> listOfChildren) {
        this.listOfChildren = listOfChildren;
    }

    public Map<Integer, PersonIdentity> getMapofPartners() {
        return MapofPartners;
    }

    public void setMapofPartners(Map<Integer, PersonIdentity> mapofPartners) {
        this.MapofPartners = mapofPartners;
    }
    //---------------------Getters And Setters For Encapsulation Of the Data Attributes---------------------------------

}