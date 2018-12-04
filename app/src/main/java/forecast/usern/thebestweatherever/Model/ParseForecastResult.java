package forecast.usern.thebestweatherever.Model;

import java.util.List;

public class ParseForecastResult {
    private Geopoint coord;
    private List<Weather> weather;
    private Global main;
    private WindSpeed wind;
    private int dt;
    private CityDescription sys;
    private int id;
    private String name;

    public ParseForecastResult() {
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public Global getMain() {
        return main;
    }

    public WindSpeed getWind() {
        return wind;
    }


    public int getDt() {
        return dt;
    }

    public CityDescription getSys() {
        return sys;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
