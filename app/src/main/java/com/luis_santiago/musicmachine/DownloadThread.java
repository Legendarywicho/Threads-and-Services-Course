package com.luis_santiago.musicmachine;

import android.os.Looper;
import android.util.Log;

/**
 * Created by Luis Santiago on 02/04/18.
 */

public class DownloadThread extends Thread {

    private final String TAG = DownloadThread.class.getSimpleName();
    public DownloadHandler mHandler;

    @Override
    public void run() {
        Looper.prepare();
        mHandler = new DownloadHandler();
        Looper.loop();
    }
}
