package com.example.hasee.myp2p.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hasee.myp2p.R;
import com.example.hasee.myp2p.commen.BaseFragment;
import com.example.hasee.myp2p.ui.randomLayout.StellarMap;
import com.example.hasee.myp2p.utils.UIUtils;
import com.loopj.android.http.RequestParams;

import java.util.Random;

import butterknife.Bind;

/**
 * Created by lzq on 2016/11/15.
 */
public class ProductRecommondFragment extends BaseFragment {

    @Bind(R.id.stellarMap)
    StellarMap stellarMap;



    private String[] datas = new String[]{"超级新手计划", "乐享活系列90天计划", "钱包计划", "30天理财计划(加息2%)", "90天理财计划(加息5%)", "180天理财计划(加息10%)",
            "林业局投资商业经营", "中学老师购买车辆", "屌丝下海经商计划", "新西游影视拍摄投资", "Java培训老师自己周转", "养猪场扩大经营",
            "旅游公司扩大规模", "阿里巴巴洗钱计划", "铁路局回款计划", "高级白领赢取白富美投资计划"
    };
    //分为两组数据
    private String[] firstDatas = new String[datas.length / 2];
    private String[] secondDatas = new String[datas.length - datas.length / 2];
    private Random random;

    @Override
    protected RequestParams getParams() {
        return new RequestParams();
    }

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected void initData(String content) {

        //给两组数组数据赋值
        for(int i = 0; i < datas.length; i++) {
            if(i < datas.length / 2){
                firstDatas[i] = datas[i];
            }else{
                secondDatas[i - datas.length / 2] = datas[i];
            }
        }

        StellerAdapter stellerAdapter = new StellerAdapter();
        //加载显示
        stellarMap.setAdapter(stellerAdapter);
        //必须提供如下两个方法的调用，否则没有显示效果
        //设置初始化显示的组别，以及是否使用动画
        stellarMap.setGroup(0,true);
        //设置x,y轴方向上的稀疏度
        stellarMap.setRegularity(5,5);

    }

    public void startAnimation() {
        stellarMap.zoomIn();


    }


    @Override
    protected void initTitle() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product_recommond;

    }

    class StellerAdapter implements StellarMap.Adapter {

        //返回显示的组数
        @Override
        public int getGroupCount() {
            return 2;
        }

        //返回指定组的元素的个数
        @Override
        public int getCount(int group) {
            if(group == 0){
                return datas.length / 2;
            }else{
                return datas.length - datas.length / 2;
            }
        }

        /**
         * 返回指定组的指定位置上的view
         * @param group
         * @param position :对于每组数据来讲，position都从0开始
         * @param convertView
         * @return
         */
        @Override
        public View getView(int group, int position, View convertView) {

            random = new Random();


            float scal = random.nextFloat()/2;
            Animation scalAnimation = new ScaleAnimation(1,1+scal,1,1+scal,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            scalAnimation.setDuration(1500);
            scalAnimation.setFillAfter(true);
            scalAnimation.setStartOffset(1000);
          /*  TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,0,Animation.RELATIVE_TO_PARENT,1,Animation.RELATIVE_TO_PARENT,0,Animation.RELATIVE_TO_PARENT,1);
            translateAnimation.setDuration(1500);

            translateAnimation.setFillAfter(true);
            translateAnimation.setStartOffset(1000);


            AnimationSet animationSet = new AnimationSet(false);
            animationSet.addAnimation(scalAnimation);
            animationSet.addAnimation(translateAnimation);
*/


            final TextView tv = new TextView(getActivity());
            if(group == 0){
                tv.setText(firstDatas[position]);
            }else{
                tv.setText(secondDatas[position]);
            }
            //提供随机的三色
            int red = random.nextInt(200);
            int green = random.nextInt(200);
            int blue = random.nextInt(200);
            tv.setTextColor(Color.rgb(red,green,blue));

            tv.setTextSize(random.nextInt(UIUtils.dp2px(5)) + UIUtils.dp2px(3));

            tv.setAnimation(scalAnimation);
            //给TextView设置监听
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("TAG", "context = " + getContext());//MainActivity
                    Toast.makeText(getContext(),tv.getText(), Toast.LENGTH_SHORT).show();
                }
            });

            return tv;
        }

        /**
         * 下一组显示平移动画的组别。查看源代码发现，此方法从未被调用，不用重写
         * @param group
         * @param degree
         * @return
         */
        @Override
        public int getNextGroupOnPan(int group, float degree) {
         return 0;
        }

        /**
         * 下一组显示缩放动画的组别。
         * @param group
         * @param isZoomIn
         * @return
         */
        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            if(group == 0){
                return 1;
            }else{
                return 0;
            }
        }
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }
}
