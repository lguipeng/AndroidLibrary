package com.szu.library.swipetodismiss;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.szu.library.ListViewBase;
import com.szu.library.R;
import com.szu.library.utils.Logger;

/**
 * Created by lgp on 2014/7/25.
 */
public class SwipeToDismissListView  extends ListViewBase {

    public final String TAG = "SwipeToDismissListView";
    public final static int  DEFAULT_DISMISS_WITH = 30;
    public final static int  DEFAULT_DISMISS_HEIGHT = 20;
    //the enable default is true
    private boolean mEnable = true;
    private State mState = State.getDefault();
    private OnDismissListener mOnDismissListener;
    private OnDismissViewListener mOnDismissViewListener;
    private int mStartY;
    private int mStartX;
    private int mSelectItem;
    private int mDismissWith;
    private int mDismissHeight;
    private ViewGroup mItemLayout;
    private View mDismissView;
    private Button mDismissButton;
    private Animation mShowAnimation;
    private Animation mDisappearAnimation;
    public SwipeToDismissListView(Context context) {
       this(context,null);
    }

    public SwipeToDismissListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    @Override
    protected void init(AttributeSet attrs) {
        if(attrs != null)
        {
            TypedArray array = getContext().obtainStyledAttributes(attrs,R.styleable.SwipeToDismissListView);
            mDismissWith = array.getInteger(R.styleable.SwipeToDismissListView_dismissWith,DEFAULT_DISMISS_WITH);
            mDismissHeight =array.getInteger(R.styleable.SwipeToDismissListView_dismissHeight,DEFAULT_DISMISS_HEIGHT);
            if(DEBUG)
            {
                Logger.getInstance().debug(TAG,"dismiss with-->" + mDismissWith + " dismiss height-->" + mDismissHeight);
            }
        }else
        {
            setDefaultDismissWH();
            if(DEBUG)
            {
                Logger.getInstance().debug(TAG,"default dismiss with-->"+mDismissWith+" default dismiss height-->"+mDismissHeight);
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        super.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        super.onScrollStateChanged(view, scrollState);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(isEnable())
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
        if(mState == State.SHOWING)
        {
            mState = State.DISAPPEARING;
            mDisappearAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.dismiss_view_disappear_animation);
            mDisappearAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    if(DEBUG) {
                        Logger.getInstance().debug(TAG,"onAnimationStart");
                    }
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mState = State.DISAPPEAR;
                    mItemLayout.removeView(mDismissView);
                    mDismissView = null;
                    mDismissButton.clearAnimation();
                    if(DEBUG)
                        Logger.getInstance().debug(TAG,"onAnimationEnd");
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mDismissButton.clearAnimation();
            mDismissButton.setAnimation(mDisappearAnimation);
        }
        mSelectItem = pointToPosition( (int)e.getX() , (int)e.getY() );
        mStartX = (int)e.getX();
        mStartY = (int)e.getY();
    }

    @Override
    protected void onMove(MotionEvent e) {

        if (DEBUG)
        {
            Logger.getInstance().debug(TAG,"X-->"+e.getX()+"  Y-->"+e.getY());
        }
        if (mStartX - e.getX() >= mDismissWith && Math.abs(e.getY() - mStartY) <= mDismissHeight
                && mState != State.DISAPPEARING)
        {
           mState = State.CAN_SHOW;
        }
    }

    @Override
    protected void onUp(MotionEvent e) {

        if (mState == State.CAN_SHOW)
        {
            if(mOnDismissViewListener != null)
            {
                mOnDismissViewListener.onPreShow();
            }
            if (mDismissView == null)
            {
                mDismissView = LayoutInflater.from(getContext()).inflate(R.layout.swipe2dismiss_layout,null);
                mShowAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.dismiss_view_animation);
                mDismissButton = (Button)mDismissView.findViewById(R.id.button);
                mDismissButton.setAnimation(mShowAnimation);
                mDismissButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mState = State.DISAPPEAR;
                        if(mOnDismissListener != null)
                        {
                            mOnDismissListener.onDismiss(mSelectItem);
                        }
                        mItemLayout.removeView(mDismissView);
                        mDismissView = null;
                        mDismissButton.clearAnimation();

                    }
                });
            }
            if (isViewGroup(getChildAt(mSelectItem - getFirstVisiblePosition())))
            {
                mItemLayout =(ViewGroup) getChildAt(mSelectItem - getFirstVisiblePosition());
            }
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            if (mItemLayout != null)
            {
                mItemLayout.addView(mDismissView,params);
                mState = State.SHOWING;
                if(mOnDismissViewListener != null)
                {
                    mOnDismissViewListener.onShow();
                }
            }else
            {
                if (DEBUG)
                {
                    Logger.getInstance().error(TAG,"The Item Layout (View Group) is Null");
                }
            }

        }
    }

    private boolean isViewGroup(View view)
    {
        return (view instanceof ViewGroup)? true: false;
    }

    public boolean isEnable() {
        return mEnable;
    }

    public void setEnable(boolean mEnable) {
        this.mEnable = mEnable;
    }

    private void setDefaultDismissWH()
    {
       setDismissWith(DEFAULT_DISMISS_WITH);
       setDismissHeight(DEFAULT_DISMISS_HEIGHT);
    }

    public void setDismissWith(int dismissWith) {
        this.mDismissWith = dismissWith;
    }

    public void setDismissHeight(int dismissHeight) {
        this.mDismissHeight = dismissHeight;
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }

    public void setOnDismissViewListener(OnDismissViewListener mOnDismissViewListener) {
        this.mOnDismissViewListener = mOnDismissViewListener;
    }

    public static interface OnDismissListener
    {
        public void onDismiss(int index);
    }

    public static  interface OnDismissViewListener
    {
        public void onShow();
        public void onPreShow();
    }

    public static enum  State{

        //the dismiss view is showing now
        SHOWING(0x0),
        //the dismiss view disappear
        DISAPPEAR(0x1),
        //the dismiss view can show now
        CAN_SHOW(0X2),
        //ths dismiss view is disappear now
        DISAPPEARING(0x3);

        private int mValue;

        State(int value){
            mValue = value;
        }
        int getValue() {
            return mValue;
        }
        static State mapIntToValue(int i)
        {
            for(State state : State.values())
            {
                if(state.getValue() == i)
                {
                    return state;
                }
            }
            //if run here, just return default
            return getDefault();
        }
        static State getDefault(){
            return DISAPPEAR;
        }
    }
}
