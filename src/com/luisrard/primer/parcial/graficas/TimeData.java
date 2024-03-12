package com.luisrard.primer.parcial.graficas;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeData timeData = (TimeData) o;
        return sec == timeData.sec && min == timeData.min && hour == timeData.hour;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sec, min, hour);
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
