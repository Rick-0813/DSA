import java.util.Objects;

public class FlightNode {
    private String airportName;

    //constructor
    public FlightNode(String airportName) {
        this.airportName = (airportName != null) ? airportName.trim() : "";
    }

    //getter
    public String getAirportName() {
        return airportName;
    }

    //setter
    public void setAirportName(String airportName) {
        if (airportName != null) {
            this.airportName = airportName.trim();
        }
    }

    @Override
    public boolean equals(Object obj) {
        //use to check the obj pass in is equal to something or not
        //use object as parameter so it can pass any thing include scanner or FlightNode
        if (this == obj) return true;
        // check the obj is null or the obj belongs to a different class
        if (obj == null || getClass() != obj.getClass()) return false;
        //after verify the obj call it that
        FlightNode that = (FlightNode) obj;
        return airportName.equalsIgnoreCase(that.airportName);
    }

    @Override
    public int hashCode() {
        //use to generate a hash code by using the airport name
        return Objects.hash(airportName.toLowerCase());
    }

    @Override
    public String toString() {
        //return the airport name
        return airportName;
    }
}