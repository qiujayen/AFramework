package aframework.core;

import android.app.Application;

/**
 * Created by Jay on 2018/8/18 13:19.
 *
 * @author Jay
 * @version 1.0
 */
public abstract class CoreApplication extends Application implements CoreApplicationDelegate.ApplicationStatusCallback, Thread.UncaughtExceptionHandler {

    @Override
    public void onCreate() {
        super.onCreate();
        CoreApplicationDelegate applicationDelegate = new CoreApplicationDelegate();
        applicationDelegate.onCreate(this);
        applicationDelegate.setApplicationStatusCallback(this);
        applicationDelegate.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 应用进入前台时
     */
    @Override
    public void onApplicationEnterForeground() {

    }

    /**
     * 应用进入后台时
     */
    @Override
    public void onApplicationEnterBackground() {

    }

    /**
     * 当发生异常且没有捕获时回调
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {

    }
}