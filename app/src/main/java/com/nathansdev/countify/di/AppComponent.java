package com.nathansdev.countify.di;

import android.app.Application;
import com.nathansdev.countify.CountiFyApp;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

import javax.inject.Singleton;

/**
 * App component interface.
 */

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, AppModule.class, ActivityBuilderModule.class})
public interface AppComponent {

    /**
     * Binding application class instance to app component.
     */
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(CountiFyApp app);
}
