package com.lake.waterlake.network;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.lake.waterlake.exception.RequestException;
import com.lake.waterlake.util.RequestParameter;


import android.util.Log;


/**
 * 异步的HttpGet请求
 *
 * @date 2016-10-10
 * http://blog.csdn.net/eddysong9280/article/details/9923391
 */
public class AsyncHttpGet extends BaseRequest {
    private static final long serialVersionUID = 2L;
    private HttpClient httpClient;
    private List<RequestParameter> parameters;

    public AsyncHttpGet(String url, List<RequestParameter> parameters,
                        RequestResultCallback requestCallback) {
//		this.url = ApplicationGlobal.ServletUrl + url;
        this.url = url;
        this.parameters = parameters;
        this.requestCallback = requestCallback;
        if (httpClient == null) {
            httpClient = new DefaultHttpClient();
            //设置网络超时
            HttpParams params = httpClient.getParams();
            HttpConnectionParams.setConnectionTimeout(params, 2000);
            HttpConnectionParams.setSoTimeout(params, 20000);
        }
    }

    @Override
    public void run() {
        try {
            formatUrl();
            Log.d(AsyncHttpGet.class.getName(),
                    "AsyncHttpGet  request to url :" + url);
            request = new HttpGet(url);
            HttpResponse response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                ByteArrayOutputStream content = new ByteArrayOutputStream();
                response.getEntity().writeTo(content);
                String str = new String(content.toByteArray()).trim();
                content.close();
                if (AsyncHttpGet.this.requestCallback != null) {
                    AsyncHttpGet.this.requestCallback.onSuccess(str);
                    return;
                }
            } else {
//                AsyncHttpGet.this.requestCallback.onSuccess("");
                RequestException exception = new RequestException(
                        RequestException.IO_EXCEPTION, "数据读取异常");
                AsyncHttpGet.this.requestCallback.onFail(exception);
            }
            Log.d(AsyncHttpGet.class.getName(),
                    "AsyncHttpGet  request to url :" + url + "  finished !");
        } catch (Exception e) {
            RequestException exception = new RequestException(
                    RequestException.IO_EXCEPTION, "无法连接至服务端");
            AsyncHttpGet.this.requestCallback.onFail(exception);
        }
//		catch (java.lang.IllegalArgumentException e) {
//			RequestException exception = new RequestException(
//			        RequestException.IO_EXCEPTION, "连接错误");
//			AsyncHttpGet.this.requestCallback.onFail(exception);
//			Log.d(AsyncHttpGet.class.getName(),
//			        "AsyncHttpGet  request to url :" + url + "  onFail  "
//			                + e.getMessage());
//		} catch (ConnectTimeoutException e) {
//			RequestException exception = new RequestException(
//			        RequestException.SOCKET_TIMEOUT_EXCEPTION, "连接服务器超时");
//			AsyncHttpGet.this.requestCallback.onFail(exception);
//			Log.d(AsyncHttpGet.class.getName(),
//			        "AsyncHttpGet  request to url :" + url + "  onFail  "
//			                + e.getMessage());
//		} catch (SocketTimeoutException e) {
//			RequestException exception = new RequestException(
//			        RequestException.SOCKET_TIMEOUT_EXCEPTION, "读取数据超时");
//			AsyncHttpGet.this.requestCallback.onFail(exception);
//			Log.d(AsyncHttpGet.class.getName(),
//			        "AsyncHttpGet  request to url :" + url + "  onFail  "
//			                + e.getMessage());
//		} catch (UnsupportedEncodingException e) {
//			RequestException exception = new RequestException(
//			        RequestException.UNSUPPORTED_ENCODEING_EXCEPTION, "编码错误");
//			AsyncHttpGet.this.requestCallback.onFail(exception);
//			Log.d(AsyncHttpGet.class.getName(),
//			        "AsyncHttpGet  request to url :" + url
//			                + "  UnsupportedEncodingException  "
//			                + e.getMessage());
//		} catch (org.apache.http.conn.HttpHostConnectException e) {
//			RequestException exception = new RequestException(
//			        RequestException.CONNECT_EXCEPTION, "连接错误");
//			AsyncHttpGet.this.requestCallback.onFail(exception);
//			Log.d(AsyncHttpGet.class.getName(),
//			        "AsyncHttpGet  request to url :" + url
//			                + "  HttpHostConnectException  " + e.getMessage());
//		} catch (ClientProtocolException e) {
//			RequestException exception = new RequestException(
//			        RequestException.CLIENT_PROTOL_EXCEPTION, "服务端协议异常");
//			AsyncHttpGet.this.requestCallback.onFail(exception);
//			e.printStackTrace();
//			Log.d(AsyncHttpGet.class.getName(),
//			        "AsyncHttpGet  request to url :" + url
//			                + "  ClientProtocolException " + e.getMessage());
//		} catch (IOException e) {
//			RequestException exception = new RequestException(
//			        RequestException.IO_EXCEPTION, "数据读取异常");
//			AsyncHttpGet.this.requestCallback.onFail(exception);
//			e.printStackTrace();
//			Log.d(AsyncHttpGet.class.getName(),
//			        "AsyncHttpGet  request to url :" + url + "  IOException  "
//			                + e.getMessage());
//		}catch (Exception e) {
//			RequestException exception = new RequestException(
//			        RequestException.EXCEPTION, "异常");
//			AsyncHttpGet.this.requestCallback.onFail(exception);
//			e.printStackTrace();
//			Log.d(AsyncHttpGet.class.getName(),
//			        "AsyncHttpGet  request to url :" + url + "  Exception  "
//			                + e.getMessage());
//		}
        super.run();
    }

    public void formatUrl() {
        if (parameters != null && parameters.size() > 0) {
            StringBuilder bulider = new StringBuilder();
            for (RequestParameter p : parameters) {
                if (bulider.length() != 0) {
                    bulider.append("&");
                }
                bulider.append(p.getName());
                bulider.append("=");
                bulider.append(p.getValue());
            }
            url += "?" + bulider.toString();
            Log.i("^!!!", url);
            System.out.println("@@:" + url);
        }
    }
}
