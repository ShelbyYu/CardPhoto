package com.xueyu.fragment;

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
import com.xueyu.component.SquareImageView;

/**
 * Created by Administrator on 2015/7/2.
 */
public class OneFragment extends BaseFragment implements View.OnClickListener{

    private SquareImageView squareImageView;
    private TextView content,autor;
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

        ImageLoader.getInstance().displayImage(photoUrl,squareImageView);
    }

    /**
     * 设置自己编辑的或者网络获取的文字
     * @param textBean
     */
    @Override
    protected void setText(TextBean textBean) {
        content.setText(textBean.getContent());
        autor.setText(textBean.getAuthor());
    }

    /**
     * 获取已有的文字，用于填充编辑文字的对话框
     * @return
     */
    @Override
    protected TextBean getText() {
        TextBean textBean=new TextBean();
        textBean.setContent(content.getText().toString().trim());
        textBean.setAuthor(autor.getText().toString().trim());

        return textBean;
    }

    /**
     * 设置字体
     * @param fontBean
     */
    @Override
    protected void setFontTypeFace(FontBean fontBean) {
        content.setTypeface(fontBean.getTypeface());
        autor.setTypeface(fontBean.getTypeface());

        content.setTextSize(fontBean.getFontSize());
        autor.setTextSize(fontBean.getFontSize());

        content.setTextColor(fontBean.getFontColor());
        autor.setTextColor(fontBean.getFontColor());
    }

    @Override
    protected TextView setFontView() {
        return content;
    }

    /**
     * 设置截屏的视图
     * @return
     */
    @Override
    protected View getScreenshotView() {
        return findViewById(R.id.onefragment_photoview);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_one,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();

    }
    private void initView(){
        squareImageView=(SquareImageView)findViewById(R.id.onefragment_imageview);
        squareImageView.setOnClickListener(this);

        content=(TextView)findViewById(R.id.onefragment_font_content);
        autor=(TextView)findViewById(R.id.onefragment_font_autor);
        autor.setOnClickListener(this);
        content.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.onefragment_imageview:
                openPhotoSelect();
                break;
            //点击了文字
            case R.id.onefragment_font_content:
            case R.id.onefragment_font_autor:
                openTextSelect();
                break;
            default:
                break;
        }
    }
}
