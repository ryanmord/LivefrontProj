package com.ryanmord.livefrontproj;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.ryanmord.livefrontproj.activity.BaseActivity;
import com.ryanmord.livefrontproj.util.NetworkChange;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.registerIdlingResources;
import static android.support.test.espresso.Espresso.unregisterIdlingResources;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * Created by ryanmord on 7/25/17.
 */

@RunWith(AndroidJUnit4.class)
public class TestFeedFragment {

    @Rule
    public ActivityTestRule<BaseActivity> mActivityRule = new ActivityTestRule<>(BaseActivity.class);



    @Before
    public void setup() {
    }

    @After
    public void tearDown() {
    }


    @Test
    public void testLayoutAndDataLoad() throws Exception {
        setMobileDataEnabled(false);
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

    @Test
    public void testSwipeRefresh() throws Exception {
        onView(withId(R.id.feed_main_recycler)).check(matches(isDisplayed()));
        onView(withId(R.id.feed_swipe_refresh)).perform(swipeDown());
        onView(withId(R.id.feed_main_recycler)).check(matches(isDisplayed()));
    }


    @Test
    public void testRefreshWithWifiDisabled() throws Exception {
        NetworkChange n = new NetworkChange(mActivityRule.getActivity(), false);

        registerIdlingResources(n);
        setMobileDataEnabled(false);
        while(!isNetworkAvailable(mActivityRule.getActivity())) {
            Log.d("STAT", "BLACHHHHHH");
        }
        unregisterIdlingResources(n);

        onView(withId(R.id.feed_main_recycler)).perform(swipeDown());
        onView(allOf(withId(android.support.design.R.id.snackbar_text))) .check(matches(isDisplayed()));

        n = new NetworkChange(mActivityRule.getActivity(), true);
        registerIdlingResources(n);
        setMobileDataEnabled(true);
        unregisterIdlingResources(n);

    }


    @Test
    public void testSwipeRefreshAgain() throws Exception {
        onView(withId(R.id.feed_main_recycler)).check(matches(isDisplayed()));
        onView(withId(R.id.feed_main_recycler)).check(matches(hasDescendant(withId(R.id.item_title))));
        onView(withId(R.id.feed_swipe_refresh)).perform(swipeDown());
        onView(withId(R.id.feed_main_recycler)).check(matches(isDisplayed()));
        onView(withId(R.id.feed_main_recycler)).check(matches(hasDescendant(withId(R.id.item_title))));
    }

    private void setMobileDataEnabled(boolean enabled) {
        WifiManager wifiManager = (WifiManager) mActivityRule.getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(enabled);
    }


    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
