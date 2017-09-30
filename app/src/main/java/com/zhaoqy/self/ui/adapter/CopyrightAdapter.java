package com.zhaoqy.self.ui.adapter;

import android.graphics.Paint;
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

public class CopyrightAdapter extends RecyclerView.Adapter<CopyrightAdapter.ViewHolder>{

    /**
     * 展示数据
     */
    private List<String> mData;

    /**
     * 事件回调监听
     */
    private OnItemClickListener onItemClickListener;

    public CopyrightAdapter(List<String> data) {
        this.mData = data;
    }

    public void updateData(List<String> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    /**
     * 设置回调监听
     *
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /**
         * 实例化展示的view
         */
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_copyright, parent, false);
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
        holder.title.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                if(onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, pos);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                if(onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemLongClick(holder.itemView, pos);
                }
                /**
                 * 此事件已经消费，不会触发单击事件
                 */
                return true;
            }
        });
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

    public interface OnItemClickListener {

        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }
}
