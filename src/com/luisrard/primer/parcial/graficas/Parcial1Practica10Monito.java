package com.luisrard.primer.parcial.graficas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Parcial1Practica10Monito extends JFrame{
    public static void main(String[] args) {
        new Parcial1Practica10Monito();
    }

    public Parcial1Practica10Monito() throws HeadlessException {
        super("Uso de gráficos");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(200,300);
        setResizable(false);
        setVisible(true);
    }


    @Override
    public void paint(Graphics g) {
        //Face
        g.drawArc(50,60, 50,50,0,360);
        g.drawArc(60,70, 30, 30, 180, 180);
        g.drawArc(60,70,30,30, 180, 180);
        g.fillOval(65, 75, 5, 5);
        g.fillOval(80, 75, 5, 5);
        //Body
        g.drawLine(75, 110, 75, 200);
        //Arms
        g.drawLine(75, 120, 45, 160);
        g.drawLine(75, 120, 105, 160);
        //Legs
        g.drawLine(75, 200,45, 240);
        g.drawLine(75, 200,105, 240);
    }

}
