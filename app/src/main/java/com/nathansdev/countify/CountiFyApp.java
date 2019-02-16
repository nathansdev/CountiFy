package com.nathansdev.countify;

import android.app.Application;
import android.content.Context;
import com.nathansdev.countify.di.DaggerAppComponent;

/**
 * CountiFy application class.
 */
public class CountiFyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
