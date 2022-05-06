package com.android.alarmy_test2;

import android.net.Uri;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "alarm_table")
public class Alarm {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int timeHour;

    private int timeMinute;

    @TypeConverters({Converters.class})
    private final Boolean[] repeatingDays;

    private String alarmTone;

    private boolean isEnabled;

    private boolean vibrate;

    private String label;

    private boolean snoozed;

    private int snoozeHour;

    private int snoozeMinute;

    private int snoozeSeconds;

    public Alarm(int timeHour, int timeMinute, Boolean[] repeatingDays, String alarmTone, boolean isEnabled, boolean vibrate, String label, boolean snoozed, int snoozeHour, int snoozeMinute, int snoozeSeconds) {
        this.timeHour = timeHour;
        this.timeMinute = timeMinute;
        this.repeatingDays = repeatingDays;
        this.alarmTone = alarmTone;
        this.isEnabled = isEnabled;
        this.vibrate = vibrate;
        this.label = label;
        this.snoozed = snoozed;
        this.snoozeHour = snoozeHour;
        this.snoozeMinute = snoozeMinute;
        this.snoozeSeconds = snoozeSeconds;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setTimeHour(int timeHour) {
        this.timeHour = timeHour;
    }

    public int getTimeHour() {
        return timeHour;
    }

    public void setTimeMinute(int timeMinute) {
        this.timeMinute = timeMinute;
    }

    public int getTimeMinute() {
        return timeMinute;
    }

    public void setRepeatingDay(int dayOfWeek, boolean value) {
        this.repeatingDays[dayOfWeek] = value;
    }

    public Boolean[] getRepeatingDays() {
        return repeatingDays;
    }

    public void setAlarmTone(String alarmTone) {
        this.alarmTone = alarmTone;
    }

    public String getAlarmTone() {
        return alarmTone;
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setVibrate(boolean vibrate) {
        this.vibrate = vibrate;
    }

    public boolean isVibrate() {
        return vibrate;
    }

    public void setSnoozed(boolean snoozed) {
        this.snoozed = snoozed;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public boolean isSnoozed() {
        return snoozed;
    }

    public void setSnoozeHour(int snoozeHour) {
        this.snoozeHour = snoozeHour;
    }

    public int getSnoozeHour() {
        return snoozeHour;
    }

    public void setSnoozeMinute(int snoozeMinute) {
        this.snoozeMinute = snoozeMinute;
    }

    public int getSnoozeMinute() {
        return snoozeMinute;
    }

    public void setSnoozeSeconds(int snoozeSeconds) {
        this.snoozeSeconds = snoozeSeconds;
    }

    public int getSnoozeSeconds() {
        return snoozeSeconds;
    }
}
