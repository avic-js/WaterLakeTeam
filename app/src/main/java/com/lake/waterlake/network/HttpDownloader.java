package com.lake.waterlake.network;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.lake.waterlake.util.FileUtils;


/**
 * 下载文件辅助类
 *
 * @author 陈佳希
 * @date 2013-10-10
 */
public class HttpDownloader {
    private URL url = null;

    /**
     * Download file from a specific Url and save as fileName to SDCard
     *
     * @param
     * @return -1 for failure 0 for success 1 file existence
     */
    public int downFile(String urlStr, String fullPathName) {
        InputStream inputStream = null;
        try {
            FileUtils fileUtils = new FileUtils();

            if (fileUtils.isFileExist(fullPathName)) {
                return 1;
            } else {
                inputStream = getInputStreamFromUrl(urlStr);
                File resultFile = fileUtils.writeFromInput(fullPathName,
                        inputStream);
                if (resultFile == null) {
                    return -1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public InputStream getInputStreamFromUrl(String urlStr)
            throws MalformedURLException, IOException {
        url = new URL(urlStr);
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        InputStream inputStream = urlConn.getInputStream();
        return inputStream;
    }
}
