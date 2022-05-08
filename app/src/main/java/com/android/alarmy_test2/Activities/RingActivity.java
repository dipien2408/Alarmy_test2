package com.android.alarmy_test2.Activities;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.alarmy_test2.Database.Alarm;
import com.android.alarmy_test2.R;
import com.android.alarmy_test2.Services.AlarmService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Random;

public class RingActivity extends AppCompatActivity {

    ImageView dismiss, snooze, clock;
    TextView time, date, label;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_ringing);

        Intent intent = getIntent();

        dismiss = findViewById(R.id.alarm_ringing_dismiss);
        snooze = findViewById(R.id.alarm_ringing_snooze);
        clock = findViewById(R.id.alarm_ringing_clock);

        time = findViewById(R.id.alarm_ringing_time);
        date = findViewById(R.id.alarm_ringing_date);
        label = findViewById(R.id.alarm_ringing_title);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM");
        DateTimeFormatter dtft = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime now = LocalDateTime.now();
        time.setText(dtft.format(now));
        date.setText(dtf.format(now));
        label.setText(R.string.alarm);
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentService = new Intent(getApplicationContext(), AlarmService.class);
                getApplicationContext().stopService(intentService);
                finish();
            }
        });

        snooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.add(Calendar.MINUTE, 5);

                Alarm alarm = new Alarm(
                        calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false, false, false, false, false, false, false
                        , "ringtone", true, true, true, "Snoozed", false, 5);

                alarm.schedule(getApplicationContext());

                Intent intentService = new Intent(getApplicationContext(), AlarmService.class);
                getApplicationContext().stopService(intentService);
                finish();
            }
        });

        animateClock();
    }

    private void animateClock() {
        ObjectAnimator rotateAnimation = ObjectAnimator.ofFloat(clock, "rotation", 0f, 20f, 0f, -20f, 0f);
        rotateAnimation.setRepeatCount(ValueAnimator.INFINITE);
        rotateAnimation.setDuration(800);
        rotateAnimation.start();
    }
}
