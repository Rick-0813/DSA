import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AuthManager {
    private static final String STAFF_FILE = "staff_data.txt";

    public static class StaffAccount {
        private String staffId;
        private String staffName;
        private String password;

        public StaffAccount(String staffId, String staffName, String password) {
            this.staffId = staffId.trim().toUpperCase();
            this.staffName = staffName.trim();
            this.password = password.trim();
        }

        public String getStaffId() { return staffId; }
        public String getStaffName() { return staffName; }
        public String getPassword() { return password; }
    }

    private Map<String, StaffAccount> staffDatabase;
    private StaffAccount currentLoggedInStaff;

    public AuthManager() {
        this.staffDatabase = new HashMap<>();
        this.currentLoggedInStaff = null;
        loadStaffFromFile();
    }

    public boolean login(Scanner scanner) {
        int maxAttempts = 3;
        int attempts = 0;

        while (attempts < maxAttempts) {
            System.out.println("\n" + UIHelper.BORDER_LINE);
            UIHelper.printBoxRow("[STAFF AUTHENTICATION LOGIN]");
            UIHelper.printBoxRow("Please enter your credentials to access MAS Flight System.");
            System.out.println(UIHelper.BORDER_LINE);

            System.out.print("Enter Staff ID  : ");
            String inputId = scanner.nextLine().trim().toUpperCase();

            System.out.print("Enter Password  : ");
            String inputPass = scanner.nextLine().trim();

            if (staffDatabase.containsKey(inputId)) {
                StaffAccount account = staffDatabase.get(inputId);
                if (account.getPassword().equals(inputPass)) {
                    this.currentLoggedInStaff = account;
                    System.out.println("\n" + UIHelper.BORDER_LINE);
                    UIHelper.printBoxRow("[✓ AUTHENTICATION SUCCESSFUL]");
                    UIHelper.printBoxRow("Welcome back, " + account.getStaffName() + " (" + account.getStaffId() + ")!");
                    System.out.println(UIHelper.BORDER_LINE);
                    return true;
                }
            }

            attempts++;
            int remaining = maxAttempts - attempts;
            System.out.println("\n[!] Access Denied: Invalid Staff ID or Password!");
            if (remaining > 0) {
                System.out.println("[!] Security Notice: " + remaining + " attempt(s) remaining before system lockout.\n");
            }
        }

        System.out.println("\n" + UIHelper.BORDER_LINE);
        UIHelper.printBoxRow("[SECURITY ALERT] Maximum login attempts exceeded!");
        UIHelper.printBoxRow("System locked to prevent unauthorized intrusion. Terminating session.");
        System.out.println(UIHelper.BORDER_LINE);
        return false;
    }

    public boolean register(Scanner scanner) {
        System.out.println("\n" + UIHelper.BORDER_LINE);
        UIHelper.printBoxRow("[NEW STAFF REGISTRATION]");
        System.out.println(UIHelper.BORDER_LINE);

        System.out.print("Enter New Staff ID (e.g. MAS05) : ");
        String id = scanner.nextLine().trim().toUpperCase();

        if (id.isEmpty()) {
            System.out.println("[Error] Staff ID cannot be empty.");
            return false;
        }
        if (staffDatabase.containsKey(id)) {
            System.out.println("[Error] Staff ID '" + id + "' is already registered in the system.");
            return false;
        }

        System.out.print("Enter Full Name                 : ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("[Error] Staff Name cannot be empty.");
            return false;
        }

        System.out.print("Create Password                 : ");
        String pass = scanner.nextLine().trim();
        if (pass.isEmpty()) {
            System.out.println("[Error] Password cannot be empty.");
            return false;
        }

        StaffAccount newStaff = new StaffAccount(id, name, pass);
        staffDatabase.put(id, newStaff);
        saveStaffToFile();

        System.out.println("\n" + UIHelper.BORDER_LINE);
        UIHelper.printBoxRow("[✓ SUCCESS] New Staff Account Registered!");
        UIHelper.printBoxRow("Staff ID: " + id + " | Staff Name: " + name);
        System.out.println(UIHelper.BORDER_LINE);
        return true;
    }

    public String getCurrentStaffId() {
        return currentLoggedInStaff != null ? currentLoggedInStaff.getStaffId() : "N/A";
    }

    public String getCurrentStaffName() {
        return currentLoggedInStaff != null ? currentLoggedInStaff.getStaffName() : "Guest";
    }

    public void logout() {
        this.currentLoggedInStaff = null;
    }

    private void saveStaffToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STAFF_FILE))) {
            for (StaffAccount acc : staffDatabase.values()) {
                writer.write(acc.getStaffId() + "," + acc.getStaffName() + "," + acc.getPassword());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("[Warning] Failed to save staff data: " + e.getMessage());
        }
    }

    private void loadStaffFromFile() {
        File file = new File(STAFF_FILE);
        if (!file.exists()) {
            loadDefaultStaff();
            saveStaffToFile();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            staffDatabase.clear();
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",", 3);
                if (parts.length == 3) {
                    staffDatabase.put(parts[0].trim().toUpperCase(),
                            new StaffAccount(parts[0].trim(), parts[1].trim(), parts[2].trim()));
                }
            }
        } catch (IOException e) {
            loadDefaultStaff();
        }
    }

    private void loadDefaultStaff() {
        staffDatabase.put("MAS01", new StaffAccount("MAS01", "Bryan Lai", "admin123"));
        staffDatabase.put("MAS02", new StaffAccount("MAS02", "Dennis Boon", "mas2026"));
        staffDatabase.put("MAS03", new StaffAccount("MAS03", "Derrick Tan", "flight999"));
        staffDatabase.put("MAS04", new StaffAccount("MAS04", "Wang Chong Liang", "secure777"));
    }
}