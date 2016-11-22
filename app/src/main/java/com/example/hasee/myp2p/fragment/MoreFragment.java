package com.example.hasee.myp2p.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hasee.myp2p.R;
import com.example.hasee.myp2p.commen.BaseFragment;
import com.example.hasee.myp2p.utils.UIUtils;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by lzq on 2016/11/11.
 */
public class MoreFragment extends BaseFragment {

    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_title)
    TextView tvTopTitle;
    @Bind(R.id.iv_top_setting)
    ImageView ivTopSetting;
    @Bind(R.id.tab_indicator2)
    TabLayout tableLayout;
    @Bind(R.id.viewpager_invest2)
    ViewPager viewpagerInvest;

    @Override
    protected RequestParams getParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected void initData(String content) {

        initFragments();
        MyAdapter myAapter = new MyAdapter(getFragmentManager());
        viewpagerInvest.setAdapter(myAapter);
        tableLayout.setupWithViewPager(viewpagerInvest);
        tableLayout.setTabMode(TabLayout.MODE_FIXED);



    }

    private List<Fragment> fragments = new ArrayList<>();
    private void initFragments() {
        ProductHotFragment productHotFragment = new ProductHotFragment();
        ProductListFragment productListFragment = new ProductListFragment();
        ProductRecommondFragment productRecommondFragment = new ProductRecommondFragment();
        fragments.add(productListFragment);
        fragments.add(productRecommondFragment);
        fragments.add(productHotFragment);




    }

    @Override
    protected void initTitle() {
        ivTopBack.setVisibility(View.INVISIBLE);
        ivTopSetting.setVisibility(View.INVISIBLE);
        tvTopTitle.setText("投资");


    }

    @Override
    public int getLayoutId() {
        Log.e("AAA", "InvestFragmentgetLayoutId");
        return R.layout.fragment_more;
    }

    private class MyAdapter extends FragmentPagerAdapter{
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Log.e("TAG", ""+UIUtils.getStrArray(R.array.invest_tab)[position]);
            String [] arr = new String[]{"全部理财","推荐理财","热门理财"};
            return arr[position];
        }
    }
}
