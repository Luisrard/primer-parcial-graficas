package com.luisrard.primer.parcial.graficas;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class Parcial1Practica4OrdenaLista {
    public static void main(String[] args) {
        TreeSet<Integer> numbers = convertStringArgsToNumbers(args);
        System.out.println(numbers);
    }

    private static TreeSet<Integer> convertStringArgsToNumbers(String[] args) {
        TreeSet<Integer> numbers = new TreeSet<>();
        for (String stringNumber : args){
            numbers.add(Integer.valueOf(stringNumber));
        }
        return numbers;
    }
}
