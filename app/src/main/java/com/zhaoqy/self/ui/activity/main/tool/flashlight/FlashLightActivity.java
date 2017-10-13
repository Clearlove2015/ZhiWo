package com.zhaoqy.self.ui.activity.main.tool.flashlight;

import android.Manifest;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.base.BaseToolboxActivity;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;

public class FlashLightActivity extends BaseToolboxActivity implements View.OnClickListener{

    @BindView(R.id.root)
    RelativeLayout root;
    @BindView(R.id.flashLight)
    ImageButton flashLight;
    @BindView(R.id.sos)
    ImageButton sos;

    Drawable[] controlDrawbles = null;
    Camera camera;
    Camera.Parameters parameters;
    volatile boolean continueSos;
    Handler sosHandler;
    final int FLASH_LIGHT_ON = 1;
    final int FLASH_LIGHT_OFF = -1;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_flash_light;
    }

    @Override
    protected void initData() {
        Resources resources = getResources();
        controlDrawbles = new Drawable[]{
                resources.getDrawable(R.mipmap.flash_light_off),
                resources.getDrawable(R.mipmap.flash_light_on),
                resources.getDrawable(R.mipmap.sos_off),
                resources.getDrawable(R.mipmap.sos_on),
                resources.getDrawable(R.mipmap.background),
                resources.getDrawable(R.mipmap.background_on)};

        flashLight.setTag("open");
        sos.setTag("close");
        FlashPermissionsDispatcher.startFlashWithCheck(this);
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    public void startFlash() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenHeight = metrics.heightPixels;
        RelativeLayout.LayoutParams flashLightParams = (RelativeLayout.LayoutParams) flashLight
                .getLayoutParams();
        flashLightParams.setMargins(0, screenHeight * 1 / 2, 0, 0);
        flashLight.setLayoutParams(flashLightParams);
        //RelativeLayout.LayoutParams sosParams = (RelativeLayout.LayoutParams) sos.getLayoutParams();
        //sosParams.setMargins(0, screenHeight * 4 / 5, 0, 0);
        //sos.setLayoutParams(sosParams);
        camera = Camera.open();
        parameters = camera.getParameters();
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(parameters);
        camera.startPreview();
        flashLight.setImageDrawable(controlDrawbles[1]);
        root.setBackground(controlDrawbles[5]);
    }

    /**
     * 为什么要获取这个权限给用户的说明
     *
     * @param request
     */
    @OnShowRationale(Manifest.permission.CAMERA)
    public void showRationaleForCamera(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setTitle("权限申请")
                .setMessage("你将用的闪光灯功能，请你授权照相机权限！")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .show();
    }

    /**
     * 如果用户不授予权限调用的方法
     */
    @OnPermissionDenied(Manifest.permission.CAMERA)
    public void showDeniedForCamera() {
        Toast.makeText(this, "没有授予照相机的权限", Toast.LENGTH_SHORT).show();
    }

    /**
     * 如果用户选择了让设备“不再询问”，而调用的方法
     */
    @OnNeverAskAgain(Manifest.permission.CAMERA)
    public void showNeverAskForCamera() {
        Toast.makeText(this, "Don't ask again!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 委托授权
        FlashPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    protected void onDestroy() {
        if (camera != null) {
            camera.release();
            camera = null;
        }
        super.onDestroy();
    }

    private void turnOnFlashLight() {
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(parameters);
        camera.startPreview();
    }

    private void turnOffFlashLight() {
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(parameters);
        camera.stopPreview();
    }

    @OnClick({R.id.flashLight, R.id.sos})
    public void onClick(View view) {
        ImageButton controlIb = (ImageButton) view;
        continueSos = false;
        switch (view.getId()) {
            case R.id.flashLight: {
                sos.setImageDrawable(controlDrawbles[2]);
                sos.setTag("close");
                if (view.getTag().equals("close")) {
                    turnOnFlashLight();
                    view.setTag("open");
                    controlIb.setImageDrawable(controlDrawbles[1]);
                    root.setBackground(controlDrawbles[5]);
                } else {
                    turnOffFlashLight();
                    view.setTag("close");
                    controlIb.setImageDrawable(controlDrawbles[0]);
                    root.setBackground(controlDrawbles[4]);
                }
                break;
            }
            case R.id.sos: {
                flashLight.setImageDrawable(controlDrawbles[0]);
                flashLight.setTag("close");
                if (view.getTag().equals("close")) {
                    view.setTag("open");
                    controlIb.setImageDrawable(controlDrawbles[3]);
                    continueSos = true;
                    sosHandler = new Handler() {

                        public void handleMessage(Message msg) {
                            if (!continueSos)
                                return;
                            switch (msg.arg1) {
                                case FLASH_LIGHT_ON:
                                    turnOnFlashLight();
                                    root.setBackground(controlDrawbles[5]);
                                    break;
                                case FLASH_LIGHT_OFF:
                                    turnOffFlashLight();
                                    root.setBackground(controlDrawbles[4]);
                                    break;
                                default:
                                    break;
                            }
                        }
                    };
                    new Thread() {
                        public void run() {
                            while (continueSos) {
                                Message msg = Message.obtain();
                                msg.arg1 = FLASH_LIGHT_ON;
                                sosHandler.sendMessage(msg);
                                try {
                                    Thread.sleep(600);
                                } catch (Exception e) {
                                    System.out.println("exception:" + e.getMessage());
                                }
                                Message message = Message.obtain();
                                message.arg1 = FLASH_LIGHT_OFF;
                                sosHandler.sendMessage(message);
                                try {
                                    Thread.sleep(300);
                                } catch (Exception e) {
                                    System.out.println("exception:" + e.getMessage());
                                }
                            }
                            Message message = Message.obtain();
                            message.arg1 = FLASH_LIGHT_OFF;
                            sosHandler.sendMessage(message);
                        }
                    }.start();

                } else {
                    view.setTag("close");
                    turnOffFlashLight();
                    controlIb.setImageDrawable(controlDrawbles[2]);
                    root.setBackground(controlDrawbles[4]);
                }
                break;
            }
        }
    }
}
