package com.example.hasee.myp2p.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.hasee.myp2p.R;
import com.example.hasee.myp2p.commen.BaseActivity;
import com.example.hasee.myp2p.gestruelock.GestureEditActivity;

import butterknife.Bind;
import butterknife.OnClick;

public class AccountSafeActivity extends BaseActivity {

    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_title)
    TextView tvTopTitle;
    @Bind(R.id.iv_top_setting)
    ImageView ivTopSetting;
    @Bind(R.id.toggleButton)
    ToggleButton toggleButton;
    @Bind(R.id.btn_resetGesture)
    Button btnResetGesture;


    @Override
    protected void initTitle() {

        ivTopBack.setVisibility(View.VISIBLE);
        tvTopTitle.setText("账户安全设置");
        ivTopSetting.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.iv_top_back)
    public void back(View view) {
        removeCurrentActivity();
    }



    private SharedPreferences sp;
    @Override
    protected void initData() {

        sp=this.getSharedPreferences("secret_protect", Context.MODE_PRIVATE);
        //读取当前的toggleButton的状态并显示
        boolean isOpen = sp.getBoolean("isOpen", false);
        toggleButton.setChecked(isOpen);
        btnResetGesture.setEnabled(isOpen);//设置button的可操作性
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    //判断本地是否设置过手势密码
                    String inputCode = sp.getString("inputCode", "");
                    if(TextUtils.isEmpty(inputCode)) {
                        new AlertDialog.Builder(AccountSafeActivity.this)
                                    .setTitle("是否现在设置手势密码")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            Toast.makeText(AccountSafeActivity.this, "现在设置手势密码", Toast.LENGTH_SHORT).show();

                                            btnResetGesture.setEnabled(true);
                                            sp.edit().putBoolean("isOpen", true).commit();

                                            sp.edit().putBoolean("isLock",true).commit();
                                            goToActivity(GestureEditActivity.class, null);

                                        }
                                    })
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(AccountSafeActivity.this, "取消了手势密码设置", Toast.LENGTH_SHORT).show();
                                            toggleButton.setChecked(false);
                                            btnResetGesture.setEnabled(false);
                                            sp.edit().putBoolean("isOpen", false).commit();
                                            sp.edit().putBoolean("isLock",false).commit();
                                        }
                                    })
                                    .show();
                    }
                    else {//之前设置过
                        Toast.makeText(AccountSafeActivity.this, "开启手势解锁", Toast.LENGTH_SHORT).show();
//                        toggleButton.setChecked(true);
                        btnResetGesture.setEnabled(true);
                        sp.edit().putBoolean("isOpen", true).commit();

                    }
                }else {
                    Toast.makeText(AccountSafeActivity.this, "关闭手势解锁", Toast.LENGTH_SHORT).show();
                    //  toggleButton.setChecked(false);
                    sp.edit().putBoolean("isOpen", false).commit();
                    btnResetGesture.setEnabled(false);

                }
            }
        });

        //给页面中的“button"设置重置密码的点击事件
        btnResetGesture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(GestureEditActivity.class,null);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_safe;
    }
}
