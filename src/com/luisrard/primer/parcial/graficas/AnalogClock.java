package com.luisrard.primer.parcial.graficas;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class AnalogClock extends JFrame implements Runnable{
    private Image backGround;
    private Image bufferMin;
    private Image bufferHour;
    int min = -1;
    int hour = -1;
    int sec = -1;
    public static void main(String[] args) {
        AnalogClock analogClock = new AnalogClock();
        new Thread(analogClock).start();
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}
            repaint();
        }
    }

    public AnalogClock() throws HeadlessException {
        super("Little Clock");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(400,400);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        if (backGround == null){
            backGround = createImage(getWidth(), getHeight());
            Graphics gBackGround = backGround.getGraphics();
            gBackGround.setClip(0,0, getWidth(), getHeight());
            gBackGround.drawOval((getWidth() - 100) / 2, (getHeight() - 100) / 2, 100, 100);
        }
        update(g);
    }

    @Override
    public void update(Graphics g) {
        g.setClip(0,0,getWidth(), getHeight());
        LocalDateTime time = LocalDateTime.now();
        if(time.getHour() != hour) {
            hour = time.getHour();

            bufferHour = createImage(getWidth(), getHeight());
            Graphics gBuffer = bufferHour.getGraphics();
            gBuffer.setClip(0,0, getWidth(), getHeight());
            gBuffer.drawImage(backGround, 0, 0, this);
            gBuffer.fillArc((getWidth() - 90) / 2 + 5, (getHeight() - 90) / 2 + 5, 80, 80, angle12(hour),6);
        }
        if (time.getMinute() != min){
            min = time.getMinute();

            bufferMin = createImage(getWidth(), getHeight());
            Graphics gBuffer = bufferMin.getGraphics();
            gBuffer.setClip(0,0, getWidth(), getHeight());
            gBuffer.drawImage(bufferHour, 0, 0, this);
            gBuffer.fillArc((getWidth() - 100) / 2 + 5, (getHeight() - 100) / 2 + 5, 90, 90, angle60(min),4);
        }
        g.drawImage(bufferMin, 0, 0, this);
        sec = time.getSecond();
        g.drawArc(100, 100, 50, 50, 0, 90);
        g.drawArc(100, 100, 50, 50, 180, 10);
        g.fillArc((getWidth() - 100) / 2 + 5, (getHeight() - 100) / 2 + 5, 90, 90, angle60(sec),2);
    }

    private static int angle12(int hora) {
        return 90 + hora * -30;
    }

    private static int angle60(int hora) {
        return 90 + hora * -6;
    }
}
