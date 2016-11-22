package com.example.hasee.myp2p.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.hasee.myp2p.MainActivity;
import com.example.hasee.myp2p.R;
import com.example.hasee.myp2p.bean.UpdateInfo;
import com.example.hasee.myp2p.utils.ActivityManager;
import com.example.hasee.myp2p.utils.AppNetConfig;
import com.example.hasee.myp2p.utils.UIUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends Activity {
    private static final int MESSAGE_MAIN = 1;
    private static final int WHAT_DOWNLOAD_VERSION_SUCCESS = 2;
    private static final int WHAT_DOWNLOAD_FAIL = 3;
    private static final int WHAT_DOWNLOAD_APK_SUCCESS = 4;

    @Bind(R.id.iv_welcome_icon)
    ImageView ivWelcomeIcon;
    @Bind(R.id.tv_welcome_version)
    TextView tvWelcomeVersion;
    @Bind(R.id.rl_welcome)
    RelativeLayout rlWelcome;
    private long startTime;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what) {
                case MESSAGE_MAIN :
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    break;

                case WHAT_DOWNLOAD_VERSION_SUCCESS://获取了服务器端返回的版本信息
                    String version = getVersion();
                    if(version.equals(updateInfo.version)){//版本相同
                        Log.e("mmmm", "5555555555555");
                        toMain();
                    }else{
                        Log.e("mmmm", "6666666");
                        showDownloadDialog();
                    }
                    break;
                case WHAT_DOWNLOAD_FAIL:
                    Toast.makeText(SplashActivity.this, "下载应用文件失败", Toast.LENGTH_SHORT).show();
                    toMain();
                    break;
                case WHAT_DOWNLOAD_APK_SUCCESS:
                    installApk();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去掉窗口标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏顶部的状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);
        //将当前的activity添加到ActivityManager中
        ActivityManager.getInstance().add(this);


        ButterKnife.bind(this);



        //启动动画
        startAnimation();
        //联网更新应用的操作
        updateApp();


    }

    /**
     * 安装apk
     */
    private void installApk() {
        Intent intent = new Intent("android.intent.action.INSTALL_PACKAGE");
        intent.setData(Uri.parse("file:" + apkFile.getAbsolutePath()));
        startActivity(intent);
    }

    private ProgressDialog dialog;
    private File apkFile;

    /**
     * 显示是否需要联网下载最新版本apk的Dialog
     */
    private void showDownloadDialog() {
        new AlertDialog.Builder(this)
                .setTitle("下载最新版本")
                .setMessage(updateInfo.desc)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showDownLoad();
                    }

                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        toMain();
                    }
                })
                .show();
    }

    /**
     * 联网下载指定url地址对应的apk文件
     * 1.提供ProgressDialog
     * 2.提供本地的存储文件
     * 3.联网下载数据
     * 4.安装
     */
    private void showDownLoad() {
        //1.提供ProgressDialog
        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//水平显示
        dialog.setCancelable(false);
        dialog.show();
        //2.提供本地的存储文件:sd卡路径1
        String filePath = this.getExternalFilesDir(null) + "/update_app.apk";
        apkFile = new File(filePath);
        //3.联网下载数据
        new Thread(){
            public void run(){
                try {
                    downloadAPk();
                    handler.sendEmptyMessage(WHAT_DOWNLOAD_APK_SUCCESS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    private void downloadAPk() throws Exception {
        FileOutputStream fos = new FileOutputStream(apkFile);
        String path = updateInfo.apkUrl;
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        conn.setRequestMethod("GET");

        conn.connect();
        if(conn.getResponseCode()==200){
            InputStream is = conn.getInputStream();
            dialog.setMax(conn.getContentLength());
            byte[] buffer = new byte[1024];
            int len;
            while((len = is.read(buffer)) != -1){
                fos.write(buffer,0,len);

                dialog.incrementProgressBy(len);
                Thread.sleep(2);
            }

            //暂且使用throws的方式处理异常了
            is.close();
            fos.close();
        }else{
            handler.sendEmptyMessage(WHAT_DOWNLOAD_FAIL);
        }

        //关闭连接
        conn.disconnect();
    }

    private UpdateInfo updateInfo;

    private void startAnimation() {
        Log.e("TAG", "zhixingle");
        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);

        alphaAnimation.setFillAfter(true);
        alphaAnimation.setDuration(2000);

       /* alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });*/


        rlWelcome.startAnimation(alphaAnimation);

    }
    private void updateApp() {
        //获取系统当前的时间
        startTime = System.currentTimeMillis();

        boolean isConnected = isConnected();
        if(!isConnected){
            Log.e("mmmm", "111111111111111");
             toMain();
        }else{
            Log.e("mmmm", "22222222222222222");
            String updateUrl = AppNetConfig.UPDATE;//获取联网请求的路径
            //使用AsyncHttpClient实现联网获取版本信息
            AsyncHttpClient client = new AsyncHttpClient();
            client.post(updateUrl,new AsyncHttpResponseHandler(){
                @Override
                public void onSuccess(String content) {
                    Log.e("mmmm", "333333333333333333");
                    Log.e("mmmm", "content"+content);
                    //使用fastjson解析json数据
                    updateInfo = JSON.parseObject(content, UpdateInfo.class);
                    handler.sendEmptyMessage(WHAT_DOWNLOAD_VERSION_SUCCESS);
                }

                @Override
                public void onFailure(Throwable error, String content) {
                    Log.e("mmmm", "4444444444444444");
                    Toast.makeText(SplashActivity.this, "联网获取更新数据失败", Toast.LENGTH_SHORT).show();
                    toMain();

                }
            });
        }


    }

    /**
     * 通过发送延迟消息，进入主界面
     */
    private void toMain() {
        long currentTimeMillis = System.currentTimeMillis();
        long delayTime = 3000 - (currentTimeMillis - startTime);
        if(delayTime < 0){
            delayTime = 0;
        }

        //发送延迟消息
        handler.sendEmptyMessageDelayed(MESSAGE_MAIN,delayTime);
    }

    /**
     * 判断是否可以联网
     * @return
     */
    private boolean isConnected() {
        boolean connected = false;

        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null) {
            connected = networkInfo.isConnected();
        }
        return connected;

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        UIUtils.getHandler().removeCallbacksAndMessages(null);
    }

    /**
     * 当前版本号
     * @return
     */
    private String getVersion() {
        String version = "未知版本";
        PackageManager manager = getPackageManager();
        try {
            PackageInfo packageInfo = manager.getPackageInfo(getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            //e.printStackTrace(); //如果找不到对应的应用包信息, 就返回"未知版本"
        }
        return version;
    }


}
