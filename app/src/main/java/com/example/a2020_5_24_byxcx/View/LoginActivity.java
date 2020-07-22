package com.example.a2020_5_24_byxcx.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.a2020_5_24_byxcx.R;
import com.example.xcxlibrary.Util.SharePrenceUtil;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SharePrenceUtil.getInt(this,"login") == 1){
            this.finish();
        }
    }

    public void OnClick(View view){
        switch (view.getId()){
            case R.id.loginitem1:
                //手机号登陆
                startActivity(new Intent(this,Login1Activity.class));
                break;
            case R.id.loginitem2:
                //注册
                startActivity(new Intent(this,Login2Activity.class));
                break;
            case R.id.loginitem3:
                break;
            case R.id.loginitem4:
                break;
            case R.id.loginitem5:
                break;
            case R.id.loginitem6:
                break;

        }
    }
}
