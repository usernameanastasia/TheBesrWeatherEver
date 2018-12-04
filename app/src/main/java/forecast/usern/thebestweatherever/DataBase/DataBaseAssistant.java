package forecast.usern.thebestweatherever.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

// https://gist.github.com/janishar/903ed8eaedcfa2f5ca1c9fa6207fd93d

public class DataBaseAssistant extends DaoMaster.OpenHelper {
    public DataBaseAssistant(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion){
            case 1:
            case 2:
        }
    }
}
