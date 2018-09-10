package com.luis_santiago.musicmachine;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private Button mDownloadButton;
    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDownloadButton = findViewById(R.id.downloadButton);



        mDownloadButton.setOnClickListener(view -> {
            Toast.makeText(this, "Downloading", Toast.LENGTH_SHORT).show();
            for(String song  : Playlist.songs){
                Intent intent = new Intent(MainActivity.this , DownloadService.class);
                intent.putExtra(Constants.KEY_SONG , song);
                startService(intent);
            }
        });

    }

}
