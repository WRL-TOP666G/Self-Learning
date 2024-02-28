/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class SocialNetworkTest {
	
    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     * 
     * testGuessFollowsGraph
     * 		Empty graph
     * 			without any stuff
     * 			no any follower
     * 
     * testInfluencersEmpty
     * 		Graph
     * 			Empty
     * 		Graph
     * 			case insensitive
     * 
     * 
     */
	
	

	// List<Tweet> tweets
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "@bbitdiddle is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "@alyssa rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "bbitdiddle", "#hype @123D 12345", d1);
    private static final Tweet tweet4 = new Tweet(4, "bbitdiddle", "Talk 12345 @123d", d2);
    private static final Tweet tweet5 = new Tweet(5, "123d", "Talk 12345 123d", d2);
    private static final Tweet tweet6 = new Tweet(6, "123d", "Talk 12345 123d @alyssa", d2);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGuessFollowsGraph() {
    	// Empty, without any stuff
    	List<Tweet> tweets = new ArrayList<>();
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
        
        assertTrue("expected empty graph", followsGraph.isEmpty());
        
        // Empty, no follower
        tweets.add(tweet5);
        followsGraph = SocialNetwork.guessFollowsGraph(tweets);
        
        assertTrue("expected empty graph", followsGraph.isEmpty());
        
        
        // No empty
        tweets.add(tweet1);
        tweets.add(tweet2);
        tweets.add(tweet3);
        tweets.add(tweet4);
        followsGraph = SocialNetwork.guessFollowsGraph(tweets);

        assertFalse("expected non-empty graph", followsGraph.isEmpty());
        assertEquals(3, followsGraph.size());
        assertTrue(followsGraph.containsKey("alyssa"));
        assertTrue(followsGraph.containsKey("bbitdiddle"));
        assertTrue(followsGraph.get("alyssa").contains("bbitdiddle"));
        assertTrue(followsGraph.get("bbitdiddle").contains("alyssa"));
        assertTrue(followsGraph.get("123d").contains("bbitdiddle"));
    }
    
    @Test
    public void testInfluencersEmpty() {
    	// Empty, without any stuff
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected empty list", influencers.isEmpty());
        
        // Empty, no follower
        List<Tweet> tweets = new ArrayList<>();
        tweets.add(tweet5);
        followsGraph = SocialNetwork.guessFollowsGraph(tweets);
        influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected empty graph", followsGraph.isEmpty());
        
        // No Empty
        tweets.add(tweet1);
        tweets.add(tweet2);
        tweets.add(tweet3);
        tweets.add(tweet4);
        tweets.add(tweet6);
        followsGraph = SocialNetwork.guessFollowsGraph(tweets);
        influencers = SocialNetwork.influencers(followsGraph);
        
        assertFalse("expected empty graph", followsGraph.isEmpty());
        assertTrue(influencers.contains("123d"));
        assertEquals(0, influencers.indexOf("alyssa"));
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */

}
