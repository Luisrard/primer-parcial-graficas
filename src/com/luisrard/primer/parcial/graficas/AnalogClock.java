package com.luisrard.primer.parcial.graficas;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnalogClock extends JFrame implements Runnable, ActionListener {
    private final JLabel status;
    private boolean ready = false;
    private AnalogClockPanel clockPanel;

    public static void main(String[] args) {
        AnalogClock analogClock = new AnalogClock();
        new Thread(analogClock).start();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}
        while (true){
            try {
                Thread.sleep(1);
            } catch (InterruptedException ignored) {}
            paintWorkArea();
        }
    }

    private void paintWorkArea() {
        if (ready){
            clockPanel.paintWorkArea();
        }
    }

    public AnalogClock() throws HeadlessException {
        super("Little Clock");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(480,520);
        setVisible(true);

        JMenuBar menuBar = new JMenuBar();
        //menu Modificar
        JMenu menuArchivo = new JMenu("Modificar");

        //opcion Nuevo
        JMenuItem opcionNuevo = new JMenuItem("Nuevo", 'N');
        opcionNuevo.addActionListener(this);
        opcionNuevo.setActionCommand("Nuevo");
        menuArchivo.add(opcionNuevo);

        menuArchivo.addSeparator();
        //opcion Alarma
        JMenuItem opcionAlarma = new JMenuItem("Alarma", 'A');
        opcionAlarma.addActionListener(this);
        opcionAlarma.setActionCommand("Alarma");
        menuArchivo.add(opcionAlarma);

        menuBar.add(menuArchivo);
        setJMenuBar(menuBar);
        status = new JLabel("Status", JLabel.LEFT);
        status.setBackground(Color.WHITE);
        clockPanel = new AnalogClockPanel(status);
        getContentPane().add(clockPanel, BorderLayout.CENTER);
        //Agregar barra de estado
        getContentPane().add(status, BorderLayout.SOUTH);

        menuArchivo.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                clockPanel.setStop(true);
            }

            @Override
            public void menuDeselected(MenuEvent e) {
                clockPanel.setStop(false);
            }

            @Override
            public void menuCanceled(MenuEvent e) {
                clockPanel.setStop(false);
            }
        });

        //Agregar zona grafica
        ready = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        System.out.println(command);
        if ("Nuevo".equals(command)) {

        } else if ("Alarma".equals(command)) {
            System.out.println("alarma pai");
            clockPanel.setEditing(true);
        }
    }
}
