package com.srimurthy.apps.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.srimurthy.apps.TwitterApplication;
import com.srimurthy.apps.adapters.EndlessScrollListener;
import com.srimurthy.apps.models.Tweet;
import com.srimurthy.apps.models.TwitterClient;
import com.srimurthy.apps.models.User;
import com.srimurthy.apps.mysimpletweets.R;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by srmurthy on 2/13/15.
 */
public class HomeTimelineFragment extends TweetsListFragment {

    private TwitterClient twitterClient;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        twitterClient = TwitterApplication.getRestClient();
        this.startIndex = 1;
        populateTimeline();

    }

    protected void populateOfflineTweets() {

        List<Tweet> persitedTweets = Tweet.getPersistedTweets();
        addAll(persitedTweets);
    }


    protected void populateTimeline() {

        twitterClient.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("DEBUG", response.toString());
                List<Tweet> tweetList = Tweet.fromJSONArray(response);
                addAll(tweetList);

                //TODO : Revisit this. Should've been done better
                //Because of lack of time, just persisting the first set.
//                if (startIndex == 1) {
//                    persistTweets(tweetList);
//                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("DEBUG", String.valueOf(errorResponse));
                //populateOfflineTweets();
            }
        }, this.startIndex);
    }

    private void persistTweets(List<Tweet> tweetList) {
        for (Tweet tweet : tweetList) {
            User eachUser = tweet.getUser();
            eachUser.save();
            tweet.save();
        }
    }

    public void displayNewTweet(Tweet tweet) {
        //addSingleTweet(tweet);
    }
}
