package com.android.alarmy_test2.Services;

import static com.android.alarmy_test2.AppCore.AlarmApplication.CHANNEL_ID;
import static com.android.alarmy_test2.Receiver.AlarmBroadcastReceiver.LABEL;
import static com.android.alarmy_test2.Receiver.AlarmBroadcastReceiver.VIBRATE;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Vibrator;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.android.alarmy_test2.Activities.RingActivity;
import com.android.alarmy_test2.R;

public class AlarmService extends Service {
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    @Override
    public void onCreate() {
        super.onCreate();

        mediaPlayer = MediaPlayer.create(this, R.raw.ringtone);
        mediaPlayer.setLooping(true);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationIntent = new Intent(this, RingActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        String alarmTitle = String.format("%s Alarm", intent.getStringExtra(LABEL));

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(alarmTitle)
                .setContentText("Ring Ring .. Ring Ring")
                .setSmallIcon(R.drawable.ic_baseline_alarm_24)
                .setContentIntent(pendingIntent)
                .build();

        mediaPlayer.start();

        boolean isVibrate = intent.getBooleanExtra(VIBRATE, false);
        if(isVibrate) {
            long[] pattern = { 0, 100, 1000 };
            vibrator.vibrate(pattern, 0);
        }

        startForeground(1, notification);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mediaPlayer.stop();
        vibrator.cancel();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
