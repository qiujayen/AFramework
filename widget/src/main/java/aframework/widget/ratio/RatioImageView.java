package aframework.widget.ratio;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import aframework.widget.R;

/**
 * Created by Jay on 2018/9/5 17:07
 *
 * @author Jay
 */
@SuppressLint("AppCompatCustomView")
public class RatioImageView extends ImageView {
    private static final int WIDTH = 0;
    private static final int HEIGHT = 1;
    private final float mWidthRatio;
    private final float mHeightRatio;
    private final int mFixed;

    public RatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(true);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioImageView);
        mWidthRatio = typedArray.getFloat(R.styleable.RatioImageView_width_radio, -1);
        mHeightRatio = typedArray.getFloat(R.styleable.RatioImageView_height_radio, -1);
        mFixed = typedArray.getInt(R.styleable.RatioImageView_fixed, WIDTH);
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
}
