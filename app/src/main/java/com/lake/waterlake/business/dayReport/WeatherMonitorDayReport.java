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
     * Created by yyh on 16/9/24.
     * 气象局监测日报
     *
     */
    public class WeatherMonitorDayReport extends Activity {

        TextView weather_time; //湖面监测时间
        TextView temper_time;//南拳站监测时间
        ListView weather_listView;//湖面
        ListView temper_listView;//南拳站
        TextView title_text;//抬头标题
        Button back_Btn;//back
        private String searchdate;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_dayreport);
        title_text =(TextView)findViewById(R.id.title_center_text);
        title_text.setText("气象监测数据日报");
        back_Btn = (Button)findViewById(R.id.back_btn);
        back_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        weather_time =  (TextView)findViewById(R.id.weather_time); // 南泉
        temper_time =(TextView)findViewById(R.id.temper_time);// 湖面
        weather_listView = (ListView)findViewById(R.id.weather_listView);//南泉
        temper_listView = (ListView)findViewById(R.id.temper_listView);// 湖面
            Intent intent=getIntent();
            String reportid=intent.getStringExtra("reportid");
            initData(reportid);
    }

    // show weather data
    public  void  showWeatherViewData(List<List<TwoParams>> obj){
        // 南泉
        TwoParamsAdapter nanquantwoAdapter =
                new TwoParamsAdapter(this,R.layout.twoparams_view,obj.get(0));
        temper_listView.setAdapter(nanquantwoAdapter);

        // 湖面
       TwoParamsAdapter laketwoAdapter =
            new TwoParamsAdapter(this,R.layout.twoparams_view,obj.get(1));
        weather_listView.setAdapter(laketwoAdapter);
        weather_time.setText(searchdate);
        temper_time.setText(searchdate);
}


    /**
     * 调用数据
     */
    public  void initData(String reportid) {
        // TODO: 16/9/24   pelease intent params  reportId
        String[] params=new String[1];
        String[] Conditions=new String[1];
        params[0]="reportId";
        Conditions[0]=reportid;
        // load weather_station
        List<RequestParameter>  parameters =
                WSFunction.getParameters(ApplicationGlobal.WSSessionId, "waterLake.weatherreport", params, Conditions);
        AsyncHttpPost   httpget = new AsyncHttpPost(ApplicationGlobal.URL_read, parameters,
                new RequestResultCallback() {
                    @Override
                    public void onSuccess(String str) {
                        try {
                            List<List<TwoParams>> allList = new ArrayList<List<TwoParams>>();
                            JSONArray jarray = new JSONArray(str);
                            List<TwoParams> pList = null;
                            for (int i=0;i<jarray.length();i++){
                                pList = new ArrayList<TwoParams>();
                                JSONObject jsonObj = (JSONObject)jarray.get(i);
                                if (i==0){
                                    searchdate=jsonObj.getString("upDateTime");
                                }

                                pList.add(new TwoParams("水表气温",jsonObj.getString("ProCol_57")));
                                pList.add(new TwoParams("0.5米水温",jsonObj.getString("ProCol_58")));
                                pList.add(new TwoParams("1米水温",jsonObj.getString("ProCol_59")));
                                pList.add(new TwoParams("水底水温",jsonObj.getString("ProCol_60")));
                                allList.add(pList);
                            }

                            // handler send data
                            Message msg =  new Message();
                            msg.what=111;
                            msg.obj = allList;
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
                    showWeatherViewData((List<List<TwoParams>>) msg.obj);
                    break;
            }

        };
    };

}
