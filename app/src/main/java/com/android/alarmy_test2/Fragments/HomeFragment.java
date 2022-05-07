package com.android.alarmy_test2.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.alarmy_test2.AddAlarm;
import com.android.alarmy_test2.Alarm;
import com.android.alarmy_test2.AlarmAdapter;
import com.android.alarmy_test2.AlarmViewModel;
import com.android.alarmy_test2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment {

    public static final int ADD_ALARM_REQUEST = 1;
    public static final int EDIT_ALARM_REQUEST = 2;

    private AlarmViewModel alarmViewModel;

    private FloatingActionButton mMainAddFab, mAddNormalFab, mAddFastFab;
    private TextView mAddNormalText, mAddFastText;
    private Animation mFabOpenAnim, mFabCloseAnim;
    private boolean isOpen;
    private final Boolean[] repeating = {false, false, false, false, true, true, false};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        mMainAddFab = v.findViewById(R.id.main_add_fab);
        mAddNormalFab = v.findViewById(R.id.add_normal_fab);
        mAddFastFab = v.findViewById(R.id.add_fast_fab);

        mAddNormalText = v.findViewById(R.id.add_normal_text);
        mAddFastText = v.findViewById(R.id.add_fast_text);

        mFabOpenAnim = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
        mFabCloseAnim = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close);

        isOpen = false;

        mMainAddFab.setOnClickListener(view -> {
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

        mAddFastFab.setOnClickListener(view -> {
            final Dialog dialog = new Dialog(getContext());
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

        RecyclerView recyclerView = v.findViewById(R.id.list_item_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        final AlarmAdapter adapter = new AlarmAdapter();
        recyclerView.setAdapter(adapter);

        alarmViewModel = new ViewModelProvider(this).get(AlarmViewModel.class);
        alarmViewModel.getAllAlarms().observe(getViewLifecycleOwner(), adapter::setAlarms);

        adapter.setOnItemClickListener(alarm -> {
            Intent intent = new Intent(getActivity(), AddAlarm.class);
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
                Intent intent = new Intent(getActivity(), AddAlarm.class);
                startForResult.launch(intent);
            }
        });
        return v;
    }

    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result != null && result.getData().getIntExtra(AddAlarm.EXTRA_ADD_OR_EDIT, 0) == 0  && result.getResultCode() == Activity.RESULT_OK) {
                int timeHour = result.getData().getIntExtra(AddAlarm.EXTRA_TIMEHOUR, 1);
                int timeMinute = result.getData().getIntExtra(AddAlarm.EXTRA_TIMEMINUTE,1);
                String tone = result.getData().getStringExtra(AddAlarm.EXTRA_TONE);
                boolean enabled = result.getData().getBooleanExtra(AddAlarm.EXTRA_ENABLED,false);
                boolean vibrate = result.getData().getBooleanExtra(AddAlarm.EXTRA_VIBRATE,false);
                String label = result.getData().getStringExtra(AddAlarm.EXTRA_LABEL);
                boolean snoozed = result.getData().getBooleanExtra(AddAlarm.EXTRA_SNOOZED,false);

                Alarm alarm = new Alarm(timeHour, timeMinute, repeating, tone, enabled, vibrate, label, snoozed, timeHour, timeMinute, timeHour);
                alarmViewModel.insert(alarm);

                Toast.makeText(getContext(), "Alarm saved", Toast.LENGTH_SHORT).show();
            } else if (result != null && result.getData().getIntExtra(AddAlarm.EXTRA_ADD_OR_EDIT, 0) == 1  && result.getResultCode() == Activity.RESULT_OK) {
                int id = result.getData().getIntExtra(AddAlarm.EXTRA_ID, -1);

                if (id == -1) {
                    Toast.makeText(getContext(), "Alarm can't be updated", Toast.LENGTH_SHORT).show();
                    return;
                }

                int timeHour = result.getData().getIntExtra(AddAlarm.EXTRA_TIMEHOUR, 1);
                int timeMinute = result.getData().getIntExtra(AddAlarm.EXTRA_TIMEMINUTE,1);
                String tone = result.getData().getStringExtra(AddAlarm.EXTRA_TONE);
                boolean enabled = result.getData().getBooleanExtra(AddAlarm.EXTRA_ENABLED,false);
                boolean vibrate = result.getData().getBooleanExtra(AddAlarm.EXTRA_VIBRATE,false);
                String label = result.getData().getStringExtra(AddAlarm.EXTRA_LABEL);
                boolean snoozed = result.getData().getBooleanExtra(AddAlarm.EXTRA_SNOOZED,false);

                Alarm alarm = new Alarm(timeHour, timeMinute, repeating, tone, enabled, vibrate, label, snoozed, timeHour, timeMinute, timeHour);
                alarm.setId(id);
                alarmViewModel.update(alarm);

                Toast.makeText(getContext(), "Alarm updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Alarm not saved", Toast.LENGTH_SHORT).show();
            }
        }
    });


}
