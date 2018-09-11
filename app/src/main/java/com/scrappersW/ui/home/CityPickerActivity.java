package com.scrappersW.ui.home;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.scrappersW.R;
import com.scrappersW.base.BaseActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import butterknife.BindView;
import main.demo.mobads.baidu.com.citymodelib.CYBChangeCityGridViewAdapter;
import main.demo.mobads.baidu.com.citymodelib.ContactAdapter;
import main.demo.mobads.baidu.com.citymodelib.QGridView;
import main.demo.mobads.baidu.com.citymodelib.UserEntity;
import me.yokeyword.indexablerv.IndexableAdapter;
import me.yokeyword.indexablerv.IndexableHeaderAdapter;
import me.yokeyword.indexablerv.IndexableLayout;

/**
 *
 * @author jiangrenming
 * @date 2018/9/5
 */

public class CityPickerActivity extends BaseActivity{

    @BindView(R.id.pic_contact_back)
     ImageView back;
    @BindView(R.id.positionView)
    View positionView;
    IndexableLayout mIndexLayout;

    private ContactAdapter mAdapter;
    private  BannerHeaderAdapter mBannerHeaderAdapter;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_pick_contact;
    }

    @Override
    protected void initView() {
        if (adjustStatusHeight){
            adjustStatusBarHeight(positionView);
        }
        intent = getIntent();
        mIndexLayout = findViewById(R.id.index_ableLayout);
    }

    @Override
    protected void initDatas() {
        try{
            mAdapter = new ContactAdapter(this);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            mIndexLayout.setLayoutManager(layoutManager);
            mIndexLayout.setAdapter(mAdapter);
            mIndexLayout.setOverlayStyle_Center();
            mAdapter.setDatas(initData());
            // 全字母排序。  排序规则设置为：每个字母都会进行比较排序；速度较慢
            mIndexLayout.setCompareMode(IndexableLayout.MODE_FAST);
            // 这里BannerView只有一个Item, 添加一个长度为1的任意List作为第三个参数
            List<String> bannerList = new ArrayList<>();
            bannerList.add("");
            mBannerHeaderAdapter = new BannerHeaderAdapter("↑", null, bannerList);
            mIndexLayout.addHeaderAdapter(mBannerHeaderAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mAdapter.setOnItemContentClickListener(new IndexableAdapter.OnItemContentClickListener<UserEntity>() {
            @Override
            public void onItemClick(View v, int originalPosition, int currentPosition, UserEntity entity) {
                if (originalPosition >= 0) {
                    intent.putExtra("city", entity.getNick());
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(CityPickerActivity.this,"选中"+entity.getNick(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private List<UserEntity> initData() {
        List<UserEntity> list = new ArrayList<>();
        // 初始化数据
        List<String> contactStrings = Arrays.asList(getResources().getStringArray(R.array.provinces));
        List<String> mobileStrings = Arrays.asList(getResources().getStringArray(R.array.provinces));
        for (int i = 0; i < contactStrings.size(); i++) {
            UserEntity contactEntity = new UserEntity(contactStrings.get(i), mobileStrings.get(i));
            list.add(contactEntity);
        }
        return list;
    }

    /**
     * 自定义的Banner Header
     */
    private String[] city = {"东莞","深圳","广州","温州","郑州","金华","佛山","上海","苏州","杭州","长沙","中山"};
    private ArrayList<String> list;
    private CYBChangeCityGridViewAdapter cybChangeCityGridViewAdapter;
    private Intent intent;
    class BannerHeaderAdapter extends IndexableHeaderAdapter {
        private static final int TYPE = 1;
        public BannerHeaderAdapter(String index, String indexTitle, List datas) {
            super(index, indexTitle, datas);
        }

        @Override
        public int getItemViewType() {
            return TYPE;
        }

        @Override
        public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
            View view = LayoutInflater.from(CityPickerActivity.this).inflate(R.layout.item_city_header, parent, false);
            VH holder = new VH(view);
            return holder;
        }

        @Override
        public void onBindContentViewHolder(RecyclerView.ViewHolder holder, Object entity) {
            // 数据源为null时, 该方法不用实现
            final VH vh = (VH) holder;
            list=new ArrayList<>();
            for(int i = 0; i<city.length; i++){
                list.add(city[i]);
            }
            System.out.println("------------city"+list);
            cybChangeCityGridViewAdapter=new CYBChangeCityGridViewAdapter(CityPickerActivity.this, list);
            vh.head_home_change_city_gridview.setAdapter(cybChangeCityGridViewAdapter);
            vh.head_home_change_city_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    intent.putExtra("city", list.get(position));
                    System.out.println("aaaaaayyyyyyyyy"+list.get(position));
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
            vh.item_header_city_dw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent.putExtra("city", vh.item_header_city_dw.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });

        }
        private class VH extends RecyclerView.ViewHolder {
            GridView head_home_change_city_gridview;
            TextView item_header_city_dw;
            public VH(View itemView) {
                super(itemView);
                head_home_change_city_gridview =(QGridView)itemView.findViewById(R.id.item_header_city_gridview);
                item_header_city_dw = itemView.findViewById(R.id.item_header_city_dw);
            }
        }
    }
}
