package com.lake.waterlake.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.lake.waterlake.ApplicationGlobal;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * 解决数值 小数点
 * @date 2017-5-8
 * 
 */
public class StringFixFun
{

    
    /**
     * 去除数字中多余的0
     *
     * @param
     * @return
     * @throws IOException
     */
    public static String getPrettyNumber(String number,int num) {
        double fixstr;
        if (num==0){
            if (number == "" || number.length()==0)
                return "--";
            return number;
        }

        if (number == "" || number.length()==0){
            return "--";
        }else{
            fixstr = BigDecimal.valueOf(Double.parseDouble(number))
                    .stripTrailingZeros().doubleValue();
        }

        NumberFormat ddf1= NumberFormat.getNumberInstance() ;
        ddf1.setMaximumFractionDigits(num);

        return ddf1.format(fixstr) ;
    }

}
