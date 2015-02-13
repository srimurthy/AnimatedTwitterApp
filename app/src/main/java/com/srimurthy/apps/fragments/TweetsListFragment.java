package com.srimurthy.apps.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.srimurthy.apps.adapters.EndlessScrollListener;
import com.srimurthy.apps.adapters.TweetsArrayAdapter;
import com.srimurthy.apps.models.Tweet;
import com.srimurthy.apps.mysimpletweets.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by srmurthy on 2/12/15.
 */
public abstract class TweetsListFragment extends Fragment {


    private TweetsArrayAdapter aTweets;
    private List<Tweet> tweets;
    private ListView lvTweets;

    protected int startIndex;
    private SwipeRefreshLayout swipeContainer;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tweets_list, parent, false);

        lvTweets = (ListView) view.findViewById(R.id.lvTweets);
        tweets = new ArrayList<>();
        aTweets = new TweetsArrayAdapter(getActivity(), tweets);
        lvTweets.setAdapter(aTweets);

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                TweetsListFragment.this.startIndex = 1;
                populateTimeline();
                swipeContainer.setRefreshing(false);
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        this.lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                TweetsListFragment.this.startIndex = page * 7;
                populateTimeline();
            }
        });


        return view;
    }

    protected abstract void populateTimeline();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void addAll(List<Tweet> tweets) {
        this.aTweets.addAll(tweets);
    }
}
