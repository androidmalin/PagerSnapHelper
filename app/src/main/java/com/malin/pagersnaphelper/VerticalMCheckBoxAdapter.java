package com.malin.pagersnaphelper;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RecyclerView CheckBox mu
 */
public class VerticalMCheckBoxAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Bean> mList;
    private LayoutInflater mInflater;
    private Activity mActivity;
    private Map<Integer, Boolean> mapCheckBox = new HashMap<>();

    public VerticalMCheckBoxAdapter(Activity context) {
        mActivity = context;
        mInflater = LayoutInflater.from(context);
    }

    public void addData(List<Bean> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public List<Bean> getData() {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        return mList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(mInflater.inflate(R.layout.vertical_m_checkbox_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {

            //1.get current data
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            final int realPosition = getRealPosition(holder);
            final Bean bean = mList.get(realPosition);

            //2.set data
            loadContent(itemViewHolder.tvItem, bean.content + ":m checkbox");

            //3. listener
            itemViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        bean.checked = true;
                        mapCheckBox.put(realPosition, true);
                    } else {
                        bean.checked = false;
                        mapCheckBox.remove(realPosition);
                    }
                }
            });

            if (mapCheckBox != null && mapCheckBox.containsKey(realPosition)) {
                itemViewHolder.checkBox.setChecked(true);
            } else {
                itemViewHolder.checkBox.setChecked(false);
            }
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
        private CheckBox checkBox;

        private ItemViewHolder(View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_content_item);
            checkBox = itemView.findViewById(R.id.cb_checkbox_item);
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
