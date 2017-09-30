package com.zhaoqy.self.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhaoqy.self.R;

import java.util.List;

/**
 * Created by zhaoqy on 2017/9/21.
 */

public class ThankAdapter extends RecyclerView.Adapter<ThankAdapter.ViewHolder>{

    /**
     * 展示数据
     */
    private List<String> mData;

    public ThankAdapter(List<String> data) {
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /**
         * 实例化展示的view
         */
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thank, parent, false);
        /**
         * 实例化viewholder
         */
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        /**
         * 绑定数据
         */
        String string = mData.get(position);
        holder.title.setText(string);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
