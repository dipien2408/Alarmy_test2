package com.android.alarmy_test2.AppCore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.alarmy_test2.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;
import java.util.Arrays;


public class AddAlarm extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.android.alarmy_test2.EXTRA_ID";
    public static final String EXTRA_TIMEHOUR =
            "com.android.alarmy_test2.EXTRA_TIMEHOUR";
    public static final String EXTRA_TIMEMINUTE =
            "com.android.alarmy_test2.EXTRA_TIMEMINUTE";
    public static final String EXTRA_ONESHOT =
            "com.android.alarmy_test2.EXTRA_ONESHOT";
    public static final String EXTRA_TONE =
            "com.android.alarmy_test2.EXTRA_TONE";
    public static final String EXTRA_ENABLED =
            "com.android.alarmy_test2.EXTRA_ENABLED";
    public static final String EXTRA_VIBRATE =
            "com.android.alarmy_test2.EXTRA_VIBRATE";
    public static final String EXTRA_LABEL =
            "com.android.alarmy_test2.EXTRA_LABEL";
    public static final String EXTRA_SNOOZED =
            "com.android.alarmy_test2.EXTRA_SNOOZED";
    public static final String EXTRA_SNOOZED_MINUTE =
            "com.android.alarmy_test2.EXTRA_SNOOZED_MINUTE";
    public static final String EXTRA_REPEAT =
            "com.android.alarmy_test2.EXTRA_REPEAT";
    public static final String EXTRA_ADD_OR_EDIT =
            "com.android.alarmy_test2.EXTRA_ADD_OR_EDIT";

    TimePicker timePicker;
    ImageView backBtn, playRingtoneBtn;
    CheckBox repeatingAllDayCheckBox, checkBoxVibrate;
    Button repeatBtn;
    TextView timeRemaining, ringTone, textViewSnoozed, textViewLabel;
    Slider volumeSlider;

    ImageView settingRingtone, settingSnoozed, settingLabel;

    ArrayList<Button> buttons = new ArrayList<>();

    ExtendedFloatingActionButton saveAlarmFab;

    boolean[] mRepeatingDays = new boolean[7];
    private boolean isPlay;
    Boolean True = true;

    //0 is add, 1 is edit
    private int isAddOrEdit = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        timePicker = findViewById(R.id.time_picker);
        timePicker.setIs24HourView(true);

        backBtn = findViewById(R.id.back_pressed);
        backBtn.setOnClickListener(view -> {
            Intent data = new Intent();
            data.putExtra(EXTRA_ADD_OR_EDIT, 3);
            setResult(RESULT_OK, data);
            AddAlarm.super.onBackPressed();
        });

        playRingtoneBtn = findViewById(R.id.play_ring_tone);
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

        if (buttons.size() == 0) {
            buttons.add((Button) findViewById(R.id.repeating_monday));
            buttons.add((Button) findViewById(R.id.repeating_tuesday));
            buttons.add((Button) findViewById(R.id.repeating_wednesday));
            buttons.add((Button) findViewById(R.id.repeating_thursday));
            buttons.add((Button) findViewById(R.id.repeating_friday));
            buttons.add((Button) findViewById(R.id.repeating_saturday));
            buttons.add((Button) findViewById(R.id.repeating_sunday));
        }

        repeatingAllDayCheckBox = findViewById(R.id.repeating_all_day);
        checkBoxVibrate = findViewById(R.id.vibrate_checkbox);
        repeatBtn = findViewById(R.id.repeating_monday);
        timeRemaining = findViewById(R.id.time_remaining);
        ringTone = findViewById(R.id.ring_tone);
        textViewSnoozed = findViewById(R.id.snoozed);
        textViewLabel = findViewById(R.id.label);
        volumeSlider = findViewById(R.id.volume_slider);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            mRepeatingDays = intent.getBooleanArrayExtra(EXTRA_REPEAT);

            initialBtn(mRepeatingDays);

            timePicker.setCurrentHour(intent.getIntExtra(EXTRA_TIMEHOUR, 1));
            timePicker.setCurrentMinute(intent.getIntExtra(EXTRA_TIMEMINUTE, 1));
            ringTone.setText(intent.getStringExtra(EXTRA_TONE));
            checkBoxVibrate.setChecked(intent.getBooleanExtra(EXTRA_VIBRATE, false));
            textViewLabel.setText(intent.getStringExtra(EXTRA_LABEL));
            textViewSnoozed.setText(new StringBuilder().append(intent.getIntExtra(EXTRA_SNOOZED_MINUTE, 5)).append("Phút").toString());
            isAddOrEdit = 1;

        } else {
            initialAddBtn();
        }

        saveAlarmFab = findViewById(R.id.save_alarm_fab);
        saveAlarmFab.setOnClickListener(view -> saveNote());

    }

    private void saveNote() {

        int timeHour, timeMinute;
        boolean vibrate, snoozed, oneShot;
        String tone, label;

        timeHour = timePicker.getCurrentHour();
        timeMinute = timePicker.getCurrentMinute();
        tone = ringTone.getText().toString();
        vibrate = checkBoxVibrate.isChecked();
        label = textViewLabel.getText().toString();
        snoozed = false;
        oneShot = true;

        if(repeatingAllDayCheckBox.isChecked()) {
            Arrays.fill(mRepeatingDays, true);
        } else {
            for(int i = 0; i < buttons.size(); i++) {
                mRepeatingDays[i] = buttons.get(i).getTag() == True;
            }
        }

        for(int i = 0; i < buttons.size(); i++) {
            if(buttons.get(i).getTag() == True) {
                oneShot = false;
                break;
            }
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TIMEHOUR, timeHour);
        data.putExtra(EXTRA_TIMEMINUTE, timeMinute);
        data.putExtra(EXTRA_ONESHOT, oneShot);
        data.putExtra(EXTRA_TONE, tone);
        data.putExtra(EXTRA_REPEAT, mRepeatingDays);
        data.putExtra(EXTRA_ENABLED, true);
        data.putExtra(EXTRA_VIBRATE, vibrate);
        data.putExtra(EXTRA_LABEL, label);
        data.putExtra(EXTRA_SNOOZED, snoozed);
        data.putExtra(EXTRA_SNOOZED_MINUTE, 5);
        data.putExtra(EXTRA_ADD_OR_EDIT, isAddOrEdit);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    public void takeBtn(View view) {
        Log.d("Take Button", this.toString());
        int index;
        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).getId() == view.getId()) {
                index = i;
                if ((buttons.get(i).getTag() == null) || (buttons.get(i).getTag() != True)) {
                    setBtnTrue(buttons.get(i));
                } else {
                    setBtnFalse(buttons.get(i));
                }
                Log.d("Index", String.valueOf(index) + buttons.get(i).getTag());

                break;
            }
        }
        for (int i = 0; i < buttons.size(); i++) {
            Log.d("Checked ", String.valueOf(buttons.get(i).getTag()));
        }
    }

    public void initialBtn(boolean[] booleans){
        for (int i = 0; i < booleans.length; i++) {
            if (booleans[i]) {
                setBtnTrue(buttons.get(i));
            } else {
                setBtnFalse(buttons.get(i));
            }
        }
    }

    public void initialAddBtn(){
        for (int i = 0; i < buttons.size(); i++) {
            setBtnFalse(buttons.get(i));
        }
    }

    public void setBtnTrue(Button button) {
        button.setTag(true);
        button.setBackgroundColor(ContextCompat.getColor(this, R.color.dayBtn));
    }

    public void setBtnFalse(Button button) {
        button.setTag(false);
        button.setBackgroundColor(ContextCompat.getColor(this, R.color.add_min_btn));
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.repeating_all_day:
                if (checked) {
                    for (int i = 0; i < buttons.size(); i++) {
                        setBtnTrue(buttons.get(i));
                    }
                }
                else {
                    for (int i = 0; i < buttons.size(); i++) {
                        setBtnFalse(buttons.get(i));
                    }
                }
                break;
        }
    }
}