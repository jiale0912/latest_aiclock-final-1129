package com.example.aiclock.alarmmanager;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;

import com.example.aiclock.R;
import com.example.aiclock.ai.imagedisplay;

import java.io.IOException;


public class ClockAlarmActivity extends Activity {
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;
    private String mysound;
    private Uri soundtrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_alarm);
        String message = this.getIntent().getStringExtra("msg");
        int flag = this.getIntent().getIntExtra("flag", 0);
        mysound = this.getIntent().getStringExtra("soundtrack");
        soundtrack = Uri.parse(mysound);
        showDialogInBroadcastReceiver(message, flag, soundtrack);


    }

    private void showDialogInBroadcastReceiver(String message, final int flag, Uri soundtrack) {

        if (flag == 1 || flag == 2) {
            try {
                mediaPlayer = new MediaPlayer();

               mediaPlayer.setDataSource(getApplicationContext(), soundtrack);
                mediaPlayer.setLooping(true);
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("Alarm error","media player cant run");
            }
        }
        //数组参数意义：第一个参数为等待指定时间后开始震动，震动时间为第二个参数。后边的参数依次为等待震动和震动的时间
        //第二个参数为重复次数，-1为不重复，0为一直震动
        if (flag == 0 || flag == 2) {
            vibrator = (Vibrator) this.getSystemService(Service.VIBRATOR_SERVICE);
            vibrator.vibrate(new long[]{100, 10, 100, 600}, 0);
        }

        final SimpleDialog dialog = new SimpleDialog(this, R.style.Theme_dialog);
        dialog.show();
        dialog.setTitle(soundtrack.toString());
        dialog.setMessage(message);
        dialog.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.bt_confirm == v || dialog.bt_cancel == v) {

                    if (flag == 1 || flag == 2) {
                      mediaPlayer.stop();
                       mediaPlayer.release();
                    }
                    else if (flag == 0 || flag == 2) {

                        vibrator.cancel();
                    }
                    Intent intent = new Intent(getApplicationContext(), imagedisplay.class);
                    startActivity(intent);
                    dialog.dismiss();
                    finish();
                }
            }
        });


    }


}
