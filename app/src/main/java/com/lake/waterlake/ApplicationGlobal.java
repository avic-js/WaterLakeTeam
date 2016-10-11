package com.lake.waterlake;


import android.os.Environment;

import java.io.File;

/**
 * Created by zoushoahua on 16/8/31.
 */
public class ApplicationGlobal {


    public static String WSSessionId = "";

    public static final String loginwsName = "admin";
    public static final String loginwspwd = "111111";


    public static String basePath = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + "/";

    public static String SAVE_IMAGE_PATH = Environment.getExternalStorageDirectory()
            .getAbsolutePath()
            + File.separatorChar;//文件分隔符（根据系统自动代替字符)
    //外网地址
    public static String appUrl = "http://58.241.40.116:8777/lans";

    public static String ServletUrl = appUrl + "/jsonPort";

    public static final String URL_login = ServletUrl + "?method=login";


    public static final String URL_read = ServletUrl + "?method=read";

    public static final String SHARED_PRENFENCE_ACCOUNT = "TAIHU_ACCOUNT";

    public static final String SHARED_PRENFENCE_PASSWORD = "TAIHU_PASSWORD";

    public static final String SHARED_PRENFENCE_NAME = "TAIHU";
}
