package com.luis_santiago.musicmachine;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by Luis Santiago on 4/7/18.
 */

public class DownloadHandler extends Handler {

    private static final String TAG = DownloadHandler.class.getSimpleName();
    private DownloadService mService;

    @Override
    public void handleMessage(Message msg) {
        downloadSong(msg.obj.toString());
        mService.stopSelf(msg.arg1);
    }

    private void downloadSong(String song){
        Long endTime =  System.currentTimeMillis() + 10 * 1000;
        while(System.currentTimeMillis() < endTime){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Log.d(TAG , "Song download" + song);
    }

    public void setService(DownloadService downloadService) {
        mService = downloadService;
    }
}
