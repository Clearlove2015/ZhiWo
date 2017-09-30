package com.zhaoqy.self.bean.eventbus;

/**
 * Created by zhaoqy on 2017/9/20.
 */

public class AccountEvent {

    public int msgId;
    public int errCode;

    public AccountEvent() {
        this.msgId = -1;
        this.errCode = -1;
    }

    public AccountEvent(int msgId, int errCode) {
        this.msgId = msgId;
        this.errCode = errCode;
    }

    public AccountEvent(AccountEvent event) {
        this.msgId = event.msgId;
        this.errCode = event.errCode;
    }
}
