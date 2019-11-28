package com.example.aiclock.alarmmanager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.example.aiclock.R;

import java.io.IOException;

public class MusicService extends Service implements MediaPlayer.OnErrorListener {

    private final IBinder mBinder = new ServiceBinder();
    MediaPlayer mPlayer;
    Uri mysong;
    private int length = 0;

    public MusicService() {
    }

    public class ServiceBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public IBinder onBind(Intent arg0) {

        return mBinder;
    }

    @Override
    public void onCreate() {

        super.onCreate();
//       mPlayer = MediaPlayer.create(this,R.raw.loud_alarm_sound);
//        mPlayer.setOnErrorListener(this);



//        mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
//
//            public boolean onError(MediaPlayer mp, int what, int
//                    extra) {
//
//                onError(mPlayer, what, extra);
//                return true;
//            }
//        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

       String temp = intent.getStringExtra("soundtrack");
       int off = intent.getIntExtra("off",0);
        mysong = Uri.parse(temp);
        if(mPlayer == null) {
            try {
                mPlayer = new MediaPlayer();

                mPlayer.setDataSource(getApplicationContext(), mysong);
                mPlayer.setLooping(true);
                mPlayer.prepare();
                mPlayer.start();

            } catch (Exception e) {
                Log.d("Error", "Mediaplayer no working");
            }
        }
        else if(off == 1)
        {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
        else
        {
            mPlayer.start();
        }

//        if (mPlayer != null) {
//            mPlayer.start();
//        }
        return START_NOT_STICKY;
    }



    public void pauseMusic() {
        if (mPlayer != null) {
            if (mPlayer.isPlaying()) {
                mPlayer.pause();
                length = mPlayer.getCurrentPosition();
            }
        }
    }

    public void resumeMusic() {
        if (mPlayer != null) {
            if (!mPlayer.isPlaying()) {
                mPlayer.seekTo(length);
                mPlayer.start();
            }
        }
    }

    public void startMusic() {


        //        mPlayer.setOnErrorListener(this);

        if (mPlayer != null) {
            mPlayer.setLooping(true);
            mPlayer.setVolume(50, 50);
            mPlayer.start();
        }

    }

    public void stopMusic() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            try {
                mPlayer.stop();
                mPlayer.release();
            } finally {
                mPlayer = null;
            }
        }
    }

    public boolean onError(MediaPlayer mp, int what, int extra) {

        Toast.makeText(this, "Music player failed", Toast.LENGTH_SHORT).show();
        if (mPlayer != null) {
            try {
                mPlayer.stop();
                mPlayer.release();
            } finally {
                mPlayer = null;
            }
        }
        return false;
    }
}
