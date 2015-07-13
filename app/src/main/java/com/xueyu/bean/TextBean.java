package com.xueyu.bean;

import java.io.Serializable;

/**
 * Created by Shey on 2015/7/7 22:26.
 * Email:1768037936@qq.com
 */
public class TextBean implements Serializable{
    private String content;
    private String author;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
