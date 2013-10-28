package com.codepath.apps.twitterclient;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	private static final int COUNT = 20;
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
    public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "gNXEwEGdoLVHcCVHvN39nQ";       // Change this
    public static final String REST_CONSUMER_SECRET = "ApEnUIZhSxnyzKoWpgeGuWn9Gh6UaWtJk06y7nHYo"; // Change this
    public static final String REST_CALLBACK_URL = "oauth://mytwitterapp"; // Change this (here and in manifest)
    
    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }
    
    public void getMyInfo(AsyncHttpResponseHandler handler) {
    	String url = getApiUrl("account/verify_credentials.json");
    	client.get(url, null, handler);
    }
    
    public void getHomeTimeline(AsyncHttpResponseHandler handler, RequestParams params) {
    	String url = getApiUrl("statuses/home_timeline.json");
    	client.get(url, params, handler);
    }
    
    public void getHomeTimelineSince(AsyncHttpResponseHandler handler, long sinceId) {
    	RequestParams params = new RequestParams();
    	params.put("since_id", Long.toString(sinceId));
    	getHomeTimeline(handler, params);
    }
    
    public void getHomeTimeline(AsyncHttpResponseHandler handler, long maxId) {
    	RequestParams params = new RequestParams();
    	params.put("count", Integer.toString(COUNT));
    	params.put("max_id", Long.toString(maxId));
    	getHomeTimeline(handler, params);
    }
    
    public void getMentionsTimeline(AsyncHttpResponseHandler handler, RequestParams params) {
    	String url = getApiUrl("statuses/mentions_timeline.json");
    	client.get(url, params, handler);
    }
    
    public void getMentionsTimelineSince(AsyncHttpResponseHandler handler, long sinceId) {
    	RequestParams params = new RequestParams();
    	params.put("since_id", Long.toString(sinceId));
    	getMentionsTimeline(handler, params);
    }
    
    public void getMentionsTimeline(AsyncHttpResponseHandler handler, long maxId) {
    	RequestParams params = new RequestParams();
    	params.put("count", Integer.toString(COUNT));
    	params.put("max_id", Long.toString(maxId));
    	getMentionsTimeline(handler, params);
    }
    
    
    public void getUserTimeline(AsyncHttpResponseHandler handler, RequestParams params) {
    	String url = getApiUrl("statuses/user_timeline.json");
    	client.get(url, params, handler);
    }
    
    public void getUserTimelineSince(AsyncHttpResponseHandler handler, long sinceId) {
    	RequestParams params = new RequestParams();
    	params.put("since_id", Long.toString(sinceId));
    	getUserTimeline(handler, params);
    }
    
    public void getUserTimeline(AsyncHttpResponseHandler handler, long maxId) {
    	RequestParams params = new RequestParams();
    	params.put("count", Integer.toString(COUNT));
    	params.put("max_id", Long.toString(maxId));
    	getUserTimeline(handler, params);
    }
    
    public void postTweet(AsyncHttpResponseHandler handler, String tweet) {
    	String url = getApiUrl("statuses/update.json");
    	RequestParams params = new RequestParams();
    	params.put("status", tweet);
    	client.post(url, params, handler);
    }
    
    // CHANGE THIS
    // DEFINE METHODS for different API endpoints here
    public void getInterestingnessList(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("format", "json");
        client.get(apiUrl, params, handler);
    }
    
    /* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
     * 	  i.e getApiUrl("statuses/home_timeline.json");
     * 2. Define the parameters to pass to the request (query or body)
     *    i.e RequestParams params = new RequestParams("foo", "bar");
     * 3. Define the request method and make a call to the client
     *    i.e client.get(apiUrl, params, handler);
     *    i.e client.post(apiUrl, params, handler);
     */
}