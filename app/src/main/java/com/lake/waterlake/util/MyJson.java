package com.lake.waterlake.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.lake.waterlake.model.UserInfo;


public class MyJson {


    /**@auth zoushaohua
     * 对异步返回的json数据的进行处理，转变为各个查询的实体类的list
     * @param value 反馈的json串
     * @return：实体类的list列表
     */
    public List<UserInfo> getUserList(String value) {
        List<UserInfo> list = new ArrayList<UserInfo>();
        try {
            JSONArray jay = new JSONArray(value);
            for (int i = 0; i < jay.length(); i++) {
                JSONObject job = jay.getJSONObject(i);
                UserInfo info = new UserInfo();
                info.setName(job.getString("name"));

                list.add(info);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

//	public interface DetailCallBack {
//		public void getList(ArrayList<SignInfo> SignList,
//							ArrayList<CommentsInfo> CommentsList,
//							ArrayList<FoodInfo> FoodList);
//	}
}
