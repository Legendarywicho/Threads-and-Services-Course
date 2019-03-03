package com.luis_santiago.musicmachine;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Luis Santiago on 3/2/19.
 */
public class PlayerService extends Service {

    private MediaPlayer mPlayer;
    private String TAG = PlayerService.class.getSimpleName();
    private IBinder mBinder = new LocalBinder();


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
        return mBinder;
    }


    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "OnUnBind");
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mPlayer.setOnCompletionListener(mediaPlayer -> {
            stopSelf();
        });
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "OnDestroy");
        mPlayer.release();
    }


    public class LocalBinder extends Binder{
        public PlayerService getService(){
            return PlayerService.this;
        }
    }

    public void play() {
        mPlayer.start();
    }

    public void pause() {
        mPlayer.pause();
    }

    public boolean isPlaying (){
        return mPlayer.isPlaying();
    }
}
