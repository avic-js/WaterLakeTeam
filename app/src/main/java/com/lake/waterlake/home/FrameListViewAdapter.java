package com.lake.waterlake.home;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
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

    public FrameListViewAdapter(Context mCtx,String[] mcs_valueUp,String[] mcs_valueDown,String[] mcs_time){
        this.mContext = mCtx;
        this.cs_valuesUp = mcs_valueUp;
        cs_valuesDown = mcs_valueDown;
        this.cs_times = mcs_time;

    }

    private Context mContext;

    public String[] cs_names ={"太湖水位(M)","平均水温(C)","氨氮(mg/L)","总氮(mg/L)","总磷(mg/L)","藻密度","沙渚天气"};

    public  int[] head_imgs = {R.drawable.ico01_2x,R.drawable.ico02_2x, R.drawable.ico06_2x,
                          R.drawable.ico06_2x,R.drawable.ico03_2x,R.drawable.ico04_2x,R.drawable.ico05_2x};

    public String[] cs_valuesUp; /** params value **/

    public String[] cs_valuesDown; /** params value **/

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
        TextView cs_value1 = BaseViewHolder.get(convertView,R.id.cs_value1);
        TextView cs_time = BaseViewHolder.get(convertView,R.id.cs_time);


        head_img.setBackgroundResource(head_imgs[position]);
        cs_name.setText(cs_names[position]);
        if (position == 2 || position==3|| position==4|| position==5) {
            String xidong = "锡东:".concat(cs_valuesUp[position]);
            String shazhu = "沙渚:".concat(cs_valuesDown[position]);
            SpannableStringBuilder xd_style = new SpannableStringBuilder(xidong);
            // style.setSpan(new BackgroundColorSpan(Color.RED), 2, 5, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //设置指定位置textview的背景颜色
            xd_style.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 3, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //设置指定位置文字的颜色

            SpannableStringBuilder sz_style = new SpannableStringBuilder(shazhu);
            sz_style.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 3, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //设置指定位置文字的颜色

            cs_value.setText(xd_style);
            cs_value1.setText(sz_style);
            cs_time.setText(cs_times[position]);
        }else if(position==1 || position==0){
            String topVal = "".concat(cs_valuesUp[position]);
            SpannableStringBuilder xd_style = new SpannableStringBuilder(topVal);
            cs_value.setText(xd_style);
            cs_value1.setText("");
            cs_time.setText(cs_times[position]);
        }else {
            String xidong = "天气:".concat(cs_valuesUp[position]);
            String shazhu = "气温:".concat(cs_valuesDown[position]);
            SpannableStringBuilder xd_style = new SpannableStringBuilder(xidong);
            // style.setSpan(new BackgroundColorSpan(Color.RED), 2, 5, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //设置指定位置textview的背景颜色
            xd_style.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 3, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //设置指定位置文字的颜色

            SpannableStringBuilder sz_style = new SpannableStringBuilder(shazhu);
            sz_style.setSpan(new ForegroundColorSpan(Color.BLACK), 0, 3, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //设置指定位置文字的颜色

            cs_value.setText(xd_style);
            cs_value1.setText(sz_style);
            cs_time.setText("");
        }

        return convertView;
    }
}
