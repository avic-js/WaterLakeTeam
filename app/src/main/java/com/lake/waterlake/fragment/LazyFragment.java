package com.lake.waterlake.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yyh on 16/8/31.
 *http://blog.csdn.net/q844258542/article/details/51525749
 * viewpager切换页面时,让fragment进行懒加载操作,是比较好的体验,节省省流量,避免预加载时造成的卡顿现象.
  懒加载操作主要使用到fragment 的 setUserVisibleHint() , 及getUserVisibleHint()方法
 setUserVisibleHint() 方法在viewpageradapter内部调用, 会为fragment 进行赋值, 如果fragment 不可见赋值为false. 反之为true.
 getUserVisibleHint() 方法是获取这个值, 通过fragment 当前是否可见, 来进行加载数据的操作, 这样就实现了预加载的功能.
 */
public abstract class LazyFragment extends Fragment{

    /**
     * Fragment title
     */

    public String fragmentTitle;

    /**
     * 是否可见
     */
    private boolean isVisible;

    /**
     * 标志位，View已经初始化完成
     * 用isAdded（）属性代替
     * isPrepared 准确，isAdded有可能出现onCreateView没走完但是isAdded了
     */
    private boolean isPrepared;

    /**
     * 是否第一次加载
     */
    private  boolean isFirstLoad = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 若 viewpager 不设置 setOffscreenPageLimit 或设置数量不够
        // 销毁的Fragment onCreateView 每次都会执行(但实体类没有从内存销毁)
        // 导致initData反复执行,所以这里注释掉
        // isFirstLoad = true;

        // 2016/04/29
        // 取消 isFirstLoad = true的注释 , 因为上述的initData本身就是应该执行的
        // onCreateView执行 证明被移出过FragmentManager initData确实要执行.
        // 如果这里有数据累加的Bug 请在initViews方法里初始化您的数据 比如 list.clear();
        isFirstLoad = true;
        View view = initViews(inflater,container,savedInstanceState);
        isPrepared = true;
        lazyLoad();

        return view;
    }

    /**
     * 如果是与ViewPager一起使用，调用的是setUserVisibleHint
     * @param isVisibleToUser 是否显示出来了
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }

    }

    /**
     * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
     * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
     *
     * @param hidden hidden True if the fragment is now hidden, false if it is not
     *               visible.
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected  void onVisible(){
        lazyLoad();
    }

    protected  void onInvisible(){};

    protected void lazyLoad(){
        if (isPrepared && isVisible && isFirstLoad){
            isFirstLoad = false;
            initData();
        }
    }


    protected  abstract  View  initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected abstract void  initData();
}
