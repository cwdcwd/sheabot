package com.newpointdesigns.tweetfeed;

import twitter4j.*;

import java.util.LinkedList;
import java.util.List;

public class TweetFeed
{
	private Twitter twitter;
	private LinkedList<String> tweet_list;
	private String searchTerm = "#thanksobama";
	public TweetFeed()
	{
		this.twitter = new TwitterFactory().getInstance();
		this.tweet_list = this.searchTweets(this.searchTerm);
	}
	
	public String getTweet() {
		String last_tweet = tweet_list.pollLast();
		if (last_tweet == null) {
			this.tweet_list = this.searchTweets(this.searchTerm);
			return this.getTweet();
		}
		return last_tweet;
	}
	
	private LinkedList<String> searchTweets(String strQuery) {
		int i = 0;
		LinkedList<String> tweet_list = new LinkedList<String>();
        try {
            Query query = new Query(strQuery);
            QueryResult result;
            do {
                result = this.twitter.search(query);
                List<Status> tweets = result.getTweets();
                for (Status tweet : tweets) {
                    tweet_list.add(tweet.getText());
                }
                i++;
            } while ((query = result.nextQuery()) != null && i < 10);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        }
        return tweet_list;
	}
	
	public static void main(String[] args)
	{
		TweetFeed tweetFeed = new TweetFeed();
		System.out.println(tweetFeed.getTweet());
	}
	
}
