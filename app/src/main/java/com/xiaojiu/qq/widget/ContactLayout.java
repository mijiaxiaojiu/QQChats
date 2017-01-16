package com.xiaojiu.qq.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaojiu.qq.R;

/**
 * 作者：${xiaojiukeji} on 17/1/14 09:52
 * 作用:
 */

public class ContactLayout extends RelativeLayout {

    private RecyclerView recyclerView;
    private TextView tv_float;
    private SlideBarView slideBar;
    private SwipeRefreshLayout swipeRefresh;

    public ContactLayout(Context context) {
        this(context, null);
    }

    public ContactLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //将R.layout.contact_layout添加到当前控件中
        LayoutInflater.from(context).inflate(R.layout.contact_layout, this, true);
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        tv_float = (TextView) findViewById(R.id.tv_float);
        slideBar = (SlideBarView) findViewById(R.id.slideBar);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeResfresh_layout);
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorAccent));

    }

    public ContactLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    public void setOnRefreshView(SwipeRefreshLayout.OnRefreshListener onRefreshListener){
        swipeRefresh.setOnRefreshListener(onRefreshListener);
    }
    public void setRefreshing(boolean refreshing){
        swipeRefresh.setRefreshing(refreshing);
    }
    public void setAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
}
