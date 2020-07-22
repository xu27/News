package com.example.xcxlibrary.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;

import com.example.xcxlibrary.R;

public class NetUtil {
    //判断dialog是否存在的标志
    private static Dialog dialog = null;

    /**
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvalible(Context context) {
        // 获得网络状态管理器
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 建立网络数组
            NetworkInfo[] net_info = connectivityManager.getAllNetworkInfo();

            if (net_info != null) {
                for (int i = 0; i < net_info.length; i++) {
                    // 判断获得的网络状态是否是处于连接状态
                    if (net_info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void checkNetwork(final Activity activity) {
        if (!isNetworkAvalible(activity)) {
            dialog = new AlertDialog.Builder(activity)
                    .setTitle("网络状态提示")
                    .setMessage("当前没有可以使用的网络，请设置网络！")
                    .setIcon(R.drawable.ic_question_fill)
                    .setCancelable(false)
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface di,
                                                    int whichButton) {
                                    //跳转到设置界面
                                    activity.startActivityForResult(new Intent(
                                                    Settings.ACTION_WIRELESS_SETTINGS),
                                            0);
                                }
                            }).create();
            if (dialog.isShowing()) {
                Log.d("checkNetwork", "dialog is showing...");
                dialog.dismiss();
            } else {
                dialog.show();
            }


        }
        return;
    }
}
