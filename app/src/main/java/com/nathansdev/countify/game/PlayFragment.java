package com.nathansdev.countify.game;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import timber.log.Timber;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Screen Where solving the target number happens.
 */
public class PlayFragment extends BaseFragment {

    @Inject
    public PlayFragment() {

    }

    @Inject
    RxEventBus eventBus;
    @BindView(R.id.text_target_number)
    TextView textTarget;
    @BindView(R.id.button_start)
    Button buttonStart;
    @BindView(R.id.text_timer)
    TextView textTimer;

    private List<Integer> randomNumberList = new ArrayList<>();
    private int target;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_play, container, false);
        setViewUnbinder(ButterKnife.bind(this, rootView));
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void setUpView(View view) {
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimerTask();
            }
        });
    }

    public void handleNumbersSelected(List<Integer> list) {
        this.randomNumberList = list;
        int max = 999;
        int min = 1;
        this.target = new Random().nextInt((max - min) + 1) + min;
        textTarget.setText(String.valueOf(target));
    }

    private void startTimerTask() {
        CountDownTimer countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Timber.d("seconds remaining %s", millisUntilFinished / 1000);
                textTimer.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                textTimer.setText("00:00");
                onTimerFinished();
            }
        };
        countDownTimer.start();
    }

    private void onTimerFinished() {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.alert)
                .setMessage(R.string.time_up)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        GameData data = GameData.builder().randomNumbers(randomNumberList).target(target).build();
                        eventBus.send(new Pair<>(AppEvents.GAME_ENDED, data));
                    }
                }).show();
    }
}
