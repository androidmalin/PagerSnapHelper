package com.example.pagersnaphelper;

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
    private PageItemAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_activity);
        initView();
        initData();
    }

    private void initData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new PageItemAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new PageItemAdapter.IPageItemClickListener() {
            @Override
            public void itemOnClick(String content, int position) {
                Toast.makeText(PageActivity.this, "" + content, Toast.LENGTH_SHORT).show();
            }
        });

        List<String> list = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            list.add(String.valueOf(i));
        }
        mAdapter.addData(list);
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);
    }
}
