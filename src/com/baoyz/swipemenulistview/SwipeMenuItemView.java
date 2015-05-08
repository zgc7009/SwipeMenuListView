package com.baoyz.swipemenulistview;

import java.util.List;

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
    private TextView[] tv;
	private OnMenuItemClickListener onItemClickListener;
	private int position;

	public void setPosition(int position) {
		this.position = position;
	}

	public SwipeMenuItemView(SwipeMenu menu) {
		super(menu.getContext());
		mMenu = menu;
		List<SwipeMenuItem> items = menu.getMenuItems();
        tv = new TextView[items.size()];
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

        if (item.getIcon() != null && SwipeMenuLayout.ALLOW_ICONS_IN_SWIPE_MENU) {
            parent.addView(createIcon(item));
        }
		if (!TextUtils.isEmpty(item.getTitle())) {
			parent.addView(createTitle(item, id));
		}

	}

	private ImageView createIcon(SwipeMenuItem item) {
		ImageView iv = new ImageView(getContext());
		iv.setImageDrawable(item.getIcon());
		return iv;
	}

	private TextView createTitle(SwipeMenuItem item, int id) {
		TextView tv = new TextView(getContext());
		tv.setText(item.getTitle());
		tv.setGravity(Gravity.CENTER);
		tv.setTextSize(item.getTitleSize());
		tv.setTextColor(item.getTitleColor());
        this.tv[id] = tv;
		return tv;
	}

    public void modifyTitle(String text, int id){
        if(tv[id] != null)
            tv[id].setText(text);
    }

	@Override
	public void onClick(View v) {
		if (onItemClickListener != null && mLayout.isOpen()) {
			onItemClickListener.onItemClick(position, mMenu, v.getId());
		}
	}

	public void setOnMenuItemClickListener(OnMenuItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	public void setLayout(SwipeMenuLayout mLayout) {
		this.mLayout = mLayout;
	}
}
