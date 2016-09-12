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
 * Created by yyh on 16/8/30.
 */
public class FrameGridAdapter extends BaseAdapter{

    private Context mcontext;

    public String[] img_text ={"饮水安全","蓝藻胡泛","气象水文","调水引流","卫星遥感","河道水质"};

    public static int[] imgs = {R.drawable.app_aapay,R.drawable.app_aligame,
            R.drawable.app_appcenter,
        R.drawable.app_assign,R.drawable.app_plane,R.drawable.app_transfer};

    public FrameGridAdapter() {
        super();
    }

    public FrameGridAdapter(Context mcontext){
        this.mcontext = mcontext;
    }



    @Override
    public int getCount() {
        return imgs.length;
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
        if (convertView ==null){
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.gridview_frame,parent,false);
        }
        TextView tv  = BaseViewHolder.get(convertView, R.id.tv_item);
        ImageView iv = BaseViewHolder.get(convertView,R.id.iv_item);
        iv.setBackgroundResource(imgs[position]);
        tv.setText(img_text[position]);
        return convertView;
    }


}
