package com.xueyu.base;

import java.io.File;
import android.app.Application;
import android.content.Context;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.xueyu.cardphoto.R;

/**
 * @author 陈学宇
 * @version 创建时间：2015年3月12日 下午5:25:00
 */
public class MyApplication extends Application {
	
	public static MyApplication myApp;
	@Override
	public void onCreate() {
		super.onCreate();
		myApp=this;
		initImageLoader(getApplicationContext());
	}
	/** 初始化ImageLoader */
	public void initImageLoader(Context context) {
		File cacheDir = StorageUtils.getOwnCacheDirectory(context, "CardPhoto/Cache");
		DisplayImageOptions defaultOptions = new DisplayImageOptions
				.Builder()
				.showImageForEmptyUri(R.drawable.icon_template)
				.showImageOnFail(R.drawable.icon_template)
				.imageScaleType(ImageScaleType.EXACTLY)//图像将完全按比例缩小的目标大小
				.cacheInMemory(true)
				.cacheOnDisc(true)
				.build();
		// 获取到缓存的目录地址
		// 创建配置ImageLoader(所有的选项都是可选的,只使用那些你真的想定制)，这个可以设定在APPLACATION里面，设置为全局的配置参数
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.defaultDisplayImageOptions(defaultOptions)
				// 线程池内加载的数量
				.threadPoolSize(3).threadPriority(Thread.NORM_PRIORITY - 2)
				.memoryCache(new WeakMemoryCache())
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				// 将保存的时候的URI名称用MD5 加密
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.discCache(new UnlimitedDiscCache(cacheDir))// 自定义缓存路径
				// .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.writeDebugLogs() // Remove for release app
				.build();
		ImageLoader.getInstance().init(config);// 全局初始化此配置
	}
}
