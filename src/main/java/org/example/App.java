package org.example;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App
{
    public static final List<Integer> arrSourceValue = new ArrayList<>();

    public static void main( String[] args ) throws RuntimeException {
        Scanner console = new Scanner(System.in);

        System.out.println("Делимое");
        String str = console.nextLine();
        arrSourceValue.add(Integer.valueOf(Calc.checkDividendDivisor(str)));

        System.out.println("Делитель");
        String str2 = console.nextLine();
        arrSourceValue.add(Integer.valueOf(Calc.checkDividendDivisor(str2)));

        console.close();

        Calculating exercise = new Calculating(arrSourceValue);
        exercise.start();
    }
}
