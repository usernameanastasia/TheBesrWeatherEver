package forecast.usern.thebestweatherever.Model;

public class ForecastForDay {
    private int id;
    private String icon;
    private int maximumTemp;
    private int minimumTemp;
    private int date;

    public ForecastForDay(){}

    public ForecastForDay(int id, String icon, int maximumTemp, int minimumTemp, int date) {
        this.id = id;
        this.icon = icon;
        this.maximumTemp = maximumTemp;
        this.minimumTemp = minimumTemp;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getMaximumTemp() {
        return maximumTemp;
    }

    public int getMinimumTemp() {
        return minimumTemp;
    }

    public int getDate() {
        return date;
    }

}
