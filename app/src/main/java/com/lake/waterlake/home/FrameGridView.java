package com.lake.waterlake.home;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by yyh on 16/8/30.
 */
public class FrameGridView extends GridView {


    public FrameGridView(Context context) {
        super(context);
    }

    public FrameGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FrameGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }


}