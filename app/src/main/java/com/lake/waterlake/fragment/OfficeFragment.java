package com.lake.waterlake.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yyh on 16/8/31.
 */
public class OfficeFragment extends LazyFragment{


        @Override
        protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return null;
        }

        @Override
        protected void initData() {
            System.out.print("-----OfficeFragment-----");
        }
}
