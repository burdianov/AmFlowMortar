package com.testography.am_mvp.ui.screens.catalog;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.testography.am_mvp.R;
import com.testography.am_mvp.data.storage.dto.ProductDto;
import com.testography.am_mvp.mvp.views.ICatalogView;
import com.testography.am_mvp.ui.fragments.adapters.CatalogAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

public class CatalogView extends RelativeLayout implements ICatalogView {

    @BindView(R.id.add_to_card_btn)
    Button mAddToCartBtn;
    @BindView(R.id.product_pager)
    ViewPager mProductPager;
    @BindView(R.id.indicator)
    CircleIndicator mIndicator;

    @Inject
    CatalogScreen.CatalogPresenter mPresenter;


    public CatalogView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //region ==================== flow view lifecycle callbacks ===================
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        if (!isInEditMode()) {
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            mPresenter.takeView(this);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (!isInEditMode()) {
            mPresenter.dropView(this);
        }
    }
    //endregion

    @Override
    public void showCatalogView(List<ProductDto> productsList) {
        CatalogAdapter adapter = null; //new CatalogAdapter(getChildFragmentManager
        // ());
        for (ProductDto product : productsList) {
            adapter.addItem(product);
        }
        mProductPager.setAdapter(adapter);
        mIndicator.setViewPager(mProductPager);
    }

    @Override
    public void updateProductCounter() {
        // TODO: 28-Oct-16 update count product on cart icon
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }
}