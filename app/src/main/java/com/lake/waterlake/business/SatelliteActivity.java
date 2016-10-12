package com.lake.waterlake.business;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lake.waterlake.ApplicationGlobal;
import com.lake.waterlake.R;

import com.lake.waterlake.model.FourParams;
import com.lake.waterlake.model.TwoParams;
import com.lake.waterlake.network.AsyncHttpPost;
import com.lake.waterlake.network.BaseRequest;
import com.lake.waterlake.network.DefaultThreadPool;
import com.lake.waterlake.network.RequestResultCallback;
import com.lake.waterlake.util.LoadImg;
import com.lake.waterlake.util.RequestParameter;
import com.lake.waterlake.util.WSFunction;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyh on 16/9/12.
 * 卫星遥感
 */
public class SatelliteActivity extends Activity {

    TextView satelite_time; //监测时间
    TextView title_text;//抬头标题
    Button back_Btn;//back
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "drink safe", Toast.LENGTH_SHORT);
        setContentView(R.layout.satellite_view);
        title_text =(TextView)findViewById(R.id.title_center_text);
        title_text.setText(R.string.satellite);
        back_Btn = (Button)findViewById(R.id.back_btn);
        back_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        satelite_time =  (TextView)findViewById(R.id.satelite_time);
        imageView = (ImageView)findViewById(R.id.imageViewDraw);
        initData();
    }

    public  void showViewData(List<TwoParams> matID){
        String url= ApplicationGlobal.ImageUrl +matID.get(0).getObj1();
        LoadImg loadImg  = new LoadImg(this);
        try {
            loadImg.loadImage(imageView, url, new LoadImg.ImageDownloadCallBack() {
                @Override
                public void onImageDownload(ImageView imageView, Bitmap bitmap) {
                    imageView.setImageBitmap(bitmap);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        satelite_time.setText(matID.get(0).getObj2());
    }


    /**
     * 调用数据
     */
    public  void initData() {
        List<RequestParameter>    parameters =
                WSFunction.getParameters(ApplicationGlobal.WSSessionId, "waterLake.satelite", null, null);
        AsyncHttpPost httpget = new AsyncHttpPost(ApplicationGlobal.URL_read, parameters,
                new RequestResultCallback() {
                    @Override
                    public void onSuccess(String str) {
                        try {
                            JSONArray jarray = new JSONArray(str);
                            List<TwoParams> pList = new ArrayList<TwoParams>();
                            String MatID ="";
                            for (int i=0;i<jarray.length();i++){
                                JSONObject jsonObj = (JSONObject)jarray.get(i);
//                                MatID    = jsonObj.getString("MatID");
                                pList.add(new TwoParams(jsonObj.getString("_MatUrl"),
                                        jsonObj.getString("upDateTime")));
                            }
                            // handler send data
                            Message msg =  new Message();
                            msg.what=111;
                            msg.obj = pList;
                            mHandler.sendMessage(msg);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFail(Exception e) {
                        e.printStackTrace();
                    }
                });
        DefaultThreadPool.getInstance().execute(httpget);
        BaseRequest.getBaseRequests().add(httpget);
    }

    /**
     * 异步回调,更新页面数据
     */
    Handler mHandler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 111:
                    showViewData((List<TwoParams>)msg.obj);
                    break;
            }
        };
    };


}
