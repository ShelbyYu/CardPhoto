package com.xueyu.fragment;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.xueyu.base.BaseFragment;
import com.xueyu.bean.FontBean;
import com.xueyu.bean.TextBean;
import com.xueyu.cardphoto.R;
import com.xueyu.component.CircleImageView;
import com.xueyu.component.SquareImageView;

/**
 * Created by Shey on 2015/7/2 15:05.
 * Email:1768037936@qq.com
 */
public class ThreeFragment extends BaseFragment implements View.OnClickListener{

    private SquareImageView squareImageView;
    private TextView content,author;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    /**
     * 设置选中的图片
     * @param photoUrl
     */
    @Override
    protected void setPhotoBackground(String  photoUrl) {
//        Drawable drawable =new BitmapDrawable(photo);
//        squareImageView.setBackgroundDrawable(drawable);
//        drawable=null;

        if(!photoUrl.contains("http://")){
            photoUrl = ImageDownloader.Scheme.FILE.wrap(photoUrl);
        }

        ImageLoader.getInstance().displayImage(photoUrl, squareImageView);
    }
    /**
     * 设置字体
     * @param fontBean
     */
    @Override
    protected void setFontTypeFace(FontBean fontBean) {
        content.setTypeface(fontBean.getTypeface());
        content.setTextSize(fontBean.getFontSize());
        content.setTextColor(fontBean.getFontColor());

        author.setTypeface(fontBean.getTypeface());
        author.setTextSize(fontBean.getFontSize());
        author.setTextColor(fontBean.getFontColor());
    }

    @Override
    protected TextView setFontView() {
        return content;
    }


    /**
     * 设置自己编辑的或者网络获取的文字
     * @param textBean
     */
    @Override
    protected void setText(TextBean textBean) {
        content.setText(textBean.getContent());
        author.setText(textBean.getAuthor());
    }

    /**
     * 获取已有的文字，用于填充编辑文字的对话框
     * @return
     */
    @Override
    protected TextBean getText() {
        TextBean textBean=new TextBean();
        textBean.setContent(content.getText().toString().trim());
        textBean.setAuthor(author.getText().toString().trim());

        return textBean;
    }
    /**
     * 设置截屏的视图
     * @return
     */
    @Override
    protected View getScreenshotView() {
        return findViewById(R.id.threefragment_cardphotoview);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_three,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }
    private void initView(){
        squareImageView=(SquareImageView)findViewById(R.id.threefragment_imageview);
        squareImageView.setOnClickListener(this);

        author=(TextView)findViewById(R.id.threefragment_font_autor);
        author.setOnClickListener(this);
        content=(TextView)findViewById(R.id.threefragment_font_content);
        content.setOnClickListener(this);
    }

    EditText dialog_content;//对话框里面的编辑框
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.threefragment_imageview:
                openPhotoSelect();
                break;
            //点击了文字
            case R.id.threefragment_font_content:
            case R.id.threefragment_font_autor:
                openTextSelect();
                break;
            default:
                break;
        }
    }
}
