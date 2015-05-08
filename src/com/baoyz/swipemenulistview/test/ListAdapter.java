package com.baoyz.swipemenulistview.test;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.R;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.List;

/**
 * Created by zach on 5/7/15.
 */
class ListAdapter extends BaseAdapter {
    
    private Context mContext;
    private List<ApplicationInfo> mAppList;
    
    public ListAdapter(Context context){
        mContext = context;
        mAppList = context.getPackageManager().getInstalledApplications(0);
    }

    public ApplicationInfo getApp(int position){
        return mAppList.get(position);
    }

    public ApplicationInfo removeApp(int position){
        return mAppList.remove(position);
    }

    @Override
    public int getCount() {
        return mAppList.size();
    }

    @Override
    public ApplicationInfo getItem(int position) {
        return mAppList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_list_app, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        ApplicationInfo item = getItem(position);
        holder.iv_icon.setImageDrawable(item.loadIcon(mContext.getPackageManager()));
        holder.tv_name.setText(item.loadLabel(mContext.getPackageManager()));
        return convertView;
    }

    class ViewHolder {
        ImageView iv_icon;
        TextView tv_name;

        public ViewHolder(View view) {
            iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            view.setTag(this);
        }
    }
    
}