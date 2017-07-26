package com.ryanmord.livefrontproj.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.test.espresso.IdlingResource;
import android.util.Log;

/**
 * Created by ryanmord on 7/25/17.
 */

public class NetworkChange implements IdlingResource {

    boolean lookingForStatus;
    private Context mContext;

    public NetworkChange(Context context, boolean desiredEnabledStatus) {
        mContext = context;
        lookingForStatus = desiredEnabledStatus;
    }

    @Override
    public String getName() {
        return NetworkChange.class.getSimpleName();
    }

    @Override
    public boolean isIdleNow() {
        return isNetworkAvailable(mContext) == lookingForStatus;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {

    }


    private boolean isNetworkAvailable(Context context) {
        boolean isMobile = false, isWifi = false;

        NetworkInfo[] infoAvailableNetworks = ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE)).getAllNetworkInfo();

        if (infoAvailableNetworks != null) {
            for (NetworkInfo network : infoAvailableNetworks) {

                if (network.getType() == ConnectivityManager.TYPE_WIFI) {
                    if (network.isConnected() && network.isAvailable())
                        isWifi = true;
                }
                if (network.getType() == ConnectivityManager.TYPE_MOBILE) {
                    if (network.isConnected() && network.isAvailable())
                        isMobile = true;
                }
            }
        }

        return isMobile || isWifi;
    }
}
