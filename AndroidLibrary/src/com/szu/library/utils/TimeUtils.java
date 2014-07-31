/**
 * 
 */
package com.szu.library.utils;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
public class TimeUtils {

  @SuppressLint("SimpleDateFormat")
  public static final SimpleDateFormat DEFAULT_FORMAT = new SimpleDateFormat("MM-dd:HH:mm");
  

  public static String getTime(long timeInMillis, SimpleDateFormat dateFormat)
  {
    return dateFormat.format(new Date(timeInMillis));
  }
  
  /**
   * Ĭ�ϵ�ʱ���ʽ
   * @return DEFAULT_FORMAT.format()
   */
  public static String getTime()
  {
    return DEFAULT_FORMAT.format(new Date(getCurrentTime()));
  }
  /**
   * get current time in milliseconds
   * 
   * @return
   */
  public static long getCurrentTime()
  {
    return System.currentTimeMillis();
  }
}
