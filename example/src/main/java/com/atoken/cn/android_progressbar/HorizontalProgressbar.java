package com.atoken.cn.android_progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;


public class HorizontalProgressbar extends ProgressBar {

    private static final int DEFAULT_TEXT_SIZE = 10;//sp
    private static final int DEFAULT_TEXT_COLOR = 0xFFFC00D1;
    private static final int DEFAULT_TEXT_OFFSET = 10;

    private static final int DEFAULT_UNREACH_COLOR = 0xFFd3d6da;
    private static final int DEFAULT_UNREACH_HEIGHT = 2;//dp
    private static final int DEFAULT_REACH_COLOR = DEFAULT_TEXT_COLOR;
    private static final int DEFAULT_REACH_HEIGHT = 2;

    protected int mTextSize = sp2px(DEFAULT_TEXT_SIZE);
    protected int mTextColor = DEFAULT_TEXT_COLOR;
    protected int mTextOffset = dp2px(DEFAULT_TEXT_OFFSET);
    protected int mUnreachColor = DEFAULT_UNREACH_COLOR;
    protected int mUnreachHeight = dp2px(DEFAULT_UNREACH_HEIGHT);
    protected int mReachColor = DEFAULT_REACH_COLOR;
    protected int mReachHeight = dp2px(DEFAULT_REACH_HEIGHT);

    protected Paint mPaint = new Paint();
    protected int mRealWidth;


    public HorizontalProgressbar(Context context) {
        this(context, null);
    }

    public HorizontalProgressbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalProgressbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setHorizontalScrollBarEnabled(true);

        obtainStyleAttrs(attrs);

        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);
    }

    /**
     * 获取自定义属性
     *
     * @param attrs
     */
    private void obtainStyleAttrs(AttributeSet attrs) {

        TypedArray ta = getContext().obtainStyledAttributes(attrs,
                R.styleable.HorizontalProgressbar);

        mTextSize = (int) ta.getDimension(R.styleable.HorizontalProgressbar_progress_text_size, mTextSize);
        mTextColor = ta.getColor(R.styleable.HorizontalProgressbar_progress_text_color, mTextColor);
        mTextOffset = (int) ta.getDimension(R.styleable.HorizontalProgressbar_progress_text_offset, mTextOffset);

        mUnreachHeight = (int) ta.getDimension(R.styleable.HorizontalProgressbar_progress_unreach_height, mUnreachHeight);
        mUnreachColor = ta.getColor(R.styleable.HorizontalProgressbar_progress_unreach_color, mUnreachColor);
        mReachHeight = (int) ta.getDimension(R.styleable.HorizontalProgressbar_progress_reach_height, mReachHeight);
        mReachColor = ta.getColor(R.styleable.HorizontalProgressbar_progress_reach_color, mReachColor);


        ta.recycle();


    }

    protected int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    protected int sp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                dpVal, getResources().getDisplayMetrics());
    }


    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthVal = MeasureSpec.getSize(widthMeasureSpec);

        int height = mesasureHeight(heightMeasureSpec);


        setMeasuredDimension(widthVal, height);

        mRealWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();


    }

    private int mesasureHeight(int heightMeasureSpec) {

        int result = 0;

        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);

        //精确值
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {

            //字体最高-字体最低
            int textHeight = (int) (mPaint.descent() + mPaint.ascent());
            //需要比较左侧 右侧 字体的高度 abs取绝对值
            int max = Math.max(mReachHeight, mUnreachHeight);
            result = Math.max(max, Math.abs(textHeight));

            //有个最大值的限度
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }

        }


        return result;
    }


    @Override
    protected synchronized void onDraw(Canvas canvas) {

        canvas.save();
        //绘制的起点
        canvas.translate(getPaddingLeft(), getHeight() / 2);

        boolean noNeedUnRech = false;

        //获取进度百分比
        float radio = getProgress() * 1.0f / getMax();
        //进度的宽度
        float progressX = radio * mRealWidth;
        String text = getProgress() + "%";
        //文本的宽度
        float textWidth = mPaint.measureText(text);
        float textHeight = (mPaint.descent() + mPaint.ascent())/2 ;

        //如果到达最后，则未到达的进度条不需要绘制
        if (progressX + textWidth > mRealWidth) {
            noNeedUnRech = true;
            progressX = mRealWidth - textWidth;
        }

        //已绘制的进度
        float endX = progressX - mTextOffset / 2;
        if (endX > 0) {
            mPaint.setColor(mReachColor);
            mPaint.setStrokeWidth(mReachHeight);
            canvas.drawLine(0, 0, endX, 0, mPaint);
        }


        // 绘制文本

        mPaint.setColor(mTextColor);
        canvas.drawText(text, progressX, -textHeight, mPaint);


        //draw unreachBar
        if (!noNeedUnRech) {
            float start = progressX + mTextOffset / 2 + textWidth;
            mPaint.setColor(mUnreachColor);
            mPaint.setStrokeWidth(mUnreachHeight);
            canvas.drawLine(start, 0, mRealWidth, 0, mPaint);
        }

        canvas.restore();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRealWidth = w - getPaddingRight() - getPaddingLeft();

    }


}

