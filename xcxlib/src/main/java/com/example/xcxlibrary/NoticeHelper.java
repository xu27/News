package com.example.xcxlibrary;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import java.io.File;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

public class NoticeHelper {

    public void suspension_notice(Context context, int icon, String title, String value){
        NotificationManager manager;
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String id = "XCX"+System.currentTimeMillis();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,id);
        //构造安卓8.0Channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            addChannel(manager,id);
        }
        //加入默认操作
        builder.setWhen(System.currentTimeMillis());
        builder.setSmallIcon(icon);
        builder.setAutoCancel(true);
        builder.setContentTitle(title);
        builder.setTicker(value);
        builder.setContentText(value);
        manager.notify(2,builder.build());

    }

    @TargetApi(Build.VERSION_CODES.O)
    public void addChannel(NotificationManager manager,String id){
        NotificationChannel channel = new NotificationChannel(id,"my_channel_01",manager.IMPORTANCE_HIGH);
        channel.setShowBadge(true);
        manager.createNotificationChannel(channel);
    }
}
