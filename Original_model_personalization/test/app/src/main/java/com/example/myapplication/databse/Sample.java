package com.example.myapplication.databse;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Sample {
    @PrimaryKey
    public int sampleID;
    public float[] bottleneck;
    public float[] label;
}
