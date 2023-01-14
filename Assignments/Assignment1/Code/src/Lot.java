//package assignment;

public class Lot {
    public int number;
    private String belongsToAuction;
    private int bidderID = 0;
    private int currentBid = 0;
    public int nextBid = 0;
    private int minIncrementBid = 0;
    private int rememberedBid = 0;

    //-------------------------------Lot Constructor--------------------------------
    public Lot(int number, String belongsToAuction, int minIncrementBid){
       setNumber(number);
       setBelongsToAuction(belongsToAuction);
       setMinIncrementBid(minIncrementBid);
       nextBid = getMinIncrementBid();
    }
    //-------------------------------Lot Constructor--------------------------------

    //-----------------Setter and Getter to validate null or <=0--------------------
    public void setNumber(int number) {
        this.number = number;
    }
    public String getBelongsToAuction() {
        return belongsToAuction;
    }
    public void setBelongsToAuction(String belongsToAuction) {
        this.belongsToAuction = belongsToAuction;
    }
    public int getBidderID() {
        return bidderID;
    }
    public void setBidderID(int bidderID) {
        this.bidderID = bidderID;
    }
    public int getRememberedBid() {
        return rememberedBid;
    }
    public void setRememberedBid(int rememberedBid) {
        this.rememberedBid = rememberedBid;
    }
    public int getCurrentBid() {
        return currentBid;
    }
    public void setCurrentBid(int currentBid) {
        this.currentBid = currentBid;
    }
    public int getMinIncrementBid() {
        return minIncrementBid;
    }
    public void setMinIncrementBid(int minIncrementBid) {
        this.minIncrementBid = minIncrementBid;
    }
    public int getNumber() {
        return number;
    }
    //-----------------Setter and Getter to validate null or <=0--------------------
}
