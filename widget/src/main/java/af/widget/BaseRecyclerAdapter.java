package af.widget;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;

/**
 * Created by Jay on 2018/8/31 16:21
 *
 * @author Jay
 */
public abstract class BaseRecyclerAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;

    public BaseRecyclerAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }


    public Context getContext() {
        return mContext;
    }

    public LayoutInflater getLayoutInflater() {
        return mLayoutInflater;
    }

    public abstract Object getItem(int position);
}
