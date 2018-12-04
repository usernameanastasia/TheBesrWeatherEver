package forecast.usern.thebestweatherever.General;

import android.content.res.Resources;
import android.util.Log;

import forecast.usern.thebestweatherever.R;

public class IconAssistant {

    //thunderstorm
    public static final int THUNDERSTORM_WITH_LIGHT_RAIN = 200;
    public static final int THUNDERSTORM_WITH_RAIN = 201;
    public static final int THUNDERSTORM_WITH_HEAVY_RAIN = 202;
    public static final int LIGHT_THUNDERSTORM = 210;
    public static final int THUNDERSTORM = 211;
    public static final int HEAVY_THUNDERSTORM = 212;
    public static final int RAGGED_THUNDERSTORM = 221;
    public static final int THUNDERSTORM_WITH_LIGHT_DRIZZLE = 220;
    public static final int THUNDERSTORM_WITH_DRIZZLE = 231;
    public static final int THUNDERSTORM_WITH_HEAVY_DRIZZLE = 232;

    //drizzle
    public static final int LIGHT_INTENSITY_DRIZZLE = 300;
    public static final int DRIZZLE = 301;
    public static final int HEAVY_INTENSITY_DRIZZLE = 302;
    public static final int LIGHT_INTENSITY_DRIZZLE_RAIN = 310;
    public static final int DRIZZLE_RAIN = 311;
    public static final int HEAVY_INTENSITY_DRIZZLE_RAIN = 312;
    public static final int SHOWER_RAIN_AND_DRIZZLE = 313;
    public static final int HEAVY_SHOWER_RAIN_AND_DRIZZLE = 314;
    public static final int SHOWER_DRIZZLE = 321;

    //rain
    public static final int LIGHT_RAIN = 500;
    public static final int MODERATE_RAIN = 501;
    public static final int HEAVY_INTENSITY_RAIN = 502;
    public static final int VERY_HEAVY_RAIN = 503;
    public static final int EXTREME_RAIN = 504;
    public static final int FREEZING_RAIN = 511;
    public static final int LIGHT_INTENSITY_SHOWER_RAIN = 520;
    public static final int SHOWER_RAIN = 521;
    public static final int HEAVY_INTENSITY_SHOWER_RAIN = 522;
    public static final int RAGGED_SHOWER_RAIN = 531;

    //snow
    public static final int LIGHT_SNOW = 600;
    public static final int SNOW = 601;
    public static final int HEAVY_SNOW = 602;
    public static final int SLEET = 611;
    public static final int SHOWER_SLEET = 612;
    public static final int LIGHT_RAIN_AND_SNOW = 615;
    public static final int RAIN_AND_SNOW = 616;
    public static final int LIGHT_SHOWER_SNOW = 620;
    public static final int SHOWER_SNOW = 621;
    public static final int HEAVY_SHOWER_SNOW = 622;

    //atmosphere
    public static final int MIST = 701;
    public static final int SMOKE = 711;
    public static final int HAZE = 721;
    public static final int SAND_DUST = 731;
    public static final int FOG = 741;
    public static final int SAND = 751;
    public static final int DUST = 761;
    public static final int VOLCANIC_ASH = 762;
    public static final int SQUALLS = 771;
    public static final int TORNADO = 781;

    //clear
    public static final int CLEAR_SKY = 800;

    //clouds
    public static final int FEW_CLOUDS = 801;
    public static final int SCATTERED_CLOUDS = 802;
    public static final int BROKEN_CLOUDS = 803;
    public static final int OVERCAST_CLOUDS = 804;


    public static int getIconForWeather(int weatherId, String idImage, Resources resources) throws NoSuchFieldException, IllegalAccessException {

        idImage = String.valueOf(idImage.toCharArray()[idImage.length() - 1]);
        Log.d("IMG ID", idImage);

        if (weatherId >= THUNDERSTORM_WITH_LIGHT_RAIN && weatherId <= THUNDERSTORM_WITH_HEAVY_DRIZZLE){
            return R.drawable.id_200;
        }

        if (weatherId >= LIGHT_INTENSITY_DRIZZLE  && weatherId <= SHOWER_DRIZZLE){
            return R.drawable.id_302;
        }

        if ((weatherId >= LIGHT_RAIN && weatherId <= RAGGED_SHOWER_RAIN)){
            return R.drawable.id_501;
        }

        if (weatherId >= LIGHT_SNOW && weatherId <= RAIN_AND_SNOW){
            return R.drawable.id_511;
        }

        if (weatherId >= LIGHT_SHOWER_SNOW && weatherId <= HEAVY_SHOWER_SNOW){
            return R.drawable.id_602;
        }

        if (weatherId >= MIST && weatherId <= VOLCANIC_ASH){
            return R.drawable.id_711;
        }

        if (weatherId == TORNADO){
            return R.drawable.id_771;
        }

        if(weatherId == CLEAR_SKY){
            return  R.drawable.id_800;
        }

        if (weatherId >= FEW_CLOUDS && weatherId <= OVERCAST_CLOUDS){
            return R.drawable.id_803;
        }

        return R.drawable.class.getField("id_" + weatherId + idImage).getInt(resources);

    }
}
