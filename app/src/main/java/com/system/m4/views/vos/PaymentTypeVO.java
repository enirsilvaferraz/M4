package com.system.m4.views.vos;

import android.os.Parcel;

import com.system.m4.repository.dtos.PaymentTypeDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eferraz on 30/04/17.
 * For M4
 */

public class PaymentTypeVO implements VOInterface {

    public static final Creator<PaymentTypeVO> CREATOR = new Creator<PaymentTypeVO>() {
        @Override
        public PaymentTypeVO createFromParcel(Parcel source) {
            return new PaymentTypeVO(source);
        }

        @Override
        public PaymentTypeVO[] newArray(int size) {
            return new PaymentTypeVO[size];
        }
    };

    private String key;
    private String name;

    public PaymentTypeVO(String name) {
        this.name = name;
    }

    protected PaymentTypeVO(Parcel in) {
        this.key = in.readString();
        this.name = in.readString();
    }

    public static List<PaymentTypeVO> asList(List<PaymentTypeDTO> dtolist) {
        List<PaymentTypeVO> volist = new ArrayList<>();
        for (PaymentTypeDTO dto : dtolist) {
            volist.add(new PaymentTypeVO(dto.getName()));
        }
        return volist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeString(this.name);
    }
}
