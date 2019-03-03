package com.luis_santiago.musicmachine;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;


public class DownloadIntentService extends IntentService {

    public DownloadIntentService(String name) {
        super(name);
        setIntentRedelivery(true);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String song;
        if (intent != null) {
            song = intent.getStringExtra(Constants.KEY_SONG);
            downloadSong(song);
        }
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

        Log.d("DOWNLOAD SERVICE" , "Song download" + song);
    }

}
