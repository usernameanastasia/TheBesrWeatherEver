package forecast.usern.thebestweatherever.Model;

public class Weather {
    private int id;
    private String description;
    private String icon;

    public Weather() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
