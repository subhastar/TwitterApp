package com.codepath.apps.twitterclient.fragments;

import com.codepath.apps.twitterclient.TwitterClientApp;

public class HomeTimelineFragment extends TweetsListFragment {
	// Fetches the first set of base tweets
	@Override
	public void fetchBaseTweets() {
		TwitterClientApp.getRestClient().getHomeTimeline(addOlderTweetsHandler, null);
	}

	// This method loads older tweets from the bottom of the feed
	@Override
	public void loadOlderTweets() {
		TwitterClientApp.getRestClient().getHomeTimeline(addOlderTweetsHandler, maxId);
	}

	// This method loads newer tweets from the top of the feed
	@Override
	public void loadNewTweets() {
		TwitterClientApp.getRestClient().getHomeTimelineSince(addNewerTweetsHandler, sinceId);
	}
}
