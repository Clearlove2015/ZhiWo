package com.zhaoqy.self.ui.activity.main.knowledge.recyclerview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.adapter.recycler.SwipeAdapter;
import com.zhaoqy.self.ui.base.BaseToolboxActivity;
import com.zhaoqy.self.ui.widget.recyclerview.decoration.LinearDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SwipeToDeleteActivity extends BaseToolboxActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    SwipeAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    ItemTouchHelper mItemTouchHelper;
    ItemTouchHelper.Callback mItemTouchCallBack;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_swipe_to_delete;
    }

    @Override
    protected void initData() {
        initAction();
        mItemTouchHelper = new ItemTouchHelper(mItemTouchCallBack);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new SwipeAdapter(getData());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new LinearDivider(this, LinearLayoutManager.VERTICAL));
        mItemTouchHelper.attachToRecyclerView(recyclerView);

    }

    private void initAction() {
        mItemTouchCallBack = new ItemTouchHelper.Callback() {
            /**
             * 设置滑动类型标记
             *
             * @param recyclerView
             * @param viewHolder
             * @return
             *          返回一个整数类型的标识，用于判断Item那种移动行为是允许的
             */
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(0,ItemTouchHelper.START | ItemTouchHelper.END);
            }

            /**
             * Item是否支持长按拖动
             *
             * @return
             *          true  支持长按操作
             *          false 不支持长按操作
             */
            @Override
            public boolean isLongPressDragEnabled() {
                return false;
            }

            /**
             * Item是否支持滑动
             *
             * @return
             *          true  支持滑动操作
             *          false 不支持滑动操作
             */
            @Override
            public boolean isItemViewSwipeEnabled() {
                return true;
            }

            /**
             * 拖拽切换Item的回调
             *
             * @param recyclerView
             * @param viewHolder
             * @param target
             * @return
             *          如果Item切换了位置，返回true；反之，返回false
             */
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return true;
            }

            /**
             * 滑动删除Item
             *
             * @param viewHolder
             * @param direction
             *           Item滑动的方向
             */
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                mAdapter.delete(viewHolder.getAdapterPosition());
            }

            /**
             * Item被选中时候回调
             *
             * @param viewHolder
             * @param actionState
             *          当前Item的状态
             *          ItemTouchHelper.ACTION_STATE_IDLE   闲置状态
             *          ItemTouchHelper.ACTION_STATE_SWIPE  滑动中状态
             *          ItemTouchHelper#ACTION_STATE_DRAG   拖拽中状态
             */
            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                //  item被选中的操作
                if(actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    int color= Color.parseColor("#aaaaaa");
                    viewHolder.itemView.setBackgroundColor(color);
                    //viewHolder.itemView.setBackgroundResource(R.color.md_gray);
                }
                super.onSelectedChanged(viewHolder, actionState);
            }

            /**
             * 移动过程中绘制Item
             *
             * @param c
             * @param recyclerView
             * @param viewHolder
             * @param dX
             *          X轴移动的距离
             * @param dY
             *          Y轴移动的距离
             * @param actionState
             *          当前Item的状态
             * @param isCurrentlyActive
             *          如果当前被用户操作为true，反之为false
             */
            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView,
                                    RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY, int actionState, boolean isCurrentlyActive) {
                float x = Math.abs(dX) + 0.5f;
                float width = viewHolder.itemView.getWidth();
                float alpha = 1f - x / width;
                viewHolder.itemView.setAlpha(alpha);
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState,
                        isCurrentlyActive);
            }

            /**
             * 移动过程中绘制Item
             *
             * @param c
             * @param recyclerView
             * @param viewHolder
             * @param dX
             *          X轴移动的距离
             * @param dY
             *          Y轴移动的距离
             * @param actionState
             *          当前Item的状态
             * @param isCurrentlyActive
             *          如果当前被用户操作为true，反之为false
             */
            @Override
            public void onChildDrawOver(Canvas c, RecyclerView recyclerView,
                                        RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState,
                                        boolean isCurrentlyActive) {
                super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState,
                        isCurrentlyActive);
            }

            /**
             * 用户操作完毕或者动画完毕后会被调用
             *
             * @param recyclerView
             * @param viewHolder
             */
            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                // 操作完毕后恢复颜色
                viewHolder.itemView.setBackgroundResource(R.color.colorWhite);
                viewHolder.itemView.setAlpha(1.0f);
                super.clearView(recyclerView, viewHolder);
            }
        };


    }

    private List<String> getData() {
        List<String> data = new ArrayList<>();
        String temp = "item";
        for(int i = 0; i < 20; i++) {
            data.add(temp + i);
        }
        return data;
    }

}
