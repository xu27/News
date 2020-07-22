package com.example.a2020_5_24_byxcx.Base;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.example.a2020_5_24_byxcx.R;

import androidx.annotation.NonNull;

public class AppMsgDialog extends Dialog {

    private ImageView imageView;

    public AppMsgDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_appmsg);
        imageView = findViewById(R.id.dialog_img);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppMsgDialog.this.isShowing()) {
                    AppMsgDialog.this.dismiss();
                }
            }
        });
    }
}
