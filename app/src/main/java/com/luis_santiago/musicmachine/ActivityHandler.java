package com.luis_santiago.musicmachine;


import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

/**
 * Created by Luis Santiago on 3/3/19.
 */
public class ActivityHandler extends Handler {

    private MainActivity mainActivity;

    public ActivityHandler(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void handleMessage(Message msg) {
        if (msg.arg1 == 0) {
            //Music is not playing
            //Play music
            if (msg.arg2 == 1) {
                mainActivity.changePlayButtonText("Play");
            } else {
                Message message = Message.obtain();
                message.arg1 = 0;
                try {
                    msg.replyTo.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                //Change play button to say pause
                mainActivity.changePlayButtonText("Pause");
            }
        } else if (msg.arg1 == 1) {
            if (msg.arg2 == 1) {
                mainActivity.changePlayButtonText("Pause");
            } else {
                //Music is playing
                //Pause music
                //Change play button to say play
                Message message = Message.obtain();
                message.arg1 = 1;
                try {
                    msg.replyTo.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                mainActivity.changePlayButtonText("Play");
            }
        }
    }


}
