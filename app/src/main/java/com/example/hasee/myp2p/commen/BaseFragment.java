package com.example.hasee.myp2p.commen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hasee.myp2p.ui.LoadingPage;
import com.loopj.android.http.RequestParams;

import butterknife.ButterKnife;

/**
 * Created by lzq on 2016/11/11.
 */
public abstract class BaseFragment extends Fragment {
    public LoadingPage loadingPage;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("AAA", "onCreateView");
        loadingPage = new LoadingPage(getActivity()) {
            @Override
            public int layoutId() {
                Log.e("AAA", "layoutId");
                return getLayoutId();
            }

            @Override
            protected void onSuccess(ResultState resultState, View view_success) {

                Log.e("AAA", "onSuccess");
                ButterKnife.bind(BaseFragment.this, view_success);//别忘了绑定布局

                initTitle();


                initData(resultState.getContent());

            }

            @Override
            protected RequestParams params() {
                return getParams();
            }

            @Override
            protected String url() {
                return getUrl();
            }
        };

        return loadingPage;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        show();
    }
    protected abstract RequestParams getParams();

    protected abstract String getUrl();

    protected abstract void initData(String content);

    protected abstract void initTitle();

    public abstract int getLayoutId();



    //实现联网操作
    public void show(){


        loadingPage.show();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
