package com.testography.am_mvp.ui.screens.address;

import com.testography.am_mvp.flow.AbstractScreen;
import com.testography.am_mvp.mvp.models.AccountModel;
import com.testography.am_mvp.mvp.presenters.IAddressPresenter;
import com.testography.am_mvp.ui.screens.account.AccountScreen;

import javax.inject.Inject;

import flow.Flow;
import flow.TreeKey;
import mortar.MortarScope;
import mortar.ViewPresenter;

public class AddressScreen extends AbstractScreen<AccountScreen.Component>
        implements TreeKey {

    @Override
    public Object createScreenComponent(AccountScreen.Component parentComponent) {
        return null;
    }

    @Override
    public Object getParentKey() {
        return new AccountScreen();
    }

    //region ==================== DI ===================

    //endregion


    //region ==================== Presenter ===================
    public class AddressPresenter extends ViewPresenter<AddressView> implements
            IAddressPresenter {

        @Inject
        AccountModel mAccountModel;

        @Override
        protected void onEnterScope(MortarScope scope) {
            super.onEnterScope(scope);
        }

        @Override
        public void clickOnAddAddress() {
            // TODO: 29-Nov-16 save address in model
            Flow.get(getView()).goBack();
        }
    }
    //endregion
}
