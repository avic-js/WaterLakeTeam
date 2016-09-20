package com.lake.waterlake.util;

import com.lake.waterlake.ApplicationGlobal;
import com.lake.waterlake.network.AsyncHttpPost;
import com.lake.waterlake.network.BaseRequest;
import com.lake.waterlake.network.DefaultThreadPool;
import com.lake.waterlake.network.RequestResultCallback;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyh on 16/9/20.
 *
 * 通用平台 处理封装类
 */
public class WSFunction {

    /**@auth zoushaohua
     * 对查询的参数进行处理，编写通用的方法调用通用发布平台可以对参数进行组合
     * @param sessionId 通用发布平台的sessionid
     * @param servName：服务名称
     * @param params：查询的参数名
     * @param Conditions：对应各个参数的值
     * @return参数的list列表
     */
    public static List<RequestParameter> getParameters(String sessionId, String servName,
                                                       String[] params, String[] Conditions) {
        List<RequestParameter> parameters = new ArrayList<RequestParameter>();
        parameters.add(new RequestParameter("sessionId", sessionId));
        parameters.add(new RequestParameter("servName", servName));
        if (params != null && params.length > 0) {
            RequestParameter condtion = null;
            for (int i = 0; i < params.length; i++) {
                condtion = new RequestParameter("condtion", Conditions[i]);
                parameters.add(condtion);
            }
            RequestParameter param = null;
            for (int i = 0; i < params.length; i++) {
                param = new RequestParameter("params", params[i]);
                parameters.add(param);
            }
        }
        return parameters;
    }

    /**
     * 初始化通用平台登陆session
     */
    public static void initwsLogin() {
        List<RequestParameter> parameters = new ArrayList<RequestParameter>();
        parameters.add(new RequestParameter("userName", ApplicationGlobal.loginwsName));
        parameters.add(new RequestParameter("password", ApplicationGlobal.loginwspwd));
        System.out.println("session 0--> 连接成功 ！！！！！");
        AsyncHttpPost httpget = new AsyncHttpPost(ApplicationGlobal.URL_login, parameters,

                new RequestResultCallback() {
                    @Override
                    public void onSuccess(String str) {
                        try {
                            JSONObject jsonObj = new JSONObject(str);
                            ApplicationGlobal.WSSessionId = jsonObj.getString("login");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        e.printStackTrace();
                    }
                });
        DefaultThreadPool.getInstance().execute(httpget);
        BaseRequest.getBaseRequests().add(httpget);
    }


}
