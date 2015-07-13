package com.xueyu.utils;

import com.loopj.android.http.AsyncHttpClient;

/**
 * Created by Shey on 2015/7/7 14:57.
 * Email:1768037936@qq.com
 */
public class SingleHttClient {
    private SingleHttClient(){}

    private static AsyncHttpClient asyncHttpClient;

    public static AsyncHttpClient getInstance(){
        if (asyncHttpClient==null){
            asyncHttpClient=new AsyncHttpClient();
        }
        return asyncHttpClient;
    }
}
