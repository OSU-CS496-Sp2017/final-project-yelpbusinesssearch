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

        private final static String YELP_SEARCH_BASE_URL = "https://api.yelp.com/v3/businesses/search";
        private final static String YELP_SEARCH_LOCATION_PARAM = "location";
        private final static String YELP_SEARCH_BUSINESS_PARAM = "locale";
        private final static String YELP_SEARCH_IN_LOCATION = "Corvallis, OR";

        public static class SearchResult implements Serializable {
            public static final String EXTRA_SEARCH_RESULT = "YelpUtils.SearchResult";
            public String location;
            public String ratings;
            public String htmlURL;
            public int stars;
        }

    public static String buildYelpSearchURL(String locale, String location) {

        Uri.Builder builder = Uri.parse(YELP_SEARCH_BASE_URL).buildUpon();

        if (location.equals("")) {
            builder.appendQueryParameter(YELP_SEARCH_LOCATION_PARAM, YELP_SEARCH_IN_LOCATION);
        }
        else {
            builder.appendQueryParameter(YELP_SEARCH_LOCATION_PARAM, location);
        }

        if (!locale.equals("")) {
            builder.appendQueryParameter(YELP_SEARCH_BUSINESS_PARAM, locale);
        }

        return builder.build().toString();
    }

    public static ArrayList<SearchResult> parseYelpSearchResultsJSON(String searchResultsJSON) {
        try {
            JSONObject searchResultsObj = new JSONObject(searchResultsJSON);
            JSONArray searchResultsItems = searchResultsObj.getJSONArray("items");

            ArrayList<SearchResult> searchResultsList = new ArrayList<SearchResult>();
            for (int i = 0; i < searchResultsItems.length(); i++) {
                SearchResult searchResult = new SearchResult();
                JSONObject searchResultItem = searchResultsItems.getJSONObject(i);
                searchResult.fullName = searchResultItem.getString("full_name");
                searchResult.description = searchResultItem.getString("description");
                searchResult.htmlURL = searchResultItem.getString("html_url");
                searchResult.stars = searchResultItem.getInt("stargazers_count");
                searchResultsList.add(searchResult);
            }
            return searchResultsList;
        } catch (JSONException e) {
            return null;
        }
    }

}
