import java.util.Scanner;

public class MenuHandler {
    private Scanner scanner;
    private FlightGraph flightGraph;
    private AuthManager authManager;

    public MenuHandler(FlightGraph flightGraph, AuthManager authManager, Scanner scanner) {
        this.flightGraph = flightGraph;
        this.authManager = authManager;
        this.scanner = scanner;
    }

    public void showMainMenu() {
        boolean running = true;
        while (running) {
            System.out.println();
            UIHelper.printMASBanner();
            UIHelper.printBoxRow("Logged-in Staff : " + authManager.getCurrentStaffName() + " (" + authManager.getCurrentStaffId() + ")");
            System.out.println(UIHelper.BORDER_LINE);
            UIHelper.printBoxRow("[1] Create / Modify Flight Graph            (Bryan Lai - Coming Soon)");
            UIHelper.printBoxRow("[2] Search for an Airport (BFS Traversal)   (Derrick Tan - ACTIVE)");
            UIHelper.printBoxRow("[3] View the Complete MAS Flight Network    (Wang Chong Liang - Coming Soon)");
            UIHelper.printBoxRow("[4] Logout & Return to Portal");
            System.out.println(UIHelper.BORDER_LINE);
            System.out.print("Enter your choice (1-4) : ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    flightGraph.addAirport("");
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
                case "Exit":
                case "exit":
                    authManager.logout();
                    System.out.println("\n[!] Logging out session. Returning to Security Portal...");
                    running = false;
                    break;
                default:
                    System.out.println("[Error] Invalid choice. Please choose 1, 2, 3, or 4.");
            }
        }
    }

    private void handleSearchAirport() {
        System.out.println("\n" + UIHelper.BORDER_LINE);
        UIHelper.printBoxRow("SEARCH AIRPORT CONNECTIVITY (BREADTH-FIRST SEARCH)");
        UIHelper.printBoxRow("Available Airports in Network:");
        
        StringBuilder sb = new StringBuilder("  -> ");
        int count = 0;
        for (String airport : flightGraph.getAllAirports()) {
            sb.append("[").append(airport).append("]  ");
            count++;
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