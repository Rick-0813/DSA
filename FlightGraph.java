import java.util.*;

public class FlightGraph {
    //key string store airport
    //value , LinkedList<String> store the airport can link to where
    private Map<String, LinkedList<String>> adjList;

    public FlightGraph() {
        //create a new and empty HashMap
        //load the default data
        this.adjList = new HashMap<>();
        loadDefaultData();
    }

    public Set<String> getAllAirports() {
        //.keySet() use to store all of the key and return it as a set
        return adjList.keySet();
    }

    public void bfsTraversal(String startAirport) {
        if (startAirport == null || startAirport.trim().isEmpty()) {
            //check is the startAirport passed in is empty or not
            System.out.println("[Error] Starting airport cannot be blank.");
            return;
        }
        startAirport = startAirport.trim();
        
        String matchedAirport = null;
        //find the airport name by using equalsIgnoreCase
        for (String airport : adjList.keySet()) {
            if (airport.equalsIgnoreCase(startAirport)) {
                matchedAirport = airport;
                break;
            }
        }

        //error message displayed there is not any airport is match
        if (matchedAirport == null) {
            System.out.println("\n[Error] Starting airport '" + startAirport + "' not found in the flight network.");
            return;
        }

        // queue: stores airports waiting  for process
        //visited use t0 record airports that have already been added to the queue
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
            //level size record how many airport need to continue
            int levelSize = queue.size();
            StringBuilder levelOutput = new StringBuilder();
            
            //confirm the different level with different output
            if (level == 0) {
                levelOutput.append("Level 0 [Origin]       : ");
            } else if (level == 1) {
                levelOutput.append("Level 1 [Direct Flight]: ");
            } else {
                levelOutput.append("Level ").append(level).append(" [").append(level - 1).append(" Stop Transit]: ");
            }

            for (int i = 0; i < levelSize; i++) {
                //.poll return the first value form queue and delete it from queue
                String current = queue.poll();
                if (level > 0) {
                    levelOutput.append("[").append(current).append("] ");
                } else {
                    levelOutput.append("[").append(current).append("]");
                }

                // traverse all airports directly linked with the current airport
                //.get can get the value for the key
                for (String neighbor : adjList.get(current)) {
                    if (!visited.contains(neighbor)) {
                        //if the neighbor has not visited add to the queue
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

    //add airport
    public void addAirport(String airport) {
        //check the airport already exist or not is yes then show the error message
        if (adjList.containsKey(airport)) {
            System.out.println("[Error] Airport '" + airport + "' already exists!");
        } else {
            // add the new airport to the adjList add store with a new empty LinkedList
            adjList.put(airport, new LinkedList<>());
            System.out.println("[Success] Airport '" + airport + "' added.");
        }
    }

    //remove airport
    public void removeAirport(String airport) {
        //find the airport if does not exist then show the error message
        if (!adjList.containsKey(airport)) {
            System.out.println("[Error] Airport '" + airport + "' not found!");
            return;
        }

        //first delete the airport name form the adjList which mean the key
        adjList.remove(airport);
        
        // Loop through all remaining airports and remove any flight routes connected to the deleted airport
        //.values() is used to get all of the linked lists from the adjList dictionary
        //routes all of the airport linked with the current airport
        for (LinkedList<String> routes : adjList.values()) {
            routes.remove(airport);
        }
        System.out.println("[Success] Airport '" + airport + "' and all its connected routes have been removed.");
    }

    //add route
    public void addRoute(String source, String destination) {
        //make sure the two airport want the add the route are exist
        if (!adjList.containsKey(source) || !adjList.containsKey(destination)) {
            System.out.println("[Error] One or both airports do not exist!");
            return;
        }
        //check is the source already contain the destination if not then add the route source to destination and destination to source
        if (!adjList.get(source).contains(destination)) {
            adjList.get(source).add(destination);
            adjList.get(destination).add(source);
            System.out.println("[Success] Flight route added between " + source + " and " + destination + ".");
        } else {
            System.out.println("[Error] This route already exists!");
        }
    }

    //remove route
    public void removeRoute(String source, String destination) {
        //check is the two airport is exist or not is not then show the error message
        if (!adjList.containsKey(source) || !adjList.containsKey(destination)) {
            System.out.println("[Error] One or both airports do not exist!");
            return;
        }
        
        //delete the route between both side
        //mean source is KL, destination is Penang , it delete KL to Penang and Penang to KL
        adjList.get(source).remove(destination);
        adjList.get(destination).remove(source);
        System.out.println("[Success] Flight route between " + source + " and " + destination + " removed.");
    }

    //show all the flight
    public void displayNetwork() {
        System.out.println("\n" + UIHelper.BORDER_LINE);
        UIHelper.printBoxRow("MAS COMPLETE FLIGHT NETWORK");
        System.out.println(UIHelper.BORDER_LINE);

        //check the adjList if it is empty show this error message
        if (adjList.isEmpty()) {
            UIHelper.printBoxRow("The flight network is currently empty.");
            System.out.println(UIHelper.BORDER_LINE);
            return;
        }

        int totalRoutes = 0; //use to calculate the totalRoute and unique routes
        
        //.entrySet use to take the data for the adjList with the key and value
        for (Map.Entry<String, LinkedList<String>> entry : adjList.entrySet()) {
            // airport store the airport name
            String airport = entry.getKey();
            //destinations store all of the destinations or routes that linked with the current airport
            LinkedList<String> destinations = entry.getValue();
            
            System.out.print(" [" + airport + "] connects to -> ");
            
            if (destinations.isEmpty()) {
                System.out.println("No outgoing flights.");
            } else {
                //String.join it can connect all of the item in the list with a ','
                System.out.println(String.join(", ", destinations));
                totalRoutes += destinations.size();
            }
        }
        
        System.out.println(UIHelper.BORDER_LINE);
        //unique route require to divide 2
        //the totalRoutes include KL to Penang , and Penang to KL ,but both calculate as one route
        UIHelper.printBoxRow("Total Airports: " + adjList.size() + " | Total Unique Routes: " + (totalRoutes / 2));
        System.out.println(UIHelper.BORDER_LINE);
    }

    //find the specific airport to another aiport
    public void findPathBFS(String startAirport, String endAirport) {
        //get the correct start and end airport name
        String start = getExactAirportName(startAirport);
        String end = getExactAirportName(endAirport);

        //if one of them does not exist then show a error message
        if (start == null || end == null) {
            System.out.println("\n[Error] One or both airports do not exist in the MAS network!");
            return;
        }

        //check the start and end destination is different
        if (start.equals(end)) {
            System.out.println("\n[Info] You are already at the destination!");
            return;
        }

        //queue use to store other airport which are queuing
        Queue<String> queue = new LinkedList<>();
        //visited use to record the airport are  already traverse
        Set<String> visited = new HashSet<>();
        //store path from start to find the end
        Map<String, String> parentMap = new HashMap<>();

        //.offer add the start to the last element in queue
        //.add add the start to the visited
        queue.offer(start);
        visited.add(start);
        boolean found = false;

        //working this if the queue is not empty
        while (!queue.isEmpty()) {
            String current = queue.poll();

            //check is it the final destination
            if (current.equals(end)) {
                found = true;
                break;
            }

            //check the neighbor airport
            for (String neighbor : adjList.get(current)) {
                if (!visited.contains(neighbor)) {
                    //add it to visited and queue
                    visited.add(neighbor);
                    queue.offer(neighbor);
                    //add the neighbor and current airport
                    // Record where the neighbor came from
                    // Key = neighbor airport (next stop), value is current airport (previous stop)
                    parentMap.put(neighbor, current);
                }
            }
        }

        System.out.println("\n" + UIHelper.BORDER_LINE);
        if (found) {

            //create a new LinkedList to store the path
            LinkedList<String> path = new LinkedList<>();
            //reverse to find the true path which mean start doing the path form end
            String step = end;
            
            while (step != null) {
                //add it to the first
                path.addFirst(step);
                //use it as key to find the value
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

    // use to get the actual airport name
    private String getExactAirportName(String input) {
        //.keySet give all of the key data is the adjList
        for (String airport : adjList.keySet()) {
            if (airport.equalsIgnoreCase(input)) {
                return airport;
            }
        }
        //if can not found then return null
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