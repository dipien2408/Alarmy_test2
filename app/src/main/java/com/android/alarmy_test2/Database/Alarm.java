package com.android.alarmy_test2.Database;

import static com.android.alarmy_test2.Receiver.AlarmBroadcastReceiver.ALARM_TONE;
import static com.android.alarmy_test2.Receiver.AlarmBroadcastReceiver.LABEL;
import static com.android.alarmy_test2.Receiver.AlarmBroadcastReceiver.ONESHOT;
import static com.android.alarmy_test2.Receiver.AlarmBroadcastReceiver.MONDAY;
import static com.android.alarmy_test2.Receiver.AlarmBroadcastReceiver.SNOOZED;
import static com.android.alarmy_test2.Receiver.AlarmBroadcastReceiver.SNOOZED_MINUTE;
import static com.android.alarmy_test2.Receiver.AlarmBroadcastReceiver.TUESDAY;
import static com.android.alarmy_test2.Receiver.AlarmBroadcastReceiver.VIBRATE;
import static com.android.alarmy_test2.Receiver.AlarmBroadcastReceiver.WEDNESDAY;
import static com.android.alarmy_test2.Receiver.AlarmBroadcastReceiver.THURSDAY;
import static com.android.alarmy_test2.Receiver.AlarmBroadcastReceiver.FRIDAY;
import static com.android.alarmy_test2.Receiver.AlarmBroadcastReceiver.SATURDAY;
import static com.android.alarmy_test2.Receiver.AlarmBroadcastReceiver.SUNDAY;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


import com.android.alarmy_test2.Receiver.AlarmBroadcastReceiver;
import com.android.alarmy_test2.Utilities.DayUtil;

import java.util.Calendar;
import java.util.Locale;

@Entity(tableName = "alarm_table")
public class Alarm {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int timeHour;

    private int timeMinute;

    private boolean monday, tuesday, wednesday, thursday, friday, saturday, sunday;

    private String alarmTone;

    private boolean isEnabled;

    private boolean isOneShot;

    private boolean isVibrate;

    private String label;

    private boolean isSnoozed;

    private int snoozeMinute;

    public Alarm(int timeHour, int timeMinute, boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday, String alarmTone, boolean isEnabled, boolean isOneShot, boolean isVibrate, String label, boolean isSnoozed, int snoozeMinute) {
        this.timeHour = timeHour;
        this.timeMinute = timeMinute;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
        this.alarmTone = alarmTone;
        this.isEnabled = isEnabled;
        this.isOneShot = isOneShot;
        this.isVibrate = isVibrate;
        this.label = label;
        this.isSnoozed = isSnoozed;
        this.snoozeMinute = snoozeMinute;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getTimeHour() {
        return timeHour;
    }

    public void setTimeHour(int timeHour) {
        this.timeHour = timeHour;
    }

    public int getTimeMinute() {
        return timeMinute;
    }

    public void setTimeMinute(int timeMinute) {
        this.timeMinute = timeMinute;
    }

    public boolean isMonday() {
        return monday;
    }

    public void setMonday(boolean monday) {
        this.monday = monday;
    }

    public boolean isTuesday() {
        return tuesday;
    }

    public void setTuesday(boolean tuesday) {
        this.tuesday = tuesday;
    }

    public boolean isWednesday() {
        return wednesday;
    }

    public void setWednesday(boolean wednesday) {
        this.wednesday = wednesday;
    }

    public boolean isThursday() {
        return thursday;
    }

    public void setThursday(boolean thursday) {
        this.thursday = thursday;
    }

    public boolean isFriday() {
        return friday;
    }

    public void setFriday(boolean friday) {
        this.friday = friday;
    }

    public boolean isSaturday() {
        return saturday;
    }

    public void setSaturday(boolean saturday) {
        this.saturday = saturday;
    }

    public boolean isSunday() {
        return sunday;
    }

    public void setSunday(boolean sunday) {
        this.sunday = sunday;
    }

    public String getAlarmTone() {
        return alarmTone;
    }

    public void setAlarmTone(String alarmTone) {
        this.alarmTone = alarmTone;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public boolean isOneShot() {
        return isOneShot;
    }

    public void setOneShot(boolean oneShot) {
        isOneShot = oneShot;
    }

    public boolean isVibrate() {
        return isVibrate;
    }

    public void setVibrate(boolean vibrate) {
        isVibrate = vibrate;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isSnoozed() {
        return isSnoozed;
    }

    public void setSnoozed(boolean snoozed) {
        isSnoozed = snoozed;
    }

    public int getSnoozeMinute() {
        return snoozeMinute;
    }

    public void setSnoozeMinute(int snoozeMinute) {
        this.snoozeMinute = snoozeMinute;
    }

    public void schedule(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        intent.putExtra(ONESHOT, isOneShot);
        intent.putExtra(VIBRATE, isVibrate);
        intent.putExtra(MONDAY, monday);
        intent.putExtra(TUESDAY, tuesday);
        intent.putExtra(WEDNESDAY, wednesday);
        intent.putExtra(THURSDAY, thursday);
        intent.putExtra(FRIDAY, friday);
        intent.putExtra(SATURDAY, saturday);
        intent.putExtra(SUNDAY, sunday);
        intent.putExtra(LABEL, label);
        intent.putExtra(SNOOZED, isSnoozed);
        intent.putExtra(ALARM_TONE, alarmTone);
        intent.putExtra(SNOOZED_MINUTE, snoozeMinute);

        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, id, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, timeHour);
        calendar.set(Calendar.MINUTE, timeMinute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // if alarm time has already passed, increment day by 1
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        }

        if (!isOneShot) {
            String toastText = null;
            try {
                toastText = String.format(Locale.getDefault(),"One Time Alarm %s scheduled for %s at %02d:%02d", label, DayUtil.toDay(calendar.get(Calendar.DAY_OF_WEEK)), timeHour, timeMinute, id);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String finalToastText = toastText;
            new Handler().postDelayed(() -> {
                Toast.makeText(context, finalToastText, Toast.LENGTH_LONG).show();
                //your code here
            },1500); // will trigger your code after 1.5 seconds

            alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    alarmPendingIntent
            );
        } else {
            String toastText = String.format(Locale.getDefault(),"Recurring Alarm %s scheduled at %02d:%02d", getRepeatingDaysText(), timeHour, timeMinute);
            String finalToastText = toastText;
            new Handler().postDelayed(() -> {
                Toast.makeText(context, finalToastText, Toast.LENGTH_LONG).show();
                //your code here
            },1500); // will trigger your code after 1.5 seconds

            final long RUN_DAILY = 24 * 60 * 60 * 1000;
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    RUN_DAILY,
                    alarmPendingIntent
            );
        }

        this.isEnabled = true;
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, id, intent, 0);
        alarmManager.cancel(alarmPendingIntent);
        this.isEnabled = false;

        String toastText = String.format(Locale.getDefault(),"Alarm cancelled for %02d:%02d", timeHour, timeMinute);
        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
        Log.i("cancel", toastText);
    }

    public String getRepeatingDaysText() {
        if (isOneShot) {
            return null;
        }

        String days = "";
        if (monday) {
            days += "T2 ";
        }
        if (tuesday) {
            days += "T3 ";
        }
        if (wednesday) {
            days += "T4 ";
        }
        if (thursday) {
            days += "T5 ";
        }
        if (friday) {
            days += "T6 ";
        }
        if (saturday) {
            days += "T7 ";
        }
        if (sunday) {
            days += "CN ";
        }

        return days;
    }
}
