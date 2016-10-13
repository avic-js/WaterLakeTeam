package com.lake.waterlake.business.dayReport;

import android.app.Activity;
import android.content.Intent;
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
import com.lake.waterlake.customAdapter.ThreeParamsAdapter;
import com.lake.waterlake.model.FourParams;
import com.lake.waterlake.model.ThreeParams;
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
 * Created by yyh on 16/9/24.
 * 水利局监测日报
 *
 */
public class WCBMonitorDayReport extends Activity {


    TextView transfer_time; //transfer监测时间
    ListView transfer_listView;//transfer列表
    TextView level_time; //level 水位水文监测时间
    ListView level_listView;//level 水位水文列表

    TextView title_text;//抬头标题
    Button back_Btn;//back
    private String searchdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wcb_monitor_dayreport);
        title_text =(TextView)findViewById(R.id.title_center_text);
        title_text.setText("水利局监测日报");
        back_Btn = (Button)findViewById(R.id.back_btn);
        back_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        transfer_time =  (TextView)findViewById(R.id.transfer_time); // 调水引流
        transfer_listView = (ListView)findViewById(R.id.transfer_listView);

        level_time  =  (TextView)findViewById(R.id.transfer_time2);  //水位水文
        level_listView = (ListView)findViewById(R.id.transfer_listView2); //水位水文
        Intent intent=getIntent();
        String reportid=intent.getStringExtra("reportid");
        initData(reportid);
    }

    public  void  showViewData(List<FourParams> obj,List<ThreeParams> obj1){

        FourParamsAdapter fourAdapter = new FourParamsAdapter(this,R.layout.fourparams_view,obj);
        transfer_listView.setAdapter(fourAdapter);

        ThreeParamsAdapter threeParamsAdapter =  new ThreeParamsAdapter(this,R.layout.threeparams_view,obj1);
        level_listView.setAdapter(threeParamsAdapter);
//        transfer_time.setText(searchdate);
        level_time.setText(searchdate);
    }

    /**
     * 调用数据
     */
    public  void initData(String reportid) {
        count=0;
        LoadTransferData(reportid);// load transfer
        LevelWater(reportid); //load level_water
    }

    // load transfer
    public void  LoadTransferData(String reportid){
        String[] params=new String[1];
        String[] Conditions=new String[1];
        params[0]="reportId";
        Conditions[0]=reportid;
        List<RequestParameter>    parameters =
                WSFunction.getParameters(ApplicationGlobal.WSSessionId, "waterLake.wcbtransfer", params, Conditions);
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
                                if (i==0){
                                    searchdate=jsonObj.getString("upDateTime");
                                }
                                String obj    = jsonObj.getString("PointName");
                                String obj1 =  jsonObj.getString("ProCol_40");
                                String obj2 =  jsonObj.getString("ProCol_41");
                                String obj3 =  jsonObj.getString("ProCol_42");
                                pList.add(new FourParams(obj,obj1,obj2,obj3));
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


    //load level_water
    public void LevelWater(String reportid){
        String[] params=new String[1];
        String[] Conditions=new String[1];
        params[0]="reportId";
        Conditions[0]=reportid;
        List<RequestParameter> parameters =
                WSFunction.getParameters(ApplicationGlobal.WSSessionId, "waterLake.wcblevel", params, Conditions);
        AsyncHttpPost httpget = new AsyncHttpPost(ApplicationGlobal.URL_read, parameters,
                new RequestResultCallback() {
                    @Override
                    public void onSuccess(String str) {
                        try {
                            JSONArray jarray = new JSONArray(str);
                            List<ThreeParams> pList = new ArrayList<ThreeParams>();
                            pList.add(new ThreeParams("监测点位","降雨量","平均水位(m)"));
                            for (int i=0;i<jarray.length();i++){
                                JSONObject jsonObj = (JSONObject)jarray.get(i);
                                if (i==0){
                                    searchdate=jsonObj.getString("upDateTime");
                                }
                                pList.add(new ThreeParams(jsonObj.getString("PointName"),
                                        jsonObj.getString("ProCol_49"),jsonObj.getString("ProCol_39")));
                            }
                            // handler send data
                            Message msg =  new Message();
                            msg.what=112;
                            msg.obj =pList;
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


    public int count=0;
    List<FourParams> fourlist=null;
    List<ThreeParams> threelist=null;

    /**
     * 异步回调,更新页面数据
     */
    Handler mHandler = new Handler(){
        public void handleMessage(Message msg){
             switch (msg.what){
                case 111:
                    fourlist = (List<FourParams>)msg.obj;
                    count++;
                    break;
                case 112:
                    threelist = (List<ThreeParams>)msg.obj;
                    count++;
                    break;
            }

            if (count>=2){// 根据回调数量确定数值
                showViewData(fourlist,threelist);
                count=0;
            }

        };
    };
}
