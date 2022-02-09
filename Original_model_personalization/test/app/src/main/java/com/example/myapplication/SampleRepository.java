package com.example.myapplication;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.myapplication.databse.Sample;
import com.example.myapplication.databse.SampleDAO;
import com.example.myapplication.databse.SampleDatabase;

public class SampleRepository {
    //-----싱글톤 유지를 위한 코드-----
    private static Context context;
    public static SampleDatabase database;
    public static SampleDAO dao;

    public SampleRepository(Context context) {
        this.context = context;
    }

    public static void initialize (Context context) {
        database = Room.databaseBuilder(
                context,
                SampleDatabase.class,
                "sampleDatabse")
                .allowMainThreadQueries()
                .build();

        dao = database.sampleDAO();
    }

    public static SampleDAO getInstance() {
        return dao;
    }

    //-----------------------------
}
