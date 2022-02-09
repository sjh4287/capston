package com.example.myapplication.databse;

import android.util.Log;

import androidx.room.TypeConverter;

import com.example.myapplication.TrainingSample;

import java.util.Arrays;

public class SampleConverter {
    @TypeConverter
    public String fromBottleneck(float[] bottleneck) {
        return Arrays.toString(bottleneck);
    }

    @TypeConverter
    public float[] toBottleneck(String bottleneck) {
        String[] temp = bottleneck.replace("[", "")
                .replace("]", "")
                .replace(" ", "")
                .split("[,/]");

        float[] floatArray = new float[temp.length];
        for (int i = 0; i < temp.length; i ++) {
            floatArray[i] = Float.parseFloat(temp[i]);
        }
        return floatArray;
    }
}
