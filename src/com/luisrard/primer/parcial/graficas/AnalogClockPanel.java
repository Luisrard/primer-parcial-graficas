package com.luisrard.primer.parcial.graficas;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

//TODO: Move the alarm backwards and play the alarm sound
public class AnalogClockPanel extends JPanel implements MouseMotionListener, MouseListener{
    private final SoundPlayer ringPlayer;
    private final SoundPlayer tiktakPlayer;
    private Image backGround;
    private Image bufferMin;
    private Image bufferHour;
    private JLabel status;
    private boolean changeSec, changeMin, changeHour;
    private final TimeData timeData = new TimeData(-200,-20,-20, -100);
    private final TimeData alarmTimeData = new TimeData(-1,-1,-1, -1);
    private final TimeData prevTimeData = new TimeData(-1,-1,-1, -1);
    private int prevMinHandChange = -20;
    private int prevHourHandChange = -20;
    private int startAngleSec;
    private int startAngleSecOrigin = -1000;
    private int totalSpins = 0;
    private double prevDegree = -1;
    private boolean editing = false;
    private boolean rEditing = false;
    private boolean stop = false;
    private boolean ringing = false;

    private static int angle12(int hora, int extraAngle) {
        return 90 + hora * -30 - extraAngle - 30;
    }

    private static int angle60(int hora, int extraAngle) {
        return 90 + hora * -6  - extraAngle;
    }

    private static int originAngleToSeconds(int originAngle){
        if (originAngle > 90){ //15 - 59
            return 15 + (360 - originAngle) / 6;
        } else {
            return 15 - originAngle / 6;
        }
    }

    public AnalogClockPanel(final JLabel status) {
        this.status = status;
        ringPlayer = new SoundPlayer("ring.wav", true);
        tiktakPlayer = new SoundPlayer("tiktak.wav", true);
        tiktakPlayer.playSound();
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    public void paintWorkArea(){
        if (backGround == null) {
            BufferedImage myPicture = null;
            try {
                myPicture = ImageIO.read(new File("src/img/clock.png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Image img = new ImageIcon(myPicture).getImage();
            backGround = createImage(getWidth(), getHeight());
            Graphics gBackGround = backGround.getGraphics();
            gBackGround.setClip(0, 0, getWidth(), getHeight());
            gBackGround.drawImage(img, 0, 0, this);
        }
        updateWorkArea();
    }
    private void updateWorkArea(){
        if (!stop & (!editing || !rEditing)){
            Graphics g = getGraphics();
            Calendar time = Calendar.getInstance();
            calculateOffsets(time);
            if(changeHour) {
                bufferHour = createImage(getWidth(), getHeight());
                Graphics gBuffer = bufferHour.getGraphics();
                gBuffer.setColor(Color.RED);
                gBuffer.setClip(0,0, getWidth(), getHeight());
                gBuffer.drawImage(backGround, 0, 0, this);
                gBuffer.fillArc((getWidth() - 150) / 2 + 7, (getHeight() - 150) / 2 + 45, 140, 140, angle12(timeData.getHour(), prevHourHandChange / 2),6);
                changeHour = false;
            }
            if (changeMin){
                bufferMin = createImage(getWidth(), getHeight());
                Graphics gBuffer = bufferMin.getGraphics();
                gBuffer.setClip(0,0, getWidth(), getHeight());
                gBuffer.drawImage(bufferHour, 0, 0, this);
                gBuffer.fillArc((getWidth() - 180) / 2 + 7, (getHeight() - 180) / 2 + 45, 170, 170, angle60(timeData.getMin(), prevMinHandChange / 10),5);
                changeMin = false;
            }
            if (changeSec){
                if (timeData.equals(alarmTimeData)){
                    startRinging();
                }
                g.setClip(0,0,getWidth(), getHeight());
                g.setColor(Color.ORANGE);
                g.drawImage(bufferMin, 0, 0, this);
                startAngleSec = angle60(timeData.getSec(), timeData.getMillis() / 166);
                g.fillArc((getWidth() - 210) / 2 + 7, (getHeight() - 210) / 2 + 45, 200, 200, startAngleSec,4);
                printHour();
                changeSec = false;
            }
        }
    }

    private void startRinging() {
        if (!ringing) {
            ringing = true;
            ringPlayer.playSound();
            System.out.println("RINGGGGG");
            System.out.println("RINGGGGG");
        }
    }

    private void printHour() {
        if (alarmTimeData.getHour() != -1){
            status.setText(timeData.toString() + ", alarm " + alarmTimeData);
        } else {
            status.setText(timeData.toString());
        }
    }

    private void calculateOffsets(Calendar time){
        int currentHours = time.get(Calendar.HOUR);
        if(currentHours != timeData.getHour()) {
            timeData.setHour(currentHours);
            changeHour = true;
        }
        int currenMinutes = time.get(Calendar.MINUTE);
        if(currenMinutes != timeData.getMin()) {
            if (prevHourHandChange > currenMinutes){
                prevHourHandChange = 0;
                changeHour = true;
            } else if (currenMinutes >= prevHourHandChange + 2){
                prevHourHandChange = (prevHourHandChange / 2) * 2;
                changeHour = true;
            }
            timeData.setMin(currenMinutes);
            changeMin = true;
        }

        int currentSeconds = time.get(Calendar.SECOND);
        if(currentSeconds != timeData.getSec()) {
            if (prevMinHandChange > currentSeconds){
                prevMinHandChange = 0;
                changeMin = true;
            } else if (currentSeconds >= prevMinHandChange + 10) {
                prevMinHandChange = (currentSeconds / 10) * 10;
                changeMin = true;
            }
            timeData.setSec(currentSeconds);
            changeSec = true;
        }

        int currentMillis = time.get(Calendar.MILLISECOND);
        if (timeData.getMillis() > currentMillis ){
            timeData.setMillis(0);
            changeSec = true;
        } else if(currentMillis > timeData.getMillis() + 166){
            timeData.setMillis((currentMillis / 166) * 166);
            changeSec = true;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(editing) {
            tiktakPlayer.stopSound();
            rEditing = true;
            boolean up;
            int y = (262) - e.getY();

            up = e.getY() <= 262;

            int x = -( getWidth() / 2) + e.getX();

            double tan = Math.atan((double) y / (double) x);
            double degrees = Math.toDegrees(tan);
            if (x == 0){
                if (up){
                    degrees = 90;
                }else {
                    degrees = 270;
                }
            } else if (e.getX() > getWidth() / 2) {//right
                if (!up) {//270-360
                    degrees = 360 + degrees;
                }
            } else {//left
                degrees = 180 + degrees;
            }
            if (prevDegree != degrees){
                changeHour(degrees);
            }
        }
    }

    private void changeHour(double degrees) {
        if (startAngleSecOrigin == -1000){
            if (startAngleSec < 0 ){
                startAngleSecOrigin = 360 + startAngleSec;
            }else{
                startAngleSecOrigin = startAngleSec;
            }
        }
        if (prevDegree > degrees + 5 || prevDegree < degrees - 5){
            if (prevDegree < 270 && prevDegree > 90 && degrees < 90){
                totalSpins += 1;
            }else if ((prevDegree > 270 || prevDegree < 90) && degrees > 90 && degrees < 180){
                totalSpins -= 1 ;
            }

            prevDegree = degrees;
            calculateNewTime();
            moveClock();
        }
    }
    private void calculateNewTime(){
        int newHour = timeData.getHour();
        int newMin = timeData.getMin();
        int newSec = totalSpins * 60;

        newSec += originAngleToSeconds((int) prevDegree);

        int piv;
        if (newSec >= 60){
            piv = newSec % 60;
            newMin += newSec / 60;
            newSec = piv;

            if (newMin >= 60){
                piv = newMin % 60;
                newHour += newMin / 60;
                newMin = piv;
            }
            if (newHour >= 12){
                newHour = newHour % 12;
            }
        }
        if (newSec < 0){
            piv = newSec % 60;
            newMin += newSec / 60;
            newSec = 60 + piv;

            if (newMin >= 60){
                piv = newMin % 60;
                newHour += newMin / 60;
                newMin = 60 + piv;
            }
            if (newHour >= 12){
                newHour = 12 + newHour % 12;
            }
        }

        alarmTimeData.setHour(newHour);
        alarmTimeData.setMin(newMin);
        alarmTimeData.setSec(newSec);
    }


    private void moveClock() {
        Graphics g = getGraphics();
        if (prevTimeData.getHour() != alarmTimeData.getHour()){
            prevTimeData.setHour(alarmTimeData.getHour());

            bufferHour = createImage(getWidth(), getHeight());
            Graphics gBuffer = bufferHour.getGraphics();
            gBuffer.setColor(Color.RED);
            gBuffer.setClip(0, 0, getWidth(), getHeight());
            gBuffer.drawImage(backGround, 0, 0, this);
            gBuffer.fillArc((getWidth() - 150) / 2 + 5, (getHeight() - 150) / 2 + 40, 140, 140, angle12(alarmTimeData.getHour(),0 ), 6);
        }
        if (prevTimeData.getMin() != alarmTimeData.getMin()){
            prevTimeData.setMin(alarmTimeData.getMin());

            bufferMin = createImage(getWidth(), getHeight());
            Graphics gBuffer = bufferMin.getGraphics();
            gBuffer.setClip(0, 0, getWidth(), getHeight());
            gBuffer.drawImage(bufferHour, 0, 0, this);
            gBuffer.fillArc((getWidth() - 180) / 2 + 5, (getHeight() - 180) / 2 + 40, 170, 170, angle60(alarmTimeData.getMin(), 0), 5);
        }

        g.setClip(0,0,getWidth(), getHeight());
        g.setColor(Color.ORANGE);
        g.drawImage(bufferMin, 0, 0, this);
        g.fillArc((getWidth() - 210) / 2 + 5, (getHeight() - 210) / 2 + 40, 200, 200, (int) prevDegree,4);
        printHour();
    }

    public void resetAlarm(){
        alarmTimeData.setHour(-1);
        alarmTimeData.setMin(-1);
        alarmTimeData.setSec(-1);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (ringing){
            stopRinging();
        }
    }

    private void stopRinging() {
        ringPlayer.stopSound();
        ringing = false;
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (editing) {
            resetEditValues();
        }
    }

    private void resetEditValues() {
        prevDegree = 0;
        totalSpins = 0;
        startAngleSecOrigin = -1000;
        editing = false;
        timeData.setSec(-20);
        timeData.setMin(-20);
        timeData.setHour(-100);
        prevTimeData.setSec(-1);
        prevTimeData.setMin(-1);
        prevTimeData.setHour(-1);
        rEditing = false;
        tiktakPlayer.playSound();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void setEditing(boolean editing) {
        this.editing = editing;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }


}
