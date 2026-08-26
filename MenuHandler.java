import java.util.Scanner;

public class MenuHandler {
    private Scanner scanner;
    private FlightGraph flightGraph;
    private AuthManager authManager;

    //constructor
    public MenuHandler(FlightGraph flightGraph, AuthManager authManager, Scanner scanner) {
        this.flightGraph = flightGraph;
        this.authManager = authManager;
        this.scanner = scanner;
    }

    //display main menu and verfity user input using switch case
    public void showMainMenu() {
        boolean running = true;
        while (running) {
            System.out.println();
            UIHelper.printMASBanner();
            UIHelper.printBoxRow("Logged-in Staff : " + authManager.getCurrentStaffName() + " (" + authManager.getCurrentStaffId() + ")");
            System.out.println(UIHelper.BORDER_LINE);
            UIHelper.printBoxRow("[1] Create / Modify Flight Graph");
            UIHelper.printBoxRow("[2] Search for an Airport (BFS Traversal)");
            UIHelper.printBoxRow("[3] View the Complete MAS Flight Network ");
            UIHelper.printBoxRow("[4] Find Flight Path (Point-to-Point)");
            UIHelper.printBoxRow("[5] Logout & Return to Portal");
            System.out.println(UIHelper.BORDER_LINE);
            System.out.print("Enter your choice (1-5) : ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    handleModifyGraph();
                    pauseForEnter();
                    break;
                case "2":
                    handleSearchAirport();
                    break;
                case "3":
                    flightGraph.displayNetwork();
                    pauseForEnter();
                    break;
                case "4":
                    handleFindPath();
                    break;
                case "5":
                case "Exit":
                case "exit":
                    authManager.logout();
                    System.out.println("\n[!] Logging out session. Returning to Security Portal...");
                    running = false;
                    break;
                default:
                    System.out.println("[Error] Invalid choice. Please choose 1, 2, 3, 4 or 5.");
            }
        }
    }

    //function 1 sub menu
    private void handleModifyGraph() {
        //default modifying is true mean once entry this function it directly show the menu
        boolean modifying = true;
        while (modifying) {
            System.out.println("\n" + UIHelper.BORDER_LINE);
            UIHelper.printBoxRow("[CREATE / MODIFY FLIGHT GRAPH]");
            UIHelper.printBoxRow("[1] Add a New Airport (Vertex)");
            UIHelper.printBoxRow("[2] Remove an Airport (Vertex)");
            UIHelper.printBoxRow("[3] Add a Flight Route (Edge)");
            UIHelper.printBoxRow("[4] Remove a Flight Route (Edge)");
            UIHelper.printBoxRow("[0] Back to Main Menu");
            System.out.println(UIHelper.BORDER_LINE);
            System.out.print("Enter your choice (0-4) : ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.print("Enter NEW Airport Name: ");
                    flightGraph.addAirport(scanner.nextLine().trim());
                    break;
                case "2":
                    System.out.print("Enter Airport Name to REMOVE: ");
                    flightGraph.removeAirport(scanner.nextLine().trim());
                    break;
                case "3":
                    System.out.print("Enter Source Airport: ");
                    String srcAdd = scanner.nextLine().trim();
                    System.out.print("Enter Destination Airport: ");
                    String destAdd = scanner.nextLine().trim();
                    flightGraph.addRoute(srcAdd, destAdd);
                    break;
                case "4":
                    System.out.print("Enter Source Airport: ");
                    String srcRem = scanner.nextLine().trim();
                    System.out.print("Enter Destination Airport: ");
                    String destRem = scanner.nextLine().trim();
                    flightGraph.removeRoute(srcRem, destRem);
                    break;
                case "0":
                    modifying = false;
                    break;
                default:
                    System.out.println("[Error] Invalid choice.");
            }
            //if is invalid choice ask user press enter to continue
            if (modifying) pauseForEnter();
        }
    }

    private void handleFindPath() {
        System.out.println("\n" + UIHelper.BORDER_LINE);
        UIHelper.printBoxRow("FIND SHORTEST FLIGHT PATH (BFS)");
        System.out.println(UIHelper.BORDER_LINE);
        
        System.out.print("Enter Origin Airport      : ");
        String start = scanner.nextLine().trim();
        
        System.out.print("Enter Destination Airport : ");
        String end = scanner.nextLine().trim();
        
        //check the user input is empty or not is not go to findPathBFS function , if yes show the error message
        if (!start.isEmpty() && !end.isEmpty()) {
            flightGraph.findPathBFS(start, end);
        } else {
            System.out.println("[Error] Airport names cannot be empty.");
        }
        
        pauseForEnter();
    }

    //use for loop to show all available airport
    //ask user the enter the origin airport and use BFS to show all the flight connections
    private void handleSearchAirport() {
        System.out.println("\n" + UIHelper.BORDER_LINE);
        UIHelper.printBoxRow("SEARCH AIRPORT CONNECTIVITY (BREADTH-FIRST SEARCH)");
        UIHelper.printBoxRow("Available Airports in Network:");
        
        //use string builder to set the format and display
        StringBuilder sb = new StringBuilder("  -> ");
        int count = 0;
        for (String airport : flightGraph.getAllAirports()) {
            sb.append("[").append(airport).append("]  ");
            count++;
            //when 4 airport added, print them as a row
            if (count % 4 == 0) {
                UIHelper.printBoxRow(sb.toString());
                sb = new StringBuilder("  -> ");
            }
        }
        if (count % 4 != 0) {
            UIHelper.printBoxRow(sb.toString());
        }

        System.out.println(UIHelper.BORDER_LINE);
        System.out.print("Enter Origin Airport Name (or '0' to cancel): ");
        String startCity = scanner.nextLine().trim();

        //trigger the BFS algorithm after get the user input
        if (!startCity.equals("0") && !startCity.isEmpty()) {
            flightGraph.bfsTraversal(startCity);
        }

        pauseForEnter();
    }

    private void pauseForEnter() {
        System.out.print("\nPress [Enter] key to return to Main Menu...");
        scanner.nextLine();
    }
}