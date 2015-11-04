package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.Adapters.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.Listeners.EndlessScrollListener;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dvalia on 11/1/15.
 */
public class TweetsListFragment extends Fragment{

    private TweetsArrayAdapter tweetsAdapter;
    private ArrayList<Tweet> tweets;
    private ListView lvTweets;
    private CustomScrollLoadListener listener;
    //inflation logic

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragement_tweets_list, parent, false);

        lvTweets = (ListView)v.findViewById(R.id.lvTweets);

        lvTweets.setAdapter(tweetsAdapter);

        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
// Triggered only when new data needs to be appended to the list
// Add whatever code is needed to append new items to your AdapterView
                long max_id = Tweet.getMaxId() - 1;
                if (listener != null) {
                    listener.onCustomScrollLoad(max_id);

                }
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });

        return v;
    }


    //creation lifecycle event

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tweets = new ArrayList<>();

        tweetsAdapter = new TweetsArrayAdapter(getActivity(), tweets);


    }

    public void addAll(List<Tweet> tweets){

        tweetsAdapter.addAll(tweets);

        System.out.println(" Tweets: " + tweets.toString());
    }

    public void insert(Tweet tweet){

        tweetsAdapter.insert(tweet, 0);
    }

    public TweetsArrayAdapter getAdapter() {
        return tweetsAdapter;
    }

    // Creating custom listener
    public interface CustomScrollLoadListener {
        public void onCustomScrollLoad(Long maxId);
    }

    public void setCustomScrollLoadListener(CustomScrollLoadListener listener) {
        this.listener = listener;
    }
}
