package com.lake.waterlake.util;

import com.lake.waterlake.ApplicationGlobal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zoushoahua on 16/9/1.
 */
public class MyParameter {

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


}
