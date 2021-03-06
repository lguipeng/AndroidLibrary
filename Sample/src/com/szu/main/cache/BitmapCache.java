package com.szu.main.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.LruCache;
import com.android.volley.toolbox.ImageLoader;

/**
 * Created by lgp on 2014/8/4.
 */
public class BitmapCache extends LruCache<String,Bitmap> implements ImageLoader.ImageCache{

    public BitmapCache(int maxSize) {
        super(maxSize);
    }

    public BitmapCache(Context context)
    {
        super(getCacheSize(context));
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight();
    }

    @Override
    public Bitmap getBitmap(String url) {
        return  get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }

    // Returns a cache size equal to approximately three screens worth of images.
    public static int getCacheSize(Context ctx) {
        final DisplayMetrics displayMetrics = ctx.getResources().
                getDisplayMetrics();
        final int screenWidth = displayMetrics.widthPixels;
        final int screenHeight = displayMetrics.heightPixels;
        // 4 bytes per pixel
        final int screenBytes = screenWidth * screenHeight * 4;
        return screenBytes * 3;
    }
}
