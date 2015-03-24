package com.szu.library.imageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.szu.library.R;

/**
 * Created by lgp on 2015/3/23.
 */
public class CircleImageView extends View{
    private final String TAG = getClass().getSimpleName();
    private Bitmap mBitmap;
    private Paint mPaint;
    private float centerX;
    private float centerY;
    private float radius;
    private float borderWidth = 0;
    private int borderColor = Color.WHITE;
    private int emptyCircleColor = Color.RED;
    private float emptyCircleTextSize;
    private String emptyCircleText = "C";
    private int emptyCircleTextColor = Color.WHITE;
    public CircleImageView(Context context) {
        super(context);
        initView(null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    private void initView(AttributeSet attrs){
        if (attrs != null){
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CircleImageView);
            borderWidth = array.getDimension(R.styleable.CircleImageView_borderWidth, 0);
            borderColor = array.getColor(R.styleable.CircleImageView_borderColor, Color.WHITE);
            emptyCircleColor = array.getColor(R.styleable.CircleImageView_emptyCircleColor, Color.RED);
            String text = array.getString(R.styleable.CircleImageView_emptyCircleText);
            emptyCircleTextSize = array.getDimension(R.styleable.CircleImageView_emptyCircleTextSize, 0);
            emptyCircleTextColor = array.getColor(R.styleable.CircleImageView_emptyCircleTextColor, Color.WHITE);
            if (!TextUtils.isEmpty(text)){
                emptyCircleText = text;
            }
            array.recycle();
        }
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getMeasuredHeight() != getMeasuredWidth()){
            int size = Math.min(getMeasuredHeight(), getMeasuredWidth());
            setMeasuredDimension(size, size);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        centerX = getMeasuredWidth() / 2;
        centerY = getMeasuredHeight() / 2;

        if (mBitmap == null){
            drawDefault(canvas);
            return;
        }

        mBitmap = decodeScaleBitmap(mBitmap, getMeasuredWidth()- getPaddingLeft() - getPaddingRight(),
                getMeasuredHeight()- getPaddingTop() - getPaddingBottom());
        radius = Math.min(mBitmap.getWidth() / 2, mBitmap.getHeight());
        if (borderWidth > 0){
            mPaint.setColor(borderColor);
            canvas.drawCircle(centerX, centerY, radius, mPaint);
        }

        BitmapShader shader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP);
        mPaint.setShader(shader);

        canvas.drawCircle(centerX, centerY, radius - borderWidth, mPaint);
    }

    public void setBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
        invalidate();
    }

    public void setImageDrawable(Drawable drawable){
        setBitmap(drawableToBitmap(drawable));
    }

    public void setImageDrawable(int res){
        setBitmap(drawableToBitmap(getDrawable(res)));
    }

    private void drawDefault(Canvas canvas){
        int width = getMeasuredWidth() - getPaddingRight() - getPaddingLeft();
        int height = getMeasuredHeight() - getPaddingBottom() - getPaddingTop();
        float radiusX = width / 2;
        float radiusY = height / 2;
        radius = Math.min(radiusX, radiusY);

        mPaint.setColor(emptyCircleColor);
        canvas.drawCircle(centerX, centerY, radius, mPaint);

        mPaint.setColor(emptyCircleTextColor);
        if (emptyCircleTextSize == 0){
            emptyCircleTextSize = radius * 2f / 1.414f;
        }

        mPaint.setTextSize(emptyCircleTextSize);
        mPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float fontHeight = fontMetrics.bottom - fontMetrics.top;
        float textBaseY = height - (height - fontHeight) / 2 - fontMetrics.bottom;

        canvas.drawText(emptyCircleText.substring(0, 1), width / 2, textBaseY,  mPaint);
    }

    private Drawable getDrawable(int res){
        if (res == 0){
            throw new IllegalArgumentException("getDrawable res can not be zero");
        }
        return getContext().getResources().getDrawable(res);
    }

    private Bitmap decodeScaleBitmap(Bitmap bitmap, int reqWidth, int reqHeight) {
        return Bitmap.createScaledBitmap(bitmap, reqWidth, reqHeight, true);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

}
