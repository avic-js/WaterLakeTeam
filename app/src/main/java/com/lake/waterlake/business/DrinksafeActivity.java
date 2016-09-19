package com.lake.waterlake.business;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lake.waterlake.R;
import com.lake.waterlake.customAdapter.PersonAdapter;
import com.lake.waterlake.model.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyh on 16/9/12.
 * 饮水安全
 */
public class DrinksafeActivity extends Activity {

    TextView SZ_time; //沙渚监测时间
    TextView XD_time2;//锡东监测时间
    ListView SZ_listView;//沙渚列表
    ListView XD_listView;//锡东列表
    TextView title_text;//抬头标题
    Button back_Btn;//back

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "drink safe", Toast.LENGTH_SHORT);
        setContentView(R.layout.drinksafe_view);
        title_text =(TextView)findViewById(R.id.title_center_text);
        title_text.setText("饮水安全");
        back_Btn = (Button)findViewById(R.id.back_btn);
        back_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SZ_time =  (TextView)findViewById(R.id.SZ_time);
        XD_time2 =(TextView)findViewById(R.id.XD_time2);
        SZ_listView = (ListView)findViewById(R.id.SZ_listView);
        XD_listView = (ListView)findViewById(R.id.XD_listView2);

        List<Person> personList = new ArrayList<Person>();
        personList.add(new Person("zhangfei","张飞","38"));
        personList.add(new Person("liwang","李王","12"));
        personList.add(new Person("xiaoming","小明","24"));
        personList.add(new Person("zhaolei","赵磊","21"));


        PersonAdapter perAdapter = new PersonAdapter(this,R.layout.my_listitem,personList);
        SZ_listView.setAdapter(perAdapter);

        personList = new ArrayList<Person>();
        personList.add(new Person("zhangfei","xxx","12"));
        personList.add(new Person("liwang","ttt","23"));
        personList.add(new Person("xiaoming","vvv","34"));
        personList.add(new Person("zhaolei", "eee", "45"));

        PersonAdapter perAdapter2 = new PersonAdapter(this,R.layout.my_listitem,personList);

        XD_listView.setAdapter(perAdapter);

    }
}
