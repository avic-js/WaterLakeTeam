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
import com.lake.waterlake.customAdapter.SixParamsAdapter;
import com.lake.waterlake.customAdapter.TwoParamsAdapter;
import com.lake.waterlake.model.SixParams;
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
 * 气象水文
 */
public class WeatherActivity extends Activity {

    TextView weather_time; //气象局监测时间
    TextView temper_time;//南拳站监测时间
    ListView weather_listView;//气象局
    ListView temper_listView;//水表气温
    TextView title_text;//抬头标题
    Button back_Btn;//back


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "drink safe", Toast.LENGTH_SHORT);
        setContentView(R.layout.weather);
        title_text =(TextView)findViewById(R.id.title_center_text);
        title_text.setText(R.string.weather);
        back_Btn = (Button)findViewById(R.id.back_btn);
        back_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        weather_time =  (TextView)findViewById(R.id.weather_time);
        temper_time =(TextView)findViewById(R.id.temper_time);
        weather_listView = (ListView)findViewById(R.id.weather_listView);
        temper_listView = (ListView)findViewById(R.id.temper_listView);


        initData();
    }

    public  void  showViewData(List<SixParams> obj,List<TwoParams> obj1){

        SixParamsAdapter sixAdapter = new SixParamsAdapter(this,R.layout.sixparams_view,obj);
        weather_listView.setAdapter(sixAdapter);

//       TwoParamsAdapter twoAdapter = new TwoParamsAdapter(this,R.layout.twoparams_view,obj1);
//        temper_listView.setAdapter(twoAdapter);
    }

    /**
     * 调用数据
     */
    public  void initData() {
        List<RequestParameter>    parameters =
                WSFunction.getParameters(ApplicationGlobal.WSSessionId, "waterLake.weekweather", null, null);
        AsyncHttpPost httpget = new AsyncHttpPost(ApplicationGlobal.URL_read, parameters,
                new RequestResultCallback() {
                    @Override
                    public void onSuccess(String str) {
                        try {
                            JSONArray jarray = new JSONArray(str);
                            List<SixParams> pList = new ArrayList<SixParams>();
                            pList.add(new SixParams("日期","天气","风向","风力","低温","高温"));
                            for (int i=0;i<jarray.length();i++){

                                JSONObject jsonObj = (JSONObject)jarray.get(i);
                                String obj    = jsonObj.getString("ProCol_51");
                                String obj1    = jsonObj.getString("ProCol_31");
                                String obj2    = jsonObj.getString("ProCol_32");
                                String obj3    = jsonObj.getString("ProCol_33");
                                String obj4    = jsonObj.getString("ProCol_35");
                                String obj5    = jsonObj.getString("ProCol_34");
                                pList.add(new SixParams(obj,obj1,obj2,obj3,obj4,obj5));
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
                    showViewData((List<SixParams>)msg.obj,(List<TwoParams>)msg.obj);
                    break;
                case  112:

                    break;
            }

        };
    };

}
