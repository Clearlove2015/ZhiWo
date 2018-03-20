package com.zhaoqy.self.ui.activity.main.knowledge.span;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.base.BaseBarActivity;

import butterknife.BindView;

public class SpanActivity extends BaseBarActivity {


    @BindView(R.id.text01)
    TextView textView01;
    @BindView(R.id.text02)
    TextView textView02;
    @BindView(R.id.text03)
    TextView textView03;
    @BindView(R.id.text04)
    TextView textView04;
    @BindView(R.id.text05)
    TextView textView05;
    @BindView(R.id.text06)
    TextView textView06;
    @BindView(R.id.text07)
    TextView textView07;
    @BindView(R.id.text08)
    TextView textView08;
    @BindView(R.id.text09)
    TextView textView09;
    @BindView(R.id.text10)
    TextView textView10;
    @BindView(R.id.text11)
    TextView textView11;
    @BindView(R.id.text12)
    TextView textView12;
    @BindView(R.id.text13)
    TextView textView13;
    @BindView(R.id.text14)
    TextView textView14;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_span;
    }

    @Override
    protected void initData() {
        SpannableString ss01 = new SpannableString("科比身穿24号球衣");
        ForegroundColorSpan colorSpan01 = new ForegroundColorSpan(Color.parseColor("#0099FF"));
        ss01.setSpan(colorSpan01, 4, ss01.length() - 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(1.6f);
        ss01.setSpan(sizeSpan, 4, ss01.length() - 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        StyleSpan styleSpan01 = new StyleSpan(Typeface.BOLD);
        ss01.setSpan(styleSpan01, 4, ss01.length() - 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView01.setText(ss01);

        SpannableString ss02 = new SpannableString("设置文字的前景色为淡蓝色");
        ForegroundColorSpan colorSpan02 = new ForegroundColorSpan(Color.parseColor("#0099EE"));
        ss02.setSpan(colorSpan02, 9, ss02.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView02.setText(ss02);

        SpannableString ss03 = new SpannableString("设置文字的背景色为淡绿色");
        BackgroundColorSpan colorSpan03 = new BackgroundColorSpan(Color.parseColor("#AC00FF30"));
        ss03.setSpan(colorSpan03, 9, ss03.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView03.setText(ss03);

        SpannableString ss04 = new SpannableString("万丈高楼平地起");
        RelativeSizeSpan sizeSpan01 = new RelativeSizeSpan(1.2f);
        RelativeSizeSpan sizeSpan02 = new RelativeSizeSpan(1.4f);
        RelativeSizeSpan sizeSpan03 = new RelativeSizeSpan(1.6f);
        RelativeSizeSpan sizeSpan04 = new RelativeSizeSpan(1.8f);
        RelativeSizeSpan sizeSpan05 = new RelativeSizeSpan(1.6f);
        RelativeSizeSpan sizeSpan06 = new RelativeSizeSpan(1.4f);
        RelativeSizeSpan sizeSpan07 = new RelativeSizeSpan(1.2f);
        ss04.setSpan(sizeSpan01, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ss04.setSpan(sizeSpan02, 1, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ss04.setSpan(sizeSpan03, 2, 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ss04.setSpan(sizeSpan04, 3, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ss04.setSpan(sizeSpan05, 4, 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ss04.setSpan(sizeSpan06, 5, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ss04.setSpan(sizeSpan07, 6, 7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView04.setText(ss04);

        SpannableString ss05 = new SpannableString("为文字设置删除线");
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        ss05.setSpan(strikethroughSpan, 5, ss05.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView05.setText(ss05);

        SpannableString ss06 = new SpannableString("为文字设置下划线");
        UnderlineSpan underlineSpan = new UnderlineSpan();
        ss06.setSpan(underlineSpan, 5, ss06.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView06.setText(ss06);

        SpannableString ss07 = new SpannableString("为文字设置上标");
        SuperscriptSpan superscriptSpan = new SuperscriptSpan();
        ss07.setSpan(superscriptSpan, 5, ss07.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView07.setText(ss07);

        SpannableString ss08 = new SpannableString("为文字设置下标");
        SubscriptSpan subscriptSpan = new SubscriptSpan();
        ss08.setSpan(subscriptSpan, 5, ss08.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView08.setText(ss08);

        SpannableString ss09 = new SpannableString("为文字设置粗体、斜体风格");
        StyleSpan styleSpan_B = new StyleSpan(Typeface.BOLD);
        StyleSpan styleSpan_I = new StyleSpan(Typeface.ITALIC);
        ss09.setSpan(styleSpan_B, 5, 7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ss09.setSpan(styleSpan_I, 8, 10, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView09.setHighlightColor(Color.parseColor("#36969696"));
        textView09.setText(ss09);

        SpannableString ss10 = new SpannableString("在文本中添加表情（表情）");
        Drawable drawable = getResources().getDrawable(R.mipmap.a9c);
        drawable.setBounds(0, 0, 56, 56);
        ImageSpan imageSpan = new ImageSpan(drawable);
        ss10.setSpan(imageSpan, 6, 8, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView10.setText(ss10);

        SpannableString ss11 = new SpannableString("设置图片");
        ss11.setSpan(
                // DynamicDrawableSpan.ALIGN_BASELINE表示依照基线对齐
                // DynamicDrawableSpan.ALIGN_BOTTOM表示依照底部对齐
                new DynamicDrawableSpan(DynamicDrawableSpan.ALIGN_BOTTOM) {

                    @Override
                    public Drawable getDrawable() {
                        Drawable d = getResources().getDrawable(
                                R.mipmap.a9c);
                        d.setBounds(0, 0, 56, 56);
                        return d;
                    }
                }, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);// 这里的参数0，1表示将“设”字替换为图片
        textView11.setText(ss11);

        SpannableString ss12 = new SpannableString("为文字设置点击事件");
        ForegroundColorSpan colorSpan12 = new ForegroundColorSpan(Color.parseColor("#0099EE"));
        ss12.setSpan(colorSpan12, 5, ss12.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        MyClickableSpan clickableSpan = new MyClickableSpan("https://github.com/zhaoqingyue/ZhiWo");
        ss12.setSpan(clickableSpan, 5, ss12.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView12.setMovementMethod(LinkMovementMethod.getInstance());
        textView12.setHighlightColor(Color.parseColor("#36969696"));
        textView12.setText(ss12);

        SpannableString ss13 = new SpannableString("为文字设置超链接");
        URLSpan urlSpan = new URLSpan("https://github.com/zhaoqingyue/ZhiWo");
        ss13.setSpan(urlSpan, 5, ss13.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView13.setMovementMethod(LinkMovementMethod.getInstance());
        textView13.setHighlightColor(Color.parseColor("#36969696"));
        textView13.setText(ss13);

        SpannableString ss14 = new SpannableString("基于X轴缩放");
        // ScaleXSpan中的参数大于1表示横向扩大，小于1大于0表示缩小，等于1表示正常显示
        ss14.setSpan(new ScaleXSpan(2), 0, ss14.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView14.setText(ss14);
    }

    class MyClickableSpan extends ClickableSpan {

        private String content;

        public MyClickableSpan(String content) {
            this.content = content;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(false);
        }

        @Override
        public void onClick(View widget) {
            Intent intent = new Intent(SpanActivity.this, ClickableSpanActivity.class);
            intent.putExtra("content", content);
            startActivity(intent);
        }
    }

}
