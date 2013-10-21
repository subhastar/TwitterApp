package com.codepath.apps.twitterclient.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tweet extends BaseModel {
    private User user;

    public User getUser() {
        return user;
    }

    public String getBody() {
        return getString("text");
    }

    public long getId() {
        return getLong("id");
    }

    public boolean isFavorited() {
        return getBoolean("favorited");
    }

    public boolean isRetweeted() {
        return getBoolean("retweeted");
    }
    
    public String getTimestamp() {
    	String timestamp = getString("created_at");
    	DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy", Locale.ENGLISH);
    	dateFormat.setLenient(false);
		Date created = null;
		try {
			created = dateFormat.parse(timestamp);
		} catch (Exception e) {
			return null;
		}
 
		Date today = new Date();
 
		Long duration = today.getTime() - created.getTime();
 
		int second = 1000;
		int minute = second * 60;
		int hour = minute * 60;
		int day = hour * 24;
 
		if (duration < second * 7) {
			return "right now";
		}
 
		if (duration < minute) {
			int n = (int) Math.floor(duration / second);
			return n + "s";
		}
 
		if (duration < minute * 2) {
			return "1m";
		}
 
		if (duration < hour) {
			int n = (int) Math.floor(duration / minute);
			return n + "m";
		}
 
		if (duration < hour * 2) {
			return "1h";
		}
 
		if (duration < day) {
			int n = (int) Math.floor(duration / hour);
			return n + "h";
		}
		if (duration > day && duration < day * 2) {
			return "yesterday";
		}
 
		if (duration < day * 365) {
			int n = (int) Math.floor(duration / day);
			return n + " days ago";
		} else {
			return "over a year ago";
		}
    }

    public static Tweet fromJson(JSONObject jsonObject) {
        Tweet tweet = new Tweet();
        try {
            tweet.jsonObject = jsonObject;
            tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return tweet;
    }

    public static ArrayList<Tweet> fromJson(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());

        for (int i=0; i < jsonArray.length(); i++) {
            JSONObject tweetJson = null;
            try {
                tweetJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            Tweet tweet = Tweet.fromJson(tweetJson);
            if (tweet != null) {
                tweets.add(tweet);
            }
        }

        return tweets;
    }
}