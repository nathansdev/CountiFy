package com.nathansdev.countify.game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.nathansdev.countify.R;

public class GameActivity extends AppCompatActivity {

    private static final String FRAG_TAG_INTRO = "introView";
    private static final String FRAG_TAG_CHOOSE_NUMBERS = "chooseNumbersView";
    private static final String FRAG_TAG_PLAY = "playGameView";
    private static final String FRAG_TAG_RESULT = "resultView";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
