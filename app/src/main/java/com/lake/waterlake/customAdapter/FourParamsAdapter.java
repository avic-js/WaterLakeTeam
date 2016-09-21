package com.lake.waterlake.customAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lake.waterlake.R;
import com.lake.waterlake.home.BaseViewHolder;
import com.lake.waterlake.model.FourParams;
import com.lake.waterlake.model.SixParams;

import java.util.List;

/**
 * Created by yyh on 16/9/21.
 */
public class FourParamsAdapter extends BaseAdapter {

    private Context mcontext;
    List<FourParams> fourParamsList;

    public FourParamsAdapter(Context context, int resource, List<FourParams> mlist) {

        this.mcontext = context;
        this.fourParamsList = mlist;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {//inflate(R.layout.my_listitem,parent,false);
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.fourparams_view, parent, false);
        }

        TextView obj = BaseViewHolder.get(convertView, R.id.obj);
        TextView obj1 = BaseViewHolder.get(convertView, R.id.obj1);
        TextView obj2 = BaseViewHolder.get(convertView, R.id.obj2);
        TextView obj3 = BaseViewHolder.get(convertView, R.id.obj3);


        obj.setText(fourParamsList.get(position).getObj1());
        obj1.setText(fourParamsList.get(position).getObj2());
        obj2.setText(fourParamsList.get(position).getObj3());
        obj3.setText(fourParamsList.get(position).getObj4());

        return convertView;
    }


    @Override
    public int getCount() {
        return fourParamsList.size();
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