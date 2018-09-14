package aframework.widget.round;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;

import aframework.widget.R;

/**
 * Created by Jay on 2018/9/6 17:36
 *
 * @author Jay
 */
class RoundBackgroundHelper {

    private final View mView;

    RoundBackgroundHelper(View view) {
        mView = view;
    }

    void loadFromAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundBackgroundHelper);


        final float radius = a.getDimension(R.styleable.RoundBackgroundHelper_round_radius, 1000);

        final int solidColor = a.getColor(R.styleable.RoundBackgroundHelper_round_solid_color, 0);

        final int strokeWith = a.getDimensionPixelOffset(R.styleable.RoundBackgroundHelper_round_stroke_width, 0);
        final int strokeColor = a.getColor(R.styleable.RoundBackgroundHelper_round_stroke_color, 0);

        a.recycle();

        GradientDrawable gradientDrawable = new GradientDrawable();

        gradientDrawable.setShape(GradientDrawable.RECTANGLE);

        gradientDrawable.setColor(solidColor);
        gradientDrawable.setCornerRadius(radius);
        gradientDrawable.setStroke(strokeWith, strokeColor);

        mView.setBackground(gradientDrawable);
    }
}
