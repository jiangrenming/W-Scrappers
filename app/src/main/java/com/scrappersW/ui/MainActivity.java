package com.scrappersW.ui;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scrappersW.R;
import com.scrappersW.adapter.PagerAdapter;
import com.scrappersW.base.BaseActivity;
import com.scrappersW.ui.main.CampFragment;
import com.scrappersW.ui.main.PersonFragment;
import com.scrappersW.ui.main.PublishFragment;
import com.scrappersW.ui.main.QueryFragment;
import com.scrappersW.wiget.MyViewPager;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 *
 * @author jiangrenming
 * @date 2018/8/27
 */

public class MainActivity extends BaseActivity implements View.OnClickListener{


    @BindView(R.id.viewpager)
    MyViewPager viewPager;

    @BindView(R.id.product)
    LinearLayout product;
    @BindView(R.id.img_make)
    ImageView img_make;
    @BindView(R.id.txt_make)
    TextView txt_make;

    @BindView(R.id.centre)
    LinearLayout centre;
    @BindView(R.id.img_red)
    ImageView img_red;
    @BindView(R.id.txt_red)
    TextView txt_red;

    @BindView(R.id.query)
    LinearLayout query;
    @BindView(R.id.img_qury)
    ImageView img_qury;
    @BindView(R.id.txt_query)
    TextView txt_query;

    @BindView(R.id.linlayout_merc)
    LinearLayout linlayout_merc;
    @BindView(R.id.img_merc)
    ImageView img_merc;
    @BindView(R.id.txt_merc)
    TextView txt_merc;

    @BindView(R.id.positionView)
    View positionView;

    private List<Fragment> fragmentList;
    private PagerAdapter pagerAdapter;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        if (adjustStatusHeight){
            adjustStatusBarHeight(positionView);
        }
        fragmentList = new ArrayList<>();
        fragmentList.add(new PublishFragment());
        fragmentList.add(new CampFragment());
        fragmentList.add(new QueryFragment());
        fragmentList.add(new PersonFragment());
         pagerAdapter = new PagerAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setNoScroll(true);
        viewPager.setOffscreenPageLimit(fragmentList.size());
    }

    @Override
    protected void initDatas() {
        product.setOnClickListener(this);
        linlayout_merc.setOnClickListener(this);
        query.setOnClickListener(this);
        centre.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.product:
                viewPager.setCurrentItem(0, false);
                txt_make.setTextColor(ContextCompat.getColor(this, R.color.yellow_DBB16D));
                img_make.setImageResource(R.mipmap.nav_icon_kaipiao_pressed);
                txt_query.setTextColor(ContextCompat.getColor(this, R.color.gray_666666));
                img_qury.setImageResource(R.mipmap.nav_icon_chaxun_default);
                txt_red.setTextColor(ContextCompat.getColor(this, R.color.gray_666666));
                img_red.setImageResource(R.mipmap.nav_icon_zhihuan_default);
                txt_merc.setTextColor(ContextCompat.getColor(this, R.color.gray_666666));
                img_merc.setImageResource(R.mipmap.nav_icon_person_default);
                pagerAdapter.notifyDataSetChanged();
                break;
            case R.id.linlayout_merc:
                viewPager.setCurrentItem(3, false);
                txt_make.setTextColor(ContextCompat.getColor(this, R.color.gray_666666));
                img_make.setImageResource(R.mipmap.nav_icon_kaipiao_default);
                txt_query.setTextColor(ContextCompat.getColor(this, R.color.gray_666666));
                img_qury.setImageResource(R.mipmap.nav_icon_chaxun_default);
                txt_red.setTextColor(ContextCompat.getColor(this, R.color.gray_666666));
                img_red.setImageResource(R.mipmap.nav_icon_zhihuan_default);
                txt_merc.setTextColor(ContextCompat.getColor(this, R.color.yellow_DBB16D));
                img_merc.setImageResource(R.mipmap.nav_icon_person_press);
                pagerAdapter.notifyDataSetChanged();
                break;
            case R.id.query:
                viewPager.setCurrentItem(2, false);
                txt_make.setTextColor(ContextCompat.getColor(this, R.color.gray_666666));
                img_make.setImageResource(R.mipmap.nav_icon_kaipiao_default);
                txt_query.setTextColor(ContextCompat.getColor(this, R.color.yellow_DBB16D));
                img_qury.setImageResource(R.mipmap.nav_icon_chaxun_pressed);
                txt_red.setTextColor(ContextCompat.getColor(this, R.color.gray_666666));
                img_red.setImageResource(R.mipmap.nav_icon_zhihuan_default);
                txt_merc.setTextColor(ContextCompat.getColor(this, R.color.gray_666666));
                img_merc.setImageResource(R.mipmap.nav_icon_person_default);
                break;
            case R.id.centre:
                viewPager.setCurrentItem(1, false);
                txt_make.setTextColor(ContextCompat.getColor(this, R.color.gray_666666));
                img_make.setImageResource(R.mipmap.nav_icon_kaipiao_default);
                txt_query.setTextColor(ContextCompat.getColor(this, R.color.gray_666666));
                img_qury.setImageResource(R.mipmap.nav_icon_chaxun_default);
                txt_red.setTextColor(ContextCompat.getColor(this, R.color.yellow_DBB16D));
                img_red.setImageResource(R.mipmap.nav_icon_zhihuan_press);
                txt_merc.setTextColor(ContextCompat.getColor(this, R.color.gray_666666));
                img_merc.setImageResource(R.mipmap.nav_icon_person_default);
                break;
            default:
                break;
        }
    }
}
