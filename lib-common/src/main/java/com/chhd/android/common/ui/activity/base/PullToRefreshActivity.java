package com.chhd.android.common.ui.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chhd.android.common.R;
import com.chhd.android.common.entity.BaseListData;
import com.chhd.android.common.global.Constant;

import java.util.List;

/**
 * 下拉刷新界面，不带Toolbar
 *
 * @author : 葱花滑蛋 (2018/03/13)
 */

public abstract class PullToRefreshActivity<Adapter extends BaseQuickAdapter, Entity>
        extends ListActivity<Adapter, Entity> implements SwipeRefreshLayout.OnRefreshListener {

    protected SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isAutoPullToRefresh()) {
            refresh();
        }
    }

    @Override
    protected void reLoad() {
        hasLoadSuccess = false;
        hasLoadComplete = false;

        refresh();
    }

    protected void refresh() {
        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                onRefresh();
            }
        }, 100);
    }

    @Override
    public void onPageLoading() {

    }

    protected int[] getColorSchemeResources() {
        return Constant.SWIPE_REFRESH_LAYOUT_COLORS;
    }

    @Override
    protected final boolean isAutoLoad() {
        return false;
    }

    protected boolean isAutoPullToRefresh() {
        return true;
    }

    @Override
    protected void onPrepare(Bundle savedInstanceState) {
        super.onPrepare(savedInstanceState);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        if (swipeRefreshLayout == null) {
            throw new NullPointerException("Layout must have one SwipeRefreshLayout, " +
                    "and id must set swipe_refresh_layout.");
        }

        swipeRefreshLayout.setColorSchemeResources(getColorSchemeResources());
        swipeRefreshLayout.setOnRefreshListener(this);
    }


    @Override
    public void onRefresh() {
        onLoad(false);
    }

    @Override
    public void onPageComplete() {
        super.onPageComplete();

        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    protected void onLoadSuccess(BaseListData<Entity> listData) {
        super.onLoadSuccess(listData);

        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    protected void onLoadSuccess(List<Entity> list) {
        super.onLoadSuccess(list);

        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    protected void onLoadError(String message) {
        super.onLoadError(message);

        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
