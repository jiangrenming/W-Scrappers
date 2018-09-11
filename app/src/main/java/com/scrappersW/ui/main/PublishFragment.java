package com.scrappersW.ui.main;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.scrappersW.R;
import com.scrappersW.base.BaseFragment;
import com.scrappersW.bean.ItemEntity;
import com.scrappersW.plie.VerticalTransitionLayout;
import com.scrappersW.ui.home.CityPickerActivity;
import com.stone.pile.libs.ADEnity;
import com.stone.pile.libs.PileLayout;
import com.stone.pile.libs.TextViewAd;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import main.demo.mobads.baidu.com.banner.bananer.MZBannerView;
import main.demo.mobads.baidu.com.banner.bananer.MZHolderCreator;
import main.demo.mobads.baidu.com.banner.bananer.MZViewHolder;


/**
 *
 * @author jiangrenming
 * @date 2018/8/28
 * 发布产品界面
 */

public class PublishFragment extends BaseFragment {


    @BindView(R.id.nomral_banner)
    MZBannerView mBananerView;
    @BindView(R.id.notes)
    TextViewAd notes;
    @BindView(R.id.pileLayout)
    PileLayout pileLayout;
   /* @BindView(R.id.descriptionView)
     TextView descriptionView;*/
    @BindView(R.id.addressView)
   VerticalTransitionLayout addressView;
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    @BindView(R.id.address)
    TextView address;

    private Integer [] imgs = {R.mipmap.one,R.mipmap.two,R.mipmap.three};
    private List<Integer> datas = new ArrayList<>();
    private String [] mesages = {"9/1-9/30号，优惠多多","已经与各省爱心机构达成合作","注册新用户赠送积分"};
    private List<ADEnity> mess = new ArrayList<>();

    private List<ItemEntity> itemEntities ;
    private int lastDisplay = -1;
    private ObjectAnimator transitionAnimator;
    private Animator.AnimatorListener animatorListener;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_publish;
    }

    @Override
    protected void initViews() {
        for (int i = 0; i < imgs.length; i++) {
            datas.add(imgs[i]);
        }
        mBananerView.setIndicatorVisible(true);
        mBananerView.setIndicatorRes(R.drawable.unseletc_dot,R.drawable.select_dot);
        mBananerView.setIndicatorAlign(MZBannerView.IndicatorAlign.BOTTOM);
        mBananerView.setIndicatorPadding(10,0,0,10);
        mBananerView.setDelayedTime(5000);
        mBananerView.setPages(datas, new MZHolderCreator<MZViewHolder>() {
            @Override
            public MZViewHolder createViewHolder() {
                return new NomralBannerViewHolder();
            }
        });
        getAnimation();
        //模拟3d堆叠拖
        itemEntities =  new ArrayList<>();
        try {
            InputStream in = getActivity().getAssets().open("data.txt");
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            String jsonStr = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObject.optJSONArray("result");
            if (null != jsonArray) {
                int len = jsonArray.length();
                for (int i = 0; i < len; i++) {
                    JSONObject itemJsonObject = jsonArray.getJSONObject(i);
                    ItemEntity itemEntity = new ItemEntity(itemJsonObject);
                    itemEntities.add(itemEntity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("获取的数据长度为",itemEntities.size()+"");

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), CityPickerActivity.class),1000);
            }
        });
    }


    @Override
    protected void updateViews() {
       //消息轮询
        for (int i = 0; i < mesages.length; i++) {
            ADEnity adEnity = new ADEnity(i+"",mesages[i],"");
            mess.add(adEnity);
        }
        notes.setmTexts(mess);
        notes.setFrontColor(getResources().getColor(R.color.color_DBB16D));
        notes.setBackColor(getResources().getColor(R.color.blue_25A2F2));
        notes.setmDuration(2000);
        notes.setmInterval(1000);
        notes.setOnClickLitener(new TextViewAd.onClickLitener() {
            @Override
            public void onClick(String mUrl) {

            }
        });
        pileLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP)
                    scrollView.requestDisallowInterceptTouchEvent(false);
                else
                    scrollView.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        //填充适配器
        pileLayout.setAdapter(new PileLayout.Adapter() {
            @Override
            public int getLayoutId() {
                return R.layout.item_layout;
            }

            @Override
            public void bindView(View view, int position) {
                ViewHolder viewHolder = (ViewHolder) view.getTag();
                if (viewHolder == null) {
                    viewHolder = new ViewHolder();
                    viewHolder.imageView = (RoundedImageView) view.findViewById(R.id.imageView);
                    view.setTag(viewHolder);
                }
                Glide.with(PublishFragment.this).load(itemEntities.get(position).getCoverImageUrl()).error(R.mipmap.one).into(viewHolder.imageView);
            }

            @Override
            public int getItemCount() {
                return itemEntities.size();
            }

            @Override
            public void displaying(int position) {
                if (lastDisplay < 0) {
                    initSecene(position);
                    lastDisplay = 0;
                } else if (lastDisplay != position) {
                    transitionSecene(position);
                    lastDisplay = position;
                }
            }

            @Override
            public void onItemClick(View view, int position) {
                super.onItemClick(view, position);
            }
        });
    }

    private void initSecene(int position) {
        Log.i("获取的地址是:",itemEntities.get(position).getAddress());
        addressView.firstInit(itemEntities.get(position).getAddress());
    }

    private void transitionSecene(int position) {
        if (transitionAnimator != null) {
            transitionAnimator.cancel();
        }
        Log.i("获取的地址是q:",itemEntities.get(position).getAddress());
        addressView.saveNextPosition(position, itemEntities.get(position).getAddress());
        transitionAnimator = ObjectAnimator.ofFloat(this, "transitionValue", 0.0f, 1.0f);
        transitionAnimator.setDuration(300);
        transitionAnimator.start();
        transitionAnimator.addListener(animatorListener);
    }
    class ViewHolder {
        RoundedImageView imageView;
    }

    private void getAnimation(){
        animatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }
            @Override
            public void onAnimationEnd(Animator animation) {
                addressView.onAnimationEnd();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        };
    }

    /**
     * 正常轮播图
     */
    private class NomralBannerViewHolder implements MZViewHolder<Integer> {
        private ImageView mImageView;
        @Override
        public View createView(Context context) {
            // 返回页面布局文件
            View view = LayoutInflater.from(context).inflate(R.layout.nomral_item,null);
            mImageView = (ImageView) view.findViewById(R.id.nomral_img);
            return view;
        }

        @Override
        public void onBind(Context context, int position, Integer data) {
            mImageView.setImageResource(imgs[position]);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mBananerView.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        mBananerView.pause();
    }
}
