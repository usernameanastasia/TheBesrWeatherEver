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
import forecast.usern.thebestweatherever.Model.ForecastForDay;
import forecast.usern.thebestweatherever.Model.AllPasringResult;
import forecast.usern.thebestweatherever.Model.Forecast;

import java.util.ArrayList;
import java.util.List;

public class ForecastControllerForDay extends RecyclerView.Adapter<ForecastControllerForDay.ViewRepresentative> {

    class ViewRepresentative extends RecyclerView.ViewHolder{
        private TextView idDate, idMaxTemp, idMinTemp;
        private ImageView idWeatherImg;
        private View view;

        ViewRepresentative(View view) {
            super(view);
            this.view = view;

            idDate = (TextView)view.findViewById(R.id.id_date);
            idMaxTemp = (TextView)view.findViewById(R.id.id_max_temp);
            idMinTemp = (TextView)view.findViewById(R.id.id_min_temp);
            idWeatherImg = (ImageView)view.findViewById(R.id.id_weather_img);
        }

        View getItemView() {
            return view;
        }
    }

    private Context context;
    private AllPasringResult allPasringResult;
    private List<ForecastForDay> forecastForDays;

    public ForecastControllerForDay(Context context, AllPasringResult allPasringResult) {
        this.context = context;
        this.allPasringResult = allPasringResult;
        forecastForDays = new ArrayList<>();

        putForecastForDay();
    }

    private void putForecastForDay(){
        int maxTemp;
        int minTemp = 0;
        for (Forecast forecastList : allPasringResult.getList()){
            if (!General.convertUnixToWeekDay(forecastList.getDt()).equals(General.getCurrentWeekDay())){
                if (General.convertUnixToHour(forecastList.getDt()).equals(General.NIGHT_TIME)){
                    minTemp = forecastList.getMain().getTemp();
                }

                if (General.convertUnixToHour(forecastList.getDt()).equals(General.DAY_TIME)){
                    maxTemp = forecastList.getMain().getTemp();
                    forecastForDays.add(new ForecastForDay(forecastList.getWeather().get(0).getId(),
                            forecastList.getWeather().get(0).getIcon(),
                            maxTemp,
                            minTemp,
                            forecastList.getDt()));
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return forecastForDays.size();
    }

    @Override
    public ViewRepresentative onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.forecast_for_day, viewGroup, false);

        return new ViewRepresentative(view);
    }

    @Override
    public void onBindViewHolder(ViewRepresentative viewRepresentative, int stance) {

        try {
            viewRepresentative.idWeatherImg.setImageResource(IconAssistant.getIconForWeather(forecastForDays.get(stance).getId(),
                    forecastForDays.get(stance).getIcon(),
                    viewRepresentative.getItemView().getResources()));

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        viewRepresentative.idDate.setText(General.convertUnixToDate(forecastForDays.get(stance).getDate()));
        viewRepresentative.idMaxTemp.setText(String.valueOf(forecastForDays.get(stance).getMaximumTemp()));
        viewRepresentative.idMinTemp.setText(String.valueOf(forecastForDays.get(stance).getMinimumTemp()));
    }

}
