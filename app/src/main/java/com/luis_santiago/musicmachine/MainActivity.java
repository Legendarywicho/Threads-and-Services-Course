package com.luis_santiago.musicmachine;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mDownloadButton;

    private Button mPlayButton;

    private Messenger mServiceMessenger;

    private boolean mBound = false;

    private final String TAG = MainActivity.class.getSimpleName();

    private Messenger mActivityMessenger = new Messenger(new ActivityHandler(this));


    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mBound = true;
            mServiceMessenger = new Messenger(iBinder);
//            Message message = Message.obtain();
//            message.arg1 = 2;
//            message.arg2 = 1;
//            message.replyTo = mActivityMessenger;
//            try {
//                mServiceMessenger.send(message);
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDownloadButton = findViewById(R.id.downloadButton);
        mPlayButton = findViewById(R.id.play_pause_button);

        mDownloadButton.setOnClickListener(view -> {
            Toast.makeText(this, "Downloading", Toast.LENGTH_SHORT).show();
            for(String song  : Playlist.songs){
                Intent intent = new Intent(MainActivity.this , DownloadIntentService.class);
                intent.putExtra(Constants.KEY_SONG , song);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(intent);
                }else{
                    startService(intent);
                }
            }
        });


        mPlayButton.setOnClickListener(view -> {
            if(mBound){
                Intent intent = new Intent(MainActivity.this , PlayerService.class);
                startService(intent);
                Message message = Message.obtain();
                message.arg1 = 2;
                message.replyTo = mActivityMessenger;
                try {
                    mServiceMessenger.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this , PlayerService.class);
        bindService(intent , mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    public void changePlayButtonText(String text){
        mPlayButton.setText(text);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mBound){
            unbindService(mServiceConnection);
            mBound = false;
        }
    }
}
