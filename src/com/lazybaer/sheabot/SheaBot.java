package com.lazybaer.sheabot;

import java.util.Random;
import com.newpointdesigns.tweetfeed.*;

public class SheaBot {
	public SheaBot() {

	}

	public static void main(String[] args) {
		// new SheaBot();
		Robotnic bot = new Robotnic();
		TweetFeed twit = new TweetFeed();
		Random generator = new Random();
		int j;
		for (int i = 0; i < 4; i++) {
			j = generator.nextInt(10000);
			bot.sendMessage(twit.getTweet());
			try {
				Thread.sleep(j);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}
	}

}
