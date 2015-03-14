package com.srimurthy.apps.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.astuetz.PagerSlidingTabStrip;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.srimurthy.apps.TwitterApplication;
import com.srimurthy.apps.fragments.ComposeTweet;
import com.srimurthy.apps.fragments.HomeTimelineFragment;
import com.srimurthy.apps.fragments.MentionsTimelineFragment;
import com.srimurthy.apps.models.Tweet;
import com.srimurthy.apps.models.TwitterClient;
import com.srimurthy.apps.models.User;
import com.srimurthy.apps.mysimpletweets.R;

import org.apache.http.Header;
import org.json.JSONObject;


public class TimelineActivity extends ActionBarActivity implements ComposeTweet.OnFragmentInteractionListener {

    private TwitterClient twitterClient;

    private TweetsPagerAdapter tweetsPagerAdapter;
    private User appUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        this.twitterClient = TwitterApplication.getRestClient();
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        tweetsPagerAdapter = new TweetsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tweetsPagerAdapter);
        PagerSlidingTabStrip pagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pagerSlidingTabStrip.setViewPager(viewPager);

        this.getSupportActionBar().setIcon(R.mipmap.ic_twitter_white_bird);
        this.getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_twitter_white_bird);
        this.twitterClient.getUserInfo(null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                TimelineActivity.this.appUser = User.fromJSON(jsonObject);
            }
        });

//        ImageView ivReply = (ImageView) findViewById(R.id.ivReply);
//        ivReply.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String replyHashTag = (String) v.getTag();
//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                ComposeTweet searchSettingFragment = ComposeTweet.newInstance(TimelineActivity.this.appUser, replyHashTag);
//                ft.replace(R.id.compose_tweet_fragment_placeholder, searchSettingFragment);
//                ft.commit();
//            }
//        });
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

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ComposeTweet searchSettingFragment = ComposeTweet.newInstance(appUser, null);
        ft.replace(R.id.compose_tweet_fragment_placeholder, searchSettingFragment);
        ft.commit();
    }

    @Override
    public void onFragmentInteraction(Tweet tweet) {
        HomeTimelineFragment homeTimelineFragment = (HomeTimelineFragment)this.tweetsPagerAdapter.getItem(0);
        homeTimelineFragment.displayNewTweet(tweet);
    }

    public void onProfileView(MenuItem item) {
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

    public void onProfileView(View view) {
        Intent i = new Intent(this, ProfileActivity.class);
        String userScreenName = (String) view.getTag();
        i.putExtra("screen_name", userScreenName);
        startActivity(i);
    }


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