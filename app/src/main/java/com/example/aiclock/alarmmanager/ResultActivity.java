package com.example.aiclock.alarmmanager;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aiclock.R;


public class ResultActivity extends Activity {
    private String mysong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                + WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
                + WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
                + WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        setContentView(R.layout.activity_result);
        mysong = this.getIntent().getStringExtra("soundtrack");
        doBindService();
        Intent music = new Intent();
        music.setClass(this,MusicService.class);
        music.putExtra("soundtrack",mysong);
        startService(music);
        TextView textResult = (TextView) findViewById(R.id.textResult);

        Bundle b = getIntent().getExtras();

        int score = b.getInt("score");
        boolean timeUp = b.getBoolean("timeup");

        if(timeUp==true)
        {
            textResult.setText("            TIME UP!!\n Your points are " + " " + score);
        }
        else
        {
            textResult.setText("Wrong answer sorry!! Your points are " + " " + score);
        }



    }

    public void playagain(View o) {

        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra("soundtrack",mysong);
        startActivity(intent);


    }
    //Bind/Unbind music service
    private boolean mIsBound = false;
    private MusicService mServ;
    private ServiceConnection Scon =new ServiceConnection(){

        public void onServiceConnected(ComponentName name, IBinder
                binder) {
            mServ = ((MusicService.ServiceBinder)binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    void doBindService(){
        bindService(new Intent(this, MusicService.class),
                Scon, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService()
    {
        if(mIsBound)
        {
            unbindService(Scon);
            mIsBound = false;
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
        if(mServ != null)
        {
            mServ.resumeMusic();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        doUnbindService();
        Intent music = new Intent();
        music.setClass(this, MusicService.class);
        stopService(music);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            Toast.makeText(this, "You are Not allowed to Change the Volume Now", Toast.LENGTH_SHORT)
                    .show();
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            Toast.makeText(this, "You are Not allowed to Change the Volume Now", Toast.LENGTH_SHORT)
                    .show();
            return true;
        }

        else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            Toast.makeText(this, "You are Not allowed to Change the Volume Now", Toast.LENGTH_SHORT)
                    .show();
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            Toast.makeText(this, "You are Not allowed to Change the Volume Now", Toast.LENGTH_SHORT)
                    .show();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
    @Override
    public void onBackPressed() {
    }
}