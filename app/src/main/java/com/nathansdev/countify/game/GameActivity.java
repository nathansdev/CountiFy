package com.nathansdev.countify.game;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.auto.value.AutoValue;
import com.nathansdev.countify.R;
import com.nathansdev.countify.base.BaseActivity;
import com.nathansdev.countify.rxevent.AppEvents;
import com.nathansdev.countify.rxevent.RxEventBus;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import javax.inject.Inject;
import java.util.List;

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
    ViewGroup rootView;
    @BindView(R.id.intro_container)
    View introContainer;
    @BindView(R.id.choose_number_container)
    View chooseNumberContainer;
    @BindView(R.id.play_container)
    View playGameContainer;
    @BindView(R.id.result_container)
    View resultContainer;

    private final CompositeDisposable disposables = new CompositeDisposable();
    private UIState uiState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);
        initState();
        setUpSubscription();
        addFragmentsToContainer();
        showUI();
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
        handleBackPressed();
    }

    /**
     * initializing the UI with state intro.
     */
    private void initState() {
        if (uiState == null) {
            // default uiState if no restored uiState found.
            uiState = UIState.builder().mode(UIState.MODE_INTRO).build();
        }
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
    private void showUI() {
        if (uiState.isIntro()) {
            introContainer.setVisibility(View.VISIBLE);
            chooseNumberContainer.setVisibility(View.INVISIBLE);
            playGameContainer.setVisibility(View.INVISIBLE);
            resultContainer.setVisibility(View.INVISIBLE);
        } else if (uiState.isChoosingNumbers()) {
            introContainer.setVisibility(View.INVISIBLE);
            chooseNumberContainer.setVisibility(View.VISIBLE);
            playGameContainer.setVisibility(View.INVISIBLE);
            resultContainer.setVisibility(View.INVISIBLE);
        } else if (uiState.isPlayingGame()) {
            introContainer.setVisibility(View.INVISIBLE);
            chooseNumberContainer.setVisibility(View.INVISIBLE);
            playGameContainer.setVisibility(View.VISIBLE);
            resultContainer.setVisibility(View.INVISIBLE);
        } else if (uiState.isResult()) {
            introContainer.setVisibility(View.INVISIBLE);
            chooseNumberContainer.setVisibility(View.INVISIBLE);
            playGameContainer.setVisibility(View.INVISIBLE);
            resultContainer.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Handle all events received from event bus.
     */
    private void handleEventData(Pair<String, Object> event) {
        if (event.first.equalsIgnoreCase(AppEvents.PLAY_GAME_CLICKED)) {
            handlePlayGameClicked();
        } else if (event.first.equalsIgnoreCase(AppEvents.SELECT_LARGE_NUMBER_CLICKED)) {
            handleLargeNumberSelection();
        } else if (event.first.equalsIgnoreCase(AppEvents.SELECT_SMALL_NUMBER_CLICKED)) {
            handleSmallNumberSelection();
        } else if (event.first.equalsIgnoreCase(AppEvents.RANDOM_NUMBERS_CHOSEN)) {
            if (event.second instanceof List) {
                handleNumbersChosen((List<Integer>) event.second);
            }
        } else if (event.first.equalsIgnoreCase(AppEvents.PLAY_GAME_AGAIN_CLICKED)) {
            handlePlayGameClicked();
        } else if (event.first.equalsIgnoreCase(AppEvents.GAME_ENDED)) {
            handleGameEnded((GameData) event.second);
        }
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

    private void handleGameEnded(GameData data) {
        uiState = uiState.withBuild().mode(UIState.MODE_RESULT).build();
        showUI();
        resultFragment.handleResultClicked(data);
    }

    private void handlePlayGameClicked() {
        uiState = uiState.withBuild().mode(UIState.MODE_CHOOSE_NUMBERS).build();
        showUI();
    }

    private void handleSmallNumberSelection() {
        chooseNumberFragment.selectSmallNumbers();
    }

    private void handleLargeNumberSelection() {
        chooseNumberFragment.selectLargeNumbers();
    }

    private void handleNumbersChosen(List<Integer> list) {
        uiState = uiState.withBuild().mode(UIState.MODE_PLAY_THE_GAME).build();
        showUI();
        playFragment.handleNumbersSelected(list);
    }

    private void handleBackPressed() {
        if (uiState.isResult()) {
            uiState = uiState.withBuild().mode(UIState.MODE_PLAY_THE_GAME).build();
            showUI();
        } else if (uiState.isPlayingGame()) {
            uiState = uiState.withBuild().mode(UIState.MODE_CHOOSE_NUMBERS).build();
            showUI();
        } else if (uiState.isChoosingNumbers()) {
            uiState = uiState.withBuild().mode(UIState.MODE_INTRO).build();
            showUI();
        } else if (uiState.isIntro()) {
            super.onBackPressed();
        }
    }

    /**
     * Class for maintaining Game UI state.
     */
    @AutoValue
    public abstract static class UIState implements Parcelable {

        static final int MODE_INTRO = 0x01;
        static final int MODE_CHOOSE_NUMBERS = 0x02;
        static final int MODE_PLAY_THE_GAME = 0x03;
        static final int MODE_RESULT = 0x04;

        static Builder builder() {
            return new AutoValue_GameActivity_UIState.Builder();
        }

        abstract int mode();

        boolean isIntro() {
            return mode() == MODE_INTRO;
        }

        boolean isChoosingNumbers() {
            return mode() == MODE_CHOOSE_NUMBERS;
        }

        boolean isPlayingGame() {
            return mode() == MODE_PLAY_THE_GAME;
        }

        boolean isResult() {
            return mode() == MODE_RESULT;
        }

        public abstract Builder toBuilder();

        private Builder withBuild() {
            return toBuilder();
        }

        @AutoValue.Builder
        public abstract static class Builder {
            abstract UIState build();

            abstract Builder mode(int m);
        }
    }
}
