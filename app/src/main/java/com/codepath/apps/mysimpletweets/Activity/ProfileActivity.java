package com.codepath.apps.mysimpletweets.Activity;

import android.media.Image;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.fragments.UserTimelineFragment;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

public class ProfileActivity extends ActionBarActivity {

    TwitterClient client;
    User user;
    String screenName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        client = TwitterApplication.getRestClient();

         user = (User) getIntent().getSerializableExtra("user");

        if(user==null) {
            client.getVerifyCredentials(new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);

                    user = user.fromJson(response);
                    getSupportActionBar().setTitle("@" + user.getScreenName());
                    populateUserHeader(user);

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                }
            });

            //get screen name from activity that launches this
            screenName = getIntent().getStringExtra("screen_name");
        }
        else {
            screenName = user.getScreenName();
            getSupportActionBar().setTitle("@" + user.getScreenName());
            populateUserHeader(user);
        }



        System.out.println("Profile activity: *****");

        if(savedInstanceState == null) {

            System.out.println("Profile activity: saveInstanceState null. Screen name: " + screenName);
            //create user timeline fragment
            UserTimelineFragment fragmentUserTimeline = UserTimelineFragment.newInstance(screenName);

            //Display user fragment within this activity (dynamically)
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, fragmentUserTimeline);

            ft.commit(); //changes the fragment 
        }

    }

    private void populateUserHeader(User user){

        TextView tvFullName = (TextView)findViewById(R.id.tvFullName);
        TextView tvTagline = (TextView)findViewById(R.id.tvTagline);
        TextView tvFollowers = (TextView)findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView)findViewById(R.id.tvFollowing);
        ImageView ivProfileImage = (ImageView)findViewById(R.id.ivProfileImage);

//        System.out.println("Profile Activity - Tagline: " + user.getTagLine());
        tvFullName.setText(user.getName());
        tvTagline.setText(user.getTagLine());
        tvFollowers.setText(user.getFollowersCount() + " Followers");
        tvFollowing.setText(user.getFollowingCount() + " Following");

        Picasso.with(this).load(user.getProfileImageUrl()).into(ivProfileImage);


    }


}
