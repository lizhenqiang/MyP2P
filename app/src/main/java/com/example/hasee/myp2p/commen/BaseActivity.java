package com.example.hasee.myp2p.commen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.hasee.myp2p.bean.User;
import com.example.hasee.myp2p.utils.ActivityManager;
import com.loopj.android.http.AsyncHttpClient;

import butterknife.ButterKnife;

/**
 * 提供通用的Activity的使用的基类
 */
public abstract class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        //将当前的activity添加到ActivityManager中
        ActivityManager.getInstance().add(this);

        initTitle();
        initData();

    }
    //初始化title
    protected abstract void initTitle();

    //初始化内容数据
    protected abstract void initData();

    //提供加载的布局的方法
    protected abstract int getLayoutId();

    //销毁当前的activity
    public void removeCurrentActivity(){
        ActivityManager.getInstance().removeCurrent();
    }

    //启动新的activity
    public void goToActivity(Class activity,Bundle bundle){
        Intent intent = new Intent(this, activity);
        if(bundle != null && bundle.size() != 0){
            intent.putExtra("data",bundle);
        }

        startActivity(intent);
    }

    //销毁所有的Activity
    public void removeAll(){
        ActivityManager.getInstance().removeAll();
    }

    //使用AsyncHttpClient，实现联网的声明
    public AsyncHttpClient client = new AsyncHttpClient();

    //保存用户信息的操作:使用sp存储
    public void saveUser(User user){
        SharedPreferences sp = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("UF_ACC","强爷");
        editor.putString("UF_AVATAR_URL",user.UF_AVATAR_URL);
        editor.putString("UF_IS_CERT",user.UF_IS_CERT);
        editor.putString("UF_PHONE",user.UF_PHONE);
        editor.commit();//只有提交以后，才可以创建此文件，并保存数据
    }

    //读取数据，得到内存中的User对象
    public User readUser(){
        User user = new User();
        SharedPreferences sp = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        user.UF_ACC = sp.getString("UF_ACC","");
        user.UF_AVATAR_URL = sp.getString("UF_AVATAR_URL","");
        user.UF_IS_CERT = sp.getString("UF_IS_CERT","");
        user.UF_PHONE = sp.getString("UF_PHONE","");

        return user;
    }
}
