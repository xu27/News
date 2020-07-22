package com.example.xcxlibrary;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class BaseService extends Service {

    protected Context mContext;

    /**
     * service执行间隔时间（毫秒）
     */
    public long IntervalMillis = 1000*60;
    /**
     * 线程
     **/
    private Runnable runable;

    private static BaseService Instance;

    private static boolean isrunning = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!isrunning) {
            // if (BaseService.intent == null) BaseService.intent = intent;
            // if (context == null) context = this.getBaseContext();

            isrunning = true;
            // Log.i(this.getClass().getSimpleName(), "onStartCommand");

            // 执行服务处理逻辑
            doServicesLogic(IntervalMillis);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 循环执行服务处理逻辑
     */
    private void doServicesLogic(final long delayMillis) {
        if (runable == null) {
            runable = new Runnable() {
                @Override
                public void run() {
                    if (isrunning) {
                        serviceLogic();                        // 执行服务处理逻辑

                        doServicesLogic(delayMillis);        // 处理逻辑执行完成1秒后再次执行
                    }
                }
            };
        }
        new Handler().postDelayed(runable, delayMillis);
    }

    /**
     * 在service中待执行的逻辑（在service未停止时，会一直执行，每轮逻辑执行间隔IntervalMillis毫秒）
     */
    public void serviceLogic() {
    }

    ;

    /**
     * 获取当前服务的单例对象
     */
    public static BaseService GetInstance() {
        if (Instance == null) Instance = new BaseService();
        return Instance;
    }

    /**
     * 启动服务, service_cls当前服务对应的类
     */
    public void start(Context context, Class<?> service_cls) {
        if (!isrunning) {
            // BaseService.context = context;
            mContext = context;
            Intent intent = new Intent(context, service_cls);
            context.startService(intent);

            // Toast.makeText(context, this.getClass().getSimpleName() + " 服务已启动 !", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 停止服务（暂时停止服务，服务逻辑会在应用退出后自行重启，并一直运行）
     */
    public void stop() {
        if (isrunning) {
            stopSelf();
            // context.stopService(intent);
            isrunning = false;

            // Toast.makeText(context, this.getClass().getSimpleName() + " 服务已停止 !", Toast.LENGTH_SHORT).show();
        }
    }

    public void setIntervalMillis(long intervalMillis) {
        IntervalMillis = intervalMillis;
    }

}
