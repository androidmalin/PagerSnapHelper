package com.malin.pagersnaphelper;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class VerticalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> mList;
    private LayoutInflater mInflater;
    private Activity mActivity;

    public VerticalAdapter(Activity context) {
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


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(mInflater.inflate(R.layout.vertical_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {

            //1.get current data
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            final int pos = getRealPosition(holder);
            final String content = mList.get(pos);

            //2.set data
            loadContent(itemViewHolder.tvItem, content + ":child");

            //3. listener
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

    private void loadContent(TextView textView, String content) {
        textView.setText(content);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvItem;

        private ItemViewHolder(View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_content_item);
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
