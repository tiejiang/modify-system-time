package com.yinyutech.testsystemtimeset;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final String ACTION_GET_NET_TIME_TO_SET_SYSTEM_TIM = "ACTION_GET_NET_TIME_TO_SET_SYSTEM_TIM";
    private Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        send = (Button)findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendBroadcastToSystemTimeSetAPP("TEST");
            }
        });
    }

    /**
     * function: send broadcast to systemtimeset APP to test.
     *
     * */
    public void sendBroadcastToSystemTimeSetAPP(String message){
        //发送广播
        String broadcastIntent = ACTION_GET_NET_TIME_TO_SET_SYSTEM_TIM;
        Intent intent = new Intent(broadcastIntent);
        intent.putExtra("MESSAGE", message);
        MainActivity.this.sendBroadcast(intent);
    }
}
