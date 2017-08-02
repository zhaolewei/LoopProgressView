package zlw.com.radialprogresslib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zlw on 2017/7/22.
 */
public class LoopProgressView extends View {
    private Paint mPaint; // 绘制进度
    private Paint mBgPaint;//绘制背景

    private int divideCount; //将圆划分为120等份
    private double lineRateSize = 1 / 33d; //线长比例

    private float progress;
    private int width, height;
    private int progressLineColor;
    private int backgroundLineColor;

    private boolean isGradualColor;
    private int gradualEndColor;

    public LoopProgressView(Context context) {
        super(context);
        init();
    }

    public LoopProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getAttrs(context, attrs);
        init();
    }

    public LoopProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttrs(context, attrs);
        init();
    }

    public void setProgerss(float rate) {
        this.progress = rate;
        invalidate();
    }

    /**
     * 得到属性值
     *
     * @param context
     * @param attrs
     */
    private void getAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LoopProgressView);

        divideCount = ta.getInt(R.styleable.LoopProgressView_lpv_divideCount, 120);
        progressLineColor = ta.getColor(R.styleable.LoopProgressView_lpv_progressLineColor, Color.parseColor("#553399"));
        backgroundLineColor = ta.getInt(R.styleable.LoopProgressView_lpv_backgroundLineColor, Color.parseColor("#180e44"));
        gradualEndColor = ta.getInt(R.styleable.LoopProgressView_lpv_gradualEndColor, Color.parseColor("#ff0000"));
        progress = ta.getFloat(R.styleable.LoopProgressView_lpv_progress, 0.2f);
        isGradualColor = ta.getBoolean(R.styleable.LoopProgressView_lpv_isGradual, false);

        ta.recycle();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(progressLineColor);
        mPaint.setStrokeWidth(2);
        mPaint.setAntiAlias(true);
        mBgPaint = new Paint();
        mBgPaint.setStrokeWidth(2);
        mBgPaint.setColor(backgroundLineColor);
        mBgPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float angle = progress * 360 * 2 / 3;//进度转角度
        float ax = 360 / (float) divideCount;  //相邻两个刻度间的度数
        for (int i = 0; i < divideCount; i++) {
            float currentAngle = ax * i;//当前i所对应的度数

            if (isGradualColor) { //设置渐变色
                float currentProgress; ////当前i所对应的进度
                if (currentAngle <= 30) {
                    currentProgress = (30 - currentAngle) / 360f;
                } else {
                    currentProgress = (360 - currentAngle + 30) / 360f;
                }
                mPaint.setColor(getGradualColor(progressLineColor, gradualEndColor, currentProgress));
            }

            if (currentAngle > 30 && currentAngle < 150) {
                continue;
            }
            //绘制普通线（背景线）
            canvas.drawLine(getRotatePointX(currentAngle, 10, height / 2), getRotatePointY(currentAngle, 10, height / 2),
                    getRotatePointX(currentAngle, (float) (width * lineRateSize + 10), height / 2), getRotatePointY(currentAngle, (float) (width * lineRateSize + 10), height / 2), mBgPaint);
            if (angle <= 30) {
                if (currentAngle <= 30 && currentAngle >= 30 - angle) {
                    canvas.drawLine(getRotatePointX(currentAngle, 10, height / 2), getRotatePointY(currentAngle, 10, height / 2),
                            getRotatePointX(currentAngle, (float) (width * lineRateSize + 10), height / 2), getRotatePointY(currentAngle, (float) (width * lineRateSize + 10), height / 2), mPaint);
                }
            } else if (currentAngle <= 30) {
                canvas.drawLine(getRotatePointX(currentAngle, 10, height / 2), getRotatePointY(currentAngle, 10, height / 2),
                        getRotatePointX(currentAngle, (float) (width * lineRateSize + 10), height / 2), getRotatePointY(currentAngle, (float) (width * lineRateSize + 10), height / 2), mPaint);
            } else if (currentAngle <= 360 && currentAngle >= 360 - (angle - 30)) {
                canvas.drawLine(getRotatePointX(currentAngle, 10, height / 2), getRotatePointY(currentAngle, 10, height / 2),
                        getRotatePointX(currentAngle, (float) (width * lineRateSize + 10), height / 2), getRotatePointY(currentAngle, (float) (width * lineRateSize + 10), height / 2), mPaint);
            }

        }
    }

    /**
     * 获取当前进度的渐变色
     *
     * @param startColor
     * @param endColor
     * @param currentProgress
     * @return
     */
    private int getGradualColor(int startColor, int endColor, float currentProgress) {
        int redStart = Color.red(startColor);
        int blueStart = Color.blue(startColor);
        int greenStart = Color.green(startColor);
        int redEnd = Color.red(endColor);
        int blueEnd = Color.blue(endColor);
        int greenEnd = Color.green(endColor);


        int red = (int) (redStart + ((redEnd - redStart) * currentProgress));
        int greed = (int) (greenStart + ((greenEnd - greenStart) * currentProgress));
        int blue = (int) (blueStart + ((blueEnd - blueStart) * currentProgress));
        return Color.argb(255, red, greed, blue);
    }

    private float getRotatePointX(float a, float x, float y) {
        float x2 = x - width / 2;
        float y2 = y - height / 2;
        return (float) (x2 * Math.cos(2 * Math.PI / 360 * a) + y2 * Math.sin(2 * Math.PI / 360 * a)) + width / 2;
    }

    private float getRotatePointY(float a, float x, float y) {
        float x2 = x - width / 2;
        float y2 = y - height / 2;
        return (float) (y2 * Math.cos(2 * Math.PI / 360 * a) - x2 * Math.sin(2 * Math.PI / 360 * a)) + height / 2;
    }

}
