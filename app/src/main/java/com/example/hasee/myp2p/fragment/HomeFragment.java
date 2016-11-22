package com.example.hasee.myp2p.fragment;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.hasee.myp2p.R;
import com.example.hasee.myp2p.bean.Image;
import com.example.hasee.myp2p.bean.Index;
import com.example.hasee.myp2p.bean.Product;
import com.example.hasee.myp2p.utils.AppNetConfig;
import com.example.hasee.myp2p.commen.BaseFragment;
import com.example.hasee.myp2p.ui.RoundProgress;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lzq on 2016/11/11.
 */
public class HomeFragment extends BaseFragment {


    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_title)
    TextView tvTopTitle;
    @Bind(R.id.iv_top_setting)
    ImageView ivTopSetting;
    @Bind(R.id.banner)
    Banner banner;
    @Bind(R.id.roundp_home)
    RoundProgress roundpHome;
    @Bind(R.id.tv_home_rate)
    TextView tvHomeRate;



    @Override
    protected RequestParams getParams() {
        return new RequestParams();
    }

    @Override
    protected String getUrl() {
        return AppNetConfig.INDEX;
    }


    private int currentProgress;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            roundpHome.setMax(100);
            roundpHome.setProgress(0);

            for(int i = 0; i < currentProgress; i++) {
                roundpHome.setProgress(roundpHome.getProgress() + 1);

                SystemClock.sleep(20);
//                roundpHome.invalidate();必须执行在主线程中
                roundpHome.postInvalidate();//主线程或分线程都可以执行，用于重绘
            }
        }
    };
    @Override
    protected void initData(String content) {

        Log.e("AAA", "content"+content);
        //1.使用fastJson解析得到的json数据,并封装数据到java对象中
        JSONObject jsonObject = JSON.parseObject(content);

        String proInfo = jsonObject.getString("proInfo");
        Product product = JSON.parseObject(proInfo, Product.class);

        String imageArr = jsonObject.getString("imageArr");
        List<Image> images = JSON.parseArray(imageArr, Image.class);

        index = new Index();
        index.product = product;
        index.images = images;

        //2.设置Banner显示图片
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片url集合:imageUrl
        List<String> imageUrl = new ArrayList<String>(images.size());
        for (int i = 0; i < images.size(); i++) {
            imageUrl.add(images.get(i).IMAURL);
            Log.e("TAG", "url = " + images.get(i).IMAURL);
        }
        banner.setImages(imageUrl);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        String[] titles = new String[]{"深情不及久伴，加息2%", "乐享活计划", "破茧重生", "安心钱包计划"};
        banner.setBannerTitles(Arrays.asList(titles));
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        //banner设置方法全部调用完毕时最后调用
        banner.start();

        //3.根据得到的产品的数据，更新界面中的产品展示
        String yearRate = index.product.yearRate;
        tvHomeRate.setText(yearRate + "%");

        currentProgress = Integer.parseInt(index.product.progress);
        new Thread(runnable).start();
    }

    @Override
    protected void initTitle() {
        ivTopBack.setVisibility(View.INVISIBLE);
        ivTopSetting.setVisibility(View.INVISIBLE);
        tvTopTitle.setText("首页");
    }


    @Override
    public int getLayoutId() {
        Log.e("AAA", "getLayoutId");
        return R.layout.fragment_home;
    }

    private Index index;


    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {

            //Picasso 加载图片简单用法
            Picasso.with(context).load((String) path).into(imageView);

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
