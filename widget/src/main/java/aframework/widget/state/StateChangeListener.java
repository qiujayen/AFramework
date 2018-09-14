package aframework.widget.state;

import android.view.View;

/**
 * Created by Jay on 2018/9/7 09:37
 *
 * @author Jay
 */
public interface StateChangeListener {
    View onCreateStateView(Integer state);

    void onStateViewChanged(View view, Object data);
}
