/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * testGetTimespan:
     * 		partition on size
     * 			tweets.size() == 0 
     * 			tweets.size() > 1
     * 		partition on tweet's earliest tweet
     * 			index = 0
     * 			index >= 1
     * 			
     * 	testGetMetionedUser:
     * 		contain special situation
     * 			contains abcd@mit.edu
     * 			no contain ...
     * 		# of mentioned users
     * 			n is empty
     * 			n == 1 
     * 			n >= 2
     * 		test case-insensitive
     * 			have same except for case name
     *			do not have ... 
     * 
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    // other 2 timestamp
    private static final Instant d3 = Instant.parse("2016-02-16T09:00:00Z");
    private static final Instant d4 = Instant.parse("2016-02-16T11:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest @bbit talk in 30 minutes #hype", d2);
    // other 2 tweet
    private static final Tweet tweet3 = new Tweet(3, "abcd", "Hello World! abcd@mit.edu", d3);
    private static final Tweet tweet4 = new Tweet(4, "cccd", "@abcd Hi @ABcd", d4);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // size == 1
    @Test 
    public void testGetTimespanOneTweet() {
    	Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1));
    	
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d1, timespan.getEnd());
    }
    
    // size > 1 & earliest tweet index == 0
    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    // size > 1 & earliest tweet index == 1
    @Test
    public void testGetTimespanTwoTweetsReverseOrder() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet2, tweet1));
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    
    // cover n = 0, n = 1, n >= 2 & user name case-insensitive
    @Test
    public void testGetMentionedUsers() {
    	List<Tweet> tweets = new ArrayList<>();
        Set<String> mentionedUsers = new HashSet<>();
       
        // n == 0
        tweets.add(tweet1);
        mentionedUsers =  Extract.getMentionedUsers(tweets);
        assertTrue("expected empty set", mentionedUsers.isEmpty());
        
        // n == 1
        tweets.add(tweet2);
        mentionedUsers =  Extract.getMentionedUsers(tweets);
        assertTrue(mentionedUsers.contains("bbit"));
        assertEquals(1, mentionedUsers.size());
       
        
        // n >= 2
        tweets.add(tweet4);
        mentionedUsers =  Extract.getMentionedUsers(tweets);
        assertTrue(mentionedUsers.contains("bbit"));
        assertTrue(mentionedUsers.contains("abcd"));
        assertEquals(2, mentionedUsers.size());
        
    }
    
    @Test
    public void testSpecialUsers() {
    	Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet3));
    	
    	assertTrue("expected empty set", mentionedUsers.isEmpty());
    }
    

    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */

}
