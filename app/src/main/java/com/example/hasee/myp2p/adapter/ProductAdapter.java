package com.example.hasee.myp2p.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hasee.myp2p.R;
import com.example.hasee.myp2p.bean.Product;
import com.example.hasee.myp2p.ui.RoundProgress;
import com.example.hasee.myp2p.utils.UIUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lzq on 2016/11/15.
 */
public class ProductAdapter extends BaseAdapter {
    private List<Product> list;

    public ProductAdapter(List<Product> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.e("TAG", parent.getClass().toString());//ListView
        Log.e("TAG", parent.getContext().toString());//加载ListView的Activity：MainActivity

        int itemViewType = getItemViewType(position);
        if(itemViewType == 0){

            TextView tv = new TextView(parent.getContext());
            tv.setText("与子同游，动辄覆舟");
            tv.setTextColor(UIUtils.getColor(R.color.home_back_selected));
            tv.setTextSize(UIUtils.dp2px(20));
            return tv;
        }

        if(position > 3){
            position--;
        }

        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_product_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Product product = list.get(position);

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

    @Override
    public int getItemViewType(int position) {
        if(position == 3){
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
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
