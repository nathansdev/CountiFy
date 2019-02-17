package com.nathansdev.countify.solution;

public class Subtract implements Operation {
    @Override
    public int solve(int x, int y) {
        if (x < y) {
            return y - x;
        } else {
            return x - y;
        }
    }

    @Override
    public String symbol() {
        return "-";
    }
}
