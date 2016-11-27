package com.testography.am_mvp.ui.screens.catalog;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.squareup.picasso.Picasso;
import com.testography.am_mvp.R;
import com.testography.am_mvp.di.DaggerService;
import com.testography.am_mvp.di.scopes.CatalogScope;
import com.testography.am_mvp.flow.AbstractScreen;
import com.testography.am_mvp.flow.Screen;
import com.testography.am_mvp.mvp.models.CatalogModel;
import com.testography.am_mvp.mvp.presenters.ICatalogPresenter;
import com.testography.am_mvp.mvp.presenters.RootPresenter;
import com.testography.am_mvp.mvp.views.IRootView;
import com.testography.am_mvp.ui.activities.RootActivity;
import com.testography.am_mvp.ui.screens.auth.AuthScreen;

import javax.inject.Inject;

import dagger.Provides;
import flow.Flow;
import mortar.MortarScope;
import mortar.ViewPresenter;

@Screen(R.layout.screen_catalog)
public class CatalogScreen extends AbstractScreen<RootActivity.RootComponent> {

    @Override
    public Object createScreenComponent(RootActivity.RootComponent parentComponent) {
        return DaggerCatalogScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(new Module())
                .build();
    }

    //region ==================== DI ===================
    @dagger.Module
    public class Module {
        @Provides
        @CatalogScope
        CatalogModel provideCatalogModel() {
            return new CatalogModel();
        }

        @Provides
        @CatalogScope
        CatalogPresenter provideCatalogPresenter() {
            return new CatalogPresenter();
        }
    }

    @dagger.Component(dependencies = RootActivity.RootComponent.class, modules =
            Module.class)
    @CatalogScope
    interface Component {
        void inject(CatalogPresenter presenter);
        void inject(CatalogView view);

        CatalogModel getCatalogModel();
        Picasso getPicasso();
    }
    //endregion

    //region ==================== Presenter ===================
    public class CatalogPresenter extends ViewPresenter<CatalogView> implements
            ICatalogPresenter {

        @Inject
        RootPresenter mRootPresenter;
        @Inject
        CatalogModel mCatalogModel;

        @Override
        protected void onEnterScope(MortarScope scope) {
            super.onEnterScope(scope);
            ((Component) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);

            if (getView() != null) {
                //getView().showCatalogView(mCatalogModel.getProductList());
            }
        }

        @Override
        public void clickOnBuyButton(int position) {
            if (getView() != null) {
                if (checkUserAuth() && getRootView() != null) {
                    getRootView().showMessage("Item " + mCatalogModel.getProductList()
                            .get(position).getProductName() +
                            " added successfully to the Cart");
                } else {
                    Flow.get(getView()).set(new AuthScreen());
                }
            }
        }

        @Nullable
        public IRootView getRootView() {
            return mRootPresenter.getView();
        }

        @Override
        public boolean checkUserAuth() {
            return false;//mCatalogModel.isUserAuth();
        }
    }
    //endregion


}
