package com.xueyu.bean;

import java.io.Serializable;

/**
 * Created by Shey on 2015/7/7 14:25.
 * Email:1768037936@qq.com
 */
public class CardPhotoBean implements Serializable{

    private String id;
    private String url;
    private String content;
    private String describe;
    private String autor;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
}
