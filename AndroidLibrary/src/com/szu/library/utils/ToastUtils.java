package com.szu.library.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by lgp on 2014/8/27.
 */
public class ToastUtils {
    public static void show(Context mContext,String message)
    {
        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
    }
}
