package com.example.hasee.myp2p.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hasee.myp2p.R;
import com.example.hasee.myp2p.commen.BaseActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

public class PieChartActivity extends BaseActivity {


    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_title)
    TextView tvTopTitle;
    @Bind(R.id.iv_top_setting)
    ImageView ivTopSetting;
    @Bind(R.id.pieChart)
    PieChart pieChart;

    private Typeface mTf;

    @Override
    protected void initTitle() {
        ivTopBack.setVisibility(View.VISIBLE);
        tvTopTitle.setText("饼状图Demo");
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
        //设置图表的描述信息
        pieChart.setDescription("android市场手机厂商占比");
        //设置内层圆的半径
        pieChart.setHoleRadius(52f);
        //设置包裹内层圆的圆的半径（不是最外层圆）
        pieChart.setTransparentCircleRadius(57f);

        //设置内层圆中的文本内容
        pieChart.setCenterText("Android\n厂商占比");
        //设置文本字体
        pieChart.setCenterTextTypeface(mTf);
        //设置内层圆中的文本内容的字体大小
        pieChart.setCenterTextSize(18f);
        //是否使用百分比表示：各个部分相加的和是否是100%
        pieChart.setUsePercentValues(true);

        //获取数据
        PieData pieData = generateDataPie();

        //设置数据的显示格式：百分比
        pieData.setValueFormatter(new PercentFormatter());
        //设置字体
        pieData.setValueTypeface(mTf);
        //设置字体大小
        pieData.setValueTextSize(11f);
        //设置字体颜色
        pieData.setValueTextColor(Color.RED);
        // set data
        pieChart.setData(pieData);

        //获取说明部分
        Legend l = pieChart.getLegend();
        //设置其显示的位置
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        //设置相邻的项在y轴方向上的间距
        l.setYEntrySpace(10f);
        //设置最上面的项距离顶部的值
        l.setYOffset(30f);

        // do not forget to refresh the chart
        // pieChart.invalidate();
        pieChart.animateXY(900, 900);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pie_chart;
    }


    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private PieData generateDataPie() {

        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int i = 0; i < 4; i++) {
            entries.add(new Entry((int) (Math.random() * 70) + 30, i));
        }

        PieDataSet d = new PieDataSet(entries, "");

        //设置不同部分之间的间隙的宽度
        d.setSliceSpace(2f);
        //设置不同部分的颜色
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);

        PieData cd = new PieData(getQuarters(), d);
        return cd;
    }

    private ArrayList<String> getQuarters() {

        ArrayList<String> q = new ArrayList<String>();
        q.add("三星");
        q.add("OPPO");
        q.add("VIVO");
        q.add("华为");

        return q;
    }
}
