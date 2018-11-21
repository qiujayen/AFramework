package af.core;


import androidx.fragment.app.Fragment;

/**
 * Created by Jay on 2018/9/7 09:13
 *
 * @author Jay
 */
public abstract class CoreFragment extends Fragment {


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            onVisible();
        } else {
            onHidden();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            onHidden();
        } else {
            onVisible();
        }
    }

    protected void onHidden() {

    }


    protected void onVisible() {

    }


}
