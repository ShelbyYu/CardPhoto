package com.xueyu.adapter;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xueyu.base.BaseFragment;
import com.xueyu.bean.FontBean;
import com.xueyu.bean.FontBeanBack;
import com.xueyu.fragment.FourFragment;
import com.xueyu.fragment.OneFragment;
import com.xueyu.fragment.ThreeFragment;
import com.xueyu.fragment.TwoFragment;

/**
 * Created by Administrator on 2015/7/2.
 */
public class FragmentAdapter extends FragmentPagerAdapter {
    public final static int TAB_COUNT = 3;

    private BaseFragment one,two,three;
    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int id) {
        switch (id) {
            case 0:
                one=new OneFragment();
                return  one;
            case 1:
                two=new TwoFragment();
                return  two;
            case 2:
                three=new ThreeFragment();
                return  three;
        }
        return null;
    }
    @Override
    public int getCount() {
        return TAB_COUNT;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "1";
            case 1:
                return "2";
            case 2:
                return "3";
        }
        return null;
    }
    public void setTypeFace(FontBean fontBean,int currentItemt){
        switch (currentItemt){
            case 0:
                one.setTypeFace(fontBean);
                break;
            case 1:
                two.setTypeFace(fontBean);
                break;
            case 2:
                three.setTypeFace(fontBean);
                break;
            default:
                break;
        }
    }
    public String getScreenshotPhotoUrl(int currentItem){
        String photoUrl=null;
        switch (currentItem){
            case 0:
                photoUrl=one.getScreenshotPhotoUrl();
                break;
            case 1:
                photoUrl=two.getScreenshotPhotoUrl();
                break;
            case 2:
                photoUrl=three.getScreenshotPhotoUrl();
                break;
            default:
                break;
        }
        return photoUrl;
    }
    public FontBean getFontBean(int currentItem){
        FontBean fontBean=new FontBean();
        switch (currentItem){
            case 0:
                fontBean=one.getFontBean();
                break;
            case 1:
                fontBean=two.getFontBean();
                break;
            case 2:
                fontBean=three.getFontBean();
                break;
            default:
                break;
        }
        return fontBean;
    }
}
