package forecast.usern.thebestweatherever.Retrofit;

import forecast.usern.thebestweatherever.Model.AllPasringResult;
import forecast.usern.thebestweatherever.Model.ParseForecastResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("weather")
    Observable<ParseForecastResult> getWeatherByLatLng(@Query("lat") String lat,
                                                       @Query("lon") String lng,
                                                       @Query("appid") String appid,
                                                       @Query("units") String unit
    );

    @GET("forecast")
    Observable<AllPasringResult> getForecastWeatherByLatLng(@Query("lat") String lat,
                                                            @Query("lon") String lng,
                                                            @Query("appid") String appid,
                                                            @Query("units") String unit
    );
}
