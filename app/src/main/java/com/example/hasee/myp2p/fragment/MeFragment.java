package com.example.hasee.myp2p.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hasee.myp2p.R;
import com.example.hasee.myp2p.activity.AccountSafeActivity;
import com.example.hasee.myp2p.activity.BarChartActivity;
import com.example.hasee.myp2p.activity.ChongZhiActivity;
import com.example.hasee.myp2p.activity.LineChartActivity;
import com.example.hasee.myp2p.activity.LoginActivity;
import com.example.hasee.myp2p.activity.PieChartActivity;
import com.example.hasee.myp2p.activity.TiXianActivity;
import com.example.hasee.myp2p.activity.UserInfoActivity;
import com.example.hasee.myp2p.bean.User;
import com.example.hasee.myp2p.commen.BaseActivity;
import com.example.hasee.myp2p.commen.BaseFragment;
import com.example.hasee.myp2p.gestruelock.GestureVerifyActivity;
import com.example.hasee.myp2p.utils.BitmapUtils;
import com.example.hasee.myp2p.utils.UIUtils;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by lzq on 2016/11/11.
 */
public class MeFragment extends BaseFragment {

    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_title)
    TextView tvTopTitle;
    @Bind(R.id.iv_top_setting)
    ImageView ivTopSetting;
    @Bind(R.id.imageView1)
    ImageView imageView1;
    @Bind(R.id.icon_time)
    RelativeLayout iconTime;
    @Bind(R.id.textView11)
    TextView textView11;
    @Bind(R.id.relativeLayout1)
    RelativeLayout relativeLayout1;
    @Bind(R.id.recharge)
    ImageView recharge;
    @Bind(R.id.withdraw)
    ImageView withdraw;
    @Bind(R.id.ll_touzi)
    TextView llTouzi;
    @Bind(R.id.ll_touzi_zhiguan)
    TextView llTouziZhiguan;
    @Bind(R.id.ll_zichang)
    TextView llZichang;
    @Bind(R.id.ll_zhanquan)
    TextView llZhanquan;
    @Bind(R.id.rl_lock)
    RelativeLayout rl_lock;
    private SharedPreferences sp;
    private boolean isLock=true;

    @Override
    protected RequestParams getParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected void initData(String content) {
        sp = this.getActivity().getSharedPreferences("secret_protect",Context.MODE_PRIVATE);
        //判断是否需要进行登录的提示
        isLogin();
    }

    private void isLogin() {
        //在本应用对应的sp存储的位置，是否已经保存了用户的登录信息

        SharedPreferences sp = this.getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String userName = sp.getString("UF_ACC", "");
        if(TextUtils.isEmpty(userName)) {
            //如果没有保存：提示AlertDialog

            login();
        }else {

            //如果保存了：读取sp中的用户信息，并显示在页面上
            doUser();
        }
    }

    private void doUser() {

        //如果在本地fanx发现用户设置过手势密码，则在此时验证
        boolean isOpen = sp.getBoolean("isOpen", false);
        Log.e("sssss", "isOpen"+isOpen);
        if(isOpen){

            rl_lock.setVisibility(View.VISIBLE);
            Intent intent = new Intent(getActivity(), GestureVerifyActivity.class);
            getActivity().startActivityForResult(intent,2);
        }
        //读取数据，得到内存中的User对象
        User user = ((BaseActivity) this.getActivity()).readUser();
        //一方面，显示用户名
        textView11.setText(user.UF_ACC);

        String filePath = this.getActivity().getCacheDir() + "/tx.png";
        File file = new File(filePath);
        if(file.exists()){
            Log.e("sssss", "isOpen");
            return;
        }
        //另一方面，加载显示用户头像
        Picasso.with(getActivity()).load(user.UF_AVATAR_URL).transform(new Transformation() {
            @Override
            public Bitmap transform(Bitmap source) {//source:矩形的Bitmap对象
                //1.压缩处理
                Bitmap zoomBitmp = BitmapUtils.zoom(source, UIUtils.dp2px(62), UIUtils.dp2px(62));
                //2.圆形处理
                Bitmap bitmap = BitmapUtils.circleBitmap(zoomBitmp);
                //回收source
                source.recycle();
                return bitmap;//返回圆形的Bitmap对象

            }

            @Override
            public String key() {
                return "";
            }
        }).into(imageView1);




    }




    //未发现登录信息，提示用户登录的Dialog
    private void login() {

        new AlertDialog.Builder(getActivity())
                    .setTitle("登录")
                    .setCancelable(false)
                    .setMessage("请先登录，go go go")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MeFragment.this.getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                            //启动activity
                            ((BaseActivity)MeFragment.this.getActivity()).goToActivity(LoginActivity.class,null);
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();
        }
    /**
     * 当当前的Fragment显示时，考虑是否需要从本地读取用户头像
     */
    @Override
    public void onResume() {
        super.onResume();
        String filePath = this.getActivity().getCacheDir()+"/tx.png";
        File file = new File(filePath);
        if(file.exists()) {
            //存储--->内存

            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            imageView1.setImageBitmap(bitmap);
        }
    }

    @Override
    protected void initTitle() {
        ivTopBack.setVisibility(View.INVISIBLE);
        ivTopSetting.setVisibility(View.VISIBLE);
        tvTopTitle.setText("我的资产");
    }
    //点击“设置”的图标，启动新的用户信息的Activity
    @OnClick(R.id.iv_top_setting)
    public void setting(View view){
        ((BaseActivity)this.getActivity()).goToActivity(UserInfoActivity.class, null);
    }

    @Override
    public int getLayoutId() {

        return R.layout.fragment_me;
    }
    @OnClick(R.id.recharge)
    public void setRecharge(View view){
        ((BaseActivity)this.getActivity()).goToActivity(ChongZhiActivity.class, null);
    }

    @OnClick(R.id.withdraw)
    public void withdraw(View view){
        ((BaseActivity)this.getActivity()).goToActivity(TiXianActivity.class,null);
    }

    @OnClick(R.id.ll_touzi)
    public void startLineChart(View view){
        ((BaseActivity)this.getActivity()).goToActivity(LineChartActivity.class,null);
    }
    @OnClick(R.id.ll_touzi_zhiguan)
    public void startBarChart(View view){
        ((BaseActivity)this.getActivity()).goToActivity(BarChartActivity.class,null);
    }
    @OnClick(R.id.ll_zichang)
    public void startPieChart(View view){
        ((BaseActivity)this.getActivity()).goToActivity(PieChartActivity.class,null);
    }
    @OnClick(R.id.ll_zhanquan)
    public void startAccount(View view){
        ((BaseActivity)this.getActivity()).goToActivity(AccountSafeActivity.class,null);
    }


    public void lock(Intent data) {

       isLock =data.getBooleanExtra("isLock",true);


        Log.e("ssa", "isLock"+isLock);
        if(isLock) {
            rl_lock.setVisibility(View.VISIBLE);
        }else {
            rl_lock.setVisibility(View.GONE);
        }
    }
}
