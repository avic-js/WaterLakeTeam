package com.lake.waterlake.network;

/**
 *
 */
public interface RequestResultCallback {
    //数据请求成功
    public void onSuccess(String str);

    //数据请求失败
    public void onFail(Exception e);

}
