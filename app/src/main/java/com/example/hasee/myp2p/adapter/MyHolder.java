package com.example.hasee.myp2p.adapter;

import android.view.View;
import android.widget.TextView;

import com.example.hasee.myp2p.R;
import com.example.hasee.myp2p.bean.Product;
import com.example.hasee.myp2p.commen.BaseHolder;
import com.example.hasee.myp2p.ui.RoundProgress;
import com.example.hasee.myp2p.utils.UIUtils;

import butterknife.Bind;

/**
 * Created by lzq on 2016/11/15.
 */
public class MyHolder extends BaseHolder {
    @Bind(R.id.p_name)
    TextView pName;
    @Bind(R.id.p_money)
    TextView pMoney;
    @Bind(R.id.p_yearlv)
    TextView pYearlv;
    @Bind(R.id.p_suodingdays)
    TextView pSuodingdays;
    @Bind(R.id.p_minzouzi)
    TextView pMinzouzi;
    @Bind(R.id.p_minnum)
    TextView pMinnum;
    @Bind(R.id.p_progresss)
    RoundProgress pProgresss;

    @Override
    protected void refreshData() {
        Product data = (Product) getData();
        pName.setText(data.name);
        pMinnum.setText(data.memberNum);
        pMoney.setText(data.money);
        pYearlv.setText(data.yearRate);
        pSuodingdays.setText(data.suodingDays);
        pMinzouzi.setText(data.minTouMoney);
        pProgresss.setProgress(Integer.parseInt(data.progress));
    }

    @Override
    public View initView() {
        return UIUtils.getView(R.layout.item_product_list);
    }
}
