package com.lake.waterlake;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lake.waterlake.fragment.ComFragmentPagerAdapter;
import com.lake.waterlake.fragment.OfficeFragment;
import com.lake.waterlake.fragment.QueryFragment;
import com.lake.waterlake.fragment.HomeFragment;
import com.lake.waterlake.fragment.VisitFragment;
import com.lake.waterlake.util.WSFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyh on 16/8/31.
 */
public class RadioMainActivity extends FragmentActivity {

    private ViewPager viewPager;
    private RadioGroup radioGroup;
    private RadioButton rbchart,rbcontent,rbdiscovery,rbme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        WSFunction.initwsLogin();//初始化SESSION

        initView();
    }

    private void initView(){

        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        rbchart = (RadioButton)findViewById(R.id.rb_homeImg);
        rbcontent = (RadioButton)findViewById(R.id.rb_contacts);
        rbdiscovery = (RadioButton)findViewById(R.id.rb_discovery);
        rbme = (RadioButton)findViewById(R.id.rb_me);
        //RadioGroup选中状态改变监听
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                /**
                 * setCurrentItem 第二个参数控制页面切换动画
                 */
                switch (checkedId){
                    case R.id.rb_homeImg:
                        viewPager.setCurrentItem(0,false);
                        break;
                    case R.id.rb_contacts:
                        viewPager.setCurrentItem(1,false);
                        break;
                    case R.id.rb_discovery:
                        viewPager.setCurrentItem(2,false);
                        break;
                    case R.id.rb_me:
                        viewPager.setCurrentItem(3,false);
                        break;

                }
            }
        });

        /**
         * viewPage part
         */

        viewPager = (ViewPager)findViewById(R.id.FrameViewPager);
        HomeFragment homeFragment =new HomeFragment();
        QueryFragment queryFragment = new QueryFragment();
        OfficeFragment officeFragment = new OfficeFragment();
        VisitFragment visitFragment = new VisitFragment();
        List<Fragment> alFragment = new ArrayList<Fragment>();
        alFragment.add(homeFragment);
        alFragment.add(queryFragment);
        alFragment.add(officeFragment);
        alFragment.add(visitFragment);
        //viewPager Adapter

        viewPager.setAdapter(new ComFragmentPagerAdapter(getSupportFragmentManager(),alFragment));
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        radioGroup.check(R.id.rb_homeImg);
                        break;
                    case 1:
                        radioGroup.check(R.id.rb_contacts);
                        break;
                    case 2:
                        radioGroup.check(R.id.rb_discovery);
                        break;
                    case 3:
                        radioGroup.check(R.id.rb_me);
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}

