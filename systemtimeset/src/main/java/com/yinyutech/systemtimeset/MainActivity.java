package com.yinyutech.systemtimeset;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.yinyutech.systemtimeset.service.GetNetTimeToSetSystemTimeService;

public class MainActivity extends Activity {

    public static Handler mReceiveTimeSetHandler;
    private GetNetTimeToSetSystemTimeService.TimeToSetBinder mTimeToSetBinder;
    private Context mContext;
    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            mTimeToSetBinder = (GetNetTimeToSetSystemTimeService.TimeToSetBinder)iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        //bind service
        Intent bindServiceIntent = new Intent(this, GetNetTimeToSetSystemTimeService.class);
        boolean isBind = bindService(bindServiceIntent, mServiceConnection, this.BIND_AUTO_CREATE);
        mReceiveTimeSetHandler = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch (msg.what){
                    case 0:
                        mTimeToSetBinder.requireSetSystemTime(mContext);
                        break;
                    default:

                        break;
                }
            }
        };

    }

    @Override
    protected void onResume() {
        super.onResume();
        //启动service之后退至后台
        boolean movetoback = moveTaskToBack(true);
    }
}

