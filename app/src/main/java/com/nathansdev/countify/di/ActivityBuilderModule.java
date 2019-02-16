package com.nathansdev.countify.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Activity builder class that maps all activities in graph using dagger.
 */
@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = {GameModule.class})
    abstract MainActivity bindMainActivity();
}
