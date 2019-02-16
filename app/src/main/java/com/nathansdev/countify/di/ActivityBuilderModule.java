package com.nathansdev.countify.di;

import com.nathansdev.countify.game.GameActivity;
import com.nathansdev.countify.splash.SplashActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Activity builder class that maps all activities in graph using dagger.
 */
@Module
public abstract class ActivityBuilderModule {

    @PerActivity
    @ContributesAndroidInjector()
    abstract SplashActivity bindSplashActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = {GameModule.class})
    abstract GameActivity bindMainActivity();
}
