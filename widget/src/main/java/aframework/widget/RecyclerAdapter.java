package aframework.widget;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jay on 2018/8/31 16:14
 *
 * @author Jay
 */
public abstract class RecyclerAdapter<T> extends BaseRecyclerAdapter<RecyclerViewHolder> {


    private List<T> mItems = new ArrayList<>();

    public RecyclerAdapter(Context context) {
        super(context);
    }

    public RecyclerAdapter(Context context, List<T> items) {
        this(context);
        mItems = items;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = getLayoutInflater().inflate(onBindViewLayout(viewType), parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @LayoutRes
    protected abstract int onBindViewLayout(int viewType);


    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setItems(List<T> items) {
        mItems = items;
    }

    public void addItems(List<T> items) {
        mItems.addAll(items);
    }

    public void addItem(T item) {
        mItems.add(item);
    }

    public T removeItem(int position) {
        return mItems.remove(position);
    }

    public void removeItem(T item) {
        mItems.remove(item);
    }

    public void clearItems() {
        mItems.clear();
    }

    public List<T> getItems() {
        return mItems;
    }

    @Override
    public T getItem(int position) {
        return mItems.get(position);
    }
}
