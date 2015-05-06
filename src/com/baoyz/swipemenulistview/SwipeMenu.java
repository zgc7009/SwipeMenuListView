package com.baoyz.swipemenulistview;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

/**
 * 
 * @author baoyz
 * @date 2014-8-23
 * 
 */
public class SwipeMenu {

	private Context mContext;
	private List<SwipeMenuItem> mItems;
    private SwipeMenuItem mAction;
	private int mViewType;

	public SwipeMenu(Context context) {
		mContext = context;
		mItems = new ArrayList<>();
	}

	public Context getContext() {
		return mContext;
	}

	public void addMenuItem(SwipeMenuItem item) {
		mItems.add(item);
	}

	public void removeMenuItem(SwipeMenuItem item) {
		mItems.remove(item);
	}

	public List<SwipeMenuItem> getMenuItems() {
		return mItems;
	}

	public SwipeMenuItem getMenuItem(int index) {
		return mItems.get(index);
	}


    public void addAction(SwipeMenuItem action){
        mAction = action;
    }

    public void removeAction(){
        mAction = null;
    }

    public SwipeMenuItem getAction(){
        return mAction;
    }

	public int getViewType() {
		return mViewType;
	}

	public void setViewType(int viewType) {
		this.mViewType = viewType;
	}

}
