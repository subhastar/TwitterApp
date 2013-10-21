package com.codepath.apps.twitterclient;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

import com.codepath.apps.twitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {
	private long maxId = Long.MAX_VALUE;
	private TweetsAdapter tweetsAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		ListView lvTweets = (ListView) findViewById(R.id.lvTweets);
		tweetsAdapter = new TweetsAdapter(getBaseContext(), new ArrayList<Tweet>());
		lvTweets.setAdapter(tweetsAdapter);
		
		final JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonTweets) {
				ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
				tweetsAdapter.addAll(tweets);
				maxId = tweets.get(tweets.size() - 1).getId() - 1;
			}
		};
		
		TwitterClientApp.getRestClient().getHomeTimeline(handler, null);
		
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				TwitterClientApp.getRestClient().getHomeTimeline(handler, maxId);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}

}
