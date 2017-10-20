package com.zhaoqy.self.ui.activity.main.tool.contacts;

/**
 * Created by zhaoqy on 2017/10/18.
 */

public class MessageBean {

    private String number;
    private String content;
    private String date;
    private int type;

    public MessageBean() {

    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
