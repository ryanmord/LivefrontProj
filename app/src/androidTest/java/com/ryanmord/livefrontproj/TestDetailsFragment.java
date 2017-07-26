package com.ryanmord.livefrontproj;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.SystemClock;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.ryanmord.livefrontproj.activity.BaseActivity;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasCategories;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

/**
 * Tests functionality of DetailsFragment
 */
@RunWith(AndroidJUnit4.class)
public class TestDetailsFragment {

    @Rule
    public ActivityTestRule<BaseActivity> mActivityRule = new ActivityTestRule<>(BaseActivity.class);

    /**
     * Setup and initialize Intent interceptor
     */
    @Before
    public void setup() {
        Intents.init();
        onView(withId(R.id.feed_main_recycler)).perform(actionOnItemAtPosition(0, click()));
    }

    /**
     * Releases Intent interceptor after tests run
     */
    @After
    public void tearDown() {
        Intents.release();
    }


    /**
     * Tests simple navigation and data layout in view
     *
     * @throws Exception    Exception
     */
    @Test
    public void testItemNavigationAndLayout() throws Exception {
        onView(withId(R.id.details_header_image)).check(matches(isDisplayed()));
        onView(withId(R.id.details_title)).check(matches(isDisplayed()));
        onView(withId(R.id.details_subtext)).check(matches(isDisplayed()));
        onView(withId(R.id.details_summary)).check(matches(isDisplayed()));
        onView(withId(R.id.details_fab)).check(matches(isDisplayed()));

        onView(withContentDescription("Navigate up")).perform(click());
        onView(withId(R.id.feed_main_recycler)).check(matches(isDisplayed()));
    }


    /**
     * Tests navigation of FloatingActionButton click
     *
     * @throws Exception    Exception
     */
    @Test
    public void testDetailsFabNavigation() throws Exception {

        intending(hasCategories(hasItem(equalTo(Intent.CATEGORY_BROWSABLE))));

        onView(withId(R.id.details_fab)).perform(click());
        Intents.assertNoUnverifiedIntents();

    }


}
