package com.lake.waterlake.customAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lake.waterlake.R;
import com.lake.waterlake.home.BaseViewHolder;
import com.lake.waterlake.model.ThreeParams;
import com.lake.waterlake.model.TwoParams;

import java.util.List;

/**
 * Created by yyh on 16/9/21.
 */
public class ThreeParamsAdapter extends BaseAdapter {

    private Context mcontext;
    List<ThreeParams> threeParamsList;

    public ThreeParamsAdapter(Context context, int resource, List<ThreeParams> mlist) {

        this.mcontext = context;
        this.threeParamsList = mlist;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {//inflate(R.layout.my_listitem,parent,false);
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.threeparams_view, parent, false);
        }

        TextView obj = BaseViewHolder.get(convertView, R.id.obj);
        TextView obj1 = BaseViewHolder.get(convertView, R.id.obj1);
        TextView obj2 = BaseViewHolder.get(convertView, R.id.obj2);

        obj.setText(threeParamsList.get(position).getObj1());
        obj1.setText(threeParamsList.get(position).getObj2());
        obj2.setText(threeParamsList.get(position).getObj3());
        return convertView;
    }


    @Override
    public int getCount() {
        return threeParamsList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}