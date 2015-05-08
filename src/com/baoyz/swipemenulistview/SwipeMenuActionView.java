package com.baoyz.swipemenulistview;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zach on 5/6/15.
 */
public class SwipeMenuActionView extends LinearLayout implements View.OnClickListener {

    public static interface OnMenuActionSwipeListener {
        void onActionSwipe(int adapterPosition);
    }

    private SwipeMenuLayout mLayout;
    private SwipeMenu mMenu;
    private OnMenuActionSwipeListener onActionClickListener;
    private int position;

    public void setPosition(int position) {
        this.position = position;
    }

    public SwipeMenuActionView(SwipeMenu menu) {
        super(menu.getContext());
        mMenu = menu;
        if(menu.getAction() != null)
            addAction(menu.getAction(), 999);
        else
            Log.e(getClass().getSimpleName(), "Error loading menu action");
    }

    private void addAction(SwipeMenuItem item, int id) {
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
            parent.addView(createTitle(item));
        }

    }

    private ImageView createIcon(SwipeMenuItem item) {
        ImageView iv = new ImageView(getContext());
        iv.setImageDrawable(item.getIcon());
        return iv;
    }

    private TextView createTitle(SwipeMenuItem item) {
        TextView tv = new TextView(getContext());
        tv.setText(item.getTitle());
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(item.getTitleSize());
        tv.setTextColor(item.getTitleColor());
        return tv;
    }

    @Override
    public void onClick(View v) {
        if (onActionClickListener != null) {
            onActionClickListener.onActionSwipe(position); //this , mMenu, v.getId());
        }
    }

    public void setOnSwipeActionSwipeListener(OnMenuActionSwipeListener onActionListener) {
        this.onActionClickListener = onActionListener;
    }

    public void setLayout(SwipeMenuLayout mLayout) {
        this.mLayout = mLayout;
    }

}
