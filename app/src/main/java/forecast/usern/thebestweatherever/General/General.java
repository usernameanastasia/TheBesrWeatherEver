package forecast.usern.thebestweatherever.General;

import android.location.Location;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class General {
    public static final String APP_ID = "7403247a0fad217b079f0bd58e16fafe";
    public static String API_LINK = "http://api.openweathermap.org/data/2.5/weather";
    public static Location CURRENT_LOCATION = null;
    public static String DAY_TIME = "14";
    public static String NIGHT_TIME = "02";

    public static String convertUnixToDate(int dt) {
        Date date = new Date(dt*1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, d MMMM");
        return simpleDateFormat.format(date);
    }

    public static String convertUnixToHour(int dt) {
        Date date = new Date(dt*1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
        return simpleDateFormat.format(date);
    }

    public static String convertUnixToHourAndMinute(int dt) {
        Date date = new Date(dt*1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(date);
    }

    public static String convertUnixToWeekDay(int dt){
        Date date = new Date(dt*1000L);
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
        return dayFormat.format(date);
    }

    public static String getCurrentWeekDay(){
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("EEEE");
        return dateFormat.format(date);
    }
}
