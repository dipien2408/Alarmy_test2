package com.android.alarmy_test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.slider.Slider;


public class AddAlarm extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.android.alarmy_test2.EXTRA_ID";
    public static final String EXTRA_TIMEHOUR =
            "com.android.alarmy_test2.EXTRA_TIMEHOUR";
    public static final String EXTRA_TIMEMINUTE =
            "com.android.alarmy_test2.EXTRA_TIMEMINUTE";
    public static final String EXTRA_TONE =
            "com.android.alarmy_test2.EXTRA_TONE";
    public static final String EXTRA_ENABLED =
            "com.android.alarmy_test2.EXTRA_ENABLED";
    public static final String EXTRA_VIBRATE =
            "com.android.alarmy_test2.EXTRA_VIBRATE";
    public static final String EXTRA_LABEL =
            "com.android.alarmy_test2.LABEL";
    public static final String EXTRA_SNOOZED =
            "com.android.alarmy_test2.SNOOZED";
    public static final String EXTRA_ADD_OR_EDIT =
            "com.android.alarmy_test2.EXTRA_ADD_OR_EDIT";
    TimePicker timePicker;
    ImageView backBtn, playRingtoneBtn;
    CheckBox repeatingAllDayCheckBox, checkBoxVibrate;
    Button repeatBtn;
    TextView timeRemaining, ringTone, textViewSnoozed, textViewLabel;
    Slider volumeSlider;
    ImageView settingRingtone, settingSnoozed, settingLabel;
    ExtendedFloatingActionButton saveAlarmFab;

    private boolean isPlay;
    //0 is add, 1 is edit
    private int isAddOrEdit = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        timePicker = findViewById(R.id.time_picker);
        backBtn = findViewById(R.id.back_pressed);
        playRingtoneBtn = findViewById(R.id.play_ring_tone);
        repeatingAllDayCheckBox = findViewById(R.id.repeating_all_day);
        checkBoxVibrate = findViewById(R.id.vibrate_checkbox);
        repeatBtn = findViewById(R.id.repeating_monday);
        timeRemaining = findViewById(R.id.time_remaining);
        ringTone = findViewById(R.id.ring_tone);
        textViewSnoozed = findViewById(R.id.snoozed);
        textViewLabel = findViewById(R.id.label);
        volumeSlider = findViewById(R.id.volume_slider);
        saveAlarmFab = findViewById(R.id.save_alarm_fab);

        timePicker.setIs24HourView(true);

        backBtn.setOnClickListener(view -> AddAlarm.super.onBackPressed());
        saveAlarmFab.setOnClickListener(view -> saveNote());

        isPlay = false;
        playRingtoneBtn.setOnClickListener(view -> {
            if(isPlay) {
                playRingtoneBtn.setImageResource(R.drawable.ic_play);
                isPlay = false;
            } else {
                playRingtoneBtn.setImageResource(R.drawable.ic_stop);
                isPlay = true;
            }
        });

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            timePicker.setCurrentHour(intent.getIntExtra(EXTRA_TIMEHOUR, 1));
            timePicker.setCurrentMinute(intent.getIntExtra(EXTRA_TIMEMINUTE, 1));
            ringTone.setText(intent.getStringExtra(EXTRA_TONE));
            checkBoxVibrate.setChecked(intent.getBooleanExtra(EXTRA_VIBRATE, false));
            textViewLabel.setText(intent.getStringExtra(EXTRA_LABEL));
            textViewSnoozed.setText(Boolean.toString(intent.getBooleanExtra(EXTRA_SNOOZED, false)));
            isAddOrEdit = 1;
        }
    }

    private void saveNote() {
        int timeHour = timePicker.getCurrentHour();
        int timeMinute = timePicker.getCurrentMinute();
        String tone = ringTone.getText().toString();
        boolean vibrate = checkBoxVibrate.isChecked();
        String label = textViewLabel.getText().toString();
        boolean snoozed;
        snoozed = !textViewSnoozed.getText().toString().equals("Tắt");

        Intent data = new Intent();
        data.putExtra(EXTRA_TIMEHOUR, timeHour);
        data.putExtra(EXTRA_TIMEMINUTE, timeMinute);
        data.putExtra(EXTRA_TONE, tone);
        data.putExtra(EXTRA_ENABLED, true);
        data.putExtra(EXTRA_VIBRATE, vibrate);
        data.putExtra(EXTRA_LABEL, label);
        data.putExtra(EXTRA_SNOOZED, snoozed);
        data.putExtra(EXTRA_ADD_OR_EDIT, isAddOrEdit);
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }


}