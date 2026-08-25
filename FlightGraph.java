import java.util.*;

public class FlightGraph {
    private Map<String, LinkedList<String>> adjList;

    public FlightGraph() {
        this.adjList = new HashMap<>();
        loadDefaultData();
    }

    public Set<String> getAllAirports() {
        return adjList.keySet();
    }

    public void bfsTraversal(String startAirport) {
        if (startAirport == null || startAirport.trim().isEmpty()) {
            System.out.println("[Error] Starting airport cannot be blank.");
            return;
        }
        startAirport = startAirport.trim();
        
        String matchedAirport = null;
        for (String airport : adjList.keySet()) {
            if (airport.equalsIgnoreCase(startAirport)) {
                matchedAirport = airport;
                break;
            }
        }

        if (matchedAirport == null) {
            System.out.println("\n[Error] Starting airport '" + startAirport + "' not found in the flight network.");
            return;
        }

        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        visited.add(matchedAirport);
        queue.offer(matchedAirport);

        System.out.println("\n" + UIHelper.BORDER_LINE);
        UIHelper.printBoxRow("BREADTH-FIRST SEARCH (BFS) FLIGHT CONNECTIVITY REPORT");
        UIHelper.printBoxRow("Origin Hub : " + matchedAirport);
        System.out.println(UIHelper.BORDER_LINE);

        int level = 0;
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            StringBuilder levelOutput = new StringBuilder();
            
            if (level == 0) {
                levelOutput.append("Level 0 [Origin]       : ");
            } else if (level == 1) {
                levelOutput.append("Level 1 [Direct Flight]: ");
            } else {
                levelOutput.append("Level ").append(level).append(" [").append(level - 1).append(" Stop Transit]: ");
            }

            for (int i = 0; i < levelSize; i++) {
                String current = queue.poll();
                if (level > 0) {
                    levelOutput.append("[").append(current).append("] ");
                } else {
                    levelOutput.append("[").append(current).append("]");
                }

                for (String neighbor : adjList.get(current)) {
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        queue.offer(neighbor);
                    }
                }
            }
            UIHelper.printBoxRow(levelOutput.toString());
            level++;
        }
        System.out.println(UIHelper.BORDER_LINE);
        UIHelper.printBoxRow("Total Connected Destinations : " + (visited.size() - 1) + " airport(s)");
        UIHelper.printBoxRow("Algorithm Time Complexity    : O(V + E) | Space Complexity: O(V)");
        System.out.println(UIHelper.BORDER_LINE);
    }

    // [BRYAN LAI'S FUNCTIONS - COMING SOON]
    public void addAirport(String airport) {
        UIHelper.printComingSoonBanner("Add Vertex (Airport)", "Bryan Lai Xuan Qi");
    }

    public void removeAirport(String airport) {
        UIHelper.printComingSoonBanner("Remove Vertex (Airport)", "Bryan Lai Xuan Qi");
    }

    public void addRoute(String source, String destination) {
        UIHelper.printComingSoonBanner("Add Edge (Flight Route)", "Bryan Lai Xuan Qi");
    }

    public void removeRoute(String source, String destination) {
        UIHelper.printComingSoonBanner("Remove Edge (Flight Route)", "Bryan Lai Xuan Qi");
    }

    public void displayNetwork() {
        UIHelper.printComingSoonBanner("View Complete MAS Flight Network", "Wang Chong Liang");
    }

    private void loadDefaultData() {
        String[] defaultAirports = {
            "Kuala Lumpur", "Penang", "Kota Kinabalu",
            "Kuching", "Johor Bahru", "Langkawi", "Miri", "Sandakan"
        };
        for (String airport : defaultAirports) {
            adjList.put(airport, new LinkedList<>());
        }

        adjList.get("Kuala Lumpur").addAll(Arrays.asList("Penang", "Johor Bahru", "Kota Kinabalu", "Kuching", "Langkawi"));
        adjList.get("Penang").addAll(Arrays.asList("Kuala Lumpur", "Langkawi"));
        adjList.get("Johor Bahru").addAll(Arrays.asList("Kuala Lumpur", "Kuching"));
        adjList.get("Langkawi").addAll(Arrays.asList("Kuala Lumpur", "Penang"));
        adjList.get("Kuching").addAll(Arrays.asList("Kuala Lumpur", "Johor Bahru", "Miri", "Kota Kinabalu"));
        adjList.get("Miri").addAll(Arrays.asList("Kuching", "Kota Kinabalu"));
        adjList.get("Kota Kinabalu").addAll(Arrays.asList("Kuala Lumpur", "Kuching", "Miri", "Sandakan"));
        adjList.get("Sandakan").addAll(Arrays.asList("Kota Kinabalu"));
    }
}