package com.example.a2020_5_24_byxcx.View.custom;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.example.a2020_5_24_byxcx.R;

import androidx.annotation.Nullable;

public class MyPbView extends View {


    private static final String INSTANCE="instance";
    private static final String KEY="KEY";
    private int mRadious;
    private int mLinewidth;
    private int textSize;
    private int color;
    private int mProgress;
    protected int style=0;
    protected Paint paint;

    public MyPbView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mRadious = (int) dp2px(50);
        mLinewidth = (int) dp2px(2);//6
        color=Color.BLUE;
        mProgress=0;
        textSize= (int) dp2px(25);
        TypedArray typedArray= context.obtainStyledAttributes(attrs, R.styleable.MyPbView);
        if (typedArray!=null){
            for(int i=0;i<typedArray.getIndexCount();i++){
                switch (typedArray.getIndex(i))
                {
                    case R.styleable.MyPbView_radious:
                        mRadious = (int)typedArray.getDimension(R.styleable.MyPbView_radious,dp2px(30));
                        break;
                    case R.styleable.MyPbView_Pbcolor:
                        color=typedArray.getInt(R.styleable.MyPbView_Pbcolor,Color.BLUE);
                        break;
                    case R.styleable.MyPbView_Line_width:
                        mLinewidth= (int) typedArray.getDimension(R.styleable.MyPbView_Line_width,dp2px(3));
                        break;
                    case R.styleable.MyPbView_android_progress:
                        mProgress=typedArray.getInt(R.styleable.MyPbView_android_progress,0);
                        break;
                    case R.styleable.MyPbView_android_textSize:
                        textSize= (int) typedArray.getDimension(R.styleable.MyPbView_android_textSize,dp2px(25));
                }
            }
        }
        inpiant();
        typedArray.recycle();
    }

    private float dp2px(int i) {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,i,getResources().getDisplayMetrics()
        );
    }

    private void inpiant() {
        paint=new Paint();
        if (style!=0){
            if(style==1){
                paint.setColor(Color.BLUE);
            }
            if (style==2){
                paint.setColor(Color.RED);
            }
        }else {
            paint.setColor(color);
        }
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int heigth = getHeight();
        int width = getWidth();
        //绘制圆背景
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(0);
        paint.setColor(Color.argb(255,255,255,255));
        canvas.drawCircle(heigth/2,
                width/2,
                width/2-getPaddingLeft()-paint.getStrokeWidth()/2,
                paint);
        //绘制圆
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(mLinewidth*1.0f/4);
        canvas.drawCircle(heigth/2,
                width/2,
                width/2-getPaddingLeft()-paint.getStrokeWidth()/2,
                paint);
        //绘制圆弧
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);//保持之前style有奇效
        paint.setStrokeWidth(mLinewidth);
        canvas.save();//保存未移动原点之前的画布
        canvas.translate(getPaddingLeft(),getPaddingTop());//移动绘制原点
        canvas.drawArc(new RectF(0,0, width-getPaddingLeft()-getPaddingRight(), heigth-getPaddingTop()-getPaddingBottom()),
                0,
                mProgress*1.0f/100*360,
                false,
                paint
                );
        canvas.restore();//还原之前的画布
        //绘制文本
        String text = "跳过";
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(0);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(textSize);
        int y = getHeight() / 2;
        Rect bound = new Rect();
        paint.getTextBounds(text, 0, text.length(), bound);
        int textHeight = bound.height();
        canvas.drawText(text, 0, text.length(), getWidth() / 2, y + textHeight / 2, paint);
    }

    /**
     * Android M运行时权限请求封装
     *
     * @param widthMeasureSpec 宽度
     * @param heightMeasureSpec 高度
     * 通过getmode方法获取传入模式
     *AT_MOST:
     * 父容器指定了一个可用的大小即SpecSize，View的大小不能大于这个值，它对应LayoutParams中的wrap_content
     *EXACTLY:
     * 父容器已经检测出View所需要的精确大小，这个时候View的最终大小就是SpecSize所指定的值。它对应于LayoutParams中的match_parent和具体的数值这二种模式
     *UNSPECIFIED:
     * 父容器不对View有任何的限制，要多大给多大。
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height,width,widthmode,widthsize,heightmode,heightsize;
        widthmode=MeasureSpec.getMode(widthMeasureSpec);//获取模式
        widthsize=MeasureSpec.getSize(widthMeasureSpec);//获取传入值
        heightmode=MeasureSpec.getMode(heightMeasureSpec);
        heightsize=MeasureSpec.getSize(heightMeasureSpec);

        //设置宽度
        width=0;
        if (widthmode==MeasureSpec.EXACTLY){
            //用户指定值
            //EXACTLY对应match_parent
            width=widthsize;
        }else {
            int needwidth=masurewidth()+getPaddingLeft()+getPaddingRight();
            if(widthmode==MeasureSpec.AT_MOST){
                width=Math.min(needwidth,widthsize);
            }else {
                width=needwidth;
            }
        }
        height=0;
        if (heightmode==MeasureSpec.EXACTLY){
            height=heightsize;
        }else {
            int needheight=masureheight()+getPaddingBottom()+getPaddingTop();
            if(height==MeasureSpec.AT_MOST){
                height=Math.min(needheight,heightsize);
            }else {
                height=needheight;
            }
        }

        setMeasuredDimension(width,height);
    }

    /**
     * 改方法用来测量高度
     */
    private int masureheight() {
        return mRadious*2;
    }


    /**
     * 改方法用来测量宽度
     */
    private int masurewidth() {
        return mRadious*2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle b=new Bundle();
        b.putInt(KEY,mProgress);
        b.putParcelable(INSTANCE,super.onSaveInstanceState());
        return b;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if(state instanceof Bundle){
            Bundle b=(Bundle)state;
            Parcelable parcelable=b.getParcelable(INSTANCE);
            super.onRestoreInstanceState(parcelable);
            mProgress=b.getInt(KEY);
            return;
        }
        super.onRestoreInstanceState(state);
    }

    public void setProgress(int mProgress) {
        this.mProgress = mProgress;
        invalidate();//重绘
    }

    public void startAnimator(MyPbView myPbView,int start,int end,int time,AnimatorListenerAdapter adapter){
       ObjectAnimator animator = ObjectAnimator.ofInt(myPbView,"progress",start,end).setDuration(time);
       animator.addListener(adapter);
       animator.start();
    }
}
