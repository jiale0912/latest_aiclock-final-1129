package com.example.aiclock;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private SwipeMenuListView myList;
    private ArrayList<Alarm> list_array;
    private AlarmListAdapter adapter;
    private TextView alarminfo;
    private Switch mySwitch;



    @Override
    protected void onResume() {
        super.onResume();
        viewData();
        adapter = new AlarmListAdapter(this,R.layout.alarm_card,list_array);

        myList.setAdapter(adapter);
        if(myList.getCount()>0)
        {
            alarminfo.setText("Alarm on");
        }
        else
        {
            alarminfo.setText("No Alarm On");
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final myDbAdapter myDB = new myDbAdapter(this);
        alarminfo = (TextView) findViewById(R.id.alarmstatus);
        list_array = new ArrayList<>();
        mySwitch = (Switch) findViewById(R.id.alarm_switch);
        myList = findViewById(R.id.alarm_list);
        viewData();

        adapter = new AlarmListAdapter(this,R.layout.alarm_card,list_array);

        myList.setAdapter(adapter);

        if(myList.getCount()>0)
        {
            alarminfo.setText("Alarm on");
        }
        else
        {
            alarminfo.setText("No Alarm On");
        }
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           Intent intent = new Intent(MainActivity.this,SetAlarm.class);
           startActivityForResult(intent,0);
            }
        });


        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(200);
                // set item title
                openItem.setIcon(R.drawable.ic_add_black_24dp);
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

// set creator

        myList.setMenuCreator(creator);
       myList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
           @Override
           public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
               Alarm alarm = list_array.get(position);

               switch (index)
               {
                   case 0:
                       Toast.makeText(MainActivity.this, "add", Toast.LENGTH_SHORT).show();
                       break;
                   case 1:
                        myDB.delete(alarm.getId());
                       Toast.makeText(MainActivity.this, "delete", Toast.LENGTH_SHORT).show();
                       onResume();
                       viewData();
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
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
