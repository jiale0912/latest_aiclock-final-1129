//package com.example.aiclock.backup;
//
//import android.app.Application;
//import android.os.AsyncTask;
//
//import androidx.lifecycle.LiveData;
//
//import java.util.List;
//
//public class AlarmRepository {
//    private AlarmDao mAlarmDao;
//    private LiveData<List<Alarm>> mAllAlarms;
//
//    AlarmRepository(Application application)
//    {
//        AlarmRoomDatabase db = AlarmRoomDatabase.getDatabase(application);
//        mAlarmDao = db.alarmDao();
//        mAllAlarms = mAlarmDao.getAllAlarms();
//    }
//
//    LiveData<List<Alarm>> getAllAlarms(){
//        return mAllAlarms;
//    }
//
//    public void insert (Alarm alarm){
//        new insertAsyncTask(mAlarmDao).execute(alarm);
//    }
//
//    private static class insertAsyncTask extends AsyncTask<Alarm, Void, Void> {
//
//        private AlarmDao mAsyncTaskDao;
//
//        insertAsyncTask(AlarmDao dao) {
//            mAsyncTaskDao = dao;
//        }
//
//        @Override
//        protected Void doInBackground(Alarm... alarms) {
//            mAsyncTaskDao.insert(alarms[0]);
//
//            return null;
//        }
//    }
//    }
