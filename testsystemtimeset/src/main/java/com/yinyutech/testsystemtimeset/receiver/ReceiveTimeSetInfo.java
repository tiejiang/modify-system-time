package com.yinyutech.testsystemtimeset.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by yinyu-tiejiang on 17-12-18.
 */

public class ReceiveTimeSetInfo extends BroadcastReceiver {

    public static final String ACTION_SYSTEM_TIM_ALREADY_SET = "ACTION_SYSTEM_TIM_ALREADY_SET";

    @Override
    public void onReceive(Context context, Intent intent) {
        String receStr = intent.getStringExtra("MESSAGE");
        Log.d("TIEJIANG", "ReceiveTimeSetInfo---onReceive"+" receStr= "+receStr);
        if (intent.getAction().equals(ACTION_SYSTEM_TIM_ALREADY_SET)){

            Log.d("TIEJIANG", "ReceiveTimeSetInfo---onReceive"+" receiveString time set");
        }
    }
}
