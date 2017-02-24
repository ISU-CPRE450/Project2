import AuctionApp.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import java.util.Scanner;


public class BuyerClient {
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
                        System.out.print("Enter amount to bid: $");
                        float amount;
                        try {
                            amount = reader.nextFloat();
                        }
                        catch(Exception e) {
                            String badInput = reader.next();
                            System.out.println("'" + badInput + "' is invalid. Please enter a floating-point value.");
                            continue;
                        }
                        printResponse(serverImp.bid(userId, amount));
                    }
                    else if (input == 2) {
                        printResponse(serverImp.viewAuctionStatus(userId));
                    }
                    else if (input == 3) {
                        printResponse(serverImp.viewBidStatus(userId));
                    }
                    else if (input == 4) {
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
        System.out.println("Welcome to the Auction App! You are entering as a buyer.");
        System.out.print("Please enter your id: ");
        userId = reader.next();
        System.out.println("Happy bidding, " + userId + "!");
        System.out.println("==============================================\n\n");
    }

    public static void printOptions() {
        System.out.println("==================== MENU ====================");
        System.out.println("1. Bid");
        System.out.println("2. View Auction Status");
        System.out.println("3. View Bid Status");
        System.out.println("4. Quit");
    }

    public static void printResponse(String response) {
        System.out.println("==============================================");
        System.out.println(response);
        System.out.println("==============================================");

    }
}
