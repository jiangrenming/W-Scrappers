package com.scrappersW.base;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scrappersW.R;
import com.scrappersW.utils.AndroidUtils;

import butterknife.ButterKnife;

/**
 *
 * @author jiangrenming
 * @date 2018/8/27
 */

public abstract class BaseActivity extends FragmentActivity implements View.OnTouchListener{
    // 1. 状态栏侵入
     public boolean adjustStatusHeight = false;
      @LayoutRes
      protected abstract  int attachLayoutRes();
      protected abstract  void initView();
      protected  abstract void initDatas();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(attachLayoutRes());
        ButterKnife.bind(this);
        //设为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            adjustStatusHeight = true;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            } else {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
        // 2. 状态栏占位View的高度调整
        String brand = Build.BRAND;
        if (brand.contains("Xiaomi")) {
            AndroidUtils.setXiaomiDarkMode(this);
        } else if (brand.contains("Meizu")) {
            AndroidUtils.setMeizuDarkMode(this);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            adjustStatusHeight = false;
        }
        initView();
        initDatas();
    }
    /**
     * 调整沉浸状态栏
     */
    public void adjustStatusBarHeight(View view) {
        int statusBarHeight = AndroidUtils.getStatusBarHeight(this);
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.height = statusBarHeight;
        view.setLayoutParams(lp);
    }
    /**
     * 设置title
     * @param title
     */
    protected void setTitle(String title) {
        RelativeLayout back = (RelativeLayout) findViewById(R.id.title_back);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        back.setOnTouchListener(this);
        ((TextView) findViewById(R.id.title)).setText(!title.isEmpty() ? title : "");
    }

    /**
     * 设置title
     * @param title
     */
    protected void setTitle(String title, View.OnClickListener onClickListener) {
        RelativeLayout back = (RelativeLayout) findViewById(R.id.title_back);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(onClickListener);
        back.setOnTouchListener(this);
        ((TextView) findViewById(R.id.title)).setText(!title.isEmpty() ? title : "");
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.touch_down));
        } else if (event.getAction() == MotionEvent.ACTION_UP
                || event.getAction() == MotionEvent.ACTION_CANCEL) {
            v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.touch_up));
        }
        return false;
    }

    //可以做统计操作
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
