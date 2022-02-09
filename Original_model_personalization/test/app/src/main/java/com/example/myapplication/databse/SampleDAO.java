package com.example.myapplication.databse;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.DeleteTable;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface SampleDAO {
    @Query("Select * FROM sample WHERE sampleID=(:ID)")
    Sample getSample(int ID);

    @Insert
    void addSample(Sample sample);

    @Query("DELETE FROM sample")
    void deleteData();
}
