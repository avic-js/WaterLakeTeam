package com.lake.waterlake.model;

/**
 * Created by yyh on 16/9/9.
 */
public class FourParams {

    private  String obj1;
    private  String obj2;
    private  String obj3;
    private  String obj4;


    public FourParams(String obj1, String obj2, String obj3, String obj4) {
        this.obj1 = obj1;
        this.obj2 = obj2;
        this.obj3 = obj3;
        this.obj4 = obj4;
    }

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

    public String getObj3() {
        return obj3;
    }

    public void setObj3(String obj3) {
        this.obj3 = obj3;
    }

    public String getObj4() {
        return obj4;
    }

    public void setObj4(String obj4) {
        this.obj4 = obj4;
    }
}
