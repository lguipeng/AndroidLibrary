package com.szu.library.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.szu.library.R;

/**
 * Created by lgp on 2014/7/27.
 */
public class PullToRefreshFootLayout extends LinearLayout {
    private View mFootLayout;
    private Button mGetMoreButton;
    private ImageView mLoadingImage;
    private TextView mLoadingText;
    private Animation mLoadingAnimation;
    public PullToRefreshFootLayout(Context context) {
        super(context);
        init(null);
    }

    public PullToRefreshFootLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }
    private void init(AttributeSet attrs)
    {
        mFootLayout = LayoutInflater.from(getContext()).inflate(R.layout.pull_to_refresh_footer,null);
        mGetMoreButton = (Button)mFootLayout.findViewById(R.id.click_to_refresh);
        mLoadingImage = (ImageView)mFootLayout.findViewById(R.id.pull_to_refresh_loading);
        mLoadingText = (TextView)mFootLayout.findViewById(R.id.pull_to_refresh_text);
        mLoadingAnimation = AnimationUtils.loadAnimation(getContext(),R.anim.loading_animation);
        addView(mFootLayout,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));


    }

    public void onRefreshing(PullToRefreshListView.Mode mode)
    {
        mGetMoreButton.setVisibility(GONE);
        mLoadingImage.setVisibility(VISIBLE);
        mLoadingText.setVisibility(VISIBLE);
        mLoadingImage.clearAnimation();
        mLoadingImage.startAnimation(mLoadingAnimation);
    }
    public void onRefreshDone(PullToRefreshListView.Mode mode)
    {
        if(mode != PullToRefreshListView.Mode.AUTO_REFRESH_IN_END)
            mGetMoreButton.setVisibility(VISIBLE);
        mLoadingImage.setVisibility(GONE);
        mLoadingText.setVisibility(GONE);
        mLoadingImage.clearAnimation();
    }

    public void initView(PullToRefreshListView.Mode mode)
    {
        if(mode == PullToRefreshListView.Mode.AUTO_REFRESH_IN_END)
        {
            mGetMoreButton.setVisibility(GONE);
        }else
        {
            mGetMoreButton.setVisibility(VISIBLE);
        }
    }
    public void setClickToRefreshListener(OnClickListener listener)
    {
        mGetMoreButton.setOnClickListener(listener);
    }
}
