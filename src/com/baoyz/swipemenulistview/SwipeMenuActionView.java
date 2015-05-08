package com.baoyz.swipemenulistview;

import android.graphics.drawable.Drawable;
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

    private ImageView mIcon;
    private TextView mTitle;
    private SwipeMenuItem mMenuItem;
    private OnMenuActionSwipeListener mOnActionClickListener;
    private int mPosition;

    public void setPosition(int position) {
        mPosition = position;
    }

    public SwipeMenuActionView(SwipeMenu menu) {
        super(menu.getContext());
        mMenuItem = menu.getAction();
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

        if (item.getCurrIcon() != null && SwipeMenuLayout.ALLOW_ICONS_IN_SWIPE_MENU) {
            parent.addView(createIcon(item));
        }
        if (!TextUtils.isEmpty(item.getCurrTitle())) {
            parent.addView(createTitle(item));
        }

    }

    private ImageView createIcon(SwipeMenuItem item) {
        mIcon = new ImageView(getContext());
        mIcon.setImageDrawable(item.getCurrIcon());
        return mIcon;
    }

    public void modifyIcon(Drawable icon){
        if(mIcon != null)
            mIcon.setImageDrawable(icon);
    }

    private TextView createTitle(SwipeMenuItem item) {
        mTitle = new TextView(getContext());
        mTitle.setText(item.getCurrTitle());
        mTitle.setGravity(Gravity.CENTER);
        mTitle.setTextSize(item.getTitleSize());
        mTitle.setTextColor(item.getTitleColor());
        return mTitle;
    }

    public void modifyTitle(String title){
        if(mTitle != null)
            mTitle.setText(title);
    }

    @Override
    public void onClick(View v) {
        if (mOnActionClickListener != null) {
            mOnActionClickListener.onActionSwipe(mPosition); //this , mMenu, v.getId());
            modifyIcon(mMenuItem.getAndSwitchCurrIcon());
            modifyTitle(mMenuItem.getAndSwitchCurrTitle());
            ((SwipeMenuLayout) getParent()).modifyOnPrimaryAction(true);
        }
    }

    /**
     * When we click to trigger action, we need to make sure we modify the status of the correlating action in our
     * menu. This will be handled by having the SwipeMenuActionView trigger modifyOnPrimaryAction in our parent
     * layout (SwipeMenuLayout) from the view's onClick method (look above for how we handle doing a similar thing
     * for notifying our items that we have performed an action)
     */
    public void modifyOnActionItemClicked(){
        modifyIcon(mMenuItem.getAndSwitchCurrIcon());
        modifyTitle(mMenuItem.getAndSwitchCurrTitle());
    }

    public void setOnSwipeActionSwipeListener(OnMenuActionSwipeListener onActionListener) {
        this.mOnActionClickListener = onActionListener;
    }

}
