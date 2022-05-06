package com.android.alarmy_test2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.alarmy_test2.Fragments.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AlarmList extends AppCompatActivity {

    public static final int ADD_ALARM_REQUEST = 1;
    public static final int EDIT_ALARM_REQUEST = 2;

    private AlarmViewModel alarmViewModel;

    private FloatingActionButton mMainAddFab, mAddNormalFab, mAddFastFab;
    private TextView mAddNormalText, mAddFastText;
    private Animation mFabOpenAnim, mFabCloseAnim;
    //private Button mDropdownBtn;
    private CardView mAlarmCard;
    private boolean isOpen;
    private Boolean[] repeating = {false, false, false, false, true, true, false};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        mMainAddFab = findViewById(R.id.main_add_fab);
        mAddNormalFab = findViewById(R.id.add_normal_fab);
        mAddFastFab = findViewById(R.id.add_fast_fab);

        mAddNormalText = findViewById(R.id.add_normal_text);
        mAddFastText = findViewById(R.id.add_fast_text);

        mFabOpenAnim = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        mFabCloseAnim = AnimationUtils.loadAnimation(this, R.anim.fab_close);

        //mDropdownBtn = findViewById(R.id.alarm_card_option);
        mAlarmCard = findViewById(R.id.alarm_card);
        isOpen = false;

        mMainAddFab.setOnClickListener(v -> {
            if(isOpen){
                mMainAddFab.setImageResource(R.drawable.ic_baseline_add_24);
                mAddNormalFab.setAnimation(mFabCloseAnim);
                mAddFastFab.setAnimation(mFabCloseAnim);

                mAddNormalText.setVisibility(View.INVISIBLE);
                mAddFastText.setVisibility(View.INVISIBLE);

                isOpen = false;
            } else {
                mMainAddFab.setImageResource(R.drawable.ic_baseline_close_24);
                mAddNormalFab.setAnimation(mFabOpenAnim);
                mAddFastFab.setAnimation(mFabOpenAnim);

                mAddNormalText.setVisibility(View.VISIBLE);
                mAddFastText.setVisibility(View.VISIBLE);

                isOpen = true;
            }

        });

//        mDropdownBtn.setOnClickListener(view -> {
//            PopupMenu popup = new PopupMenu(getApplicationContext(), this.mAlarmCard);
//            popup.inflate(R.menu.alarm_cardview_dropdown);
//            popup.show();
//        });

        mAddFastFab.setOnClickListener(view -> {
            final Dialog dialog = new Dialog(getApplicationContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.add_fast_alarm);

            Window window = dialog.getWindow();
            if(window == null) {
                return;
            }

            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            WindowManager.LayoutParams windowAttributes = window.getAttributes();
            windowAttributes.gravity = Gravity.CENTER;
            window.setAttributes(windowAttributes);

            dialog.setCancelable(true);

            dialog.show();
        });

        RecyclerView recyclerView = findViewById(R.id.list_item_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final AlarmAdapter adapter = new AlarmAdapter();
        recyclerView.setAdapter(adapter);

        alarmViewModel = new ViewModelProvider(this).get(AlarmViewModel.class);
        alarmViewModel.getAllAlarms().observe(this, alarms -> adapter.setAlarms(alarms));

        adapter.setOnItemClickListener(alarm -> {
            Intent intent = new Intent(AlarmList.this, AddAlarm.class);
            intent.putExtra(AddAlarm.EXTRA_ID, alarm.getId());
            intent.putExtra(AddAlarm.EXTRA_TIMEHOUR, alarm.getTimeHour());
            intent.putExtra(AddAlarm.EXTRA_TIMEMINUTE, alarm.getTimeMinute());
            intent.putExtra(AddAlarm.EXTRA_TONE, alarm.getAlarmTone());
            intent.putExtra(AddAlarm.EXTRA_VIBRATE, alarm.isVibrate());
            intent.putExtra(AddAlarm.EXTRA_LABEL, alarm.getLabel());
            intent.putExtra(AddAlarm.EXTRA_SNOOZED, alarm.isSnoozed());
            startActivityForResult(intent, EDIT_ALARM_REQUEST);
        });

        mAddNormalFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AlarmList.this, AddAlarm.class);
                startActivityForResult(intent, ADD_ALARM_REQUEST);
            }
        });
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_ALARM_REQUEST && resultCode == RESULT_OK) {
            int timeHour = data.getIntExtra(AddAlarm.EXTRA_TIMEHOUR, 1);
            int timeMinute = data.getIntExtra(AddAlarm.EXTRA_TIMEMINUTE,1);
            String tone = data.getStringExtra(AddAlarm.EXTRA_TONE);
            boolean enabled = data.getBooleanExtra(AddAlarm.EXTRA_ENABLED,false);
            boolean vibrate = data.getBooleanExtra(AddAlarm.EXTRA_VIBRATE,false);
            String label = data.getStringExtra(AddAlarm.EXTRA_LABEL);
            boolean snoozed = data.getBooleanExtra(AddAlarm.EXTRA_SNOOZED,false);

            Alarm alarm = new Alarm(timeHour, timeMinute, repeating, tone, enabled, vibrate, label, snoozed, timeHour, timeMinute, timeHour);
            alarmViewModel.insert(alarm);

            Toast.makeText(this, "Alarm saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_ALARM_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddAlarm.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Alarm can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            int timeHour = data.getIntExtra(AddAlarm.EXTRA_TIMEHOUR, 1);
            int timeMinute = data.getIntExtra(AddAlarm.EXTRA_TIMEMINUTE,1);
            String tone = data.getStringExtra(AddAlarm.EXTRA_TONE);
            boolean enabled = data.getBooleanExtra(AddAlarm.EXTRA_ENABLED,false);
            boolean vibrate = data.getBooleanExtra(AddAlarm.EXTRA_VIBRATE,false);
            String label = data.getStringExtra(AddAlarm.EXTRA_LABEL);
            boolean snoozed = data.getBooleanExtra(AddAlarm.EXTRA_SNOOZED,false);

            Alarm alarm = new Alarm(timeHour, timeMinute, repeating, tone, enabled, vibrate, label, snoozed, timeHour, timeMinute, timeHour);
            alarm.setId(id);
            alarmViewModel.update(alarm);

            Toast.makeText(this, "Alarm updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Alarm not saved", Toast.LENGTH_SHORT).show();
        }
    }
}