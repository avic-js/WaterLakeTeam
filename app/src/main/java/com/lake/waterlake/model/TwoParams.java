package com.lake.waterlake.model;

/**
 * Created by yyh on 16/9/9.
 * 河道水质，饮水安全
 *
 */
public class TwoParams {

    public TwoParams(String obj1,String obj2){
        this.obj1 = obj1;
        this.obj2 = obj2;
    }

    private  String obj1;

    private  String obj2;

    public String getObj1() {
        return obj1;
    }

    public void setObj1(String obj1) {
        this.obj1 = obj1;
    }

    public String getObj2() {
        return obj2;
    }

    public void setObj2(String obj2) {
        this.obj2 = obj2;
    }
}
