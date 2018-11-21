package af.core;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

/**
 * Created by Jay on 2018/8/18 13:18.
 *
 * @author Jay
 * @version 1.0
 */
public class CoreApplicationDelegate implements Application.ActivityLifecycleCallbacks {

    private int activities;
    private boolean isBackground;
    private ApplicationStatusCallback mApplicationStatusCallback;

    public void onCreate(Application application) {
        application.registerActivityLifecycleCallbacks(this);
    }

    public void setDefaultUncaughtExceptionHandler(final Thread.UncaughtExceptionHandler handler) {
        Thread.setDefaultUncaughtExceptionHandler(handler);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                //主线程异常拦截
                //noinspection InfiniteLoopStatement
                while (true) {
                    try {
                        Looper.loop();//主线程的异常会从这里抛出
                    } catch (Throwable e) {
                        handler.uncaughtException(Looper.getMainLooper().getThread(), e);
                    }
                }
            }
        });
    }

    public void setApplicationStatusCallback(ApplicationStatusCallback callback) {
        mApplicationStatusCallback = callback;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        activities++;
        if (isBackground && mApplicationStatusCallback != null) {
            isBackground = false;
            mApplicationStatusCallback.onApplicationEnterForeground();
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        activities--;
        if (activities == 0 && mApplicationStatusCallback != null) {
            isBackground = true;
            mApplicationStatusCallback.onApplicationEnterBackground();
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    public interface ApplicationStatusCallback {
        /**
         * 应用进入前台时
         */
        void onApplicationEnterForeground();

        /**
         * 应用进入后台时
         */
        void onApplicationEnterBackground();
    }
}
