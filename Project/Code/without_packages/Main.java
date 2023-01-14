import assignment.classes.BiologicalRelation;
import assignment.classes.FileIdentifier;
import assignment.classes.PersonIdentity;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Genealogy genealogy = new Genealogy();

        PersonIdentity a = genealogy.addPerson("A");
        PersonIdentity b = genealogy.addPerson("B");
        PersonIdentity c = genealogy.addPerson("C");
        PersonIdentity d = genealogy.addPerson("D");
        PersonIdentity e = genealogy.addPerson("E");
        PersonIdentity f = genealogy.addPerson("F");
        PersonIdentity g = genealogy.addPerson("G");
        PersonIdentity h = genealogy.addPerson("H");
        PersonIdentity i = genealogy.addPerson("I");
        PersonIdentity j = genealogy.addPerson("J");
        PersonIdentity k = genealogy.addPerson("K");
        PersonIdentity l = genealogy.addPerson("L");
        PersonIdentity m = genealogy.addPerson("M");


        genealogy.recordAttributes(a, new HashMap<>() {{
            put("birthDate", "1998-10-19");
            put("birthLocation", "Halifax");
            put("deathDate", "2021-01-29");
            put("deathLocation", "Montreal");
            put("gender", "female");
            put("occupation", "student");
        }});

        genealogy.recordAttributes(b, new HashMap<>() {{
            put("birthDate", "1891-09-19");
            put("birthLocation", "Ottawa");
            put("deathDate", "1928-19-01");
            put("deathLocation", "Toronto");
            put("gender", "female");
            put("occupation", "engineer");
        }});

        genealogy.recordAttributes(c, new HashMap<>() {{
            put("birthDate", "1992-10-01");
            put("birthLocation", "Kempe");
            put("deathDate", "2032-10-28");
            put("deathLocation", "Tokyo");
            put("gender", "male");
            put("occupation", "doctor");
        }});

        genealogy.recordReference(a, "Ref1");
        genealogy.recordReference(b, "Ref2");
        genealogy.recordReference(c, "Ref3");


        genealogy.recordNote(a, "Note1");
        genealogy.recordNote(b, "Note2");
        genealogy.recordNote(c, "Note3");


        genealogy.recordChild(j, d);
        genealogy.recordChild(d, a);
        genealogy.recordChild(d, b);
        genealogy.recordChild(d, c);
        genealogy.recordChild(j, i);
        genealogy.recordChild(m, i);
        genealogy.recordChild(m, l);
        genealogy.recordChild(i, g);
        genealogy.recordChild(i, h);
        genealogy.recordChild(l, k);
        genealogy.recordChild(g, e);
        genealogy.recordChild(g, f);

        genealogy.recordPartnering(j, m);
        genealogy.recordDissolution(j, m);


        FileIdentifier fileIdentifier = genealogy.addMediaFile("/CSCI3901/1.jpg");
        genealogy.tagMedia(fileIdentifier, "computer");
        genealogy.tagMedia(fileIdentifier, "mac");
        genealogy.recordMediaAttributes(fileIdentifier, new HashMap<>() {{
            put("date", "2011");
            put("location", "Russia");
        }});

        FileIdentifier fileIdentifier1 = genealogy.addMediaFile("/CSCI3901/2.jpg");
        genealogy.tagMedia(fileIdentifier1, "computer");
        genealogy.tagMedia(fileIdentifier1, "dell");
        genealogy.tagMedia(fileIdentifier1, "hp");
        genealogy.recordMediaAttributes(fileIdentifier1, new HashMap<>() {{
            put("date", "2014-10");
            put("location", "Montreal");
        }});

        FileIdentifier fileIdentifier2 = genealogy.addMediaFile("CSCI3901/3.jpg");
        genealogy.tagMedia(fileIdentifier2, "pi");
        genealogy.tagMedia(fileIdentifier2, "hp");
        genealogy.recordMediaAttributes(fileIdentifier2, new HashMap<>() {{
            put("date", "2018-11-27");
            put("location", "Vegas");
        }});

        FileIdentifier fileIdentifier4 = genealogy.addMediaFile("/CSCI3901/2.jpg");

        List<PersonIdentity> people = new ArrayList<>();
        people.add(a);
        people.add(b);
        people.add(c);
        people.add(null);

        genealogy.peopleInMedia(fileIdentifier, people);

//
//        PersonIdentity a = new PersonIdentity(1, "A");
//        PersonIdentity b = new PersonIdentity(2, "B");
//        PersonIdentity c = new PersonIdentity(3, "C");
//        PersonIdentity d = new PersonIdentity(4, "D");
//        PersonIdentity e = new PersonIdentity(5, "E");
//        PersonIdentity j = new PersonIdentity(10, "J");
//        PersonIdentity f = new PersonIdentity(6, "F");
//        PersonIdentity h = new PersonIdentity(8, "H");
//        PersonIdentity g = new PersonIdentity(2, "G");
//
//        FileIdentifier fileIdentifier2 = new FileIdentifier("/CSCI3901/2.jpg");
//        fileIdentifier2.setMediaID(2);

        System.out.println("----------------------------------------------------------");
        PersonIdentity p = genealogy.findPerson("G");
        System.out.println("r1 - Person ID: " + p.getPersonID());
        System.out.println("----------------------------------------------------------");
        System.out.println("r2 - file location: " + genealogy.findMediaFile("/CSCI3901/1.jpg"));
        System.out.println("----------------------------------------------------------");
        System.out.println("r3 - Person Name: " + genealogy.findName(a));
        System.out.println("----------------------------------------------------------");

        System.out.println("r4 - file name: " + genealogy.findMediaFile(fileIdentifier2));

        System.out.println("--------------------r5----------------------------------");
        ;
        BiologicalRelation biologicalRelation = genealogy.findRelation(a, b);
        System.out.println("Common Ancestor: " + biologicalRelation.getCommonAncestor());
        System.out.println("Cousinship: " + biologicalRelation.getDegreeOFCousinShip());
        System.out.println("Removal: " + biologicalRelation.getLevelOfRemoval());
        System.out.println("Relation: " + biologicalRelation.getRelation());
        System.out.println("--------------------r5----------------------------------");

        System.out.println("--------------------r5----------------------------------");
        BiologicalRelation biologicalRelation2 = genealogy.findRelation(a, e);
        System.out.println("Common Ancestor: " + biologicalRelation2.getCommonAncestor());
        System.out.println("Cousinship: " + biologicalRelation2.getDegreeOFCousinShip());
        System.out.println("Removal: " + biologicalRelation2.getLevelOfRemoval());
        System.out.println("Relation: " + biologicalRelation2.getRelation());
        System.out.println("--------------------r5----------------------------------");

        System.out.println("--------------------r5----------------------------------");
        BiologicalRelation biologicalRelation3 = genealogy.findRelation(e, j);
        System.out.println("Common Ancestor: " + biologicalRelation3.getCommonAncestor());
        System.out.println("Cousinship: " + biologicalRelation3.getDegreeOFCousinShip());
        System.out.println("Removal: " + biologicalRelation3.getLevelOfRemoval());
        System.out.println("Relation: " + biologicalRelation3.getRelation());
        System.out.println("--------------------r5----------------------------------");

        System.out.println("--------------------r5----------------------------------");
        BiologicalRelation biologicalRelation4 = genealogy.findRelation(f, h);
        System.out.println("Common Ancestor: " + biologicalRelation4.getCommonAncestor());
        System.out.println("Cousinship: " + biologicalRelation4.getDegreeOFCousinShip());
        System.out.println("Removal: " + biologicalRelation4.getLevelOfRemoval());
        System.out.println("Relation: " + biologicalRelation4.getRelation());
        System.out.println("--------------------r5----------------------------------");

//        System.out.println("-------------r5-------------------------------------------");
//        PersonIdentity identity5 = genealogy.CommonAncestor(c, k);
//        System.out.println("Common Ancestor: "+ genealogy.findName(identity5));
//        BiologicalRelation biologicalRelation5 = genealogy.findRelation(c, k);
//        System.out.println("Cousinship: "+biologicalRelation5.getCousinShip());
//        System.out.println("Removal: "+biologicalRelation5.getLevelOfRemoval());
//        System.out.println("Relation: "+biologicalRelation5.getRelation());
//        System.out.println("--------------------r5----------------------------------");

        System.out.println("r6: Descendents:");
        Set<PersonIdentity> check2 = genealogy.descendents(j, 1);
        for (PersonIdentity p1 : check2) {
            System.out.println(p1.getPersonName());
        }
        System.out.println("----------------------------------------------------------");
        System.out.println("r7: Ancestors:");
        Set<PersonIdentity> check = genealogy.ancestores(a, 1);
        for (PersonIdentity p1 : check) {
            System.out.println(p1.getPersonName());
        }
        System.out.println("----------------------------------------------------------");
        System.out.println("r8: Notes and References");
        List<String> notes = genealogy.notesAndReferences(b);
        for (String s : notes) {
            System.out.println(s);
        }
        System.out.println("----------------------------------------------------------");
        System.out.println("r9: Finding Media by Tag Between Start and End Date:");
        Set<FileIdentifier> mediaByTags = genealogy.findMediaByTag("computer", "2013-10-10", "2100-10-20");
        for (FileIdentifier f1 : mediaByTags) {
            System.out.println(f1.getMediaFileLocation());
        }

        System.out.println("--Both null dates--");
        System.out.println(genealogy.findMediaByTag("computer", null, null));

        System.out.println("--Start date null--");
        Set<FileIdentifier> mediaByTags3 = genealogy.findMediaByTag("computer", null, "2015-01-01");
        for (FileIdentifier f1 : mediaByTags3) {
            System.out.println(f1.getMediaFileLocation());
        }

        System.out.println("--end date null--");
        Set<FileIdentifier> mediaByTags2 = genealogy.findMediaByTag("computer", "2013-01-01", null);
        for (FileIdentifier f1 : mediaByTags2) {
            System.out.println(f1.getMediaFileLocation());
        }


        System.out.println("----------------------------------------------------------");
        System.out.println("r10 : Finding Media by Location Between Start and End Date\n");
        Set<FileIdentifier> mediaByLocation = genealogy.findMediaByLocation("Montreal", "2011-01-28", "2016-12-31");
        System.out.println(mediaByLocation);
        System.out.println("--both dates null--");
        Set<FileIdentifier> mediaByLocation1 = genealogy.findMediaByLocation("Montreal", null, null);
        for (FileIdentifier f1 : mediaByLocation1) {
            if (f1.getMediaFileLocation() == null) {
                continue;
            }
            System.out.println(f1.getMediaFileLocation());
        }
        System.out.println("--start date null--");
        Set<FileIdentifier> mediaByLocation2 = genealogy.findMediaByLocation("Montreal", null, "2020-10-19");
        for (FileIdentifier f1 : mediaByLocation2) {
            if (f1.getMediaFileLocation() == null) {
                continue;
            }
            System.out.println(f1.getMediaFileLocation());
        }
        System.out.println("--end date null--");
        Set<FileIdentifier> mediaByLocation3 = genealogy.findMediaByLocation("Montreal", "2000-01-01", null);
        for (FileIdentifier f1 : mediaByLocation3) {
            if (f1.getMediaFileLocation() == null) {
                continue;
            }
            System.out.println(f1.getMediaFileLocation());
        }

        //System.out.println(genealogy.findMediaByLocation(" ", " ", "2001-01-01"));
        System.out.println(genealogy.findMediaByLocation("Russia", null, null));
        System.out.println("----------------------------------------------------------");
        System.out.println("r11: Return the set of media files that include any of individuals within the date range");
        Set<PersonIdentity> peopleSet = new HashSet<>();
        peopleSet.add(a);
        peopleSet.add(c);
        peopleSet.add(null);
        List<FileIdentifier> IndividualsMedia = genealogy.findIndividualsMedia(peopleSet, "2001-01-12", "2100-08-23");
        for (FileIdentifier f1 : IndividualsMedia) {
            System.out.println(f1.getMediaFileLocation());
        }

        System.out.println("--Both null--");
        List<FileIdentifier> IndividualsMedia1 = genealogy.findIndividualsMedia(peopleSet, null, null);
        for (FileIdentifier f1 : IndividualsMedia1) {
            System.out.println(f1.getMediaFileLocation());
        }

        System.out.println("--start null--");
        List<FileIdentifier> IndividualsMedia2 = genealogy.findIndividualsMedia(peopleSet, null, "2020-04-08");
        for (FileIdentifier f1 : IndividualsMedia2) {
            System.out.println(f1.getMediaFileLocation());
        }

        System.out.println("--end null--");
        List<FileIdentifier> IndividualsMedia3 = genealogy.findIndividualsMedia(peopleSet, "2008-12-01", null);
        for (FileIdentifier f1 : IndividualsMedia3) {
            System.out.println(f1.getMediaFileLocation());
        }


        System.out.println("----------------------------------------------------------");
        System.out.println("r12 : Finding Media by Immediate child\n");
        List<FileIdentifier> biologicalFamilyMedia = genealogy.findBiologicalFamilyMedia(d);
        for (FileIdentifier f1 : biologicalFamilyMedia) {
            System.out.println(f1.getMediaFileLocation());
        }

    }
}