package af.widget;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Jay on 2018/8/31 14:13
 *
 * @author Jay
 * 1.支持 ContextMenu
 * 2.支持 setEmptyView
 * 3.支持 setOnItemClickListener
 * 4.支持 setOnItemLongClickListener
 */
public class PerfectRecyclerView extends RecyclerView {

    private ContextMenuInfo mContextMenuInfo;
    private View mEmptyView;

    private final RecyclerViewDataObserver mObserver = new RecyclerViewDataObserver();
    private AdapterRecyclerView.OnItemClickListener mOnItemClickListener;
    private AdapterRecyclerView.OnItemLongClickListener mOnItemLongClickListener;
    private RecyclerView.OnItemTouchListener mOnItemTouchListener = new OnItemTouchListener();

    public PerfectRecyclerView(Context context) {
        super(context);
    }

    public PerfectRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PerfectRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public void setOnItemClickListener(AdapterRecyclerView.OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(AdapterRecyclerView.OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
    }

    @Override
    public void onChildAttachedToWindow(View child) {
        super.onChildAttachedToWindow(child);
        child.setLongClickable(true);
    }

    @Override
    public void onChildDetachedFromWindow(View child) {
        super.onChildDetachedFromWindow(child);
        child.setLongClickable(false);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        addOnItemTouchListener(mOnItemTouchListener);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeOnItemTouchListener(mOnItemTouchListener);
    }

    @Override
    protected ContextMenuInfo getContextMenuInfo() {
        return mContextMenuInfo;
    }


    @Override
    public boolean showContextMenuForChild(View originalView) {

        int position = getChildAdapterPosition(originalView);
        long itemId = getChildItemId(originalView);
        mContextMenuInfo = new ContextMenuInfo(originalView, position, itemId);

        return super.showContextMenuForChild(originalView);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        final Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(mObserver);
        }
        super.setAdapter(adapter);

        adapter.registerAdapterDataObserver(mObserver);
    }

    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;

        // If not explicitly specified this view is important for accessibility.
        if (emptyView != null
                && emptyView.getImportantForAccessibility() == IMPORTANT_FOR_ACCESSIBILITY_AUTO) {
            emptyView.setImportantForAccessibility(IMPORTANT_FOR_ACCESSIBILITY_YES);
        }

        final Adapter adapter = getAdapter();
        final boolean empty = ((adapter == null) || adapter.getItemCount() == 0);
        updateEmptyStatus(empty);
    }

    private void updateEmptyStatus(boolean empty) {
        if (empty) {
            if (mEmptyView != null) {
                mEmptyView.setVisibility(View.VISIBLE);
                setVisibility(View.GONE);
            } else {
                // If the caller just removed our empty view, make sure the list view is visible
                setVisibility(View.VISIBLE);
            }
        } else {
            if (mEmptyView != null) mEmptyView.setVisibility(View.GONE);
            setVisibility(View.VISIBLE);
        }
    }

    private class RecyclerViewDataObserver extends AdapterDataObserver {

        @Override
        public void onChanged() {
            Adapter adapter = getAdapter();
            updateEmptyStatus(adapter.getItemCount() == 0);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            onChanged();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            onChanged();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            onChanged();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            onChanged();
        }
    }


    public static class ContextMenuInfo implements ContextMenu.ContextMenuInfo {

        ContextMenuInfo(View targetView, int position, long id) {
            this.targetView = targetView;
            this.position = position;
            this.id = id;
        }

        /**
         * The child view for which the context menu is being displayed. This
         * will be one of the children of this AdapterView.
         */
        public View targetView;

        /**
         * The position in the adapter for which the context menu is being
         * displayed.
         */
        public int position;

        /**
         * The row id of the item for which the context menu is being displayed.
         */
        public long id;
    }


    private class OnItemTouchListener extends RecyclerView.SimpleOnItemTouchListener {

        private GestureDetector mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                if (mOnItemClickListener == null) {
                    return super.onSingleTapUp(e);
                }

                final View childViewUnder = findChildViewUnder(e.getX(), e.getY());
                final int position = getChildAdapterPosition(childViewUnder);
                final long childItemId = getChildItemId(childViewUnder);

                if (position == RecyclerView.NO_POSITION) {
                    return super.onSingleTapUp(e);
                }
                childViewUnder.setClickable(true);
                mOnItemClickListener.onItemClick(PerfectRecyclerView.this, childViewUnder, position, childItemId);
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                if (mOnItemLongClickListener == null) {
                    super.onLongPress(e);
                    return;
                }
                final View childViewUnder = findChildViewUnder(e.getX(), getY());
                final int position = getChildAdapterPosition(childViewUnder);
                final long childItemId = getChildItemId(childViewUnder);

                if (position == RecyclerView.NO_POSITION) {
                    super.onLongPress(e);
                    return;
                }
                childViewUnder.setLongClickable(true);
                mOnItemLongClickListener.onItemLongClick(PerfectRecyclerView.this, childViewUnder, position, childItemId);

            }

        });

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            return mGestureDetector.onTouchEvent(e);
        }
    }
}
