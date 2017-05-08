package com.lake.waterlake.xml;

import com.lake.waterlake.model.UserInfo;

import java.io.InputStream;
import java.util.List;

/**
 * Created by yyh on 16/12/22.
 */
public interface IUserInfoParser {
    /**
     * 解析输入流 得到UserInfo对象集合
     *
     * @param is
     * @return
     * @throws Exception
     */

    List<UserInfo> parse(InputStream is) throws Exception;


    /**
     * 序列化UserInfo对象集合 得到XML形式的字符串
     * @param userinfo
     * @return
     * @throws Exception
     */
    String serialize(List<UserInfo> userinfo) throws Exception;

}
