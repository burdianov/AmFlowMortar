package com.testography.am_mvp.ui.screens.address;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.testography.am_mvp.mvp.views.IAddressView;

import butterknife.ButterKnife;

public class AddressView extends RelativeLayout implements IAddressView {

    private AddressScreen.AddressPresenter mPresenter;

    public AddressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //region ==================== flow view lifecycle callbacks ===================
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            mPresenter.takeView(this);
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (!isInEditMode()) {
            mPresenter.dropView(this);
        }
    }
    //endregion

    @Override
    public void showInputError() {

    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }
}
