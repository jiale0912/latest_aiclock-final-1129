package com.example.aiclock;

import android.net.Uri;

import java.time.DayOfWeek;


public class Alarm {

    private int id;
    private int hour;
    private int min;
    private String tips;
    private int week;
    private Uri soundtrack;
    private int soundorvibrator;
    private int status;
    private int flag;
    private int weeklength;

    public int getWeeklength() {
        return weeklength;
    }

    public void setWeeklength(int weeklength) {
        this.weeklength = weeklength;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public Alarm() {
        this.id = id;
        this.hour = hour;
        this.min = min;
        this.tips = tips;
        this.week = week;
        this.soundtrack = soundtrack;
        this.soundorvibrator = soundorvibrator;
        this.status = status;
        this.flag = flag;
        this.weeklength = weeklength;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public void setSoundtrack(Uri soundtrack) {
        this.soundtrack = soundtrack;
    }

    public void setSoundorvibrator(int soundorvibrator) {
        this.soundorvibrator = soundorvibrator;
    }

    public int getId() {
        return id;
    }

    public int getHour() {
        return hour;
    }

    public int getMin() {
        return min;
    }

    public String getTips() {
        return tips;
    }

    public int getWeek() {
        return week;
    }

    public Uri getSoundtrack() {
        return soundtrack;
    }

    public int getSoundorvibrator() {
        return soundorvibrator;
    }
}
