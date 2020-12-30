package com.example.lightmeter;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

public class MyApplication extends Application {
    private static Context context;
    @Override
    public void onCreate(){
        context = getApplicationContext();
        Log.d("MyApplication","获取Context成功!");
        super.onCreate();
    }
    public static Context getContext(){
        Log.d("MyApplication","获取Context");
        return context;
    }
}
