package com.ryanmord.livefrontproj;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.ryanmord.livefrontproj.activity.BaseActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.registerIdlingResources;
import static android.support.test.espresso.Espresso.unregisterIdlingResources;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Tests FeedFragment functionality
 */
@RunWith(AndroidJUnit4.class)
public class TestFeedFragment {

    @Rule
    public ActivityTestRule<BaseActivity> mActivityRule = new ActivityTestRule<>(BaseActivity.class);

    /**
     * Tests the initial loading and presentation of data
     *
     * @throws Exception    Exception
     */
    @Test
    public void testLayoutAndDataLoad() throws Exception {
        SystemClock.sleep(3000);
        //Base components
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
        onView(withId(R.id.toolbar_logo)).check(matches(isDisplayed()));
        onView(withId(R.id.feed_main_recycler)).check(matches(isDisplayed()));

        //Individual recycler items
        onView(withId(R.id.feed_main_recycler)).check(matches(hasDescendant(withId(R.id.item_title))));
        onView(withId(R.id.feed_main_recycler)).check(matches(hasDescendant(withId(R.id.item_image))));
        onView(withId(R.id.feed_main_recycler)).check(matches(hasDescendant(withId(R.id.item_date))));
    }

    /**
     * Tests the SwipeRefresh layout of FeedFragment
     *
     * @throws Exception    Exception
     */
    @Test
    public void testSwipeRefresh() throws Exception {
        onView(withId(R.id.feed_main_recycler)).check(matches(isDisplayed()));
        onView(withId(R.id.feed_swipe_refresh)).perform(swipeDown());
        onView(withId(R.id.feed_main_recycler)).check(matches(isDisplayed()));
    }


}
