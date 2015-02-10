package com.codepath.apps.restclienttemplate;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ComposeTweet.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ComposeTweet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComposeTweet extends Fragment {

    private EditText etTweetBody;
    private TextView tvCharacterCount;
    private ImageView ivProfilePicture;
    private TextView tvUserName;
    private TextView tvScreenName;

    private TwitterClient twitterClient;
    private User appUser;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment ComposeTweet.
     */
    public static ComposeTweet newInstance(TwitterClient twitterClient, User appUser) {
        ComposeTweet fragment = new ComposeTweet();
        fragment.twitterClient = twitterClient;
        fragment.appUser = appUser;
        return fragment;
    }

    public ComposeTweet() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RelativeLayout fragmentLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_compose_tweet, container, false);
        this.tvCharacterCount = (TextView)fragmentLayout.findViewById(R.id.tvCharacterCount);
        this.tvUserName = (TextView)fragmentLayout.findViewById(R.id.tvFragmentUserName);
        this.tvScreenName = (TextView)fragmentLayout.findViewById(R.id.tvFragmentScreenName);
        this.ivProfilePicture = (ImageView)fragmentLayout.findViewById(R.id.ivFragmentProfileImage);

        this.tvScreenName.setText(this.appUser.getScreenName());
        this.tvUserName.setText(this.appUser.getName());
        Picasso.with(getActivity()).load(this.appUser.getProfileImageUrl()).into(this.ivProfilePicture);

        this.etTweetBody = (EditText)fragmentLayout.findViewById(R.id.etTweetBody);
        this.etTweetBody.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ComposeTweet.this.tvCharacterCount.setText(String.valueOf(140 - ComposeTweet.this.etTweetBody.getText().length()));
            }
        });


        Button saveButton = (Button) fragmentLayout.findViewById(R.id.btnSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComposeTweet.this.hideFragment();
                String tweetText = ComposeTweet.this.etTweetBody.getText().toString();
                postTweet(tweetText);
            }
        });

        Button closeButton = (Button) fragmentLayout.findViewById(R.id.btnClose);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComposeTweet.this.hideFragment();
            }
        });

        return fragmentLayout;
    }

    public void postTweet(String tweetText) {
        if (mListener != null) {

            this.twitterClient.postTweet(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    Log.d("DEBUG", response.toString());
                    mListener.onFragmentInteraction();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.e("DEBUG", errorResponse.toString());
                }
            }, tweetText);
        }
    }

    private void hideFragment() {
        FragmentTransaction ft = ComposeTweet.this.getFragmentManager().beginTransaction();
        ft.hide(ComposeTweet.this);
        ft.commit();
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction();
    }
}