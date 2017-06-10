package cs496.yelpsearch.utils;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;
import java.io.Serializable;

import static android.content.ContentValues.TAG;

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
            JSONArray searchResultsItems = searchResultsObj.getJSONArray("businesses");
            Log.d(TAG, "parseYelpSearchResultsJSON searchResultsItems: " + searchResultsItems);
            Log.d(TAG, "parseYelpSearchResultsJSON searchResultsItems Length: " + searchResultsItems.length());

            ArrayList<SearchResult> searchResultsList = new ArrayList<SearchResult>();
            Log.d(TAG, "parseYelpSearchResultsJSON searchResultList: " + searchResultsList);

            for (int i = 0; i < searchResultsItems.length(); i++) {
                SearchResult searchResult = new SearchResult();
                JSONObject searchResultItem = searchResultsItems.getJSONObject(i);

                Log.d(TAG, "parseYelpSearchResultsJSON searchResultItem: " + searchResultItem);
                Log.d(TAG, "parseYelpSearchResultsJSON searchResultItem Length: " + searchResultItem.length());

                searchResult.review_count = searchResultItem.getString("review_count");
                Log.d(TAG, "parseYelpSearchResultsJSON review count: " + searchResult.review_count);

                searchResult.phone = searchResultItem.getString("display_phone");
                Log.d(TAG, "parseYelpSearchResultsJSON phone: " + searchResult.phone);

                searchResult.price = searchResultItem.getString("price");
                Log.d(TAG, "parseYelpSearchResultsJSON price: " + searchResult.price);


                searchResult.image_url = searchResultItem.getString("image_url");
                Log.d(TAG, "parseYelpSearchResultsJSON image URL: " + searchResult.image_url);

                searchResult.is_closed = searchResultItem.getBoolean("is_closed");
                Log.d(TAG, "parseYelpSearchResultsJSON is_closed: " + searchResult.is_closed);

                JSONObject locationobj = searchResultItem.getJSONObject("location");
                Log.d(TAG, "parseYelpSearchResultsJSON locationObj: " + locationobj);


                searchResult.address1 = locationobj.getString("display_address");
                Log.d(TAG, "parseYelpSearchResultsJSON dispay_address1: " + searchResult.address1);


                searchResult.rating = searchResultItem.getString("rating");
                Log.d(TAG, "parseYelpSearchResultsJSON rating: " + searchResult.rating);

                searchResultsList.add(searchResult);
                Log.d(TAG, "parseYelpSearchResultsJSON searchResultList: " + searchResultsList);
            }
            return searchResultsList;
        } catch (JSONException e) {
            return null;
        }
    }

}
