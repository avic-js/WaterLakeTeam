package com.lake.waterlake.business;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lake.waterlake.R;
import com.lake.waterlake.customAdapter.PersonAdapter;
import com.lake.waterlake.model.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyh on 16/9/12.
 */
public class ComSecondListViewActivity extends Activity {

    TextView kindName;//地点名称
    TextView jc_time;//监测时间
    ListView root_listView;//列表
    ScrollView root_scrollView;// roll_root

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_sec_view);

        kindName = (TextView)findViewById(R.id.kindName);
        jc_time = (TextView)findViewById(R.id.tj_time);
        root_listView = (ListView)findViewById(R.id.contnet_listView);
        root_scrollView= (ScrollView)findViewById(R.id.scrollView);

        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("zhangfei","张飞","38"));
        personList.add(new Person("liwang","李王","12"));
        personList.add(new Person("xiaoming","小明","24"));
        personList.add(new Person("zhaolei","赵磊","21"));

        ListView mylistview =  (ListView)findViewById(R.id.contnet_listView);
//        PersonAdapter perAdapter = new PersonAdapter(this,R.layout.my_listitem,personList);
//
//        mylistview.setAdapter(perAdapter);

        /**
         * 动态加载类
         */
        LinearLayout root_layout = (LinearLayout)findViewById(R.id.root_linearLayout);// root
        //root_LinearLayout_style
        LinearLayout.LayoutParams llp_root =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        RelativeLayout newSingleRL = new RelativeLayout(this);
        newSingleRL = gennerateSingleLayout("锡东（环保局）","2016:10:10",11,12);

        root_layout.addView(newSingleRL,llp_root);

    }


    /**
     * 新建一个动态布局
     *RelativeLayout.Layoutparams params = (RelativeLayout.LayoutParams)button.getLayoutParams();
     params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
     params.addRule(RelativeLayout.LEFT_OF, R.id.id_to_be_left_of);

     button.setLayoutParams(params); //使layout更新
     */
    private RelativeLayout gennerateSingleLayout(String tName, String time,int tNameId,int timeId){
        RelativeLayout layout_root_relative = new RelativeLayout(this);

        RelativeLayout relativelayout_root = new RelativeLayout(this);
        relativelayout_root.setBackgroundColor(Color.argb(0xff, 0x00, 0xff, 0x00));
        relativelayout_root.setPadding(5,5,5,5);

        //名称
        TextView tvName = new TextView(this);
        RelativeLayout.LayoutParams rp_sub =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        tvName.setText(tName);
        tvName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);

        tvName.setId(Integer.valueOf(tNameId));//import
        tvName.setPadding(5, 5, 5, 5);
        // rp_sub.addRule(RelativeLayout.ALIGN_LEFT);
        rp_sub.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        tvName.setGravity(Gravity.CENTER);
        //tvName.setLayoutParams(rp_sub);
        relativelayout_root.addView(tvName);


        // 时间
        TextView mtime = new TextView(this);
        RelativeLayout.LayoutParams rp_sub_time =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        mtime.setText(time);
        mtime.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        //  mtime.setLayoutParams(rp_sub);//设置父节点
        mtime.setId(Integer.valueOf(timeId));//import
        // mtime.setPadding(5, 5, 5, 5);
        mtime.setGravity(Gravity.CENTER);

//        rp_sub_time.addRule(RelativeLayout.RIGHT_OF, Integer.valueOf(tNameId));
        rp_sub_time.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        mtime.setLayoutParams(rp_sub_time);//
        relativelayout_root.addView(mtime);
        //   layout_root_relative.addView(layout_sub_Lin,RL_MW);


        //RadioButton 自定义组件
        TextView mrbE  = new TextView(this);
        RelativeLayout.LayoutParams rp_sub_RButton =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        mrbE.setId(Integer.valueOf(124));
        mrbE.setText("监测时间:");
        //   mrb.setChecked(true);
        mrbE.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 19);
        mrbE.setLayoutParams(rp_sub_RButton);
        //mrb.setButtonDrawable(getResources().getDrawable(android.R.color.transparent));//android:button="@null"
        Drawable drawable=this.getResources().getDrawable(R.drawable.app_aapay);
        mrbE.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        drawable.setBounds(0, 0, 80, 80);
        mrbE.setCompoundDrawables(drawable, null, null, null);
        //mtime.setGravity(Gravity.RIGHT);
        mrbE.setGravity(Gravity.CENTER);
        //rp_sub_RButton.addRule(RelativeLayout.LEFT_OF,Integer.valueOf(timeId));
        // rp_sub_RButton.addRule(RelativeLayout.CENTER_VERTICAL);

        rp_sub_RButton.addRule(RelativeLayout.LEFT_OF, Integer.valueOf(timeId));

        mrbE.setLayoutParams(rp_sub_RButton);//
        // relativelayout_root.addView(mrbE, rp_sub_time);
        // rp_sub_RButton.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        relativelayout_root.addView(mrbE);

        return relativelayout_root;
    }

}
