package com.lake.waterlake.business;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.lake.waterlake.R;

/**
 * Created by yyh on 16/9/2.
 *
 * 通用二级列表页面
 */
public class commonSecondListViewActivity extends Activity{

    TextView kindName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_sec_view);



    }
}
