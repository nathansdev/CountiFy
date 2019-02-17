package com.nathansdev.countify.game;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nathansdev.countify.R;
import com.nathansdev.countify.base.BaseFragment;
import com.nathansdev.countify.rxevent.AppEvents;
import com.nathansdev.countify.rxevent.RxEventBus;
import com.nathansdev.countify.solution.Game;
import com.nathansdev.countify.solution.GameCallbacks;
import timber.log.Timber;

import javax.inject.Inject;

public class ResultFragment extends BaseFragment implements GameCallbacks {
    @Inject
    public ResultFragment() {

    }

    @Inject
    RxEventBus eventBus;

    //bind views
    @BindView(R.id.text_result)
    TextView textResult;
    @BindView(R.id.text_title_result)
    TextView textTitleResult;
    @BindView(R.id.button_play_again)
    Button buttonPlayAgain;
    private Game game;
    private GameData gameData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_result, container, false);
        setViewUnbinder(ButterKnife.bind(this, rootView));
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void setUpView(View view) {
        buttonPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventBus.send(new Pair<>(AppEvents.PLAY_GAME_AGAIN_CLICKED, null));
            }
        });
        game = new Game(this);
    }

    public void handleResultClicked(GameData data) {
        this.gameData = data;
        game.getResult(gameData);
    }

    @Override
    public void onFoundSolution(String result) {
        Timber.d("onFoundSolution %s", result);
        textResult.setText(result);
    }
}
