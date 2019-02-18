package com.nathansdev.countify.game;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

    /**
     * show alert when the user presses back button
     */
    public void onBackPressed() {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.alert)
                .setMessage(R.string.play_again_alert)
                .setCancelable(true)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                reset();
                dialog.dismiss();
                eventBus.send(new Pair<>(AppEvents.PLAY_GAME_AGAIN_CLICKED, null));
            }
        }).show();
    }

    @Override
    public void onDestroyView() {
        if (game != null) {
            game.hadleDestroy();
        }
        super.onDestroyView();
    }

    private void reset() {
        this.game = null;
        textResult.setText("");
    }
}
