package com.scrappersW.ui.main;

import android.util.Log;

import com.scrappersW.R;
import com.scrappersW.base.BaseFragment;

/**
 *
 * @author jiangrenming
 * @date 2018/8/28
 */

public class CampFragment extends BaseFragment {


    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_camp;
    }

    @Override
    protected void initViews() {
        Log.i("初始化集中营界面","是");
    }

    @Override
    protected void updateViews() {
        Log.i("更新集中营界面","是");
    }
}
