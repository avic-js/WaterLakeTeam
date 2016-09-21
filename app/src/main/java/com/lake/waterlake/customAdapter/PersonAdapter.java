package com.lake.waterlake.customAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lake.waterlake.R;
import com.lake.waterlake.home.BaseViewHolder;
import com.lake.waterlake.model.Person;
import com.lake.waterlake.model.TwoParams;

import java.util.List;

/**
 * Created by yyh on 16/9/5.
 */
public class PersonAdapter  extends BaseAdapter{

    private Context mcontext;
    List<TwoParams> personList;

    public PersonAdapter(Context context, int resource, List<TwoParams> mlist) {

        this.mcontext =  context;
        this.personList =  mlist;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){//inflate(R.layout.my_listitem,parent,false);
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.my_listitem,parent,false);
        }

        TextView id = BaseViewHolder.get(convertView,R.id.obj);
        TextView name  = BaseViewHolder.get(convertView,R.id.obj2);

        id.setText(personList.get(position).getObj1());
        name.setText(personList.get(position).getObj2());

        return convertView;
    }


    @Override
    public int getCount() {
        return personList.size();
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
