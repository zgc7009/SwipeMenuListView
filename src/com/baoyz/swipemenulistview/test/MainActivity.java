package com.baoyz.swipemenulistview.test;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Toast;

import com.baoyz.swipemenulistview.R;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.ISwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuItemView;
import com.baoyz.swipemenulistview.SwipeMenuListView;

public class MainActivity extends Activity {

    private SwipeMenuListView mListView;
    private ListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mListView = (SwipeMenuListView) findViewById(R.id.listView);

        mAdapter = new ListAdapter(this);
        mListView.setAdapter(mAdapter);

        // step 1. create a MenuCreator
        ISwipeMenuCreator creator = new ISwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                createMenu(menu);
                addMenuAction(menu);
            }

            private void createMenu(SwipeMenu menu) {
                SwipeMenuItem item1 = new SwipeMenuItem(
                        getApplicationContext());
                item1.setBackground(new ColorDrawable(Color.rgb(0xE5, 0x18, 0x5E)));
                item1.setWidth(dp2px(120));
                item1.setIcon(R.drawable.ic_action_favorite);
                item1.setTitle("Option 1");
                menu.addMenuItem(item1);

                SwipeMenuItem item2 = new SwipeMenuItem(getApplicationContext());
                item2.setBackground(new ColorDrawable(Color.rgb(0x30, 0xB1, 0xF5)));
                item2.setWidth(dp2px(120));
                item2.setIcon(R.drawable.ic_action_good);
                item2.setTitle("Option 2");
                menu.addMenuItem(item2);

                SwipeMenuItem item3 = new SwipeMenuItem(getApplicationContext());
                item3.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
                item3.setWidth(dp2px(120));
                item3.setIcon(R.drawable.ic_action_share);
                item3.setTitle("Option 3");
                menu.addMenuItem(item3);
            }

            private void addMenuAction(SwipeMenu menu) {
                final SwipeMenuItem action = new SwipeMenuItem(getApplicationContext());
                action.setBackground(new ColorDrawable(Color.rgb(0x30, 0xB1,0xF5)));
                action.setWidth(mListView.getWidth() - 200);
                action.setIcon(R.drawable.ic_action_about);
                action.setTitle("Action");
                menu.addAction(action);
            }
        };

        // set creator
        mListView.setMenuCreator(creator);

    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}