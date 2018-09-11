package com.scrappersW.wiget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 *
 * @author jiangrenming
 * @date 2018/8/28
 * 禁止滑动操作
 */

public class MyViewPager extends ViewPager{
    private boolean noScroll = false;
    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (noScroll)
            return false;
        else
            return super.onTouchEvent(arg0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (noScroll)
            return false;
        else
            return super.onInterceptTouchEvent(arg0);
    }

    public void setNoScroll(boolean noScroll) {
        this.noScroll = noScroll;
    }
}
