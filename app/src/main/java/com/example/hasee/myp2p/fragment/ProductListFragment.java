package com.example.hasee.myp2p.fragment;

import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.example.hasee.myp2p.R;
import com.example.hasee.myp2p.adapter.ProductAdapter1;
import com.example.hasee.myp2p.adapter.ProductAdapter2;
import com.example.hasee.myp2p.bean.Product;
import com.example.hasee.myp2p.commen.BaseFragment;
import com.example.hasee.myp2p.utils.AppNetConfig;
import com.loopj.android.http.RequestParams;

import java.util.List;

import butterknife.Bind;

/**
 * Created by lzq on 2016/11/15.
 */
public class ProductListFragment extends BaseFragment {


    @Bind(R.id.lv_product_list)
    ListView lvProductList;
    private List<Product> products;


    @Override
    protected RequestParams getParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return AppNetConfig.PRODUCT;
    }

    @Override
    protected void initData(String content) {

        //解析json数据
        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(content);

        boolean isSuccess = jsonObject.getBoolean("success");
        if(isSuccess) {
            String data = jsonObject.getString("data");
            //解析得到集合数据
            products= JSON.parseArray(data, Product.class);
        }


       //分类型listview
     /*  ProductAdapter productAdapter = new ProductAdapter(products);
        lvProductList.setAdapter(productAdapter);
*/

        //通用方式一：暴露getView()供子类实现。开发中可以使用
        ProductAdapter1 productAdapter = new ProductAdapter1(products);
        lvProductList.setAdapter(productAdapter);

        //通用方式二：既使用了ViewHolder，同时实现了更简洁的抽取
        ProductAdapter2 productAdapter2 = new ProductAdapter2(products);
        lvProductList.setAdapter(productAdapter2);

    }

    @Override
    protected void initTitle() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product_list;
    }
}
