package com.lake.waterlake.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lake.waterlake.R;

import com.lake.waterlake.home.*;


/**
 * Created by yyh on 16/8/31.
 */
public class ChatFragment extends LazyFragment{

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
        System.out.print("-----ChatFragment-----");
       // flistView = (FrameListView)view.findViewById(R.id.frameListView);
       // flistView.setAdapter(new FrameListViewAdapter(ChatFragment.this.getContext(), mcs_value, mcs_time));

        fgridview=(FrameGridView)view.findViewById(R.id.framegridView);
        fgridview.setAdapter(new FrameGridAdapter(ChatFragment.this.getContext()));

        String[] mcs_value ={"3.2","2.4","1.99","0.99","0.2","0.4"};;
        String[] mcs_time={"2016.3.2","2016.4.2","2016.4.2","2016.4.2","2016.4.2","2016.4.2"};

         flistView = (FrameListView) view.findViewById(R.id.frameListView);
         flistView.setAdapter(new FrameListViewAdapter(ChatFragment.this.getContext(),mcs_value,mcs_time));

    }

}
