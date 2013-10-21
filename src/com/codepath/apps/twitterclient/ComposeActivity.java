package com.codepath.apps.twitterclient;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;

public class ComposeActivity extends Activity {
	private EditText etTweetContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		
		etTweetContent = (EditText) findViewById(R.id.etTweetContent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose, menu);
		return true;
	}
	
	public void postTweet(View v) {
		String tweetContent = etTweetContent.getText().toString();
		
		final JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject placeholder) {
				setResult(RESULT_OK, new Intent());
				finish();
			}
		};
		
		TwitterClientApp.getRestClient().postTweet(handler, tweetContent);
	}

}
