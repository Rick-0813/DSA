
public class UIHelper {
    public static final String BORDER_LINE = "+====================================================================================================+";

    public static void printMASBanner() {
        System.out.println(BORDER_LINE);
        System.out.println("|                                                                                                    |");
        System.out.println("|   /$$      /$$  /$$$$$$   /$$$$$$        /$$$$$$$$ /$$       /$$$$$$  /$$   /$$ /$$$$$$$$          |");
        System.out.println("|  | $$$    /$$$ /$$__  $$ /$$__  $$      | $$_____/| $$      |_  $$_/ | $$  /$$/|__  $$__/          |");
        System.out.println("|  | $$$$  /$$$$| $$  \\ $$| $$  \\__/      | $$      | $$        | $$   | $$ /$$/    | $$             |");
        System.out.println("|  | $$ $$/$$ $$| $$$$$$$$|  $$$$$$       | $$$$$   | $$        | $$   | $$$$$/     | $$             |");
        System.out.println("|  | $$  $$$| $$| $$__  $$ \\____  $$      | $$__/   | $$        | $$   | $$  $$     | $$             |");
        System.out.println("|  | $$\\  $ | $$| $$  | $$ /$$  \\ $$      | $$      | $$        | $$   | $$\\  $$    | $$             |");
        System.out.println("|  | $$ \\/  | $$| $$  | $$|  $$$$$$/      | $$      | $$$$$$$$ /$$$$$$ | $$ \\  $$   | $$             |");
        System.out.println("|  |__/     |__/|__/  |__/ \\______/       |__/      |________/|______/ |__/  \\__/   |__/             |");
        System.out.println("|                                                                                                    |");
        System.out.println("|                     -- MALAYSIA AIRLINES DOMESTIC FLIGHT NETWORK --                                |");
        System.out.println("|                                                                                                    |");
        System.out.println(BORDER_LINE);
    }

    public static void printSecurityBanner() {
        System.out.println(BORDER_LINE);
        System.out.println("|                                                                                                    |");
        System.out.println("|    /$$$$$$  /$$$$$$$$  /$$$$$$  /$$   /$$ /$$$$$$$  /$$$$$$ /$$$$$$$$ /$$     /$$                  |");
        System.out.println("|   /$$__  $$| $$_____/ /$$__  $$| $$  | $$| $$__  $$|_  $$_/|__  $$__/|  $$   /$$/                  |");
        System.out.println("|  | $$  \\__/| $$      | $$  \\__/| $$  | $$| $$  \\ $$  | $$     | $$    \\  $$ /$$/                   |");
        System.out.println("|  |  $$$$$$ | $$$$$   | $$      | $$  | $$| $$$$$$$/  | $$     | $$     \\  $$$$/                    |");
        System.out.println("|   \\____  $$| $$__/   | $$      | $$  | $$| $$__  $$  | $$     | $$      \\  $$/                     |");
        System.out.println("|   /$$  \\ $$| $$      | $$    $$| $$  | $$| $$  \\ $$  | $$     | $$       | $$                      |");
        System.out.println("|  |  $$$$$$/| $$$$$$$$|  $$$$$$/|  $$$$$$/| $$  | $$ /$$$$$$   | $$       | $$                      |");
        System.out.println("|   \\______/ |________/ \\______/  \\______/ |__/  |__/|______/   |__/       |__/                      |");
        System.out.println("|                                                                                                    |");
        System.out.println("|                   -- AUTHORIZED MAS STAFF ACCESS PORTAL ONLY --                                    |");
        System.out.println("|                                                                                                    |");
        System.out.println(BORDER_LINE);
    }

    public static void printBoxRow(String content) {
        // set total width for UI
        int totalWidth = 100;
        // check the content size, if too long truncate it and add "..."
        if (content.length() > totalWidth - 4) {
            content = content.substring(0, totalWidth - 7) + "...";
        }
        //calculate how many space need to fill
        int padding = totalWidth - content.length() - 2;
        //set the format for the row
        StringBuilder sb = new StringBuilder();
        sb.append("|  ").append(content);
        //use for loop to append " " to align the right border
        for (int i = 0; i < padding; i++) {
            sb.append(" ");
        }
        sb.append("|");
        System.out.println(sb.toString());
    }

}