package com.xueyu.base;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.xueyu.bean.FontBean;
import com.xueyu.bean.FontBeanBack;
import com.xueyu.bean.TextBean;
import com.xueyu.cardphoto.MainActivity;
import com.xueyu.cardphoto.PhotoCategoryActivity;
import com.xueyu.cardphoto.R;
import com.xueyu.cardphoto.TextCategoryActivity;
import com.xueyu.utils.FileUtil;
import com.xueyu.utils.PhotoUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2015/7/2.
 */
public abstract  class BaseFragment extends Fragment {

    protected View contentView;
    public Context context;
    public LayoutInflater mInflater;

    private Handler handler = new Handler();

    public void runOnUiThread(Runnable action) {
        handler.post(action);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        context=getActivity();
        mInflater = LayoutInflater.from(context);
    }

    public static final int REQUESTCODE_UPLOADAVATAR_CAMERA = 1;//拍照修改头像
    public static final int REQUESTCODE_UPLOADAVATAR_LOCATION = 2;//本地相册修改头像
    public static final int REQUESTCODE_UPLOADAVATAR_CROP = 3;//系统裁剪头像

    protected String cardPhotoUrl= FileUtil.getMainPath(),takePhotoUrl=null;
    /**
     * 打开添加图片选择的对话框
     */
    public void openPhotoSelect(){
        new MaterialDialog.Builder(getActivity())
                .items(R.array.photo)
                .cancelable(false)
                .negativeText("取消")
                .negativeColor(context.getResources().getColor(R.color.green))
                .itemsCallback(new MaterialDialog.ListCallback() {

                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        switch (which) {
                            //拍照
                            case 0:
                                //每点击拍照，都会重新生成图片的路径
                                takePhotoUrl = FileUtil.getMainFile() + "/" + new SimpleDateFormat("yyMMddHHmmss").format(new Date()) + ".jpeg";
                                File file = new File(takePhotoUrl);

                                Uri imageUri = Uri.fromFile(file);
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                                startActivityForResult(intent, REQUESTCODE_UPLOADAVATAR_CAMERA);

                                dialog.dismiss();
                                break;
                            //本地相册
                            case 1:
                                Intent intentLocal = new Intent(Intent.ACTION_PICK, null);
                                intentLocal.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                startActivityForResult(intentLocal, REQUESTCODE_UPLOADAVATAR_LOCATION);
                                dialog.dismiss();
                                break;
                            //精选
                            case 2:
                                Intent intent1=new Intent(context, PhotoCategoryActivity.class);
                                startActivityForResult(intent1,4);
                                dialog.dismiss();
                                break;
                            default:
                                break;
                        }
                    }
                }).build().show();
    }
    /**
     * 打开添加文字选择的对话框
     */
    EditText dialog_content,dialog_autor;//对话框里面的编辑框
    public void openTextSelect(){
        new MaterialDialog.Builder(getActivity())
                .items(R.array.text)
                .cancelable(false)
                .negativeText("取消")
                .negativeColor(context.getResources().getColor(R.color.green))
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog mainDialog, View itemView, int which, CharSequence text) {
                        switch (which) {
                            //自己编辑
                            case 0:
                                mainDialog.dismiss();
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
                                                    //可以不署名
                                                    TextBean textBean=new TextBean();
                                                    textBean.setContent(dialog_content.getText().toString().trim());
                                                    textBean.setAuthor(dialog_autor.getText().toString().trim());
                                                    setText(textBean);
                                                    dialog.dismiss();
                                                }
                                            }

                                            @Override
                                            public void onNegative(MaterialDialog dialog) {
                                                dialog.dismiss();
                                            }
                                        }).build();
                                dialog_content = (EditText) dialog.getCustomView().findViewById(R.id.dialog_content);
                                dialog_autor = (EditText) dialog.getCustomView().findViewById(R.id.dialog_autor);
                                dialog_content.setText(getText().getContent());
                                dialog_autor.setText(getText().getAuthor());
                                Editable etext = dialog_content.getText();
                                Selection.setSelection(etext, etext.length());

                                dialog.show();

                                break;
                            //精选
                            case 1:
                                Intent intent1 = new Intent(context, TextCategoryActivity.class);
                                startActivityForResult(intent1, 5);
                                mainDialog.dismiss();
                                break;
                            default:
                                break;
                        }
                    }
                }).build().show();
    }

    /**
     * 设置选中的图片
     * @param photoUrl
     */
    protected abstract void setPhotoBackground(String photoUrl);

    /**
     * 设置文字
     * @param textBean
     */
    protected abstract void setText(TextBean textBean);

    /**
     * 获取文字内容
     * @return
     */
    protected abstract TextBean getText();
    /**
     * 设置文字字体
     * @param fontBean
     */
    protected abstract void setFontTypeFace(FontBean fontBean);
    public void setTypeFace(FontBean fontBean){
        setFontTypeFace(fontBean);
    }

    /**
     * 在继承的子Fragment里设置显示字体的TextView
     * @return
     */
    protected abstract TextView setFontView();

    /**
     * 获取字体的数据
     * @return
     */
    public FontBean getFontBean(){
        FontBean fontBean=new FontBean();
        TextView textView=setFontView();

        fontBean.setFontSize(textView.getTextSize());
        fontBean.setFontColor(textView.getCurrentTextColor());
        fontBean.setTypeface(textView.getTypeface());

        return fontBean;
    }

    protected abstract View getScreenshotView();//获取要截屏的视图
    /**
     * 获取截屏图片的路径
     * @return
     */
    public String getScreenshotPhotoUrl(){
        if(!cardPhotoUrl.contains(".jpeg")){
            //如果没有截图后的路径，就自动生成路径
            cardPhotoUrl=FileUtil.getMainPath()+"/"+new SimpleDateFormat("yyMMddHHmmss").format(new Date())+".jpeg";
        }

        View view=getScreenshotView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap=view.getDrawingCache();
        if (bitmap!=null){
            PhotoUtil.saveBitmap(cardPhotoUrl,bitmap,true);
        }

        bitmap.recycle();
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(false);
        System.gc();

        return cardPhotoUrl;
    }

    boolean isFromCamera = false;// 区分拍照旋转
    int degree = 0;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            //拍照返回
            case REQUESTCODE_UPLOADAVATAR_CAMERA:
                if (resultCode == MainActivity.RESULT_OK) {
                    if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        toast("SD不可用");
                        return;
                    }
                    isFromCamera = true;
                    File file = new File(takePhotoUrl);
                    degree = PhotoUtil.readPictureDegree(file.getAbsolutePath());
                    int width=800;
                    width=PhotoUtil.getPhotoWidth(takePhotoUrl);
                    startImageAction(Uri.fromFile(file), width, width,REQUESTCODE_UPLOADAVATAR_CROP,true);
                }
                break;
            //本地相册返回
            case REQUESTCODE_UPLOADAVATAR_LOCATION:
                Uri uri = null;
                if (data == null) {
                    return;
                }
                if (resultCode == MainActivity.RESULT_OK) {
                    if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        toast("SD不可用");
                        return;
                    }
                    isFromCamera = false;
                    uri = data.getData();

                    int width=800;
                    Bundle extras=data.getExtras();
                    if (extras!=null){
                        Bitmap bitmap=extras.getParcelable("data");
                        if (bitmap!=null){
                            if (bitmap.getHeight()>bitmap.getWidth()){
                                width=bitmap.getHeight();
                            }else {
                                width=bitmap.getWidth();
                            }
                        }
                        if (!bitmap.isRecycled()){
                            bitmap.recycle();
                        }
                    }

                    startImageAction(uri, width, width,REQUESTCODE_UPLOADAVATAR_CROP, true);
                } else {
                    toast("照片获取失败");
                }

                break;
            // 裁剪头像返回
            case REQUESTCODE_UPLOADAVATAR_CROP:
                if (resultCode==MainActivity.RESULT_OK){
//                    Bitmap bitmap= BitmapFactory.decodeFile(cardPhotoUrl);
//                    if (bitmap!=null){
//                        bitmap=PhotoUtil.compressImage(bitmap);
//                        if (isFromCamera && degree != 0) {
//                            bitmap = PhotoUtil.rotaingImageView(degree, bitmap);
//                        }
//                        setPhotoBackground(bitmap);
//                    }
                    setPhotoBackground(cardPhotoUrl);
                }else {
                    toast("取消了选择");
                }
                break;
            //联网获取图片返回
            case 4:
                if (resultCode==MainActivity.RESULT_OK&& data!=null){
                    Bundle bundle=data.getExtras();
                    String url=bundle.getString("photoUrl");
                    if (!TextUtils.isEmpty(url)){
                        setPhotoBackground(url);
                    }
                }
                break;
            //联网获取文字返回
            case 5:
                if (resultCode==MainActivity.RESULT_OK&& data!=null){
                    Bundle bundle=data.getExtras();
                    TextBean textBean=(TextBean)bundle.getSerializable("textBean");
                    setText(textBean);
                }
                break;
            default:
                break;
        }
    }
    /**
     * @Title: startImageAction裁剪图片
     */
    private void startImageAction(Uri uri, int outputX, int outputY,
                                  int requestCode, boolean isCrop) {
        //生成截图的路径
        cardPhotoUrl=FileUtil.getMainPath()+"/"+new SimpleDateFormat("yyMMddHHmmss").format(new Date())+".jpeg";
        Intent intent = null;
        if (isCrop) {
            intent = new Intent("com.android.camera.action.CROP");
        } else {
            intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        }
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(cardPhotoUrl)));
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, requestCode);
    }

    Toast mToast;

    public void toast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }
    public View findViewById(int paramInt) {
        return getView().findViewById(paramInt);
    }
    /**
     * Activity跳转
     * @param cla
     * @param isFinish 是否关闭现在所在的Activity
     */
    public void forward(Class<?> cla,boolean isFinish) {
        getActivity().startActivity(new Intent(getActivity(), cla));
        if(isFinish) getActivity().finish();
    }

}
