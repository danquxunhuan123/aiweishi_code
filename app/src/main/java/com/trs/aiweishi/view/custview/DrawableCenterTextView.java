package com.trs.aiweishi.view.custview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by TRS on 2018/11/19.
 */

public class DrawableCenterTextView extends AppCompatTextView {
    public DrawableCenterTextView(Context context) {
        this(context, null);
    }

    public DrawableCenterTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable[] drawables = getCompoundDrawables();
        if (drawables != null) {
            Drawable leftDrawable = drawables[0]; //drawableLeft
            Drawable rightDrawable = drawables[2];//drawableRight
            if (leftDrawable != null || rightDrawable != null) {
                //1,获取text的width
                float textWidth = getPaint().measureText(getText().toString());
                //2,获取padding
                int drawablePadding = getCompoundDrawablePadding();
                int drawableWidth;
                float bodyWidth;
                if (leftDrawable != null) {
                    //3,获取drawable的宽度
                    drawableWidth = leftDrawable.getIntrinsicWidth();
                    //4,获取绘制区域的总宽度
                    bodyWidth = textWidth + drawablePadding + drawableWidth;
                } else {
                    drawableWidth = rightDrawable.getIntrinsicWidth();
                    bodyWidth = textWidth + drawablePadding + drawableWidth;
                    //图片居右设置padding
                    setPadding(0, 0, (int) (getWidth() - bodyWidth), 0);
                }
                canvas.translate((getWidth() - bodyWidth) / 2, 0);
            }
        }
        super.onDraw(canvas);
    }
}
