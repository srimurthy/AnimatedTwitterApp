package com.srimurthy.apps.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.srimurthy.apps.TwitterApplication;
import com.srimurthy.apps.fragments.UserTimelineFragment;
import com.srimurthy.apps.models.TwitterClient;
import com.srimurthy.apps.models.User;
import com.srimurthy.apps.mysimpletweets.R;

import org.apache.http.Header;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ProfileActivity extends ActionBarActivity {

    private TwitterClient twitterClient;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        this.twitterClient = TwitterApplication.getRestClient();
        String screen_name = getIntent().getStringExtra("screen_name");
        if (screen_name != null && screen_name.startsWith("@")) {
            screen_name = screen_name.replaceFirst("@", "");
        }
        this.twitterClient.getUserInfo(screen_name, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                ProfileActivity.this.user = User.fromJSON(jsonObject);
                getSupportActionBar().setTitle(ProfileActivity.this.user.getScreenName());
                populateUserProfileHeader(ProfileActivity.this.user);
            }
        });

        if (savedInstanceState == null) {
            UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(screen_name);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.flContainer, userTimelineFragment);
            fragmentTransaction.commit();
        }
    }

    private void populateUserProfileHeader(User user) {


        RelativeLayout rlUserHeader = (RelativeLayout) findViewById(R.id.rlUserHeader);
        TextView tvName = (TextView) findViewById(R.id.tvName);
        //TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
        TextView tvScreenName = (TextView) findViewById(R.id.tvScreenName);

        TextView tvTweetCount = (TextView) findViewById(R.id.tvTweetCount);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);


        String url = user.getProfileBackgroundImageUrl();
        // Download image from URL and display within ImageView
        new ImageDownloadTask(rlUserHeader).execute(url);


        tvName.setText(user.getName());
        //tvTagline.setText(user.getTagLine());
        tvTweetCount.setText(String.valueOf(user.getStatusesCount()));
        tvScreenName.setText(user.getScreenName());
        tvFollowers.setText(String.valueOf(user.getFollowersCount()));
        tvFollowing.setText(String.valueOf(user.getFollowingCount()));
        Picasso.with(this).load(user.getProfileImageUrl()).into(ivProfileImage);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class ImageDownloadTask extends AsyncTask<String, Void, Bitmap> {
        RelativeLayout rlUserHeader;

        public ImageDownloadTask(RelativeLayout rlUserHeader) {
            this.rlUserHeader = rlUserHeader;
        }

        protected Bitmap doInBackground(String... addresses) {
            Bitmap bitmap = null;
            InputStream in = null;
            try {
                // 1. Declare a URL Connection
                URL url = new URL(addresses[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                // 2. Open InputStream to connection
                conn.connect();
                in = conn.getInputStream();
                // 3. Download and decode the bitmap using BitmapFactory
                bitmap = BitmapFactory.decodeStream(in);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (in != null)
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            return bitmap;
        }

        // Fires after the task is completed, displaying the bitmap into the ImageView
        @Override
        protected void onPostExecute(Bitmap result) {
            // Set bitmap image for the result

            BitmapDrawable background = new BitmapDrawable(result);
            rlUserHeader.setBackgroundDrawable(background);
        }
    }

}
