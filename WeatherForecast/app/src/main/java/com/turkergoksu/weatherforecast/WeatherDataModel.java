package com.turkergoksu.weatherforecast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WeatherDataModel {

    private String mCity;
    private List<String> mTemperatures = new ArrayList<>();
    private List<String> mIconNames = new ArrayList<>();
    private List<Integer> mConditions = new ArrayList<>();
    private List<String> mDates = new ArrayList<>();

    public static WeatherDataModel fromJson(JSONObject jsonObject){
        try{
            WeatherDataModel weatherData = new WeatherDataModel();
            weatherData.mCity = jsonObject.getJSONObject("city").getString("name");

            for (int i=0;i<5;i++){
                int conditions = jsonObject.getJSONArray("list").getJSONObject(i*8)
                        .getJSONArray("weather").getJSONObject(0).getInt("id");
                weatherData.mConditions.add(i, conditions);
                weatherData.mIconNames.add(i, updateWeatherIcon(weatherData.mConditions.get(i)));

                Calendar cal = Calendar.getInstance();
                String date = jsonObject.getJSONArray("list").getJSONObject(i*8)
                        .getString("dt_txt").split(" ")[0];
                // date format = YYYY-MM-DD
                weatherData.mDates.add(i, date.split("-")[2] + " " +
                        getMonth(Integer.valueOf(date.split("-")[1])) + ", " +
                        getDay((cal.get(Calendar.DAY_OF_WEEK) + i) % 7));


                double tempResult = jsonObject.getJSONArray("list").getJSONObject(i*8)
                        .getJSONObject("main").getDouble("temp") - 273.15;
                weatherData.mTemperatures.add(i, Integer.toString((int)Math.rint(tempResult)));
            }

            return weatherData;
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    private static String updateWeatherIcon(int condition) {

        if (condition >= 0 && condition < 300) {
            return "tstorm1";
        } else if (condition >= 300 && condition < 500) {
            return "light_rain";
        } else if (condition >= 500 && condition < 600) {
            return "shower3";
        } else if (condition >= 600 && condition <= 700) {
            return "snow4";
        } else if (condition >= 701 && condition <= 771) {
            return "fog";
        } else if (condition >= 772 && condition < 800) {
            return "tstorm3";
        } else if (condition == 800) {
            return "sunny";
        } else if (condition >= 801 && condition <= 804) {
            return "cloudy2";
        } else if (condition >= 900 && condition <= 902) {
            return "tstorm3";
        } else if (condition == 903) {
            return "snow5";
        } else if (condition == 904) {
            return "sunny";
        } else if (condition >= 905 && condition <= 1000) {
            return "tstorm3";
        }

        return "dunno";
    }

    private static String getDay(int dayNumber) {
        switch (dayNumber){
            case 1:
                return "Sun";
            case 2:
                return "Mon";
            case 3:
                return "Tue";
            case 4:
                return "Wed";
            case 5:
                return "Thu";
            case 6:
                return "Fri";
            default:
                return "Sat";
        }
    }

    private static String getMonth(int monthNumber) {
        switch (monthNumber){
            case 1:
                return "Jan";
            case 2:
                return "Feb";
            case 3:
                return "Mar";
            case 4:
                return "Apr";
            case 5:
                return "May";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Aug";
            case 9:
                return "Sep";
            case 10:
                return "Oct";
            case 11:
                return "Nov";
            case 12:
                return "Dec";
            default:
                return "Unk";
        }
    }

    public String getCity() {
        return mCity;
    }

    public List<String> getTemperatures() {
        return mTemperatures;
    }

    public List<String> getIconNames() {
        return mIconNames;
    }

    public List<String> getDates() {
        return mDates;
    }
}
