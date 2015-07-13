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
import com.xueyu.bean.TextBean;
import com.xueyu.cardphoto.R;
import com.xueyu.component.SquareImageView;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Shey on 2015/7/7 16:20.
 * Email:1768037936@qq.com
 */
public class TextListListviewAdapter extends SimpleBaseAdapter<TextBean>{

    public TextListListviewAdapter(Context context, List<TextBean> data) {
        super(context, data);
    }

    @Override
    public int getItemResource() {
        return R.layout.item_listview_textlist;
    }

    @Override
    public View getItemView(int position, View convertView, ViewHolder holder) {


        TextBean textBean =(TextBean)getItem(position);

        TextView content,author;

        content=holder.getView(R.id.item_textlist_content);
        author=holder.getView(R.id.item_textlist_author);

        if (!TextUtils.isEmpty(textBean.getContent())){
            content.setText(textBean.getContent());
        }
        if (!TextUtils.isEmpty(textBean.getAuthor())){
            author.setText(textBean.getAuthor());
        }

        return convertView;
    }
}

