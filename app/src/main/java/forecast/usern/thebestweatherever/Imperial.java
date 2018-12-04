package forecast.usern.thebestweatherever;

import android.app.Application;

import forecast.usern.thebestweatherever.DataBase.DaoMaster;
import forecast.usern.thebestweatherever.DataBase.DaoSession;
import forecast.usern.thebestweatherever.DataBase.DataBaseAssistant;

public class Imperial extends Application {

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        daoSession = new DaoMaster(new DataBaseAssistant(this, "places.db").getWritableDb()).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
