import assignment.classes.BiologicalRelation;
import assignment.classes.FamilyTree;
import assignment.classes.FileIdentifier;
import assignment.classes.PersonIdentity;
import assignment.database.MySQLQuery;
import assignment.populators.GenealogyPopulator;
import assignment.validators.Validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

public class Genealogy {
    //Declaration of the lists and maps and my SQl initializer and validator object initializer
    private final MySQLQuery mySQLQuery;
    private final Validator validator = new Validator();
    private final List<PersonIdentity> personIdentityList;
    private final List<FileIdentifier> fileIdentifierList;
    private final Map<Integer, FamilyTree> familyTreeMap;
    private final Map<Integer, FileIdentifier> fileIdentifierMap;
    private final String dateFormat = "yyyy-mm-dd";

    //Genealogy constructor to repopulate the previous session's data
    Genealogy() {
        GenealogyPopulator genealogyPopulator = new GenealogyPopulator();
        mySQLQuery = new MySQLQuery();
        //The following instances will repopulate the lists, objects and maps with the data fetched from the database
        this.personIdentityList = genealogyPopulator.getPersonIdentityList();
        this.fileIdentifierList = genealogyPopulator.getFileIdentifierList();
        this.familyTreeMap = genealogyPopulator.getFamilyTreeMap();
        this.fileIdentifierMap = genealogyPopulator.getFileIdentifierMap();
    }

    //method to add the person
    public PersonIdentity addPerson(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        //Add person to database
        mySQLQuery.InsertPersonByName(name);
        //fetch the personId and add it to the object
        int lastPersonId = mySQLQuery.getLastPersonId();
        System.out.println("Person being added: ");
        System.out.println(lastPersonId + " - " + name);
        PersonIdentity person = new PersonIdentity(lastPersonId, name);
        //finally add the personIdentity object to map and list
        familyTreeMap.put(person.getPersonID(), new FamilyTree(person));
        personIdentityList.add(person);
        return person;
    }

    //Method to add attributes of a person
    public Boolean recordAttributes(PersonIdentity person, Map<String, String> attributes) {
        if (person == null || validator.isPersonPresent(person) || attributes.isEmpty() || attributes.containsKey(null) || attributes.containsValue(null)) {
            return false;
        }
        //Iterate trhough the list of persons to find the person
        for (int i = 0, personIdentityListSize = personIdentityList.size(); i < personIdentityListSize; i++) {
            PersonIdentity newPerson = personIdentityList.get(i);
            if (person.getPersonID() != newPerson.getPersonID()) {
                continue;
            }
            //if the person is found add the attributes
            int personID = personIdentityList.get(i).getPersonID();
            for (Entry<String, String> newMap : attributes.entrySet()) {
                String keyAttributes = newMap.getKey();
                String attributeValues = newMap.getValue();
                //Get the key value pairs and add the attributes
                addAttributes(i, personID, keyAttributes, attributeValues);

            }
            return true;
        }
        return false;
    }

    //Method to add attributes from the recordAttributes method
    private void addAttributes(int i, int personID, String keyAttributes, String attributeValues) {
        //below cases are self-explanatory. sorry
        if ("birthDate".equals(keyAttributes)) {
            personIdentityList.get(i).setBirthDate(attributeValues);
            mySQLQuery.updateBirthDate(attributeValues, personID);
        } else if ("birthLocation".equals(keyAttributes)) {
            personIdentityList.get(i).setBirthLocation(attributeValues);
            mySQLQuery.updateBirthLocation(attributeValues, personID);
        } else if ("deathDate".equals(keyAttributes)) {
            personIdentityList.get(i).setDeathDate(attributeValues);
            mySQLQuery.updateDeathDate(attributeValues, personID);
        } else if ("deathLocation".equals(keyAttributes)) {
            personIdentityList.get(i).setDeathLocation(attributeValues);
            mySQLQuery.updateDeathLocation(attributeValues, personID);
        } else if ("gender".equals(keyAttributes)) {
            personIdentityList.get(i).setGender(attributeValues);
            mySQLQuery.updateGender(attributeValues, personID);
        } else if ("occupation".equals(keyAttributes)) {
            personIdentityList.get(i).setOccupationName(attributeValues);
            mySQLQuery.updateOccupation(attributeValues, personID);
        }
    }

    //Method to record references of a person
    public Boolean recordReference(PersonIdentity person, String reference) {
        //validation
        if (person == null || validator.isPersonPresent(person) || reference == null || reference.trim().isEmpty()) {
            return false;
        }
        //Iterate through the list of persons and find that person
        for (int i = 0, personIdentityListSize = personIdentityList.size(); i < personIdentityListSize; i++) {
            PersonIdentity newPerson = personIdentityList.get(i);
            if (person.getPersonID() == newPerson.getPersonID()) {
                //If person found, insert the references of that person in db and person Identity list
                int personID = personIdentityList.get(i).getPersonID();
                mySQLQuery.updateReferences(reference, personID);
                personIdentityList.get(i).getReferencesOfPerson().add(reference);
                return true;
            }
        }
        return false;
    }

    //Method to record notes of a person
    public Boolean recordNote(PersonIdentity person, String note) {
        if (person == null || validator.isPersonPresent(person) || note == null || note.trim().isEmpty()) {
            return false;
        }
        //Iterate through the list of persons and find that person
        for (int i = 0, personIdentityListSize = personIdentityList.size(); i < personIdentityListSize; i++) {
            PersonIdentity newPerson = personIdentityList.get(i);
            if (person.getPersonID() == newPerson.getPersonID()) {
                //If person found, insert the notes of that person in db and person Identity list
                int personID = personIdentityList.get(i).getPersonID();
                mySQLQuery.updateNotes(note, personID);
                personIdentityList.get(i).getNotesOfPerson().add(note);
                return true;
            }
        }
        return false;
    }

    //method to record the child for a parent
    public Boolean recordChild(PersonIdentity parent, PersonIdentity child) {
        if (parent == null || child == null || validator.isPersonPresent(parent) || validator.isPersonPresent(child)) {
            return false;
        }
        //Check if both the parent and child exist in the familytree
        if (!familyTreeMap.containsKey(parent.getPersonID()) || !familyTreeMap.containsKey(child.getPersonID())) {
            return false;
        }
        //If exists, add child to the parent and parent to the child
        familyTreeMap.get(parent.getPersonID()).getListOfChildren().add(child);
        familyTreeMap.get(child.getPersonID()).getListOfParents().add(parent);
        //Add child relation to the database
        mySQLQuery.addChild(parent.getPersonID(), child.getPersonID());
        return true;
    }

    //Method to record the partnership of two persons
    public Boolean recordPartnering(PersonIdentity partner1, PersonIdentity partner2) {
        //Validation
        if (partner1 == null || partner2 == null || validator.isPersonPresent(partner1) || validator.isPersonPresent(partner2)) {
            return false;
        }
        //Check if both the partners exist in the familytree
        if (!familyTreeMap.containsKey(partner1.getPersonID()) || !familyTreeMap.containsKey(partner2.getPersonID())) {
            return false;
        }
        //If exists, relate both the partners with key 1
        familyTreeMap.get(partner1.getPersonID()).getMapofPartners().put(1, partner2);
        familyTreeMap.get(partner2.getPersonID()).getMapofPartners().put(1, partner1);
        //Add relation to the database
        mySQLQuery.addPartnering(partner1.getPersonID(), partner2.getPersonID());
        return true;
    }

    public Boolean recordDissolution(PersonIdentity partner1, PersonIdentity partner2) {
        //Validation
        if (partner1 == null || partner2 == null || validator.isPersonPresent(partner1) || validator.isPersonPresent(partner2)) {
            return false;
        }
        //Check if both the partners exist in the familytree
        if (!familyTreeMap.containsKey(partner1.getPersonID()) || !familyTreeMap.containsKey(partner2.getPersonID())) {
            return false;
        }
        //If exists, relate both the partners with key 0
        familyTreeMap.get(partner1.getPersonID()).getMapofPartners().put(0, partner2);
        familyTreeMap.get(partner2.getPersonID()).getMapofPartners().put(0, partner1);
        //Add relation to the database
        mySQLQuery.addDissolution(partner1.getPersonID(), partner2.getPersonID());
        return true;
    }

    public FileIdentifier addMediaFile(String fileLocation) {
        //Validation
        if (fileLocation == null || fileLocation.trim().isEmpty()) {
            return null;
        }
        //The commented part is to avoid duplicate file locations, but I am commenting it
        //out as it is ambiguous and I heard multiple opinions on this. The project pdf also
        //didn't mention that file location should be unique. so if it is unique, please uncomment the below part. Thanks
//        if(validator.isFileLocationPresent(fileLocation.trim()) != null){
//            System.out.println("File location is already present");
//            return null;
//        }
        FileIdentifier fileIdentifier = new FileIdentifier(fileLocation);
        //Add person to database
        mySQLQuery.insertMedia(fileLocation);
        //fetch the mediId
        int mediaID = mySQLQuery.getLastMediaId();
        fileIdentifier.setMediaID(mediaID);
        //Set the media Id and add the fileIdentifier to map and list
        fileIdentifierMap.put(mediaID, fileIdentifier);
        fileIdentifierList.add(fileIdentifier);
        return fileIdentifier;
    }

    //method to record media attributes for a given file identifier
    public Boolean recordMediaAttributes(FileIdentifier fileIdentifier, Map<String, String> attributes) {
        //Validation
        if (fileIdentifier == null || validator.isMediaPresent(fileIdentifier) || attributes.isEmpty() || attributes.containsKey(null) || attributes.containsValue(null)) {
            return false;
        }
        //Iterate trhough the list of media files to find the person
        for (int i = 0, fileIdentifierListSize = fileIdentifierList.size(); i < fileIdentifierListSize; i++) {
            FileIdentifier newMedia = fileIdentifierList.get(i);
            if (fileIdentifier.getMediaID() == newMedia.getMediaID()) {
                int id = fileIdentifier.getMediaID();
                //Call the add media attributes method to add the media attributes
                addMediaAttributes(attributes, i, id);
                return true;
            }
        }
        return false;
    }

    //method to help record media attributes
    private void addMediaAttributes(Map<String, String> attributes, int i, int id) {
        for (Entry<String, String> newMap : attributes.entrySet()) {
            //Iterate through the set passed and add each attribute the media file
            String keyAttributes = newMap.getKey();
            String attributeValues = newMap.getValue();
            //If date is partial adding 01-01 to it. Referenced in th assumptions in documentation
            if ("date".equals(keyAttributes)) {
                if (attributeValues.trim().length() == 4) {
                    attributeValues += "-01-01";
                }
                if (attributeValues.trim().length() == 7) {
                    attributeValues += "-01";
                }
                //Set the attributes and update in the database
                fileIdentifierList.get(i).setDateTaken(attributeValues);
                mySQLQuery.updateMediaDate(attributeValues, id);
            } else if ("location".equals(keyAttributes)) {
                //add location attribute if key is location
                fileIdentifierList.get(i).setLocationOfMedia(attributeValues);
                mySQLQuery.updateMediaLocation(attributeValues, id);
            }
        }
    }

    //Method to add tags to the media file
    public Boolean tagMedia(FileIdentifier fileIdentifier, String tag) {
        //Validation
        if (fileIdentifier == null || validator.isMediaPresent(fileIdentifier) || tag == null || tag.trim().isEmpty()) {
            return false;
        }
        //iterate through the media files list and find the one
        for (int i = 0, fileIdentifierListSize = fileIdentifierList.size(); i < fileIdentifierListSize; i++) {
            FileIdentifier newMedia = fileIdentifierList.get(i);
            if (fileIdentifier.getMediaID() == newMedia.getMediaID()) {
                //If found, add tags to the file and update in the database
                fileIdentifierList.get(i).getTagsOfMedia().add(tag);
                mySQLQuery.updateMediaTag(tag, fileIdentifierList.get(i).getMediaID());
                return true;
            }
        }
        return false;
    }

    //method to add list of people appearing in the media file
    public Boolean peopleInMedia(FileIdentifier fileIdentifier, List<PersonIdentity> people) {
        //Validation
        if (fileIdentifier == null || validator.isMediaPresent(fileIdentifier) || people == null || people.isEmpty()) {
            return false;
        }
        //iterate through the file list and find the one
        for (int i = 0, fileIdentifierListSize = fileIdentifierList.size(); i < fileIdentifierListSize; i++) {
            FileIdentifier newMedia = fileIdentifierList.get(i);
            if (fileIdentifier.getMediaID() == newMedia.getMediaID()) {
                //Iterate through the people's list and find the poeple in personIdentity list
                for (int j = 0, peopleSize = people.size(); j < peopleSize; j++) {
                    PersonIdentity newPeople = people.get(j);
                    if (newPeople != null && validator.isPersonIDPresent(newPeople.getPersonID())) {
                        //If the person is found and he is present in the database, add the people to media database and fileidentifier
                        fileIdentifierList.get(i).getPeopleInMedia().add(newPeople);
                        mySQLQuery.updatePeopleinMedia(fileIdentifier.getMediaID(), newPeople.getPersonID());

                    }
                }
                return true;
            }
        }
        return false;
    }

    //Method to find the person with given name
    public PersonIdentity findPerson(String name) {
        //Validation
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        //Iterate through each item in the family tree and check for the personIdentity with the name provided
        Iterator<Entry<Integer, FamilyTree>> iterator = familyTreeMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<Integer, FamilyTree> entry = iterator.next();
            if (entry.getValue().getPersonIdentity().getPersonName().equals(name)) {
                return entry.getValue().getPersonIdentity();
            }
        }
        return null;
    }

    //Method to locate the media file by name
    public FileIdentifier findMediaFile(String name) {
        //Validation
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        //Iterate through the fileIdentifier map and find the media file with the same name
        for (Map.Entry<Integer, FileIdentifier> fileIdentifierEntry : fileIdentifierMap.entrySet()) {
            if (fileIdentifierEntry.getValue().getMediaFileLocation().equals(name)) {
                return fileIdentifierEntry.getValue();
            }
        }
        return null;
    }

    //method to find the name by personidentity object
    public String findName(PersonIdentity id) {
        //Validation
        if (id == null || validator.isPersonPresent(id)) {
            return null;
        }
        //Just return the personname related to that id
        return id.getPersonName();
    }

    //method to find the file location related to the fileidentifier
    public String findMediaFile(FileIdentifier fileId) {
        //Validation
        if (fileId == null || validator.isMediaPresent(fileId)) {
            return null;
        }
        //just return the file location
        return fileId.getMediaFileLocation();
    }

    //Method to find the common ancestor of two persons given
    public PersonIdentity CommonAncestor(PersonIdentity firstPerson, PersonIdentity secondPerson) {
        //Validation
        if (firstPerson == null || secondPerson == null) {
            return null;
        }
        PersonIdentity id;
        //Iterate through the list of first person and secondperson and check of children
        List<Integer> left = new ArrayList<>(List.of(firstPerson.getPersonID()));
        List<Integer> right = new ArrayList<>(List.of(secondPerson.getPersonID()));
        checkParentChilds(firstPerson, secondPerson, left, right);
        //left list traversal
        for (int i = 0, leftSize = left.size(); i < leftSize; i++) {
            Integer integer = left.get(i);
            //right list traversal
            for (int j = 0, rightSize = right.size(); j < rightSize; j++) {
                Integer value = right.get(j);
                //if the ancestor matches , get the personidentity
                if (Objects.equals(integer, value)) {
                    id = familyTreeMap.get(integer).getPersonIdentity();
                    return id;
                }
            }
        }
        return null;
    }

    //Method to check parent childs to help common ancestor
    private void checkParentChilds(PersonIdentity firstPerson, PersonIdentity secondPerson, List<Integer> left, List<Integer> right) {
        //Validate for nulls
        if (!(familyTreeMap.get(firstPerson.getPersonID()) == null || familyTreeMap.get(secondPerson.getPersonID()) == null)) {
            //If both the parents are present
            if (!familyTreeMap.get(firstPerson.getPersonID()).getListOfParents().isEmpty()) {
                //run and loop and get the immediate parent and compare
                do {
                    firstPerson = familyTreeMap.get(firstPerson.getPersonID()).getListOfParents().get(0);
                    left.add(firstPerson.getPersonID());
                } while (!familyTreeMap.get(firstPerson.getPersonID()).getListOfParents().isEmpty());
            }
            //If the parents are present
            if (!familyTreeMap.get(secondPerson.getPersonID()).getListOfParents().isEmpty()) {
                do {
                    //run loop and get the immediate parent and compare
                    secondPerson = familyTreeMap.get(secondPerson.getPersonID()).getListOfParents().get(0);
                    right.add(secondPerson.getPersonID());
                } while (!familyTreeMap.get(secondPerson.getPersonID()).getListOfParents().isEmpty());
            }
        }
    }

    //method to find the level of node to find the difference of levels in find relation
    private int levelOfNOde(int personID) {
        int level = 0;
        //Iterate throught the family tree map
        for (Iterator<Entry<Integer, FamilyTree>> iterator = familyTreeMap.entrySet().iterator(); iterator.hasNext(); ) {
            Entry<Integer, FamilyTree> newMap = iterator.next();
            FamilyTree familyTree = newMap.getValue();
            //If the person identity matches
            if (familyTree.getPersonIdentity().getPersonID() == personID) {
                //if the the person has few more parents level ++ or return the current level
                if (newMap.getValue().getListOfParents().size() == 0) {
                    return level;
                } else {
                    while (familyTree.getListOfParents().size() != 0) {
                        //If the person has parents again loop after level++
                        familyTree = familyTreeMap.get(familyTree.getListOfParents().get(0).getPersonID());
                        level++;
                    }
                }
            }
        }
        return level;
    }

    //Method to find the relation between two persons
    public BiologicalRelation findRelation(PersonIdentity person1, PersonIdentity person2) {
        //Validation
        final String relationAD = "Ancestor/Descendent Relation";
        final String sibling = "Sibling";
        final String cousins = "cousins";
        if (person1 == null || person2 == null || validator.isPersonPresent(person1) || validator.isPersonPresent(person2)) {
            return null;
        }
        //Create a new biological relation object
        BiologicalRelation biologicalRelation = new BiologicalRelation(person1, person2);
        //Find the common ancestor if any, otherwise null
        PersonIdentity commonAncestorID = CommonAncestor(person1, person2);
        if (commonAncestorID == null) {
            return null;
        }
        //Find nA and nB, Formulae were mentioned in the documentation
        int nA = Math.abs(levelOfNOde(commonAncestorID.getPersonID()) - levelOfNOde(person1.getPersonID()));
        int nB = Math.abs(levelOfNOde(commonAncestorID.getPersonID()) - levelOfNOde(person2.getPersonID()));
        //Find degreeofCousinship and degree of removal, Formulae were mentioned in the documentation
        int degreeOfCousinship = Math.min(nA, nB) - 1;
        int degreeOfRemoval = Math.abs(nA - nB);
        //Establish relationships accoring to the relation mentioned in the documentation
        if (nA == 0 || nB == 0) {
            biologicalRelation.setRelation(relationAD);
        } else if (Math.min(nA, nB) == 1 && degreeOfRemoval > 0) {
            biologicalRelation.setRelation(sibling + " " + degreeOfRemoval + " Removed");
        } else if (Math.min(nA, nB) == 1 && degreeOfRemoval < 0) {
            biologicalRelation.setRelation(sibling);
        } else {
            biologicalRelation.setRelation(degreeOfCousinship + " " + cousins + " " + degreeOfRemoval + " removed ");
        }
        //set cousinship, degree of removal, and relation to the object
        biologicalRelation.setCommonAncestor(commonAncestorID.getPersonName());
        biologicalRelation.setDegreeOFCousinShip(degreeOfCousinship);
        biologicalRelation.setLevelOfRemoval(degreeOfRemoval);
        return biologicalRelation;
    }

    //Method to find the ancestors
    public Set<PersonIdentity> ancestores(PersonIdentity person, Integer generations) {
        //Validation
        if (person == null || validator.isPersonPresent(person)) {
            return Collections.emptySet();
        }
        //iterate through the family tree and get all the persons who are being generations away by generations-- and add to list
        Set<PersonIdentity> personIdentitySet = new HashSet<>();
        if (!familyTreeMap.get(person.getPersonID()).getListOfParents().isEmpty() && generations != 0) {
            do {
                person = familyTreeMap.get(person.getPersonID()).getListOfParents().get(0);
                personIdentitySet.add(person);
                generations--;
            } while (!familyTreeMap.get(person.getPersonID()).getListOfParents().isEmpty() && generations != 0);
        }
        return personIdentitySet;
    }

    //Method to find the descendants
    public Set<PersonIdentity> descendents(PersonIdentity person, Integer generations) {
        //Validation
        if (person == null || validator.isPersonPresent(person)) {
            return Collections.emptySet();
        }
        //iterate through the family tree and get all the persons who are being generations away by generations-- and add to list
        Set<PersonIdentity> personIdentitySet = new HashSet<>();
        if (!familyTreeMap.get(person.getPersonID()).getListOfChildren().isEmpty()) {
            if (generations != 0) {
                do {
                    for (PersonIdentity child : familyTreeMap.get(person.getPersonID()).getListOfChildren()) {
                        personIdentitySet.add(child);
                        personIdentitySet.addAll(descendents(child, generations - 1));
                    }
                    generations--;
                } while (!familyTreeMap.get(person.getPersonID()).getListOfChildren().isEmpty() && generations != 0);
            }
        }
        return personIdentitySet;
    }

    //Method to list all the note and referneces of a given person
    public List<String> notesAndReferences(PersonIdentity person) {
        //Validation
        if (person == null || validator.isPersonPresent(person)) {
            return Collections.emptyList();
        }
        //Iterate through the familytreemap and list all the notes and referneces linked to each person and add them to it
        List<String> listNotesReferences = new ArrayList<>();
        Iterator<Entry<Integer, FamilyTree>> iterator = familyTreeMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<Integer, FamilyTree> entry = iterator.next();
            if (Objects.equals(entry.getValue().getPersonIdentity().getPersonID(), person.getPersonID())) {
                //If the person Id is matched. add the notes and references
                listNotesReferences.addAll(entry.getValue().getPersonIdentity().getNotesOfPerson());
                listNotesReferences.addAll(entry.getValue().getPersonIdentity().getReferencesOfPerson());
            }
        }
        return listNotesReferences;
    }

    //Method to find the fileidentifiers for a give tag and within the date range
    public Set<FileIdentifier> findMediaByTag(String tag, String startDate, String endDate) {
        //Validation
        if (tag == null || tag.trim().isEmpty()) {
            return null;
        }
        //Check for the dates null scenario
        Set<FileIdentifier> setFindMediaByTag = new HashSet<>();
        try {
            if (startDate == null || endDate == null) {
                //If dates are null call the nulldates method to handle the scenario
                setFindMediaByTag = nullDatesMediaTags(tag, startDate, endDate, setFindMediaByTag);
            } else {
                //Validation
                if (startDate.trim().isEmpty() || endDate.trim().isEmpty()) {
                    return Collections.emptySet();
                }
                //Parse the given dates
                Date start = new SimpleDateFormat(dateFormat).parse(startDate);
                Date end = new SimpleDateFormat(dateFormat).parse(endDate);
                //Pass the dates and file Identifier ti tagmediahelper
                Iterator<Entry<Integer, FileIdentifier>> iterator = fileIdentifierMap.entrySet().iterator();
                tagMediaHelper(tag, setFindMediaByTag, start, end, iterator);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return setFindMediaByTag;
    }

    //Method to handle null dates from the media tags method
    private Set<FileIdentifier> nullDatesMediaTags(String tag, String startDate, String endDate, Set<FileIdentifier> setFindMediaByTag) throws ParseException {
        if (startDate == null && endDate == null) {
            //If both dates are null, itrate through the file identifier map and get all the tags related to all the media files
            for (Entry<Integer, FileIdentifier> mediaEntry : fileIdentifierMap.entrySet()) {
                for (String tag1 : mediaEntry.getValue().getTagsOfMedia()) {
                    if (tag1 != null && tag1.equals(tag)) {
                        setFindMediaByTag.add(mediaEntry.getValue());
                    }
                }
            }
        } else {
            //else iterate through the fileidentifiermap
            for (Entry<Integer, FileIdentifier> fileIdentifierEntry : fileIdentifierMap.entrySet()) {
                for (String tag1 : fileIdentifierEntry.getValue().getTagsOfMedia()) {
                    if (tag1 != null && tag1.equals(tag)) {
                        if (fileIdentifierEntry.getValue().getDateTaken() != null) {
                            Date dateNew = new SimpleDateFormat(dateFormat).parse(fileIdentifierEntry.getValue().getDateTaken());
                            if (endDate == null) {
                                //If only start date is null, filter accordingly
                                Date start = new SimpleDateFormat(dateFormat).parse(startDate);
                                if (dateNew.after(start)) {
                                    setFindMediaByTag.add(fileIdentifierEntry.getValue());
                                }
                            } else {
                                //If only end date is null, filter accordingly
                                Date end = new SimpleDateFormat(dateFormat).parse(endDate);
                                if (dateNew.before(end)) {
                                    setFindMediaByTag.add(fileIdentifierEntry.getValue());
                                }
                            }

                        }
                    }
                }
            }
        }
        return setFindMediaByTag;
    }

    //Method to help tagmedia mehtod
    private void tagMediaHelper(String tag, Set<FileIdentifier> setFindMediaByTag, Date start, Date end, Iterator<Entry<Integer, FileIdentifier>> iterator) throws ParseException {
        while (iterator.hasNext()) {
            //iterate through all the media files and find if the media has any tags
            Entry<Integer, FileIdentifier> integerFileIdentifier = iterator.next();
            List<String> tagsOfMedia = integerFileIdentifier.getValue().getTagsOfMedia();
            int i = 0;
            int tagsOfMediaSize = tagsOfMedia.size();
            while (i < tagsOfMediaSize) {
                String s = tagsOfMedia.get(i);
                //if it is not equal to the given tag, pass
                if (!s.equals(tag)) {
                    i++;
                    continue;
                }
                //If the date linked to media is null, pass
                if (integerFileIdentifier.getValue().getDateTaken() == null) {
                    i++;
                    continue;
                }
                //If the file is in the given date range, add it to the setmedia tag to be returned
                Date dateNew = new SimpleDateFormat(dateFormat).parse(integerFileIdentifier.getValue().getDateTaken());
                if (!dateNew.after(start) || !dateNew.before(end)) {
                    i++;
                    continue;
                }
                setFindMediaByTag.add(integerFileIdentifier.getValue());
                i++;
            }
        }
    }

    //Method to find the set of fileIdentifiers in the give date range and location
    public Set<FileIdentifier> findMediaByLocation(String location, String startDate, String endDate) {
        //Validation
        if (location == null || location.trim().isEmpty()) {
            return null;
        }
        Set<FileIdentifier> setFindMediaByLocation = new HashSet<>();
        try {
            //Handling the dates null case with help of nulldates media method
            if (startDate == null || endDate == null) {
                setFindMediaByLocation = nullDatesMediaLocation(location, startDate, endDate, setFindMediaByLocation);
            } else {
                if (startDate.trim().isEmpty() || endDate.trim().isEmpty()) {
                    return null;
                }
                //parse the dates
                Date start = new SimpleDateFormat(dateFormat).parse(startDate);
                Date end = new SimpleDateFormat(dateFormat).parse(endDate);
                //iterate through the file identifier map and check if valid location is present
                Iterator<Entry<Integer, FileIdentifier>> iterator = fileIdentifierMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Entry<Integer, FileIdentifier> next = iterator.next();
                    //Check for valid location and valid dates
                    if (next.getValue().getLocationOfMedia() == null) continue;
                    if (next.getValue().getDateTaken() == null) continue;
                    //if location is equal to the given location add the location to set location within the given date ranges
                    if (next.getValue().getLocationOfMedia().equals(location)) {
                        Date dateNew = new SimpleDateFormat(dateFormat).parse(next.getValue().getDateTaken());
                        if (dateNew.after(start) && dateNew.before(end)) {
                            setFindMediaByLocation.add(next.getValue());
                        }
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return setFindMediaByLocation;
    }

    //This method is created to help media location method with null case scenario
    private Set<FileIdentifier> nullDatesMediaLocation(String location, String startDate, String endDate, Set<FileIdentifier> setFindMediaByLocation) throws ParseException {
        //If both dates are null
        if (startDate == null && endDate == null) {
            //Iterate through the fileIdentifier map
            for (Entry<Integer, FileIdentifier> mediaEntry : fileIdentifierMap.entrySet()) {
                String locationOfFile = mediaEntry.getValue().getLocationOfMedia();
                //If valid location is found, add it to the set to be returned
                if (locationOfFile != null && locationOfFile.equals(location)) {
                    setFindMediaByLocation.add(mediaEntry.getValue());
                }
            }
        } else {
            //else, iterate through the fileidentifier map and check which date is null
            for (Entry<Integer, FileIdentifier> fileIdentifierEntry : fileIdentifierMap.entrySet()) {
                String locationOfFile = fileIdentifierEntry.getValue().getLocationOfMedia();
                if (locationOfFile != null && locationOfFile.equals(location)) {
                    if (fileIdentifierEntry.getValue().getLocationOfMedia().equals(location)) {
                        if (fileIdentifierEntry.getValue().getDateTaken() != null) {
                            Date dateNew = new SimpleDateFormat(dateFormat).parse(fileIdentifierEntry.getValue().getDateTaken());
                            if (endDate == null) {
                                //If end date is null, filter based on the start date only
                                Date start = new SimpleDateFormat(dateFormat).parse(startDate);
                                if (dateNew.after(start)) {
                                    setFindMediaByLocation.add(fileIdentifierEntry.getValue());
                                }
                            } else {
                                //If start date is null, filter based on the end date only
                                Date end = new SimpleDateFormat(dateFormat).parse(endDate);
                                if (dateNew.before(end)) {
                                    setFindMediaByLocation.add(fileIdentifierEntry.getValue());
                                }
                            }

                        }
                    }
                }
            }
        }
        return setFindMediaByLocation;
    }

    //method to find fileidentifiers contaning the set of people
    public List<FileIdentifier> findIndividualsMedia(Set<PersonIdentity> people, String startDate, String endDate) {
        //Validation
        if (people == null || people.isEmpty()) {
            return null;
        }
        List<FileIdentifier> listIndividualsMedia = new ArrayList<>();
        try {
            //Iterate trough the fileidentifier map and check for the person related to the media file
            for (Iterator<Entry<Integer, FileIdentifier>> iterator = fileIdentifierMap.entrySet().iterator(); iterator.hasNext(); ) {
                Entry<Integer, FileIdentifier> newMap = iterator.next();
                List<PersonIdentity> peopleInMedia = newMap.getValue().getPeopleInMedia();
                int i = 0;
                int peopleInMediaSize = peopleInMedia.size();
                while (i < peopleInMediaSize) {
                    PersonIdentity newPerson = peopleInMedia.get(i);
                    //call individual media helper for each item in the list of people related to the media
                    if (individualsInMediaHelper(people, startDate, endDate, listIndividualsMedia, newMap, newPerson))
                        return null;
                    i++;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return listIndividualsMedia;
    }

    //Method to help Individual in media method
    private boolean individualsInMediaHelper(Set<PersonIdentity> people, String startDate, String endDate, List<FileIdentifier> listIndividualsMedia, Entry<Integer, FileIdentifier> newMap, PersonIdentity newPerson) throws ParseException {
        //Iterate through the people set passed
        for (PersonIdentity temp :
                people) {
            if (temp == null) {
                continue;
            }
            //If the person related to media matches with the item in set of people
            if (temp.getPersonID() == newPerson.getPersonID()) {
                if (newMap.getValue().getDateTaken() != null) {
                    Date dateNew = new SimpleDateFormat(dateFormat).parse(newMap.getValue().getDateTaken());
                    //Check if both the given dates are null. Then add all the media into the list
                    if (startDate == null && endDate == null) {
                        listIndividualsMedia.add(newMap.getValue());
                    } else if (startDate == null) {
                        //if only start date is null, add the media till end date
                        Date end = new SimpleDateFormat(dateFormat).parse(endDate);
                        if (dateNew.before(end)) {
                            listIndividualsMedia.add(newMap.getValue());
                        }
                    } else if (endDate == null) {
                        //If only end date is null, add all the media file from start date
                        Date start = new SimpleDateFormat(dateFormat).parse(startDate);
                        if (dateNew.after(start)) {
                            listIndividualsMedia.add(newMap.getValue());
                        }
                    } else {
                        //Else add the media files within the given date range
                        if (startDate.trim().isEmpty() || endDate.trim().isEmpty()) {
                            return true;
                        }
                        Date start = new SimpleDateFormat(dateFormat).parse(startDate);
                        Date end = new SimpleDateFormat(dateFormat).parse(endDate);
                        if (dateNew.after(start) && dateNew.before(end)) {
                            listIndividualsMedia.add(newMap.getValue());
                        }
                    }
                }
            }
        }
        return false;
    }

    //Method to find the media related to the immediate child of the person given
    public List<FileIdentifier> findBiologicalFamilyMedia(PersonIdentity person) {
        //Validation
        if (person == null || validator.isPersonPresent(person)) {
            return Collections.emptyList();
        }
        List<FileIdentifier> listBiologicalFamily = new ArrayList<>();
        //get the immediate child of the person
        PersonIdentity levelzero = familyTreeMap.get(person.getPersonID()).getListOfChildren().get(0);
        //iterate though all the media files and list of people related to each file
        for (Iterator<Entry<Integer, FileIdentifier>> iterator = fileIdentifierMap.entrySet().iterator(); iterator.hasNext(); ) {
            Entry<Integer, FileIdentifier> entry = iterator.next();
            List<PersonIdentity> peopleInMedia = entry.getValue().getPeopleInMedia();
            int i = 0;
            while (i < peopleInMedia.size()) {
                PersonIdentity person1 = peopleInMedia.get(i);
                //If both the persons are equal add the media file to the list to be returned
                if (Objects.equals(person1.getPersonID(), levelzero.getPersonID())) {
                    listBiologicalFamily.add(entry.getValue());
                }
                i++;
            }
        }

        listBiologicalFamily.sort(Comparator.comparing(FileIdentifier::getDateTaken));
        return listBiologicalFamily;
    }

}