package com.example.a2020_5_24_byxcx.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a2020_5_24_byxcx.R;
import com.example.a2020_5_24_byxcx.View.custom.MySeekbar;
import com.example.xcxlibrary.BaseActivity;
import com.example.xcxlibrary.Util.CheckUtil;
import com.example.xcxlibrary.Util.SharePrenceUtil;

public class Login1Activity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "Login1Activity";

    private boolean flag = false;

    @BindView(R.id.login1_phone)
    protected EditText phone;
    @BindView(R.id.login1_yanzheng)
    protected EditText yanzheng;
    @BindView(R.id.login_get_yanzheng)
    protected Button getyanzheng;
    @BindView(R.id.login1_but)
    protected Button login;
    @BindView(R.id.login1_CheckSeekbar)
    protected MySeekbar mySeekbar;

    private String number = null;

    private Unbinder mUnbinder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);
        mUnbinder = ButterKnife.bind(this);
        init();
    }

    private void init() {
        getyanzheng.setEnabled(false);
        getyanzheng.setOnClickListener(this);
        login.setOnClickListener(this);
        phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (CheckUtil.isMobileNO(phone.getText().toString())) {
                        mySeekbar.setVisibility(View.VISIBLE);
                        getyanzheng.setEnabled(true);
                    } else {
                        showToast("手机号码不对");
                    }
                }
            }
        });
        mySeekbar.setListener(new MySeekbar.CheckListener() {
            @Override
            public void Checkok() {
                if (!flag) {
                    getyanzheng.setBackgroundColor(getResources().getColor(R.color.black_color));
                    flag = true;
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_get_yanzheng:
                if (flag) {
                    if (CheckUtil.isMobileNO(phone.getText().toString())) {
                        getnumber();
                    } else {
                        showToast("手机号码不对");
                    }
                } else {
                    showToast("请滑动验证");
                }
                break;
            case R.id.login1_but:
                if (CheckUtil.isMobileNO(phone.getText().toString())) {
                    if (yanzheng.getText().toString().equals(number)) {
                        showToast("登陆成功");
                        SharePrenceUtil.saveInt(Login1Activity.this, "login", 1);
                        SharePrenceUtil.saveLong(Login1Activity.this, "lastLogonTime", System.currentTimeMillis());
                        finish();
                    }
                }
                break;
        }

    }

    private void getnumber() {
        int i = (int) ((Math.random() * 9000) + 1000);
        Log.d(TAG, "getnumber: " + i);
        number = i + "";
        showToast("你的验证码是" + number);
    }

    private void showToast(String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Login1Activity.this.getApplication(), s, Toast.LENGTH_LONG).show();
            }
        });
    }
}
