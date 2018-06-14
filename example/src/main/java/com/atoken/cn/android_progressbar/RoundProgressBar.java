package com.atoken.cn.android_progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;


public class RoundProgressBar extends HorizontalProgressbar {


    private int mRadius = dp2px(50);
    private int mMaxPaintWidth;

    public RoundProgressBar(Context context) {
        super(context, null);
    }

    public RoundProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public RoundProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mReachHeight = (int) (mUnreachHeight * 2.5f);

        TypedArray ta = getContext().obtainStyledAttributes(attrs,
                R.styleable.RoundProgressBar);
        mRadius = (int) ta.getDimension(R.styleable.RoundProgressBar_Round_radius, mTextOffset);

        ta.recycle();

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mTextSize = sp2px(14);
    }


    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);


        mMaxPaintWidth = Math.max(mReachHeight, mUnreachHeight);

        if (heightMode != MeasureSpec.EXACTLY) {

            int exceptHeight = (getPaddingTop() + getPaddingBottom()
                    + mRadius * 2 + mMaxPaintWidth);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(exceptHeight,
                    MeasureSpec.EXACTLY);
        }
        if (widthMode != MeasureSpec.EXACTLY) {
            int exceptWidth = (getPaddingLeft() + getPaddingRight()
                    + mRadius * 2 + mMaxPaintWidth);
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(exceptWidth,
                    MeasureSpec.EXACTLY);
        }

        setMeasuredDimension(heightMeasureSpec, heightMeasureSpec);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        String text = getProgress() + "%";
        float textWidth = mPaint.measureText(text);
        float textHeight = (mPaint.descent() + mPaint.ascent()) / 2;

        canvas.save();

        canvas.translate(getPaddingLeft(), getPaddingTop());
        mPaint.setStyle(Paint.Style.STROKE);

        //draw mUnreach
        mPaint.setColor(mUnreachColor);
        mPaint.setStrokeWidth(mUnreachHeight);
        canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);

        //Reach
        mPaint.setColor(mReachColor);
        mPaint.setStrokeWidth(mReachHeight);
        float sweepAngle = getProgress() * 1.0f / getMax() * 360;
        RectF rectF = new RectF(0, 0, mRadius * 2, mRadius * 2);
        canvas.drawArc(rectF, 0, sweepAngle, false, mPaint);

        //test
        mPaint.setColor(mTextColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText(text, mRadius - textWidth / 2, mRadius - textHeight, mPaint);


        canvas.restore();
    }


}
