package forecast.usern.thebestweatherever.Controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import forecast.usern.thebestweatherever.General.General;
import forecast.usern.thebestweatherever.General.IconAssistant;
import forecast.usern.thebestweatherever.R;
import forecast.usern.thebestweatherever.Model.ForecastForHour;
import forecast.usern.thebestweatherever.Model.AllPasringResult;
import forecast.usern.thebestweatherever.Model.Forecast;

import java.util.ArrayList;
import java.util.List;

public class ForecastControllerForHour extends RecyclerView.Adapter<ForecastControllerForHour.ViewRepresentative> {

    class ViewRepresentative extends RecyclerView.ViewHolder{

        private TextView idTime, idTempeature;
        private ImageView idWeatherImage;
        private View view;

        ViewRepresentative(View view) {
            super(view);
            this.view = view;

            idTime = (TextView)view.findViewById(R.id.id_time);
            idTempeature = (TextView)view.findViewById(R.id.id_temperature);
            idWeatherImage = (ImageView)view.findViewById(R.id.id_weather_image);
        }

        View getItemView() {
            return view;
        }
    }

    private Context context;
    private AllPasringResult allPasringResult;
    private List<ForecastForHour> forecastForHours;

    public ForecastControllerForHour(Context context, AllPasringResult allPasringResult) {
        this.context = context;
        this.allPasringResult = allPasringResult;
        forecastForHours = new ArrayList<>();

        putForecastForHour();
    }

    private void putForecastForHour(){
        boolean isFirst = true;
        String hourNow = "";
        for (Forecast weatherList : allPasringResult.getList()){
            if (isFirst){
                forecastForHours.add(new ForecastForHour(weatherList.getWeather().get(0).getId(),
                        weatherList.getWeather().get(0).getIcon(),
                        weatherList.getMain().getTemp(),
                        "NOW"));
                hourNow = General.convertUnixToHour(weatherList.getDt());
                isFirst = false;
            } else {
                if (!hourNow.equals(General.convertUnixToHour(weatherList.getDt()))) {
                    forecastForHours.add(new ForecastForHour(weatherList.getWeather().get(0).getId(),
                            weatherList.getWeather().get(0).getIcon(),
                            weatherList.getMain().getTemp(),
                            General.convertUnixToHour(weatherList.getDt())));
                } else {
                    break;
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return forecastForHours.size();
    }

    @Override
    public ViewRepresentative onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.forecast_for_hours, viewGroup, false);

        return new ViewRepresentative(itemView);
    }

    @Override
    public void onBindViewHolder(ViewRepresentative viewRepresentative, int stance) {
        viewRepresentative.idTime.setText(forecastForHours.get(stance).getDate());

        try {
            viewRepresentative.idWeatherImage.setImageResource(IconAssistant.getIconForWeather(forecastForHours.get(stance).getId(),
                    forecastForHours.get(stance).getIcon(),
                    viewRepresentative.getItemView().getResources()));

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        viewRepresentative.idTempeature.setText(new StringBuilder(String.valueOf(forecastForHours.get(stance).getTemp())).append("°"));
    }


}
