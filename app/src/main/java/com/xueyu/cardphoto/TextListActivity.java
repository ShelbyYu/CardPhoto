package com.xueyu.cardphoto;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.twotoasters.jazzylistview.JazzyGridView;
import com.twotoasters.jazzylistview.JazzyListView;
import com.twotoasters.jazzylistview.effects.SlideInEffect;
import com.xueyu.adapter.TextListListviewAdapter;
import com.xueyu.base.BaseActivity;
import com.xueyu.bean.TextBean;
import com.xueyu.utils.SingleHttClient;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shey on 2015/7/7 16:24.
 * Email:1768037936@qq.com
 */
public class TextListActivity extends BaseActivity implements AbsListView.OnScrollListener{

    private SwipeRefreshLayout swipeRefreshLayout;
    private JazzyListView jazzyListView;
    private TextListListviewAdapter listviewAdapter;
    private TextView titleView;

    private static final int STATE_REFRESH = 0;// 下拉刷新
    private static final int STATE_MORE = 1;// 加载更多
    private int limit = 20;		// 每页的数据是20条
    private int curPage = 1;		// 当前页的编号，从0开始

    private String id="1";//图片集的id
    private String title;//标题栏

    private boolean isCanUpRefresh=true;//是否能够上拉刷新
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_textlist);

        id=getIntent().getExtras().getString("id");
        title=getIntent().getExtras().getString("title");

        initView();
        listviewAdapter=new TextListListviewAdapter(context,null);
        jazzyListView.setTransitionEffect(new SlideInEffect());
        jazzyListView.setOnScrollListener(this);
        jazzyListView.setAdapter(listviewAdapter);
        swipeRefreshLayout.setColorSchemeColors(R.color.main_color,R.color.green,R.color.red);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        isCanUpRefresh=true;
                        queryData(1,STATE_REFRESH);
                    }
                });
            }
        });
        jazzyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextBean textBean =(TextBean)listviewAdapter.getItem(position);

                Intent intent=getIntent();
                intent.putExtra("textBean",textBean);
                setResult(TextCategoryActivity.RESULT_OK, intent);
                finish();
            }
        });
        handler.post(new Runnable() {
            @Override
            public void run() {
                isCanUpRefresh=true;
                swipeRefreshLayout.setProgressViewOffset(false,0,50);
                swipeRefreshLayout.setRefreshing(true);
                queryData(1,STATE_REFRESH);
            }
        });
    }
    Handler handler=new Handler();
    /**
     * 分页获取数据
     * @param page	页码
     * @param actionType	ListView的操作类型（下拉刷新、上拉加载更多）
     */
    private void queryData(final int page, final int actionType) {

        if (!isCanUpRefresh){
            swipeRefreshLayout.setRefreshing(false);
            return;
        }

        RequestParams params=new RequestParams();
        params.put("f_id",id);
        params.put("curPage",page);
        params.put("pageLen",limit);
        SingleHttClient.getInstance().post("http://zero.myname9.com/index.php/app/text/getList", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("数据错误2：", statusCode + "");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                List<TextBean> textBeanList = null;
                try {
                    textBeanList =parseJson(responseString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (textBeanList !=null && textBeanList.size() > 0) {
                    if (actionType == STATE_REFRESH) {
                        listviewAdapter.removeAll();
                        // 当是下拉刷新操作时，将当前页的编号重置为0，重新添加
                        curPage = 1;
                    }
                    listviewAdapter.addAll(textBeanList);
                    // 这里在每次加载完数据后，将当前页码+1，这样在上拉刷新的onPullUpToRefresh方法中就不需要操作curPage了
                    curPage++;
                } else if (actionType == STATE_MORE) {
                    toast("没有更多数据了");
                    isCanUpRefresh=false;
                } else if (actionType == STATE_REFRESH) {
                    toast("没有数据");
                    isCanUpRefresh=false;
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private List<TextBean> parseJson(String response) throws JSONException {
        List<TextBean> textBeanList=new ArrayList<>();

        JSONObject jsonObject=new JSONObject(response).getJSONObject("data");
        JSONArray jsonArray=jsonObject.getJSONArray("textList");

        TextBean textBean;
        for (int i=0;i<jsonArray.length();i++){
            JSONObject itemObject=jsonArray.getJSONObject(i);

            textBean =new TextBean();
            textBean.setContent(itemObject.getString("content"));
            textBean.setAuthor(itemObject.getString("author"));
            textBeanList.add(textBean);
        }

        return textBeanList;
    }

    private void initView(){
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.textlist_swipelayout);
        jazzyListView=(JazzyListView)findViewById(R.id.textlist_listview);
        titleView=(TextView)findViewById(R.id.textlist_textView);

        if (!TextUtils.isEmpty(title)){
            titleView.setText(title);
        }
    }

    private int lastItem;//listview最后一个Item
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        //下拉到空闲是，且最后一个item的数等于数据的总数时，进行更新
        if(lastItem == listviewAdapter.getCount() && scrollState == this.SCROLL_STATE_IDLE){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    // 上拉刷新(从第一页开始装载数据)
                    queryData(curPage, STATE_MORE);
                }
            });
        }
    }
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        lastItem = firstVisibleItem + visibleItemCount;
    }
}
