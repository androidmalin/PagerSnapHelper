package com.malin.pagersnaphelper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PageHorizontalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> mList;
    private LayoutInflater mInflater;
    private Activity mActivity;

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_ITEM_2 = 1;

    public PageHorizontalAdapter(Activity context) {
        mActivity = context;
        mInflater = LayoutInflater.from(context);
    }

    public void addData(List<String> list) {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    private IPageItemClickListener mIPageItemClickListener;


    public interface IPageItemClickListener {
        void itemOnClick(String content, int position);

        void itemBtnOnClick(boolean nextPage, int currentPosition);
    }

    public void setOnItemClickListener(IPageItemClickListener listener) {
        this.mIPageItemClickListener = listener;
    }

    public ArrayList<String> getData() {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        return mList;
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return TYPE_ITEM;
        } else {
            return TYPE_ITEM_2;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            return new ItemViewHolder(mInflater.inflate(R.layout.page_horizontal_item, parent, false));
        } else if (viewType == TYPE_ITEM_2) {
            return new ItemViewHolder2(mInflater.inflate(R.layout.page_horizontal_item2, parent, false));
        }
        return new ItemViewHolder(mInflater.inflate(R.layout.page_horizontal_item, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {

            //1.get current data
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            final int pos = getRealPosition(holder);
            final String content = mList.get(pos);

            //2.set data
            loadContent(itemViewHolder.rvList);

            //3. listener
            itemViewHolder.btnPre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mIPageItemClickListener == null) return;
                    mIPageItemClickListener.itemBtnOnClick(false, pos);
                }
            });

            itemViewHolder.btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mIPageItemClickListener == null) return;
                    mIPageItemClickListener.itemBtnOnClick(true, pos);
                }
            });
        } else if (holder instanceof ItemViewHolder2) {
            //1.get current data
            ItemViewHolder2 itemViewHolder = (ItemViewHolder2) holder;
            final int pos = getRealPosition(holder);
            final String content = mList.get(pos);

            //2.set data
            itemViewHolder.tvNumber.setText(content + ":type2");
            itemViewHolder.tvNumber.setTextColor(Color.parseColor("#00FF00"));

            //3. listener
            itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mIPageItemClickListener == null || content == null) return;
                    mIPageItemClickListener.itemOnClick(content, pos);
                }
            });

            itemViewHolder.btnPre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mIPageItemClickListener == null) return;
                    mIPageItemClickListener.itemBtnOnClick(false, pos);
                }
            });

            itemViewHolder.btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mIPageItemClickListener == null) return;
                    mIPageItemClickListener.itemBtnOnClick(true, pos);
                }
            });
        }
    }

    private int getRealPosition(RecyclerView.ViewHolder holder) {
        return holder.getLayoutPosition();
    }

    private void loadContent(RecyclerView recyclerView) {
        //1.Adapter
        VerticalAdapter verticalAdapter = new VerticalAdapter(mActivity);

        //2.layoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        //2.setLayoutManager
        recyclerView.setLayoutManager(linearLayoutManager);

        //3.setAdapter
        recyclerView.setAdapter(verticalAdapter);

        //4. get data
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(String.valueOf(i));
        }

        //6. set data
        verticalAdapter.addData(list);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView rvList;
        private Button btnPre;
        private Button btnNext;


        private ItemViewHolder(View itemView) {
            super(itemView);
            rvList = itemView.findViewById(R.id.rv_list);
            btnPre = itemView.findViewById(R.id.btn_pre);
            btnNext = itemView.findViewById(R.id.btn_next);
        }
    }

    private static class ItemViewHolder2 extends RecyclerView.ViewHolder {
        private TextView tvNumber;
        private Button btnPre;
        private Button btnNext;


        private ItemViewHolder2(View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tv_content2);
            btnPre = itemView.findViewById(R.id.btn_pre2);
            btnNext = itemView.findViewById(R.id.btn_next2);
        }
    }

    public void clearData() {
        if (mList != null) {
            mList.clear();
        }
        notifyDataSetChanged();
    }


    public int getDataSize() {
        return mList == null ? 0 : mList.size();
    }

}
