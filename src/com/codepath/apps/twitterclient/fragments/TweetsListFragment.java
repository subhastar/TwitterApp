package com.codepath.apps.twitterclient.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.twitterclient.EndlessScrollListener;
import com.codepath.apps.twitterclient.R;
import com.codepath.apps.twitterclient.TweetsAdapter;
import com.codepath.apps.twitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public abstract class TweetsListFragment extends Fragment {
	private TweetsAdapter tweetsAdapter;
	private ArrayList<Tweet> tweets;
	private PullToRefreshListView lvTweets;
	
	protected JsonHttpResponseHandler addOlderTweetsHandler;
	protected JsonHttpResponseHandler addNewerTweetsHandler;
	protected long maxId = 0;
	protected long sinceId = Long.MAX_VALUE;
	
	
	@Override
	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState) {
		return inf.inflate(R.layout.fragment_tweets_list, parent, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		tweets = new ArrayList<Tweet>();
		lvTweets = (PullToRefreshListView) getActivity().findViewById(R.id.lvTweets);
		tweetsAdapter = new TweetsAdapter(getActivity(), tweets);
		lvTweets.setAdapter(tweetsAdapter);
		
		createHandlers();
		
		// Fetch base tweets
		fetchBaseTweets();
		
		// Scrolling at the bottom should load older tweets
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				loadOlderTweets();
			}
		});
		
		// Pull to refresh should add newer tweets
		lvTweets.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				loadNewTweets();
			}
		});
	}
	
	// Fetches the first set of base tweets
	public abstract void fetchBaseTweets();

	// This method loads older tweets from the bottom of the feed
	public abstract void loadOlderTweets();

	// This method loads newer tweets from the top of the feed
	public abstract void loadNewTweets();

	private void createHandlers() {
		// This handler adds older tweets to the bottom of the feed
		addOlderTweetsHandler = new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonTweets) {
				ArrayList<Tweet> olderTweets = Tweet.fromJson(jsonTweets);
				if (olderTweets.size() == 0) {
					Log.d("DEBUG", "size zero array");
					return;
				}
				tweetsAdapter.addAll(olderTweets);
				maxId = olderTweets.get(olderTweets.size() - 1).getId() - 1;
				sinceId = olderTweets.get(0).getId();
				
				lvTweets.onRefreshComplete();
			}
			
			@Override
			public void onFailure(Throwable t, JSONObject errorResponse) {
				Log.d("DEBUG", "fail case " + errorResponse);
			}
		};

		// This handler adds newer tweets to the top of the feed
		addNewerTweetsHandler = new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonTweets) {
				ArrayList<Tweet> newTweets = Tweet.fromJson(jsonTweets);
				if (newTweets.size() > 0) {
					for(Tweet tweet : newTweets) {
						tweets.add(0, tweet);
					}
					tweetsAdapter.notifyDataSetChanged();
					maxId = newTweets.get(newTweets.size() - 1).getId() - 1;
					sinceId = newTweets.get(0).getId();
				}
				
				lvTweets.onRefreshComplete();
			}
		};
	}
}
