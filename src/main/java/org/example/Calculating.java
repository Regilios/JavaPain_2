package org.example;

import java.util.ArrayList;
import java.util.List;

public class Calculating {
    private final Integer dividend;
    private final Integer divisor;
    private static int quotient = 0; // остаток от деления
    public static final int AFTERDECIMAL = 4; // кол-во знаков после запятой
    private static final ArrayList<String> listIncreaseNumber = new ArrayList<>(); // хранение числа которое больше делителя
    private static boolean overflowFlag = false; // переполнение делимого
    public static StringBuilder stringResult = new StringBuilder(); // конечный результат деления столбиком
    public static DivisionResult initResult;

    public Calculating(List<Integer> arrSourceValue) {
        this.dividend = arrSourceValue.get(0);
        this.divisor = arrSourceValue.get(1);
    }

    public void start() {
        initResult();
        computation(getChars(dividend), divisor);
    }

    private void initResult() {
        initResult = new DivisionResult(this.dividend, this.divisor, AFTERDECIMAL);
    }

    private static char[] getChars(int dividend) { // делимое посимвольно
        return String.valueOf(dividend).toCharArray();
    }

    private static String getNumberTemp() {
        return listIncreaseNumber.get(0);
    }

    private static void setNumberTemp(int currentTemp) {
        listIncreaseNumber.set(0, String.valueOf(currentTemp));
    }

    private static void removeTemp() {
        listIncreaseNumber.remove(0);
    }

    private static void appendResult(String str) {
        stringResult.append(str);
    }

    private static boolean shares(Integer dividendInt, Integer divisor) { // можно ли поделить число не увеличивая
        if (dividendInt < divisor) {
            listIncreaseNumber.add(String.valueOf(dividendInt));
            return false;
        }

        return true;
    }

    private void appStepResult(Integer dividend, Integer divisor) { // записывем рузельтат текущего деления
        quotient = dividend % divisor;
        appendResult(String.valueOf((dividend / divisor)));

        int numerator = (dividend / divisor) * divisor;
        initResult.addStepValue(dividend, numerator, quotient);
    }

    private static int biasDividendChar(char[] dividendChar, int i) {
        int currentTemp;

        if (i >= dividendChar.length) {
            currentTemp = Integer.parseInt(getNumberTemp() + "0");
            appendResult("0,");
            overflowFlag = true;
        } else {
            currentTemp = Integer.parseInt(getNumberTemp() + dividendChar[i]);
        }

        return currentTemp;
    }

    private static int increaseCurrentTemp(int divisor, int currentTemp, boolean j) {
        while (currentTemp <= divisor) {
            currentTemp = Integer.parseInt(getNumberTemp() + "0");
            if (j) {
                j = false;
            } else {
                appendResult("0");
            }
            setNumberTemp(currentTemp);
        }

        return currentTemp;
    }

    private static void checkComma(boolean overflowFlag, int quotient) {
        if (quotient != 0 && !overflowFlag) appendResult(",");
    }

    private void decimalPlaces(int quotient, int divisor) {
        for (int i = 0; i < Calculating.AFTERDECIMAL; i++) {
            if (shares(quotient, divisor)) {
                appStepResult(quotient, divisor);
            } else {
                int currentTemp = (i == 0) ? Integer.parseInt(getNumberTemp()) : quotient;
                boolean j = true;

                currentTemp = increaseCurrentTemp(divisor, currentTemp, j);
                appStepResult(currentTemp, divisor);
                quotient = currentTemp % divisor;
                removeTemp();

                if (quotient == 0)  break;
            }
        }
    }

    public void computation(char[] dividendChar, int divisor) {
        for (int i = 0; i < dividendChar.length; i++) {
            int dividendInt = (quotient == 0) ? Integer.parseInt(String.valueOf(dividendChar[i])) : Integer.parseInt(quotient + String.valueOf(dividendChar[i]));

            if (shares(dividendInt, divisor)) {
                appStepResult(dividendInt, divisor);
            } else {
                int currentTemp = Integer.parseInt(getNumberTemp());
                while (currentTemp <= divisor && !overflowFlag) {
                    i++;
                    currentTemp = biasDividendChar(dividendChar, i);
                    setNumberTemp(currentTemp);
                }

                appStepResult(currentTemp, divisor);
                removeTemp();
            }
        }

        if (overflowFlag || (quotient != 0)) {
            checkComma(overflowFlag, quotient);
            decimalPlaces(quotient, divisor);
        }

       initResult.printResult();
    }


}
