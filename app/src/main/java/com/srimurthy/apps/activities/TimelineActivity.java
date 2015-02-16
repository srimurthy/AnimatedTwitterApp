package com.srimurthy.apps.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.astuetz.PagerSlidingTabStrip;
import com.srimurthy.apps.adapters.TweetsArrayAdapter;
import com.srimurthy.apps.TwitterApplication;
import com.srimurthy.apps.fragments.HomeTimelineFragment;
import com.srimurthy.apps.fragments.MentionsTimelineFragment;
import com.srimurthy.apps.fragments.TweetsListFragment;
import com.srimurthy.apps.models.TwitterClient;
import com.srimurthy.apps.mysimpletweets.R;
import com.srimurthy.apps.adapters.EndlessScrollListener;
import com.srimurthy.apps.models.Tweet;
import com.srimurthy.apps.models.User;
import com.srimurthy.apps.fragments.ComposeTweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class TimelineActivity extends ActionBarActivity implements ComposeTweet.OnFragmentInteractionListener {

    private TweetsListFragment tweetsListFragment;
    private User appUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        ViewPager viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));
        PagerSlidingTabStrip pagerSlidingTabStrip = (PagerSlidingTabStrip)findViewById(R.id.tabs);
        pagerSlidingTabStrip.setViewPager(viewPager);
        // getAppUser();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.compose_tweet) {
            displayTweetComposeDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    public void displayTweetComposeDialog() {
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ComposeTweet searchSettingFragment = ComposeTweet.newInstance(this.twitterClient, appUser);
//        ft.replace(R.id.compose_tweet_fragment_placeholder, searchSettingFragment);
//        ft.commit();
    }

    @Override
    public void onFragmentInteraction() {
        // populateOwnTimeline();
    }

    public void onProfileView(MenuItem item) {
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

    public void onProfileView(View view) {
        Intent i = new Intent(this, ProfileActivity.class);
        String userScreenName = (String)view.getTag();
        i.putExtra("screen_name", userScreenName);
        startActivity(i);
    }


//    private void getAppUser() {
//        twitterClient.getAppUser(new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                Log.d("DEBUG", response.toString());
//                TimelineActivity.this.appUser = User.fromJSON(response);
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                throwable.printStackTrace();
//            }
//        });
//    }
//
//
//
//    private void populateOwnTimeline() {
//
//        twitterClient.getOwnTimeline(new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                Log.d("DEBUG", response.toString());
//                tweetsListFragment.addAll(Tweet.fromJSONArray(response));
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                Log.e("DEBUG", errorResponse.toString());
//            }
//        });
//    }


    public class TweetsPagerAdapter extends FragmentPagerAdapter {
        private String[] tabTitles = new String[]{"Home", "Mentions"};

        public TweetsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new HomeTimelineFragment();
            } else if (position == 1) {
                return new MentionsTimelineFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return this.tabTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return this.tabTitles[position];
        }
    }
}