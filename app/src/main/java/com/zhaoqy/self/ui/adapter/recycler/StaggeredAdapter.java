package com.zhaoqy.self.ui.adapter.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhaoqy.self.R;

import java.util.List;

/**
 * Created by zhaoqy on 2017/9/30.
 */

public class StaggeredAdapter extends RecyclerView.Adapter<StaggeredAdapter.ViewHolder> {

    /**
     * 展示数据
     */
    private List<String> mData;

    public StaggeredAdapter(List<String> data) {
        this.mData = data;
    }

    public void updateData(List<String> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        // 瀑布流外部设置spanCount为2，在这列设置两个不同的item type,以区分不同的布局
        return position % 3;
    }

    @Override
    public StaggeredAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v;
        if(viewType == 1) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_staggered_one, parent, false);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_staggered_two, parent, false);
        }
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StaggeredAdapter.ViewHolder holder, int position) {
        // 绑定数据
        holder.mTv.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTv;

        public ViewHolder(View itemView) {
            super(itemView);
            mTv = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
