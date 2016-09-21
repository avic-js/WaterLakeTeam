package com.lake.waterlake.customAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lake.waterlake.R;
import com.lake.waterlake.home.BaseViewHolder;
import com.lake.waterlake.model.SixParams;
import com.lake.waterlake.model.ThreeParams;

import java.util.List;

/**
 * Created by yyh on 16/9/21.
 */
public class SixParamsAdapter extends BaseAdapter {

    private Context mcontext;
    List<SixParams> sixParamsList;

    public SixParamsAdapter(Context context, int resource, List<SixParams> mlist) {

        this.mcontext = context;
        this.sixParamsList = mlist;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {//inflate(R.layout.my_listitem,parent,false);
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.sixparams_view, parent, false);
        }

        TextView obj = BaseViewHolder.get(convertView, R.id.obj);
        TextView obj1 = BaseViewHolder.get(convertView, R.id.obj1);
        TextView obj2 = BaseViewHolder.get(convertView, R.id.obj2);
        TextView obj3 = BaseViewHolder.get(convertView, R.id.obj3);
        TextView obj4 = BaseViewHolder.get(convertView, R.id.obj4);
        TextView obj5 = BaseViewHolder.get(convertView, R.id.obj5);

        obj.setText(sixParamsList.get(position).getObj1());
        obj1.setText(sixParamsList.get(position).getObj2());
        obj2.setText(sixParamsList.get(position).getObj3());
        obj3.setText(sixParamsList.get(position).getObj4());
        obj4.setText(sixParamsList.get(position).getObj5());
        obj5.setText(sixParamsList.get(position).getObj6());
        return convertView;
    }


    @Override
    public int getCount() {
        return sixParamsList.size();
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