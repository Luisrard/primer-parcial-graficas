package com.luisrard.primer.parcial.graficas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Parcial1Practica15KeyListener extends JFrame{
    public static void main(String[] args) {
        new Parcial1Practica15KeyListener();
    }

    public Parcial1Practica15KeyListener() throws HeadlessException {
        super("Key Listener");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400,300);
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                System.out.println("Key Typed: " + e.getKeyChar());
            }

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("Key Pressed: " + e.getKeyChar());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println("Key Released: " + e.getKeyChar());

            }
        });
        setVisible(true);
    }

}
