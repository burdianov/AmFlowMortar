package com.testography.am_mvp.flow;

import android.util.Log;

import com.testography.am_mvp.mortar.ScreenScoper;

import flow.ClassKey;

public abstract class AbstractScreen<T> extends ClassKey {

    public static final String TAG = "AbstractScreen";

    public String getScopeName() {
        return getClass().getName();
    }

    public abstract Object createScreenComponent(T parentComponent);

    // TODO: 25-Nov-16 unregister scope 
    public void unregisterScope() {
        Log.e(TAG, "unregisterScope: " + this.getScopeName());
        ScreenScoper.destroyScreenScope(getScopeName());
    }
}
