package com.android.alarmy_test2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmHolder> {
    private List<Alarm> alarms = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public AlarmHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alarm_card_view, parent, false);
        return new AlarmHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmHolder holder, int position) {
        int count = 0;
        Alarm currentAlarm = alarms.get(position);
        holder.switchMaterialTime.setText(new StringBuilder().append(currentAlarm.getTimeHour()).append(" : ").append(currentAlarm.getTimeMinute()).toString());
        holder.switchMaterialTime.setOnClickListener(view -> {
            currentAlarm.setIsEnabled(!currentAlarm.isEnabled());
        });
        holder.menuOption.setOnClickListener(view -> {
            PopupMenu popup = new PopupMenu(view.getContext(), holder.itemView);
            popup.inflate(R.menu.alarm_cardview_dropdown);
            popup.show();
        });
        Boolean[] days = currentAlarm.getRepeatingDays();
        for (int i = 0; i < 7; i++) {
            if(days[i]) count++;
        }
        holder.textViewRepeat.setText(new StringBuilder().append("Days").append(count).toString());
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

    public Alarm getAlarmAt(int position) {
        return alarms.get(position);
    }

    class AlarmHolder extends RecyclerView.ViewHolder {
        private SwitchMaterial switchMaterialTime;
        private TextView textViewRepeat;
        private TextView textViewLabel;
        private MaterialButton menuOption;

        public AlarmHolder(View itemView) {
            super(itemView);
            switchMaterialTime = itemView.findViewById(R.id.alarm_switch);
            textViewRepeat = itemView.findViewById(R.id.alarm_repeat);
            textViewLabel = itemView.findViewById(R.id.alarm_label);
            menuOption = itemView.findViewById(R.id.alarm_card_option);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(alarms.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Alarm alarm);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
