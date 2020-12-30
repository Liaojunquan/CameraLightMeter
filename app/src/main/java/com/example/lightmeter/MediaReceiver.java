package com.example.lightmeter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class MediaReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case Intent.ACTION_MEDIA_CHECKING:
                Log.d("MediaReceiver","检查中");
                break;
            case Intent.ACTION_MEDIA_MOUNTED:
                /*Uri uri = intent.getData();// 获取挂载路径, 读取U盘文件
                Log.d("MediaReceiver",uri.toString());
                String[] str = uri.toString().split("/");
                otgPath = "/" + str[1] + "/" + str[2];*/
                Toast.makeText(MyApplication.getContext(),"OTG插入",Toast.LENGTH_SHORT).show();
                break;
            case Intent.ACTION_MEDIA_EJECT:
                Log.d("MediaReceiver","弹出");
                Toast.makeText(MyApplication.getContext(),"OTG拔出",Toast.LENGTH_SHORT).show();
                break;
            case Intent.ACTION_MEDIA_UNMOUNTED:
                Log.d("MediaReceiver","UNMOUNTED");
                break;
        }
    }
}
