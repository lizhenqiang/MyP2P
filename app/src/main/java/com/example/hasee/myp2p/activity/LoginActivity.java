package com.example.hasee.myp2p.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.hasee.myp2p.MainActivity;
import com.example.hasee.myp2p.R;
import com.example.hasee.myp2p.bean.User;
import com.example.hasee.myp2p.commen.BaseActivity;
import com.example.hasee.myp2p.utils.AppNetConfig;
import com.example.hasee.myp2p.utils.MD5Utils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import butterknife.Bind;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_title)
    TextView tvTopTitle;
    @Bind(R.id.iv_top_setting)
    ImageView ivTopSetting;
    @Bind(R.id.textView1)
    TextView textView1;
    @Bind(R.id.log_ed_mob)
    EditText logEdMob;
    @Bind(R.id.about_com)
    RelativeLayout aboutCom;
    @Bind(R.id.tv_2)
    TextView tv2;
    @Bind(R.id.log_ed_pad)
    EditText logEdPad;
    @Bind(R.id.log_log_btn)
    Button logLogBtn;
    @OnClick(R.id.iv_top_back)
    public void back(View view){
//        this.removeCurrentActivity();
        this.removeAll();
        goToActivity(MainActivity.class,null);
    }
    @OnClick(R.id.log_log_btn)
    public void login(View view){
        //1.获取手机号和加密以后的密码
        String number = logEdMob.getText().toString().trim();
        String password = logEdPad.getText().toString().trim();
        //判断输入的信息是否存在空
        if(!TextUtils.isEmpty(number)&&!TextUtils.isEmpty(password)) {
            //2.联网将用户数据发送给服务器，其中手机号和密码作为请求参数

            String url = AppNetConfig.LOGIN;
            RequestParams params = new RequestParams();
            params.put("username",number);
            params.put("password", MD5Utils.MD5(password));

            client.post(url,params,new AsyncHttpResponseHandler(){
                @Override
                public void onSuccess(String content) {
                    super.onSuccess(content);
                    Log.e("dddd", "onSuccess");
                    //3.1解析json数据
                    JSONObject jsonObject = JSON.parseObject(content);
                    boolean isSuccess = jsonObject.getBoolean("success");
                    if(isSuccess){

                        String data = jsonObject.getString("data");
                        User user = JSON.parseObject(data, User.class);
                        //3.2保存得到的用户信息（使用sp存储）
                        Log.e("ddddd", ""+user.UF_ACC);
                        saveUser(user);
                        //3.3重新加载页面，显示用户的信息在MeFragment中
                        LoginActivity.this.removeAll();
                        LoginActivity.this.goToActivity(MainActivity.class,null);

                    }else {
                        Toast.makeText(LoginActivity.this, "用户名不存在或密码不正确", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Throwable error, String content) {
                    Log.e("ddddd", "onFailure");
                    super.onFailure(error, content);
                }

            });

        }
    }

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activty);
    }
*/
    @Override
    protected void initTitle() {
        Log.e("sss", "用户登录");
        ivTopBack.setVisibility(View.VISIBLE);
        tvTopTitle.setText("用户登录");
        ivTopSetting.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_activty;
    }
}
