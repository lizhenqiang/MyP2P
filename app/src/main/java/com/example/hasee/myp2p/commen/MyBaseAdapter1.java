package com.example.hasee.myp2p.commen;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by lzq on 2016/11/15.
 */
public abstract class MyBaseAdapter1<T> extends BaseAdapter {
    public List<T> list;
    public MyBaseAdapter1(List<T> list){
        this.list =list;
    }
    @Override
    public int getCount() {
        return list ==null?0: list.size();
    }

    @Override
    public Object getItem(int position) {
        return list ==null?null: list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return myGetView(position,convertView,parent);
    }

    public abstract View myGetView(int position, View convertView, ViewGroup parent);
}
