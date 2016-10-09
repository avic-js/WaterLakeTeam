package com.lake.waterlake.customcomponents;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RadioButton;
import android.content.res.TypedArray;

import com.lake.waterlake.R;

/**
 * Created by yyh on 16/9/1.
 * 自定义MenuRadio 组件
 */
public class MenuRadioButton  extends RadioButton{

    private int mDrawableSize;// xml set size

    private int mdynaSize;// 动态加载 size

    public MenuRadioButton(Context context,int dynaSize){
        this(context);
      //  this.mdynaSize =  dynaSize;
       // this.initRadioButton(context,null,1);
    }

    public MenuRadioButton(Context context) {
        this(context, null, 0);
    }

    public MenuRadioButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MenuRadioButton(Context context, AttributeSet attrs,int defStyle) {
        super(context, attrs, defStyle);
        this.initRadioButton(context, attrs, defStyle);
    }

    public  void initRadioButton(Context context, AttributeSet attrs,int defStyle){

        Drawable drawableLeft = null,drawableRight = null,drawableTop = null,  drawableBottom = null;

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MenuRadioButton);

        for (int i=0;i<array.getIndexCount();i++){
            int attr =  array.getIndex(i);
            Log.i("MyRadioButton", "attr:" + attr);
            switch (attr){
                case  R.styleable.MenuRadioButton_selfdrawableSize:
                        if (defStyle==0) { // 是否动态加载
                        mDrawableSize = array.getDimensionPixelSize(R.styleable.MenuRadioButton_selfdrawableSize, 50);
                    } else{
                        mDrawableSize = array.getDimensionPixelSize(mdynaSize, 50);
                        Drawable dra  =  array.getDrawable(R.styleable.MenuRadioButton_drawableLeft);


                    }
                    break;
                case R.styleable.MenuRadioButton_drawableTop:
                    drawableTop = array.getDrawable(attr);
                    break;
                case R.styleable.MenuRadioButton_drawableBottom:
                    drawableRight = array.getDrawable(attr);
                    break;
                case R.styleable.MenuRadioButton_drawableRight:
                    drawableBottom = array.getDrawable(attr);
                    break;
                case R.styleable.MenuRadioButton_drawableLeft:
                    drawableLeft = array.getDrawable(attr);
                    break;
                default :
                    break;
            }

        }
        array.recycle();

        setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);
    }



    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left,
                                                        Drawable top, Drawable right, Drawable bottom) {

        if (left != null) {
            left.setBounds(0, 0, mDrawableSize, mDrawableSize);
        }
        if (right != null) {
            right.setBounds(0, 0, mDrawableSize, mDrawableSize);
        }
        if (top != null) {
            top.setBounds(0, 0, mDrawableSize, mDrawableSize);
        }
        if (bottom != null) {
            bottom.setBounds(0, 0, mDrawableSize, mDrawableSize);
        }
        setCompoundDrawables(left, top, right, bottom);
    }
}
















