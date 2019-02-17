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
import timber.log.Timber;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Random;

/**
 * Number Choosing screen for the Game.
 */
public class ChooseNumberFragment extends BaseFragment {
    private static final String COMMA = ",";
    private static final String SPACE = " ";

    @Inject
    public ChooseNumberFragment() {

    }

    @Inject
    RxEventBus eventBus;

    //bind views
    @BindView(R.id.text_choose_number_hint)
    TextView textChooseNumberHint;
    @BindView(R.id.text_chosen_number)
    TextView textChosenNumber;
    @BindView(R.id.button_choose_small_number)
    Button buttonChooseSmallNumber;
    @BindView(R.id.button_choose_large_number)
    Button buttonChooseLargeNumber;

    private ArrayList<Integer> smallNumbersList = new ArrayList<>();
    private ArrayList<Integer> largeNumbersList = new ArrayList<>();
    private ArrayList<Integer> chosenNumbersList = new ArrayList<>();
    private StringBuilder builder = new StringBuilder();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_choose_numbers, container, false);
        setViewUnbinder(ButterKnife.bind(this, rootView));
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void setUpView(View view) {
        buttonChooseLargeNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventBus.send(new Pair<>(AppEvents.SELECT_LARGE_NUMBER_CLICKED, null));
                return;
            }
        });

        buttonChooseSmallNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventBus.send(new Pair<>(AppEvents.SELECT_SMALL_NUMBER_CLICKED, null));
                return;
            }
        });
        initNumbers();
    }

    /**
     * initializing list to choose random numbers.
     */
    private void initNumbers() {
        for (int i = 0; i <= 10; i++) {
            smallNumbersList.add(i);
            smallNumbersList.add(i);
        }

        largeNumbersList.add(25);
        largeNumbersList.add(50);
        largeNumbersList.add(75);
        largeNumbersList.add(100);
    }

    public void selectSmallNumbers() {
        if (chosenNumbersList.size() >= 6) {
            onNumbersRangeReached();
            return;
        }
        Integer selected = smallNumbersList.get(new Random().nextInt(smallNumbersList.size()));
        Timber.d("selected small numbers %s", selected);
        chosenNumbersList.add(selected);
        updateNumbersText(selected);
    }

    public void selectLargeNumbers() {
        if (chosenNumbersList.size() >= 6) {
            return;
        }
        Integer selected = largeNumbersList.get(new Random().nextInt(largeNumbersList.size()));
        Timber.d("selected large numbers %s", selected);
        chosenNumbersList.add(selected);
        updateNumbersText(selected);
    }

    /**
     * raise event when chosen numbers list exceeds 6.
     */
    private void onNumbersRangeReached() {
        eventBus.send(new Pair<>(AppEvents.RANDOM_NUMBERS_CHOSEN, chosenNumbersList));
    }

    private void updateNumbersText(Integer number) {
        if (chosenNumbersList.size() == 1) {
            builder.append(number);
        } else {
            builder.append(COMMA).append(SPACE).append(number);
        }
        textChosenNumber.setText(builder);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
