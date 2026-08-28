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

    // ==========================================
    // Fix 1: Add Airport (Vertex)
    // ==========================================
    public void addAirport(String airport) {
        String exactName = getExactAirportName(airport);
        
        if (exactName != null) {
            System.out.println("[Error] Airport '" + exactName + "' already exists!");
        } else {
            adjList.put(airport.trim(), new LinkedList<>());
            System.out.println("[Success] Airport '" + airport.trim() + "' added.");
        }
    }

    // ==========================================
    // Fix 2: Remove Airport (Vertex)
    // ==========================================
    public void removeAirport(String airport) {
        String exactName = getExactAirportName(airport);
        
        if (exactName == null) {
            System.out.println("[Error] Airport '" + airport + "' not found!");
            return;
        }

        adjList.remove(exactName);
        
        for (LinkedList<String> routes : adjList.values()) {
            routes.remove(exactName);
        }
        System.out.println("[Success] Airport '" + exactName + "' and all its connected routes have been removed.");
    }

    // ==========================================
    // Fix 3: Add Route (Edge)
    // ==========================================
    public void addRoute(String source, String destination) {
        String exactSource = getExactAirportName(source);
        String exactDest = getExactAirportName(destination);

        if (exactSource == null || exactDest == null) {
            System.out.println("[Error] One or both airports do not exist!");
            return;
        }
        
        if (!adjList.get(exactSource).contains(exactDest)) {
            adjList.get(exactSource).add(exactDest);
            adjList.get(exactDest).add(exactSource);
            System.out.println("[Success] Flight route added between " + exactSource + " and " + exactDest + ".");
        } else {
            System.out.println("[Error] This route already exists!");
        }
    }

    // ==========================================
    // Fix 4: Remove Route (Edge)
    // ==========================================
    public void removeRoute(String source, String destination) {
        String exactSource = getExactAirportName(source);
        String exactDest = getExactAirportName(destination);

        if (exactSource == null || exactDest == null) {
            System.out.println("[Error] One or both airports do not exist!");
            return;
        }
        
        if (adjList.get(exactSource).contains(exactDest)) {
            adjList.get(exactSource).remove(exactDest);
            adjList.get(exactDest).remove(exactSource);
            System.out.println("[Success] Flight route between " + exactSource + " and " + exactDest + " removed.");
        } else {
            System.out.println("[Error] Flight route does not exist!");
        }
    }

    public void displayNetwork() {
        System.out.println("\n" + UIHelper.BORDER_LINE);
        UIHelper.printBoxRow("MAS COMPLETE FLIGHT NETWORK");
        System.out.println(UIHelper.BORDER_LINE);

        if (adjList.isEmpty()) {
            UIHelper.printBoxRow("The flight network is currently empty.");
            System.out.println(UIHelper.BORDER_LINE);
            return;
        }

        int totalRoutes = 0; 
        
        for (Map.Entry<String, LinkedList<String>> entry : adjList.entrySet()) {
            String airport = entry.getKey();
            LinkedList<String> destinations = entry.getValue();
            
            System.out.print(" [" + airport + "] connects to -> ");
            
            if (destinations.isEmpty()) {
                System.out.println("No outgoing flights.");
            } else {
                System.out.println(String.join(", ", destinations));
                totalRoutes += destinations.size();
            }
        }
        
        System.out.println(UIHelper.BORDER_LINE);
        UIHelper.printBoxRow("Total Airports: " + adjList.size() + " | Total Unique Routes: " + (totalRoutes / 2));
        System.out.println(UIHelper.BORDER_LINE);
    }

    public void findPathBFS(String startAirport, String endAirport) {
        String start = getExactAirportName(startAirport);
        String end = getExactAirportName(endAirport);

        if (start == null || end == null) {
            System.out.println("\n[Error] One or both airports do not exist in the MAS network!");
            return;
        }

        if (start.equals(end)) {
            System.out.println("\n[Info] You are already at the destination!");
            return;
        }

        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        Map<String, String> parentMap = new HashMap<>();

        queue.offer(start);
        visited.add(start);
        boolean found = false;

        while (!queue.isEmpty()) {
            String current = queue.poll();

            if (current.equals(end)) {
                found = true;
                break;
            }

            for (String neighbor : adjList.get(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                    parentMap.put(neighbor, current);
                }
            }
        }

        System.out.println("\n" + UIHelper.BORDER_LINE);
        if (found) {
            LinkedList<String> path = new LinkedList<>();
            String step = end;
            
            while (step != null) {
                path.addFirst(step);
                step = parentMap.get(step);
            }

            UIHelper.printBoxRow("SHORTEST FLIGHT PATH FOUND!");
            UIHelper.printBoxRow("Total Transit(s) : " + (path.size() - 2) + " stop(s)");
            
            String routeString = String.join(" -> ", path);
            UIHelper.printBoxRow("Route: " + routeString);
            
        } else {
            UIHelper.printBoxRow("NO FLIGHT PATH AVAILABLE");
            UIHelper.printBoxRow("Sorry, cannot reach " + end + " from " + start + ".");
        }
        System.out.println(UIHelper.BORDER_LINE);
    }

    private String getExactAirportName(String input) {
        for (String airport : adjList.keySet()) {
            if (airport.equalsIgnoreCase(input)) {
                return airport;
            }
        }
        return null;
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