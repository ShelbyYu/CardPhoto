package com.xueyu.cardphoto;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import com.xueyu.adapter.FragmentAdapter;
import com.xueyu.base.BaseActivity;
import com.xueyu.bean.FontBean;
import com.xueyu.bean.FontBeanBack;

import java.io.File;
import github.chenupt.springindicator.SpringIndicator;
import github.chenupt.springindicator.viewpager.ScrollerViewPager;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    ScrollerViewPager viewPager;
    SpringIndicator springIndicator;
    FragmentAdapter fragmentAdapter;

    private TextView share,font,save;

    public static Typeface currentTypeFace=Typeface.DEFAULT;//唉，没办法，谁叫它不是序列化的呢，传递不了啊
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        fragmentAdapter=new FragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentAdapter);
        springIndicator.setViewPager(viewPager);
    }

    /**
     * 初始化控件
     */
    private void initView(){
        viewPager=(ScrollerViewPager)findViewById(R.id.main_viewpager);
        springIndicator=(SpringIndicator)findViewById(R.id.main_indicator);
        viewPager.setOffscreenPageLimit(4);//设置Viewpager可以缓冲4个页面

        share=(TextView)findViewById(R.id.main_share);
        font=(TextView)findViewById(R.id.main_font);
        save=(TextView)findViewById(R.id.main_save);
        share.setOnClickListener(this);
        font.setOnClickListener(this);
        save.setOnClickListener(this);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==1){
            FontBeanBack fontBeanBack=(FontBeanBack)data.getSerializableExtra("FontBeanBack");
            FontBean fontBean=new FontBean();
            fontBean.setFontColor(fontBeanBack.getFontColor());
            fontBean.setFontSize(fontBeanBack.getFontSize());
            fontBean.setTypeface(SelectFontActivity.currentTypeFace);

            fragmentAdapter.setTypeFace(fontBean,viewPager.getCurrentItem());
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_share:
                toast("正在分享...");

                Intent intent=new Intent(Intent.ACTION_SEND);
                File f = new File(fragmentAdapter.getScreenshotPhotoUrl(viewPager.getCurrentItem()));
                if (f != null && f.exists() && f.isFile()) {
                    intent.setType("image/jpeg");
                    Uri u = Uri.fromFile(f);
                    intent.putExtra(Intent.EXTRA_STREAM, u);
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(Intent.createChooser(intent, "请选择"));

                break;
            case R.id.main_font:
                Intent intent1=new Intent(this,SelectFontActivity.class);

                FontBean fontBean=fragmentAdapter.getFontBean(viewPager.getCurrentItem());
                FontBeanBack fontBeanBack=new FontBeanBack();
                fontBeanBack.setFontColor(fontBean.getFontColor());
                fontBeanBack.setFontSize(fontBean.getFontSize());
                currentTypeFace=fontBean.getTypeface();

                intent1.putExtra("FontBeanBack",fontBeanBack);
                startActivityForResult(intent1, 0);
                break;
            case R.id.main_save:
                String url=fragmentAdapter.getScreenshotPhotoUrl(viewPager.getCurrentItem());
                toast("已保存在："+ url);
                break;
            default:
                break;
        }
    }

    private long exitTime=0;
    // 按下菜单键时
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                toast("再按一次就退出");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
