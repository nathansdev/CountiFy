package com.nathansdev.countify.game;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.nathansdev.countify.R;
import com.nathansdev.countify.base.BaseFragment;
import com.nathansdev.countify.rxevent.RxEventBus;

import javax.inject.Inject;

public class IntroFragment extends BaseFragment {
    @Inject
    public IntroFragment() {

    }

    @Inject
    RxEventBus eventBus;

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

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
