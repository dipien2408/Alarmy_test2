package com.android.alarmy_test2.Services;

import static com.android.alarmy_test2.AppCore.AlarmApplication.CHANNEL_ID;
import static com.android.alarmy_test2.Receiver.AlarmBroadcastReceiver.ALARM_TONE;
import static com.android.alarmy_test2.Receiver.AlarmBroadcastReceiver.LABEL;
import static com.android.alarmy_test2.Receiver.AlarmBroadcastReceiver.VIBRATE;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.android.alarmy_test2.Activities.RingActivity;
import com.android.alarmy_test2.R;

public class AlarmService extends Service {
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private Vibrator vibrator;
    Uri ringtone;
    String tone;
    @Override
    public void onCreate() {
        super.onCreate();

        mediaPlayer.setLooping(true);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        ringtone = RingtoneManager.getActualDefaultRingtoneUri(this.getBaseContext(), RingtoneManager.TYPE_ALARM);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationIntent = new Intent(this, RingActivity.class);
        startActivity(notificationIntent);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        String alarmTitle = String.valueOf(R.string.alarm);

        tone = intent.getStringExtra(ALARM_TONE);
        if (tone != null) {
            alarmTitle = intent.getStringExtra(LABEL);
            try {
                mediaPlayer.setDataSource(this.getBaseContext(), Uri.parse(tone));
                mediaPlayer.prepareAsync();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                mediaPlayer.setDataSource(this.getBaseContext(), ringtone);
                mediaPlayer.prepareAsync();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(alarmTitle)
                .setContentText("Alarm")
                .setSmallIcon(R.drawable.ic_baseline_alarm_24)
                .setContentIntent(pendingIntent)
                .build();

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
                Log.d("Music", "started");
            }
        });


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
