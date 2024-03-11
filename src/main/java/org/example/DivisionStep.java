package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class DivisionStep {
    public int currentDividend;
    private int numerator;
    public static int dividend;
    private static int divisor;
    private int countStep;
    private int quotient;
    private static int accuracy;
    private static StringBuilder spaceTemp = new StringBuilder();
    public static StringBuilder div = new StringBuilder();
    public static String newLine = System.lineSeparator();

    public DivisionStep (int dividend, int divisor, int accuracy)  {
        this.dividend = dividend;
        this.divisor = divisor;
        this.accuracy = accuracy;
    }

    public DivisionStep (int currentDividend, int numerator, int quotient, int countStep)  {
        this.currentDividend = currentDividend;
        this.numerator = numerator;
        this.quotient = quotient;
        this.countStep = countStep;
    }

    private static int getLenDiv(int d) {
        char[] len = String.valueOf(d).toCharArray();
        return len.length;
    }

    private static int getLengResult() {
        String tempDivisionResult = String.valueOf(getBigDecimal());
        return tempDivisionResult.length();
    }

    private static BigDecimal getBigDecimal() {
        BigDecimal divisionResult = BigDecimal.valueOf((double) dividend / divisor);
        return divisionResult.setScale(accuracy, RoundingMode.DOWN);
    }

    private static void addSpaceStep(int numeratorLen, int currentQuotient) {
        if (currentQuotient == 0) {
            spaceTemp.append(" ".repeat(Math.max(0, numeratorLen)));
        } else {
            spaceTemp.append(" ".repeat(Math.max(0, numeratorLen - getLenDiv(currentQuotient))));
        }
    }

    public static void drawStartBlock(DivisionStep step) {
        int spaceDividend = getLenDiv(dividend) - getLenDiv(step.currentDividend); // пробелы после числителя
        int numeratorLen = getLenDiv(step.numerator); // длинна числителя
        int numeratorOffset = getLenDiv(step.currentDividend) - numeratorLen; //пробелы перед числителем
        int currentQuotient = step.quotient; // остаток деления
        int lengthResult = getLengResult(); // длинна результата деления

        addSpaceStep(numeratorLen, currentQuotient);
        div.append("_").append(dividend).append('|').append(divisor).append(newLine);
        div.append(" ").append(" ".repeat(Math.max(0, numeratorOffset))).append(step.numerator).append(" ".repeat(Math.max(0, spaceDividend))).append("|").append("-".repeat(Math.max(0, lengthResult))).append(newLine);

        // проверка исключений когда числитель меньше знаменателя на порядок. Прим: 12587946 / 698
        if (numeratorOffset > 0) {
             spaceTemp.append(" ".repeat(Math.max(0, numeratorOffset)));
             numeratorOffset = 0;
        }
        div.append(" ").append(" ".repeat(Math.max(0, numeratorOffset))).append("-".repeat(Math.max(0, getLenDiv(step.currentDividend)))).append(" ".repeat(Math.max(0, spaceDividend))).append("|").append(getBigDecimal()).append(newLine);
    }

    public static void drawNextStep(DivisionStep step, boolean end) {
        int currentDividendLen = getLenDiv(step.currentDividend);
        int numeratorLen = getLenDiv(step.numerator);
        int currentQuotient = step.quotient;
        String spaceNumerator = "";

        // проверка исключений когда числитель меньше знаменателя на порядок после запятой. Прим: 79845 / 4
        if (numeratorLen < currentDividendLen) {
            spaceNumerator = " ".repeat(Math.max(0, currentDividendLen-numeratorLen));
        }

        div.append(spaceTemp).append("_").append(step.currentDividend).append(newLine);
        div.append(spaceTemp).append(" ").append(spaceNumerator).append(step.numerator).append(newLine);
        div.append(spaceTemp).append(" ").append("-".repeat(Math.max(0, currentDividendLen))).append(newLine);

        if (end) {
            spaceTemp.append(" ".repeat(Math.max(0, currentDividendLen - getLenDiv(currentQuotient))));
            div.append(spaceTemp).append(" ").append(currentQuotient).append(newLine);
        }

        addSpaceStep(currentDividendLen, currentQuotient);
    }

    public static void printValue(List<DivisionStep> step) {
        drawStartBlock(step.get(1));

        for (int i = 2; i < step.size(); i++) {
            drawNextStep(step.get(i), i == (step.size() - 1));
        }

        System.out.println(div);
    }
}
