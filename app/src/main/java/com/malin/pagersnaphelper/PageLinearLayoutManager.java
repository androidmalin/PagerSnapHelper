package com.malin.pagersnaphelper;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;


public class PageLinearLayoutManager extends LinearLayoutManager {

    private boolean isScrollEnabled = true;

    public PageLinearLayoutManager(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }

    @Override
    public boolean canScrollHorizontally() {
        return isScrollEnabled && super.canScrollHorizontally();
    }
}