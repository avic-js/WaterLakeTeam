package com.lake.waterlake.home;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lake.waterlake.R;

/**
 * Created by yyh on 16/8/31.
 */
public class FrameListViewAdapter extends BaseAdapter {

    private Context mcontext;

    public String[] img_text ={"太湖水位","平均水温","氨氮","总氮","总磷","藻密度"};

    public  int[] imgs = {R.drawable.app_aapay,R.drawable.app_aligame, R.drawable.app_appcenter,
                          R.drawable.app_assign,R.drawable.app_plane,R.drawable.app_transfer};

    @Override
    public int getCount() {
        return img_text.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
