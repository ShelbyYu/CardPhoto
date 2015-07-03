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
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.xueyu.base.BaseFragment;
import com.xueyu.bean.FontBean;
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
     * @param photo
     */
    @Override
    protected void setPhotoBackground(Bitmap photo) {
        Drawable drawable =new BitmapDrawable(photo);
        squareImageView.setBackgroundDrawable(drawable);
        drawable=null;
        System.gc();
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

    EditText dialog_content,dialog_autor;//对话框里面的编辑框
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.onefragment_imageview:
                openPhotoSelect();
                break;
            //点击了文字
            case R.id.onefragment_font_content:
            case R.id.onefragment_font_autor:
                MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .customView(R.layout.dialog_select_font)
                .positiveText(R.string.sure)
                .negativeText(R.string.cancel)
                .cancelable(false)
                .callback(new MaterialDialog.Callback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        if (TextUtils.isEmpty(dialog_content.getText().toString().trim())) {
                            toast("请写上装逼文字");
                            return;
                        } else {
                            if (TextUtils.isEmpty(dialog_autor.getText().toString().trim())) {
                                toast("请署名");
                                return;
                            } else {
                                content.setText(dialog_content.getText().toString().trim());
                                autor.setText(dialog_autor.getText().toString().trim());
                                dialog.dismiss();
                            }
                        }
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                }).build();

                dialog_content=(EditText)dialog.getCustomView().findViewById(R.id.dialog_content);
                dialog_autor=(EditText)dialog.getCustomView().findViewById(R.id.dialog_autor);
                dialog_content.setText(content.getText().toString());
                dialog_autor.setText(autor.getText().toString());
                Editable etext = dialog_content.getText();
                Selection.setSelection(etext, etext.length());

                dialog.show();
                break;
            default:
                break;
        }
    }
}
