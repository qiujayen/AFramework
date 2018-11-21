package af.widget.state;

import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Jay on 2018/9/7 09:27
 *
 * @author Jay
 */
public class StateHelper {
    private static final String TAG = "StateHelper";

    public static final int NORMAL = -1;
    public static final int EMPTY = 0;
    public static final int LOADING = 1;
    public static final int ERROR = 2;

    private SparseArray<View> mStateViewPool = new SparseArray<>();

    private final ViewGroup mStateLayout;

    private final List<Integer> mStates = new ArrayList<>();
    private StateChangeListener mStateChangeListener;

    {
        setState(NORMAL, EMPTY, LOADING, ERROR);
    }

    public StateHelper(ViewGroup stateLayout) {
        mStateLayout = stateLayout;
    }

    public void setState(Integer... states) {
        mStates.clear();
        addState(states);
    }

    public void addState(Integer... states) {
        mStates.addAll(Arrays.asList(states));
    }

    public void setStateChangeListener(StateChangeListener listener) {
        mStateChangeListener = listener;
    }

    public void setup() {
        setup(NORMAL);
    }

    public void setup(int normalState) {
        final int childCount = mStateLayout.getChildCount();
        if (childCount > 1) {
            Log.e(TAG, "只能有一个子 View");
        }
        View normalView = mStateLayout.getChildAt(0);
        mStateViewPool.put(normalState, normalView);

        if (mStateChangeListener != null) {
            for (Integer state : mStates) {
                if (state == normalState) {
                    continue;
                }
                View stateView = mStateChangeListener.onCreateStateView(state);
                mStateViewPool.put(state, stateView);
                mStateLayout.addView(stateView);
                stateView.setVisibility(View.GONE);
            }
        }
    }

    public void setCurrentState(int state, Object data) {
        if (mStateChangeListener != null) {
            final int size = mStateViewPool.size();
            for (int i = 0; i < size; i++) {
                int statePool = mStateViewPool.keyAt(i);
                View view = mStateViewPool.get(statePool);
                if (statePool == state) {
                    view.setVisibility(View.VISIBLE);
                    mStateChangeListener.onStateViewChanged(view, data);
                } else {
                    view.setVisibility(View.GONE);
                }
            }
        }
    }
}
