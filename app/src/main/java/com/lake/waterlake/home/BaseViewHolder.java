package com.lake.waterlake.home;

import android.util.SparseArray;
import android.view.View;

/**
 * Created by yyh on 16/8/31.
 */
public class BaseViewHolder {
    /**
     * @Description:万能的viewHolder
     * @author http://blog.csdn.net/finddreams
     */

        @SuppressWarnings("unchecked")
        public static <T extends View> T get(View view, int id) {
            SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
            if (viewHolder == null) {
                viewHolder = new SparseArray<View>();
                view.setTag(viewHolder);
            }
            View childView = viewHolder.get(id);
            if (childView == null) {
                childView = view.findViewById(id);
                viewHolder.put(id, childView);
            }
            return (T) childView;
        }


}
