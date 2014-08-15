package com.szu.library;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.AbsListView;
import android.widget.ListView;
import com.szu.library.utils.Logger;

/**
 * Created by lgp on 2014/7/25.
 */
public abstract class ListViewBase extends ListView implements AbsListView.OnScrollListener{

    public final boolean DEBUG = true;
    private final String TAG = "ListViewBase";
    private boolean mIsBottom;
    private boolean mIsTop;

    public ListViewBase(Context context) {
        this(context,null);
    }

    public ListViewBase(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
        setupListener();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        if(false)
        {
            Logger.getInstance().debug(TAG,"First Visible: " + firstVisibleItem + ". Visible Count: " + visibleItemCount
                    + ". Total Items:" + totalItemCount);
        }
        if(false)
        {
            Logger.getInstance().debug(TAG,". Visible Count: " + getLastVisiblePosition()
                    + ". Total Items:" + totalItemCount);
        }

        if(firstVisibleItem == 0 || totalItemCount == 0)
        {
            mIsTop = true;
        }else
        {
            mIsTop = false;
        }
        if((getLastVisiblePosition() == (totalItemCount -1) ) && firstVisibleItem != 0)
        {
                mIsBottom = true;
        }else
        {
            mIsBottom = false;

        }
    }

    public boolean isBottom() {
        return mIsBottom;
    }

    public boolean isTop() {
        return mIsTop;
    }

    private void setupListener()
    {
        setOnScrollListener(this);
        Logger.getInstance().debug(TAG,"on init");
    }
    protected abstract void init(AttributeSet attrs);
    protected abstract  void onDown(MotionEvent e);
    protected abstract  void onMove(MotionEvent e);
    protected abstract  void onUp(MotionEvent e);
}
