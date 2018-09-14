package aframework.widget.round;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Jay on 2018/9/6 16:55
 *
 * @author Jay
 */
@SuppressLint("AppCompatCustomView")
public class RoundButton extends Button {

    public RoundButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RoundButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        new RoundBackgroundHelper(this).loadFromAttributes(context, attrs);
    }
}
