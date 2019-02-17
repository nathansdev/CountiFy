package com.nathansdev.countify;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import com.nathansdev.countify.di.DaggerAppComponent;
import com.nathansdev.countify.log.TimberThreadDebugTree;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;

import javax.inject.Inject;

/**
 * CountiFy application class.
 */
public class CountiFyApp extends Application implements HasActivityInjector {
    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this);
        Timber.plant(new TimberThreadDebugTree());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }
}
