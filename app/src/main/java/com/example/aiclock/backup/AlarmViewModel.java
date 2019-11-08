//package com.example.aiclock.backup;
//
//import android.app.Application;
//
//import androidx.annotation.NonNull;
//import androidx.lifecycle.AndroidViewModel;
//import androidx.lifecycle.LiveData;
//
//import java.util.List;
//
//public class AlarmViewModel extends AndroidViewModel {
//
//    private AlarmRepository mRepository;
//    private LiveData<List<Alarm>> mAllAlarms;
//
//    public AlarmViewModel(@NonNull Application application) {
//        super(application);
//        mRepository = new AlarmRepository(application);
//        mAllAlarms = mRepository.getAllAlarms();
//    }
//
//    LiveData<List<Alarm>> getAllWords() { return mAllAlarms; }
//
//    public void insert(Alarm alarm) { mRepository.insert(alarm); }
//
//
//}
