package com.nathansdev.countify.solution;

import com.nathansdev.countify.game.GameData;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private static final Operation[] OPERATIONS = {new Add(),
            new Subtract(),
            new Multiply(),
            new Divide()};

    private ArrayList<String> solution = new ArrayList<>();
    private GameCallbacks callbacks;

    public Game(GameCallbacks gameCallbacks) {
        this.callbacks = gameCallbacks;
    }

    private boolean findSolution(int[] t, int nb, int total) {
        for (int i = 0; i < nb; i++) {
            if (t[i] == total) {
                return true;
            }

            for (int j = i + 1; j < nb; j++) {
                for (Operation OPERATION : OPERATIONS) {
                    int res = OPERATION.solve(t[i], t[j]);

                    if (res != 0) {
                        int savei = t[i], savej = t[j];
                        t[i] = res;
                        t[j] = t[nb - 1];

                        if (findSolution(t, nb - 1, total)) {
                            solution.add(Math.max(savei, savej) + " " +
                                    OPERATION.symbol() + " " +
                                    Math.min(savei, savej) + " = " + res);
                            return true;
                        }

                        t[i] = savei;
                        t[j] = savej;
                    }
                }
            }
        }
        return false;
    }

    public void getResult(GameData data) {
        if (findSolution(convertIntegers(data.randomNumbers()), data.randomNumbers().size(), data.target())) {
            StringBuilder builder = new StringBuilder();
            for (int i = solution.size() - 1; i >= 0; i--) {
                builder.append(solution.get(i)).append("\n");
            }
            if (!builder.toString().trim().isEmpty()) {
                callbacks.onFoundSolution(builder.toString());
            }
        }
    }

    private static int[] convertIntegers(List<Integer> integers) {
        int[] ret = new int[integers.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = integers.get(i);
        }
        return ret;
    }
}
