package com.codepath.apps.mysimpletweets.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

public class NewTweetActivity extends AppCompatActivity {

    private TwitterClient client;
    private User userMe;
    private ImageView ivMyImage;
    private EditText etTweet;
    private TextView tvScreenName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tweet);

        //userMe = (User)getIntent().getSerializableExtra("user");

        System.out.println("NewTweetActivity.onCreate");

        client = TwitterApplication.getRestClient(); //get a singleton rest client

        client.getVerifyCredentials(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                System.out.println("NewTweetActivity onSuccess: ");

                userMe = userMe.fromJson(response);
                getSupportActionBar().setTitle("@" + userMe.getScreenName());

                loadData();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);

                System.out.println("NewTweetActivity onFailure: " + errorResponse.toString());
            }

        });

        //System.out.println("id: " + userMe.getUid() + ",name: " + userMe.getName() + ", screename: " + userMe.getScreenName() + ",url: " + userMe.getProfileImageUrl());

        setUpViews();

    }

    private void setUpViews(){

        ivMyImage = (ImageView)findViewById(R.id.iv_myimage);
        etTweet = (EditText)findViewById(R.id.etNewTweet);
        tvScreenName = (TextView)findViewById(R.id.tvScreename);


    }

    private void loadData(){

        //insert image using picasso
        Picasso.with(this).load(userMe.getProfileImageUrl()).into(ivMyImage);
        tvScreenName.setText("@" + userMe.getScreenName());

    }

    public void onNewTweet(View view){


        String tweet = etTweet.getText().toString();
        if(!tweet.isEmpty()) {
            client.postTweet(tweet, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                   // super.onSuccess(statusCode, headers, response);

                    Tweet tweetObj = Tweet.fromJson(response);
                    Intent retData = new Intent();
                    retData.putExtra("tweetObj",tweetObj);

                    // Activity finished ok, return the data
                    setResult(RESULT_OK, retData); // set result code and bundle data for response
                    finish(); // closes the activity, pass data to parent
                }

                @Override
                public void onFailure(int statusCode, Header[] headers,  Throwable throwable, JSONObject errorResponse) {
                    // super.onFailure(statusCode, headers, responseString, throwable);

                    System.out.println("OnNewTweet Failure: " + errorResponse.toString());

                }
            });
        }


    }

    public void onTweetCancel(View view){

        // Activity finished ok, return the data
        setResult(RESULT_CANCELED, null); // set result code and bundle data for response
        finish();
    }
}
