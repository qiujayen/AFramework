package aframework.widget;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Jay on 2018/8/31 16:15
 *
 * @author Jay
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private final SparseArray<View> mViews = new SparseArray<>();

    public RecyclerViewHolder(View itemView) {
        super(itemView);
    }

    public <V extends View> V getView(int id) {
        View view = mViews.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            mViews.put(id, view);
        }
        return (V) view;
    }

    public RecyclerViewHolder setText(int viewId, int text) {
        TextView textView = getView(viewId);
        textView.setText(text);
        return this;
    }

    public RecyclerViewHolder setText(int viewId, CharSequence text) {
        TextView textView = getView(viewId);
        textView.setText(text);
        return this;
    }
}
