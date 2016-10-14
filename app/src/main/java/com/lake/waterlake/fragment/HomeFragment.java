package com.lake.waterlake.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import com.lake.waterlake.ApplicationGlobal;
import com.lake.waterlake.R;
import com.lake.waterlake.business.BluealgaeActivity;
import com.lake.waterlake.business.DrinksafeActivity;
import com.lake.waterlake.business.RiverqualityActivity;
import com.lake.waterlake.business.SatelliteActivity;
import com.lake.waterlake.business.TransferWaterActivity;
import com.lake.waterlake.business.WeatherActivity;
import com.lake.waterlake.business.dayReport.WCBBluealgaeDayReport;
import com.lake.waterlake.business.dayReport.WCBMonitorDayReport;
import com.lake.waterlake.home.*;
import com.lake.waterlake.model.TwoParams;
import com.lake.waterlake.network.AsyncHttpPost;
import com.lake.waterlake.network.BaseRequest;
import com.lake.waterlake.network.DefaultThreadPool;
import com.lake.waterlake.network.RequestResultCallback;
import com.lake.waterlake.util.RequestParameter;
import com.lake.waterlake.util.WSFunction;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyh on 16/8/31.
 * 首页
 */
public class HomeFragment extends LazyFragment{

    View view;
    FrameListView flistView;
    FrameGridView fgridview;

    String[] mcs_valueUp   = new String[7];
    String[] mcs_valueDown = new String[7];
    String[] mcs_djTime    = new String[7]; //new String[]{"2016.3.2","2016.4.2","2016.4.2","2016.4.2","2016.4.2","2016.4.2"};

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.main_frame,container,false);

        return view;
    }

    /**
     * load taihu,shuiwen,andan,zongdan,zongling,zaomidu,tianqi
     */
    public void  LoadMiddleData(){

        count = 0;

        // load_taihu_avg_level 水位
        LoadTHAvgLevelDataPart();
        // load_avg_water_temper 平均水温
        LoadNQAvgWaterTemperDataPart();
        // load huanbao NH3,TN,TP... 各项参数
        LoadHBDataPart();
        // load weather
        LoadWeatherDataPart();

        }

    public void showViewData(List<List<TwoParams>> obj){
        flistView = (FrameListView) view.findViewById(R.id.frameListView);
        flistView.setAdapter(new FrameListViewAdapter(HomeFragment.this.getContext(), mcs_valueUp, mcs_valueDown,mcs_djTime));
    }

    // load_avg_water_temper 平均水温 南泉气象站记录沙渚地区的数据，记录天气情况，平均水温
    public void   LoadNQAvgWaterTemperDataPart(){
        List<RequestParameter> parameters =    parameters =
                WSFunction.getParameters(ApplicationGlobal.WSSessionId, "waterLake.facetemp", null, null);
        AsyncHttpPost  httpget = new AsyncHttpPost(ApplicationGlobal.URL_read, parameters,
                new RequestResultCallback() {
                    @Override
                    public void onSuccess(String str) {
                        try {
                            JSONArray jarray = new JSONArray(str);
                            Float avgtemp =0f;
                            for (int i=0;i<jarray.length();i++){
                                JSONObject jsonObj = (JSONObject)jarray.get(i); // 南泉 4项温度求平均
                                avgtemp = (Float.valueOf(jsonObj.getString("ProCol_57")) +
                                                Float.valueOf(jsonObj.getString("ProCol_58"))
                                        + Float.valueOf(jsonObj.getString("ProCol_59"))+
                                                Float.valueOf(jsonObj.getString("ProCol_60")))/4;
                                  mcs_djTime[1] = jsonObj.getString("upDateTime");

                                mcs_valueUp[6]= jsonObj.getString("ProCol_31");
                                mcs_valueDown[6]= jsonObj.getString("ProCol_35")+"~"+jsonObj.getString("ProCol_34");

                            }
                            DecimalFormat decimalFormat=new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.

                            mcs_valueUp[1]= decimalFormat.format(avgtemp);
                            mcs_valueDown[1]= "";

                            // handler send data
                            Message msg =  new Message();
                            msg.what=112;
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
    // load weather
    public void LoadWeatherDataPart(){


    }

    // load taihu avg level
    public void  LoadTHAvgLevelDataPart(){
        List<RequestParameter> parameters =
                WSFunction.getParameters(ApplicationGlobal.WSSessionId, "waterLake.avgtaihu", null, null);
        AsyncHttpPost httpget = new AsyncHttpPost(ApplicationGlobal.URL_read, parameters,
                new RequestResultCallback() {
                    @Override
                    public void onSuccess(String str) {
                        try {
                            JSONArray jarray = new JSONArray(str);
                            String taihu_AvgLevel="0";
                            for (int i=0;i<jarray.length();i++){
                                JSONObject jsonObj = (JSONObject)jarray.get(i);
                                taihu_AvgLevel =    jsonObj.getString("ProCol_39");
                                mcs_djTime[0] = jsonObj.getString("upDateTime");
                            }
                            mcs_valueUp[0]= taihu_AvgLevel;
                            mcs_valueDown[0]= "";
                            // handler send data
                            Message msg =  new Message();
                            msg.what=111;
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


    // load huanbao NH3,TN,TP...
    public void  LoadHBDataPart(){
        List<RequestParameter> parameters =
                WSFunction.getParameters(ApplicationGlobal.WSSessionId, "waterLake.drinksafe", null, null);
        AsyncHttpPost httpget = new AsyncHttpPost(ApplicationGlobal.URL_read, parameters,
                new RequestResultCallback() {
                    @Override
                    public void onSuccess(String str) {
                        try {
                            JSONArray jarray = new JSONArray(str);
                            List<List<TwoParams>> allList =  new ArrayList<List<TwoParams>>();
                            List<TwoParams> pList = null;
                            String paramTime ="";
                            for (int i=0;i<jarray.length();i++){
                                JSONObject jsonObj = (JSONObject)jarray.get(i);
                                pList = new ArrayList<TwoParams>();
                                pList.add(new TwoParams(getResources().getString(R.string.NH3), jsonObj.getString("ProCol_4")));//氨氮
                                pList.add(new TwoParams(getResources().getString(R.string.TN), jsonObj.getString("ProCol_5")));//总氮
                                pList.add(new TwoParams(getResources().getString(R.string.TP), jsonObj.getString("ProCol_6")));//总磷
                                pList.add(new TwoParams(getResources().getString(R.string.algae), jsonObj.getString("ProCol_9")));//藻密度
                                allList.add(pList);
                                paramTime = jsonObj.getString("upDateTime");
                            }
                            //pageUp data
                            List<TwoParams> szList = allList.get(0);
                            List<TwoParams> xdList = allList.get(1);
                            for (int j=0;j<szList.size();j++){
                                mcs_valueUp[j+2]= szList.get(j).getObj2();
                                mcs_valueDown[j+2]= xdList.get(j).getObj2();
                                mcs_djTime[j+2] = paramTime;
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

    public int count=0;
    /**
     * 异步回调,更新页面数据
     */
    Handler mHandler = new Handler(){
        public void handleMessage(Message msg){
            count++;
          if (count>=3){// 根据回调数量确定数值
              showViewData((List<List<TwoParams>>)msg.obj);
              count=0;
            }

        };
    };

    @Override
    protected void initData() {
        System.out.print("-----HomeFragment-----");
       // flistView = (FrameListView)view.findViewById(R.id.frameListView);
       // flistView.setAdapter(new FrameListViewAdapter(HomeFragment.this.getContext(), mcs_value, mcs_time));
        fgridview=(FrameGridView)view.findViewById(R.id.framegridView);
        fgridview.setAdapter(new FrameGridAdapter(HomeFragment.this.getContext()));
        fgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (FrameGridAdapter.imgs[position]) {
                    case R.drawable.ico1_2x://DrinksafeActivity
                        startActivity(new Intent(getActivity(), DrinksafeActivity.class));//启动另一个Activity
                        break;
                    case R.drawable.ico2_2x://
                        startActivity(new Intent(getActivity(), BluealgaeActivity.class));//启动另一个Activity
                        break;
                    case R.drawable.ico3_2x://
                        startActivity(new Intent(getActivity(), WeatherActivity.class));//启动另一个Activity
                        break;
                    case R.drawable.ico4_2x://
                        startActivity(new Intent(getActivity(), TransferWaterActivity.class));//启动另一个Activity
                        break;
                    case R.drawable.ico5_2x://
                        startActivity(new Intent(getActivity(), SatelliteActivity.class));
                        break;
                    case R.drawable.ico6_2x://
                        startActivity(new Intent(getActivity(), RiverqualityActivity.class));//启动另一个Activity
                        break;
                    default:
                        break;
                }
            }
        });

        LoadMiddleData();// load middle content
    }

}
