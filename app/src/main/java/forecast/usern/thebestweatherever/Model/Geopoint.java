package forecast.usern.thebestweatherever.Model;

public class Geopoint {
    private double lon;
    private double lat;

    public Geopoint() {
    }

    @Override
    public String toString() {
        return "Lat: " + this.lat + "Lon: "+ this.lon;
    }
}
