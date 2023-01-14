//package assignment;

import java.util.ArrayList;
import java.util.Objects;

public class Auction {
    private int firstLot;
    private int lastLot;
    private int minIncrementBid;
    private int totalBidSum;
    private int lotNumber;
    private int bidderId;
    private int bid;
    public String status;
    public String name;
    public String returnWinningBids;

    //Lots ArrayList
    private final ArrayList<Lot> L1 = new ArrayList<>();

    //----------------------------Auction Constructor-------------------------------------------
    public Auction(String name, int firsLot, int lastLot, int minIncrementBid){
        setName(name);
        setFirstLot(firsLot);
        setLastLot(lastLot);
        setMinIncrementBid(minIncrementBid);
        this.status = "new";
        //New lot objects to be created and pushed into lot arraylist
        for(int i=firsLot; i<=lastLot; i++){
            Lot newLot = new Lot(i, name, minIncrementBid);
            L1.add(newLot);
        }
    }
    //----------------------------Auction Constructor-------------------------------------------

    //----------------------Method to process bids after placing---------------------------------
    public int runBids(int lotNumber, int bidderId, int bid){
        for (Lot lot : L1) {

            if (lot.getNumber() == lotNumber) {
                //------------Bid conditions------------------------------
                //if bid is less than the next minimum bid, it returns 1. Bid isn't accepted
                if (bid < lot.nextBid)
                    return 1;
                else {
                    //If the bidder is same as previous winning bidder
                    if (bidderId == lot.getBidderID()) {
                        if (bid <= lot.getRememberedBid()) {
                            if (lot.getRememberedBid() <= lot.nextBid) {
                                return 3;
                            } else {
                                return 4;
                            }
                        } else if (bid > lot.getRememberedBid()) {
                            lot.setRememberedBid(bid);
                            if (lot.getRememberedBid() < lot.nextBid) {
                                return 3;
                            } else {
                                return 4;
                            }
                        }
                    }
                    //If the bidder is changed from the previous bidder
                    else {
                        if (bid < lot.getRememberedBid()) {
                            if (bid + lot.getMinIncrementBid() > lot.getRememberedBid()) {
                                lot.setCurrentBid(bid);
                                lot.nextBid = lot.getCurrentBid() + lot.getMinIncrementBid();
                                return 2;
                            } else {
                                lot.setCurrentBid(bid + lot.getMinIncrementBid());
                                lot.nextBid = lot.getCurrentBid() + lot.getMinIncrementBid();
                                if (lot.getRememberedBid() < lot.nextBid) {
                                    return 3;
                                } else {
                                    return 4;
                                }
                            }
                        } else if (bid > lot.getRememberedBid()) {
                            if(bid>=lot.getRememberedBid()+lot.getMinIncrementBid()){
                                lot.setCurrentBid(lot.getRememberedBid()+lot.getMinIncrementBid());
                            }else{
                                lot.setCurrentBid(lot.getRememberedBid());
                            }
                            lot.setBidderID(bidderId);
                            lot.setRememberedBid(bid);
                            lot.nextBid = lot.getCurrentBid() + lot.getMinIncrementBid();
                            if (lot.getRememberedBid() < lot.nextBid) {
                                return 3;
                            } else {
                                return 4;
                            }
                        } else {
                            lot.setCurrentBid(lot.getRememberedBid());
                            lot.nextBid = lot.getCurrentBid() + lot.getMinIncrementBid();
                            return 1;
                        }
                    }
                }

                //----------------------Bid conditions-----------------------
            }
        }
        return 0;
    }
    //----------------------Method to process bids after placing---------------------------------

    //----------------------Method to make an Auction Open for bidding----------------------------
    public boolean openAuction() {
        //Auction cannot be opened if it was closed previously
        if(Objects.equals(getStatus(), "close")){
            return false;
        }
        setStatus("open");
        return true;
    }
    //----------------------Method to make an Auction Open for bidding----------------------------

    //----------------------Method to make an Auction Close for bidding----------------------------
    public boolean closeAuction() {
        //Auction cannot be closed if it was not opened previously
        if(!Objects.equals(getStatus(), "open")){
            return false;
        }
        setStatus("close");
        return true;
    }
    //----------------------Method to make an Auction Open for bidding----------------------------

    //----------------------Method to print winning bids for each Lot----------------------------
    public String winningBids() {
        returnWinningBids = "";
        for (Lot lot: L1) {
            returnWinningBids = returnWinningBids + (lot.getNumber() + "\t" + lot.getCurrentBid() + "\t" + lot.getBidderID() + "\n");
        }
        return returnWinningBids;
    }
    //----------------------Method to print winning bids for each Lot----------------------------

    //-------------Method to calculate lotValue to be used in auctionStatus---------------------
    public int getLotValue(){
        int totalLotValue = 0;
        for (Lot checkLot: L1) {
            totalLotValue = totalLotValue + checkLot.getCurrentBid();
        }
        return totalLotValue;
    }
    //-------------Method to calculate lotValue to be used in auctionStatus---------------------

    //-------------Method to to update number of lots and sum of fee Owed------------------------
    public void findLots(Bidder checkBidder) {
        for (Lot checkLot:L1) {
            if(checkLot.getBidderID() == checkBidder.getBidderId()){
                checkBidder.noOfLots++;
                checkBidder.sumOfBids = checkBidder.sumOfBids + checkLot.getCurrentBid();
            }
        }
    }
    //-------------Method to to update number of lots and sum of fee Owed------------------------

    //-----------------Setter and Getter to validate null or <=0--------------------
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public int getTotalBidSum() {
        return totalBidSum;
    }
    public void setTotalBidSum(int totalBidSum) {
        this.totalBidSum = totalBidSum;
    }
    public int getLotNumber() {
        return lotNumber;
    }
    public void setLotNumber(int lotNumber) {
        this.lotNumber = lotNumber;
    }
    public int getBidderId() {
        return bidderId;
    }
    public void setBidderId(int bidderId) {
        this.bidderId = bidderId;
    }
    public int getBid() {
        return bid;
    }
    public void setBid(int bid) {
        this.bid = bid;
    }
    public String getName() {
        return name;
    }
    private void setName(String name) {
        this.name = name;
    }
    private void setFirstLot(int firstLot) {
        this.firstLot = firstLot;
    }
    public int getFirstLot() {
        return firstLot;
    }
    private void setLastLot(int lastLot) {
        this.lastLot = lastLot;
    }
    public int getLastLot(){
        return lastLot;
    }
    private void setMinIncrementBid(int minIncrementBid){
        this.minIncrementBid = minIncrementBid;
    }
    public int getMinIncrementBid(){
        return minIncrementBid;
    }
    //-----------------Setter and Getter to validate null or <=0--------------------
}
