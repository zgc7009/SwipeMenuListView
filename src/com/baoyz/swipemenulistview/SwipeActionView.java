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
public class SwipeActionView extends LinearLayout implements View.OnClickListener {

    private SwipeMenuLayout mLayout;
    private SwipeMenu mMenu;
    private OnMenuActionClickListener onActionClickListener;

    public SwipeActionView(SwipeMenu menu) {
        super(menu.getContext());
        mMenu = menu;
        if(menu.getAction() != null)
            addAction(menu.getAction(), 999);
        else
            Log.e(getClass().getSimpleName(), "Error loading menu action");
    }

    private void addAction(SwipeMenuItem item, int id) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(item.getWidth(), LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout parent = new LinearLayout(getContext());
        parent.setId(id);
        parent.setGravity(Gravity.CENTER);
        parent.setOrientation(LinearLayout.VERTICAL);

        parent.setLayoutParams(params);
        parent.setBackgroundDrawable(item.getBackground());
        parent.setOnClickListener(this);
        addView(parent);

        if (item.getIcon() != null) {
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
        if (onActionClickListener != null && mLayout.isOpen()) {
            onActionClickListener.onActionClick(); //this , mMenu, v.getId());
        }
    }

    public OnMenuActionClickListener getOnSwipeActionClickListener() {
        return onActionClickListener;
    }

    public void setOnSwipeActionClickListener(OnMenuActionClickListener onActionListener) {
        this.onActionClickListener = onActionListener;
    }

    public void setLayout(SwipeMenuLayout mLayout) {
        this.mLayout = mLayout;
    }

    public static interface OnMenuActionClickListener {
        void onActionClick();
    }
}
