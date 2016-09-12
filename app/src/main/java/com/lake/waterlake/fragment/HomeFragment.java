package com.lake.waterlake.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.lake.waterlake.R;

import com.lake.waterlake.business.CommonSecondListViewActivity;
import com.lake.waterlake.home.*;


/**
 * Created by yyh on 16/8/31.
 * 首页
 */
public class HomeFragment extends LazyFragment{

    View view;
    FrameListView flistView;
    FrameGridView fgridview;

    String[] mcs_value ={"3.2","2.4","1.99","0.99","0.2","0.4"};;
    String[] mcs_time={"2016.3.2","2016.4.2","2016.4.2","2016.4.2","2016.4.2","2016.4.2"};


    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.main_frame,container,false);



        return view;
    }

    @Override
    protected void initData() {
        System.out.print("-----HomeFragment-----");
       // flistView = (FrameListView)view.findViewById(R.id.frameListView);
       // flistView.setAdapter(new FrameListViewAdapter(HomeFragment.this.getContext(), mcs_value, mcs_time));

        fgridview=(FrameGridView)view.findViewById(R.id.framegridView);
        fgridview.setAdapter(new FrameGridAdapter(HomeFragment.this.getContext()));
        fgridview.setOnItemClickListener(new  AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            /**
             * R.drawable.app_aapay,R.drawable.app_aligame,
             R.drawable.app_appcenter,
             R.drawable.app_assign,R.drawable.app_plane,R.drawable.app_transfer
             */
                switch (FrameGridAdapter.imgs[position]){
                    case R.drawable.app_aapay:
                    startActivity(new Intent(getActivity(), CommonSecondListViewActivity.class));//启动另一个Activity
                   // finish();//结束此Activity，可回收
                    break;
                    case R.drawable.app_aligame:
                        startActivity(new Intent(getActivity(), CommonSecondListViewActivity.class));//启动另一个Activity
                        //finish();//结束此Activity，可回收
                        break;

                    default:
                        break;

                }

            }
        });


        String[] mcs_value ={"3.2","2.4","1.99","0.99","0.2","0.4"};;
        String[] mcs_time={"2016.3.2","2016.4.2","2016.4.2","2016.4.2","2016.4.2","2016.4.2"};

         flistView = (FrameListView) view.findViewById(R.id.frameListView);
         flistView.setAdapter(new FrameListViewAdapter(HomeFragment.this.getContext(),mcs_value,mcs_time));

    }



}
