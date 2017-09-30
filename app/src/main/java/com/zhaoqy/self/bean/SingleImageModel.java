package com.zhaoqy.self.bean;

import java.io.Serializable;

/**
 * Created by zhaoqy on 2017/9/29.
 */

public class SingleImageModel implements Serializable {

    public String path;
    public boolean isPicked;
    public long date;
    public long id;

    public SingleImageModel(String path, boolean isPicked, long date, long id){
        this.path = path;
        this.isPicked = isPicked;
        this.date = date;
        this.id = id;
    }

    public SingleImageModel(){

    }

    public boolean isThisImage(String path){
        return this.path.equalsIgnoreCase(path);
    }
}
