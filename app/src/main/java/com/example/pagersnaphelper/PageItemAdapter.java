package com.example.pagersnaphelper;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class PageItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> mList;
    private LayoutInflater mInflater;

    public PageItemAdapter(Activity context) {
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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.page_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            final int pos = getRealPosition(holder);
            final String content = mList.get(pos);
            loadImgCode(itemViewHolder.tvNumber, pos + 1);
            itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mIPageItemClickListener == null || content == null) return;
                    mIPageItemClickListener.itemOnClick(content, pos);
                }
            });
        }
    }

    private int getRealPosition(RecyclerView.ViewHolder holder) {
        return holder.getLayoutPosition();
    }

    private void loadImgCode(TextView textView, int position) {
        textView.setText(String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNumber;

        private ItemViewHolder(View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.iv_item);
        }
    }

    public void clearData() {
        if (mList != null) {
            mList.clear();
        }
        notifyDataSetChanged();
    }


    public void destroyData() {
        if (mList != null) {
            mList.clear();
            mList = null;
        }
    }

    public int getDataSize() {
        return mList == null ? 0 : mList.size();
    }

}
