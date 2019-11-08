//package com.example.aiclock.backup;
//
//import androidx.lifecycle.LiveData;
//import androidx.room.Dao;
//import androidx.room.Insert;
//import androidx.room.Query;
//
//import java.util.List;
//@Dao
//public interface AlarmDao {
//    @Insert
//    void insert(Alarm alarm);
//
//    @Query("Delete fROM alarm_table")
//    void deleteAll();
//
//    @Query("Select * FROM  alarm_table ORDER BY id ASC")
//     LiveData<List<Alarm>> getAllAlarms();
//}
