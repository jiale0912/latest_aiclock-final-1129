package com.example.aiclock;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.aiclock.alarmmanager.AlarmManagerUtil;
import com.example.aiclock.alarmmanager.MusicService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.transition.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.DigitalClock;
import android.widget.Switch;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.security.Key;
import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {


    private SwipeMenuListView myList;
    private ArrayList<Alarm> list_array;
    private ArrayList<Alarm> on_alarm_list;
    private AlarmListAdapter adapter;
    private TextView alarminfo;
    private Switch mySwitch;
    private ClockView myClock;
    private DigitalClock myDigitalClock;
    private TextClock myTextClock;
    private int off;
    private String[] weeks;
    private String myweek;
    private SharedPreferences alarm_ID;
    private String sharedPrefFile = "com.example.aiclock";
    int alarmid;

    PowerManager pm;
    PowerManager.WakeLock wl;
    KeyguardManager km;
    KeyguardManager.KeyguardLock kl;
    String locktag = "INFO";
    private Alarm alarm;
    @Override
    protected void onResume() {
        super.onResume();
        viewData();
        adapter = new AlarmListAdapter(this, R.layout.alarm_card, list_array);

        myList.setAdapter(adapter);
        for (int i = 0; i < on_alarm_list.size(); i++) {
            alarm = on_alarm_list.get(i);
            myweek = alarm.getWeek();
            weeks = myweek.split(",");
            if (myweek.equals("0")) {
                AlarmManagerUtil.setAlarm(getApplicationContext(), alarm.getFlag(), alarm.getHour(), alarm.getMin(), alarm.getAlarmid(), Integer.parseInt(myweek), alarm.getTips(), alarm.getSoundorvibrator(), alarm.getSoundtrack());
                Log.d("alarm setted", "Setted");

            } else if (weeks.length > 0 && !myweek.equals("0")) {

                Log.d("alarm checking", myweek);
                for (int x = 0; x < weeks.length - 1; x++) {
                    AlarmManagerUtil.setAlarm(getApplicationContext(), alarm.getFlag(), alarm.getHour(), alarm.getMin(), alarm.getAlarmid() + x, Integer.parseInt(weeks[x]), alarm.getTips(), alarm.getSoundorvibrator(), alarm.getSoundtrack());
                    alarmid++;
                    SharedPreferences.Editor preferencesEditor = alarm_ID.edit();
                    preferencesEditor.putInt("alarmid",alarmid);
                    preferencesEditor.apply();
                }
                Log.d("alarm setted", "Multiple Setted");

            }
        }
//        if(myList.getCount()>0)
//        {
//            alarminfo.setText("Alarm on");
//        }
//        else
//        {
//            alarminfo.setText("No Alarm On");
//        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        final ViewGroup transitionsContainer = (ViewGroup) findViewById(R.id.transitioncontainer);
        myTextClock = transitionsContainer.findViewById(R.id.textclock);
        myClock = transitionsContainer.findViewById(R.id.clockView);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final myDbAdapter myDB = new myDbAdapter(this);
        myTextClock = findViewById(R.id.textclock);
        myClock = findViewById(R.id.clockView);
//        myDigitalClock = findViewById(R.id.digitalClock);
        alarm_ID = getApplicationContext().getSharedPreferences(sharedPrefFile,MODE_PRIVATE);

        alarmid = alarm_ID.getInt("alarmid",0);
        list_array = new ArrayList<>();
        myList = findViewById(R.id.alarm_list);
//        off = this.getIntent().getIntExtra("off",0);
//        if(off == 1)
//        {
//            android.os.Process.killProcess(android.os.Process.myPid());
//                       System.exit(0);
//                       finish();
//        }

        viewData();
        myDbAdapter db = new myDbAdapter(getApplicationContext());
        list_array = db.getOnAlarm(1);

        adapter = new AlarmListAdapter(this, R.layout.alarm_card, list_array);

        myList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

//        if(myList.getCount()>0)
//        {
//            alarminfo.setText("Alarm on");
//        }
//        else
//        {
//            alarminfo.setText("No Alarm On");
//        }
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, SetAlarm.class);
                startActivityForResult(intent, 0);
            }
        });


        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(243, 198,
                        2)));
                // set item width
                openItem.setWidth(200);
                // set item title
                openItem.setIcon(R.drawable.ic_action_edit);
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu

                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(200);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_action_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };


        myClock.setOnClickListener(new View.OnClickListener() {
            boolean visible;

            @Override
            public void onClick(View v) {

                TransitionManager.beginDelayedTransition(transitionsContainer, new Fade().setDuration(300).setStartDelay(100));
                visible = !visible;
//                    myTextClock.setVisibility(visible ? View.VISIBLE: View.GONE);
//                    myClock.setVisibility(!visible ? View.INVISIBLE : View.GONE);
                if (myClock.getVisibility() == View.VISIBLE) {
                    myClock.setVisibility(View.INVISIBLE);
                    myTextClock.setVisibility(View.VISIBLE);


                }
            }
        });

        myTextClock.setOnClickListener(new View.OnClickListener() {
            boolean visible;

            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(transitionsContainer, new Fade().setDuration(300).setStartDelay(100));
                visible = !visible;

                if (myTextClock.getVisibility() == View.VISIBLE) {
                    myTextClock.setVisibility(View.INVISIBLE);
                    myClock.setVisibility(View.VISIBLE);
                }
            }
        });
// set creator

        myList.setMenuCreator(creator);
        myList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                Alarm alarm = list_array.get(position);
                myweek = alarm.getWeek();
                weeks = myweek.split(",");
                switch (index) {
                    case 0:
                        Toast.makeText(MainActivity.this, "Edit", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, EditAlarm.class);
                        intent.putExtra("alarmid", alarm.getId());
                        startActivity(intent);
                        break;
                    case 1:
                        if (alarm.getStatus() == 1 && weeks.length > 0 && !myweek.equals("0")) {
                            Log.d("alarm off", myweek);
                            for (int i = 0; i < weeks.length - 1; i++) {
                                AlarmManagerUtil.cancelAlarm(getApplicationContext(), alarm.getAlarmid() + i);
                                Toast.makeText(MainActivity.this, "Alarm Deleted", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            AlarmManagerUtil.cancelAlarm(MainActivity.this, alarm.getAlarmid());
                            Toast.makeText(MainActivity.this, "Alarm Deleted", Toast.LENGTH_SHORT).show();
                        }
                        myDB.delete(alarm.getId());

                        onResume();
                        viewData();
//                       String value = String.valueOf(alarm.getId());
//                       myDB.delete(value);
//                       onResume();
//                       Toast.makeText(MainActivity.this, value, Toast.LENGTH_SHORT).show();

                        break;
                    default:
                        break;

                }
                return false;
            }
        });


    }


    private void viewData() {
        myDbAdapter db = new myDbAdapter(this);
        list_array = db.getData();
        on_alarm_list = db.getOnAlarm(1);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


}