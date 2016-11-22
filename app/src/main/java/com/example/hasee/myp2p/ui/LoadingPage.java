package com.example.hasee.myp2p.ui;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.hasee.myp2p.R;
import com.example.hasee.myp2p.utils.UIUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by lzq on 2016/11/14.
 */
public abstract class LoadingPage extends FrameLayout {
    private Context mContext;

    //1.提供联网操作的4种状态
    private final int STATE_LOADING = 1;//加载状态
    private final int STATE_ERROR = 2;//联网失败的状态
    private final int STATE_EMPTY = 3;//联网成功，但是返回数据为空的状态
    private final int STATE_SUCCESS = 4;//联网成功，且正确返回数据的状态

    private int state_current = STATE_LOADING;//表示当前的状态


    //2.提供4个不同的页面
    private View view_loading;
    private View view_error;
    private View view_empty;
    private View view_success;

    public LoadingPage(Context context) {
        this(context,null);
    }
    public LoadingPage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        init();
    }

    private void init(){
        //初始化必要的View
        //指明视图显示宽高的参数
        LayoutParams  params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);

        if(view_loading==null) {
            view_loading= UIUtils.getView(R.layout.page_loading);
            addView(view_loading,params);
        }
        if (view_error == null) {
            view_error = UIUtils.getView(R.layout.page_error);
            addView(view_error, params);
        }
        if (view_empty == null) {
            view_empty = UIUtils.getView(R.layout.page_empty);
            addView(view_empty, params);
        }

        //根据state_current的值，决定显示哪个具体的View
        showSafePage();
    }

    private void showSafePage() {
        //更新界面的操作需要在主线程中执行
        UIUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //加载显示具体的View
                showPage();
            }
        });
    }

    private void showPage() {
        view_loading.setVisibility(state_current==STATE_LOADING?VISIBLE:GONE);
        view_empty.setVisibility(state_current==STATE_EMPTY?VISIBLE:GONE);
        view_error.setVisibility(state_current==STATE_ERROR?VISIBLE:GONE);

        if(view_success==null) {
            Log.e("AAA", "讲道理这个位置执行了");
            //view_success = UIUtils.getView(layoutId());
            view_success = View.inflate(mContext,layoutId(),null);
            addView(view_success);
        }
        view_success.setVisibility(state_current == STATE_SUCCESS ?VISIBLE : GONE);
    }

    public abstract int layoutId();
    private ResultState resultState;

    //执行联网操作
    public void show(){
        String url = url();
        if(TextUtils.isEmpty(url)) {
            resultState = ResultState.SUCCESS;
            resultState.setContent("");
            loadPage();
            return;
            
        }

        //模拟联网操作的延迟
        UIUtils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AsyncHttpClient client = new AsyncHttpClient();
                client.get(url(), params(), new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String content) {
                        if (TextUtils.isEmpty(content)) {//"" 或 null
                            resultState = ResultState.EMPTY;
                            resultState.setContent("");
                        } else {
                            resultState = ResultState.SUCCESS;
                            resultState.setContent(content);
                        }

                        loadPage();
                    }

                    @Override
                    public void onFailure(Throwable error, String content) {
//                state_current = STATE_ERROR;
                        resultState = ResultState.ERROR;
                        resultState.setContent("");
                        loadPage();
                    }
                });
            }
        }, 2000);

    }

    private void loadPage() {
        switch (resultState) {
            case ERROR:
                state_current = STATE_ERROR;
                break;
            case EMPTY:
                state_current = STATE_EMPTY;
                break;
            case SUCCESS:
                state_current = STATE_SUCCESS;
                break;
        }

        showSafePage();

        if (state_current == STATE_SUCCESS) {//如果当前是联网成功的状态
            onSuccess(resultState, view_success);
        }

    }

    protected abstract void onSuccess(ResultState resultState, View view_success);

    protected abstract RequestParams params();

    protected abstract String url();

    //提供一个枚举类:将当前联网以后的状态以及可能返回的数据，封装在枚举类中
    public enum ResultState {

        ERROR(2), EMPTY(3), SUCCESS(4);

        int state;
        private String content;

        ResultState(int state) {
            this.state = state;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
