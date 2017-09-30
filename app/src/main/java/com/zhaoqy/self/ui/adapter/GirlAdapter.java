package com.zhaoqy.self.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.zhaoqy.self.R;
import com.zhaoqy.self.mvp.model.Girl;

/**
 * Created by zhaoqy on 2017/9/28.
 */

public class GirlAdapter extends RecyclerArrayAdapter<Girl.ResultsBean> {

    public GirlAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new GirlViewHolder(parent);
    }

    class GirlViewHolder extends BaseViewHolder<Girl.ResultsBean> {
        private ImageView image;

        public GirlViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_girl);
            image = $(R.id.image);
        }

        @Override
        public void setData(Girl.ResultsBean data) {
            super.setData(data);
            Glide.with(getContext())
                    .load(data.getUrl())
                    .placeholder(R.mipmap.default_img)
                    .error(R.mipmap.default_img)
                    .into(image);
        }
    }
}
