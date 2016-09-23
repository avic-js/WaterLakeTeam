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
import com.lake.waterlake.customAdapter.PersonAdapter;
import com.lake.waterlake.customAdapter.TwoParamsAdapter;
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
 * Created by yyh on 16/9/12.
 * 河道水质
 */
public class RiverqualityActivity extends Activity {

    TextView jc_time1; //监测时间
    TextView jc_time2;//监测时间
    TextView jc_time3;//监测时间
    TextView jc_time4;//监测时间
    TextView jc_time5;//监测时间
    TextView jc_time6;//监测时间

    ListView body_listView1;//列表
    ListView body_listView2;//列表
    ListView body_listView3;//列表
    ListView body_listView4;//列表
    ListView body_listView5;//列表
    ListView body_listView6;//列表

    TextView title_text;//抬头标题
    Button back_Btn;//back


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "drink safe", Toast.LENGTH_SHORT);
        setContentView(R.layout.riverquality_view);
        title_text =(TextView)findViewById(R.id.title_center_text);
        title_text.setText(R.string.riverquality);
        back_Btn = (Button)findViewById(R.id.back_btn);
        back_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        jc_time1 =  (TextView)findViewById(R.id.jc_time1);
        jc_time2 =  (TextView)findViewById(R.id.jc_time2);
        jc_time3 =  (TextView)findViewById(R.id.jc_time3);
        jc_time4 =  (TextView)findViewById(R.id.jc_time4);
        jc_time5 =  (TextView)findViewById(R.id.jc_time5);
        jc_time6 =  (TextView)findViewById(R.id.jc_time6);

        body_listView1 = (ListView)findViewById(R.id.body_listView1);
        body_listView2 = (ListView)findViewById(R.id.body_listView2);
        body_listView3 = (ListView)findViewById(R.id.body_listView3);
        body_listView4 = (ListView)findViewById(R.id.body_listView4);
        body_listView5 = (ListView)findViewById(R.id.body_listView5);
        body_listView6 = (ListView)findViewById(R.id.body_listView6);
        initData();
    }

    public  void  showViewData(List<TwoParams> obj,List<TwoParams> obj1){

        TwoParamsAdapter twoParamsAdapter1 = new TwoParamsAdapter(this,R.layout.twoparams_view,obj);
        body_listView1.setAdapter(twoParamsAdapter1);

        TwoParamsAdapter twoParamsAdapter2 = new TwoParamsAdapter(this,R.layout.twoparams_view,obj1);
        body_listView2.setAdapter(twoParamsAdapter2);

        TwoParamsAdapter twoParamsAdapter3 = new TwoParamsAdapter(this,R.layout.twoparams_view,obj1);
        body_listView3.setAdapter(twoParamsAdapter3);

        TwoParamsAdapter twoParamsAdapter4 = new TwoParamsAdapter(this,R.layout.twoparams_view,obj1);
        body_listView4.setAdapter(twoParamsAdapter4);

        TwoParamsAdapter twoParamsAdapter5 = new TwoParamsAdapter(this,R.layout.twoparams_view,obj1);
        body_listView5.setAdapter(twoParamsAdapter5);

        TwoParamsAdapter twoParamsAdapter6 = new TwoParamsAdapter(this,R.layout.twoparams_view,obj1);
        body_listView6.setAdapter(twoParamsAdapter6);
    }

    /**
     * 调用数据
     */
    public  void initData() {
        List<RequestParameter>    parameters =
                WSFunction.getParameters(ApplicationGlobal.WSSessionId, "waterLake.riverquality", null, null);
        AsyncHttpPost httpget = new AsyncHttpPost(ApplicationGlobal.URL_read, parameters,
                new RequestResultCallback() {
                    @Override
                    public void onSuccess(String str) {
                        try {
                            JSONArray jarray = new JSONArray(str);
                            List<List<TwoParams>> allList =  new ArrayList<List<TwoParams>>();
                            List<TwoParams> pList=null;
                            for (int i=0;i<jarray.length();i++){
                                pList = new ArrayList<TwoParams>();
                                JSONObject jsonObj = (JSONObject)jarray.get(i);
                                pList.add(new TwoParams(getResources().getString(R.string.NH3), jsonObj.getString("ProCol_1")));//氨氮
                                pList.add(new TwoParams(getResources().getString(R.string.TN), jsonObj.getString("ProCol_2")));//总氮
                                pList.add(new TwoParams(getResources().getString(R.string.TP), jsonObj.getString("ProCol_3")));//总磷
                                pList.add(new TwoParams(getResources().getString(R.string.algae), jsonObj.getString("ProCol_4")));//藻密度
                                pList.add(new TwoParams(getResources().getString(R.string.NAWQA), jsonObj.getString("ProCol_5")));//水质评价
                                pList.add(new TwoParams(getResources().getString(R.string.PH), jsonObj.getString("ProCol_8")));//PH
                                pList.add(new TwoParams(getResources().getString(R.string.DO), jsonObj.getString("ProCol_9")));//溶解氧
                                allList.add(pList);
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
                    showViewData((List<TwoParams>)msg.obj,(List<TwoParams>)msg.obj);
                    break;
                case  112:

                    break;
            }

        };
    };

}
