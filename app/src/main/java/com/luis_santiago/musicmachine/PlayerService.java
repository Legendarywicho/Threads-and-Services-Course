package com.luis_santiago.musicmachine;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by Luis Santiago on 3/2/19.
 */
public class PlayerService extends Service {

    private MediaPlayer mPlayer;
    private String TAG = PlayerService.class.getSimpleName();
    public Messenger mMessenger = new Messenger(new PlayerHandler(this));

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "OnCreate");
        mPlayer = MediaPlayer.create(this, R.raw.jingle);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "OnBind");
        return mMessenger.getBinder();
    }


    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "OnUnBind");
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        NotificationCompat.Builder notificationBuilder;
        NotificationManager mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel("test", "some name", NotificationManager.IMPORTANCE_DEFAULT);
            mChannel.setDescription("Hello");
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.BLUE);
            if (mNotifyManager != null) {
                mNotifyManager.createNotificationChannel(mChannel);
                //mNotifyManager.notify(0, notification);
            }
            notificationBuilder = new NotificationCompat.Builder(this , mChannel.getId());
        }else{
            notificationBuilder = new NotificationCompat.Builder(this);
        }


        Notification notification = notificationBuilder.build();
        startForeground(15 , notification);
        mPlayer.setOnCompletionListener(mediaPlayer -> {
            stopSelf();
            stopForeground(true);
        });
        return Service.START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "OnDestroy");
        mPlayer.release();
    }


    public void play() {
        mPlayer.start();
    }

    public void pause() {
        mPlayer.pause();
    }

    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }
}
