package com.example.hasee.myp2p.activity;

import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hasee.myp2p.R;
import com.example.hasee.myp2p.commen.BaseActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

public class BarChartActivity extends BaseActivity {


    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_title)
    TextView tvTopTitle;
    @Bind(R.id.iv_top_setting)
    ImageView ivTopSetting;
    @Bind(R.id.barChart)
    BarChart barChart;

    private Typeface mTf;
    @Override
    protected void initTitle() {
        ivTopBack.setVisibility(View.VISIBLE);
        tvTopTitle.setText("柱状图Demo");
        ivTopSetting.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.iv_top_back)
    public void back(View view){
        removeCurrentActivity();
    }

    @Override
    protected void initData() {
        //加载本地的字体库
        mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        //设置图表的描述
        barChart.setDescription("北京市雾霾指数统计表");
        //是否设置网格背景
        barChart.setDrawGridBackground(false);
        //是否绘制柱状图的阴影
        barChart.setDrawBarShadow(false);

        //获取x轴
        XAxis xAxis = barChart.getXAxis();
        //设置x轴显示的位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置x轴显示的字体
        xAxis.setTypeface(mTf);
        //是否绘制网格线
        xAxis.setDrawGridLines(true);
        //是否绘制x轴轴线
        xAxis.setDrawAxisLine(true);

        //获取左边的y轴
        YAxis leftAxis = barChart.getAxisLeft();
        //设置左边的y轴的字体
        leftAxis.setTypeface(mTf);
        //参数1：指明左边的y轴设置区间的个数 参数2：是否均匀显示区间
        leftAxis.setLabelCount(5, false);
        //设置最高的柱状图距离顶部的数值
        leftAxis.setSpaceTop(10f);

        YAxis rightAxis = barChart.getAxisRight();
        //不显示右边的y轴
        rightAxis.setEnabled(false);

        BarData barData = generateDataBar();
        barData.setValueTypeface(mTf);

        // set data
        barChart.setData(barData);

        //设置y轴的动画显示
        barChart.animateY(700);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bar_chart;
    }


    /**
     *
     * 生成显示的随机数据
     * 这些数据在项目中，可能来自于服务器。需要联网获取数据
     * @return
     */
    private BarData generateDataBar() {

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        for (int i = 0; i < 12; i++) {
            entries.add(new BarEntry((int) (Math.random() * 70) + 30, i));
        }

        BarDataSet d = new BarDataSet(entries, "New DataSet 1" );
        //设置柱状图之间的间距
        d.setBarSpacePercent(40f);
        //设置柱状图显示的颜色
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        //设置高亮的透明度
        d.setHighLightAlpha(255);
        //设置x轴的数据
        BarData cd = new BarData(getMonths(), d);
        return cd;
    }

    private ArrayList<String> getMonths() {

        ArrayList<String> m = new ArrayList<String>();
        m.add("Jan");
        m.add("Feb");
        m.add("Mar");
        m.add("Apr");
        m.add("May");
        m.add("Jun");
        m.add("Jul");
        m.add("Aug");
        m.add("Sep");
        m.add("Okt");
        m.add("Nov");
        m.add("Dec");

        return m;
    }
}
