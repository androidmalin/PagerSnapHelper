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

    private ArrayList<Bean> mList;
    private LayoutInflater mInflater;
    private Activity mActivity;
    private List<Bean> mMCheckBoxList = new ArrayList<>();
    private List<Bean> mSingleCheckBoxList = new ArrayList<>();

    private static final int TYPE_ITEM_M_CHECK = 0;
    private static final int TYPE_ITEM_2 = 1;
    private static final int TYPE_ITEM_SINGLE_CHECK = 2;

    public PageHorizontalAdapter(Activity context) {
        mActivity = context;
        mInflater = LayoutInflater.from(context);
        initData();
    }

    private void initData() {
        for (int i = 0; i < 100; i++) {
            Bean bean = new Bean();
            bean.content = String.valueOf(i);
            bean.checked = false;
            mMCheckBoxList.add(bean);
            mSingleCheckBoxList.add(bean);
        }
    }

    public void addData(List<Bean> list) {
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

    public ArrayList<Bean> getData() {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        return mList;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_ITEM_M_CHECK;
        } else if (position == 1) {
            return TYPE_ITEM_SINGLE_CHECK;
        } else {
            return TYPE_ITEM_2;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM_M_CHECK) {
            return new ItemMCheckBoxViewHolder(mInflater.inflate(R.layout.page_horizontal_item, parent, false));
        } else if (viewType == TYPE_ITEM_2) {
            return new ItemViewHolder2(mInflater.inflate(R.layout.page_horizontal_item2, parent, false));
        } else if (viewType == TYPE_ITEM_SINGLE_CHECK) {
            return new ItemSingleCheckBoxViewHolder(mInflater.inflate(R.layout.page_horizontal_item, parent, false));
        }
        return new ItemMCheckBoxViewHolder(mInflater.inflate(R.layout.page_horizontal_item, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemMCheckBoxViewHolder) {

            //1.get current data
            ItemMCheckBoxViewHolder itemMCheckBoxViewHolder = (ItemMCheckBoxViewHolder) holder;
            final int pos = getRealPosition(holder);
            final Bean bean = mList.get(pos);

            //2.set data
            loadMCheckBoxContent(itemMCheckBoxViewHolder.rvList);

            //3. listener
            itemMCheckBoxViewHolder.btnPre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mIPageItemClickListener == null) return;
                    mIPageItemClickListener.itemBtnOnClick(false, pos);
                }
            });

            itemMCheckBoxViewHolder.btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mIPageItemClickListener == null) return;
                    mIPageItemClickListener.itemBtnOnClick(true, pos);
                }
            });
        } else if (holder instanceof ItemSingleCheckBoxViewHolder) {

            //1.get current data
            ItemSingleCheckBoxViewHolder itemSingleCheckBoxViewHolder = (ItemSingleCheckBoxViewHolder) holder;
            final int pos = getRealPosition(holder);

            //2.set data
            loadSingleCheckBoxContent(itemSingleCheckBoxViewHolder.rvList);

            //3. listener
            itemSingleCheckBoxViewHolder.btnPre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mIPageItemClickListener == null) return;
                    mIPageItemClickListener.itemBtnOnClick(false, pos);
                }
            });

            itemSingleCheckBoxViewHolder.btnNext.setOnClickListener(new View.OnClickListener() {
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
            final Bean bean = mList.get(pos);

            //2.set data
            itemViewHolder.tvNumber.setText(bean.content + ":type2");
            itemViewHolder.tvNumber.setTextColor(Color.parseColor("#00FF00"));

            //3. listener
            itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mIPageItemClickListener == null || bean.content == null) return;
                    mIPageItemClickListener.itemOnClick(bean.content, pos);
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

    private VerticalMCheckBoxAdapter verticalMCheckBoxAdapter;
    private LinearLayoutManager linearLayoutManager;

    private void loadMCheckBoxContent(RecyclerView recyclerView) {
        //1.Adapter
        if (verticalMCheckBoxAdapter == null) {
            verticalMCheckBoxAdapter = new VerticalMCheckBoxAdapter(mActivity);
        }

        //2.layoutManager
        if (linearLayoutManager == null) {
            linearLayoutManager = new LinearLayoutManager(mActivity);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        }


        //2.setLayoutManager
        recyclerView.setLayoutManager(linearLayoutManager);

        //3.setAdapter
        recyclerView.setAdapter(verticalMCheckBoxAdapter);

        //set data
        verticalMCheckBoxAdapter.addData(mMCheckBoxList);
    }

    private VerticalSingleCheckBoxAdapter singleCheckBoxAdapter;
    private LinearLayoutManager linearLayoutManagerSingle;

    private void loadSingleCheckBoxContent(RecyclerView recyclerView) {
        //1.Adapter
        if (singleCheckBoxAdapter == null) {
            singleCheckBoxAdapter = new VerticalSingleCheckBoxAdapter(mActivity);
        }

        //2.layoutManager
        if (linearLayoutManagerSingle == null) {
            linearLayoutManagerSingle = new LinearLayoutManager(mActivity);
            linearLayoutManagerSingle.setOrientation(LinearLayoutManager.VERTICAL);
        }

        //2.setLayoutManager
        recyclerView.setLayoutManager(linearLayoutManagerSingle);

        //3.setAdapter
        recyclerView.setAdapter(singleCheckBoxAdapter);

        // set data
        singleCheckBoxAdapter.addData(mSingleCheckBoxList);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    private static class ItemMCheckBoxViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView rvList;
        private Button btnPre;
        private Button btnNext;


        private ItemMCheckBoxViewHolder(View itemView) {
            super(itemView);
            rvList = itemView.findViewById(R.id.rv_list);
            btnPre = itemView.findViewById(R.id.btn_pre);
            btnNext = itemView.findViewById(R.id.btn_next);
        }
    }

    private static class ItemSingleCheckBoxViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView rvList;
        private Button btnPre;
        private Button btnNext;


        private ItemSingleCheckBoxViewHolder(View itemView) {
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
