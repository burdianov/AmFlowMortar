package com.testography.am_mvp.flow;

import flow.ClassKey;

public abstract class AbstractScreen<T> extends ClassKey {
    public String getScopeName() {
        return getClass().getName();
    }

    public abstract Object createScreenComponent(T parentComponent);

    // TODO: 25-Nov-16 unregister scope 
    public void unregisterScope() {

    }
}
