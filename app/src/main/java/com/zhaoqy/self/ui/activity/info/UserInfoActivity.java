package com.zhaoqy.self.ui.activity.info;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.litesuits.common.assist.Network;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.othershe.nicedialog.ViewHolder;
import com.squareup.picasso.Picasso;
import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.base.BaseToolboxActivity;
import com.zhaoqy.self.ui.widget.VerticalCard;
import com.zhaoqy.self.util.PermissionUtils;
import com.zhaoqy.self.util.PhotoHelper;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class UserInfoActivity extends BaseToolboxActivity implements View.OnClickListener {

    public static final int REQUEST_PHOTO_TAKE = 1024;                         // 拍照请求
    public static final int REQUEST_PHOTO_GALLERY = REQUEST_PHOTO_TAKE + 1; // 从相册选取请求
    public static final int REQUEST_PHOTO_CUT = REQUEST_PHOTO_GALLERY + 1;  // 图片裁剪请求

    @BindView(R.id.head)
    ImageView head;
    @BindView(R.id.nickname)
    VerticalCard nickname;
    @BindView(R.id.signature)
    VerticalCard signature;

    private String NN_path = null;// 如果是中兴n5s，保存拍照的相片路径改变
    private String cutPath = null;// 裁减后照片路径

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_userinfo;
    }

    @Override
    protected void initData() {
        cutPath = PhotoHelper.getPhotoPath(0);
        NN_path = PhotoHelper.getPath();

        Bitmap headBitmap = PhotoHelper.getPhotoBitmap();
        if (headBitmap != null) {
            //head.setImageBitmap(PhotoHelper.getCircleBitmap(headBitmap));
            head.setImageBitmap(headBitmap);
        } else {
            Picasso.with(this).load(R.mipmap.head).into(head);
        }
    }

    @OnClick({R.id.personal, R.id.id, R.id.head_layout, R.id.head, R.id.nickname, R.id.mobile, R.id.weixin,
            R.id.qq, R.id.email, R.id.airpay, R.id.qrcode_layout, R.id.sex, R.id.area, R.id.signature,
            R.id.logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.personal: {
                /**
                 * 个人主页
                 */
                Intent intent = new Intent(this, HomePageActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.id: {
                /**
                 * 身份证号
                 */
                break;
            }
            case R.id.head_layout: {
                /**
                 * 头像模块
                 */
                if (PermissionUtils.isNeedPermissionForStorage(this)) {
                    Toast.makeText(this, "请打开储存空间权限", Toast.LENGTH_SHORT).show();
                    return;
                }
                modifyHeadPic();
                break;
            }
            case R.id.head: {
                /**
                 * 头像
                 */
                Intent intent = new Intent(this, BigImageActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.nickname: {
                /**
                 * 昵称
                 */
                String nickname = "zhaoqy";
                Intent intent = new Intent(this, EditActivity.class);
                intent.putExtra(EditActivity.EDIT_TITLE, "昵称");
                intent.putExtra(EditActivity.EDIT_KEY, nickname);
                startActivityForResult(intent, EditActivity.REQUEST_NICKNAME);
                break;
            }
            case R.id.mobile: {
                /**
                 * 手机号
                 */
                break;
            }
            case R.id.weixin: {
                /**
                 * 微信号
                 */
                break;
            }
            case R.id.qq: {
                /**
                 * QQ号
                 */
                break;
            }
            case R.id.email: {
                /**
                 * 邮箱地址
                 */
                break;
            }
            case R.id.airpay: {
                /**
                 * 支付宝
                 */
                break;
            }
            case R.id.qrcode_layout: {
                /**
                 * 二维码名片
                 */
                Intent intent = new Intent(this, QrcodeActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.sex: {
                /**
                 * 性别
                 */
                break;
            }
            case R.id.area: {
                /**
                 * 地区
                 */
                break;
            }
            case R.id.signature: {
                /**
                 * 个性签名
                 */
                String sign = "天道酬勤";
                Intent intent = new Intent(this, EditActivity.class);
                intent.putExtra(EditActivity.EDIT_TITLE, "个性签名");
                intent.putExtra(EditActivity.EDIT_KEY, sign);
                startActivityForResult(intent, EditActivity.REQUEST_SIGNATURE);
                break;
            }
            case R.id.logout: {
                /**
                 * 退出登录
                 */
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case EditActivity.REQUEST_NICKNAME: {
                    if (data != null) {
                        Bundle bundle = data.getExtras();
                        String name = bundle.getString(EditActivity.EDIT_KEY);
                        nickname.setContent(name);
                    }
                    break;
                }
                case EditActivity.REQUEST_SIGNATURE: {
                    if (data != null) {
                        Bundle bundle = data.getExtras();
                        String name = bundle.getString(EditActivity.EDIT_KEY);
                        signature.setContent(name);
                    }
                    break;
                }
                case REQUEST_PHOTO_TAKE: {
                    int level = Build.VERSION.SDK_INT;
                    String model = Build.MODEL;
                    /**
                     * 针对中兴n5s，特殊处理路径
                     */
                    if ("ZTE N5S".equals(model)) {
                        File temp = new File(NN_path + "/photo.jpg");
                        if (!TextUtils.isEmpty(NN_path)) {
                            PhotoHelper.cropImageUri(this, Uri.fromFile(temp), 1,
                                    1, PhotoHelper.PHOTOSIZE,
                                    PhotoHelper.PHOTOSIZE, REQUEST_PHOTO_CUT);
                        }
                        /**
                         * 目前只针对5.0系统以上且小米2手机修改。 原因：小米的5.0
                         * ROM上拍照裁减后保存图片到本地，无法保存（由系统负责，写入数据为0B）
                         * 处理：拍照后直接压缩处理图片显示（去掉裁减图片，注：其他版本手机等都保持不变）
                         */
                    } else if (("MI 2".equals(model)) || "Moto X Pro".equals(model)
                            && level > 20) {
                        if (!Network.isConnected(this)) {
                            /**
                             * 如果没网就不在本地保存头像的url，防止下一次登录会显示本地头像而不是从服务器获取
                             */
                            PhotoHelper.saveUserPhotoUrl("ERROR : NO NET !");
                            Toast.makeText(UserInfoActivity.this, R.string.common_set_failed,
                                    Toast.LENGTH_SHORT).show();
                        }else
                            updatePhoto();
                    } else {
                        PhotoHelper.cropImageUri(this,
                                Uri.fromFile(new File(cutPath)), 1, 1,
                                PhotoHelper.PHOTOSIZE, PhotoHelper.PHOTOSIZE,
                                REQUEST_PHOTO_CUT);
                    }
                    break;
                }
                case REQUEST_PHOTO_GALLERY: {
                    /**
                     * 从相册选取请求号
                     */
                    Uri uri = data.getData();
                    PhotoHelper.startCrop(this, uri,
                            Uri.fromFile(new File(PhotoHelper.getPhotoPath(0))), REQUEST_PHOTO_CUT);
                    break;
                }
                case REQUEST_PHOTO_CUT: {
                    /**
                     * 图片裁剪
                     */
                    if (!Network.isConnected(this)) {
                        /**
                         * 如果没网就不在本地保存头像的url，防止下一次登录会显示本地头像而不是从服务器获取
                         */
                        PhotoHelper.saveUserPhotoUrl("ERROR : NO NET !");
                        Toast.makeText(UserInfoActivity.this, R.string.common_set_failed,
                                Toast.LENGTH_SHORT).show();
                    }else {
                        updatePhoto();
                    }
                    break;
                }
            }
        }
    }

    private void updatePhoto() {
        byte[] photoData = PhotoHelper.getUserPhotoData(cutPath);
        Toast.makeText(this, "修改头像成功", Toast.LENGTH_SHORT).show();
        Bitmap headBitmap = PhotoHelper.getPhotoBitmap(cutPath);
        if (headBitmap != null) {
            head.setImageBitmap(headBitmap);
            //head.setImageBitmap(PhotoHelper.getCircleBitmap(headBitmap));
        }
        PhotoHelper.saveBitmapToTrueHead(headBitmap);
    }

    private void modifyHeadPic() {
        NiceDialog.init()
                .setLayoutId(R.layout.dialog_setphoto)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
                        /**
                         * 拍照
                         */
                        holder.setOnClickListener(R.id.setphoto_take_photo, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                if (PermissionUtils.isNeedPermission(UserInfoActivity.this,
                                        Manifest.permission.CAMERA, PermissionUtils.CAMERA)) {
                                    Toast.makeText(UserInfoActivity.this, "请打开摄像头权限", Toast.LENGTH_SHORT)
                                            .show();
                                } else {
                                    openCamra();
                                }
                            }
                        });

                        /**
                         * 从相册中选取
                         */
                        holder.setOnClickListener(R.id.setphoto_choose_photo, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                               PhotoHelper.onChoosePhoto(UserInfoActivity.this,
                                        REQUEST_PHOTO_GALLERY);
                            }
                        });

                        /**
                         * 取消
                         */
                        holder.setOnClickListener(R.id.setphoto_cancel, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }
                })
                .setDimAmount(0.6f)
                .setOutCancel(true)
                .setShowBottom(true)
                .show(getSupportFragmentManager());
    }



    private void openCamra() {
        PhotoHelper.openCamra(this, cutPath, NN_path, REQUEST_PHOTO_TAKE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PermissionUtils.CAMERA: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamra();
                } else {
                    Toast.makeText(UserInfoActivity.this, "打开摄像头失败", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }
}
