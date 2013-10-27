package com.codepath.apps.twitterclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.twitterclient.fragments.TweetsListFragment;

public class TimelineActivity extends FragmentActivity {
	private static int REQUEST_CODE_COMPOSE = 1;
	
	private TweetsListFragment tweetsListFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		tweetsListFragment = (TweetsListFragment) getSupportFragmentManager()
				.findFragmentById(R.id.homeTimelineFragment);
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
			tweetsListFragment.loadNewTweets();
		}
	}
}
