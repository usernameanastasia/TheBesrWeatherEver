package forecast.usern.thebestweatherever.Model;

public class ForecastForHour {
    private int id;
    private String icon;
    private int temp;
    private String date;

    public ForecastForHour(int id, String icon, int temp, String date) {
        this.id = id;
        this.icon = icon;
        this.temp = temp;
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

    public int getTemp() {
        return temp;
    }

    public String getDate() {
        return date;
    }

}
