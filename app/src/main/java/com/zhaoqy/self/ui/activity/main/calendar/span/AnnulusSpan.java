package com.zhaoqy.self.ui.activity.main.calendar.span;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

import com.zhaoqy.self.R;
import com.zhaoqy.self.app.AppContext;
import com.zhaoqy.self.util.DpUtil;

public class AnnulusSpan implements LineBackgroundSpan {

    @Override
    public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom,
                               CharSequence text, int start, int end, int lnum) {
        Paint paint = new Paint();
        paint.setAntiAlias(true); //消除锯齿
        paint.setStyle(Paint.Style.STROKE);//绘制空心圆或 空心矩形
        int ringWidth = DpUtil.dip2px(1);//圆环宽度
        //绘制圆环
        paint.setColor(AppContext.getContext().getResources().getColor(R.color.colorPrimary));
        paint.setStrokeWidth(ringWidth);
        c.drawCircle((right - left) / 2, (bottom - top) / 2, right/2-DpUtil.dip2px(1), paint);
    }
}
