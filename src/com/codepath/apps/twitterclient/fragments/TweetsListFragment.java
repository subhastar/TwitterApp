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
import com.codepath.apps.twitterclient.TwitterClientApp;
import com.codepath.apps.twitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class TweetsListFragment extends Fragment {
	private long maxId = 0;
	private long sinceId = Long.MAX_VALUE;
	private TweetsAdapter tweetsAdapter;
	private ArrayList<Tweet> tweets;
	private PullToRefreshListView lvTweets;
	
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
		
		final JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
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
				Log.d("DEBUG", "fail case");
			}
		};
		
		TwitterClientApp.getRestClient().getHomeTimeline(handler, null);
		
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				TwitterClientApp.getRestClient().getHomeTimeline(handler, maxId);
			}
		});
		
		lvTweets.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				Log.d("DEBUG", "calling load new tweets");
				loadNewTweets();
			}
		});
	}
	
	public void loadNewTweets() {
		final JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
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
		
		TwitterClientApp.getRestClient().getHomeTimelineSince(handler, sinceId);
	}
}
