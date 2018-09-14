package aframework.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

/**
 * Created by Jay on 2018/9/6 15:40
 *
 * @author Jay
 */
public class RadiusCompatButton extends AppCompatButton {
    private static final float DEFAULT_RADIUS = 1000;
    private float mRadius;

    public RadiusCompatButton(Context context, AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.buttonStyle);
    }

    public RadiusCompatButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RadiusCompatButton);

        mRadius = typedArray.getDimension(R.styleable.RadiusCompatButton_round_radius, DEFAULT_RADIUS);

        typedArray.recycle();

        setBackground();
    }

    private void setBackground() {
        Drawable background = getBackground();

        InsetDrawable insetDrawable = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // SDK VERSION >= 21
            if (background instanceof RippleDrawable) {
                RippleDrawable rippleDrawable = (RippleDrawable) background;

                Drawable itemDrawable = rippleDrawable.getDrawable(0);
                if (itemDrawable instanceof InsetDrawable) {
                    insetDrawable = (InsetDrawable) itemDrawable;
                }
            }
        } else if (background instanceof InsetDrawable) {
            insetDrawable = (InsetDrawable) background;
        }

        if (insetDrawable != null) {
            Drawable drawable = insetDrawable.getDrawable();

            if (drawable instanceof GradientDrawable) {
                GradientDrawable gradientDrawable = (GradientDrawable) drawable;
                gradientDrawable.setCornerRadius(mRadius);
            }
        }

    }
}
