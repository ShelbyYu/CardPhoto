package com.xueyu.fragment;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xueyu.base.BaseFragment;
import com.xueyu.bean.FontBean;
import com.xueyu.cardphoto.R;

/**
 * Created by Shey on 2015/7/2 15:05.
 * Email:1768037936@qq.com
 */
public class FourFragment extends BaseFragment{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * 设置选中的图片
     * @param photo
     */
    @Override
    protected void setPhotoBackground(Bitmap photo) {

    }

    @Override
    protected void setFontTypeFace(FontBean fontBean) {

    }

    @Override
    protected TextView setFontView() {
        return null;
    }

    /**
     * 设置截屏的视图
     * @return
     */
    @Override
    protected View getScreenshotView() {
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_four,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
