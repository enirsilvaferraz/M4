package com.system.m4.views.components.dialogs.list;

import com.system.m4.businness.PaymentTypeBusinness;
import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.infrastructure.ConverterUtils;
import com.system.m4.repository.dtos.DTOAbs;
import com.system.m4.repository.dtos.PaymentTypeDTO;
import com.system.m4.views.vos.PaymentTypeVO;
import com.system.m4.views.vos.VOInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Enir on 19/05/2017.
 * For M4
 */

public class ListPaymentTypePresenter extends ListComponentPresenterAbs {

    public ListPaymentTypePresenter(ListComponentContract.View view) {
        super(view);
    }

    @Override
    public void requestList() {

        PaymentTypeBusinness.requestPaymentTypeList(new BusinnessListener.OnMultiResultListenner<PaymentTypeDTO>() {

            @Override
            public void onSuccess(List<PaymentTypeDTO> list) {
                List<VOInterface> voList = new ArrayList<>();
                for (PaymentTypeDTO dto : list) {
                    voList.add(ConverterUtils.fromPaymentType(dto));
                }
                getView().renderList(voList);
            }

            @Override
            public void onError(Exception e) {
                getView().showError(e.getMessage());
            }
        });
    }

    @Override
    public void requestDelete() {

        PaymentTypeBusinness.delete(ConverterUtils.fromPaymentType((PaymentTypeVO) getSelectedItem()), new BusinnessListener.OnPersistListener() {

            @Override
            public void onSuccess(DTOAbs dto) {
                getView().deleteItem(getSelectedItem());
                markItemOff();
            }

            @Override
            public void onError(Exception e) {
                getView().showError(e.getMessage());
            }
        });
    }

    @Override
    public void save(String value, final VOInterface vo) {

        vo.setName(value);
        PaymentTypeBusinness.save(ConverterUtils.fromPaymentType((PaymentTypeVO) vo), new BusinnessListener.OnPersistListener() {

            @Override
            public void onSuccess(DTOAbs dto) {
                if (vo.getKey() != null) {
                    getView().changeItem(ConverterUtils.fromPaymentType((PaymentTypeDTO) dto));
                } else {
                    getView().addItem(ConverterUtils.fromPaymentType((PaymentTypeDTO) dto));
                }
                getView().markItemOff();
            }

            @Override
            public void onError(Exception e) {
                getView().showError(e.getMessage());
            }
        });
    }

    protected PaymentTypeVO getVoInstance() {
        return new PaymentTypeVO();
    }
}
