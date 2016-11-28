package com.testography.am_mvp.ui.screens.account;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;

import com.testography.am_mvp.mvp.views.IAccountView;

public class AccountView extends CoordinatorLayout implements IAccountView {
    public AccountView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void changeState() {

    }

    @Override
    public void showEditState() {

    }

    @Override
    public void showPreviewState() {

    }

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public String getUserPhone() {
        return null;
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }
}
