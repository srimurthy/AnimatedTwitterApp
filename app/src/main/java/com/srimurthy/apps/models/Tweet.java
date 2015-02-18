package com.srimurthy.apps.models;

import android.database.Cursor;
import android.text.format.DateUtils;

import com.activeandroid.Cache;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by srmurthy on 2/4/15.
 */
@Table(name = "Tweets")
public class Tweet extends Model {

    @Column(name = "tweet_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private long tweetId;

    @Column(name = "body")
    private String body;

    @Column(name = "uid")
    private long uid;

    @Column(name = "User", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    private User user;

    @Column(name = "create_at")
    private String createAt;

    @Column(name = "retweet_count")
    private int retweetCount;

    @Column(name = "favorite_count")
    private int favoriteCount;

    @Column(name = "media_url")
    private String mediaURL;

    private static final String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
    private static final SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);

    static {
        sf.setLenient(true);
    }

    public String getMediaURL() {
        return mediaURL;
    }

    public void setMediaURL(String mediaURL) {
        this.mediaURL = mediaURL;
    }


    public int getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(int retweetCount) {
        this.retweetCount = retweetCount;
    }

    public long getTweetId() {
        return tweetId;
    }

    public void setTweetId(long tweetId) {
        this.tweetId = tweetId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getCreateAt() {
        return getRelativeTimeAgo(this.createAt);
        //return this.createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public static Tweet fromJSON(JSONObject jsonObject) {
        Tweet tweet = new Tweet();

        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createAt = jsonObject.getString("created_at");
            tweet.favoriteCount = jsonObject.getInt("favorite_count");
            tweet.retweetCount = jsonObject.getInt("retweet_count");
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
            JSONObject entities = jsonObject.getJSONObject("entities");

            JSONArray mediaArray = entities.optJSONArray("media");

            if (mediaArray != null && mediaArray.length() > 0) {
                JSONObject mediaObject = mediaArray.getJSONObject(0);
                tweet.mediaURL = mediaObject.getString("media_url");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tweet;
    }

    public static List<Tweet> fromJSONArray(JSONArray jsonArray) {
        List<Tweet> tweetList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                Tweet tweet = fromJSON(jsonArray.getJSONObject(i));
                if (tweet != null) {
                    tweetList.add(tweet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }
        return tweetList;
    }

    public String getRelativeTimeAgo(String rawJsonDate) {
        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return relativeDate;
    }

    public static List<Tweet> getPersistedTweets() {
        return new Select()
                .from(Tweet.class)
                .execute();
    }
}
