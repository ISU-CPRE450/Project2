import AuctionApp.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;

class AuctionServerImp extends AuctionServerPOA {
    private static float currentPrice = 0;
    private static Item item = null;
    private static boolean isSold = false;
    private static String highestBidderId = null;
    private static String sellerId = null;
    private ORB orb;
    public void setORB(ORB orb_val) {
        orb = orb_val;
    }
    // Generic methods
    public String viewAuctionStatus(String id) {
        String resp = "\n";
        if (item == null) {
           resp += "Auction status: empty\n";
        }
        else {
            if (isSold) {
                resp += "Auction status: sold\n";
            }
            else {
                resp += "Auction status: active\n";
            }
            resp += "Item description: " + item.description + "\n";
            resp += "Current price: $" + currentPrice + "\n";
            if (sellerId != null && id.equals(sellerId)) {
                if (highestBidderId == null) {
                    resp += "There have been no bids on this item yet\n";
                }
                else {
                    resp += "Highest bidder id: " + highestBidderId + "\n";
                }
            }
        }
        return resp;
    }
    // Buyer methods
    public String bid(String id, float amount) {
        if (sellerId != null && id.equals(sellerId)) {
            return "\nBid not applied - the seller can not bid on his/her own item\n";
        }
        if (item == null) {
            return "\nBid not applied - there is nothing to bid on\n";
        }

        if (isSold) {
            return "\nBid not applied - the item is already sold\n";
        }

        if (amount <= currentPrice) {
            return "\nBid not applied - $" + amount + " is less than the current price\n";
        }
        currentPrice = amount;
        highestBidderId = id;
        return "\nYou bid $" + amount + ", and have become the highest bidder!\n";
    }
    public String viewBidStatus(String id) {
        if (item == null) {
            return "\nNo bid information to report - no auction active\n";
        }
        if (isSold) {
            return "\nNo bid information to report - item already sold\n";
        }
        if (id.equals(sellerId)) {
            return "\nNo bid information to report - you are the seller\n";
        }
        if (id.equals(highestBidderId)) {
            return "\nYou are the highest bidder\n";
        }
        return "\nYou are not the highest bidder\n";
    }
    // Seller methods
    public String offerItem(String id, Item newItem) {
        if (item != null && !isSold) {
            return "\nOffer not accepted - an item is already being auctioned\n";
        }
        float amount = newItem.initialPrice;
        this.item = newItem;
        sellerId = id;
        highestBidderId = null;
        isSold = false;
        currentPrice = amount;
        String resp = "\nOffer accepted. '" + newItem.description + "' is being auctioned, starting at $" + amount + "\n";
        return resp;
    }
    public String viewHighBidder(String id) {
        if (sellerId == null || !id.equals(sellerId)) {
            return "\nRequest to view highest bidder denied - you are not the current seller\n";
        }
        if (highestBidderId == null) {
            return "\nNo one has bid on your item yet\n";
        }
        return "\nHighest bidder id: " + highestBidderId + "\n";
    }
    public String sell(String id) {
        if (sellerId == null || !id.equals(sellerId)) {
            return "\nRequest to sell item denied - you are not the current seller\n";
        }
        if (item == null) {
            return "\nRequest to sell item denied - there is no item for sale\n";
        }
        if (highestBidderId == null) {
            return "\nRequest to sell item denied - no one has bid on it yet\n";
        }
        isSold = true;
        return "\nItem sold to " + highestBidderId + " for " + currentPrice + "\n";
    }
}

public class Server {
    public static void main(String[] args) {
        try{
            System.out.println("Creating server...");
            ORB orb = ORB.init(args, null);
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();
            AuctionServerImp serverImp = new AuctionServerImp();
            serverImp.setORB(orb);
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(serverImp);
            AuctionServer href = AuctionServerHelper.narrow(ref);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            String name = "Auction";
            NameComponent path[] = ncRef.to_name( name );
            ncRef.rebind(path, href);
            System.out.println("Server created! Ready for clients");
            orb.run();
        }
        catch(Exception e) {
            System.err.println("Server error: " + e);
            e.printStackTrace(System.out);
        }
        System.out.println("Auction server exiting");
    }

}
