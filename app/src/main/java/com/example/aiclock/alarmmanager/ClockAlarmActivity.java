package com.example.aiclock.alarmmanager;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.KeyguardManager;
import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.aiclock.R;



public class ClockAlarmActivity extends Activity {
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;
    private String mysound;
    private Uri soundtrack2;
    private Ringtone rt;
    private String message;
    private int flag;
    private Uri sound;
    private int id;
    public static final String LT= "lockTag";
    public static final String tempoID = "tempID" ;
    private String sharedPrefFile = "com.example.aiclock_tempoID";
    PowerManager pm;
    PowerManager.WakeLock wl;
    KeyguardManager km;
    KeyguardManager.KeyguardLock kl;
    String locktag = "INFO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                + WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
                + WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
                + WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        Log.i("INFO", "onCreate() in DismissLock");
        pm = (PowerManager) getSystemService(getApplicationContext().POWER_SERVICE);
        km=(KeyguardManager)getSystemService(getApplicationContext().KEYGUARD_SERVICE);
        kl=km.newKeyguardLock(locktag);
        wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP|PowerManager.ON_AFTER_RELEASE, locktag);
        wl.acquire(); //wake up the screen
        kl.disableKeyguard();// dismiss the keyguard
        setContentView(R.layout.activity_clock_alarm);
        doBindService();

        message = this.getIntent().getStringExtra("msg");
        flag = this.getIntent().getIntExtra("flag", 1);
//        int mysound = this.getIntent().getIntExtra("soundtrack",0);
        String ts = this.getIntent().getStringExtra("soundtrack");
        id = this.getIntent().getIntExtra("id",0);
        final SharedPreferences temppref ;
        temppref = this.getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = temppref.edit();
        preferencesEditor.putInt("tempoID",id);
        preferencesEditor.apply();
        sound = Uri.parse(ts);
        soundtrack2 = sound;

        showDialogInBroadcastReceiver(message, flag,sound);


    }

    private void showDialogInBroadcastReceiver(String message,final int flag,Uri soundtrack) {
//        if (flag == 1 || flag == 2) {

//                mediaPlayer = MediaPlayer.create(this, soundtrack);
//                mediaPlayer.setLooping(true);
//                mediaPlayer.start();

        Intent music = new Intent();
        music.setClass(this, MusicService.class);
        music.putExtra("soundtrack",soundtrack.toString());
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            startForegroundService(music);
        }
        else
        startService(music);
//            try {
//                mediaPlayer = new MediaPlayer();
//
//               mediaPlayer.setDataSource(getApplicationContext(), soundtrack);
//                mediaPlayer.setLooping(true);
//                mediaPlayer.prepare();
//                mediaPlayer.start();
//            } catch (IOException e) {
//                e.printStackTrace();
//                Log.d("Alarm error","media player cant run");
//            }
//        }
        //数组参数意义：第一个参数为等待指定时间后开始震动，震动时间为第二个参数。后边的参数依次为等待震动和震动的时间
        //第二个参数为重复次数，-1为不重复，0为一直震动
//        if (flag == 0 || flag == 2) {


//       }

        final SimpleDialog dialog = new SimpleDialog(this, R.style.Theme_dialog);
        dialog.show();
        dialog.setTitle("Alarm");
        dialog.setMessage(message);
        dialog.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.bt_confirm == v || dialog.bt_cancel == v) {


                    Intent intent = new Intent(getApplicationContext(), imagedisplay.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    intent.putExtra("soundtrack",soundtrack2.toString());



                    startActivity(intent);
//                    if (flag == 1 || flag == 2) {
//                        mediaPlayer.stop();
//                        mediaPlayer.release();
//                    }
//                    else if (flag == 0 || flag == 2) {
//
//                        vibrator.cancel();
//                    }
                    dialog.dismiss();
                    finish();
                }
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
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if(wl.isHeld())
        {
            wl.release();
        }
    }

}
