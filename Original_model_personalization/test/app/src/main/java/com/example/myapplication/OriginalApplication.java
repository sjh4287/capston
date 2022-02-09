package com.example.myapplication;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class OriginalApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Context context = getApplicationContext();
        if (context == null) {
            Log.d("이게 범인", context.toString());
        }
        SampleRepository.initialize(this.getApplicationContext());
    }
}
