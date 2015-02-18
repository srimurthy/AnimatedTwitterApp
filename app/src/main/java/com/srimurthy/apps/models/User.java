package com.srimurthy.apps.models;

import android.database.Cursor;

import com.activeandroid.Cache;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by srmurthy on 2/4/15.
 */
@Table(name = "User")
public class User extends Model {

    @Column(name = "uid")
    private long uid;

    @Column(name = "name")
    private String name;

    @Column(name = "screen_name")
    private String screenName;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "tag_line")
    private String tagLine;

    @Column(name = "statuses_count")
    private int statusesCount;

    @Column(name = "followers_count")
    private int followersCount;

    @Column(name = "following_count")
    private int followingCount;

    @Column(name = "profile_banner_url")
    private String profileBackgroundImageUrl;

    public String getProfileBackgroundImageUrl() {
        return profileBackgroundImageUrl;
    }

    public void setProfileBackgroundImageUrl(String profileBackgroundImageUrl) {
        this.profileBackgroundImageUrl = profileBackgroundImageUrl;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public int getStatusesCount() {
        return statusesCount;
    }

    public void setStatusesCount(int statusesCount) {
        this.statusesCount = statusesCount;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getScreenName() {
        if (this.screenName != null && this.screenName.isEmpty() == false) {
            return "@" + this.screenName;
        }
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public static User fromJSON(JSONObject jsonObject) {
        User user = new User();

        try {
            user.uid = jsonObject.getInt("id");
            user.name = jsonObject.getString("name");
            user.screenName = jsonObject.getString("screen_name");
            user.profileImageUrl = jsonObject.getString("profile_image_url");
            user.profileBackgroundImageUrl = jsonObject.getString("profile_banner_url");
            user.tagLine = jsonObject.getString("description");
            user.statusesCount = jsonObject.getInt("statuses_count");
            user.followersCount = jsonObject.getInt("followers_count");
            user.followingCount = jsonObject.getInt("friends_count");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

}