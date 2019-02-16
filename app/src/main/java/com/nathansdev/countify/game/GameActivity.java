package com.nathansdev.countify.game;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nathansdev.countify.R;
import com.nathansdev.countify.base.BaseActivity;
import com.nathansdev.countify.rxevent.RxEventBus;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import javax.inject.Inject;

/**
 * App main activity.
 */
public class GameActivity extends BaseActivity {

    private static final String FRAG_TAG_INTRO = "introView";
    private static final String FRAG_TAG_CHOOSE_NUMBERS = "chooseNumbersView";
    private static final String FRAG_TAG_PLAY = "playGameView";
    private static final String FRAG_TAG_RESULT = "resultView";
    private static final String TAG = GameActivity.class.getSimpleName();

    // injection
    @Inject
    RxEventBus eventBus;
    @Inject
    IntroFragment introFragment;
    @Inject
    ChooseNumberFragment chooseNumberFragment;
    @Inject
    PlayFragment playFragment;
    @Inject
    ResultFragment resultFragment;

    // bind views
    @BindView(R.id.root)
    View rootContainer;
    @BindView(R.id.intro_container)
    View introContainer;
    @BindView(R.id.choose_number_container)
    View chooseNumberContainer;
    @BindView(R.id.play_container)
    View playGameContainer;
    @BindView(R.id.result_container)
    View resultContainer;

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);
        setUpSubscription();
        addFragmentsToContainer();
        setUpViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * Subscribe to event bus to receive events.
     */
    private void setUpSubscription() {
        disposables.add(eventBus.toObservables()
                .onErrorReturn(throwable -> {
                    Timber.tag(TAG).e(throwable);
                    return null;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(event -> {
                    Timber.v("Event received %s", event.first);
                    handleEventData(event);
                })
        );
    }

    /**
     * adding fragments to container.
     */
    private void addFragmentsToContainer() {
        IntroFragment introFrag = getIntroFrag();
        if (introFrag == null) {
            introFrag = introFragment;
            getSupportFragmentManager().beginTransaction()
                    .add(introContainer.getId(), introFrag, FRAG_TAG_INTRO)
                    .commit();
        }

        ChooseNumberFragment chooseNumberFrag = getChooseNumberFrag();
        if (chooseNumberFrag == null) {
            chooseNumberFrag = chooseNumberFragment;
            getSupportFragmentManager().beginTransaction()
                    .add(chooseNumberContainer.getId(), chooseNumberFrag, FRAG_TAG_CHOOSE_NUMBERS)
                    .commit();
        }

        PlayFragment playFrag = getPlayFrag();
        if (playFrag == null) {
            playFrag = playFragment;
            getSupportFragmentManager().beginTransaction()
                    .add(playGameContainer.getId(), playFrag, FRAG_TAG_PLAY)
                    .commit();
        }

        ResultFragment resultFrag = getResultFrag();
        if (resultFrag == null) {
            resultFrag = resultFragment;
            getSupportFragmentManager().beginTransaction()
                    .add(resultContainer.getId(), resultFrag, FRAG_TAG_RESULT)
                    .commit();
        }
    }


    /**
     * initialize views based on current mode.
     */
    private void setUpViews() {
        introContainer.setVisibility(View.VISIBLE);
        chooseNumberContainer.setVisibility(View.INVISIBLE);
        playGameContainer.setVisibility(View.INVISIBLE);
        resultContainer.setVisibility(View.INVISIBLE);
    }

    /**
     * Handle all events received from event bus.
     */
    private void handleEventData(Pair<String, Object> event) {

    }

    /**
     * Return Intro fragment by tag.
     */
    private IntroFragment getIntroFrag() {
        return (IntroFragment) getSupportFragmentManager().findFragmentByTag(FRAG_TAG_CHOOSE_NUMBERS);
    }

    /**
     * Return Number Selection fragment by tag.
     */
    private ChooseNumberFragment getChooseNumberFrag() {
        return (ChooseNumberFragment) getSupportFragmentManager().findFragmentByTag(FRAG_TAG_CHOOSE_NUMBERS);
    }

    /**
     * Return Feedback filter fragment by tag.
     */
    private PlayFragment getPlayFrag() {
        return (PlayFragment) getSupportFragmentManager().findFragmentByTag(FRAG_TAG_PLAY);
    }

    /**
     * Return Search filter fragment by tag.
     *
     * @return search filter fragment.
     */
    private ResultFragment getResultFrag() {
        return (ResultFragment) getSupportFragmentManager().findFragmentByTag(FRAG_TAG_RESULT);
    }
}
