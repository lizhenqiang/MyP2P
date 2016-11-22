package com.example.hasee.myp2p.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hasee.myp2p.R;
import com.example.hasee.myp2p.commen.BaseActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

public class LineChartActivity extends BaseActivity {

    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_title)
    TextView tvTopTitle;
    @Bind(R.id.iv_top_setting)
    ImageView ivTopSetting;
    @Bind(R.id.lineChart)
    LineChart lineChart;
    private Typeface mTf;
    @Override
    protected void initTitle() {
        ivTopBack.setVisibility(View.VISIBLE);
        tvTopTitle.setText("折线图Demo");
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
        lineChart.setDescription("林丹出轨事件关注度");
        //设置是否绘制网格背景
        lineChart.setDrawGridBackground(false);
        //获取图表的x轴
        XAxis xAxis = lineChart.getXAxis();
        //设置x轴的显示位置
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        //设置x轴字体
        xAxis.setTypeface(mTf);
        //是否绘制x轴网格线
        xAxis.setDrawGridLines(false);
        //是否绘制x轴轴线
        xAxis.setDrawAxisLine(true);

        //获取左边的y轴
        YAxis leftAxis = lineChart.getAxisLeft();
        //设置左边y轴的字体
        leftAxis.setTypeface(mTf);
        //参数1：设置左边显示区间的个数。参数2：是否需要均匀的设置区间的端点。false:是需要均匀的。
        leftAxis.setLabelCount(15, true);

        //获取右边的y轴
        YAxis rightAxis = lineChart.getAxisRight();
        //设置其为不显式状态
        rightAxis.setEnabled(false);

        // set data
        lineChart.setData(generateDataLine());

        //设置x方向的动画，及其展示的时间。一旦执行此方法，就不再需要调用：invalidate()
        lineChart.animateX(750);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_line_chart;
    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private LineData generateDataLine() {

        ArrayList<Entry> e1 = new ArrayList<Entry>();
        //定义y轴显示的数据(随机生成。）
        //真实项目中，此数据来自于服务器
        for (int i = 0; i < 12; i++) {
            e1.add(new Entry((int) (Math.random() * 65) + 40, i));
        }

        //获取折线1的数据集
        LineDataSet d1 = new LineDataSet(e1, "New DataSet 1");
        //设置折线的宽度
        d1.setLineWidth(5.5f);
        //设置圆圈的半径
        d1.setCircleSize(10.5f);
        //设置高亮的颜色（选中圆圈时显示的x轴、y轴颜色）
        d1.setHighLightColor(Color.rgb(255, 0, 0));
        //设置小圆圈是否显示具体数据
        d1.setDrawValues(true);


        ArrayList<LineDataSet> sets = new ArrayList<LineDataSet>();
        sets.add(d1);

        LineData cd = new LineData(getMonths(), sets);
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
