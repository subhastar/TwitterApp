package com.codepath.apps.twitterclient.fragments;

import android.app.Activity;
import android.util.Log;

import com.codepath.apps.twitterclient.TwitterClientApp;

public class UserTimelineFragment extends TweetsListFragment {
	private ScreenNameProvider provider;
	
	public interface ScreenNameProvider {
		public String getScreenName();
	}
	
	// Fetches the first set of base tweets
	@Override
	public void fetchBaseTweets() {
		TwitterClientApp.getRestClient().getUserTimeline(addOlderTweetsHandler, provider.getScreenName());
	}

	// This method loads older tweets from the bottom of the feed
	@Override
	public void loadOlderTweets() {
		TwitterClientApp.getRestClient().getUserTimeline(addOlderTweetsHandler, maxId, provider.getScreenName());
	}

	// This method loads newer tweets from the top of the feed
	@Override
	public void loadNewTweets() {
		TwitterClientApp.getRestClient().getUserTimelineSince(addNewerTweetsHandler, sinceId, provider.getScreenName());
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof ScreenNameProvider) {
			provider = (ScreenNameProvider) activity;
		}
	}
}
