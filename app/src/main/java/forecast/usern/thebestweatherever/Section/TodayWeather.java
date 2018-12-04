package forecast.usern.thebestweatherever.Section;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import forecast.usern.thebestweatherever.Controller.ForecastControllerForDay;
import forecast.usern.thebestweatherever.Controller.ForecastControllerForHour;
import forecast.usern.thebestweatherever.DataBase.Places;
import forecast.usern.thebestweatherever.General.General;
import forecast.usern.thebestweatherever.General.IconAssistant;
import forecast.usern.thebestweatherever.R;
import forecast.usern.thebestweatherever.Retrofit.ApiInterface;
import forecast.usern.thebestweatherever.Retrofit.RetrofitClient;
import forecast.usern.thebestweatherever.Model.AllPasringResult;
import forecast.usern.thebestweatherever.Model.ParseForecastResult;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


public class TodayWeather extends Fragment {

    private static final int LAYOUT = R.layout.today_weather;
    public static final String CURRENT_LOCATION = "1";
    public static final String OTHER_LOCATION = "2";

    private Places place;
    private String displayMode;
    private ScrollView informationDay;
    private ImageView weatherImage;
    private TextView idName, idDay, idHumidity, idSunset, idSunrise, idTemp, idPressure, idWeendSpeed, idDescription;
    private LinearLayout generalWeather;
    private RecyclerView forecastForHour;
    private RecyclerView forecastForDay;
    private CompositeDisposable compositeDisposable;
    private ApiInterface apiInterface;


    public TodayWeather() {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);

        generalWeather = (LinearLayout) view.findViewById(R.id.general_weather);
        weatherImage = (ImageView) view.findViewById(R.id.weather_image);
        idName = (TextView) view.findViewById(R.id.id_city_name);
        idDay = (TextView) view.findViewById(R.id.id_day);
        idHumidity = (TextView) view.findViewById(R.id.id_humidity);
        idSunrise = (TextView) view.findViewById(R.id.id_sunrise);
        idSunset = (TextView) view.findViewById(R.id.id_sunset);
        idTemp = (TextView) view.findViewById(R.id.id_temp);
        idPressure = (TextView) view.findViewById(R.id.id_pressure);
        idDescription = (TextView) view.findViewById(R.id.id_description);
        idWeendSpeed = (TextView) view.findViewById(R.id.id_wind_speed);
        informationDay = (ScrollView) view.findViewById(R.id.information_day);
        forecastForHour = (RecyclerView) view.findViewById(R.id.forecast_for_hour);
        forecastForHour.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        forecastForDay = (RecyclerView) view.findViewById(R.id.forecast_for_day);
        forecastForDay.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        updateDataBase();
        return view;
    }

    public void updateDataBase(){
        if (displayMode.equals(OTHER_LOCATION) && place != null){
            weatherInformation(place.getLatitude(), place.getLongitude());
            getWeatherInformationForCity(place.getLatitude(), place.getLongitude());
            Log.d("Geopoint", place.getLatitude() + " " + place.getLongitude());

        } else if (displayMode.equals(CURRENT_LOCATION)){
            weatherInformation(General.CURRENT_LOCATION.getLatitude(), General.CURRENT_LOCATION.getLongitude());
            getWeatherInformationForCity(General.CURRENT_LOCATION.getLatitude(), General.CURRENT_LOCATION.getLongitude());
        }
    }

    public static TodayWeather getPage(Places place, String displayMode){
        Bundle bundle = new Bundle();
        TodayWeather todayWeather = new TodayWeather();
        todayWeather.place = place;
        todayWeather.displayMode= displayMode;
        todayWeather.setArguments(bundle);
        return todayWeather;
    }

    @NonNull
    @Override
    public String toString() {
        return "TodayWeather: " + "place: " + place.getName();
    }

    private void weatherInformation(double latitude, double longitude) {
        compositeDisposable.add(apiInterface.getWeatherByLatLng(String.valueOf(latitude), String.valueOf(longitude), General.APP_ID, "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ParseForecastResult>() {
                    @Override
                    public void accept(ParseForecastResult weatherResult) throws Exception {
                        if (isAdded() && getActivity() != null) {
                            weatherImage.setImageResource(IconAssistant.getIconForWeather(weatherResult.getWeather().get(0).getId(),
                                    weatherResult.getWeather().get(0).getIcon(),
                                    getResources()));
                            idName.setText(weatherResult.getName());
                            idDescription.setText(weatherResult.getWeather().get(0).getDescription());
                            idTemp.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getTemp())).append("°C"));
                            idDay.setText(General.convertUnixToDate(weatherResult.getDt()));
                            idPressure.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getPressure())).append(" hPa"));
                            idHumidity.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getHumidity())).append(" %"));
                            idSunrise.setText(General.convertUnixToHourAndMinute(weatherResult.getSys().getSunrise()));
                            idSunset.setText(General.convertUnixToHourAndMinute(weatherResult.getSys().getSunset()));
                            idWeendSpeed.setText(new StringBuilder(String.valueOf(weatherResult.getWind().getSpeed())).append(" m/s"));
                            generalWeather.setVisibility(View.VISIBLE);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getActivity(), ""+throwable.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("Err", "" + throwable.getMessage());
                    }
                })
        );
    }

    private void getWeatherInformationForCity(double latitude, double longitude){
        compositeDisposable.add(apiInterface.getForecastWeatherByLatLng(String.valueOf(latitude), String.valueOf(longitude), General.APP_ID, "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AllPasringResult>() {
                    @Override
                    public void accept(AllPasringResult weatherForecastResult) throws Exception {
                        ForecastControllerForHour forecastControllerForHour = new ForecastControllerForHour(getContext(), weatherForecastResult);
                        forecastForHour.setAdapter(forecastControllerForHour);

                        ForecastControllerForDay forecastControllerForDay = new ForecastControllerForDay(getContext(), weatherForecastResult);
                        forecastForDay.setAdapter(forecastControllerForDay);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getActivity(), ""+throwable.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("Err", "" + throwable.getMessage());
                    }
                }));
    }


    public Places getPlace() {
        return place;
    }

}
