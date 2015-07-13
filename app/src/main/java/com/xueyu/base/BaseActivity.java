package com.xueyu.base;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by Shey on 2015/7/2 18:20.
 * Email:1768037936@qq.com
 */
public class BaseActivity extends ActionBarActivity {
    public Context context;
    public String webUrl="http://www.jcodecraeer.com";
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        this.context=this;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
    /**
     * 显示吐司
     * @param 信息
     * @param 时间
     */
    Toast mToast;
    protected void toast(String info) {
        if (mToast == null) {
            mToast = Toast.makeText(this, info, Toast.LENGTH_LONG);
        } else {
            mToast.setText(info);
        }
        mToast.show();
    }

    /**
     * 跳转Activity，无数据参数
     *
     * @param classObj,isClose
     */
    protected void forward(Class<?> classObj,boolean isFinish) {
        Intent intent = new Intent();
        intent.setClass(this, classObj);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
        if(isFinish) this.finish();
    }
    /**
     * 跳转Activity，有数据参数
     *
     * @param classObj
     * @param params
     * @param isFinish
     */
    protected void forward(Class<?> classObj, Bundle params,boolean isFinish) {
        Intent intent = new Intent();
        intent.setClass(this, classObj);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtras(params);
        this.startActivity(intent);
        if(isFinish) this.finish();
    }
}

