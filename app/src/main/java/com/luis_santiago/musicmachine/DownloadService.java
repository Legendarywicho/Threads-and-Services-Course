package com.luis_santiago.musicmachine;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Luis Santiago on 9/10/18.
 */
public class DownloadService extends Service {

    private static final String TAG = DownloadService.class.getSimpleName();
    private DownloadHandler mHandler;

    @Override
    public void onCreate() {
        DownloadThread thread = new DownloadThread();
        thread.setName("Download name");
        thread .start();
        while (thread.mHandler == null) {}
        mHandler = thread.mHandler;
        mHandler.setService(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String song = intent.getStringExtra(Constants.KEY_SONG);
        Message message = Message.obtain();
        message.obj = song;
        message.arg1 = startId;
        mHandler.sendMessage(message);
        return Service.START_REDELIVER_INTENT;
    }

}
