package com.codepath.apps.mysimpletweets.Activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.mysimpletweets.Adapters.SmartFragmentStatePagerAdapter;
import com.codepath.apps.mysimpletweets.Adapters.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.Listeners.EndlessScrollListener;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.fragments.HomeTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.MentionsTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.TweetsListFragment;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity {

//    private TwitterClient client;
//    private TweetsArrayAdapter tweetsAdapter;
//    private ArrayList<Tweet> tweets;
//    private ListView lvTweets;
    //private User userMe;
    private SwipeRefreshLayout swipeContainer;
    private ViewPager vpPager;
    private SmartFragmentStatePagerAdapter tweetsPagerAdatper;

  //  private TweetsListFragment fragmentTweetsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

//        client = TwitterApplication.getRestClient(); //get a singleton rest client

        setUpViews();

//        tweets = new ArrayList<>();
//
//        tweetsAdapter = new TweetsArrayAdapter(this, tweets);
//
//        lvTweets.setAdapter(tweetsAdapter);



//        if(savedInstanceState == null)
//            fragmentTweetsList = (TweetsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_timeline);

       // populateTimeline(false);

        //Get view pager

        vpPager = (ViewPager)findViewById(R.id.viewpager);
        tweetsPagerAdatper = new TweetsPagerAdapter(getSupportFragmentManager());

        //set the viewpager adapter for the pager
        vpPager.setAdapter(tweetsPagerAdatper);

        //find the sliding tabstrip
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip)findViewById(R.id.tabs);

        //Attach the viewpager to the tabstrip
        tabStrip.setViewPager(vpPager);

    }

    private void setUpViews(){


       // swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

//        lvTweets = (ListView)findViewById(R.id.lvTweets);

////        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
////
////            @Override
////            public void onRefresh() {
////                // Your code to refresh the list here.
////                // Make sure you call swipeContainer.setRefreshing(false)
////                // once the network request has completed successfully.
////                fetchTimelineAsync(0);
//            }
//        });


//        lvTweets.setOnScrollListener(new EndlessScrollListener() {
//            @Override
//            public boolean onLoadMore(int page, int totalItemsCount) {
//                // Triggered only when new data needs to be appended to the list
//                // Add whatever code is needed to append new items to your AdapterView
//                //customLoadMoreDataFromApi(page);
//                customLoadMoreDataFromApi(totalItemsCount);
//                return true; // ONLY if more data is actually being loaded; false otherwise.
//            }
//        });

//        // Configure the refreshing colors
//        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);

    }

    // Append more data into the adapter
    public void customLoadMoreDataFromApi(int offset) {
        // This method probably sends out a network request and appends new data items to your adapter.
        // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
        // Deserialize API response and then construct new objects to append to the adapter
        // imageResultAdapter.

        System.out.println("Scrolling....offset:" + offset);

//        if(isNetworkAvailable()==false) {
//
//            etText.setText("!!! Search failed!. No Network Connection!!!");
//            return;
//        }

       // populateTimeline(true);
    }

//    public void fetchTimelineAsync(int page) {
//        client.getHomeTimeline(false, new JsonHttpResponseHandler() {
//            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                // Remember to CLEAR OUT old items before appending in the new ones
//                tweetsAdapter.clear();
//                // ...the data has come back, add new items to your adapter...
//                tweets.addAll(Tweet.fromJsonArray(response));
//                // Now we call setRefreshing(false) to signal refresh has finished
//                swipeContainer.setRefreshing(false);
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
////                super.onFailure(statusCode, headers, throwable, errorResponse);
//
//                System.out.println("SwipeRefresh onFailure: " + errorResponse.toString());
//            }
//        });
//    }



//    private void populateTimeline(boolean scroll){
//
//        client.getHomeTimeline(scroll, new JsonHttpResponseHandler() {
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                // super.onSuccess(statusCode, headers, response);
//
//                System.out.println("OnScroll onSuccess: ");
//                fragmentTweetsList.addAll(Tweet.fromJsonArray(response));
//
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
////                super.onFailure(statusCode, headers, throwable, errorResponse);
//
//                System.out.println("OnScroll onFailure: " + errorResponse.toString());
//            }
//
//
//        });
//
//        client.getVerifyCredentials(new JsonHttpResponseHandler() {
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//               // super.onSuccess(statusCode, headers, response);
//
//                userMe = User.fromJson(response);
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers,  Throwable throwable, JSONObject errorResponse) {
//               // super.onFailure(statusCode, headers, responseString, throwable);
//
//                System.out.println("Verify Credentials onFailure: " + errorResponse.toString());
//
//            }
//        });
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_compose_tweet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.compose_tweet) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onComposeTweetAction(MenuItem mi){

        // Toast.makeText(getApplicationContext(), "onComposeAction", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, NewTweetActivity.class);

        //intent.putExtra("user", userMe);

        startActivityForResult(intent, 200);
        //startActivity(intent);


    }

   public void onProfileView(MenuItem mi){

       //Launch the profile vie

       //System.out.println("TimelineActivity:onProfileView***");
       Intent i = new Intent(this,ProfileActivity.class);
       //i.putExtra("screen_name", "null");
       startActivity(i);
   }



    public void onReplyAction(View view){

        Toast.makeText(getApplicationContext(), "onReplyAction", Toast.LENGTH_SHORT).show();

    }

    public void onRetweetAction(View view){

        Toast.makeText(getApplicationContext(), "onRetweetAction", Toast.LENGTH_SHORT).show();

    }

    public void onLikesAction(View view){

        Toast.makeText(getApplicationContext(), "onLikesAction", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(data != null) {
            Tweet tweetObj = (Tweet) data.getSerializableExtra("tweetObj");

            HomeTimelineFragment homeTimelineFragment = (HomeTimelineFragment) tweetsPagerAdatper.getRegisteredFragment(0);

            homeTimelineFragment.insert(tweetObj);

            vpPager.setCurrentItem(0);

            //tweetsAdapter.insert(tweetObj, 0);

        }
//            System.out.println("TweetsAdapter size before: " + tweetsAdapter.getCount());
//           // tweetsAdapter.add(tweetObj);
//            //tweets.add(tweetObj);
//
//            System.out.println("TweetsAdapter size after: " + tweetsAdapter.getCount());
//            System.out.println("id: " + tweetObj.getUser().getUid() + ",name: " + tweetObj.getUser().getName() + ", screename: " + tweetObj.getUser().getScreenName() + ",url: " + tweetObj.getBody());
//


//        }



    }

    //returns the order of fragments in the view pager
    public class TweetsPagerAdapter extends SmartFragmentStatePagerAdapter {


        private String tabTitles[] = {"Home", "Mentions"};

        //adapter gets the manager insert or remove fragment from activity
        public TweetsPagerAdapter(FragmentManager fm){
            super(fm);


        }


        //the order and creation of fragments within the pager
        @Override
        public Fragment getItem(int position) {

            if(position ==0)
                return new HomeTimelineFragment();
            if(position == 1){
                return new MentionsTimelineFragment();
            }
            else
                return null;
        }

        //return the tab title
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        //how many fragments are to swipe between
        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }
}
