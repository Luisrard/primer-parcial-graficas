package com.luisrard.primer.parcial.graficas;

public class TimeData {
    private int millis;
    private int sec;
    private int min;
    private int hour;

    public TimeData(int millis, int sec, int min, int hour) {
        this.sec = sec;
        this.min = min;
        this.hour = hour;
        this.millis = millis;
    }

    public TimeData() {
    }

    public int getSec() {
        return sec;
    }

    public void setSec(int sec) {
        this.sec = sec;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMillis() {
        return millis;
    }

    public void setMillis(int millis) {
        this.millis = millis;
    }

    @Override
    public String toString() {
        return hour + ":" + min + ":" + sec;
    }
}
