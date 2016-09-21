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

    TextView SZ_time; //沙渚监测时间
    TextView XD_time2;//锡东监测时间
    ListView SZ_listView;//沙渚列表
    ListView XD_listView;//锡东列表
    TextView title_text;//抬头标题
    Button back_Btn;//back

    public List<TwoParams> personList;
    public  List<TwoParams> personList2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "drink safe", Toast.LENGTH_SHORT);
        setContentView(R.layout.drinksafe_view);
        title_text =(TextView)findViewById(R.id.title_center_text);
        title_text.setText(R.string.transferWater);
        back_Btn = (Button)findViewById(R.id.back_btn);
        back_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SZ_time =  (TextView)findViewById(R.id.SZ_time);
        XD_time2 =(TextView)findViewById(R.id.XD_time2);
        SZ_listView = (ListView)findViewById(R.id.SZ_listView);
        XD_listView = (ListView)findViewById(R.id.XD_listView2);
        personList = new ArrayList<TwoParams>();
        personList2 = new ArrayList<TwoParams>();

        initData();
    }

    public  void  showViewData(List<TwoParams> obj,List<TwoParams> obj1){

        PersonAdapter perAdapter = new PersonAdapter(this,R.layout.my_listitem,obj);
        SZ_listView.setAdapter(perAdapter);

        PersonAdapter perAdapter2 = new PersonAdapter(this,R.layout.my_listitem,obj1);
        XD_listView.setAdapter(perAdapter);
    }

    /**
     * 调用数据
     */
    public  void initData() {
        List<RequestParameter>    parameters =
                WSFunction.getParameters(ApplicationGlobal.WSSessionId, "waterLake.test", null, null);
        AsyncHttpPost httpget = new AsyncHttpPost(ApplicationGlobal.URL_read, parameters,
                new RequestResultCallback() {
                    @Override
                    public void onSuccess(String str) {
                        try {
                            JSONArray jarray = new JSONArray(str);
                            List<TwoParams> pList = new ArrayList<TwoParams>();
                            for (int i=0;i<jarray.length();i++){
                                JSONObject jsonObj = (JSONObject)jarray.get(i);
                                String proName    = jsonObj.getString("ProName");
                                String rank =  jsonObj.getString("Rank");
                                pList.add(new TwoParams(proName, rank));
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