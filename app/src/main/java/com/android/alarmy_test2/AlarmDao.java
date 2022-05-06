package com.android.alarmy_test2;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AlarmDao {

    @Insert
    void insert(Alarm alarm);

    @Update
    void update(Alarm alarm);

    @Delete
    void delete(Alarm alarm);

    @Query("SELECT * FROM alarm_table ORDER BY timeHour ASC")
    LiveData<List<Alarm>> getAllAlarms();
}
