package com.testography.am_mvp.ui.screens.catalog;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.testography.am_mvp.data.storage.dto.ProductDto;

import java.util.ArrayList;
import java.util.List;

public class CatalogAdapter extends PagerAdapter {

    private List<ProductDto> mProductList = new ArrayList<>();

    public CatalogAdapter() {

    }

    @Override
    public int getCount() {
        return mProductList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    public void addItem(ProductDto product) {
        mProductList.add(product);
        notifyDataSetChanged();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ProductDto product = mProductList.get(position);
        // TODO: 27-Nov-16 create mortar context for product screen
        Context productContext = null;

        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
