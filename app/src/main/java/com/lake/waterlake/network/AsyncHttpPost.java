package com.lake.waterlake.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;

import com.lake.waterlake.exception.RequestException;
import com.lake.waterlake.util.RequestParameter;


import android.util.Log;


/**
 * 异步HttpPost请求 线程的终止工作交给线程池，当activity停止的时候，设置回调函数为false ，就不会执行回调方法�?
 *
 * @author
 * @date 2016-10-10
 */
public class AsyncHttpPost extends BaseRequest {
    private static final long serialVersionUID = 2L;
    DefaultHttpClient httpClient;
    List<RequestParameter> parameters = null;

    public AsyncHttpPost(String url, List<RequestParameter> parameters,
                         RequestResultCallback requestCallback) {
        this.url = url;
        this.parameters = parameters;
        this.requestCallback = requestCallback;
        if (httpClient == null)
            httpClient = new DefaultHttpClient();
    }

    @Override
    public void run() {
        try {
            String args = "";
            request = new HttpPost(url);
            Log.d(AsyncHttpPost.class.getName(), "AsyncHttpPost  request to url :" + url);
            request.getParams().setParameter(
                    CoreConnectionPNames.CONNECTION_TIMEOUT, connectTimeout);
            request.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
                    1000000);
            if (parameters != null && parameters.size() > 0) {
                List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
                for (RequestParameter parameter : parameters) {
                    list.add(new BasicNameValuePair(parameter.getName(),
                            parameter.getValue()));

                    args += "&" + parameter.getName() + "=" + parameter.getValue();
                }
                ((HttpPost) request).setEntity(new UrlEncodedFormEntity(list,
                        HTTP.UTF_8));
            }

            //-----打印请求路径---------------------------------------
            if (!args.equals("")) {
                Log.d(AsyncHttpPost.class.getName(), "AsyncHttpPost url->" + url + "?" + args.substring(1, args.length()));
                System.out.println(AsyncHttpPost.class.getName() + "-->AsyncHttpPost url->" + url + "?" + args.substring(1, args.length()));
            } else {
                Log.d(AsyncHttpPost.class.getName(), "AsyncHttpPost url->" + url);
                System.out.println(AsyncHttpPost.class.getName() + "-->AsyncHttpPost url->" + url);
            }
            //----------------------------------------------------

            HttpResponse response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                ByteArrayOutputStream content = new ByteArrayOutputStream();
                response.getEntity().writeTo(content);
                String ret = new String(content.toByteArray()).trim();
                content.close();
                if (AsyncHttpPost.this.requestCallback != null) {
                    AsyncHttpPost.this.requestCallback.onSuccess(ret);
                    return;
                }
            } else {
                RequestException exception = new RequestException(
                        RequestException.IO_EXCEPTION, "响应码异�?响应码："
                        + statusCode);
                AsyncHttpPost.this.requestCallback.onFail(exception);
            }
            Log.d(AsyncHttpPost.class.getName(),
                    "AsyncHttpPost  request to url :" + url + "  finished !");
        } catch (IllegalArgumentException e) {
            RequestException exception = new RequestException(
                    RequestException.IO_EXCEPTION, "连接错误");
            AsyncHttpPost.this.requestCallback.onFail(exception);
            Log.d(AsyncHttpGet.class.getName(),
                    "AsyncHttpPost  request to url :" + url + "  onFail  "
                            + e.getMessage());
        } catch (ConnectTimeoutException e) {
            RequestException exception = new RequestException(
                    RequestException.SOCKET_TIMEOUT_EXCEPTION, "连接超时");
            AsyncHttpPost.this.requestCallback.onFail(exception);
            Log.d(AsyncHttpPost.class.getName(),
                    "AsyncHttpPost  request to url :" + url + "  onFail  "
                            + e.getMessage());
        } catch (SocketTimeoutException e) {
            RequestException exception = new RequestException(
                    RequestException.SOCKET_TIMEOUT_EXCEPTION, "读取超时");
            AsyncHttpPost.this.requestCallback.onFail(exception);
            Log.d(AsyncHttpPost.class.getName(),
                    "AsyncHttpPost  request to url :" + url + "  onFail  "
                            + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            RequestException exception = new RequestException(
                    RequestException.UNSUPPORTED_ENCODEING_EXCEPTION, "编码错误");
            AsyncHttpPost.this.requestCallback.onFail(exception);
            Log.d(AsyncHttpPost.class.getName(),
                    "AsyncHttpPost  request to url :" + url
                            + "  UnsupportedEncodingException  "
                            + e.getMessage());
        } catch (HttpHostConnectException e) {

            RequestException exception = new RequestException(
                    RequestException.CONNECT_EXCEPTION, "连接错误");
            AsyncHttpPost.this.requestCallback.onFail(exception);
            Log.d(AsyncHttpPost.class.getName(),
                    "AsyncHttpPost  request to url :" + url
                            + "  HttpHostConnectException  " + e.getMessage());
        } catch (ClientProtocolException e) {
            RequestException exception = new RequestException(
                    RequestException.CLIENT_PROTOL_EXCEPTION, "客户端协议异常");
            AsyncHttpPost.this.requestCallback.onFail(exception);
            e.printStackTrace();
            Log.d(AsyncHttpPost.class.getName(),
                    "AsyncHttpPost  request to url :" + url
                            + "  ClientProtocolException " + e.getMessage());
        } catch (IOException e) {
            RequestException exception = new RequestException(
                    RequestException.IO_EXCEPTION, "数据读取异常");
            AsyncHttpPost.this.requestCallback.onFail(exception);
            e.printStackTrace();
            Log.d(AsyncHttpPost.class.getName(),
                    "AsyncHttpPost  request to url :" + url + "  IOException  "
                            + e.getMessage());
        } finally {
            // request.
        }
        super.run();
    }
}