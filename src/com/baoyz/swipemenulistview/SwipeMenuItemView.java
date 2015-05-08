package com.baoyz.swipemenulistview;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author baoyz
 * @date 2014-8-23
 * 
 */
public class SwipeMenuItemView extends LinearLayout implements OnClickListener {

    public static interface OnMenuItemClickListener {
        void onItemClick(int adapterPosition, SwipeMenu menu, int index);
    }

	private SwipeMenuLayout mLayout;
	private SwipeMenu mMenu;
    private ImageView[] mIcons;
    private TextView[] mTitles;
	private OnMenuItemClickListener mOnItemClickListener;
	private int mPosition;

	public void setPosition(int position) {
		mPosition = position;
	}

	public SwipeMenuItemView(SwipeMenu menu) {
		super(menu.getContext());
		mMenu = menu;
		List<SwipeMenuItem> items = menu.getMenuItems();
        mIcons = new ImageView[items.size()];
        mTitles = new TextView[items.size()];
		int id = 0;
		for (SwipeMenuItem item : items) {
			addItem(item, id++);
		}
	}

	private void addItem(SwipeMenuItem item, int id) {
		LayoutParams params = new LayoutParams(item.getWidth(), LayoutParams.MATCH_PARENT);
		LinearLayout parent = new LinearLayout(getContext());
		parent.setId(id);
		parent.setGravity(Gravity.CENTER);
		parent.setOrientation(LinearLayout.VERTICAL);

		parent.setLayoutParams(params);
		parent.setBackgroundDrawable(item.getBackground());
		parent.setOnClickListener(this);
		addView(parent);

        if (item.getCurrIcon() != null && SwipeMenuLayout.ALLOW_ICONS_IN_SWIPE_MENU) {
            parent.addView(createIcon(item, id));
        }
		if (!TextUtils.isEmpty(item.getCurrTitle())) {
			parent.addView(createTitle(item, id));
		}

	}

	private ImageView createIcon(SwipeMenuItem item, int id) {
		ImageView iv = new ImageView(getContext());
		iv.setImageDrawable(item.getCurrIcon());
        mIcons[id] = iv;
		return iv;
	}

    public void modifyIcon(Drawable icon, int id){
        if(id < mIcons.length && mIcons[id] != null)
            mIcons[id].setImageDrawable(icon);
    }

	private TextView createTitle(SwipeMenuItem item, int id) {
		TextView tv = new TextView(getContext());
		tv.setText(item.getCurrTitle());
		tv.setGravity(Gravity.CENTER);
		tv.setTextSize(item.getTitleSize());
		tv.setTextColor(item.getTitleColor());
        mTitles[id] = tv;
		return tv;
	}

    public void modifyTitle(String text, int id){
        if(id < mTitles.length && mTitles[id] != null)
            mTitles[id].setText(text);
    }

	@Override
	public void onClick(View v) {
		if (mOnItemClickListener != null && mLayout.isOpen()) {
            int id = v.getId();
			mOnItemClickListener.onItemClick(mPosition, mMenu, id);
            SwipeMenuItem item = mMenu.getMenuItem(id);
            modifyIcon(item.getAndSwitchCurrIcon(), id);
            modifyTitle(item.getAndSwitchCurrTitle(), id);
            ((SwipeMenuLayout) getParent()).modifyOnPrimaryAction(false);
		}
	}

    /**
     * When we swipe to trigger action, we need to make sure we modify the status of the correlating button in our
     * item menu. This will be handled by having the SwipeMenuActionView trigger modifyOnPrimaryAction in our
     * parent layout (SwipeMenuLayout) from the view's onClick method (look above for how we handle doing a similar thing
     * for notifying our items that we have performed an action) 
     */
    public void modifyOnActionSwiped(){
        int id = mMenu.getMenuItems().size() - 1;
        SwipeMenuItem item = mMenu.getMenuItem(id);
        modifyIcon(item.getAndSwitchCurrIcon(), id);
        modifyTitle(item.getAndSwitchCurrTitle(), id);
    }

	public void setOnMenuItemClickListener(OnMenuItemClickListener onItemClickListener) {
		this.mOnItemClickListener = onItemClickListener;
	}

	public void setLayout(SwipeMenuLayout mLayout) {
		this.mLayout = mLayout;
	}
}
