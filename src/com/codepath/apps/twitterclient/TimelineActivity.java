package com.codepath.apps.twitterclient;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.twitterclient.fragments.HomeTimelineFragment;
import com.codepath.apps.twitterclient.fragments.MentionsFragment;

public class TimelineActivity extends FragmentActivity implements TabListener {
	private static int REQUEST_CODE_COMPOSE = 1;
	private static String HOME_TIMELINE_TAG = "HomeTimelineFragment";
	private static String MENTIONS_TIMELINE_TAG = "MentionsTimelineFragment";
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		setupNavigationTabs();
	}
	
	private void setupNavigationTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		
		Tab tabHome = actionBar.newTab().setText("Home")
				.setTag(HOME_TIMELINE_TAG)
				.setIcon(R.drawable.ic_home)
				.setTabListener(this);
		
		Tab tabMentions = actionBar.newTab().setText("Mentions")
				.setTag(MENTIONS_TIMELINE_TAG)
				.setIcon(R.drawable.ic_mention)
				.setTabListener(this);
		
		actionBar.addTab(tabHome);
		actionBar.addTab(tabMentions);
		actionBar.selectTab(tabHome);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.action_compose:
			compose();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void compose() {
		Intent i = new Intent(getApplicationContext(), ComposeActivity.class);
		startActivityForResult(i, REQUEST_CODE_COMPOSE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("DEBUG", "inside on activity result " + requestCode + " " + resultCode);
		if (resultCode == RESULT_OK &&
				requestCode == REQUEST_CODE_COMPOSE) {
			//tweetsListFragment.loadNewTweets();
		}
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		FragmentManager manager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fts = manager.beginTransaction();
		if (tab.getTag().equals(HOME_TIMELINE_TAG)) {
			// set the fragment to home timeline
			fts.replace(R.id.frame_container, new HomeTimelineFragment());
		} else {
			// set the fragment to mentions timeline
			fts.replace(R.id.frame_container, new MentionsFragment());

		}
		fts.commit();
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		
	}
}
