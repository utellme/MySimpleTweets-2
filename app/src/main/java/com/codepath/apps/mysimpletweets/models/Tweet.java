package com.codepath.apps.mysimpletweets.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by dvalia on 10/24/15.
 */
public class Tweet implements Serializable{

    private String body;
    private long uid;
    private String createdAt;
    private User user;

    private static long MAX_ID;


    //take an array of JSON Images and create a list of ImageResults
    public static Tweet fromJson(JSONObject jsonObject){

        Tweet tweet = new Tweet();
        // Process each result in json array, decode and convert to business object

            try {

                tweet.body = jsonObject.getString("text");
                tweet.uid = jsonObject.getLong("id");
                tweet.createdAt = jsonObject.getString("created_at");
                tweet.user = User.fromJson(jsonObject.getJSONObject("user"));

                System.out.println("TweetUser:" + tweet.user.getScreenName() + ", Tweetid:" + tweet.uid );


            } catch (Exception e) {
                e.printStackTrace();

            }


        return tweet;
    }


    public static ArrayList<Tweet> fromJsonArray(JSONArray jsonArray){

        ArrayList<Tweet> tweets = new ArrayList<>();

        for(int i=0; i<jsonArray.length() ; i++){

           try {

               JSONObject tweetObject = jsonArray.getJSONObject(i);

               Tweet tweet = Tweet.fromJson(tweetObject);

               MAX_ID = tweet.getUid();

               tweets.add(tweet);
           }
           catch(JSONException e) {
               e.printStackTrace();
               continue;
           }

        }

        return tweets;
    }


    public static long getMaxId(){

        return MAX_ID;

    }


    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
