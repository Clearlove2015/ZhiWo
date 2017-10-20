package com.zhaoqy.self.ui.activity.main.knowledge.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.zhaoqy.self.R;

/**
 * Created by zhaoqy on 2017/10/14.
 */

public class CanvasView extends View {

    // 画笔
    private Paint mPaint = new Paint();

    public CanvasView(Context context) {
        this(context, null);
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //设置画笔颜色
        //mPaint.setColor(Color.RED);
        mPaint.setColor(context.getResources().getColor(R.color.colorPrimary));
        mPaint.setStyle(Paint.Style.FILL);  //设置画笔模式为填充
        mPaint.setStrokeWidth(10f);         //设置画笔宽度为10px
        mPaint.setTextSize(20);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制颜色
        canvas.drawColor(Color.WHITE);

        //在坐标(100,100)位置绘制一个点
        canvas.drawPoint(100, 100, mPaint);
        canvas.drawText("Paint", 100, 120, mPaint);
        //绘制一组点，坐标位置由float数组指定
        canvas.drawPoints(new float[]{
                500,500,
                500,600,
                500,700 },mPaint);

        // 第一种
        //canvas.drawRect(100, 100, 800, 400, mPaint);
        // 第二种
        Rect rect = new Rect(100,100,800,400);
        //canvas.drawRect(rect, mPaint);
        // 第三种
        RectF rectF = new RectF(100,100,800,400);
        //canvas.drawRect(rectF, mPaint);

    }
}
