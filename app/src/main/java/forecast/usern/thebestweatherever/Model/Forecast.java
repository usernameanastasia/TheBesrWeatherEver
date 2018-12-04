package forecast.usern.thebestweatherever.Model;

import java.util.List;

public class Forecast {

    private int dt;
    private Global main;
    private List<Weather> weather;

    public Forecast(){
    }
    public int getDt() {
        return dt;
    }

    public Global getMain() {
        return main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

}
