package com.nathansdev.countify.solution;

public class Add implements Operation {
    @Override
    public int solve(int x, int y) {
        int r = x + y;

        if (r <= x || r <= y) {
            return 0;
        } else {
            return r;
        }
    }

    @Override
    public int operate(int x, int y) {
        return x + y;
    }

    @Override
    public String symbol() {
        return "+";
    }
}
