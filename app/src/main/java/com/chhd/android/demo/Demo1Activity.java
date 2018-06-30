package com.chhd.android.demo;

import android.os.Bundle;

import com.chhd.android.common.http.HttpObserver;
import com.chhd.android.common.http.ResponseTransformer;
import com.chhd.android.common.http.RxHelper;
import com.chhd.android.common.ui.activity.base.ListActivity;
import com.chhd.android.common.ui.activity.base.PullToRefreshTActivity;
import com.chhd.android.common.mvp.IPageView;

public class Demo1Activity extends ListActivity<Adapter, Object> {

    @Override
    public int getContentResId() {
        return R.layout.layout_pull_to_refresh;
    }

    @Override
    public void onInit(Bundle savedInstanceState) {

    }

    @Override
    public Adapter getAdapter() {
        return new Adapter(list);
    }

    @Override
    public void onLoad(boolean isLoadMore) {
        HttpUtils
                .retrofit("http://10.0.0.137:8080/tms-server/")
                .create(ApiService.class)
                .getRentList(listData.getPageStart(isLoadMore) + "")
                .compose(RxHelper.<ResponseData<ListData<Object>>>ioMainThread())
                .compose(ResponseTransformer.<ListData<Object>>transform())
                .subscribe(new HttpObserver<ListData<Object>>() {
                    @Override
                    public void onSucceed(ListData<Object> listData) {
                        onLoadSuccess(listData);
                    }

                    @Override
                    protected IPageView showPageView() {
                        return (IPageView) instance;
                    }
                });
    }
}
