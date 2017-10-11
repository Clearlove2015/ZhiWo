package com.zhaoqy.self.ui.activity.main.flashlight;

import android.support.v4.app.ActivityCompat;

import java.lang.ref.WeakReference;

import permissions.dispatcher.PermissionUtils;
import permissions.dispatcher.PermissionRequest;

/**
 * Created by zhaoqy on 2017/10/10.
 */

public class FlashPermissionsDispatcher {

    private static final int REQUEST_STARTFLASH = 0;

    private static final String[] PERMISSION_STARTFLASH = new String[] {"android.permission.CAMERA"};

    private FlashPermissionsDispatcher() {
    }

    static void startFlashWithCheck(FlashLightActivity target) {
        if (PermissionUtils.hasSelfPermissions(target, PERMISSION_STARTFLASH)) {
            target.startFlash();
        } else {
            if (PermissionUtils.shouldShowRequestPermissionRationale(target, PERMISSION_STARTFLASH)) {
                target.showRationaleForCamera(new StartFlashPermissionRequest(target));
            } else {
                ActivityCompat.requestPermissions(target, PERMISSION_STARTFLASH, REQUEST_STARTFLASH);
            }
        }
    }

    static void onRequestPermissionsResult(FlashLightActivity target, int requestCode, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_STARTFLASH:
                if (PermissionUtils.getTargetSdkVersion(target) < 23 && !PermissionUtils.hasSelfPermissions(target, PERMISSION_STARTFLASH)) {
                    target.showDeniedForCamera();
                    return;
                }
                if (PermissionUtils.verifyPermissions(grantResults)) {
                    target.startFlash();
                } else {
                    if (!PermissionUtils.shouldShowRequestPermissionRationale(target, PERMISSION_STARTFLASH)) {
                        target.showNeverAskForCamera();
                    } else {
                        target.showDeniedForCamera();
                    }
                }
                break;
            default:
                break;
        }
    }

    private static final class StartFlashPermissionRequest implements PermissionRequest {

        private final WeakReference<FlashLightActivity> weakTarget;

        private StartFlashPermissionRequest(FlashLightActivity target) {
            this.weakTarget = new WeakReference<>(target);
        }

        @Override
        public void proceed() {
            FlashLightActivity target = weakTarget.get();
            if (target == null) return;
            ActivityCompat.requestPermissions(target, PERMISSION_STARTFLASH, REQUEST_STARTFLASH);
        }

        @Override
        public void cancel() {
            FlashLightActivity target = weakTarget.get();
            if (target == null) return;
            target.showDeniedForCamera();
        }
    }
}
