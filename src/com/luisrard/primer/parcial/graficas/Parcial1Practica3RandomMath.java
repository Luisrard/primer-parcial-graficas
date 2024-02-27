package com.luisrard.primer.parcial.graficas;

public class Parcial1Practica3RandomMath {
    public static void main(String[] args) {
        double a = generateRandomNumber();
        double b = generateRandomNumber();
        validateBiggerNumber(a,b);
    }

    private static void validateBiggerNumber(double a, double b) {
        System.out.println("A = " + a);
        System.out.println("B = " + b);
        if (a == b){
            System.out.println("The numbers are equal");
        } else if(a > b){
            System.out.println("A is bigger than B");
        } else {
            System.out.println("B is bigger than A");
        }
    }

    private static double generateRandomNumber() {
        return Math.random();
    }
}
