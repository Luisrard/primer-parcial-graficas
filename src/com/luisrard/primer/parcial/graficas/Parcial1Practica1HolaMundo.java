package com.luisrard.primer.parcial.graficas;

public class Parcial1Practica1HolaMundo {
    public static void main(String[] args) {
        System.out.println("Hola mundo");
        Integer i = 10;
        System.out.println(change(i));
        System.out.println(i);
    }

    public static Integer change(Integer     i){
        return ++i;
    }
}
