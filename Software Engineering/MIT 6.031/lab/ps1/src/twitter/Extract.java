/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.time.Instant;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {
	
	private static final String validCharacter = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890_";

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
    	if(tweets == null) {
    		throw new RuntimeException("list of tweets can not be null");
    	}
    	
    	Instant start = tweets.get(0).getTimestamp();
    	Instant end = tweets.get(0).getTimestamp();
    	
    	
    	for(Tweet tweet : tweets) {
    		Instant curTweetTime = tweet.getTimestamp();
    		if(curTweetTime.isBefore(start)) {
    			start = curTweetTime;
    		} else if(curTweetTime.isAfter(end)) {
    			end = curTweetTime;
    		}
    	}
    	
    	Timespan timespan = new Timespan(start, end);
    	
    	return timespan;
        
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
    	if(tweets == null) {
    		throw new RuntimeException("list of tweets can not be null");
    	}
    	
    	Set<String> res = new HashSet<>();
    	
    	for(Tweet tweet : tweets){
    		String text = tweet.getText();
    		String[] words = text.split(" ");
    		for(String word : words) {
    			boolean isNoValid = false;
    			if(word == null || word.charAt(0)!='@') continue;
    			
    			for(int i=1; i<word.length(); i++) {
    				String c = word.charAt(i) + "";
    				if(!validCharacter.contains(c)) {
    					isNoValid = true;
    					break;
    				}
    			}
    			if(isNoValid) continue;
    			String username = word.substring(1);
    			res.add(username.toLowerCase());
    				
    		}
   
    	}
    	return res;
    }

}
