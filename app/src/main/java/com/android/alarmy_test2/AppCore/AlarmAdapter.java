package com.android.alarmy_test2.AppCore;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.ViewModelProvider;
import com.android.alarmy_test2.Database.Alarm;
import com.android.alarmy_test2.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmHolder> {
    private List<Alarm> alarms = new ArrayList<>();
    private OnItemClickListener itemClickListener;
    private OnToggleAlarmListener toggleAlarmListener;
    private AlarmViewModel alarmViewModel;
    public AlarmAdapter(OnToggleAlarmListener toggleAlarmListener, Context context) {
        this.toggleAlarmListener = toggleAlarmListener;
        alarmViewModel = new ViewModelProvider((FragmentActivity) context).get(AlarmViewModel.class);
    }

    @NonNull
    @Override
    public AlarmHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alarm_card_view, parent, false);
        return new AlarmHolder(itemView, toggleAlarmListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmHolder holder, int position) {
        Alarm currentAlarm = alarms.get(position);
        holder.switchMaterialTime.setText(new StringBuilder().append(currentAlarm.getTimeHour()).append(" : ").append(currentAlarm.getTimeMinute()).toString());
        holder.switchMaterialTime.setChecked(currentAlarm.isEnabled());
        holder.switchMaterialTime.setOnCheckedChangeListener((buttonView, isChecked) -> toggleAlarmListener.onToggle(currentAlarm));
        holder.menuOption.setOnClickListener(view -> {
            PopupMenu popup = new PopupMenu(view.getContext(), holder.itemView);
            popup.inflate(R.menu.alarm_cardview_dropdown);
            popup.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.nav_delete) {
                    if(currentAlarm.isEnabled()) {
                        currentAlarm.cancelAlarm(view.getContext());
                    }
                    alarmViewModel.delete(currentAlarm);
                    Toast.makeText(view.getContext(), "Alarm deleted", Toast.LENGTH_SHORT).show();
                }
                return true;
            });
            popup.show();
        });
        if (!currentAlarm.isOneShot()) {
            holder.textViewRepeat.setText(currentAlarm.getRepeatingDaysText());
        } else {
            holder.textViewRepeat.setText("  ");
        }

        holder.textViewLabel.setText(String.valueOf(currentAlarm.getLabel()));
    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }

    public void setAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
        notifyDataSetChanged();
    }

    @Override
    public void onViewRecycled(@NonNull AlarmHolder holder) {
        super.onViewRecycled(holder);
        holder.switchMaterialTime.setOnCheckedChangeListener(null);
    }

    public Alarm getAlarmAt(int position) {
        return alarms.get(position);
    }

    class AlarmHolder extends RecyclerView.ViewHolder {
        private SwitchMaterial switchMaterialTime;
        private TextView textViewRepeat;
        private TextView textViewLabel;
        private MaterialButton menuOption;

        private OnToggleAlarmListener toggleAlarmListener;

        public AlarmHolder(View itemView, OnToggleAlarmListener toggleAlarmListener) {
            super(itemView);
            switchMaterialTime = itemView.findViewById(R.id.alarm_switch);
            textViewRepeat = itemView.findViewById(R.id.alarm_repeat);
            textViewLabel = itemView.findViewById(R.id.alarm_label);
            menuOption = itemView.findViewById(R.id.alarm_card_option);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (itemClickListener != null && position != RecyclerView.NO_POSITION) {
                    itemClickListener.onItemClick(alarms.get(position));
                }
            });

            this.toggleAlarmListener = toggleAlarmListener;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Alarm alarm);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
