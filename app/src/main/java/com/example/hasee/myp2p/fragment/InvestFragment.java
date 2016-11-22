package com.example.hasee.myp2p.fragment;

import android.os.Handler;
import android.os.Message;
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
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by lzq on 2016/11/11.
 */
public class InvestFragment extends BaseFragment {

    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_title)
    TextView tvTopTitle;
    @Bind(R.id.iv_top_setting)
    ImageView ivTopSetting;
    @Bind(R.id.tab_indicator)
    TabPageIndicator tabIndicator;
    @Bind(R.id.viewpager_invest)
    ViewPager viewpagerInvest;
    private ProductHotFragment productHotFragment;
    private ProductListFragment productListFragment;
    private ProductRecommondFragment productRecommondFragment;

    boolean isDragging =false;

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            startAnimation();
        }
    };



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
        tabIndicator.setViewPager(viewpagerInvest);

        initListener();

    }

    private void initListener() {
        tabIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==1) {
                    startAnimation();
                }else{
                    handler.removeCallbacksAndMessages(null);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

                if(state==ViewPager.SCROLL_STATE_DRAGGING) {
                    isDragging=true;
                    handler.removeMessages(0);
                    Log.e("TAG", "ViewPager拖拽中----");
                }else if(state==ViewPager.SCROLL_STATE_IDLE) {
                    Log.e("TAG", "ViewPager静止中----");
                }else if(state==ViewPager.SCROLL_STATE_SETTLING&&isDragging) {
                    handler.sendEmptyMessageDelayed(0,4000);
                    isDragging=false;
                    Log.e("TAG", "ViewPager滑动----");
                }

            }
        });









    }






    private void startAnimation() {

        handler.sendEmptyMessageDelayed(0,4000);
        productRecommondFragment.startAnimation();
    }

    private List<Fragment> fragments = new ArrayList<>();
    private void initFragments() {
         productHotFragment = new ProductHotFragment();
         productListFragment = new ProductListFragment();
         productRecommondFragment = new ProductRecommondFragment();
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
        return R.layout.fragment_invest;
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
            return UIUtils.getStrArray(R.array.invest_tab)[position];
        }
    }
}
