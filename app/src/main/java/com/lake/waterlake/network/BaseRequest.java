package com.lake.waterlake.network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpUriRequest;

import android.util.Log;

/**
 * 目标
 * 1、安全有�?
 * 2、高�?
 * 3、易用性 易控性
 * 4、activity停止后停止该activity�?��的线程�?
 * <p/>
 * 5、监测内存，当内存溢出的时候 自动垃圾回收，清理资源，当程序结束之后终止线程�?
 *
 * @author
 * @date 2016-10-10
 */
public class BaseRequest implements Runnable, Serializable {
    private static final long serialVersionUID = 1L;
    public HttpUriRequest request = null;
    private static List<BaseRequest> requests;

    public static List<BaseRequest> getBaseRequests() {
        if (requests == null)
            requests = new ArrayList<BaseRequest>();
        return requests;
    }

    /**
     *
     */
    protected String url = null;
    /**
     * default is 5 ,to set .
     */
    protected int connectTimeout = 30000;
    /**
     * default is 5 ,to set .
     */
    protected int readTimeout = 30000;

    protected RequestResultCallback requestCallback = null;

    @Override
    public void run() {
    }

    protected void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    protected void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public HttpUriRequest getRequest() {
        return request;
    }

    public static void cancelAllRequest() {
        if (requests != null && requests.size() > 0) {
            for (BaseRequest request : requests) {
                if (request.getRequest() != null) {
                    try {
                        request.getRequest().abort();
                        requests.remove(request.getRequest());
                        Log.d("netlib", "netlib ,onDestroy request to  "
                                + request.getRequest().getURI()
                                + "  is removed");
                    } catch (UnsupportedOperationException e) {
                        // do nothing .
                    }
                }
            }
        }
    }

    /**
     * 取消当前的网络请求
     */
    public void cancelRequest(List<BaseRequest> prmRequests) {
        if (prmRequests != null && prmRequests.size() > 0) {
            for (BaseRequest request : prmRequests) {
                this.getRequest().abort();
                requests.remove(this.getRequest());
            }
        }

    }
}
