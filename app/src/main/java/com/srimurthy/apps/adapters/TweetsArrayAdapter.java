package com.srimurthy.apps.adapters;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.srimurthy.apps.fragments.ComposeTweet;
import com.srimurthy.apps.models.User;
import com.srimurthy.apps.mysimpletweets.R;
import com.srimurthy.apps.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by srmurthy on 2/4/15.
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {

    private static class ViewHolder {
        ImageView ivProfileImage;
        TextView tvUserName;
        TextView tvBody;
        TextView tvCreatedAt;
        TextView tvScreenName;
        TextView tvRetweetCount;
        TextView tvFavoritesCount;
        ImageView ivMediaImage;
        ImageView ivReply;
    }

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, 0, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Tweet tweet = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_tweet, parent, false);
            viewHolder.ivProfileImage = (ImageView)convertView.findViewById(R.id.ivProfileImage);
            viewHolder.tvUserName = (TextView)convertView.findViewById(R.id.tvUserName);
            viewHolder.tvBody = (TextView)convertView.findViewById(R.id.tvBody);
            viewHolder.tvCreatedAt = (TextView)convertView.findViewById(R.id.tvCreatedAt);
            viewHolder.tvScreenName = (TextView)convertView.findViewById(R.id.tvScreenName);
            viewHolder.tvRetweetCount = (TextView)convertView.findViewById(R.id.tvRetweetCount);
            viewHolder.tvFavoritesCount = (TextView)convertView.findViewById(R.id.tvFavoritesCount);
            viewHolder.ivMediaImage = (ImageView)convertView.findViewById(R.id.ivMediaImage);
            viewHolder.ivReply = (ImageView)convertView.findViewById(R.id.ivReply);



            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvUserName.setText(tweet.getUser().getName());
        viewHolder.tvBody.setText(tweet.getBody());
        viewHolder.ivProfileImage.setImageResource(android.R.color.transparent);
        viewHolder.ivProfileImage.setTag(tweet.getUser().getScreenName());
        viewHolder.ivReply.setTag(tweet.getUser().getScreenName());
        viewHolder.tvCreatedAt.setText(tweet.getCreateAt());
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(viewHolder.ivProfileImage);
        viewHolder.tvScreenName.setText(tweet.getUser().getScreenName());
        viewHolder.tvRetweetCount.setText(String.valueOf(tweet.getRetweetCount()));
        viewHolder.tvFavoritesCount.setText(String.valueOf(tweet.getFavoriteCount()));


        if (tweet.getMediaURL() != null && tweet.getMediaURL().isEmpty() == false) {
            viewHolder.ivMediaImage.setImageResource(android.R.color.transparent);
            viewHolder.ivMediaImage.setTag(tweet.getUser().getScreenName());
            Picasso.with(getContext()).load(tweet.getMediaURL()).centerCrop().resize(200,150).into(viewHolder.ivMediaImage);
        }
        return convertView;
    }
}