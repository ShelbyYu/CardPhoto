package com.xueyu.component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.xueyu.cardphoto.R;

/**
 * Created by Shey on 2015/7/2 15:22.
 * Email:1768037936@qq.com
 */
public class SquareImageView extends ImageView {



    public SquareImageView(Context context) {
        this(context, null);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width=MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width,width);
    }

}
