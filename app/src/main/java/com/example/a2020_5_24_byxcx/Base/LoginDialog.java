package com.example.a2020_5_24_byxcx.Base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a2020_5_24_byxcx.R;
import com.example.a2020_5_24_byxcx.View.LoginActivity;

import androidx.annotation.NonNull;

public class LoginDialog extends Dialog {

    private TextView textView1,textView2;

    public LoginDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_login);
        textView1 = findViewById(R.id.login);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, LoginActivity.class));
                if (LoginDialog.this.isShowing()) {
                    LoginDialog.this.dismiss();
                }
            }
        });
        textView1 = findViewById(R.id.quxiao);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LoginDialog.this.isShowing()) {
                    LoginDialog.this.dismiss();
                }
            }
        });
    }
}
