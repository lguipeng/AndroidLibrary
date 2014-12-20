package com.szu.library.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.szu.library.R;
import com.szu.library.utils.TimeUtils;

/**
 * Created by lgp on 2014/7/26.
 */
public class PullToRefreshHeadLayout extends LinearLayout{

    private View mHeadLayout;
    private ImageView mPullToRefreshView;
    private ImageView mLoadingView;
    private RotateAnimation mPullAnimation;
    private RotateAnimation mReverseAnimation;
    private Animation mLoadingAnimation;
    private TextView mTitle;
    private TextView mSubTitle;
    private String mDate;

    public PullToRefreshHeadLayout(Context context) {
        super(context);
        init();
    }

    public PullToRefreshHeadLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init()
    {
        mHeadLayout = LayoutInflater.from(getContext()).inflate(R.layout.pull_to_refresh_header,null);
        mPullToRefreshView = (ImageView) mHeadLayout.findViewById(R.id.pull_to_refresh_arrow);
        mLoadingView = (ImageView) mHeadLayout.findViewById(R.id.pull_to_refresh_loading);
        mTitle = (TextView) mHeadLayout.findViewById(R.id.pull_to_refresh_text);
        mSubTitle = (TextView) mHeadLayout.findViewById(R.id.pull_to_refresh_sub_text);

        setGravity(Gravity.CENTER_HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        addView(mHeadLayout, params);

        mPullAnimation = new android.view.animation.RotateAnimation(0, -180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mPullAnimation.setInterpolator(new LinearInterpolator());
        mPullAnimation.setDuration(250);
        mPullAnimation.setFillAfter(true);

        mReverseAnimation = new android.view.animation.RotateAnimation(-180, 0,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mReverseAnimation.setInterpolator(new LinearInterpolator());
        mReverseAnimation.setDuration(250);
        mReverseAnimation.setFillAfter(true);

        mLoadingAnimation = AnimationUtils.loadAnimation(getContext(),R.anim.loading_animation);

    }

    public void onPullView(int value,boolean isShowReverseAnimation)
    {
        mPullToRefreshView.setVisibility(VISIBLE);
        mLoadingView.clearAnimation();
        mLoadingView.setVisibility(GONE);
        mTitle.setVisibility(VISIBLE);
        mTitle.setText(R.string.pull_to_refresh);
        mDate = TimeUtils.getTime();
        mSubTitle.setVisibility(VISIBLE);
        mSubTitle.setText("更新于: "+mDate);
        if (isShowReverseAnimation)
        {
            mPullToRefreshView.clearAnimation();
            mPullToRefreshView.startAnimation(mReverseAnimation);
        }
        setPadding(0,value,0,0);
    }

    public void onReleaseView(int value,boolean isShowPullAnimation)
    {

        mPullToRefreshView.setVisibility(VISIBLE);
        mLoadingView.setVisibility(GONE);
        mTitle.setVisibility(VISIBLE);
        mTitle.setText(R.string.release_to_refresh);
        mSubTitle.setVisibility(VISIBLE);
        mDate = TimeUtils.getTime();
        mSubTitle.setText("更新于: "+mDate);
        if(isShowPullAnimation)
        {
            mPullToRefreshView.clearAnimation();
            mPullToRefreshView.startAnimation(mPullAnimation);
        }

       setPadding(0,value,0,0);
    }

    public void onDoneView(int mHeadHeight)
    {
        mPullToRefreshView.clearAnimation();
        mLoadingView.clearAnimation();
        mLoadingView.setVisibility(GONE);
        mPullToRefreshView.setVisibility(GONE);
        mTitle.setVisibility(GONE);
        mSubTitle.setVisibility(GONE);
        setPadding(0, mHeadHeight ,0 , 0);
    }

    public void onRefreshingView()
    {
        mPullToRefreshView.setVisibility(GONE);
        mPullToRefreshView.clearAnimation();
        mLoadingView.setVisibility(VISIBLE);
        mLoadingView.clearAnimation();
        mLoadingView.startAnimation(mLoadingAnimation);
        mSubTitle.setVisibility(GONE);
        mTitle.setVisibility(VISIBLE);
        mTitle.setText(R.string.refreshing);
        setPadding(0,0,0,0);
    }

    public int getViewHeight()
    {
        return getMeasuredHeight();
    }
}
