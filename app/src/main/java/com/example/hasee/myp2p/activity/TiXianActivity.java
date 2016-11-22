package com.example.hasee.myp2p.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hasee.myp2p.R;
import com.example.hasee.myp2p.commen.BaseActivity;
import com.example.hasee.myp2p.utils.UIUtils;

import butterknife.Bind;
import butterknife.OnClick;


public class TiXianActivity extends BaseActivity {

    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_title)
    TextView tvTopTitle;
    @Bind(R.id.iv_top_setting)
    ImageView ivTopSetting;
    @Bind(R.id.account_zhifubao)
    TextView accountZhifubao;
    @Bind(R.id.select_bank)
    RelativeLayout selectBank;
    @Bind(R.id.chongzhi_text)
    TextView chongzhiText;
    @Bind(R.id.view)
    View view;
    @Bind(R.id.input_money)
    EditText inputMoney;
    @Bind(R.id.chongzhi_text2)
    TextView chongzhiText2;
    @Bind(R.id.textView5)
    TextView textView5;
    @Bind(R.id.btn_tixian)
    Button btnTixian;
    @Override
    protected void initTitle() {

        ivTopBack.setVisibility(View.VISIBLE);
        tvTopTitle.setText("提现");
        ivTopSetting.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void initData() {

        btnTixian.setClickable(false);
        //给EditText设置文本内容改变的监听
        inputMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i("TAG", "ChongZhiActivity beforeTextChanged()");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i("TAG", "ChongZhiActivity onTextChanged()");
            }

            @Override
            public void afterTextChanged(Editable s) {
//                Log.i("TAG", "ChongZhiActivity afterTextChanged()");
                String moneyNum = inputMoney.getText().toString().trim();
                if (TextUtils.isEmpty(moneyNum)) {//空
                    //1.设置Button的背景颜色
                    btnTixian.setBackgroundResource(R.drawable.btn_023);
                    //2.设置Button可点击性
                    btnTixian.setClickable(false);

                } else {
                    //1.设置Button的背景颜色
                    btnTixian.setBackgroundResource(R.drawable.btn_01);
                    //2.设置Button可点击性
                    btnTixian.setClickable(true);
                }
            }
        });
    }

    @OnClick(R.id.btn_tixian)
    public void withdraw(View view){
        String inputMoney = this.inputMoney.getText().toString();
        //请求服务器，将提现的数额发给指定的servlet下处理。略
        Toast.makeText(TiXianActivity.this, "您的提现请求已经发送成功，请于48小时以后查看账户转账信息", Toast.LENGTH_SHORT).show();

        //页面显示2秒以后，自动关闭
        UIUtils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                removeCurrentActivity();
            }
        },2000);
    }

    //返回键的回调方法
    @OnClick(R.id.iv_top_back)
    public void back(View view) {
        removeCurrentActivity();//结束当前的activity
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_ti_xian;
    }
}
