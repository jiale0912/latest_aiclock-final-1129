package com.example.aiclock.alarmmanager;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aiclock.R;

/**
 * Created by LENOVO on 12/12/2016.
 */

public class Splash extends Activity {

    TextView tv;
    private String mysong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                + WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
                + WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
                + WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        setContentView(R.layout.splash);
        mysong = this.getIntent().getStringExtra("soundtrack");
        tv = (TextView) findViewById(R.id.textView);
        final Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.fadeout);
doBindService();
        Intent music = new Intent();
        music.setClass(this,MusicService.class);
        music.putExtra("soundtrack",mysong);
        startService(music);
        tv.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                finish();
                Intent intent = new Intent(Splash.this, MenuOptions.class);
               intent.putExtra("soundtrack",mysong);
                startActivity(intent);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

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
