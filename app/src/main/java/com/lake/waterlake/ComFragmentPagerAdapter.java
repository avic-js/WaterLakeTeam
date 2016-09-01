package com.lake.waterlake;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by yyh on 16/8/31.
 */
public class ComFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;

    public ComFragmentPagerAdapter(FragmentManager fm,List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }


}
