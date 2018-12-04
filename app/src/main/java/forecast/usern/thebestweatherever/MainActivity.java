package forecast.usern.thebestweatherever;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import forecast.usern.thebestweatherever.Controller.FlipController;
import forecast.usern.thebestweatherever.DataBase.DaoSession;
import forecast.usern.thebestweatherever.DataBase.Places;
import forecast.usern.thebestweatherever.General.General;
import forecast.usern.thebestweatherever.Section.TodayWeather;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final static int CITY_REQUEST_CODE = 1;

    private List<TextView> switches = new ArrayList<>();
    private DaoSession daoSession;
    private ImageButton addCityButt;
    private ImageButton deleteCityButt;
    private ViewPager viewPager;
    private FlipController flipController;
    private LinearLayout linearLayout;
    private RelativeLayout relativeLayout;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relativeLayout = (RelativeLayout) findViewById(R.id.root_view);
        launchDataBase();

        // Request
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            placeRequest();
                            placeCallback();
                        }
                        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
                        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        Snackbar.make(relativeLayout, "Permissions Denied", Snackbar.LENGTH_LONG)
                                .show();
                    }
                }).check();
    }


    private void launchDataBase(){
        daoSession = ((Imperial)getApplication()).getDaoSession();
    }

    // Request
    private void placeRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    //Callback
    private void placeCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                General.CURRENT_LOCATION = locationResult.getLastLocation();
                super.onLocationResult(locationResult);
                initializePage();
                initializeStartPlaces();
                launchSwitches(0);
                listenerAddCityButt();
                listenerDeleteCityButt();
            }
        };
    }

    @Override
    protected void onActivityResult(int idReqest, int idResult, Intent data) {
        if (idReqest == CITY_REQUEST_CODE) {
            if (idResult == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i("City", "Place: " + place.getName());
                addNewPlace(place);
                launchSwitches(flipController.getCount()-1);

            } else if (idResult == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.i("City", status.getStatusMessage());

            } else if (idResult == RESULT_CANCELED) {
            }
        }
    }

    private void initializePage() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        linearLayout = (LinearLayout) findViewById(R.id.switches);
        flipController = new FlipController(getSupportFragmentManager());
        addNewCity(new Places(), TodayWeather.CURRENT_LOCATION);

        viewPager.setAdapter(flipController);
        viewPager.addOnPageChangeListener(viewListener);
    }

    private void initializeStartPlaces(){
        List<Places> places = daoSession.getPlacesDao().loadAll();

        if (!places.isEmpty()){
            for (Places place : places){
                addNewCity(place, TodayWeather.OTHER_LOCATION);
            }
        }
    }

    private void addNewCity(Places place, String mode){
        flipController.addFragment(TodayWeather.getPage(place, mode));
        switches.add(new TextView(this));

        flipController.notifyDataSetChanged();
    }

    private void launchSwitches(int position) {
        linearLayout.removeAllViews();

        for (TextView textView : switches) {
            textView.setText(Html.fromHtml("&#10032"));
            textView.setTextSize(12);
            textView.setTextColor(getResources().getColor(R.color.star_color));
            linearLayout.addView(textView);
        }

        if (switches.size() > 0) {
            switches.get(position).setTextColor(getResources().getColor(R.color.stars_color));
        }
    }

    private void listenerAddCityButt() {
        addCityButt = (ImageButton) findViewById(R.id.addCityButt);
        addCityButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(MainActivity.this);
                    startActivityForResult(intent, CITY_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                } catch (GooglePlayServicesNotAvailableException e) {
                }
            }
        });
    }

    private void listenerDeleteCityButt(){
        deleteCityButt = (ImageButton) findViewById(R.id.deleteCityButt);

        deleteCityButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCity();
                launchSwitches(0);
            }
        });
    }

    private void addNewPlace(Place place){
        Places placeEntity = new Places(place.getName().toString(), place.getLatLng().longitude, place.getLatLng().latitude);
        daoSession.getPlacesDao().insert(placeEntity);
        addNewCity(placeEntity, TodayWeather.OTHER_LOCATION);
        viewPager.setCurrentItem(flipController.getCount());
    }

    private void deleteCity(){
        TodayWeather todayWeather = (TodayWeather) flipController.getItem(viewPager.getCurrentItem());
        daoSession.getPlacesDao().delete(todayWeather.getPlace());
        switches.remove(viewPager.getCurrentItem());
        flipController.removeFragment(viewPager.getCurrentItem());
        flipController.notifyDataSetChanged();
    }

    private void deleteButtonShowing(int position){
        if (position == 0){
            deleteCityButt.setVisibility(View.GONE);
        } else {
            deleteCityButt.setVisibility(View.VISIBLE);
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            launchSwitches(position);
            deleteButtonShowing(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };


}
