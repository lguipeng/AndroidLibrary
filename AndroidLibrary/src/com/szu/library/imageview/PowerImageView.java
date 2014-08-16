package com.szu.library.imageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.szu.library.R;

import java.io.InputStream;
import java.lang.reflect.Field;

/**
 * Created by lgp on 2014/8/15.
 */
public class PowerImageView extends ImageView implements View.OnClickListener{

    private final String TAG = "PowerImageView";

    private Movie movie;

    private boolean isAutoPlay;

    private boolean isRepeatPlay;

    private int resourceId;

    private int bitmapWidth;

    private int bitmapHeight;

    private Bitmap mStartPlayButton;

    private boolean isPlaying;

    private long mMovieStart = 0;

    public PowerImageView(Context context) {
       this(context,null);
    }

    public PowerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        if(attrs != null)
        {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PowerImageView);
            resourceId = getResourceId(context,a);
            if(resourceId != 0)
            {
                InputStream is = getResources().openRawResource(resourceId);
                movie = Movie.decodeStream(is);
                if(movie != null)
                {
                    isAutoPlay = a.getBoolean(R.styleable.PowerImageView_auto_play,true);
                    isRepeatPlay = a.getBoolean(R.styleable.PowerImageView_repeat_play,true);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    bitmapWidth = bitmap.getWidth();
                    bitmapHeight = bitmap.getHeight();

                    bitmap.recycle();
                    if(!isAutoPlay)
                    {
                        mStartPlayButton = BitmapFactory.decodeResource(getResources(),R.drawable.img_play_normal);
                        setOnClickListener(this);
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == getId())
        {
            isPlaying = true;
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(movie == null)
        {
            super.onDraw(canvas);
        }else
        {
            if(isAutoPlay)
            {
                if(isRepeatPlay)
                {
                    playMovie(canvas);
                    invalidate();
                }else
                {
                    if(!playMovie(canvas))
                    {
                        invalidate();
                    }
                }

            }else
            {
                if(isPlaying)
                {
                    if (playMovie(canvas)) {
                        isPlaying = false;
                    }
                    invalidate();
                }else
                {
                    movie.setTime(0);
                    movie.draw(canvas, 0, 0);
                    int offsetW = (bitmapWidth - mStartPlayButton.getWidth()) / 2;
                    int offsetH = (bitmapHeight - mStartPlayButton.getHeight()) / 2;
                    canvas.drawBitmap(mStartPlayButton, offsetW, offsetH, null);
                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (movie != null) {
            setMeasuredDimension(bitmapWidth, bitmapHeight);

        }
    }
    private boolean playMovie(Canvas canvas) {
        long now = SystemClock.uptimeMillis();
        if (mMovieStart == 0) {
            mMovieStart = now;
        }
        int duration = movie.duration();
        if (duration == 0) {
            duration = 1000;
        }
        int relTime = (int) ((now - mMovieStart) % duration);
        movie.setTime(relTime);
        movie.draw(canvas, 0, 0);
        if ((now - mMovieStart) >= duration) {
            mMovieStart = 0;
            return true;
        }
        return false;
    }
    /**
     * get resource id by reflect
     * @param context
     * @param typedArray
     * @return resource id
     */
    private int getResourceId(Context context,TypedArray typedArray)
    {
        int id = 0;
        try{
            Field field = TypedArray.class.getDeclaredField("mValue");
            field.setAccessible(true);
            TypedValue typedValue = (TypedValue)field.get(typedArray);
            id = typedValue.resourceId;
        }catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            if(typedArray != null)
            {
                typedArray.recycle();
            }
        }
        return id;
    }
}
