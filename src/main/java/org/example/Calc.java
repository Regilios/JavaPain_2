package org.example;

import org.apache.commons.lang3.StringUtils;

public class Calc  {
    public static String EMPTY_STRING = "Введите значение!";
    public static String WORD_STRING = "Только числа!";
    public static String ZERO_STRING = "Нельзя использоваться ноль у делимого / делителя!";
    public static String INTEGER_STRING = "Только целые числа!";
    public static String checkDividendDivisor(String str) throws RuntimeException {

        String temp = str.trim();
        if (StringUtils.isBlank(temp)) {
            throw new RuntimeException(EMPTY_STRING);
        }

        char[] symbolStr = temp.toCharArray();
        for (var singleChar : symbolStr) {
            if (Character.isAlphabetic(singleChar)) {
                throw new RuntimeException(WORD_STRING);
            }
        }

        if (temp.equals("0")) {
            throw new RuntimeException(ZERO_STRING);
        }

        try {
            Integer.parseInt(temp);
        } catch (NumberFormatException e) {
            throw new RuntimeException(INTEGER_STRING);
        }

        return temp;
    }
}
