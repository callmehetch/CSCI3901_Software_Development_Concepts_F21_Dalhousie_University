package assignment;

import assignment.classes.FileIdentifier;
import assignment.classes.PersonIdentity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GenealogyTest {

    private static Genealogy genealogy;
    private FileIdentifier example = genealogy.addMediaFile("/CSCI3901/example.jpg");

    @BeforeAll
    public static void setup() {
        System.out.println("Creating geneology object");
        genealogy = new Genealogy();
    }

    @BeforeEach
    @DisplayName("Populate")
    public void populate() {
        PersonIdentity person1 = genealogy.addPerson("person1");
        PersonIdentity person2 = genealogy.addPerson("person2");
        PersonIdentity person3 = genealogy.addPerson("person3");
    }


    @Test
    @DisplayName("Testing add person")
    public void addPerson() {
        assertNull(genealogy.addPerson(null));
        assertNull(genealogy.addPerson(""));
        assertNull(genealogy.addPerson(" "));
        PersonIdentity a;
        assertSame(a = genealogy.addPerson("A"), a);
    }

    @Test
    @DisplayName("Testing person attributes")
    public void recordAttributes() {
        PersonIdentity a = genealogy.addPerson("A");
        PersonIdentity b = genealogy.addPerson("B");
        PersonIdentity c = genealogy.addPerson("C");
        assertFalse(genealogy.recordAttributes(null, new HashMap<>()));
        assertFalse(genealogy.recordAttributes(a, new HashMap<>()));
        assertFalse(genealogy.recordAttributes(a, new HashMap<>() {{
            put(null, "1996-01-29");
            put("birthLocation", "Halifax");
            put("deathDate", "2078-10-29");
            put("deathLocation", "Montreal");
            put("gender", "female");
            put("occupation", "student");
        }}));
        assertFalse(genealogy.recordAttributes(a, new HashMap<>() {{
            put("birthDate", null);
            put("birthLocation", "Halifax");
            put("deathDate", "2021-09");
            put("deathLocation", " ");
            put("gender", "female");
            put("occupation", "student");
        }}));
        assertTrue(genealogy.recordAttributes(b, new HashMap<>() {{
            put("birthDate", "1891-10-29");
            put("birthLocation", "Ottawa");
            put("deathDate", "1928-10");
            put("deathLocation", "Toronto");
            put("gender", "female");
            put("occupation", "engineer");
        }}));
        PersonIdentity xyz = new PersonIdentity(999999, "xyz");
        assertFalse(genealogy.recordAttributes(xyz, new HashMap<>() {{
            put("birthDate", "1781-10-21");
            put("birthLocation", "Ottawa");
            put("deathDate", "1862");
            put("deathLocation", "Toronto");
            put("gender", "female");
            put("occupation", "engineer");
        }}));
    }

    @Test
    @DisplayName("Testing person references")
    public void recordReference() {
        PersonIdentity a = genealogy.addPerson("A");
        PersonIdentity b = genealogy.addPerson("B");
        PersonIdentity c = genealogy.addPerson("C");
        assertFalse(genealogy.recordReference(null, "Ref1"));
        assertFalse(genealogy.recordReference(b, null));
        assertFalse(genealogy.recordReference(c, ""));
        assertFalse(genealogy.recordReference(a, " "));
        assertTrue(genealogy.recordReference(c, "Ref5"));
        PersonIdentity xyz = new PersonIdentity(999999, "xyz");
        assertFalse(genealogy.recordReference(xyz, "DUMMY"));
    }

    @Test
    @DisplayName("Testing person notes")
    public void recordNote() {
        PersonIdentity a = genealogy.addPerson("A");
        PersonIdentity b = genealogy.addPerson("B");
        PersonIdentity c = genealogy.addPerson("C");
        assertFalse(genealogy.recordNote(null, "NOTE1"));
        assertFalse(genealogy.recordNote(b, null));
        assertFalse(genealogy.recordNote(c, ""));
        assertFalse(genealogy.recordNote(a, " "));
        assertTrue(genealogy.recordNote(c, "NOTE1"));
        PersonIdentity xyz = new PersonIdentity(999999, "xyz");
        assertFalse(genealogy.recordNote(xyz, "DUMMY"));
    }

    @Test
    @DisplayName("Testing person child")
    public void recordChild() {
        PersonIdentity a = genealogy.addPerson("A");
        PersonIdentity b = genealogy.addPerson("B");
        PersonIdentity xyz = new PersonIdentity(999999, "xyz");
        assertFalse(genealogy.recordChild(null, null));
        assertFalse(genealogy.recordChild(a, null));
        assertFalse(genealogy.recordChild(null, b));
        assertTrue(genealogy.recordChild(a, b));
        assertFalse(genealogy.recordChild(xyz, b));
    }

    @Test
    @DisplayName("Testing person partnering")
    void recordPartnering() {
        PersonIdentity a = genealogy.addPerson("A");
        PersonIdentity b = genealogy.addPerson("B");
        PersonIdentity xyz = new PersonIdentity(999999, "xyz");
        assertFalse(genealogy.recordPartnering(null, null));
        assertFalse(genealogy.recordPartnering(a, null));
        assertFalse(genealogy.recordPartnering(null, b));
        assertTrue(genealogy.recordPartnering(a, b));
        assertFalse(genealogy.recordPartnering(xyz, b));
    }

    @Test
    @DisplayName("Testing person dissolution")
    void recordDissolution() {
        PersonIdentity a = genealogy.addPerson("A");
        PersonIdentity b = genealogy.addPerson("B");
        PersonIdentity xyz = new PersonIdentity(999999, "xyz");
        assertFalse(genealogy.recordDissolution(null, null));
        assertFalse(genealogy.recordDissolution(a, null));
        assertFalse(genealogy.recordDissolution(null, b));
        assertTrue(genealogy.recordDissolution(a, b));
        assertFalse(genealogy.recordDissolution(xyz, b));
    }

    @Test
    @DisplayName("Testing add media")
    void addMediaFile() {
        assertNull(genealogy.addMediaFile(null));
        assertNull(genealogy.addMediaFile(""));
        assertNull(genealogy.addMediaFile(" "));
        FileIdentifier fileIdentifier;
        assertSame(fileIdentifier = genealogy.addMediaFile("/CSCI3901/IMAGE.jpg"), fileIdentifier);
    }

    @Test
    @DisplayName("Testing media attributes")
    void recordMediaAttributes() {
        FileIdentifier fileIdentifier = genealogy.addMediaFile("/CSCI3901/1.jpg");
        assertFalse(genealogy.recordAttributes(null, new HashMap<>()));
        assertFalse(genealogy.recordMediaAttributes(null, new HashMap<>() {{
            put("date", "21/04/2011");
            put("location", "Russia");
        }}));
        assertFalse(genealogy.recordMediaAttributes(fileIdentifier, new HashMap<>()));
        assertFalse(genealogy.recordMediaAttributes(fileIdentifier, new HashMap<>() {{
            put(null, "21/04/2011");
            put("location", "Russia");
        }}));
        assertFalse(genealogy.recordMediaAttributes(fileIdentifier, new HashMap<>() {{
            put(null, "21/04/2011");
            put("location", null);
        }}));
        assertFalse(genealogy.recordMediaAttributes(fileIdentifier, new HashMap<>() {{
            put(null, "21/04/2011");
            put("location", "Canada");
        }}));
        assertTrue(genealogy.recordMediaAttributes(example, new HashMap<>() {{
            put("date", "2011");
            put("location", "Canada");
        }}));

        assertTrue(genealogy.recordMediaAttributes(example, new HashMap<>() {{
            put("date", "2011-09-18");
            put("location", "Canada");
        }}));
        assertTrue(genealogy.recordMediaAttributes(example, new HashMap<>() {{
            put("date", "2011-09");
            put("location", "Canada");
        }}));
        FileIdentifier xyz = new FileIdentifier("/CSCI5100/xyz.jpg");
        assertFalse(genealogy.recordMediaAttributes(xyz, new HashMap<>() {{
            put("date", "2011-04-19");
            put("location", "Russia");
        }}));
    }

    @Test
    @DisplayName("Testing media tags")
    void tagMedia() {
        FileIdentifier fileIdentifier = genealogy.addMediaFile("/CSCI3901/15.jpg");
        assertFalse(genealogy.tagMedia(null, "computer"));
        assertFalse(genealogy.tagMedia(fileIdentifier, null));
        assertFalse(genealogy.tagMedia(fileIdentifier, ""));
        assertFalse(genealogy.tagMedia(fileIdentifier, " "));
        assertTrue(genealogy.tagMedia(example, "computer"));
        FileIdentifier xyz = new FileIdentifier("/CSCI5100/xyz.jpg");
        assertFalse(genealogy.tagMedia(xyz, "computer"));
    }

    @Test
    @DisplayName("Testing people in media")
    void peopleInMedia() {
        PersonIdentity a = genealogy.addPerson("A");
        PersonIdentity b = genealogy.addPerson("B");
        PersonIdentity c = genealogy.addPerson("C");
        List<PersonIdentity> people = new ArrayList<>();
        people.add(a);
        people.add(b);
        people.add(c);
        FileIdentifier fileIdentifier = genealogy.addMediaFile("/CSCI3901/1.jpg");
        assertFalse(genealogy.peopleInMedia(null, people));
        assertFalse(genealogy.peopleInMedia(fileIdentifier, null));
        List<PersonIdentity> emptyList = new ArrayList<>();
        assertFalse(genealogy.peopleInMedia(fileIdentifier, emptyList));
        assertTrue(genealogy.peopleInMedia(fileIdentifier, people));
        PersonIdentity xyz = new PersonIdentity(999999, "xyz");
        people.add(xyz);
        //Adds remaining people except below
        assertTrue(genealogy.peopleInMedia(fileIdentifier, people));
    }

    @Test
    @DisplayName("Testing find person")
    void findPerson() {
        PersonIdentity p = genealogy.addPerson("G");
        assertNull(genealogy.findPerson(null));
        assertNull(genealogy.findPerson(""));
        assertNull(genealogy.findPerson(" "));
        //assertEquals(p, genealogy.findPerson("G"));
    }

    @Test
    @DisplayName("Testing find media file with string")
    void findMediaFile() {
        String name = null;
        assertNull(genealogy.findMediaFile(name));
        assertNull(genealogy.findMediaFile(""));
        assertNull(genealogy.findMediaFile(" "));
//        assertEquals();
    }

    @Test
    @DisplayName("Testing fine name")
    void findName() {
        PersonIdentity xyz = new PersonIdentity(999999, "xyz");
        assertNull(genealogy.findName(null));
        assertNull(genealogy.findName(xyz));
    }

    @Test
    @DisplayName("Testing find media file with file identifier")
    void testFindMediaFile() {
        FileIdentifier xyz = new FileIdentifier("/CSCI5100/xyz.jpg");
        FileIdentifier nullFile = null;
        assertNull(genealogy.findMediaFile(nullFile));
        assertNull(genealogy.findMediaFile(xyz));
        assertEquals("/CSCI3901/example.jpg", genealogy.findMediaFile(example));
    }

    @Test
    @DisplayName("Testing find relation")
    void findRelation() {
        PersonIdentity a = genealogy.addPerson("A");
        assertNull(genealogy.findRelation(null, null));
        assertNull(genealogy.findRelation(null, a));
        assertNull(genealogy.findRelation(a, null));
    }

    @Test
    @DisplayName("Testing ancestors")
    void ancestors() {
        assertTrue(genealogy.ancestores(null, 1).isEmpty());
        PersonIdentity xyz = new PersonIdentity(999999, "xyz");
        assertTrue(genealogy.ancestores(xyz, 2).isEmpty());
        PersonIdentity a = genealogy.addPerson("A");
        assertTrue(genealogy.ancestores(a, 999999).isEmpty());
    }

    @Test
    @DisplayName("Testing descendents")
    void descendents() {
        assertTrue(genealogy.descendents(null, 1).isEmpty());
        PersonIdentity xyz = new PersonIdentity(999999, "xyz");
        assertTrue(genealogy.descendents(xyz, 2).isEmpty());
        PersonIdentity a = genealogy.addPerson("A");
        assertTrue(genealogy.descendents(a, 999999).isEmpty());
    }

    @Test
    @DisplayName("Testing notes and references")
    void notesAndReferences() {
        assertTrue(genealogy.notesAndReferences(null).isEmpty());
        PersonIdentity xyz = new PersonIdentity(999999, "xyz");
        assertTrue(genealogy.notesAndReferences(xyz).isEmpty());
        PersonIdentity p = genealogy.addPerson("P");
        assertTrue(genealogy.notesAndReferences(p).isEmpty());
    }

    @Test
    @DisplayName("Testing media with tags")
    void findMediaByTag() {
        assertNull(genealogy.findMediaByTag(null, "", ""));
        assertNull(genealogy.findMediaByTag(null, "2001-01-01", ""));
        assertNull(genealogy.findMediaByTag(null, "", "2001-01-01"));
        assertNull(genealogy.findMediaByTag(null, "2001-01-01", "2001-01-01"));
        assertNull(genealogy.findMediaByTag(null, " ", " "));
        assertNull(genealogy.findMediaByTag(null, "2001-01-01", " "));
        assertNull(genealogy.findMediaByTag(null, " ", "2001-01-01"));
        assertNull(genealogy.findMediaByTag(null, "01/01/2001", "2001-01-01"));
        assertNull(genealogy.findMediaByTag("", "", ""));
        assertNull(genealogy.findMediaByTag(" ", "2001-01-01", ""));
        assertNull(genealogy.findMediaByTag(" ", "2001-01-01", " "));
        assertNull(genealogy.findMediaByTag(" ", "", "2001-01-01"));
        assertNull(genealogy.findMediaByTag(" ", " ", "2001-01-01"));
        assertFalse(genealogy.findMediaByTag("computer", null, null).isEmpty());
        assertTrue(genealogy.findMediaByTag("computer", "2001-01-01", "2001-01-01").isEmpty());
    }

    @Test
    @DisplayName("Testing media with location")
    void findMediaByLocation() {
        assertNull(genealogy.findMediaByLocation(null, "", ""));
        assertNull(genealogy.findMediaByLocation(null, "2001-01-01", ""));
        assertNull(genealogy.findMediaByLocation(null, "", "2001-01-01"));
        assertNull(genealogy.findMediaByLocation(null, "2001-01-01", "2001-01-01"));
        assertNull(genealogy.findMediaByLocation(null, " ", " "));
        assertNull(genealogy.findMediaByLocation(null, "2001-01-01", " "));
        assertNull(genealogy.findMediaByLocation(null, " ", "2001-01-01"));
        assertNull(genealogy.findMediaByLocation(null, "2001-01-01", "2001-01-01"));
        assertNull(genealogy.findMediaByLocation("", "", ""));
        assertNull(genealogy.findMediaByLocation(" ", "2001-01-01", ""));
        assertNull(genealogy.findMediaByLocation(" ", "2001-01-01", " "));
        assertNull(genealogy.findMediaByLocation(" ", "", "2001-01-01"));
        assertNull(genealogy.findMediaByLocation(" ", " ", "2001-01-01"));
        assertFalse(genealogy.findMediaByLocation("Russia", null, null).isEmpty());
        assertTrue(genealogy.findMediaByLocation("computer", "2001-01-01", "2001-01-01").isEmpty());
    }

    @Test
    @DisplayName("Testing find individuals media")
    void findIndividualsMedia() {
        PersonIdentity a = genealogy.addPerson("A");
        PersonIdentity c = genealogy.addPerson("B");
        Set<PersonIdentity> peopleSet = new HashSet<>();
        peopleSet.add(a);
        peopleSet.add(c);
        assertNull(genealogy.findIndividualsMedia(null, "", ""));
        assertNull(genealogy.findIndividualsMedia(null, "2001-01-01", ""));
        assertNull(genealogy.findIndividualsMedia(null, "", "2001-01-01"));
        assertNull(genealogy.findIndividualsMedia(null, "2001-01-01", "2001-01-01"));
        assertNull(genealogy.findIndividualsMedia(null, " ", " "));
        assertNull(genealogy.findIndividualsMedia(null, "2001-01-01", " "));
        assertNull(genealogy.findIndividualsMedia(null, " ", "2001-01-01"));
        assertNull(genealogy.findIndividualsMedia(null, "2001-01-01", "2001-01-01"));

        assertTrue(genealogy.findIndividualsMedia(peopleSet, "2011-01-25", " ").isEmpty());
        assertTrue(genealogy.findIndividualsMedia(peopleSet, "2001-01-01", "").isEmpty());
        assertTrue(genealogy.findIndividualsMedia(peopleSet, "2001-01-01", " ").isEmpty());
        assertTrue(genealogy.findIndividualsMedia(peopleSet, "", "2001-01-01").isEmpty());
        assertTrue(genealogy.findIndividualsMedia(peopleSet, " ", "2001-01-01").isEmpty());

        assertNull(genealogy.findIndividualsMedia(new HashSet<>(), "2001-01-01", "2001-01-01"));
        assertTrue(genealogy.findIndividualsMedia(peopleSet, "2001-01-01", "2001-01-01").isEmpty());
    }

    @Test
    void findBiologicalFamilyMedia() {
        PersonIdentity nullPerson = null;
        assertTrue(genealogy.findBiologicalFamilyMedia(nullPerson).isEmpty());
        PersonIdentity xyz = new PersonIdentity(999999, "xyz");
        assertTrue(genealogy.findBiologicalFamilyMedia(xyz).isEmpty());
    }
}