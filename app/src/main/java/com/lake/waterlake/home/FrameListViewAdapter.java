package com.lake.waterlake.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lake.waterlake.R;

/**
 * Created by yyh on 16/8/31.
 */
public class FrameListViewAdapter extends BaseAdapter {
    public FrameListViewAdapter(){
        super();
    }

    public FrameListViewAdapter(Context mCtx,String[] mcs_value,String[] mcs_time){
        this.mContext = mCtx;
        this.cs_values = mcs_value;
        this.cs_times = mcs_time;

    }

    private Context mContext;

    public String[] cs_names ={"太湖水位(M)","平均水温(C)","氨氮(mg/L)","总氮(mg/L)","总磷(mg/L)","藻密度(M3)"};

    public  int[] head_imgs = {R.drawable.app_aapay,R.drawable.app_aligame, R.drawable.app_appcenter,
                          R.drawable.app_assign,R.drawable.app_plane,R.drawable.app_transfer};

    public String[] cs_values; /** params value **/

    public String[] cs_times; /** params time **/

    @Override
    public int getCount() {
        return cs_names.length;
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
        if (convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_frame,parent,false);
        }

        ImageView head_img = BaseViewHolder.get(convertView,R.id.header_list);
        TextView cs_name = BaseViewHolder.get(convertView,R.id.cs_name);
        TextView cs_value = BaseViewHolder.get(convertView,R.id.cs_value);
        TextView cs_time = BaseViewHolder.get(convertView,R.id.cs_time);

        head_img.setBackgroundResource(head_imgs[position]);
        cs_name.setText(cs_names[position]);
        cs_value.setText(cs_values[position]);
        cs_time.setText(cs_times[position]);

        return convertView;
    }
}
