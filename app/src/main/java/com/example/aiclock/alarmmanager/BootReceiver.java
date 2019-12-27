package com.example.aiclock.alarmmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.aiclock.Alarm;
import com.example.aiclock.myDbAdapter;

import java.util.ArrayList;

public class BootReceiver extends BroadcastReceiver {
    ArrayList<Alarm> list_array = new ArrayList<>();
    Alarm alarm = new Alarm();
    String myweeks;
    String[] weeks;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            myDbAdapter db = new myDbAdapter(context);
            list_array = db.getOnAlarm(1);
            for (int i = 0; i < list_array.size(); i++) {
                alarm = list_array.get(i);
                myweeks = alarm.getWeek();
                weeks = myweeks.split(",");
                if (myweeks.equals("0")) {
                    AlarmManagerUtil.setAlarm(context, alarm.getFlag(), alarm.getHour(), alarm.getMin(), alarm.getAlarmid(), Integer.parseInt(myweeks), alarm.getTips(), alarm.getSoundorvibrator(), alarm.getSoundtrack());
                    Log.d("alarm setted", "Setted");

                } else if (weeks.length > 0 && !myweeks.equals("0")) {

                    Log.d("alarm checking", myweeks);
                    for (int x = 0; x < weeks.length - 1; x++) {
                        AlarmManagerUtil.setAlarm(context, alarm.getFlag(), alarm.getHour(), alarm.getMin(), alarm.getAlarmid() + x, Integer.parseInt(weeks[x]), alarm.getTips(), alarm.getSoundorvibrator(), alarm.getSoundtrack());
                    }
                    Log.d("alarm setted", "Multiple Setted");

                }
            }


        }
    }
}
