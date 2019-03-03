package com.luis_santiago.musicmachine;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mDownloadButton;

    private Button mPlayButton;

    private PlayerService mPlayerService;

    private boolean mBound = false;

    private final String TAG = MainActivity.class.getSimpleName();

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mBound = true;
            PlayerService.LocalBinder localBinder = (PlayerService.LocalBinder) iBinder;
            mPlayerService = localBinder.getService();
            if(mPlayerService.isPlaying()){
                mPlayButton.setText("Pause");
            }
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
                startService(intent);

            }
        });


        mPlayButton.setOnClickListener(view -> {
            if(mBound){
                if(mPlayerService.isPlaying()){
                    mPlayerService.pause();
                    mPlayButton.setText("Play");
                }else{
                    Intent intent = new Intent(MainActivity.this , PlayerService.class);
                    startService(intent);
                    mPlayerService.play();
                    mPlayButton.setText("Pause");
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

    @Override
    protected void onStop() {
        super.onStop();
        if(mBound){
            unbindService(mServiceConnection);
            mBound = false;
        }
    }
}
