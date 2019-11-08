//package com.example.aiclock.backup;
//
//import android.content.Context;
//
//import androidx.room.Database;
//import androidx.room.Room;
//import androidx.room.RoomDatabase;
//
//@Database(entities = {Alarm.class},version =1,exportSchema = false)
//public abstract class AlarmRoomDatabase extends RoomDatabase {
//    public abstract AlarmDao alarmDao();
//    private static AlarmRoomDatabase INSTANCE;
//
//    static AlarmRoomDatabase getDatabase(final Context context){
//        if (INSTANCE == null){
//            synchronized (AlarmRoomDatabase.class){
//                if (INSTANCE == null)
//                {
//                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),AlarmRoomDatabase.class,"alarm_database")
//                            .fallbackToDestructiveMigration()
//                            .build();
//                }
//            }
//        }
//        return INSTANCE;
//    }
//}
