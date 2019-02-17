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
import com.nathansdev.countify.solution.*;
import timber.log.Timber;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Fragment Screen Where solving the target number happens.
 */
public class PlayFragment extends BaseFragment implements View.OnClickListener {

    private static final String EQUAL = " = ";
    private static final String SPACE = " ";
    private static final String NEXT = "\n";

    @Inject
    public PlayFragment() {

    }

    @Inject
    RxEventBus eventBus;
    @BindView(R.id.text_target_number)
    TextView textTarget;
    @BindView(R.id.text_timer)
    TextView textTimer;
    @BindView(R.id.button_start)
    Button buttonStart;
    @BindView(R.id.button_reset)
    Button buttonReset;
    @BindView(R.id.button_operator_add)
    Button buttonAdd;
    @BindView(R.id.button_operator_multiply)
    Button buttonMultiply;
    @BindView(R.id.button_operator_divide)
    Button buttonDivide;
    @BindView(R.id.button_operator_subtract)
    Button buttonSubtract;
    @BindView(R.id.button_operand_one)
    Button buttonOperandOne;
    @BindView(R.id.button_operand_two)
    Button buttonOperandTwo;
    @BindView(R.id.button_operand_three)
    Button buttonOperandThree;
    @BindView(R.id.button_operand_four)
    Button buttonOperandFour;
    @BindView(R.id.button_operand_five)
    Button buttonOperandFive;
    @BindView(R.id.button_operand_six)
    Button buttonOperandSix;
    @BindView(R.id.text_result)
    TextView textResult;
    @BindView(R.id.text_title_result)
    TextView textTitleResult;

    private CountDownTimer countDownTimer;
    private List<Integer> randomNumberList = new ArrayList<>();
    private List<Button> operatorButtonList = new ArrayList<>();
    private List<Button> operandButtonList = new ArrayList<>();
    private int target;
    private boolean isTimerStarted = false;
    private int operandX = 0, operandY = 0;
    private Operation currentOperation = null;
    private Button currentOperand = null;
    private StringBuilder builder = new StringBuilder();

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
        buttonStart.setOnClickListener(this);
        buttonReset.setOnClickListener(this);
        buttonOperandOne.setOnClickListener(this);
        buttonOperandTwo.setOnClickListener(this);
        buttonOperandThree.setOnClickListener(this);
        buttonOperandFour.setOnClickListener(this);
        buttonOperandFive.setOnClickListener(this);
        buttonOperandSix.setOnClickListener(this);
        buttonAdd.setOnClickListener(this);
        buttonMultiply.setOnClickListener(this);
        buttonDivide.setOnClickListener(this);
        buttonSubtract.setOnClickListener(this);
        operatorButtonList.add(buttonAdd);
        operatorButtonList.add(buttonDivide);
        operatorButtonList.add(buttonMultiply);
        operatorButtonList.add(buttonSubtract);
        operandButtonList.add(buttonOperandOne);
        operandButtonList.add(buttonOperandTwo);
        operandButtonList.add(buttonOperandThree);
        operandButtonList.add(buttonOperandFour);
        operandButtonList.add(buttonOperandFive);
        operandButtonList.add(buttonOperandSix);
    }

    public void handleNumbersSelected(List<Integer> list) {
        this.randomNumberList = list;
        int max = 999;
        int min = 1;
        this.target = new Random().nextInt((max - min) + 1) + min;
        textTarget.setText(String.valueOf(target));
        setNumbers();
        enableAllOperators();
    }

    private void setNumbers() {
        buttonOperandOne.setText(String.valueOf(randomNumberList.get(0)));
        buttonOperandTwo.setText(String.valueOf(randomNumberList.get(1)));
        buttonOperandThree.setText(String.valueOf(randomNumberList.get(2)));
        buttonOperandFour.setText(String.valueOf(randomNumberList.get(3)));
        buttonOperandFive.setText(String.valueOf(randomNumberList.get(4)));
        buttonOperandSix.setText(String.valueOf(randomNumberList.get(5)));
    }

    private void startTimerTask() {
        isTimerStarted = true;
        buttonStart.setEnabled(false);
        buttonReset.setEnabled(true);
        countDownTimer = new CountDownTimer(30000, 1000) {
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
        isTimerStarted = false;
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

    @Override
    public void onDestroyView() {
        handleDestroy();
        super.onDestroyView();
    }

    private void handleDestroy() {
        buttonStart.setOnClickListener(null);
        buttonReset.setOnClickListener(null);
        buttonOperandOne.setOnClickListener(null);
        buttonOperandTwo.setOnClickListener(null);
        buttonOperandThree.setOnClickListener(null);
        buttonOperandFour.setOnClickListener(null);
        buttonOperandFive.setOnClickListener(null);
        buttonOperandSix.setOnClickListener(null);
        buttonAdd.setOnClickListener(null);
        buttonMultiply.setOnClickListener(null);
        buttonDivide.setOnClickListener(null);
        buttonSubtract.setOnClickListener(null);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.button_start:
                startTimerTask();
                break;
            case R.id.button_reset:
                resetGame();
                break;
            case R.id.button_operand_one:
                handleOnSelectOperand(buttonOperandOne);
                break;
            case R.id.button_operand_two:
                handleOnSelectOperand(buttonOperandTwo);
                break;
            case R.id.button_operand_three:
                handleOnSelectOperand(buttonOperandThree);
                break;
            case R.id.button_operand_four:
                handleOnSelectOperand(buttonOperandFour);
                break;
            case R.id.button_operand_five:
                handleOnSelectOperand(buttonOperandFive);
                break;
            case R.id.button_operand_six:
                handleOnSelectOperand(buttonOperandSix);
                break;
            case R.id.button_operator_add:
                handleOnSelectOperator(buttonAdd, new Add());
                break;
            case R.id.button_operator_subtract:
                handleOnSelectOperator(buttonSubtract, new Subtract());
                break;
            case R.id.button_operator_divide:
                handleOnSelectOperator(buttonDivide, new Divide());
                break;
            case R.id.button_operator_multiply:
                handleOnSelectOperator(buttonMultiply, new Multiply());
                break;
            default:
                break;
        }
    }

    private void handleOnSelectOperand(Button operand) {
        if (isTimerRunning()) {
            return;
        }
        this.currentOperand = operand;
        if (operandX == 0) {
            this.operandX = Integer.parseInt(operand.getText().toString());
        } else if (operandY == 0) {
            this.operandX = Integer.parseInt(operand.getText().toString());
        }
        if (currentOperation == null) {

        }
        doOperation();
    }

    private void handleOnSelectOperator(Button buttonOperator, Operation operation) {
        if (isTimerRunning()) {
            return;
        }
        this.currentOperation = operation;
        disableCurrentOperator(buttonOperator);
    }

    private void resetGame() {
        countDownTimer.cancel();
        isTimerStarted = false;
        buttonStart.setEnabled(true);
        disableAllOperators();
        handleNumbersSelected(randomNumberList);
        operandX = 0;
        operandY = 0;
        currentOperation = null;
        currentOperand = null;
    }

    private void doOperation() {
        if (operandX != 0 && operandY != 0 && currentOperation != null) {
            int result = currentOperation.solve(operandX, operandY);
            builder.append(operandX).append(SPACE).append(currentOperation.symbol())
                    .append(SPACE).append(operandY).append(EQUAL).append(result).append(NEXT);
            Timber.d("current value %s", result);
            Timber.d("builder text %s", builder);
            currentOperand.setText(String.valueOf(result));
            textResult.setText(builder);
        }
    }

    private void disableCurrentOperator(Button buttonOperator) {
        for (Button button : operatorButtonList) {
            if (button.getId() == buttonOperator.getId()) {
                button.setEnabled(false);
            } else {
                button.setEnabled(true);
            }
        }
    }

    private void disableCurrentOperand(Button buttonOperator) {
        for (Button button : operandButtonList) {
            if (button.getId() == buttonOperator.getId()) {
                button.setEnabled(false);
            } else {
                button.setEnabled(true);
            }
        }
    }

    private void enableAllOperators() {
        for (Button button : operatorButtonList) {
            button.setEnabled(true);
        }
    }

    private void disableAllOperators() {
        for (Button button : operatorButtonList) {
            button.setEnabled(false);
        }
    }

    private void enableAllOperands() {
        for (Button button : operandButtonList) {
            button.setEnabled(true);
        }
    }

    private void disableAllOperands() {
        for (Button button : operandButtonList) {
            button.setEnabled(false);
        }
    }

    private boolean isTimerRunning() {
        return !isTimerStarted;
    }
}
