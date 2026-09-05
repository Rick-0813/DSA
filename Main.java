import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //
        Scanner scanner = new Scanner(System.in);
        AuthManager authManager = new AuthManager();
        FlightGraph flightGraph = new FlightGraph();

        boolean running = true;
        while (running) {
            // display pre-login menu while true
            System.out.println();
            UIHelper.printSecurityBanner();
            UIHelper.printBoxRow("[1] Staff Login");
            UIHelper.printBoxRow("[2] Register New Staff Account");
            UIHelper.printBoxRow("[3] Terminate Program");
            System.out.println(UIHelper.BORDER_LINE);
            System.out.print("Enter your choice (1-3) : ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    // the if is use to verify the login
                    // after return true display the main menu
                    if (authManager.login(scanner)) {
                        MenuHandler menuHandler = new MenuHandler(flightGraph, authManager, scanner);
                        menuHandler.showMainMenu();
                    }
                    break;
                case "2":
                    authManager.register(scanner);
                    break;
                case "3":
                    System.out.println("\n" + UIHelper.BORDER_LINE);
                    UIHelper.printBoxRow("System terminated safely. Goodbye!");
                    System.out.println(UIHelper.BORDER_LINE);
                    running = false;
                    break;
                default:
                    System.out.println("[Error] Invalid option. Please select 1, 2, or 3.");
            }
        }
        scanner.close();
    }
}