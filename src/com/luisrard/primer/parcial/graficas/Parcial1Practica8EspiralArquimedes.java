package com.luisrard.primer.parcial.graficas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

public class Parcial1Practica8EspiralArquimedes extends JFrame implements Runnable{
    private static final short HALF_CIRCUMFERENCE = 180;
    private static final short FULL_CIRCUMFERENCE = 360;
    private static final short JUMPS_SIZE = 10;
    private static final short STEPS_PER_HALF_CIRCLE = 10;
    private static final short ARC_ANGLE = HALF_CIRCUMFERENCE / STEPS_PER_HALF_CIRCLE;
    private static final int DELAY = 10;

    private int x;
    private int y;
    private int width;
    private int height;
    private int startAngle;
    private boolean upHalf;
    private boolean pause = false;
    private boolean painting = false;

    private Color color;

    public static void main(String[] args) {
        new Parcial1Practica8EspiralArquimedes().run();
    }

    public Parcial1Practica8EspiralArquimedes() throws HeadlessException {
        super("Archimedes' spiral");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(800,800);
        setVisible(true);
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                color = obtainColorWithAxis(e.getX(), e.getY(), getWidth(), getHeight());
            }
        });
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pause = !pause;
                if (pause){
                    System.out.println("Pause");
                } else {
                    System.out.println("Continue");
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    @Override
    public void update(Graphics g) {
        if (!pause) {
            g.setColor(color);
            painting = true;
            if (width == JUMPS_SIZE){
                g.drawLine(x + JUMPS_SIZE /2, y + JUMPS_SIZE /2, x + JUMPS_SIZE, y + JUMPS_SIZE /2);
            }
            if (width <= getWidth()) {
                g.drawArc(x, y, width, height, startAngle, ARC_ANGLE);
                if (upHalf){
                    g.drawArc(x + JUMPS_SIZE / 2, y, width, height, startAngle + 180, ARC_ANGLE);
                } else{
                    g.drawArc(x - JUMPS_SIZE / 2, y, width, height, startAngle + 180, ARC_ANGLE);
                }


                startAngle += ARC_ANGLE;
                if (startAngle == HALF_CIRCUMFERENCE) {
                    y -= JUMPS_SIZE / 2;
                    width += JUMPS_SIZE;
                    height += JUMPS_SIZE;
                    upHalf = false;
                }
                if (startAngle == FULL_CIRCUMFERENCE) {
                    startAngle = 0;
                    x -= JUMPS_SIZE;
                    y -= JUMPS_SIZE / 2;
                    width += JUMPS_SIZE;
                    height += JUMPS_SIZE;
                    upHalf = true;
                }
            } else {
                super.paint(g);
                g.clearRect(0,0,getWidth(), getHeight());
            }
            painting = false;
        }
    }

    @Override
    public void run() {
        while (true){
            if (painting){
                System.out.println("painting");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {}
            }
            else {
                x = getWidth() / 2 - JUMPS_SIZE;
                y = getHeight() / 2;
                width = JUMPS_SIZE;
                height = JUMPS_SIZE;
                startAngle = 0;
                upHalf = true;
                while (width <= getWidth()) {
                    try {
                        Thread.sleep(DELAY);
                    } catch (InterruptedException e) {
                    }
                    update(getGraphics());
                }
            }
        }
    }

    public static Color obtainColorWithAxis(int x, int y, int width, int height){
        int r = x * 255 / width;
        int b = y * 255 / height;
        return new Color(r,0,b);
    }
}
