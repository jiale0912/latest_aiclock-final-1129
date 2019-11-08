package com.example.aiclock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.example.alarmmanagerclock.AlarmManagerUtil;

import java.util.ArrayList;
import java.util.List;


public class AlarmListAdapter extends ArrayAdapter<Alarm> {

    myDbAdapter db = new myDbAdapter(getContext());
    private List<Alarm> alarms = new ArrayList<>();
    private Context context;

    public AlarmListAdapter(Context context,int resource, List<Alarm> objects) {
        super(context, resource, objects);
        this.context = context;
        this.alarms = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v==null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.alarm_card,null);
        }
        final Alarm mAlarm = alarms.get(position);


        TextView time = (TextView) v.findViewById(R.id.alarm_time);
        TextView status = (TextView) v.findViewById(R.id.alarm_setting);
        Switch mySwitch = (Switch) v.findViewById(R.id.alarm_switch);
        if(mAlarm.getMin() < 10)
        {
            time.setText(mAlarm.getHour() + ":0" + mAlarm.getMin());

        }
        else {
            time.setText(mAlarm.getHour() + ":" + mAlarm.getMin());
        }
        if(mAlarm.getTips() != "")
        {
            status.setText(mAlarm.getTips());

        }
        else {
            status.setText("Alarm");
        }
        if(mAlarm.getStatus() == 1)
        {
            mySwitch.setChecked(true);
            AlarmManagerUtil.setAlarm(getContext(), 1, mAlarm.getHour(), mAlarm.getMin(), mAlarm.getId(), mAlarm.getWeek(), mAlarm.getTips(), mAlarm.getSoundorvibrator(),mAlarm.getSoundtrack());
            Toast.makeText(getContext(), "Alarm on", Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "Alarm set", Toast.LENGTH_SHORT).show();
        }
        else
        {
            mySwitch.setChecked(false);
        }
        mySwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAlarm.getStatus() == 1)
                {

                    db.updateStatus(0,mAlarm.getId());
                    Toast.makeText(getContext(), "alarm off", Toast.LENGTH_SHORT).show();
                    AlarmManagerUtil.cancelAlarm(getContext(),mAlarm.getId());
                    mAlarm.setStatus(0);


                }
                else
                {

                    db.updateStatus(1,mAlarm.getId());
                    AlarmManagerUtil.setAlarm(getContext(), mAlarm.getFlag(), mAlarm.getHour(), mAlarm.getMin(), mAlarm.getId(), mAlarm.getWeek(), mAlarm.getTips(), mAlarm.getSoundorvibrator(),mAlarm.getSoundtrack());
                    Toast.makeText(getContext(), "Alarm on", Toast.LENGTH_SHORT).show();
                    mAlarm.setStatus(1);

                }
            }
        });
        return v;
    }
}
