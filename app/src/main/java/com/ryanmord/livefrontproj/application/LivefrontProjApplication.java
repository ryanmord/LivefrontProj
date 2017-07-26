package com.ryanmord.livefrontproj.application;

import android.app.Application;

import timber.log.Timber;


/**
 * Project Application class for handling Application level logic.
 */
public class LivefrontProjApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Plant logging tree for application use
        Timber.plant(new Timber.DebugTree());
    }

}

