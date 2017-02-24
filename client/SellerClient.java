import AuctionApp.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import java.util.Scanner;


public class SellerClient {
    static AuctionServer serverImp;
    static Scanner reader;
    static String userId;
    public static void main(String[] args) {
        try {
            reader = new Scanner(System.in);
            printWelcomeMessageAndGetUserID();
            System.out.println("Establishing connection to server...\n\n");
            ORB orb = ORB.init(args, null);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            String name = "Auction";
            serverImp = AuctionServerHelper.narrow(ncRef.resolve_str(name));
            System.out.println("Connection established!\n\n");
            while (true) {
                try {
                    printOptions();
                    System.out.print("Enter a number to select an option: ");
                    int input = 0;
                    try {
                        input = reader.nextInt();
                    }
                    catch(Exception e) {
                        String badInput = reader.next();
                        System.out.println("'" + badInput + "' is not an option.");
                        continue;
                    }
                    if (input == 1) {
                        System.out.print("Enter item description: ");
                        reader.nextLine();
                        String description = reader.nextLine();
                        System.out.print("Enter initial price (press enter skip): $");
                        float amount = 0;
                        String strAmount = reader.nextLine();
                        if (!strAmount.equals("")) {
                            amount = Float.valueOf(strAmount);
                        }
                        Item item = new Item(description, amount);
                        printResponse(serverImp.offerItem(userId, item));
                    }
                    else if (input == 2) {
                        printResponse(serverImp.viewHighBidder(userId));
                    }
                    else if (input == 3) {
                        printResponse(serverImp.viewAuctionStatus(userId));
                    }
                    else if(input == 4) {
                        printResponse(serverImp.sell(userId));
                    }
                    else if (input == 5) {
                        System.out.println("==============================================");
                        System.out.println("Exiting Auction App. Goodbye.");
                        break;
                    }
                    else {
                        System.out.println("'" + input + "' is not an option;");
                    }
                }
                catch(Exception e) {
                    System.out.println("Error: " + e);
                    e.printStackTrace(System.out);
                }
            }
        }
        catch(Exception e) {
            System.out.println("Error: " + e);
            e.printStackTrace(System.out);
            System.out.flush();
        }
    }

    public static void printWelcomeMessageAndGetUserID() {
        System.out.println("==============================================");
        System.out.println("Welcome to the Auction App! You are entering as a seller.");
        System.out.print("Please enter your id: ");
        userId = reader.next();
        System.out.println("Happy selling, " + userId + "!");
        System.out.println("==============================================\n\n");
    }

    public static void printOptions() {
        System.out.println("==================== MENU ====================");
        System.out.println("1. Offer Item");
        System.out.println("2. View Highest Bidder");
        System.out.println("3. View Auction Status");
        System.out.println("4. Sell");
        System.out.println("5. Quit");
    }

    public static void printResponse(String response) {
        System.out.println("==============================================");
        System.out.println(response);
        System.out.println("==============================================");

    }
}
