package com.testography.am_mvp.ui.screens.account;

import com.testography.am_mvp.di.scopes.AccountScope;
import com.testography.am_mvp.flow.AbstractScreen;
import com.testography.am_mvp.mvp.models.AccountModel;
import com.testography.am_mvp.mvp.presenters.IAccountPresenter;
import com.testography.am_mvp.mvp.presenters.RootPresenter;
import com.testography.am_mvp.ui.activities.RootActivity;

import dagger.Provides;
import mortar.ViewPresenter;

public class AccountScreen extends AbstractScreen<RootActivity.RootComponent> {

    private int mCustomState = 1;

    public int getCustomState() {
        return mCustomState;
    }

    public void setCustomState(int customState) {
        mCustomState = customState;
    }

    @Override
    public Object createScreenComponent(RootActivity.RootComponent parentComponent) {
        return null;
    }

    //region ==================== DI ===================
    @dagger.Module
    public class Module {
        @Provides
        @AccountScope
        AccountModel provideAccountModel() {
            return new AccountModel();
        }

        @Provides
        @AccountScope
        AccountPresenter provideAccountPresenter() {
            return new AccountPresenter();
        }
    }

    @dagger.Component(dependencies = RootActivity.RootComponent.class, modules =
            Module.class)
    @AccountScope
    public interface Component {
        void inject(AccountPresenter presenter);

        void inject(AccountView view);

        RootPresenter getRootPresenter();

        AccountModel getAccountModel();
    }
    //endregion


    //region ==================== Presenter ===================
    public class AccountPresenter extends ViewPresenter<AccountView> implements
            IAccountPresenter {

        @Override
        public void onClickAddress() {

        }

        @Override
        public void switchViewState() {

        }

        @Override
        public void switchOrder(boolean isChecked) {

        }

        @Override
        public void switchPromo(boolean isChecked) {

        }

        @Override
        public void takePhoto() {

        }

        @Override
        public void chooseCamera() {

        }

        @Override
        public void chooseGallery() {

        }
    }
    //endregion
}
