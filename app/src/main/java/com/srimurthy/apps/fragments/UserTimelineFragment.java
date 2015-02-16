package com.srimurthy.apps.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.srimurthy.apps.TwitterApplication;
import com.srimurthy.apps.models.Tweet;
import com.srimurthy.apps.models.TwitterClient;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by srmurthy on 2/15/15.
 */
public class UserTimelineFragment extends TweetsListFragment {

    private TwitterClient twitterClient;
    private int startIndex;


    public static UserTimelineFragment newInstance(String screen_name) {
        UserTimelineFragment userTimelineFragment = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("screen_name", screen_name);
        userTimelineFragment.setArguments(args);
        return userTimelineFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        twitterClient = TwitterApplication.getRestClient();
        this.startIndex = 1;
        populateTimeline();


    }

    protected void populateTimeline() {
        String screen_name = (String)getArguments().get("screen_name");
        twitterClient.getUserTimeline(screen_name,new JsonHttpResponseHandler() {
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
