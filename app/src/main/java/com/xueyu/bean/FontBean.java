package com.xueyu.bean;

import android.graphics.Typeface;

import java.io.Serializable;

/**
 * Created by Shey on 2015/7/3 14:38.
 * Email:1768037936@qq.com
 */
public class FontBean implements Serializable{

    private float fontSize=15;
    private Typeface typeface;
    private int fontColor;

    public float getFontSize() {
        return fontSize;
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }

    public Typeface getTypeface() {
        return typeface;
    }

    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
    }

    public int getFontColor() {
        return fontColor;
    }

    public void setFontColor(int fontColor) {
        this.fontColor = fontColor;
    }
}
