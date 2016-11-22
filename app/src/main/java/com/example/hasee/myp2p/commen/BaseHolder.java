package com.example.hasee.myp2p.commen;

import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by lzq on 2016/11/15.
 */
public abstract  class BaseHolder <T> {
    private View rootView;
    private T data;

    public BaseHolder() {
        rootView = initView();
        rootView.setTag(this);
        ButterKnife.bind(this,rootView);
    }

    public View getRootView() {
        return rootView;
    }

    public void setRootView(View rootView) {
        this.rootView = rootView;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
        refreshData();
    }
    //装配数据
    protected abstract void refreshData();

    //提供item layout
    public abstract  View initView();
}
