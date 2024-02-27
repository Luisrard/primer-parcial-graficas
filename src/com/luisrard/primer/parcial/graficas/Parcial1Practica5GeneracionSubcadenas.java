package com.luisrard.primer.parcial.graficas;

public class Parcial1Practica5GeneracionSubcadenas {
    public static void main(String[] args) {
        String text = args[0];
        printSubstrings(text);
    }

    private static void printSubstrings(String text) {
        int length = text.length();
        for (int i = length; i > 0; i--){
            System.out.println(text.substring(0, i));
        }
        for (int i = length - 1; i >= 0; i--){
            System.out.println(text.substring(i));
        }
    }
}
