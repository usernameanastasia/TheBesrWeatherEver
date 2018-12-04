package forecast.usern.thebestweatherever.Model;

public class Global {
    private double temp;
    private double pressure;
    private int humidity;

    public Global() {
    }

    public int getTemp() {
        return (int)temp;
    }

    public int getPressure() {
        return (int)pressure;
    }

    public int getHumidity() {
        return humidity;
    }


}
