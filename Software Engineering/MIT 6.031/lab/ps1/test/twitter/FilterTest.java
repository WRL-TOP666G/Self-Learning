/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FilterTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * 
     * 		testWrittenByUsername
     * 			nobody
     * 			A person, one tweet
     * 			A person, two tweets
     * 
     * 		testInTimespan
     * 			start and end time are the same
     * 			... not the same
     * 
     * 			tweets are not in the between
     * 			... are ...
     * 
     * 		testContaining
     * 			non any case
     * 			all lower cases
     * 			case-insesitive
     * 	
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T09:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "bbitdiddle", "#hype 12345", d3);
    private static final Tweet tweet4 = new Tweet(4, "bbitdiddle", "Talk 12345", d3);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testWrittenByUsername() {
    	List<Tweet> list = new ArrayList<>();
    	list.add(tweet1);
    	list.add(tweet2);
    	
    	// n == 0
    	List<Tweet> writtenBy = Filter.writtenBy(list, "nobody");
        
        assertEquals("expected singleton list", 0, writtenBy.size());
        assertFalse("expected list to contain tweet", writtenBy.contains(tweet1));
     
        // n == 1
    	writtenBy = Filter.writtenBy(list, "alyssa");
        
        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
        
        // n >= 2
        list.add(tweet3);
    	writtenBy = Filter.writtenBy(list, "bbitdiddle");
        
        assertEquals("expected singleton list", 2, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet2));
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet3));
    	
       
    }
    
    @Test
    public void testInTimespan() {
    	
    	// Same start & end time and not in the between
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = testStart;
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));
        
        assertTrue("expected non-empty list", inTimespan.isEmpty());
        assertFalse("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", -1, inTimespan.indexOf(tweet1));
        
        
        // Same start & end time and in the between
        inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2, tweet3), new Timespan(testStart, testEnd));
        
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet3)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet3));
        
        
        // Not the same start & end time and not in the between
        testEnd = Instant.parse("2016-02-17T12:00:00Z");
        inTimespan = Filter.inTimespan(Arrays.asList(tweet3), new Timespan(testStart, testEnd));
        
        assertTrue("expected non-empty list", inTimespan.isEmpty());
        assertFalse("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", -1, inTimespan.indexOf(tweet1));
        
        
        // Not the same start & end time and in the between
        inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2, tweet3), new Timespan(testStart, testEnd));
        
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));
        assertEquals("expected same order", 1, inTimespan.indexOf(tweet2));
    }
    
    @Test
    public void testContaining() {
    	// all lower cases
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2, tweet3, tweet4), Arrays.asList("no_any_case"));
        
        assertTrue("expected non-empty list", containing.isEmpty());
        assertEquals("expected same order", -1, containing.indexOf(tweet1));
        
    	// all lower cases
        containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("talk"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
        
        
    	// cases-insensitive
        containing = Filter.containing(Arrays.asList(tweet1, tweet2, tweet3, tweet4), Arrays.asList("talk"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2, tweet4)));
        assertEquals("expected same order", 2, containing.indexOf(tweet4));
    }

    /*
     * Warning: all the tests you write here must be runnable against any Filter
     * class that follows the spec. It will be run against several staff
     * implementations of Filter, which will be done by overwriting
     * (temporarily) your version of Filter with the staff's version.
     * DO NOT strengthen the spec of Filter or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Filter, because that means you're testing a stronger
     * spec than Filter says. If you need such helper methods, define them in a
     * different class. If you only need them in this test class, then keep them
     * in this test class.
     */

}
