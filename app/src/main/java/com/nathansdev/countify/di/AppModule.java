package com.nathansdev.countify.di;

import com.nathansdev.countify.rxevent.RxEventBus;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * Contains all singleton and provides methods needed for app.
 */
@Module
public abstract class AppModule {
    @Provides
    @Singleton
    static RxEventBus provideRxEventBus() {
        return new RxEventBus();
    }
}
