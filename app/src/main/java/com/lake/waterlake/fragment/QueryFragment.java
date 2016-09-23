package com.lake.waterlake.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.lake.waterlake.R;

/**
 * Created by
 * 综合查询
 */
public class QueryFragment extends LazyFragment{

    View view;
    private Spinner spinnerDpt = null;
    private Spinner spinnerTime = null;
    private ArrayAdapter<CharSequence> adapterCity = null;
    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        view =  inflater.inflate(R.layout.query_frame,container,false);
//        //静态实现的下拉框，数据写在query_frame.xml文件中
//        spinnerDpt =(Spinner)view.findViewById(R.id.spinnerDpt);
//        spinnerDpt.setOnItemSelectedListener(new OnItemSelectedListenerImpl());
//        //动态实现的下拉框，数据在程序中获得，实际项目可能来自数据库等
//        spinnerTime = (Spinner)view.findViewById(R.id.spinnerTime);
////        spinnerTime.setAdapter(adapterCity);
//        spinnerTime.setOnItemSelectedListener(new OnItemSelectedListenerImpl());
        return view;

    }

    @Override
    protected void initData() {

    }

    private class OnItemSelectedListenerImpl implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            String time = spinnerTime.getSelectedItem().toString();
//            parent.getItemAtPosition(position).toString();
             String dept=spinnerDpt.getSelectedItem().toString();
            Toast.makeText(getActivity(), "下拉框选择是：" + time+"---"+dept,
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // TODO Auto-generated method stub
        }
    }
}

