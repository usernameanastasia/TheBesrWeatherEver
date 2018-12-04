package forecast.usern.thebestweatherever.Model;

public class CityDescription {
    private int id;
    private int sunrise;
    private int sunset;

    public CityDescription() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSunrise() {
        return sunrise;
    }

    public int getSunset() {
        return sunset;
    }

}
