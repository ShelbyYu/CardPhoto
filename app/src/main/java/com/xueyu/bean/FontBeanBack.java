package com.xueyu.bean;


import java.io.Serializable;

/**
 * Created by Shey on 2015/7/3 17:44.
 * Email:1768037936@qq.com
 */
public class FontBeanBack implements Serializable{
    private float fontSize=15.0f;
    private int fontColor;

    public float getFontSize() {
        return fontSize;
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }

    public int getFontColor() {
        return fontColor;
    }

    public void setFontColor(int fontColor) {
        this.fontColor = fontColor;
    }

}
