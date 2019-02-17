package com.nathansdev.countify.game;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nathansdev.countify.R;
import com.nathansdev.countify.base.BaseFragment;
import com.nathansdev.countify.rxevent.AppEvents;
import com.nathansdev.countify.rxevent.RxEventBus;

import javax.inject.Inject;

public class IntroFragment extends BaseFragment {
    @Inject
    public IntroFragment() {

    }

    @Inject
    RxEventBus eventBus;

    //bind views
    @BindView(R.id.button_play)
    Button buttonPlay;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_intro, container, false);
        setViewUnbinder(ButterKnife.bind(this, rootView));
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void setUpView(View view) {
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventBus.send(new Pair<>(AppEvents.PLAY_GAME_CLICKED, null));
                return;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
