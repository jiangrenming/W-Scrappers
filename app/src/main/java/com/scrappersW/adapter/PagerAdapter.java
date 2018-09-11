package com.scrappersW.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.scrappersW.ui.main.CampFragment;
import com.scrappersW.ui.main.PublishFragment;

import java.util.List;

/**
 * xxxxx
 *
 * @author Sago丶
 * @date 2017/8/1
 */

public class PagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;

    public PagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;

    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
    @Override
    public int getItemPosition(Object object) {
        if (object instanceof PublishFragment){
            return  POSITION_NONE;
        }else if (object instanceof CampFragment){
            return  POSITION_NONE;
        }else {
            return POSITION_UNCHANGED;
        }
    }
}