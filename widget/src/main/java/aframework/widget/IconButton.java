package aframework.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Jay on 2018/9/14 13:38
 *
 * @author Jay
 */
public class IconButton extends AppCompatButton {

    private TextView mTextView;
    private ImageView mIconView;

    public IconButton(Context context, AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.buttonStyle);
    }

    public IconButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mTextView = new TextView(context, attrs, defStyleAttr);

        mIconView = new ImageView(context, attrs, defStyleAttr);

    }
}
