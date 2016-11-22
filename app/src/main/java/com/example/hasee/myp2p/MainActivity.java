package com.example.hasee.myp2p;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.hasee.myp2p.commen.BaseActivity;
import com.example.hasee.myp2p.commen.BaseFragment;
import com.example.hasee.myp2p.fragment.HomeFragment;
import com.example.hasee.myp2p.fragment.InvestFragment;
import com.example.hasee.myp2p.fragment.MeFragment;
import com.example.hasee.myp2p.fragment.MoreFragment;

import java.util.ArrayList;

import butterknife.Bind;

public class MainActivity extends BaseActivity {
    public  MeFragment meFragment;

    private static final int MESSAGE_BACK = 1;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            isFlag=true;
        }
    };
    private boolean isFlag=true;
    @Bind(R.id.fl_main)
    FrameLayout flMain;
    @Bind(R.id.rb_main_home)
    RadioButton rbMainHome;
    @Bind(R.id.rb_main_invest)
    RadioButton rbMainInvest;
    @Bind(R.id.rb_main_me)
    RadioButton rbMainMe;
    @Bind(R.id.rb_main_more)
    RadioButton rbMainMore;
    @Bind(R.id.rg_main)
    RadioGroup rgMain;
    private ArrayList<BaseFragment> fragments;
    private int position;
    private BaseFragment fragment;

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        initFragment();
        initListener();
        initData();


    }*/



    @Override
    protected void initTitle() {

    }

    @Override
    protected void initData() {
        initFragment();
        initListener();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    private void initListener() {
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_main_home:

                        position=0;
                        break;
                    case R.id.rb_main_invest:
                        position=1;

                        break;
                    case R.id.rb_main_me:
                        position=2;

                        break;
                    case R.id.rb_main_more:
                        position=3;

                        break;

                }


                BaseFragment toFragment = fragments.get(position);
                changeFragment(fragment,toFragment);
            }
        });
        rgMain.check(R.id.rb_main_home);

    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new InvestFragment());

        meFragment = new MeFragment();
        fragments.add(meFragment);
        fragments.add(new MoreFragment());
    }
    private void changeFragment(BaseFragment fromFragment,BaseFragment toFragment) {

        if(toFragment!=fromFragment) {
            if(toFragment!=null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();



                if(!toFragment.isAdded()) {
                    transaction.add(R.id.fl_main,toFragment).commit();
                    if(fromFragment!=null) {
                        transaction.hide(fromFragment);
                    }
                }else {
                    transaction.show(toFragment).commit();
                    if(fromFragment!=null) {
                        transaction.hide(fromFragment);
                    }
                }
                fragment= toFragment;
            }
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if(keyCode== KeyEvent.KEYCODE_BACK&&isFlag) {
            isFlag=false;
            Toast.makeText(MainActivity.this, "再点击一次退出应用", Toast.LENGTH_SHORT).show();
            handler.sendEmptyMessageDelayed(MESSAGE_BACK,2000);
            return true;
        }
        return super.onKeyUp(keyCode, event);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("uuuuu", "uuuu");
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1&&data!=null) {
            meFragment.lock(data);
        }

    }
}
