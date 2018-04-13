package com.zhaoqy.self.ui.activity.main.knowledge.canvas;

import android.content.Context;
import android.widget.ImageView;

import com.lqr.ninegridimageview.LQRNineGridImageView;
import com.lqr.ninegridimageview.LQRNineGridImageViewAdapter;
import com.squareup.picasso.Picasso;
import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.base.BaseBarActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CanvasActivity extends BaseBarActivity {

    @BindView(R.id.ngiv1)
    LQRNineGridImageView mNgiv1;
    @BindView(R.id.ngiv2)
    LQRNineGridImageView mNgiv2;
    @BindView(R.id.ngiv3)
    LQRNineGridImageView mNgiv3;
    @BindView(R.id.ngiv4)
    LQRNineGridImageView mNgiv4;
    @BindView(R.id.ngiv5)
    LQRNineGridImageView mNgiv5;
    @BindView(R.id.ngiv6)
    LQRNineGridImageView mNgiv6;
    @BindView(R.id.ngiv7)
    LQRNineGridImageView mNgiv7;
    @BindView(R.id.ngiv8)
    LQRNineGridImageView mNgiv8;
    @BindView(R.id.ngiv9)
    LQRNineGridImageView mNgiv9;

    private List<String> mData1, mData2, mData3, mData4, mData5, mData6, mData7, mData8, mData9;

    private String[] IMG_URL_ARR = {
            "https://pic4.zhimg.com/02685b7a5f2d8cbf74e1fd1ae61d563b_xll.jpg",
            "https://pic4.zhimg.com/fc04224598878080115ba387846eabc3_xll.jpg",
            "https://pic3.zhimg.com/d1750bd47b514ad62af9497bbe5bb17e_xll.jpg",
            "https://pic4.zhimg.com/da52c865cb6a472c3624a78490d9a3b7_xll.jpg",
            "https://pic3.zhimg.com/0c149770fc2e16f4a89e6fc479272946_xll.jpg",
            "https://pic1.zhimg.com/76903410e4831571e19a10f39717988c_xll.png",
            "https://pic3.zhimg.com/33c6cf59163b3f17ca0c091a5c0d9272_xll.jpg",
            "https://pic4.zhimg.com/52e093cbf96fd0d027136baf9b5cdcb3_xll.png",
            "https://pic3.zhimg.com/f6dc1c1cecd7ba8f4c61c7c31847773e_xll.jpg"
    };

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_canvas;
    }

    @Override
    protected void initData() {

        LQRNineGridImageViewAdapter adapter = new LQRNineGridImageViewAdapter<String>() {

            @Override
            protected void onDisplayImage(Context context, ImageView imageView, String s) {
                Picasso.with(context)
                        .load(s)
                        .placeholder(R.mipmap.default_img)
                        .error(R.mipmap.default_img_failed)
                        .into(imageView);
            }

            /**
             * 自定义生成ImageView方式，用于九宫格头像中的一个个图片控件，可以设置ScaleType等属性
             */
            @Override
            protected ImageView generateImageView(Context context) {
                return super.generateImageView(context);
            }
        };

        mData1 = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            mData1.add(IMG_URL_ARR[i]);
        }
        mNgiv1.setAdapter(adapter);
        mNgiv1.setImagesData(mData1);

        mData2 = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            mData2.add(IMG_URL_ARR[i]);
        }
        mNgiv2.setAdapter(adapter);
        mNgiv2.setImagesData(mData2);

        mData3 = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            mData3.add(IMG_URL_ARR[i]);
        }
        mNgiv3.setAdapter(adapter);
        mNgiv3.setImagesData(mData3);

        mData4 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            mData4.add(IMG_URL_ARR[i]);
        }
        mNgiv4.setAdapter(adapter);
        mNgiv4.setImagesData(mData4);

        mData5 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mData5.add(IMG_URL_ARR[i]);
        }
        mNgiv5.setAdapter(adapter);
        mNgiv5.setImagesData(mData5);

        mData6 = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            mData6.add(IMG_URL_ARR[i]);
        }
        mNgiv6.setAdapter(adapter);
        mNgiv6.setImagesData(mData6);

        mData7 = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            mData7.add(IMG_URL_ARR[i]);
        }
        mNgiv7.setAdapter(adapter);
        mNgiv7.setImagesData(mData7);

        mData8 = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            mData8.add(IMG_URL_ARR[i]);
        }
        mNgiv8.setAdapter(adapter);
        mNgiv8.setImagesData(mData8);

        mData9 = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            mData9.add(IMG_URL_ARR[i]);
        }
        mNgiv9.setAdapter(adapter);
        mNgiv9.setImagesData(mData9);
    }

}
