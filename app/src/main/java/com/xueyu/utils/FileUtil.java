package com.xueyu.utils;

import android.os.Environment;
import java.io.File;

/**
 * @author 陈学宇
 * @version 创建时间：2015年6月9日 下午8:28:37
 */
public class FileUtil {

	/**
	 * 获取应用的主路径
	 * @return
	 */
	public static String getMainPath(){
		String path=null;
		String extStatus=Environment.getExternalStorageState();
		File extFile=Environment.getExternalStorageDirectory();
		if(extStatus.equals(Environment.MEDIA_MOUNTED)){
			path=extFile.getAbsolutePath()+"/CardPhoto";
		}else {
			path="/mnt/sdcard/CardPhoto";
		}

		return path;
	}

	/**
	 * 获取缓存路径
	 * @return
	 */
	public static File getMainFile(){

		File file=new File(getMainPath());
		if (!file.exists()){
			file.mkdirs();
		}

		return file;
	}

	/**
	 * 获取文件的类型
	 * @param url
	 * @return
	 */
	public static String getFileType(String url){
		String type=".jpeg";
		
		if(url!=null&&url.length()>0){
			int x=url.lastIndexOf(".");
			if(x>-1&&(x<url.length()-1)){
				type=url.substring(x+1);
				return type;
			}
		}
		return type;
	}
}
