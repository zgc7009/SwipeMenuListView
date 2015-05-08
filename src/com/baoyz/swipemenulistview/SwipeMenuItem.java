package com.baoyz.swipemenulistview;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

/**
 * 
 * @author baoyz
 * @date 2014-8-23
 * 
 */
public class SwipeMenuItem {

    private static final int DEFAULT_TEXT_COLOR = Color.WHITE;
    private static final int DEFAULT_TEXT_SIZE = 12;
    private static final int DEFAULT_WIDTH = 120;

	private int id;
	private Context mContext;
    private String currTitle;
	private String title;
    private String altTitle;
    private Drawable currIcon;
	private Drawable icon;
    private Drawable altIcon;
	private Drawable background;
	private int titleColor = DEFAULT_TEXT_COLOR;
	private int titleSize = DEFAULT_TEXT_SIZE;
	private int width = DEFAULT_WIDTH;

	public SwipeMenuItem(Context context) {
		mContext = context;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTitleColor() {
		return titleColor;
	}

	public int getTitleSize() {
		return titleSize;
	}

	public void setTitleSize(int titleSize) {
		this.titleSize = titleSize;
	}

	public void setTitleColor(int titleColor) {
		this.titleColor = titleColor;
	}

    public String getCurrTitle(){
        return currTitle;
    }

    public String getAndSwitchCurrTitle(){
        currTitle = (currTitle.equals(title)? altTitle: title);
        return currTitle;
    }

	public void setTitles(String title, String altTitle) {
		this.title = title;
        this.altTitle = altTitle;
        currTitle = title;
	}

	public void setTitles(int resId, int altResId) {
		setTitles(mContext.getString(resId), mContext.getString(altResId));
	}

    public Drawable getAndSwitchCurrIcon(){
        currIcon = (currIcon.equals(icon)? altIcon: icon);
        return currIcon;
    }

	public Drawable getCurrIcon() {
		return icon;
	}

	public void setIcons(Drawable icon, Drawable altIcon) {
		this.icon = icon;
        this.altIcon = altIcon;
        currIcon = icon;
	}

	public void setIcons(int resId, int altResId) {
		setIcons(mContext.getResources().getDrawable(resId), mContext.getResources().getDrawable(altResId));
	}

	public Drawable getBackground() {
		return background;
	}

	public void setBackground(Drawable background) {
		this.background = background;
	}

	public void setBackground(int resId) {
		this.background = mContext.getResources().getDrawable(resId);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

}
