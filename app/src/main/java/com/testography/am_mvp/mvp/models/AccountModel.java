package com.testography.am_mvp.mvp.models;

import android.net.Uri;

import com.testography.am_mvp.data.storage.dto.UserAddressDto;
import com.testography.am_mvp.data.storage.dto.UserDto;

import java.util.ArrayList;
import java.util.Map;

public class AccountModel extends AbstractModel {

    public UserDto getUserDto() {
        return new UserDto(getUserProfileInfo(), getUserAddresses(), getUserSettings());
    }

    private Map<String, String> getUserProfileInfo() {
        return null;//mDataManager.getUserProfileInfo();
    }

    private ArrayList<UserAddressDto> getUserAddresses() {
        return null;//mDataManager.getUserAddress();
    }

    private Map<String, Boolean> getUserSettings() {
        return null;//mDataManager.getUserSetting();
    }

    public void saveProfileInfo(String name, String phone) {
        //mDataManager.saveProfileInfo(name, phone);
    }

    public void saveAvatarPhoto(Uri photoUri) {
        // TODO: 29-Nov-16 implement this
    }

    public void savePromoNotification(boolean isChecked) {
//        mDataManager.saveSetting(PreferencesManager.NOTIFICATION_PROMO_KEY, isChecked);
    }

    public void saveOrderNotification(boolean isChecked) {
//        mDataManager.saveSetting(PreferencesManager.NOTIFICATION_ORDER_KEY,
//                isChecked);
    }

    public void addAddress(UserAddressDto userAddressDto) {
//        mDataManager.addAddress(userAddressDto);
    }

    // TODO: 29-Nov-16 remove address
}
