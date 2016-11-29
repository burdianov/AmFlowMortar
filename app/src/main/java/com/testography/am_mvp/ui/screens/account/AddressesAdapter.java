package com.testography.am_mvp.ui.screens.account;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.testography.am_mvp.R;
import com.testography.am_mvp.data.storage.dto.UserAddressDto;

import java.util.ArrayList;

public class AddressesAdapter extends RecyclerView
        .Adapter<AddressesAdapter.AddressViewHolder> {

    private ArrayList<UserAddressDto> mUserAddresses;

    public AddressesAdapter(ArrayList<UserAddressDto> userAddressesDto) {
        mUserAddresses = userAddressesDto;
    }

    @Override
    public AddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .recycler_view_address, parent, false);

        return new AddressViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AddressViewHolder holder, int position) {
        holder.name.setText(mUserAddresses.get(position).getName());
        holder.street.setText(mUserAddresses.get(position).getStreet());
    }

    @Override
    public int getItemCount() {
        return mUserAddresses.size();
    }

    public static class AddressViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private EditText street;
//        this.house = house;
//        this.apartment = apartment;
//        this.floor = floor;
//        this.comment = comment;

        public AddressViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.account_address_name_txt);
            street = (EditText) itemView.findViewById(R.id
                    .account_address_street_et);
        }
    }
}
