package com.xueyu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.xueyu.bean.CardPhotoBean;
import com.xueyu.cardphoto.R;
import com.xueyu.component.SquareImageView;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Shey on 2015/7/7 14:24.
 * Email:1768037936@qq.com
 */
public class CategoryGridviewAdapter extends SimpleBaseAdapter<CardPhotoBean>{
    private ImageLoadingListener mAnimateFirstListener = new AnimateFirstDisplayListener();
    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {
        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
        @Override
        public void onLoadingComplete(String imageUri, View view,Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }

    public CategoryGridviewAdapter(Context context, List<CardPhotoBean> data) {
        super(context, data);
    }

    @Override
    public int getItemResource() {
        return R.layout.item_gridview_category;
    }

    @Override
    public View getItemView(int position, View convertView, ViewHolder holder) {

        SquareImageView squareImageView;
        TextView content;

        CardPhotoBean cardPhotoBean =(CardPhotoBean)getItem(position);

        squareImageView=holder.getView(R.id.item_category_imageview);
        content=holder.getView(R.id.item_category_textview);

        if (!TextUtils.isEmpty(cardPhotoBean.getContent())){
            content.setText(cardPhotoBean.getContent());
        }
        if (!TextUtils.isEmpty(cardPhotoBean.getUrl())){
            ImageLoader.getInstance().displayImage(cardPhotoBean.getUrl(),squareImageView,mAnimateFirstListener);
        }

        return convertView;
    }
}
