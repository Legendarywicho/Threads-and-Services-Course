package com.luis_santiago.musicmachine;


import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;

/**
 * Created by Luis Santiago on 3/3/19.
 */
public class PlayerHandler extends Handler {

    private PlayerService mPlayerService;

    public PlayerHandler(PlayerService playerService) {
        this.mPlayerService = playerService;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.arg1){
            case 0 :{ //play
                mPlayerService.play();
                break;
            }

            case 1: { //Pause
                mPlayerService.pause();
                break;
            }

            case 2 : {
                int isPlaying = mPlayerService.isPlaying()? 1 : 0;
                Message message = Message.obtain();
                message.arg1 = isPlaying;
                message.replyTo = mPlayerService.mMessenger;
                try {
                    msg.replyTo.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
