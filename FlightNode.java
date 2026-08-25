import java.util.Objects;

public class FlightNode {
    private String airportName;

    public FlightNode(String airportName) {
        this.airportName = (airportName != null) ? airportName.trim() : "";
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        if (airportName != null) {
            this.airportName = airportName.trim();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        FlightNode that = (FlightNode) obj;
        return airportName.equalsIgnoreCase(that.airportName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(airportName.toLowerCase());
    }

    @Override
    public String toString() {
        return airportName;
    }
}