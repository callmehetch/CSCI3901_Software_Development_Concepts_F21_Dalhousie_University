//package assignment;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class OnlineAuctionSystem {
    //Declaration of the variables
    public String nLine;
    public String[] sString;
    public String returnAuctionStatus;
    public String returnFeesOwed;
    public int lLotNumber;
    public int lBidderID;
    public int lBid;
    public int bidNum = 0;

    //Declaration of ArrayLists of Auctions and Bidders.
    ArrayList<Bidder> B1 = new ArrayList<>();
    ArrayList<Auction> A1 = new ArrayList<>();

    //---------------------------------Auction Creation----------------------------------------------------
    public Auction createAuction(String name, int firstLot, int lastLot, int minBid) {
        //Check if Auction command input is null or empty
        if(name==null)
            return null;
        //Check if the first lot number or last lot number is less than or equal to zero.
        if (firstLot <= 0 || lastLot <= 0 || minBid <= 0) {
            return null;
        }
        else {
            if(name.isEmpty()){
                return null;
            }
            else {
                //For each auction, we get to check if  auction has the given lot number.
                for (Auction auction : A1) {
                    if (auction.getFirstLot() <= firstLot && auction.getLastLot() >= firstLot) {
                        return null;
                    }
                    if (auction.getFirstLot() <= lastLot && auction.getLastLot() >= lastLot) {
                        return null;
                    }
                }
                //Finally, if above cases are excepted, new auction object will be created and added to the ArrayList.
                Auction newAuction = new Auction(name, firstLot, lastLot, minBid);
                A1.add(newAuction);
                return newAuction;
            }
        }
    }
    //---------------------------------Auction Creation----------------------------------------------------

    //---------------------------------Bidder Creation----------------------------------------------------

    public Bidder createBidder(String name) {
        //Check if Bidder command input is null or empty.
        if (name==null){
            return null;
        }
        if (name.isEmpty()) {
            return null;
        }
        //After exceptions, a new bidder object is created and pushed into bidder ArrayList.
        //bidNum variable will be updated each time a bidder is added and this will be bidderID.
        bidNum++;
        Bidder newBidder = new Bidder(name, bidNum);
        B1.add(newBidder);
        return newBidder;
    }
    //---------------------------------Bidder Creation----------------------------------------------------

    //----------------------------Method to Load Bids from file-------------------------------------------
    public Integer loadBids(String filename) {
        int successBids = 0;//A variable to return the number of accepted bids from a loaded file.
        //Check if the filename is empty or null.
        if(filename==null){
            return null;
        }
        if(filename.isEmpty()){
            return null;
        }
      try{
          //File will be scanned
          File fileName = new File(filename);
          Scanner scan = new Scanner(fileName);
          //For each line, it will be split with pivot '\t' and store into an array sString.
          //Each element of sString will be converted to an Integer and passed to placeBid function as lot number, bidder ID and bid.
          while (scan.hasNextLine()){
              nLine = scan.nextLine();
              sString = nLine.split("\t");
              lBidderID = Integer.parseInt(sString[0]);
              lLotNumber = Integer.parseInt(sString[1]);
              lBid = Integer.parseInt(sString[2]);
              int k = placeBid(lLotNumber, lBidderID, lBid);
              //After the bids are processed in placeBids, if it returns 2 or 3 or 4, we update the successBids count
              if(k==2 || k==3 || k==4)
                  successBids++;
          }
          return successBids;
      }
      catch (Exception e){
          return 0;
      }
    }
    //----------------------------Method to Load Bids from file-------------------------------------------
    public Integer placeBid(int lotNumber, Integer bidderId, int bid) {
        //Check if the inputs are invalid
        if(bidderId<=0 || lotNumber<=0 || bid<=0){
           return 0;
        }
        //Find the auction which given lot belongs to
        for (Auction auction : A1) {
            if (auction.getFirstLot() <= lotNumber && auction.getLastLot() >= lotNumber) {
                //Process bid to runBids only if the auction is open.
                if (Objects.equals(auction.getStatus(), "open")) {
                    return auction.runBids(lotNumber, bidderId, bid);
                } else {
                    return 0;
                }
            }
        }
             return 0;
     }

    //----------------------------Method to get Auction status--------------------------------------------
    public String auctionStatus() {
        //A string variable to return
        returnAuctionStatus = "";
        //For each auction, return name, status and sum of it's lots.
        for (Auction auction : A1) {
            returnAuctionStatus = returnAuctionStatus + (auction.name + "\t" + auction.status + "\t" + auction.getLotValue() + "\n");
        }
        return returnAuctionStatus;
    }
    //----------------------------Method to get Auction status--------------------------------------------

    //----------------------------Method to get Owedfee---------------------------------------------------
    public String feesOwed () {
        //A string variable to return

        returnFeesOwed = "";
        //For each bidder, calculate no of lots the bidder has won, sum of bids the bidder owes and the bidder name
            for (Bidder checkBidder: B1) {
                //Initialize the number of lots and sum of bids to zero.
                checkBidder.noOfLots = 0;
                checkBidder.sumOfBids = 0;
                for (Auction auctionBid:A1) {
                    //For each auction that bidder belongs to, the auction should be closed.
                    if (auctionBid.getStatus()=="close"){
                        auctionBid.findLots(checkBidder);
                    }
                }
                returnFeesOwed = returnFeesOwed + (checkBidder.getName() + "\t" + checkBidder.noOfLots + "\t" + checkBidder.sumOfBids + "\n");
            }
            return returnFeesOwed;
        }
    //----------------------------Method to get Owedfee---------------------------------------------------

}


