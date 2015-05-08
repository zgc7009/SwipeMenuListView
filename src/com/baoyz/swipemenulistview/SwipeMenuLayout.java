package com.baoyz.swipemenulistview;

import android.graphics.Color;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.ScrollerCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;

/**
 * 
 * @author baoyz
 * @date 2014-8-23
 * 
 */
public class SwipeMenuLayout extends FrameLayout {

    public static final boolean ALLOW_ICONS_IN_SWIPE_MENU = false;

	private static final int CONTENT_VIEW_ID = 1;
	private static final int MENU_VIEW_ID = 2;
    private static final int ACTION_VIEW_ID = 3;

	private static final int STATE_CLOSE = 0;
	private static final int STATE_PARTIALLY_OPEN = 1;
    //private static final int STATE_FULLY_OPEN = 2;

	private int MIN_FLING = dp2px(15);
	private int MAX_VELOCITYX = -dp2px(500);
    private int FULL_OPEN_BUFFER = dp2px(100);

	private View mContentView;
	private SwipeMenuItemView mMenuView;
    private int mMenuViewMaxWidth = 0;
    private SwipeMenuActionView mActionView;
	private int mDownX;
	private int state = STATE_CLOSE;
	private GestureDetectorCompat mGestureDetector;
	private OnGestureListener mGestureListener;
	private boolean isFling;
	private ScrollerCompat mOpenScroller;
	private ScrollerCompat mCloseScroller;
	private int mBaseX;
	private int position;

	public SwipeMenuLayout(View contentView, SwipeMenuItemView menuView, SwipeMenuActionView actionView) {
		super(contentView.getContext());
		mContentView = contentView;
		mMenuView = menuView;
		mMenuView.setLayout(this);
        mActionView = actionView;
        mActionView.setLayout(this);
		init();
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
		mMenuView.setPosition(position);
        mActionView.setPosition(position);
	}

	private void init() {
		setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		mGestureListener = new SimpleOnGestureListener() {
			@Override
			public boolean onDown(MotionEvent e) {
				isFling = false;
				return true;
			}

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
				// TODO
				if ((e1.getX() - e2.getX()) > MIN_FLING && velocityX < MAX_VELOCITYX) {
					//isFling = true;
				}
				// Log.i("byz", MAX_VELOCITYX + ", velocityX = " + velocityX);
				return super.onFling(e1, e2, velocityX, velocityY);
			}
		};
		mGestureDetector = new GestureDetectorCompat(getContext(), mGestureListener);

		mCloseScroller = ScrollerCompat.create(getContext());
		mOpenScroller = ScrollerCompat.create(getContext());

		LayoutParams contentParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		mContentView.setLayoutParams(contentParams);
		if (mContentView.getId() < 1) {
			mContentView.setId(CONTENT_VIEW_ID);
		}

		mMenuView.setId(MENU_VIEW_ID);
		mMenuView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));

        mActionView.setId(ACTION_VIEW_ID);
        mActionView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT));

		addView(mContentView);
		addView(mMenuView);
        addView(mActionView);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
	}

	//TODO this is what we need to modify for our swipe event
	public boolean onSwipe(MotionEvent event) {
		mGestureDetector.onTouchEvent(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:

			mDownX = (int) event.getX();
			isFling = false;
			break;
		case MotionEvent.ACTION_MOVE:
			// Log.i("byz", "downX = " + mDownX + ", moveX = " + event.getX());
			int dis = (int) (mDownX - event.getX());

			if (state == STATE_PARTIALLY_OPEN) {
				dis += mMenuView.getWidth();
			}
			swipe(dis);
			break;
		case MotionEvent.ACTION_UP:

            // if we aren't flinging AND scrolling past the extent of our menu view
            // fully open
            if(!isFling && (mDownX - event.getX()) > mMenuView.getWidth())
                smoothPerformAction();

            // else if we are flinging OR scrolling and have scrolled at least half the width of the menu view
            // partially open
			else if (isFling || (mDownX - event.getX()) > (mMenuView.getWidth() / 2))
                smoothPartiallyOpenMenu();

            // else, close
            else {
				smoothCloseMenu();
				return false;
			}
			break;
		}
		return true;
	}

	public boolean isOpen() {
		return //state == STATE_FULLY_OPEN ||
		        state == STATE_PARTIALLY_OPEN;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}

    private void swipe(int dis) {
        boolean fullyOpen = false;
        if (dis > mMenuView.getWidth() && state != STATE_PARTIALLY_OPEN) {
            fullyOpen = true;
            if(dis > mContentView.getWidth() - FULL_OPEN_BUFFER)
                dis = mContentView.getWidth() - FULL_OPEN_BUFFER;
        }
        else if (dis > mMenuView.getWidth())            // keep smoothPartiallyOpenMenu from opening too far
            dis = mMenuView.getWidth();
		else if (dis < 0)                               // keep from scrolling right
			dis = 0;

		mContentView.layout(-dis, mContentView.getTop(),
				mContentView.getWidth() - dis, getMeasuredHeight());

        if(!fullyOpen) {
            mMenuView.layout(mContentView.getWidth() - dis, mMenuView.getTop(),
                    mContentView.getWidth() + mMenuView.getWidth() - dis,
                    mMenuView.getBottom());
            mActionView.layout(mContentView.getWidth(), mActionView.getTop(),
                    mContentView.getWidth() - mActionView.getWidth(), mActionView.getBottom());
        }
        else{
            mMenuView.layout(mContentView.getWidth(), mMenuView.getTop(),
                    mContentView.getWidth() + mMenuView.getWidth(), mMenuView.getBottom());
            mActionView.layout(mContentView.getWidth() - dis, mMenuView.getTop(),
                    mContentView.getWidth(), mMenuView.getBottom());
        }
	}

	@Override
	public void computeScroll() {
		if (state == STATE_PARTIALLY_OPEN) {
			if (mOpenScroller.computeScrollOffset()) {
				swipe(mOpenScroller.getCurrX());
				postInvalidate();
			}
		} else {
			if (mCloseScroller.computeScrollOffset()) {
				swipe(mBaseX - mCloseScroller.getCurrX());
				postInvalidate();
			}
		}
	}

	public void smoothCloseMenu() {
        Log.d(getClass().getSimpleName(), "Smooth closing menu");
		state = STATE_CLOSE;

		mBaseX = -mContentView.getLeft();
		mCloseScroller.startScroll(0, 0, mBaseX, 0, 350);
		postInvalidate();
	}

	public void smoothPartiallyOpenMenu() {
        Log.d(getClass().getSimpleName(), "Partially opening");
		state = STATE_PARTIALLY_OPEN;
		mOpenScroller.startScroll(-mContentView.getLeft(), 0,
				mMenuView.getWidth(), 0, 350);
		postInvalidate();
	}

    public void smoothPerformAction(){
        Log.d(getClass().getSimpleName(), "Fully opening");
        mActionView.onClick(mActionView);
        smoothCloseMenu();
    }

	public void closeMenu() {
        Log.d(getClass().getSimpleName(), "Closing menu");
		if (mCloseScroller.computeScrollOffset()) {
			mCloseScroller.abortAnimation();
		}
		if (//state == STATE_FULLY_OPEN ||
            state == STATE_PARTIALLY_OPEN) {
			state = STATE_CLOSE;
			swipe(0);
		}
	}

	public void openMenu() {
		if (state == STATE_CLOSE) {
			state = STATE_PARTIALLY_OPEN;
			swipe(mMenuView.getWidth());
		}
	}

	public View getContentView() {
		return mContentView;
	}

	public SwipeMenuItemView getMenuView() {
		return mMenuView;
	}

	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getContext().getResources().getDisplayMetrics());
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mMenuView.measure(MeasureSpec.makeMeasureSpec(0,
				MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(
				getMeasuredHeight(), MeasureSpec.EXACTLY));
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		mContentView.layout(0, 0, getMeasuredWidth(),
				mContentView.getMeasuredHeight());
		mMenuView.layout(getMeasuredWidth(), 0,
				getMeasuredWidth() + mMenuView.getMeasuredWidth(),
				mContentView.getMeasuredHeight());
		// setMenuHeight(mContentView.getMeasuredHeight());
		// bringChildToFront(mContentView);
	}

	public void setMenuHeight(int measuredHeight) {
		Log.i("byz", "pos = " + position + ", height = " + measuredHeight);
		LayoutParams params = (LayoutParams) mMenuView.getLayoutParams();
		if (params.height != measuredHeight) {
			params.height = measuredHeight;
			mMenuView.setLayoutParams(mMenuView.getLayoutParams());
		}
	}
}
