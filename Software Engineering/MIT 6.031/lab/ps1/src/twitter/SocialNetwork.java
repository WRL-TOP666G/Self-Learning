/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * SocialNetwork provides methods that operate on a social network.
 * 
 * A social network is represented by a Map<String, Set<String>> where map[A] is
 * the set of people that person A follows on Twitter, and all people are
 * represented by their Twitter usernames. Users can't follow themselves. If A
 * doesn't follow anybody, then map[A] may be the empty set, or A may not even exist
 * as a key in the map; this is true even if A is followed by other people in the network.
 * Twitter usernames are not case sensitive, so "ernie" is the same as "ERNie".
 * A username should appear at most once as a key in the map or in any given
 * map[A] set.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class SocialNetwork {
	
    static class Follower{
    	String username;
    	int numberOfFollowers;
    	public Follower(String username, int numberOfFollowers) {
    		this.username = username;
    		this.numberOfFollowers = numberOfFollowers;
    	}
    }

	private static final String validCharacter = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890_";
    /**
     * Guess who might follow whom, from evidence found in tweets.
     * 
     * @param tweets
     *            a list of tweets providing the evidence, not modified by this
     *            method.
     * @return a social network (as defined above) in which Ernie follows Bert
     *         if and only if there is evidence for it in the given list of
     *         tweets.
     *         One kind of evidence that Ernie follows Bert is if Ernie
     *         @-mentions Bert in a tweet. This must be implemented. Other kinds
     *         of evidence may be used at the implementor's discretion.
     *         All the Twitter usernames in the returned social network must be
     *         either authors or @-mentions in the list of tweets.
     */
    public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {
    	if(tweets == null) {
    		throw new RuntimeException("list of tweets can not be null");
    	}

    	Map<String, Set<String>> res = new HashMap<>();
    	for(Tweet tweet : tweets) {
    		String author = tweet.getAuthor().toLowerCase();
    		String follower = null;
    		String[] words = tweet.getText().split(" ");
    		for(String word : words) {
    			if(word!=null && word.charAt(0)=='@') {
    				follower = word.substring(1).toLowerCase();
    			}
    		}
  
    		if(follower != null) {
    			if(!res.containsKey(follower)) res.put(follower, new HashSet<>());
    			res.get(follower).add(author);
    		}
    		
    	}
    	
    	return res;
    }

    /**
     * Find the people in a social network who have the greatest influence, in
     * the sense that they have the most followers.
     * 
     * @param followsGraph
     *            a social network (as defined above)
     * @return a list of all distinct Twitter usernames in followsGraph, in
     *         descending order of follower count.
     */
    public static List<String> influencers(Map<String, Set<String>> followsGraph) {
    	if(followsGraph == null) {
    		throw new RuntimeException("list of tweets can not be null");
    	}
    	
    	List<Follower> listFollowers = new ArrayList<>();
    	
    	for(String key : followsGraph.keySet()) {
    		int numberOfFollowers = followsGraph.get(key).size();
    		Follower follower = new Follower(key, numberOfFollowers);
    		listFollowers.add(follower);
    		
    	}
    	
    	Collections.sort(listFollowers, (a,b)->(b.numberOfFollowers - a.numberOfFollowers));
    	List<String> res = new ArrayList<>();
    	for(Follower f: listFollowers) {
    		res.add(f.username);
    	}
    	
    	return res;
    }
    


}


