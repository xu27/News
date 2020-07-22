package com.example.a2020_5_24_byxcx.View;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.a2020_5_24_byxcx.Modle.MyMainModle;
import com.example.a2020_5_24_byxcx.Modle.News_JAVAbean.Base_Bean;
import com.example.a2020_5_24_byxcx.Modle.News_JAVAbean.Bean_item;
import com.example.a2020_5_24_byxcx.R;
import com.example.xcxlibrary.Util.HttpUtil.RetrofitUtil.RetrofitManager;
import com.example.xcxlibrary.Util.NoticeHelper;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

class MsgReceiver extends BroadcastReceiver {
    private static final String TAG = "MsgReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: ");
        switch (intent.getAction()) {
            case Intent.ACTION_BOOT_COMPLETED:
                Log.d(TAG, "开机");
                break;
            case Intent.ACTION_SHUTDOWN:
                Log.d(TAG, "关机");
                break;
            case Intent.ACTION_SCREEN_ON:
                Log.d(TAG, "亮屏");
                break;
            case Intent.ACTION_SCREEN_OFF:
                Log.d(TAG, "息屏");
                break;
            case Intent.ACTION_USER_PRESENT:
                Log.d(TAG, "手机解锁");
                break;
            case "location.reportsucc":
                Bean_item item = intent.getParcelableExtra("key");
                Intent intent1 = new Intent(context.getApplicationContext(), ArticleActivity.class);
                        intent1.putExtra("url", item.getUrl());
                        intent1.putExtra("source", item.getSource());
                        intent1.putExtra("img", item.getImgsrc());
                        intent1.putExtra("title",item.getTitle());
                        intent1.putExtra("id", item.getDocid());
                        //intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(), (int) System.currentTimeMillis(), intent1, PendingIntent.FLAG_ONE_SHOT);
                        NoticeHelper noticeHelper = new NoticeHelper();
                        noticeHelper.suspension_notice(context, R.drawable.video_backward_icon,
                                item.getSource(),item.getTitle(),pendingIntent);
                break;
        }
    }
}
