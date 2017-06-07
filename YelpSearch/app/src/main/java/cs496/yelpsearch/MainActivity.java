package cs496.yelpsearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private EditText mSearchBoxET;
    //private YelpSearchAdapter mYelpSearchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchBoxET = (EditText)findViewById(R.id.et_search_box);

        Button searchButton = (Button)findViewById(R.id.btn_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchQuery = mSearchBoxET.getText().toString();
                if (!TextUtils.isEmpty(searchQuery)) {
                    doGitHubSearch(searchQuery);
                }
            }
        });

    private void doYelpSearch(String searchQuery) {
        String YelpSearchUrl = YelpUtils.buildYelpURL(searchQuery);
        new GitHubSearchTask().execute(YelpSearchUrl);
    }

    }

    /** Called when the user hits the "Find" button*/
    public void onSearchResultClick(View view) {
        Intent searchResultsIntent = new Intent(this, YelpSearchResultActivity.class);
        startActivity(searchResultsIntent);
    }
}
