package com.lake.waterlake.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.lake.waterlake.ApplicationGlobal;
import com.lake.waterlake.R;
import com.lake.waterlake.business.dayReport.EPAMonitorDayReport;
import com.lake.waterlake.business.dayReport.WCBBluealgaeDayReport;
import com.lake.waterlake.business.dayReport.WCBMonitorDayReport;
import com.lake.waterlake.business.dayReport.WeatherMonitorDayReport;
import com.lake.waterlake.customAdapter.TwoParamsAdapter;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by
 * 综合查询
 */
public class QueryFragment extends LazyFragment{

    View view;
    private Spinner spinnerDpt = null;
    private Spinner spinnerTime = null;
    private ArrayAdapter<CharSequence> adapterCity = null;
    private ListView reportView;// 报表的展示view
    SimpleAdapter adapter;
    List<Map<String,Object>> listData=new ArrayList<Map<String,Object>>();
    List<FourParams> allList =  new ArrayList<FourParams>();
    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.query_frame,container,false);

        return view;

    }

    @Override
    protected void initData() {
        //静态实现的下拉框，数据写在query_frame.xml文件中
        spinnerDpt =(Spinner)view.findViewById(R.id.spinnerDpt);
        spinnerDpt.setOnItemSelectedListener(new OnItemSelectedListenerImpl());
        //动态实现的下拉框，数据在程序中获得，实际项目可能来自数据库等
        spinnerTime = (Spinner)view.findViewById(R.id.spinnerTime);
//        spinnerTime.setAdapter(adapterCity);
        spinnerTime.setOnItemSelectedListener(new OnItemSelectedListenerImpl());
        reportView=(ListView)view.findViewById(R.id.query_listView);
    }

    private class OnItemSelectedListenerImpl implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            String time = spinnerTime.getSelectedItem().toString();
//          parent.getItemAtPosition(position).toString();
            String dept=spinnerDpt.getSelectedItem().toString();
            String day=TValueChange(time);
            String depid=DValueChange(dept);
            String[] params=new String[2];
            String[] Conditions=new String[2];
            params[0]="deptid";
            params[1]="day";
            Conditions[0]=depid;
            Conditions[1]=day;
            List<RequestParameter> parameters =
                    WSFunction.getParameters(ApplicationGlobal.WSSessionId, "waterLake.dptreport", params, Conditions);
            AsyncHttpPost httpget = new AsyncHttpPost(ApplicationGlobal.URL_read, parameters,
                    new RequestResultCallback() {
                        @Override
                        public void onSuccess(String str) {
                            try {
                                JSONArray jarray = new JSONArray(str);


                                for (int i=0;i<jarray.length();i++){
                                    FourParams pList =new FourParams() ;
                                    JSONObject jsonObj = (JSONObject)jarray.get(i);
                                    pList.setObj1(jsonObj.getString("Reportid"));
                                    pList.setObj2(jsonObj.getString("ReportName"));
                                    pList.setObj3(jsonObj.getString("ReportTime"));
                                    pList.setObj4(jsonObj.getString("ReportAssociateID"));
//                                    pList.add(new ThreeParams(jsonObj.getString("Reportid"), , ));//溶解氧
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



//            Toast.makeText(getActivity(), "下拉框选择是：" + time+"---"+dept,
//                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // TODO Auto-generated method stub
        }
    }


    public  void  showViewData(List<FourParams> obj){
        List<FourParams> allListtmp =  new ArrayList<FourParams>();
        allListtmp=obj;

        for(int i=0;i<allListtmp.size();i++){
            Map<String,Object> map=new HashMap<String,Object>();
            FourParams fourParams=new FourParams() ;
            fourParams=(FourParams)allListtmp.get(i);
            map.put("file_list_item1",fourParams.getObj1());
            map.put("file_list_item2",fourParams.getObj2());
            map.put("file_list_item3",fourParams.getObj3());
            listData.add(map);
        }
        adapter=new SimpleAdapter(QueryFragment.this.getActivity(),
                listData,
                R.layout.list_report_item_5,
                new String[]{"file_list_item1","file_list_item2","file_list_item3"},
                new int[]{R.id.list_reportid,R.id.list_reportname,R.id.list_reportime});
        reportView.setAdapter(adapter);
        reportView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view, int position, long id) {
                Bundle bundle = new Bundle();

                bundle.putString("reportid", allList.get(position).getObj1());
//                bundle.putString("address", DeviceTypeNBQTempList.get(position).getDeviceAddress());
//                bundle.putString("pos", DeviceTypeNBQTempList.get(position).getDevicePos());
//                bundle.putString("devicetype", DeviceTypeNBQTempList.get(position).getDeviceType());
//                bundle.putString("devicename", DeviceTypeNBQTempList.get(position).getDeviceName());

                Intent intent = new Intent();
                intent.putExtras(bundle);
                if(allList.get(position).getObj4().equals("1012")){//水利局蓝藻
                    intent.setClass(getActivity(), WCBBluealgaeDayReport.class);
                } else if(allList.get(position).getObj4().equals("1001")){//水利局
                    intent.setClass(getActivity(), WCBMonitorDayReport.class);
                } else if(allList.get(position).getObj4().equals("1003")){//气象局
                    intent.setClass(getActivity(), WeatherMonitorDayReport.class);
                }else if(allList.get(position).getObj4().equals("1005")){//环保局
                    intent.setClass(getActivity(), EPAMonitorDayReport.class);
                }

                startActivity(intent);

            }
        });


    }
    public String TValueChange(String time)
    {
        String timetmp="";
        if (time.equals("今日")){
            timetmp="1";
        }if (time.equals("本周")) {
        timetmp="7";
        }if (time.equals("近两月")) {
        timetmp="60";
        }
       return timetmp;

    }
    public String DValueChange(String dept)
    {
        String depttmp="";
        if (dept.equals("全部")){
            depttmp="1002,1003,1005";
        }if (dept.equals("气象局")) {
        depttmp="1003";
        }if (dept.equals("水利局")) {
        depttmp="1002";
        }if(dept.equals("环保局")){
        depttmp="1005";
    }
        return depttmp;

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

