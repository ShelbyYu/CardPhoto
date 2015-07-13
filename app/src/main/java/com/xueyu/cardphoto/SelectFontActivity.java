package com.xueyu.cardphoto;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.afollestad.materialdialogs.MaterialDialog;
import com.xueyu.base.BaseActivity;
import com.xueyu.bean.FontBeanBack;
import com.xueyu.component.ColorSelector;
import com.xueyu.component.MaterialEditText;

/**
 * Created by Shey on 2015/7/3 12:26.
 * Email:1768037936@qq.com
 */
public class SelectFontActivity extends BaseActivity implements View.OnClickListener,View.OnFocusChangeListener{

    private float fontSize=15;
    public static Typeface currentTypeFace=Typeface.DEFAULT;
    private int fontColor=0;

    private MaterialEditText style,size;
    private View colorView;
    private Button cancel,sure;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_selectfont);

        fontColor=getResources().getColor(R.color.font_color);
        initView();

        FontBeanBack fontBeanBack=(FontBeanBack)getIntent().getExtras().getSerializable("FontBeanBack");
        if (fontBeanBack!=null){
            fontSize=fontBeanBack.getFontSize()/2;
            size.setTextSize(fontSize);
            style.setTextSize(fontSize);

            fontColor=fontBeanBack.getFontColor();
            colorView.setBackgroundColor(fontColor);

            currentTypeFace=MainActivity.currentTypeFace;
            size.setTypeface(currentTypeFace);
            style.setTypeface(currentTypeFace);
        }
    }

    private void initView(){
        style=(MaterialEditText)findViewById(R.id.selectfont_style);
        size=(MaterialEditText)findViewById(R.id.selectfont_size);
        colorView=(View)findViewById(R.id.selectfont_color);
        cancel=(Button)findViewById(R.id.selectfont_cancel);
        sure=(Button)findViewById(R.id.selectfont_sure);

        style.setOnFocusChangeListener(this);
        size.setOnFocusChangeListener(this);
        colorView.setOnClickListener(this);
        cancel.setOnClickListener(this);
        sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            //选择字体颜色
            case R.id.selectfont_color:
                new ColorSelector(this, fontColor, new ColorSelector.OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int color) {
                        fontColor=color;
                        style.setTextColor(fontColor);
                        size.setTextColor(fontColor);

                        colorView.setBackgroundColor(color);
                    }
                }).show();
                break;
            //取消选择
            case R.id.selectfont_cancel:
                setResult(0);
                finish();
                break;
            //确定选择
            case R.id.selectfont_sure:
                FontBeanBack fontBeanBack=new FontBeanBack();
                fontBeanBack.setFontColor(fontColor);
                fontBeanBack.setFontSize(fontSize);

                Intent intent=getIntent();
                Bundle bundle=new Bundle();
                bundle.putSerializable("FontBeanBack",fontBeanBack);
                intent.putExtras(bundle);
                setResult(1, intent);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) return;
        switch (v.getId()) {
            //设置字体样式
            case R.id.selectfont_style:
                new MaterialDialog.Builder(this)
                        .items(R.array.fontstyle)
                        .cancelable(false)
                        .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                switch (which) {
                                    case 0:
                                        currentTypeFace = Typeface.DEFAULT;
                                        break;
                                    //娃娃体
                                    case 1:
                                        Typeface wawa = Typeface.createFromAsset(getAssets(), "fonts/wawa.TTF");
                                        currentTypeFace = wawa;
                                        break;
                                    //方正胖头鱼
                                    case 2:
                                        Typeface pangtouyu = Typeface.createFromAsset(getAssets(), "fonts/pangtouyu.TTF");
                                        currentTypeFace = pangtouyu;
                                        break;
                                    //华文新魏
                                    case 3:
                                        Typeface huawenxinwei = Typeface.createFromAsset(getAssets(), "fonts/huawenxinwei.TTF");
                                        currentTypeFace = huawenxinwei;
                                        break;
                                    //方正硬笔行书
                                    case 4:
                                        Typeface yingbihangshu = Typeface.createFromAsset(getAssets(), "fonts/yingbihangshu.TTF");
                                        currentTypeFace = yingbihangshu;
                                        break;
                                }
                                style.setTypeface(currentTypeFace);
                                size.setTypeface(currentTypeFace);
                            }
                        })
                        .positiveText(R.string.sure)
                        .negativeText("取消")
                        .build()
                        .show();
                break;
            //选择字体大小
            case R.id.selectfont_size:
                new MaterialDialog.Builder(this)
                        .items(R.array.fontsize)
                        .cancelable(false)
                        .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                switch (which) {
                                    case 0:
                                        fontSize = 15;
                                        break;
                                    case 1:
                                        fontSize = 10;
                                        break;
                                    case 2:
                                        fontSize = 13;
                                        break;
                                    case 3:
                                        fontSize = 18;
                                        break;
                                    case 4:
                                        fontSize = 22;
                                        break;
                                }
                                style.setTextSize(fontSize);
                                size.setTextSize(fontSize);
                            }
                        })
                        .positiveText(R.string.sure)
                        .negativeText("取消")
                        .build()
                        .show();
                break;
        }
    }
}
