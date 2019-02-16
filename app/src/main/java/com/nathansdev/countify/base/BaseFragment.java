package com.nathansdev.countify.base;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;

/**
 * Base fragment class that any fragment can extends.
 */

public abstract class BaseFragment extends Fragment {

    @Nullable
    private Unbinder viewUnbinder;

    private BaseActivity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            AndroidSupportInjection.inject(this);
        }
        if (activity instanceof BaseActivity) {
            this.mActivity = (BaseActivity) activity;
        }
        super.onAttach(activity);
    }

    @Override
    public void onAttach(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            AndroidSupportInjection.inject(this);
        }
        if (context instanceof BaseActivity) {
            this.mActivity = (BaseActivity) context;
        }
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpView(view);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        if (viewUnbinder != null) {
            viewUnbinder.unbind();
        }
        mActivity = null;
        super.onDestroyView();
    }

    public void setViewUnbinder(Unbinder binder) {
        viewUnbinder = binder;
    }

    protected abstract void setUpView(View view);

    public BaseActivity getBaseActivity() {
        return mActivity;
    }
}
