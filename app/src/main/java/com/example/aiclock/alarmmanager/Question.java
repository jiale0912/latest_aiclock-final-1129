package com.example.aiclock.alarmmanager;

import android.app.Activity;
import android.view.KeyEvent;
import android.widget.Toast;

public class Question extends Activity {

    private int ID;
    private String QUESTION;
    private String OPTA;
    private String OPTB;
    private String OPTC;

    private String ANSWER;


    public Question() {
        ID = 0;
        QUESTION = "";
        OPTA = "";
        OPTB = "";
        OPTC = "";

        ANSWER = "";

    }

    public Question(String qUESTION, String oPTA, String oPTB, String oPTC,
                    String aNSWER) {
        QUESTION = qUESTION;
        OPTA = oPTA;
        OPTB = oPTB;
        OPTC = oPTC;

        ANSWER = aNSWER;

    }

    public int getID() {
        return ID;
    }

    public String getQUESTION() {
        return QUESTION;
    }

    public String getOPTA() {
        return OPTA;
    }

    public String getOPTB() {
        return OPTB;
    }

    public String getOPTC() {
        return OPTC;
    }

    public String getANSWER() {
        return ANSWER;
    }

    public void setID(int id) {
        ID = id;
    }

    public void setQUESTION(String qUESTION) {
        QUESTION = qUESTION;
    }

    public void setOPTA(String oPTA) {
        OPTA = oPTA;
    }

    public void setOPTB(String oPTB) {
        OPTB = oPTB;
    }

    public void setOPTC(String oPTC) {
        OPTC = oPTC;
    }

    public void setANSWER(String aNSWER) {
        ANSWER = aNSWER;
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

// @Override
// public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
// getMenuInflater().inflate(R.menu.main, menu);
// return true;
// }
