package com.codepath.apps.mysimpletweets.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.codepath.apps.mysimpletweets.Activity.ProfileActivity;
import com.codepath.apps.mysimpletweets.Adapters.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by dvalia on 11/1/15.
 */
public class MentionsTimelineFragment extends TweetsListFragment {

    private TwitterClient client;
    private SwipeRefreshLayout swipeContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        client = TwitterApplication.getRestClient(); //get a singleton rest client

        TweetsArrayAdapter adapter = getAdapter();
        adapter.setProfileImageListener(new TweetsArrayAdapter.ProfileImageListener() {
            @Override
            public void onProfileImageSelected(User user) {
                Intent i = new Intent(getContext(), ProfileActivity.class);
                i.putExtra("user", user);
                startActivity(i);
            }
        });

        populateTimeline(0);

    }

    private void populateTimeline(long MaxId){

        client.getMentionsTimeline(false, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                // super.onSuccess(statusCode, headers, response);

                System.out.println("OnScroll onSuccess: ");
                addAll(Tweet.fromJsonArray(response));

                swipeContainer.setRefreshing(false);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);

                System.out.println("OnScroll onFailure: " + errorResponse.toString());
                swipeContainer.setRefreshing(false);
            }


        });


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        swipeContainer= (SwipeRefreshLayout) view.findViewById(R.id.SwipeContainer);
//Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Log.i("DEBUG", "inside refresh of MentionsTimelineFragment");
                getAdapter().clear();
                populateTimeline(0);
            }
        });
        setCustomScrollLoadListener(new CustomScrollLoadListener() {
            @Override
            public void onCustomScrollLoad(Long maxId) {
               // Log.i("DEBUG", "inside Mentions:setCustomScrollLoadListener received");
                populateTimeline(maxId);
            }
        });
    }
}
