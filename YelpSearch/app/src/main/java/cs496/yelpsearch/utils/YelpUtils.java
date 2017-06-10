package cs496.yelpsearch.utils;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;
import java.io.Serializable;

/**
 * Created by benny on 5/31/2017.
 */

public class YelpUtils {

    private final static String YELP_SEARCH_BASE_URL = "https://api.yelp.com/v3/businesses/search";
    private final static String YELP_SEARCH_LOCATION_PARAM = "location";
    private final static String YELP_SEARCH_BUSINESS_PARAM = "term";
    private final static String YELP_SEARCH_IN_LOCATION = "corvallis";



    public static class SearchResult implements Serializable {
        public static final String EXTRA_SEARCH_RESULT = "YelpUtils.SearchResult";
        public String term;
        public String location;
        public String rating;
        public String phone;
        public String price;
        public String review_count;
        public String image_url;
        public String address1;
        public String city;
        public String state;
        public String zip_code;
        public boolean is_closed;

    }

    public static String buildYelpSearchURL(String term, String location) {

        Uri.Builder builder = Uri.parse(YELP_SEARCH_BASE_URL).buildUpon();

        if (location.equals("")) {
            builder.appendQueryParameter(YELP_SEARCH_LOCATION_PARAM, YELP_SEARCH_IN_LOCATION);
        }
        else {
            builder.appendQueryParameter(YELP_SEARCH_LOCATION_PARAM, location);
        }

        if (!term.equals("")) {
            builder.appendQueryParameter(YELP_SEARCH_BUSINESS_PARAM, term);
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
                searchResult.location = searchResultItem.getString("location");
                searchResult.rating = searchResultItem.getString("rating");
                searchResult.phone = searchResultItem.getString("phone");
                searchResult.price = searchResultItem.getString("price");
                searchResultsList.add(searchResult);
            }
            return searchResultsList;
        } catch (JSONException e) {
            return null;
        }
    }

}
