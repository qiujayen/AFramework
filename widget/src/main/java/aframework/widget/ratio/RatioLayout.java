package aframework.widget.ratio;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import aframework.widget.R;

/**
 * Created by Jay on 2018/9/5 16:29
 *
 * @author Jay
 */
public class RatioLayout extends ViewGroup {
    private static final int WIDTH = 0;
    private static final int HEIGHT = 1;
    private final float mWidthRatio;
    private final float mHeightRatio;
    private final int mFixed;

    public RatioLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(true);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);
        mWidthRatio = typedArray.getFloat(R.styleable.RatioLayout_width_radio, -1);
        mHeightRatio = typedArray.getFloat(R.styleable.RatioLayout_height_radio, -1);
        mFixed = typedArray.getInt(R.styleable.RatioLayout_fixed, WIDTH);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mWidthRatio != -1 && mHeightRatio != -1) {
            // width/height = mWidthRatio/mHeightRatio
            final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
            final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
            switch (mFixed) {
                case WIDTH:
                    if (widthMode != MeasureSpec.EXACTLY) {
                        throw new IllegalStateException("layout_width value is not exactly");
                    }
                    final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
                    heightMeasureSpec = MeasureSpec.makeMeasureSpec(Math.round(mHeightRatio / mWidthRatio * widthSize), widthMode);
                    break;
                case HEIGHT:

                    if (heightMode != MeasureSpec.EXACTLY) {
                        throw new IllegalStateException("layout_height value is not exactly");
                    }
                    final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

                    widthMeasureSpec = MeasureSpec.makeMeasureSpec(Math.round(mWidthRatio / mHeightRatio * heightSize), heightMode);

                    break;
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            child.layout(l, t, r, b);
        }
    }
}
