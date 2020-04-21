package com.malin.pagersnaphelper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PageActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private PageHorizontalAdapter mPageHorizontalAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_activity);
        initView();
        initData();
    }

    private void initData() {
        //1.pageLayoutManager
        PageHorizontalLayoutManager pageLayoutManager = new PageHorizontalLayoutManager(this);
        pageLayoutManager.setScrollEnabled(false);
        pageLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        //2.setLayoutManager
        mRecyclerView.setLayoutManager(pageLayoutManager);

        //3.setAdapter
        mPageHorizontalAdapter = new PageHorizontalAdapter(this);
        mRecyclerView.setAdapter(mPageHorizontalAdapter);

        //4.set ClickListener
        mPageHorizontalAdapter.setOnItemClickListener(new PageHorizontalAdapter.IPageItemClickListener() {
            @Override
            public void itemOnClick(String content, int position) {
                Toast.makeText(PageActivity.this, "" + content, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void itemBtnOnClick(boolean nextPage, int currentPosition) {
                int changedPosition;
                if (nextPage) {
                    changedPosition = currentPosition + 1;
                } else {
                    changedPosition = currentPosition - 1;
                }
                if (changedPosition >= 0 && changedPosition < mPageHorizontalAdapter.getDataSize()) {
                    mRecyclerView.scrollToPosition(changedPosition);
                }

                if (changedPosition == mPageHorizontalAdapter.getDataSize() - 1) {
                    Toast.makeText(PageActivity.this, "last page", Toast.LENGTH_SHORT).show();
                }
                if (changedPosition == 0) {
                    Toast.makeText(PageActivity.this, "first page", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //5. get data
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            list.add(i + " page");
        }

        //6. set data
        mPageHorizontalAdapter.addData(list);
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);
    }
}
