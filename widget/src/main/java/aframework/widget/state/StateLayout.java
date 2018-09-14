package aframework.widget.state;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Jay on 2018/9/7 09:17
 *
 * @author Jay
 */
public class StateLayout extends ViewGroup {

    private final StateHelper mStateHelper;

    public StateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        mStateHelper = new StateHelper(this);
    }

    public void setState(Integer... states) {
        mStateHelper.setState(states);
    }

    public void addState(Integer... states) {
        mStateHelper.addState(states);
    }

    public void setStateChangeListener(StateChangeListener listener) {
        mStateHelper.setStateChangeListener(listener);
    }

    public void setup() {
        mStateHelper.setup();
    }

    public void setup(int normalState) {
        mStateHelper.setup(normalState);
    }

    public void setCurrentState(int state, Object data) {
        mStateHelper.setCurrentState(state, data);
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
