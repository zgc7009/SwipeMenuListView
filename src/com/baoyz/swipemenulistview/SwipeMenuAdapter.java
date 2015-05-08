package com.baoyz.swipemenulistview;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.WrapperListAdapter;

import com.baoyz.swipemenulistview.SwipeMenuItemView.OnMenuItemClickListener;
import com.baoyz.swipemenulistview.SwipeMenuActionView.OnMenuActionSwipeListener;

/**
 * 
 * @author baoyz
 * @date 2014-8-24
 * 
 */
public class SwipeMenuAdapter implements WrapperListAdapter, OnMenuItemClickListener, OnMenuActionSwipeListener {

    private ListAdapter mAdapter;

	private Context mContext;
	private OnMenuItemClickListener mOnMenuItemClickListener;
    private OnMenuActionSwipeListener mOnMenuActionSwipeListener;

	public SwipeMenuAdapter(Context context, ListAdapter adapter, SwipeMenuItemView.OnMenuItemClickListener onMenuItemClickListener,
                            SwipeMenuActionView.OnMenuActionSwipeListener onMenuActionSwipeListener) {
		mAdapter = adapter;
		mContext = context;
        mOnMenuItemClickListener = onMenuItemClickListener;
        mOnMenuActionSwipeListener = onMenuActionSwipeListener;
	}

	@Override
	public int getCount() {
		return mAdapter.getCount();
	}

	@Override
	public Object getItem(int position) {
		return mAdapter.getItem(position);
	}

	@Override
	public long getItemId(int position) {
		return mAdapter.getItemId(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SwipeMenuLayout layout;

		if (convertView == null) {
			View contentView = mAdapter.getView(position, convertView, parent);	// Will get our ListView item's convertView as our contentView

			// Will create a SwipeMenu
			SwipeMenu menu = new SwipeMenu(mContext);
			menu.setViewType(mAdapter.getItemViewType(position));
			createMenu(menu);                               // create menu

			// Manage SwipeMenuView
			SwipeMenuItemView menuView = new SwipeMenuItemView(menu);
			menuView.setOnMenuItemClickListener(this);

            // Manage SwipeActionView
            SwipeMenuActionView actionView = new SwipeMenuActionView(menu);
            actionView.setOnSwipeActionSwipeListener(this);

			// This will create a SwipeMenuListView with the contentView being the main item in the list and the menuView being the overlay
			layout = new SwipeMenuLayout(contentView, menuView, actionView);
			layout.setPosition(position);
		} else {
			layout = (SwipeMenuLayout) convertView;
			layout.closeMenu();
			layout.setPosition(position);
		}

		return layout;
	}

	/**
	 * Will create our swipe menu
	 * TODO this is what we need to modify for our app
	 *
	 * @param menu
	 */
	public void createMenu(SwipeMenu menu) {}

	@Override
	public void onItemClick(int adapterPosition, SwipeMenu menu, int index) {
        if (mOnMenuItemClickListener != null)
            mOnMenuItemClickListener.onItemClick(adapterPosition, menu, index);
    }

    @Override
    public void onActionSwipe(int adapterPosition) {
        if (mOnMenuActionSwipeListener != null)
            mOnMenuActionSwipeListener.onActionSwipe(adapterPosition);
    }

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		mAdapter.registerDataSetObserver(observer);
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		mAdapter.unregisterDataSetObserver(observer);
	}

	@Override
	public boolean areAllItemsEnabled() {
		return mAdapter.areAllItemsEnabled();
	}

	@Override
	public boolean isEnabled(int position) {
		return mAdapter.isEnabled(position);
	}

	@Override
	public boolean hasStableIds() {
		return mAdapter.hasStableIds();
	}

	@Override
	public int getItemViewType(int position) {
		return mAdapter.getItemViewType(position);
	}

	@Override
	public int getViewTypeCount() {
		return mAdapter.getViewTypeCount();
	}

	@Override
	public boolean isEmpty() {
		return mAdapter.isEmpty();
	}

	@Override
	public ListAdapter getWrappedAdapter() {
		return mAdapter;
	}

}
