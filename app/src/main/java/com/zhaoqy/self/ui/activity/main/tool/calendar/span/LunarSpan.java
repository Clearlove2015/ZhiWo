package com.zhaoqy.self.ui.activity.main.tool.calendar.span;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

import com.zhaoqy.self.ui.activity.main.tool.calendar.utils.Lunar;
import com.zhaoqy.self.util.DpUtil;

import java.util.Calendar;

public class LunarSpan implements LineBackgroundSpan {

    private String year;
    private String month;

    public LunarSpan(String year, String month) {
        this.year = year;
        this.month = month;
    }

    @Override
    public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom,
                               CharSequence text, int start, int end, int lnum) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.valueOf(year), Integer.valueOf(month)-1, Integer.valueOf(text.toString()));
        Lunar lunar = new Lunar(calendar.getTime());
        String chinaDayString = lunar.getChinaDayString();
        Paint paint = new Paint();
        paint.setTextSize(DpUtil.dip2px(10));
        /**
         * 设置农历文本颜色
         */
        paint.setColor(Color.parseColor("#cccccc"));
        /*int color = AppContext.getContext().getResources().getColor(R.color.colorHint);
        paint.setColor(color);*/
        c.drawText(chinaDayString, (right - left) / 2 - DpUtil.dip2px(10),
                (bottom - top) / 2 + DpUtil.dip2px(17), paint);
    }
}