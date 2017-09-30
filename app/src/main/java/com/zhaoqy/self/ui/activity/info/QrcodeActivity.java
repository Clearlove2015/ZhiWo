package com.zhaoqy.self.ui.activity.info;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.Toast;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.base.BaseToolboxActivity;

import butterknife.BindView;
import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

public class QrcodeActivity extends BaseToolboxActivity {

    @BindView(R.id.qrcode)
    ImageView qrcode;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_qrcode;
    }

    @Override
    protected void initData() {
        String content = "My name is zhaoqingyue.";
        Bitmap bitmap = QRCodeEncoder.syncEncodeQRCode(content, BGAQRCodeUtil.dp2px(this, 150));
        if (bitmap != null) {
            qrcode.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "生成二维码失败", Toast.LENGTH_SHORT).show();
        }
    }
}
