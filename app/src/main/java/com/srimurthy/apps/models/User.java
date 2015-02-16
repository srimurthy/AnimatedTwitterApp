package com.srimurthy.apps.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by srmurthy on 2/4/15.
 */
public class User {
    private String name;
    private long uid;
    private String screenName;
    private String profileImageUrl;
    private String tagLine;
    private int followersCount;
    private int followingCount;

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
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
        if(this.screenName != null && this.screenName.isEmpty() == false) {
            return "@"+this.screenName;
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
            user.tagLine = jsonObject.getString("description");
            user.followersCount = jsonObject.getInt("followers_count");
            user.followingCount = jsonObject.getInt("friends_count");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }
}