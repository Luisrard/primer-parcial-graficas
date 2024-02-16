package com.luisrard.primer.parcial.graficas;

public class Parcial1Practica2RandomMath {
    public static void main(String[] args) {
        int a = generateRandomNumber();
        int b = generateRandomNumber();
        validateBiggerNumber(a,b);
    }

    private static void validateBiggerNumber(int a, int b) {
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

    private static int generateRandomNumber() {
        return (int) (1000 * Math.random());
    }
}
