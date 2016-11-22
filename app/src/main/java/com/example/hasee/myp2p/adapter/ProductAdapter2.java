package com.example.hasee.myp2p.adapter;

import com.example.hasee.myp2p.commen.BaseHolder;
import com.example.hasee.myp2p.commen.MyBaseAdapter2;

import java.util.List;

/**
 * Created by lzq on 2016/11/15.
 */
public class ProductAdapter2 extends MyBaseAdapter2 {
    public ProductAdapter2(List list) {
        super(list);
    }

    @Override
    public BaseHolder getHolder() {
        return new MyHolder();
    }
}
