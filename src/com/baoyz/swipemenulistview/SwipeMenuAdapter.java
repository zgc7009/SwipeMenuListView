package com.baoyz.swipemenulistview;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.WrapperListAdapter;

import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.baoyz.swipemenulistview.SwipeMenuView.OnSwipeItemClickListener;
import com.baoyz.swipemenulistview.SwipeActionView.OnMenuActionClickListener;

/**
 * 
 * @author baoyz
 * @date 2014-8-24
 * 
 */
public class SwipeMenuAdapter implements WrapperListAdapter, OnSwipeItemClickListener, OnMenuActionClickListener {

    private ListAdapter mAdapter;

	private Context mContext;
	private OnMenuItemClickListener onMenuItemClickListener;
    private OnMenuActionClickListener onActionClickListener;

	public SwipeMenuAdapter(Context context, ListAdapter adapter) {
		mAdapter = adapter;
		mContext = context;
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
            createMenuAction(menu);      // attach action to menu

			// Manage SwipeMenuView
			SwipeMenuView menuView = new SwipeMenuView(menu);
			menuView.setOnSwipeItemClickListener(this);

            // Manage SwipeActionView
            SwipeActionView actionView = new SwipeActionView(menu);
            actionView.setOnSwipeActionClickListener(this);

			// This will create a SwipeMenuListView with the contentView being the main item in the list and the menuView being the overlay
			SwipeMenuListView listView = (SwipeMenuListView) parent;
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
	public void createMenu(SwipeMenu menu) {
		// Test Code
		SwipeMenuItem item = new SwipeMenuItem(mContext);
		item.setTitle("Item 1");
		item.setBackground(new ColorDrawable(Color.GRAY));
		menu.addMenuItem(item);

		item = new SwipeMenuItem(mContext);
		item.setTitle("Item 2");
		item.setBackground(new ColorDrawable(Color.RED));
		menu.addMenuItem(item);
	}

    public void createMenuAction(SwipeMenu menu){
        // Test Code
        SwipeMenuItem action = new SwipeMenuItem(mContext);
        action.setTitle("Action");
        action.setBackground(new ColorDrawable(Color.BLUE));
        menu.addAction(action);
    }

	@Override
	public void onItemClick(SwipeMenuView view, SwipeMenu menu, int index) {
		if (onMenuItemClickListener != null) {
			onMenuItemClickListener.onMenuItemClick(view.getPosition(), menu,
					index);
		}
	}

    @Override
    public void onActionClick() {

    }

	public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
		this.onMenuItemClickListener = onMenuItemClickListener;
	}

    public void setOnSwipeActionClickListener(OnMenuActionClickListener onActionClickListener){
        this.onActionClickListener = onActionClickListener;
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
