package com.yinyutech.systemtimeset.service;

import android.app.AlarmManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.UserHandle;
import android.util.Log;

import com.yinyutech.systemtimeset.utils.Constants;

import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by yinyu-tiejiang on 17-12-18.
 */

public class GetNetTimeToSetSystemTimeService extends Service {

    public TimeToSetBinder mTimeToSetBinder = new TimeToSetBinder();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {

        return mTimeToSetBinder;
    }

    public class TimeToSetBinder extends Binder{

        private Handler mDataHandler;

        public void requireSetSystemTime(final Context context){

            new Thread(new WebsiteDataDeal()).start();
            mDataHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);

                    switch (msg.what){
                        case 1:
                            String mTimeValue = (String) msg.obj;
                            Log.d("TIEJIANG", "GetNetTimeToSetSystemTimeService.TimeToSetBinder---requireSetSystemTime-mTimeValue= "
                                    +mTimeValue
                                    +" mTimeValue length= "+mTimeValue.length());
                            if (mTimeValue.length() == 19){
                                String[] temp = mTimeValue.split("\\s+");
                                String[] mYearMonthData = temp[0].split("-");
                                int year = Integer.valueOf(mYearMonthData[0]);
                                int month = Integer.valueOf(mYearMonthData[1]);
                                int day = Integer.valueOf(mYearMonthData[2]);
                                Log.d("TIEJIANG", "GetNetTimeToSetSystemTimeService.TimeToSetBinder---requireSetSystemTime-"
                                    +" year= "+year
                                    +" month= "+month
                                    +" day= "+day);
                                String[] mHourMinuteSecond = temp[1].split(":");
                                int hour = Integer.valueOf(mHourMinuteSecond[0]);
                                int minute = Integer.valueOf(mHourMinuteSecond[1]);
                                int second = Integer.valueOf(mHourMinuteSecond[2]);
                                Log.d("TIEJIANG", "GetNetTimeToSetSystemTimeService.TimeToSetBinder---requireSetSystemTime-"
                                        +" hour= "+hour
                                        +" minute= "+minute
                                        +" second= "+second);
                                setSysDate(context, year, month-1, day);
                                setSysTime(context, hour, minute);
//                                setSysDate(context, year, 10, 12);
//                                setSysTime(context, hour, 22);
                                sendBroadcastToXiaoLeRobot(context, "already set");
                            }
                            break;
                        default:

                            break;
                    }
                }
            };
        }

        /**
         * function: send broadcast to XiaoLeRobot that system time already set.
         *
         * */
        public void sendBroadcastToXiaoLeRobot(Context context, String message){
            //发送广播
            String broadcastIntent = Constants.ACTION_SYSTEM_TIM_ALREADY_SET;
            Intent intent = new Intent(broadcastIntent);
            intent.putExtra("MESSAGE", message);
            context.sendBroadcastAsUser(intent, UserHandle.ALL);
        }

        class WebsiteDataDeal implements Runnable{

            @Override
            public void run() {
                String data = getNetWorkTime();
//            String websiteData = String.valueOf(data);
                mDataHandler.obtainMessage(1, data).sendToTarget();
            }
        }

        public String getNetWorkTime(){
            String format = "--";
            URL url = null;//取得资源对象
            long ld = 0;
            try {
                url = new URL("http://www.baidu.com");
                URLConnection uc = url.openConnection();//生成连接对象
                uc.connect(); //发出连接
                ld = uc.getDate(); //取得网站日期时间
                Log.d("TIEJIANG", "StartActivity---getNetWorkTime"+" ld= "+ld);
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(ld);
                format = formatter.format(calendar.getTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return format;
        }

        /**
         * 设置系统日期
         * */
        public void setSysDate(Context mContext, int year, int month, int day){
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, day);

            long when = c.getTimeInMillis();

            if(when / 1000 < Integer.MAX_VALUE){
                ((AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE)).setTime(when);
            }
        }

        /**
         * 设置系统时间
         * */
        public void setSysTime(Context mContext, int hour, int minute){
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, hour);
            c.set(Calendar.MINUTE, minute);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);

            long when = c.getTimeInMillis();

            if(when / 1000 < Integer.MAX_VALUE){
                ((AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE)).setTime(when);
            }
        }
    }
}

