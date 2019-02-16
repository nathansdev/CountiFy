package com.nathansdev.countify.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import butterknife.ButterKnife;
import com.nathansdev.countify.R;
import com.nathansdev.countify.base.BaseActivity;
import com.nathansdev.countify.game.GameActivity;

public class SplashActivity extends BaseActivity {

    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        routeToMain();
    }

    // route to Game Activity after some delay
    private void routeToMain() {
        handler.postDelayed(() -> {
            Intent mainIntent = new Intent(SplashActivity.this, GameActivity.class);
            startActivity(mainIntent);
            finish();
        }, 1000);
    }

    @Override
    protected void onDestroy() {
        if (handler != null) {
            handler.removeCallbacks(null);
        }
        super.onDestroy();
    }
}
