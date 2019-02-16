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

    @ContributesAndroidInjector
    abstract IntroFragment provideIntroFragmentFactory();

    @ContributesAndroidInjector
    abstract ChooseNumberFragment provideChooseNumberFragmentFactory();

    @ContributesAndroidInjector
    abstract PlayFragment providePlayFragmentFactory();

    @ContributesAndroidInjector
    abstract ResultFragment provideResultFragmentFactory();
}
