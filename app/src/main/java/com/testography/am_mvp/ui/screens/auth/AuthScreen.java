package com.testography.am_mvp.ui.screens.auth;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.testography.am_mvp.R;
import com.testography.am_mvp.di.scopes.AuthScope;
import com.testography.am_mvp.flow.AbstractScreen;
import com.testography.am_mvp.flow.Screen;
import com.testography.am_mvp.mvp.models.AuthModel;
import com.testography.am_mvp.mvp.presenters.IAuthPresenter;
import com.testography.am_mvp.mvp.views.IRootView;
import com.testography.am_mvp.ui.activities.RootActivity;
import com.testography.am_mvp.utils.CredentialsValidator;

import dagger.Provides;
import mortar.ViewPresenter;

@Screen(R.layout.screen_auth)
public class AuthScreen extends AbstractScreen<RootActivity.Component> {

    private int mCustomState = 1;

    public void setCustomState(int customState) {
        mCustomState = customState;
    }

    public int getCustomState() {
        return mCustomState;
    }

    @Override
    public Object createScreenComponent(RootActivity.Component parentComponent) {
        return null;
    }

    //region ==================== DI ===================
    @dagger.Module
    public class Module {

        @Provides
        @AuthScope
        AuthPresenter providePresenter() {
            return new AuthPresenter();
        }

        @Provides
        @AuthScope
        AuthModel provideAuthModel() {
            return new AuthModel();
        }
    }

    @dagger.Component(dependencies = RootActivity.Component.class, modules =
            Module.class)
    @AuthScope
    public interface Component {
        void inject(AuthPresenter presenter);
        void inject(AuthView view);
    }
    //endregion

    //region ==================== Presenter ===================
    public class AuthPresenter extends ViewPresenter<AuthView> implements IAuthPresenter {

//        @Inject
//        AuthModel mAuthModel;
//        @Inject
//        RootPresenter mRootPresenter;

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);

            if (getView() != null) {
                if (checkUserAuth()) {
                    getView().hideLoginBtn();
                } else {
                    getView().showLoginBtn();
                }
            }
        }

        @Nullable
        private IRootView getRootView() {
            return null; //mRootPresenter.getView();
        }

        @Override
        public void clickOnLogin() {
            if (getView() != null && getRootView() != null) {
                if (getView().isIdle()) {
                    getView().setCustomState(AuthView.LOGIN_STATE);
                } else {
                    String email = getView().getUserEmail();
                    String password = getView().getUserPassword();

                    if (!CredentialsValidator.isValidEmail(email)) {
                        getView().requestEmailFocus();
//                    getView().showMessage(sAppContext.getString(R.string.err_msg_email));
                        return;
                    }
                    if (!CredentialsValidator.isValidPassword(password)) {
                        getView().requestPasswordFocus();
//                    getView().showMessage(sAppContext.getString(R.string.err_msg_password));
                        return;
                    }
//                    mAuthModel.loginUser(email, password);
                    getRootView().showLoad();
                    getRootView().showMessage("request for user auth");

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            getRootView().hideLoad();
                        }
                    }, 3000);
                }
            }
        }

        @Override
        public void clickOnFb() {
            if (getRootView() != null) {
                getRootView().showMessage("clickOnFb");
            }
        }

        @Override
        public void clickOnVk() {
            if (getRootView() != null) {
                getRootView().showMessage("clickOnVk");
            }
        }

        @Override
        public void clickOnTwitter() {
            if (getRootView() != null) {
                getRootView().showMessage("clickOnTwitter");
            }
        }

        @Override
        public void clickOnShowCatalog() {
            if (getView() != null && getRootView() != null) {
                // TODO: 25-Nov-16 start RootActivity

            }
        }

        @Override
        public boolean checkUserAuth() {
            return false; //mAuthModel.isAuthUser();
        }
    }
    //endregion


}
