package com.szu.library.pulltorefresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import com.szu.library.ListViewBase;
import com.szu.library.R;
import com.szu.library.utils.Logger;

/**
 * Created by lgp on 2014/7/26.
 */
public class PullToRefreshListView extends ListViewBase {

    private final String TAG = "PullToRefreshListView";
    private final int RATIO = 2;
    private PullToRefreshHeadLayout mHeadLayout;
    private PullToRefreshFootLayout mFootLayout;
    private State mHeadState = State.getDefault();
    private State mFootState = State.getDefault();
    private Mode mode;
    private OnRefreshListener mOnRefreshListener;
    private int mStartY;
    private int mLastY;
    private boolean isShowPullAnimation = false;
    private boolean isShowReverseAnimation = false;
    private int mHeadHeight;
    private int mMinusHeadHeight;
    private boolean mIsAddFoot = false;
    public PullToRefreshListView(Context context) {
        super(context);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init(AttributeSet attrs) {

        if(attrs != null)
        {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.PullToRefreshListView);
            int m = array.getInt(R.styleable.PullToRefreshListView_ptrMode,0);
            setMode(Mode.mapIntToValue(m));
            if(DEBUG)
            {
                Logger.getInstance().debug(TAG,"MODE = "+mode);
            }
        }else
        {
            mode = Mode.getDefault();
        }
        mHeadLayout = new PullToRefreshHeadLayout(getContext(),attrs);
        measureView(mHeadLayout);
        addHeaderView(mHeadLayout);
        mFootLayout = new PullToRefreshFootLayout(getContext(),attrs);
        measureView(mFootLayout);

        mHeadHeight = mHeadLayout.getViewHeight();
        mMinusHeadHeight = -1 * mHeadHeight;
        mHeadLayout.setPadding(0 , mMinusHeadHeight ,0 , 0);
        mHeadLayout.invalidate();

        if(DEBUG)
        {
            Logger.getInstance().debug(TAG," addHeaderView height "+ mHeadLayout.getViewHeight());
            Logger.getInstance().debug(TAG," addFooterView height "+ mFootLayout.getMeasuredHeight());
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        super.onScroll(view,firstVisibleItem,visibleItemCount,totalItemCount);

        if(isBottom() && !mIsAddFoot)
        {
            mIsAddFoot = true;
            mFootLayout.initView(getMode());
            addFooterView(mFootLayout);
            mFootLayout.setPadding(0,0,0,0);
            mFootLayout.invalidate();
            mFootLayout.setClickToRefreshListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(mFootState != State.REFRESHING)
                        {
                            mFootLayout.onRefreshing(getMode());
                            mFootState = State.REFRESHING;
                            onRefreshingEvent(Orientation.FROM_END);
                        }
                    }
            });
        }

        if(isBottom() && mIsAddFoot && getMode() == Mode.AUTO_REFRESH_IN_END)
        {
            if(mFootState == State.DONE){
                mFootLayout.onRefreshing(Mode.AUTO_REFRESH_IN_END);
                mFootState = State.REFRESHING;
                onRefreshingEvent(Orientation.FROM_END);
            }

        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        super.onScrollStateChanged(view,scrollState);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(true)
        {
            switch (ev.getAction())
            {
                case MotionEvent.ACTION_DOWN: onDown(ev);break;
                case MotionEvent.ACTION_MOVE: onMove(ev);break;
                case MotionEvent.ACTION_UP: onUp(ev);break;
                default:break;
            }
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onDown(MotionEvent e) {

        if(DEBUG)
        {
            Logger.getInstance().debug(TAG,"isTop -->"+isTop());
        }
        if(isTop())
        {
            mStartY = (int)e.getY();
        }
    }

    @Override
    protected void onMove(MotionEvent e) {
        if(isTop())
        {
            mLastY = (int)e.getY();
            int newValue = (mLastY - mStartY ) / RATIO;
            int paddingValue = newValue + mMinusHeadHeight;
            switch (mHeadState)
            {
                case PULL_TO_REFRESH:
                    setSelection(0);
                    mHeadLayout.setVisibility(VISIBLE);
                    if( newValue > mHeadHeight )
                    {
                        mHeadState = State.RELEASE_TO_REFRESH;
                        isShowPullAnimation = true;
                    }
                    updateHeadView(mHeadState, paddingValue);
                    isShowPullAnimation = false;
                    break;
                case RELEASE_TO_REFRESH:
                    setSelection(0);
                    if( newValue <= mHeadHeight )
                    {
                        mHeadState = State.PULL_TO_REFRESH;
                        isShowReverseAnimation = true;
                    }
                    updateHeadView(mHeadState, paddingValue);
                    isShowReverseAnimation = false;
                    break;
                case REFRESHING:
                    break;
                case DONE:
                    if(mLastY - mStartY >0)
                    {
                        mHeadState = State.PULL_TO_REFRESH;
                        updateHeadView(State.PULL_TO_REFRESH,paddingValue);
                    }
                    break;
            }
        }
    }

    @Override
    protected void onUp(MotionEvent e) {
        if(mHeadState != State.REFRESHING)
        {
            if(mHeadState == State.RELEASE_TO_REFRESH)
            {
                mHeadState = State.REFRESHING;
                updateHeadView(State.REFRESHING, 0);
                onRefreshingEvent(Orientation.FROM_START);
            }else if(mHeadState == State.PULL_TO_REFRESH)
            {
                mHeadState = State.DONE;
                updateHeadView(State.DONE, 0);
            }
        }

    }

    private void updateHeadView(State mState,int value)
    {
        if(DEBUG)
        {
            Logger.getInstance().debug(TAG,"value-->"+value);
        }
        switch (mState)
        {
            case PULL_TO_REFRESH:
                 mHeadLayout.onPullView(value,isShowReverseAnimation);
                 break;
            case RELEASE_TO_REFRESH:
                 mHeadLayout.onReleaseView(value,isShowPullAnimation);
                 break;
            case REFRESHING:
                 mHeadLayout.onRefreshingView();
                 break;
            case DONE:
                mHeadLayout.onDoneView(mMinusHeadHeight);
                break;
        }
    }

    private void measureView(View child) {

        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
                    MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    public boolean setOnRefreshDone()
    {

        if(mHeadState != State.REFRESHING && mFootState != State.REFRESHING)
        {
            return false;
        }
        if(mHeadState == State.REFRESHING)
        {
            mHeadState = State.DONE;
            updateHeadView(State.DONE,0);
            onRefreshCompleteEvent();
        }

        if(mFootState == State.REFRESHING)
        {
            mFootState = State.DONE;
            mFootLayout.onRefreshDone(getMode());
            onRefreshCompleteEvent();
        }
        return true;
    }

    public boolean setOnManualRefresh()
    {
        if(mHeadState == State.REFRESHING)
        {
            return false;
        }else
        {
            mHeadState = State.REFRESHING;
            updateHeadView(mHeadState, 0);
            onRefreshingEvent(Orientation.FROM_START);
        }
        return true;
    }

    private void onRefreshingEvent(Orientation orientation)
    {
        if(mOnRefreshListener != null)
        {
            mOnRefreshListener.OnRefresh(orientation);
        }
    }

    private void onRefreshCompleteEvent()
    {
        if(mOnRefreshListener != null)
        {
            mOnRefreshListener.OnRefreshComplete();
        }
    }

    public void setOnRefreshListener(OnRefreshListener mOnRefreshListener) {
        this.mOnRefreshListener = mOnRefreshListener;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public static interface  OnRefreshListener{

        void OnRefresh(Orientation orientation);

        //you must not allow to call setOnRefreshDone in this method when implements this interface
        void OnRefreshComplete();
    }

    public static enum State {

        PULL_TO_REFRESH(0x1),

        RELEASE_TO_REFRESH(0x2),

        REFRESHING(0x3),

        DONE(0x4);

        private int mIntValue;

        State(int intValue) {
            mIntValue = intValue;
        }
        static State mapIntToValue(final int stateInt) {
            for (State value : State.values()) {
                if (stateInt == value.getIntValue()) {
                    return value;
                }
            }
            // If run here, return default
            return DONE;
        }

        static State getDefault()
        {
            return DONE;
        }
        public int getIntValue() {
            return mIntValue;
        }
    }

    public static enum Mode{

        NORMAL(0x0),

        AUTO_REFRESH_IN_END(0x1);

        private int mValue;
        Mode(int value){
            this.mValue = value;
        }
        static Mode getDefault()
        {
            return NORMAL;
        }
        static Mode mapIntToValue(final int stateInt) {
            for (Mode value : Mode.values()) {

                if (stateInt == value.getIntValue()) {
                    Logger.getInstance().debug("Mode value","return mode -->"+value.getIntValue());
                    return value;
                }
            }
            // If run here, return default
            return getDefault();
        }
        public int getIntValue() {
            return mValue;
        }

    }

    public static enum Orientation{
        FROM_START,
        FROM_END;
    }
}
