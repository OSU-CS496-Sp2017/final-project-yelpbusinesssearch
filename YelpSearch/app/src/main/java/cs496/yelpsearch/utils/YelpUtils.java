package cs496.yelpsearch.utils;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

/**
 * Created by benny on 5/31/2017.
 */

public class YelpUtils {

    public static String buildForecastURL(String forecastLocation, String temperatureUnits) {

        Uri.Builder builder = Uri.parse(OWM_FORECAST_BASE_URL).buildUpon(); //change this to Request.Builder();

        if (!temperatureUnits.equals("")) {
            builder.appendQueryParameter(OWM_FORECAST_UNITS_PARAM, temperatureUnits);
        }

        if (!forecastLocation.equals("")) {
            builder.appendQueryParameter(OWM_FORECAST_QUERY_PARAM, forecastLocation);
            weatherLocation = forecastLocation;
        }

        builder.appendQueryParameter(OWM_FORECAST_APPID_PARAM, OWM_FORECAST_APPID);

        if (temperatureUnits.equals("imperial")){
            weatherUnits = "\u00b0F";
        }
        if (temperatureUnits.equals("kelvin")){
            weatherUnits = "\u00b0K";
        }
        if (temperatureUnits.equals("metric")){
            weatherUnits = "\u00b0C";
        }



        return builder.build().toString();
    }

    public static ArrayList<ForecastItem> parseForecastJSON(String forecastJSON) {
        try {
            JSONObject forecastObj = new JSONObject(forecastJSON);
            JSONArray forecastList = forecastObj.getJSONArray("list");
            SimpleDateFormat dateParser = new SimpleDateFormat(OWM_FORECAST_DATE_FORMAT);
            dateParser.setTimeZone(TimeZone.getTimeZone(OWM_FORECAST_TIME_ZONE));

            ArrayList<ForecastItem> forecastItemsList = new ArrayList<ForecastItem>();
            for (int i = 0; i < forecastList.length(); i++) {
                ForecastItem forecastItem = new ForecastItem();
                JSONObject forecastListElem = forecastList.getJSONObject(i);

                String dateString = forecastListElem.getString("dt_txt");
                forecastItem.dateTime = dateParser.parse(dateString);

                forecastItem.description = forecastListElem.getJSONArray("weather").getJSONObject(0).getString("main");

                JSONObject mainObj = forecastListElem.getJSONObject("main");
                forecastItem.temperature = Math.round(mainObj.getDouble("temp"));
                forecastItem.temperatureLow = Math.round(mainObj.getDouble("temp_min"));
                forecastItem.temperatureHigh = Math.round(mainObj.getDouble("temp_max"));
                forecastItem.humidity = Math.round(mainObj.getDouble("humidity"));

                JSONObject windObj = forecastListElem.getJSONObject("wind");
                forecastItem.windSpeed = Math.round(windObj.getDouble("speed"));
                forecastItem.windDirection = windAngleToDirection(windObj.getDouble("deg"));

                forecastItemsList.add(forecastItem);
            }
            return forecastItemsList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}
