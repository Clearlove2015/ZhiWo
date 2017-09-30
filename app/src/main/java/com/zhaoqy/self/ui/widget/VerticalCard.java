package com.zhaoqy.self.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhaoqy.self.R;

public class VerticalCard extends LinearLayout {

    private TextView mLetter;
    private TextView mTitle;
    private TextView mContent;

    private String title;
    private String content;
    private int title_color;
    private int content_color;
    private int bg_color;
    private boolean hasarrow;
    private boolean hasline;
    private boolean hasletter;
    private boolean hasunderline;

    public VerticalCard(Context context) {
        super(context);
    }

    @SuppressLint("Recycle")
    public VerticalCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        handleTypedArray(context, attrs);
        initView(context);
    }

    private void handleTypedArray(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.VerticalCard);
        title = typedArray.getString(R.styleable.VerticalCard_vc_title);
        content = typedArray.getString(R.styleable.VerticalCard_vc_content);
        int def_color = getResources().getColor(R.color.colorNormal);
        title_color = typedArray.getColor(
                R.styleable.VerticalCard_vc_title_color, def_color);
        content_color = typedArray.getColor(
                R.styleable.VerticalCard_vc_content_color, def_color);
        bg_color = typedArray.getColor(
                R.styleable.VerticalCard_vc_bg_color, def_color);
        hasarrow = typedArray.getBoolean(R.styleable.VerticalCard_vc_hasarrow,
                true);
        hasline = typedArray.getBoolean(R.styleable.VerticalCard_vc_hasline,
                true);
        hasletter = typedArray.getBoolean(R.styleable.VerticalCard_vc_hasletter,
                true);
        hasunderline = typedArray.getBoolean(R.styleable.VerticalCard_vc_hasunderline,
                false);
        typedArray.recycle();
    }

    private void initView(Context context) {
        inflate(context, R.layout.layout_vertical_card, this);

        /**
         * 设置第一个字符
         */
        if (!TextUtils.isEmpty(title)) {
            mLetter = (TextView) findViewById(R.id.letter);
            mLetter.setText(title.subSequence(0, 1));
        }
        /**
         * 设置标题
         */
        mTitle = (TextView) findViewById(R.id.title);
        mTitle.setText(title);
        mTitle.setTextColor(title_color);

        /**
         * 设置下划线
         */
        if (hasunderline) {
            mTitle.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
        }

        /**
         * 设置内容可见
         */
        if (!TextUtils.isEmpty(content)) {
            mContent = (TextView) findViewById(R.id.content);
            mContent.setVisibility(View.VISIBLE);
            mContent.setText(content);
            mTitle.setTextColor(content_color);
        }

        /**
         * 右侧方向箭头不可见
         */
        if (!hasarrow) {
            ImageView arrow = (ImageView) findViewById(R.id.arrow);
            arrow.setVisibility(View.GONE);
        }

        /**
         * 间隔线不可见
         */
        if (!hasline) {
            View line = findViewById(R.id.line);
            line.setVisibility(View.GONE);
        }

        /**
         * 字母不可见
         */
        if (!hasletter) {
            TextView letter = (TextView) findViewById(R.id.letter);
            letter.setVisibility(View.GONE);
        }
    }

    public void setContent(String content) {
        mContent.setText(content);
    }
}
