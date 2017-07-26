package com.ryanmord.livefrontproj;

import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;

import com.ryanmord.livefrontproj.util.DateUtils;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getContext;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.*;

/**
 * Tests for the DateUtils class
 */
@RunWith(AndroidJUnit4.class)
public class TestUtils {

    /**
     * Tests that expected strings are returned from durationSinceDate method
     *
     * @throws Exception    Exception
     */
    @Test
    public void testDateUtils() throws Exception {
        DateTime testDate = new DateTime();

        //test now
        assertEquals("Now", DateUtils.durationSinceDate(getTargetContext(), testDate));

        //test minute
        testDate = testDate.minusMinutes(1);
        assertEquals("1 minute ago", DateUtils.durationSinceDate(getTargetContext(), testDate));

        //test minutes
        testDate = testDate.minusMinutes(1);
        assertEquals("2 minutes ago", DateUtils.durationSinceDate(getTargetContext(), testDate));

        //test hour
        testDate = testDate.minusHours(1);
        assertEquals("1 hour ago", DateUtils.durationSinceDate(getTargetContext(), testDate));

        //test hours
        testDate = testDate.minusHours(1);
        assertEquals("2 hours ago", DateUtils.durationSinceDate(getTargetContext(), testDate));


        //test day
        testDate = testDate.minusDays(1);
        assertEquals("1 day ago", DateUtils.durationSinceDate(getTargetContext(), testDate));

        //test days
        testDate = testDate.minusDays(1);
        assertEquals("2 days ago", DateUtils.durationSinceDate(getTargetContext(), testDate));
    }

}
