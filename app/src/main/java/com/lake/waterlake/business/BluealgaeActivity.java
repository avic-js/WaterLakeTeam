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
import com.lake.waterlake.customAdapter.ThreeParamsAdapter;
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
 * Created by yyh on 16/9/12.
 * 蓝藻胡滥
 */
public class BluealgaeActivity extends Activity {

    TextView bluealgae_time; //监测时间

    ListView bluealgae_listView;//

    TextView title_text;//抬头标题
    Button back_Btn;//back


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluealgae_view);// initView
        title_text =(TextView)findViewById(R.id.title_center_text);
        title_text.setText(R.string.bluealgae);// set title name
        back_Btn = (Button)findViewById(R.id.back_btn);
        back_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });// set btn click event

        bluealgae_time =  (TextView)findViewById(R.id.bluealgae_time);// set time

        bluealgae_listView = (ListView)findViewById(R.id.bluealgae_listView);//set listview

        initData();
    }

    /**
     * set body content
     * @param obj
     */
    public  void  showViewData(List<ThreeParams> obj){

        ThreeParamsAdapter threeAdapter = new ThreeParamsAdapter(this,R.layout.threeparams_view,obj);
        bluealgae_listView.setAdapter(threeAdapter);

    }

    /**
     * call business data
     */
    public  void initData() {
        List<RequestParameter>    parameters =
                WSFunction.getParameters(ApplicationGlobal.WSSessionId, "waterLake.bluealgae", null, null);
        AsyncHttpPost httpget = new AsyncHttpPost(ApplicationGlobal.URL_read, parameters,
                new RequestResultCallback() {
                    @Override
                    public void onSuccess(String str) {
                        try {
                            JSONArray jarray = new JSONArray(str);
                            List<ThreeParams> pList = new ArrayList<ThreeParams>();
                            pList.add(new ThreeParams("类别","当日情况","累计情况"));
                            for (int i=0;i<jarray.length();i++){
                                JSONObject jsonObj = (JSONObject)jarray.get(i);
                                String obj1  = jsonObj.getString("ProCol_43");
                                String obj2 = jsonObj.getString("ProCol_44");
                                String obj = jsonObj.getString("PointName");
                                pList.add(new ThreeParams(obj,obj1,obj2));
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
     * async call, update page data
     *
     */
    Handler mHandler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 111:
                    showViewData((List<ThreeParams>)msg.obj);
                    break;
                case  112:

                    break;
            }

        };
    };

}
