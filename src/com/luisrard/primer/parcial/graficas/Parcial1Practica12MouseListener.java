package com.luisrard.primer.parcial.graficas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Parcial1Practica12MouseListener extends JFrame{
    public static void main(String[] args) {
        new Parcial1Practica12MouseListener();
    }

    public Parcial1Practica12MouseListener() throws HeadlessException {
        super("Mouse Listener");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400,300);
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Mouse clicked " + e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("Mouse pressed " + e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("Mouse released " + e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                System.out.println("Mouse entered " + e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                System.out.println("Mouse exited " + e);
            }
        });
        setVisible(true);
    }

}
