package com.lake.waterlake.xml.parser;

import com.lake.waterlake.model.UserInfo;
import com.lake.waterlake.xml.IUserInfoParser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by yyh on 16/12/29.
 */
public class SaxUserInfoParser implements IUserInfoParser {


    /**
     * 解析输入流 得到UserInfo对象集合
     * @param is
     * @return
     * @throws Exception
     */
    @Override
    public List<UserInfo> parse(InputStream is) throws Exception {
        /**
         * 取得SAXParserFactory实例
         * 从factory获取SAXParser实例
         * 实例化自定义Handler
         * 根据自定义Handler规则解析输入流
         */
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser =  factory.newSAXParser();
        MyHandelr myHandelr = new MyHandelr();
        parser.parse(is,myHandelr);
        return myHandelr.getUserInfos();
    }

    /**
     * 序列化UserInfo对象集合 得到XML形式的字符串
     * @param userinfo
     * @return
     * @throws Exception
     */
    @Override
    public String serialize(List<UserInfo> userinfo) throws Exception {



        return null;
    }

    /**
     * 当执行文档时遇到起始节点，startElement方法将会被调用，我们可以获取起始节点相关信息；
     * 然后characters方法被调用，我们可以获取节点内的文本信息；
     * 最后endElement方法被调用，我们可以做收尾的相关操作
     */
    private class MyHandelr extends DefaultHandler {

        List<UserInfo> userInfos;

        UserInfo userInfo;

        StringBuilder builder;

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            userInfos = new ArrayList<UserInfo>();
            builder =  new StringBuilder();
        }

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            if (localName.equals("person")){
                userInfo = new UserInfo();
            }
            builder.setLength(0);//将字符长度设置为0 以便重新开始读取元素内的字符节点
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
            if (localName.equals("name")){
                userInfo.setName(builder.toString());
            }
            if (localName.equals("cname")){
                userInfo.setCname(builder.toString());
            }
            if (localName.equals("password")){
                userInfo.setPassword(builder.toString());
            }
            if (localName.equals("person")){
                userInfos.add(userInfo);
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
            builder.append(ch, start, length);//将读取的字符数组追加到builder中
        }


        //返回解析后得到的UserInfo对象集合
        public List<UserInfo> getUserInfos() {
            return userInfos;
        }
    }
}
