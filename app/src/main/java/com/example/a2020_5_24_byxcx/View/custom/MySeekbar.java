package com.example.a2020_5_24_byxcx.View.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.a2020_5_24_byxcx.R;

import androidx.annotation.Nullable;

public class MySeekbar extends View {
    private static final String TAG = "MySeekbar";

    private CheckListener listener;

    private int nowX = 0;
    private int maxX = 0;

    private int butwidth = 150;

    private Paint paint;

    public MySeekbar(Context context) {
        super(context);
    }

    public MySeekbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MySeekbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MySeekbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int height =  getHeight();
        int width = getWidth();
        maxX = width-butwidth;
        Log.d(TAG, "onDraw: h"+height);
        Log.d(TAG, "onDraw: w"+width);
        //设置笔
        paint = new Paint();
        paint.setAntiAlias(true);
        //绘制背景
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getResources().getColor(R.color.no_color));
        canvas.drawRect(0, 0, width, height,paint);
        //绘制进度条
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getResources().getColor(R.color.colorPrimary));
        canvas.drawRect(0, 0, nowX, height,paint);
        //绘制文字
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getResources().getColor(R.color.white_color));
        paint.setTextSize(25*2);
        Rect lRect = new Rect();
        String textContent = "拖动滑块验证";
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        paint.getTextBounds(textContent,0,textContent.length(),lRect);
        //用控件所占区域的长方形的宽度，减去文字所在的长方形的跨度，一半的位置就是文字开始的X坐标
        int baseLineX = getMeasuredWidth()/2-lRect.width()/2;
        int baselineY = (getMeasuredHeight() - (-fontMetrics.ascent + fontMetrics.descent)) / 2+fontMetrics.descent-fontMetrics.ascent-6;
        canvas.drawText(textContent, 0, textContent.length(), baseLineX , baselineY, paint);
        //绘制进度条按钮
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getResources().getColor(R.color.white_color));
        canvas.drawRect(nowX, 0, nowX+butwidth, height,paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(getResources().getColor(R.color.no_color2));
        canvas.drawRect(nowX, 0, nowX+butwidth, height,paint);
        //绘制箭头
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(getResources().getColor(R.color.no_color));
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth((float) 5.0);
        float sX,sY,eX,eY;
        //1
        sX = nowX+butwidth/2-30;
        sY = 30;
        eX = nowX+butwidth/2;
        eY = height/2;
        canvas.drawLine(sX,sY,eX,eY,paint);
        sX = nowX+butwidth/2-30;
        sY = height-30;
        eX = nowX+butwidth/2;
        eY = height/2;
        canvas.drawLine(sX,sY,eX,eY,paint);
        //2
        sX = nowX+butwidth/2+10;
        sY = 30;
        eX = nowX+butwidth/2+40;
        eY = height/2;
        canvas.drawLine(sX,sY,eX,eY,paint);
        sX = nowX+butwidth/2+40;
        sY = height/2;
        eX = nowX+butwidth/2+10;
        eY = height-30;
        canvas.drawLine(sX,sY,eX,eY,paint);
        paint.setXfermode(null);
    }

    private void OnDrawbtu(int x) {
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height,width,widthmode,widthsize,heightmode,heightsize;
        widthmode=MeasureSpec.getMode(widthMeasureSpec);//获取模式
        widthsize=MeasureSpec.getSize(widthMeasureSpec);//获取传入值
        heightmode=MeasureSpec.getMode(heightMeasureSpec);
        heightsize=MeasureSpec.getSize(heightMeasureSpec);
        //Log.d(TAG, "onMeasure: wmode"+widthmode);
        //Log.d(TAG, "onMeasure: hmode"+heightmode);
        width=0;
        if (widthmode==MeasureSpec.EXACTLY){
            width=widthsize;
            //Log.d(TAG, "onMeasure: w"+width);
        }
        height=0;
        if (heightmode==MeasureSpec.EXACTLY){
            height=heightsize;
            //Log.d(TAG, "onMeasure: h"+height);
        }
        setMeasuredDimension(width,height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int movex = (int) event.getX();
        Log.d(TAG, "onTouchEvent: now"+nowX);
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                if (nowX<maxX){
                    nowX =+ movex;
                    if (nowX>maxX){
                        nowX=maxX;
                    }
                    Log.d(TAG, "onTouchEvent: 可以移动");
                    invalidate();
                }else {
                    Log.d(TAG, "onTouchEvent: 完成");
                }
                break;
            case MotionEvent.ACTION_UP:
                    if (nowX>=maxX){
                        if (listener != null){
                            listener.Checkok();
                            this.setEnabled(false);
                        }
                        return true;
                    }else {
                        nowX = 0;
                        invalidate();
                    }
                break;
        }

        return true;
    }

    public void setListener(CheckListener listener) {
        this.listener = listener;
    }

    public interface CheckListener{
        void Checkok();
    }
}
