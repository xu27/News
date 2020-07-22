package com.example.xcxlibrary;

import android.Manifest;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class BaseActivity extends AppCompatActivity {

    private boolean isExit = false;

    protected CompositeDisposable compositeDisposable = null;

    protected boolean NET_FLAG = true;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    protected void addDisposable(Disposable disposable){
        if (compositeDisposable!=null){
            compositeDisposable.add(disposable);
        }
    }


    @Override

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode==KeyEvent.KEYCODE_BACK){
            if (isDouble_click_to_return()) {
                exitByDoubleClick();
            }else {
                return super.onKeyDown(keyCode, event);
            }
        }

        return false;

    }

    protected boolean isDouble_click_to_return(){
        return false;
    }

    private void exitByDoubleClick() {
        Timer tExit=null;
        if(!isExit){
            isExit=true;
            Toast.makeText(BaseActivity.this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
            tExit=new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit=false;//取消退出
                }
            },2000);// 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        }else{
            onDestroy();
            System.exit(0);
        }
    }

    protected void getPermission(String... permissions) {
        //获取权限
        RxPermissions rxPermissions = new RxPermissions(this);
        addDisposable(rxPermissions.requestEach(permissions)
                .subscribe(new Consumer<Permission>() {
                               @Override
                               public void accept(Permission permission) throws Exception {
                                   if (permission.granted) {
                                   } else if (permission.shouldShowRequestPermissionRationale) {
                                       // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                                       Toast.makeText(BaseActivity.this, "没有" + permission.name + "部分功能可能无法使用", Toast.LENGTH_SHORT);
                                   } else {
                                       // 用户拒绝了该权限，并且选中『不再询问』，提醒用户手动打开权限
                                       Toast.makeText(BaseActivity.this, "请手动打开权限" + permission.name, Toast.LENGTH_SHORT);
                                   }
                               }
                           }
                )
        );
    }
}
