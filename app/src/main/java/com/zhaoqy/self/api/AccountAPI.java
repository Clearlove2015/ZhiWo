package com.zhaoqy.self.api;

import android.text.TextUtils;

import com.zhaoqy.self.app.MsgId;
import com.zhaoqy.self.bean.eventbus.AccountEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zhaoqy on 2017/9/20.
 */

public class AccountAPI {

    private static AccountAPI mAccountAPI = null;

    public static AccountAPI getInstance() {
        if (mAccountAPI == null) {
            synchronized (AccountAPI.class) {
                if (mAccountAPI == null) {
                    mAccountAPI = new AccountAPI();
                }
            }
        }
        return mAccountAPI;
    }

    public void getVerifyCode(String mobile) {
        AccountEvent event = new AccountEvent();
        event.msgId = MsgId.MSGID_GET_LOGIN_VERICODE_SUCCESS;
        event.errCode = 0;
        EventBus.getDefault().post(event);
    }

    public void mobileLogin(String mobile, String verifycode) {
        AccountEvent event = new AccountEvent();
        event.msgId = MsgId.MSGID_LOGIN_MOBILE_LOGIN_SUCCESS;
        event.errCode = 0;
        EventBus.getDefault().post(event);
    }

    public void login(String loginName, String password) {
        AccountEvent event = new AccountEvent();
        event.msgId = MsgId.MSGID_LOGIN_ACCOUNT_LOGIN_SUCCESS;
        event.errCode = 0;
        EventBus.getDefault().post(event);
    }

    /**
     * 验证手机格式
     */
    public static boolean isPhoneNum(String number) {
		/*
		 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
        // "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位
        String num = "[1][358]\\d{9}";
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            // matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }

}
