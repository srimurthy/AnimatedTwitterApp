package com.srimurthy.apps.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.srimurthy.apps.TwitterApplication;
import com.srimurthy.apps.models.Tweet;
import com.srimurthy.apps.models.TwitterClient;
import com.srimurthy.apps.mysimpletweets.R;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by srmurthy on 2/13/15.
 */
public class MentionsTimelineFragment extends TweetsListFragment {
    private TwitterClient twitterClient;
    private int startIndex;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        twitterClient = TwitterApplication.getRestClient();
        this.startIndex = 1;
        populateTimeline();


    }

    protected void populateTimeline() {

        twitterClient.getMentionsTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("DEBUG", response.toString());
                addAll(Tweet.fromJSONArray(response));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("DEBUG", errorResponse.toString());
            }
        }, this.startIndex);
    }
}
