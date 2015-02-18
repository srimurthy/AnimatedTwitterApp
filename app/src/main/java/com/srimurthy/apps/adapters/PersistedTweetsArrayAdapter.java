//package com.srimurthy.apps.adapters;
//
//import android.content.Context;
//import android.database.Cursor;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CursorAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.squareup.picasso.Picasso;
//import com.srimurthy.apps.models.Tweet;
//import com.srimurthy.apps.mysimpletweets.R;
//
///**
// * Created by srmurthy on 2/17/15.
// */
//
//public class PersistedTweetsArrayAdapter extends CursorAdapter {
//
//    private static class ViewHolder {
//        ImageView ivProfileImage;
//        TextView tvUserName;
//        TextView tvBody;
//        TextView tvCreatedAt;
//        TextView tvScreenName;
//        TextView tvFavoritesCount;
//    }
//
//    public PersistedTweetsArrayAdapter(Context context, Cursor cursor) {
//        super(context, cursor, 0);
//    }
//
//    // The newView method is used to inflate a new view and return it,
//    // you don't bind any data to the view at this point.
//    @Override
//    public View newView(Context context, Cursor cursor, ViewGroup parent) {
//        return LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
//    }
//
//    // The bindView method is used to bind all data to a given view
//    // such as setting the text on a TextView.
//    @Override
//    public void bindView(View convertView, Context context, Cursor cursor) {
//
//        ImageView ivProfileImage = (ImageView)convertView.findViewById(R.id.ivProfileImage);
//        TextView tvUserName = (TextView)convertView.findViewById(R.id.tvUserName);
//        TextView tvBody = (TextView)convertView.findViewById(R.id.tvBody);
//        TextView tvCreatedAt = (TextView)convertView.findViewById(R.id.tvCreatedAt);
//        TextView tvScreenName = (TextView)convertView.findViewById(R.id.tvScreenName);
//        TextView tvFavoritesCount = (TextView)convertView.findViewById(R.id.tvFavoritesCount);
//
//        // Extract properties from cursor
//        String body = cursor.getString(cursor.getColumnIndexOrThrow("body"));
//        int priority = cursor.getInt(cursor.getColumnIndexOrThrow("priority"));
//
//        tvUserName.setText(tweet.getUser().getName());
//        tvBody.setText(tweet.getBody());
//        ivProfileImage.setImageResource(android.R.color.transparent);
//        ivProfileImage.setTag(tweet.getUser().getScreenName());
//        tvCreatedAt.setText(tweet.getCreateAt());
//        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);
//        tvScreenName.setText(tweet.getUser().getScreenName());
//        tvFavoritesCount.setText(String.valueOf(tweet.getFavoriteCount()));
//
//        // Populate fields with extracted properties
//        tvBody.setText(body);
//        tvPriority.setText(String.valueOf(priority));
//    }
//}