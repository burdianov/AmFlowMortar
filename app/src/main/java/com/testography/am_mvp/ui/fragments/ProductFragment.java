package com.testography.am_mvp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.testography.am_mvp.App;
import com.testography.am_mvp.R;
import com.testography.am_mvp.data.storage.dto.ProductDto;
import com.testography.am_mvp.di.DaggerService;
import com.testography.am_mvp.di.components.DaggerPicassoComponent;
import com.testography.am_mvp.di.components.PicassoComponent;
import com.testography.am_mvp.di.modules.PicassoCacheModule;
import com.testography.am_mvp.di.scopes.ProductScope;
import com.testography.am_mvp.mvp.presenters.ProductPresenter;
import com.testography.am_mvp.mvp.views.IProductView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.Provides;

public class ProductFragment extends Fragment implements IProductView, View.OnClickListener {
    public static final String TAG = "ProductFragment";
    private static final String PRODUCT_KEY = "PRODUCT";

    @BindView(R.id.product_name_txt)
    TextView mProductNameTxt;
    @BindView(R.id.product_description_txt)
    TextView mProductDescriptionTxt;
    @BindView(R.id.product_image)
    ImageView mProductImage;
    @BindView(R.id.product_count_txt)
    TextView mProductCountTxt;
    @BindView(R.id.product_price_txt)
    TextView mProductPriceTxt;
    @BindView(R.id.plus_btn)
    ImageButton mPlusBtn;
    @BindView(R.id.minus_btn)
    ImageButton mMinusBtn;

    @Inject
    Picasso mPicasso;

    @Inject
    ProductPresenter mPresenter;

    public ProductFragment() {

    }

    public static ProductFragment newInstance(ProductDto product) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(PRODUCT_KEY, product);
        ProductFragment fragment = new ProductFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            ProductDto product = bundle.getParcelable(PRODUCT_KEY);
            Component component = createDaggerComponent(product);
            component.inject(this);
            // TODO: 04-Nov-16 fix recreate component
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        ButterKnife.bind(this, view);
        readBundle(getArguments());
        mPresenter.takeView(this);
        mPresenter.initView();
        mPlusBtn.setOnClickListener(this);
        mMinusBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onDestroyView() {
        mPresenter.dropView();
        super.onDestroyView();
    }

    //region ==================== IProductView ===================
    @Override
    public void showProductView(final ProductDto product) {
        mProductNameTxt.setText(product.getProductName());
        mProductDescriptionTxt.setText(product.getDescription());
        mProductCountTxt.setText(String.valueOf(product.getCount()));
        if (product.getCount() > 0) {
            mProductPriceTxt.setText(String.valueOf(product.getCount() * product
                    .getPrice() + ".-"));
        } else {
            mProductPriceTxt.setText(String.valueOf(product.getPrice() + ".-"));
        }

        //region ==================== TO BE FINE-TUNED ===================

//        DisplayMetrics metrics = new DisplayMetrics();
//        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
//
//        final int width, height;
//        if (metrics.widthPixels < metrics.heightPixels) {
//            width = (int) (metrics.widthPixels / 2f);
//            height = (int) (width / 1.78f);
//        } else {
//            height = (int) (metrics.heightPixels / 5f);
//            width = (int) (height * 1.78f);
//        }
//
//        mPicasso.load(product.getImageUrl())
//                .networkPolicy(NetworkPolicy.OFFLINE)
//                .resize(width, height)
//                .centerCrop()
//                .placeholder(R.drawable.placeholder)
//                .into(mProductImage, new Callback() {
//                    @Override
//                    public void onSuccess() {
//                        Log.e(TAG, "onSuccess: load from cache");
//                    }
//
//                    @Override
//                    public void onError() {
//                        mPicasso.load(product.getImageUrl())
//                                .resize(width, height)
//                                .centerCrop()
//                                .placeholder(R.drawable.placeholder)
//                                .into(mProductImage);
//                    }
//                });
        //endregion

        mPicasso.load(product.getImageUrl())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .fit()
                .centerInside()
                .placeholder(R.drawable.placeholder)
                .into(mProductImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.e(TAG, "onSuccess: load from cache");
                    }

                    @Override
                    public void onError() {
                        mPicasso.load(product.getImageUrl())
                                .fit()
                                .centerInside()
                                .placeholder(R.drawable.placeholder)
                                .into(mProductImage);
                    }
                });

//        Glide.with(getActivity())
//                .load(product.getImageUrl())
//                .placeholder(R.drawable.placeholder)
//                .error(R.drawable.placeholder)
//                .fitCenter()
//                .dontAnimate()
////                .crossFade()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(mProductImage);
    }

    @Override
    public void updateProductCountView(ProductDto product) {
        mProductCountTxt.setText(String.valueOf(product.getCount()));
        if (product.getCount() >= 0) {
            mProductPriceTxt.setText(String.valueOf(product.getCount() * product
                    .getPrice() + ".-"));
        }
    }

    //endregion

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.minus_btn:
                mPresenter.clickOnMinus();
                break;
            case R.id.plus_btn:
                mPresenter.clickOnPlus();
                break;
        }
    }

    //region ==================== DI ===================

    private Component createDaggerComponent(ProductDto product) {
        PicassoComponent picassoComponent = DaggerService.getComponent(PicassoComponent.class);
        if (picassoComponent == null) {
            picassoComponent = DaggerPicassoComponent.builder()
                    .appComponent(App.getAppComponent())
                    .picassoCacheModule(new PicassoCacheModule())
                    .build();
            DaggerService.registerComponent(PicassoComponent.class, picassoComponent);
        }
        return DaggerProductFragment_Component.builder()
                .picassoComponent(picassoComponent)
                .module(new Module(product))
                .build();
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    @dagger.Module
    public class Module {
        ProductDto mProductDto;

        public Module(ProductDto productDto) {
            mProductDto = productDto;
        }

        @Provides
        @ProductScope
        ProductPresenter provideProductPresenter() {
            return new ProductPresenter(mProductDto);
        }
    }

    @dagger.Component(dependencies = PicassoComponent.class, modules = Module.class)
    @ProductScope
    interface Component {
        void inject(ProductFragment fragment);
    }

    //endregion


}
