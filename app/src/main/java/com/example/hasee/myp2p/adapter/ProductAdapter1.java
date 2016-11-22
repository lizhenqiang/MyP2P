package com.example.hasee.myp2p.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hasee.myp2p.R;
import com.example.hasee.myp2p.bean.Product;
import com.example.hasee.myp2p.commen.MyBaseAdapter1;
import com.example.hasee.myp2p.ui.RoundProgress;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lzq on 2016/11/15.
 */
public class ProductAdapter1 extends MyBaseAdapter1 {
    public ProductAdapter1(List list) {
        super(list);
    }

    @Override
    public View myGetView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {


            convertView = View.inflate(parent.getContext(), R.layout.item_product_list, null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Product product = (Product) list.get(position);

        //装配数据
        holder.pMinnum.setText(product.memberNum);
        holder.pMinzouzi.setText(product.minTouMoney);
        holder.pMoney.setText(product.money);
        holder.pName.setText(product.name);
        holder.pProgresss.setProgress(Integer.parseInt(product.progress));
        holder.pSuodingdays.setText(product.suodingDays);
        holder.pYearlv.setText(product.yearRate);

        return convertView;
    }

    static class ViewHolder {
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

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
