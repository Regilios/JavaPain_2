package org.example;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.example.DivisionStep.printValue;

public class DivisionResult {
    public static List<DivisionStep> step = new LinkedList<>();
    private static int dividend;
    private static int divisor;
    private static int countStep = 0;
    private static int accuracy;

    public DivisionResult(int dividend, int divisor, int afterDecimal) {
        this.dividend = dividend;
        this.divisor = divisor;
        this.accuracy = afterDecimal;
        addFirstValue();
    }
    public void addFirstValue() {
        DivisionStep devisionStep = new DivisionStep(dividend,divisor,accuracy);
        step.add(devisionStep);
    }
    public void addStepValue(int currentDividend, int numerator, int quotient) {
        DivisionStep devisionStep = new DivisionStep(currentDividend,numerator,quotient,countStep);
        step.add(devisionStep);
        countStep++;
    }
    public static void printResult() {
          printValue(step);
    }
}
