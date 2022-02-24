/* Copyright 2019 The TensorFlow Authors. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
==============================================================================*/

package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import java.io.File;

/**
 * Main activity of the classifier demo app.
 */

public class MainActivity extends FragmentActivity {

    public static String dir = "";

  @RequiresApi(api = Build.VERSION_CODES.R)
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    dir = getFilesDir().getAbsolutePath();

    File file = new File(MainActivity.dir +"/sample.txt");
    file.delete();
    Log.d("파일 삭제됨", "파일 삭제됨");

      //비정상 종료 예외처리
    Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());
    // If we're being restored from a previous state,
    // then we don't need to do anything and should return or else
    // we could end up with overlapping fragments.
    if (savedInstanceState != null) {
      return;
    }

    PermissionsFragment firstFragment = new PermissionsFragment();

    getSupportFragmentManager()
        .beginTransaction()
        .add(R.id.fragment_container, firstFragment)
        .commit();

    getSupportFragmentManager()
        .addFragmentOnAttachListener(
            (fragmentManager, fragment) -> {
              if (fragment instanceof PermissionsFragment) {
                ((PermissionsFragment) fragment)
                    .setOnPermissionsAcquiredListener(
                        () -> {
                          CameraFragment cameraFragment = new CameraFragment();

                          getSupportFragmentManager()
                              .beginTransaction()
                              .replace(R.id.fragment_container, cameraFragment)
                              .commit();
                        });
              }
            });
  }
    //----------비정상 종료 예외처리----------
    class ExceptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
            e.printStackTrace();
            File file = new File(MainActivity.dir +"/sample.txt");
            file.delete();
            Log.d("파일 삭제됨", "파일 삭제됨");
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(10);
        }
    }
    //---------------------------------
    //----------종료시 파일 삭제----------
    @Override
    protected void onDestroy() {
        File file = new File(MainActivity.dir +"/sample.txt");
        file.delete();
        Log.d("파일 삭제됨", "파일 삭제됨");
      super.onDestroy();
    }
    //---------------------------------
}
