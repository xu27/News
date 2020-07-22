package com.example.a2020_5_24_byxcx.Base;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.a2020_5_24_byxcx.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.annotation.NonNull;

public class Header_imgDialog extends BottomSheetDialog implements View.OnClickListener{

    private TextView byDCIM,byCAMERA,byCANCEL;

    private OnClickListener listener;

    public Header_imgDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.getheaderimg_dialog);
        byDCIM = findViewById(R.id.header_img_Dialog_check_byDCIM);
        byCAMERA = findViewById(R.id.header_img_Dialog_check_byCAMERA);
        byCANCEL = findViewById(R.id.header_img_Dialog_check_byCANCEL);
        byDCIM.setOnClickListener(this);
        byCAMERA.setOnClickListener(this);
        byCANCEL.setOnClickListener(this);
        //设置透明背景
        this.getWindow().findViewById(R.id.design_bottom_sheet)
                .setBackgroundResource(android.R.color.transparent);
    }

    public Header_imgDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    protected Header_imgDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }
    }

    public interface OnClickListener{
        public void onClick(View view);
    }
}
