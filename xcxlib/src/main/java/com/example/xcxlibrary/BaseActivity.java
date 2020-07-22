package com.example.xcxlibrary;

import android.Manifest;
import android.os.Bundle;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class BaseActivity extends AppCompatActivity {

    private CompositeDisposable compositeDisposable = null;

    @Override
    protected void onStart() {
        super.onStart();
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    protected void getPermission(String[] permissions) {
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
