package com.testography.am_mvp.mortar;

import android.util.Log;

import com.testography.am_mvp.di.DaggerService;
import com.testography.am_mvp.flow.AbstractScreen;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import mortar.MortarScope;

public class ScreenScoper {
    private static final String TAG = "ScreenScoper";
    private static Map<String, MortarScope> sScopeMap = new HashMap<>();

    public static MortarScope getScreenScope(AbstractScreen screen) {
        if (!sScopeMap.containsKey(screen.getScopeName())) {
            Log.e(TAG, "getScreenScope: create new scope");
            return createScreenScope(screen);
        } else {
            Log.e(TAG, "getScreenScope: return the existing scope");
            return sScopeMap.get(screen.getScopeName());
        }
    }

    public static void registerScope(MortarScope scope) {
        sScopeMap.put(scope.getName(), scope);
    }

    private static void cleanScopeMap() {
        Iterator<Map.Entry<String, MortarScope>> iterator = sScopeMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, MortarScope> entry = iterator.next();
            if (entry.getValue().isDestroyed()) {
                iterator.remove();
            }
        }
    }

    private static void destroyScreenScope(String scopeName) {
        MortarScope mortarScope = sScopeMap.get(scopeName);
        mortarScope.destroy();
        cleanScopeMap();
    }

    private static String getParentScopeName(AbstractScreen screen) {
        return null;
    }

    private static MortarScope createScreenScope(AbstractScreen screen) {
        Log.e(TAG, "createScreenScope with name: " + screen.getScopeName());
        String parentScopeName = getParentScopeName(screen);
        MortarScope parentScope = sScopeMap.get(parentScopeName);
        Object parentComponent = screen.createScreenComponent(parentScope
                .getService(DaggerService.SERVICE_NAME));
        return null;
    }
}
