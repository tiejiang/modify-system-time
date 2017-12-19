package com.yinyutech.systemtimeset.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yinyutech.systemtimeset.MainActivity;

import static android.content.Intent.ACTION_BOOT_COMPLETED;


/**
 * Created by yinyu-tiejiang on 17-8-8.
 */

public class PowerBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(ACTION_BOOT_COMPLETED)){
//            StartXiaoLeAPP(context);
            // 启动应用首界面
            Intent actIntent = new Intent(context.getApplicationContext(), MainActivity.class);
            actIntent.setAction("android.intent.action.MAIN");
            actIntent.addCategory("android.intent.category.LAUNCHER");
            actIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(actIntent);
        }
    }

    public void StartXiaoLeAPP(final Context context){

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
                // 启动应用首界面
                Intent actIntent = new Intent(context.getApplicationContext(), MainActivity.class);
                actIntent.setAction("android.intent.action.MAIN");
                actIntent.addCategory("android.intent.category.LAUNCHER");
                actIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(actIntent);
//            }
//        }, 7000);
    }
}
