package com.example.myapplication.databse;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Sample.class}, version = 1)
@TypeConverters(SampleConverter.class)
public abstract class SampleDatabase extends RoomDatabase {
    public abstract SampleDAO sampleDAO();
}
