package com.ensc40.manitosecurity;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;


/**
 * Created by Collin on 12/4/14.
 */
public class BTChatService extends Service {
    private BTChat mChatService = null;
    private final IBinder mBinder = new LocalBinder();


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(mChatService == null){
            mChatService = new BTChat(getApplicationContext(), mHandler);
        }
        return Service.START_STICKY;

    }

    @Override
    public IBinder onBind(Intent intent) {
        mHandler = ((ManitoApplication) getApplication()).getHandler();
        return mBinder;
    }

    public class LocalBinder extends Binder {
        BTChatService getService() {
            return BTChatService.this;
        }
    }

    /**
     * The Handler that gets information back from the BTChat
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BTChat.STATE_CONNECTED:
                            break;
                        case BTChat.STATE_CONNECTING:
                            break;
                        case BTChat.STATE_LISTEN:
                        case BTChat.STATE_NONE:
                            break;
                    }
                    break;
                case Constants.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    break;
                case Constants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    break;
                case Constants.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    break;
                case Constants.MESSAGE_TOAST:
                    if (null != this) {
                        Toast.makeText(getApplicationContext(), msg.getData().getString(Constants.TOAST),
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
}
