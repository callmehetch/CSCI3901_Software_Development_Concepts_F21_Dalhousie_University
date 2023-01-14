public class BiologicalRelation {
    //Declaring the attributes required for a relation and reporting.
    private int degreeOFCousinShip;
    private int levelOfRemoval;
    private PersonIdentity firstPersonID;
    private PersonIdentity secondPersonID;
    private String relation;
    private String commonAncestor;

    //Biological Relation constructor to initialize persons.
    public BiologicalRelation(PersonIdentity firstPersonID, PersonIdentity secondPersonID) {
        setFirstPersonID(firstPersonID);
        setSecondPersonID(secondPersonID);
    }

    //---------------------Getters And Setters For Encapsulation Of the Data Attributes---------------------------------
    public int getDegreeOFCousinShip() {
        return degreeOFCousinShip;
    }

    public void setDegreeOFCousinShip(int degreeOFCousinShip) {
        this.degreeOFCousinShip = degreeOFCousinShip;
    }

    public int getLevelOfRemoval() {
        return levelOfRemoval;
    }

    public void setLevelOfRemoval(int levelOfRemoval) {
        this.levelOfRemoval = levelOfRemoval;
    }

    public void setFirstPersonID(PersonIdentity firstPersonID) {
        this.firstPersonID = firstPersonID;
    }

    public void setSecondPersonID(PersonIdentity secondPersonID) {
        this.secondPersonID = secondPersonID;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getCommonAncestor() {
        return commonAncestor;
    }

    public void setCommonAncestor(String commonAncestor) {
        this.commonAncestor = commonAncestor;
    }

    //---------------------Getters And Setters For Encapsulation Of the Data Attributes---------------------------------

}