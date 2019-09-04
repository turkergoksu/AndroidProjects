package com.turkergoksu.weatherforecast;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class WeatherController extends AppCompatActivity {

    final int REQUEST_CODE = 200;

    // Base url for making a 5 day weather request.
    final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/forecast";

    // App ID to use OpenWeatherMap.
    final String APP_ID = getResources().getString(R.string.app_id);

    // Time between location updates(5000ms = 5seconds).
    final long MIN_TIME = 5000;

    // Distance between location updates(1000m = 1km).
    final float MIN_DISTANCE = 1000;

    String LOCATION_PROVIDER = LocationManager.GPS_PROVIDER;

    TextView mCity;

    List<TextView> mDateTextViews = new ArrayList<>();
    List<TextView> mTemperatureTextViews = new ArrayList<>();
    List<ImageView> mWeatherImageViews = new ArrayList<>();

    LocationManager mLocationManager;
    LocationListener mLocationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_controller_layout);

        ImageButton changeCityButton = (ImageButton) findViewById(R.id.changeCityButton);
        mCity = (TextView) findViewById(R.id.location);

        mDateTextViews.add(0, (TextView) findViewById(R.id.date));
        mTemperatureTextViews.add(0, (TextView) findViewById(R.id.temperature));
        mWeatherImageViews.add(0, (ImageView) findViewById(R.id.weatherImage));

        mDateTextViews.add(1, (TextView) findViewById(R.id.dateAfterDay1));
        mTemperatureTextViews.add(1, (TextView) findViewById(R.id.temperatureAfterDay1));
        mWeatherImageViews.add(1, (ImageView) findViewById(R.id.weatherImageAfterDay1));

        mDateTextViews.add(2, (TextView) findViewById(R.id.dateAfterDay2));
        mTemperatureTextViews.add(2, (TextView) findViewById(R.id.temperatureAfterDay2));
        mWeatherImageViews.add(2, (ImageView) findViewById(R.id.weatherImageAfterDay2));

        mDateTextViews.add(3, (TextView) findViewById(R.id.dateAfterDay3));
        mTemperatureTextViews.add(3, (TextView) findViewById(R.id.temperatureAfterDay3));
        mWeatherImageViews.add(3, (ImageView) findViewById(R.id.weatherImageAfterDay3));

        mDateTextViews.add(4, (TextView) findViewById(R.id.dateAfterDay4));
        mTemperatureTextViews.add(4, (TextView) findViewById(R.id.temperatureAfterDay4));
        mWeatherImageViews.add(4, (ImageView) findViewById(R.id.weatherImageAfterDay4));


        changeCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changeCityIntent = new Intent(WeatherController.this, ChangeCityController.class);
                startActivity(changeCityIntent);
            }
        });
    }

    // TODO: Understand what myIntent is
    @Override
    protected void onResume() {
        super.onResume();

        Intent changeCityIntent = getIntent();
        String city = changeCityIntent.getStringExtra("City");

        if (city != null){
            getWeatherForNewCity(city);

        } else {
            getWeatherForCurrentLocation();
        }
    }

    private void getWeatherForNewCity(String city) {
        RequestParams params = new RequestParams();
        params.put("q", city);
        params.put("appid", APP_ID);

        letsDoSomeNetworking(params);
    }

    private void getWeatherForCurrentLocation() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("WF", "Getting weather for current location");
                String longitude = String.valueOf(location.getLongitude());
                String latitude = String.valueOf(location.getLatitude());

                Log.d("WF", "longitude is: " + longitude);
                Log.d("WF", "latitude is: " + latitude);

                RequestParams params = new RequestParams();
                params.put("appid", APP_ID);
                params.put("lat", latitude);
                params.put("lon", longitude);
                letsDoSomeNetworking(params);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.d("WF", "onStatusChanged()");
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.d("WF", "onProviderEnabled()");
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.d("WF", "onProviderDisabled()");
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        mLocationManager.requestLocationUpdates(LOCATION_PROVIDER, MIN_TIME, MIN_DISTANCE, mLocationListener);
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, mLocationListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("WF", "onRequestPermissionsResult(): Permission granted!");
                getWeatherForCurrentLocation();
            } else {
                // Permission denied.
                Log.d("WF", "Permission denied!");
            }
        }
    }

    private void letsDoSomeNetworking(final RequestParams params){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(WEATHER_URL, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                Log.d("WF", "Success! JSON: " + response.toString());
                WeatherDataModel weatherData = WeatherDataModel.fromJson(response);
                updateUI(weatherData);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response){
                Log.e("WF", params.toString());
                Log.e("WF", "Fail " + e.toString());
                Log.d("WF", "Status code " + statusCode);
                Toast.makeText(WeatherController.this, "Request Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateUI(WeatherDataModel weatherData){
        mCity.setText(weatherData.getCity());

        for (int i=0;i<5;i++){
            mDateTextViews.get(i).setText(weatherData.getDates().get(i));
            mTemperatureTextViews.get(i).setText(weatherData.getTemperatures().get(i) + "°");
            mWeatherImageViews.get(i).setImageResource(getResources().getIdentifier(weatherData.getIconNames()
                    .get(i), "drawable", getPackageName()));
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mLocationManager != null){
            mLocationManager.removeUpdates(mLocationListener);
        }
    }
}
