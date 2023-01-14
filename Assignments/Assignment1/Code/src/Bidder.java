//package assignment;
public class Bidder {
    private int bidderID;
    public int sumOfBids;
    public int noOfLots;
    private String name;

    //----------------------------Bidder Constructor-------------------------------------------
    public Bidder(String name, int bidNum){
        setName(name);
        setBidderID(bidNum);
    }
    //----------------------------Bidder Constructor-------------------------------------------

    //----------------------------Method to get Bidder ID--------------------------------------
    public Integer getBidderId() {
        return getBidderID();
    }
    //----------------------------Method to get Bidder ID--------------------------------------

    //-----------------Setter and Getter to validate null or <=0--------------------
    private void setName(String name) {
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public int getBidderID() {
        return bidderID;
    }
    public void setBidderID(int bidderID) {
        this.bidderID = bidderID;
    }
    //-----------------End of Setter and Getter to validate null or <=0--------------
}
