package cs496.yelpsearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import cs496.yelpsearch.utils.YelpUtils;

public class YelpSearchResultActivity extends AppCompatActivity {

    private TextView mSearchResultNameTV;
    private TextView mSearchResultDescriptionTV;
    private TextView mSearchResultStarsTV;
    private TextView mSearchResultAddressTV;
    private TextView mSearchResultIsClosedTV;
    private TextView mSearchResultUrlTV;
    private YelpUtils.SearchResult mSearchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yelp_search_result);

        mSearchResultNameTV = (TextView)findViewById(R.id.tv_search_result_name);
        mSearchResultDescriptionTV = (TextView)findViewById(R.id.tv_search_result_description);
        mSearchResultStarsTV = (TextView)findViewById(R.id.tv_search_result_stars);
        mSearchResultAddressTV = (TextView) findViewById(R.id.tv_search_result_address);
        mSearchResultIsClosedTV = (TextView) findViewById(R.id.tv_search_result_isclosed);
        mSearchResultUrlTV = (TextView) findViewById(R.id.tv_search_result_url);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(YelpUtils.SearchResult.EXTRA_SEARCH_RESULT)) {
            mSearchResult = (YelpUtils.SearchResult)intent.getSerializableExtra(YelpUtils.SearchResult.EXTRA_SEARCH_RESULT);
            mSearchResultNameTV.setText(mSearchResult.name);
            mSearchResultDescriptionTV.setText(mSearchResult.rating + " (" + mSearchResult.review_count + " reviews)");
            mSearchResultStarsTV.setText(mSearchResult.phone);

            String img = "<a href='" + mSearchResult.image_url + "'>Image</a>";
            mSearchResultUrlTV.setClickable(true);
            mSearchResultUrlTV.setMovementMethod(LinkMovementMethod.getInstance());
            mSearchResultUrlTV.setText(Html.fromHtml(img));

            mSearchResultAddressTV.setText(mSearchResult.realAddress);
            if(mSearchResult.is_closed){
                mSearchResultIsClosedTV.setText("Open Now");
            }
            else {
                mSearchResultIsClosedTV.setText("Closed Now");
            }

        }
    }

}
