package com.yinyutech.systemtimeset.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.yinyutech.systemtimeset.utils.Constants;
import static com.yinyutech.systemtimeset.MainActivity.mReceiveTimeSetHandler;
/**
 * Created by yinyu-tiejiang on 17-12-18.
 */

public class GetNetTimeToSet extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String receiveString = intent.getStringExtra("MESSAGE");
        if (intent.getAction().equals(Constants.ACTION_GET_NET_TIME_TO_SET_SYSTEM_TIM)){
            Log.d("TIEJIANG", "GetNetTimeToSet---onReceive"+" receiveString= "+receiveString);
            //note code to get and set system time
            mReceiveTimeSetHandler.obtainMessage(0, "require set system time").sendToTarget();
        }
    }
}
