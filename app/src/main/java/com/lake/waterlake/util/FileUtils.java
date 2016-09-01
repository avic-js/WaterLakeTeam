package com.lake.waterlake.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import com.lake.waterlake.ApplicationGlobal;

/**
 *
 * @date 2016-10-10
 * 
 */
public class FileUtils
{
    private static String SDPATH;
    
    private int FILESIZE = 4 * 1024;
    
    public static String getSDPATH()
    {
        return SDPATH;
    }
    
    public FileUtils()
    {
        // 得到当前外部存储设备的目录( /SDCARD )
        SDPATH = ApplicationGlobal.SAVE_IMAGE_PATH;
    }
    
    /**
     * 在SD卡上创建文件
     * 
     * @param fileName
     * @return
     * @throws IOException
     */
    public File createSDFile(String fileName) throws IOException
    {
        File file = new File(SDPATH + fileName);
        file.createNewFile();
        return file;
    }
    
    /**
     * 在SD卡上创建目录
     * 
     * @param dirName
     * @return
     */
    public File createSDDir(String dirName)
    {
        File dir = new File(SDPATH + dirName);
        dir.mkdirs();
        return dir;
    }
    
    /**
     * 判断SD卡上的文件夹是否存在
     * 
     * @param fileName
     * @return
     */
    public boolean isFileExist(String fileName)
    {
        File file = new File(SDPATH + fileName);
        return file.exists();
    }
    
    /**
     * 删除文件
     * 
     * @param fileName
     * @return
     */
    public boolean deleteFile(String fileName)
    {
        File file = new File(SDPATH + fileName);
        return file.delete();
    }
    
    /**
     * @return
     * 
     *         方法描述：根据传入的绝对路径删除文件 （参数含义说明如下）
     */
    public boolean deleteFileWithPath(String absolutePath)
    {
        File file = new File(absolutePath);
        return file.delete();
    }
    
    /**
     * 将一个InputStream里面的数据写入到SD卡中
     * 
     * @param path
     * @param fileName
     * @param input
     * @return
     */
    public File write2SDFromInput(String fileName, InputStream input)
    {
        File file = null;
        OutputStream output = null;
        try
        {
            int lastSeperator = fileName.lastIndexOf("/");
            if (lastSeperator != -1)
            {
                String path = fileName.substring(0, lastSeperator);
                createSDDir(path);
            }
            file = createSDFile(fileName);
            output = new FileOutputStream(file);
            byte[] buffer = new byte[FILESIZE];
            int len = 0;
            while ((len = input.read(buffer)) != -1)
            {
                output.write(buffer, 0, len);
            }
            output.flush();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                output.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return file;
    }
    
    /**
     * @param fileName
     * @param bytes
     * @return
     * 
     *         方法描述：把byte数组写入到文件 位于SDCARD根目录下 （参数含义说明如下） 文件名，字节数组(建议不能太大，容易内存溢出)
     */
    public String writeFile2SD(String fileName, byte[] bytes)
    {
        File file = null;
        OutputStream output = null;
        try
        {
            int lastSeperator = fileName.lastIndexOf("/");
            if (lastSeperator != -1)
            {
                String path = fileName.substring(0, lastSeperator);
                createSDDir(path);
            }
            file = createSDFile(fileName);
            output = new FileOutputStream(file);
            output.write(bytes);
            output.flush();
            return file.getAbsolutePath();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                output.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return "";
    }
    
    // 建立一个MIME类型与文件后缀名的匹配表
    private final String[][] MIME_MapTable = {
            // {后缀名， MIME类型}
            { ".3gp", "video/3gpp" },
            { ".apk", "application/vnd.android.package-archive" },
            { ".asf", "video/x-ms-asf" }, { ".avi", "video/x-msvideo" },
            { ".bin", "application/octet-stream" }, { ".bmp", "image/bmp" },
            { ".c", "text/plain" }, { ".class", "application/octet-stream" },
            { ".conf", "text/plain" }, { ".cpp", "text/plain" },
            { ".doc", "application/msword" },
            { ".exe", "application/octet-stream" }, { ".gif", "image/gif" },
            { ".gtar", "application/x-gtar" }, { ".gz", "application/x-gzip" },
            { ".h", "text/plain" }, { ".htm", "text/html" },
            { ".html", "text/html" }, { ".jar", "application/java-archive" },
            { ".java", "text/plain" }, { ".jpeg", "image/jpeg" },
            { ".jpg", "image/jpeg" }, { ".js", "application/x-javascript" },
            { ".log", "text/plain" }, { ".m3u", "audio/x-mpegurl" },
            { ".m4a", "audio/mp4a-latm" }, { ".m4b", "audio/mp4a-latm" },
            { ".m4p", "audio/mp4a-latm" }, { ".m4u", "video/vnd.mpegurl" },
            { ".m4v", "video/x-m4v" }, { ".mov", "video/quicktime" },
            { ".mp2", "audio/x-mpeg" }, { ".mp3", "audio/x-mpeg" },
            { ".mp4", "video/mp4" },
            { ".mpc", "application/vnd.mpohun.certificate" },
            { ".mpe", "video/mpeg" }, { ".mpeg", "video/mpeg" },
            { ".mpg", "video/mpeg" }, { ".mpg4", "video/mp4" },
            { ".mpga", "audio/mpeg" },
            { ".msg", "application/vnd.ms-outlook" }, { ".ogg", "audio/ogg" },
            { ".pdf", "application/pdf" }, { ".png", "image/png" },
            { ".pps", "application/vnd.ms-powerpoint" },
            { ".ppt", "application/vnd.ms-powerpoint" },
            { ".prop", "text/plain" },
            { ".rar", "application/x-rar-compressed" },
            { ".rc", "text/plain" }, { ".rmvb", "audio/x-pn-realaudio" },
            { ".rtf", "application/rtf" }, { ".sh", "text/plain" },
            { ".tar", "application/x-tar" },
            { ".tgz", "application/x-compressed" }, { ".txt", "text/plain" },
            { ".wav", "audio/x-wav" }, { ".wma", "audio/x-ms-wma" },
            { ".wmv", "audio/x-ms-wmv" },
            { ".wps", "application/vnd.ms-works" }, { ".xml", "text/xml" },
            { ".xml", "text/plain" }, { ".z", "application/x-compress" },
            { ".zip", "application/zip" }, { "", "*/*" } };
    
    /**
     * 打开文件
     * 
     * @param file
     */
    public void openFile(Context context, File file)
    {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // 设置intent的Action属性
        intent.setAction(Intent.ACTION_VIEW);
        // 获取文件file的MIME类型
        String type = getMIMEType(file);
        // 设置intent的data和Type属性。
        intent.setDataAndType(Uri.fromFile(file), type);
        try
        {
            context.startActivity(intent);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(context, "您的手机不支持此格式，不能打开", Toast.LENGTH_SHORT)
                    .show();
        }
    }
    
    /**
     * 根据文件后缀名获得对应的MIME类型。
     * 
     * @param file
     */
    private String getMIMEType(File file)
    {
        String type = "*/*";
        String fName = file.getName();
        
        // 获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0)
        {
            return type;
        }
        
        /* 获取文件的后缀名 */
        String end = fName.substring(dotIndex, fName.length()).toLowerCase();
        if (end == "")
            return type;
        
        // 在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < MIME_MapTable.length; i++)
        {
            if (end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }
    
    public byte[] ImageToArray(File file)
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] result = null;
        FileInputStream in = null;
        try
        {
            in = new FileInputStream(file);
            byte[] buff = new byte[10240];
            int len = 0;
            while ((len = in.read(buff)) != -1)
            {
                bos.write(buff, 0, len);
                result = bos.toByteArray();
            }
            in.close();
            bos.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return result;
    }
    
    public File writeFromInput(String fullPathName, InputStream input)
    {
        File file = null;
        OutputStream output = null;
        try
        {
            file = new File(fullPathName);
            if (file.exists())
                return file;
            
            int length = 0;
            output = new FileOutputStream(file);
            byte buffer[] = new byte[4 * 1024];
            while ((length = input.read(buffer)) > 0)
            {
                output.write(buffer, 0, length);
            }
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                output.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        
        return file;
    }
 
    private static String TAG = "FileUtils";
    public static File getCacheFile(String imageUri){  
        File cacheFile = null;  
        try {  
            if (Environment.getExternalStorageState().equals(  
                    Environment.MEDIA_MOUNTED)) {  
                File sdCardDir = Environment.getExternalStorageDirectory();  
                String fileName = getFileName(imageUri);  
                File dir = new File(sdCardDir.getCanonicalPath()  
                        + "/Domtop");  
                if (!dir.exists()) {  
                    dir.mkdirs();  
                }  
                cacheFile = new File(dir, fileName);  
                Log.i(TAG, "exists:" + cacheFile.exists() + ",dir:" + dir + ",file:" + fileName);  
            }    
        } catch (IOException e) {  
            e.printStackTrace();  
            Log.e(TAG, "getCacheFileError:" + e.getMessage());  
        }  
          
        return cacheFile;  
    }
    
    public static String getFileName(String path) {  
        int index = path.lastIndexOf("/");  
        return path.substring(index + 1);  
    }  
    
  //从byte[]转file
    public static File getFileFromBytes(byte[] b, String outputFile) {
        BufferedOutputStream stream = null;
         File file = null;
         try {
        file = new File(outputFile);
             FileOutputStream fstream = new FileOutputStream(file);
             stream = new BufferedOutputStream(fstream);
             stream.write(b);
         } catch (Exception e) {
             e.printStackTrace();
        } finally {
            if (stream != null) {
                 try {
                    stream.close();
                 } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
         return file;
     }
    
    public static byte[] compressBitmap(Bitmap bitmap,float size){
        if(bitmap==null||getSizeOfBitmap(bitmap)<=size){
                return null;//如果图片本身的大小已经小于这个大小了，就没必要进行压缩
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//如果签名是png的话，则不管quality是多少，都不会进行质量的压缩
        int quality=100;
          while (baos.toByteArray().length / 1024f>size) {
                   quality=quality-4;// 每次都减少4
                   baos.reset();// 重置baos即清空baos
                  if(quality<=0){
                                break;
                 }
               bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
              Log.e("FileUtils","------质量--------"+baos.toByteArray().length/1024f);    
     }
     return baos.toByteArray();
    }
    
    public static long getSizeOfBitmap(Bitmap bitmap){
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//这里100的话表示不压缩质量
        long length=baos.toByteArray().length/1024;//读出图片的kb大小
        return length;
    }
}
