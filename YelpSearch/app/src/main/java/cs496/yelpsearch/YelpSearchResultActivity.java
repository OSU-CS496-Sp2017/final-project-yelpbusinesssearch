package cs496.yelpsearch;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import cs496.yelpsearch.utils.YelpUtils;

public class YelpSearchResultActivity extends AppCompatActivity {

    private TextView mSearchResultNameTV;
    private TextView mSearchResultDescriptionTV;
    private TextView mSearchResultStarsTV;
    private YelpUtils.SearchResult mSearchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yelp_search_result);

        mSearchResultNameTV = (TextView)findViewById(R.id.tv_search_result_name);
        mSearchResultDescriptionTV = (TextView)findViewById(R.id.tv_search_result_description);
        mSearchResultStarsTV = (TextView)findViewById(R.id.tv_search_result_stars);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(YelpUtils.SearchResult.EXTRA_SEARCH_RESULT)) {
            mSearchResult = (YelpUtils.SearchResult)intent.getSerializableExtra(YelpUtils.SearchResult.EXTRA_SEARCH_RESULT);
            mSearchResultNameTV.setText(mSearchResult.location);
            mSearchResultDescriptionTV.setText(mSearchResult.rating);
            mSearchResultStarsTV.setText(mSearchResult.price);
        }
    }

}
