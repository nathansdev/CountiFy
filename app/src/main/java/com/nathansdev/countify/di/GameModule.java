package com.nathansdev.countify.di;

import com.nathansdev.countify.game.ChooseNumberFragment;
import com.nathansdev.countify.game.IntroFragment;
import com.nathansdev.countify.game.PlayFragment;
import com.nathansdev.countify.game.ResultFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Provider that injects necessary fragment of main module.
 */

@Module
public abstract class GameModule {

    @PerFragment
    @ContributesAndroidInjector
    abstract IntroFragment provideIntroFragmentFactory();

    @PerFragment
    @ContributesAndroidInjector
    abstract ChooseNumberFragment provideChooseNumberFragmentFactory();

    @PerFragment
    @ContributesAndroidInjector
    abstract PlayFragment providePlayFragmentFactory();

    @PerFragment
    @ContributesAndroidInjector
    abstract ResultFragment provideResultFragmentFactory();
}
