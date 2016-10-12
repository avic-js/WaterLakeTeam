package com.lake.waterlake.business;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lake.waterlake.ApplicationGlobal;
import com.lake.waterlake.R;
import com.lake.waterlake.customAdapter.FourParamsAdapter;
import com.lake.waterlake.customAdapter.PersonAdapter;
import com.lake.waterlake.model.FourParams;
import com.lake.waterlake.model.TwoParams;
import com.lake.waterlake.network.AsyncHttpPost;
import com.lake.waterlake.network.BaseRequest;
import com.lake.waterlake.network.DefaultThreadPool;
import com.lake.waterlake.network.RequestResultCallback;
import com.lake.waterlake.util.RequestParameter;
import com.lake.waterlake.util.WSFunction;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyh on 16/9/19.
 * 调水引流
 */
public class TransferWaterActivity extends Activity {

    TextView transfer_time; //transfer监测时间
    String temp_transfer_time;

    ListView transfer_listView;//transfer列表

    TextView title_text;//抬头标题
    Button back_Btn;//back

    public List<TwoParams> personList;
    public  List<TwoParams> personList2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "drink safe", Toast.LENGTH_SHORT);
        setContentView(R.layout.transferwater_view);
        title_text =(TextView)findViewById(R.id.title_center_text);
        title_text.setText(R.string.transferWater);
        back_Btn = (Button)findViewById(R.id.back_btn);
        back_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        transfer_time =  (TextView)findViewById(R.id.transfer_time);

        transfer_listView = (ListView)findViewById(R.id.transfer_listView);

        initData();
    }

    public  void  showViewData(List<FourParams> obj){

        FourParamsAdapter  fourAdapter = new FourParamsAdapter(this,R.layout.fourparams_view,obj);
        transfer_listView.setAdapter(fourAdapter);
        transfer_time.setText(temp_transfer_time);

    }

    /**
     * 调用数据
     */
    public  void initData() {
        List<RequestParameter>    parameters =
                WSFunction.getParameters(ApplicationGlobal.WSSessionId, "waterLake.transferWater", null, null);
        AsyncHttpPost httpget = new AsyncHttpPost(ApplicationGlobal.URL_read, parameters,
                new RequestResultCallback() {
                    @Override
                    public void onSuccess(String str) {
                        try {
                            JSONArray jarray = new JSONArray(str);
                            List<FourParams> pList = new ArrayList<FourParams>();
                            pList.add(new FourParams("站点","平均流量","当日水量","当年水量"));
                            for (int i=0;i<jarray.length();i++){
                                JSONObject jsonObj = (JSONObject)jarray.get(i);
                                String obj    = jsonObj.getString("PointName");
                                String obj1 =  jsonObj.getString("ProCol_40");
                                String obj2 =  jsonObj.getString("ProCol_41");
                                String obj3 =  jsonObj.getString("ProCol_42");
                                pList.add(new FourParams(obj,obj1,obj2,obj3));
                                if (i==(jarray.length()-1)){
                                    temp_transfer_time = jsonObj.getString("upDateTime");
                                }
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
                    showViewData((List<FourParams>)msg.obj);
                    break;
                case  112:

                    break;
            }

        };
    };

}